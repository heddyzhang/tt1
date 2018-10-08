package kit.kurac.KuraChokuDataIn;

import static fb.com.IKitComConst.*;
import static fb.com.IKitComConstHM.*;
import static kit.kurac.KuraChokuDataIn.IKuracKuraChokuDataIn.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kit.syuka.JuchuUriageDenpyoHako.SyukaJuchuUriageDenpyoHako_NumberClient;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionErrors;

import fb.com.ComHaitaService;
import fb.com.ComUserSession;
import fb.com.ComUtil;
import fb.inf.KitRrkUpdateService;
import fb.inf.exception.DataNotFoundException;
import fb.inf.exception.DeadLockException;
import fb.inf.exception.MaxRowsException;
import fb.inf.exception.UniqueKeyViolatedException;
import fb.inf.exception.UpdateRowException;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * データベースへの書き込みを実装するビジネスロジッククラスです。
 *
 */
public class KuracKuraChokuDataIn_SyukaService extends KitRrkUpdateService {

	/**
	 * シリアルID
	 */
	private static final long serialVersionUID = -972054795100312620L;

	/** クラス名. */
	private static String className__ = KuracKuraChokuDataIn_SyukaService.class.getName();
	/** カテゴリ. */
	private static Category category__ = Category.getInstance(className__);

	/** databaseオブジェクト */
	private PbsDatabase db_ = null;

	/** 排他サービス */
	private ComHaitaService haitaServ = null;


	/**
	 * コンストラクタです.
	 *
	 * @param request paramName
	 */
	public KuracKuraChokuDataIn_SyukaService(ComUserSession cus, PbsDatabase db_, boolean isTran, ActionErrors ae) {
		super(cus, db_, isTran, ae);
		_reset();
		this.db_ = db_;
		// 排他サービスを生成する
		haitaServ = new ComHaitaService(cus, db_, isTran, ae);
	}

	/**
	 * 入出力パラメータの初期化を行う.
	 */
	protected void _reset() {
		db_ = null;
		haitaServ = null;
	}

	/**
	 * 出荷追加処理
	 * 　蔵元入力（蔵元データ／ヘッダー部）・蔵元入力（蔵元データ／ディテール部）
	 * 　を同一トランザクションで処理
	 *
	 * @return TRUE:成功 / FALSE:失敗
	 */
	public boolean executeSyuka() {

		category__.info("出荷追加処理 START");

		db_.setTransaction();
		// 基盤側で操作ログを出力する際にはtrueを与える迂回
		db_.setTranLog(true);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		PbsDatabase db = getDatabase(); //DBコネクション

		// ==================================================
		// 出荷作成蔵元ﾃﾞｰﾀ抽出
		// ==================================================

		boolean err_flg = false;

		List<PbsRecord> recListH = new ArrayList<PbsRecord>();
		List<PbsRecord> recListD = new ArrayList<PbsRecord>();
		List<PbsRecord> recListC = new ArrayList<PbsRecord>();

		String w_uriden_no = null;
		int det_line_no = 0;
		int ctg_line_no = 0;

		// ﾍｯﾀﾞｰ処理
		PbsRecord[] kurac_hd_recs= kurac_hd_Select();

		if(kurac_hd_recs == null) {
			setErrorMessageId("errors.Select.NoData");
			return false;
		}

		for(int i=0; i<kurac_hd_recs.length; i++) {

			err_flg = false;
			recListH.clear();

			PbsRecord[] kanseiRecH = null;

			PbsRecord wksyuka_hd = new PbsRecord();

			String w_syuka_suryo_box = SU_ZERO;
			String w_syuka_suryo_set = SU_ZERO;

			String w_syuka_kingaku_tot  = SU_ZERO;
			String w_jyuryo_tot         = SU_ZERO;
			String w_syuka_youryo_tot   = SU_ZERO;
			String w_rebate1_youryo_tot = SU_ZERO;
			String w_rebate2_youryo_tot = SU_ZERO;
			String w_rebate3_youryo_tot = SU_ZERO;
			String w_rebate4_youryo_tot = SU_ZERO;
			String w_rebate5_youryo_tot = SU_ZERO;
			String w_rebateo_youryo_tot = SU_ZERO;

			/**=================================================================
			 * 出荷ﾃﾞｰﾀ_ﾍｯﾀﾞｰ部処理
			 *==================================================================**/

			//卸店詳細ﾏｽﾀｰ_ﾍｯﾀﾞｰ参照
			PbsRecord[] orosi_hd_recs = m_orosi_hd_get(kurac_hd_recs[i].getString("TATESN_CD"));
			if(orosi_hd_recs == null) {																		//対象データ無し
				setErrorMessageId("errors.unmatch.M.orosisyosaiHd", kurac_hd_recs[i].getString("TATESN_CD"));
				continue;
			}

			//卸店ﾏｽﾀｰ、引取運賃区分ﾏｽﾀｰ参照
			PbsRecord[] orositen_recs = m_orositen_get(kurac_hd_recs[i].getString("TATESN_CD"));
			if(orositen_recs == null) {																		//対象データ無し
				setErrorMessageId("errors.unmatch.M.orositen", kurac_hd_recs[i].getString("TATESN_CD"));
				continue;
			}

			//利用区分
			wksyuka_hd.put("RIYOU_KBN"             , AVAILABLE_KB_RIYO_KA);
			//会社CD
			wksyuka_hd.put("KAISYA_CD"             , cus.getKaisyaCd());
			//出荷先区分
			wksyuka_hd.put("SYUKA_KBN"             , SHIPMENT_KB_KAZEI);
			//ﾃﾞｰﾀ種別CD
			wksyuka_hd.put("SYUBETU_CD"            , JDATAKIND_KB_TANKA_TIGAI);
			//蔵CD
			wksyuka_hd.put("KURA_CD"               , KBN_KURA_MISU);
			//運賃区分
			wksyuka_hd.put("UNCHIN_KBN"            , kurac_hd_recs[i].getString("UNCHIN_KBN"));
			//運送店CD
			wksyuka_hd.put("UNSOTEN_CD"            , kurac_hd_recs[i].getString("UNSOTEN_CD"));
			//出荷日(売上伝票発行予定日)
			wksyuka_hd.put("SYUKA_DT"              , kurac_hd_recs[i].getString("HASO_YOTEI_DT"));
			//ミナシ日付
			wksyuka_hd.put("MINASI_DT"             , kurac_hd_recs[i].getString("HASO_YOTEI_DT"));
			//訂正時訂正元出荷日
			wksyuka_hd.put("TEISEI_SYUKA_DT"       , "");
			//訂正時訂正元売上伝票NO
			wksyuka_hd.put("TEISEI_URIDEN_NO"      , "");
			//着荷予定日
			wksyuka_hd.put("CHACUNI_YOTEI_DT"      , "");
			//荷受時間区分
			wksyuka_hd.put("NIUKE_TIME_KBN"        , orosi_hd_recs[0].getString("NIUKE_TIME_KBN"));
			//荷受時間_開始
			wksyuka_hd.put("NIUKE_BIGIN_TIME"      , orosi_hd_recs[0].getString("NIUKE_BIGIN_TIME"));
			//荷受時間_終了
			wksyuka_hd.put("NIUKE_END_TIME"        , orosi_hd_recs[0].getString("NIUKE_END_TIME"));
			//先方発注NO
			wksyuka_hd.put("SENPO_HACYU_NO"        , kurac_hd_recs[i].getString("HACYU_NO"));
			//受注NO
			wksyuka_hd.put("JYUCYU_NO"             , "");
			//縦線CD
			wksyuka_hd.put("TATESN_CD"             , kurac_hd_recs[i].getString("TATESN_CD"));
			//担当者CD
			wksyuka_hd.put("TANTOSYA_CD"           , orosi_hd_recs[0].getString("TANTOSYA_CD"));
			//特約店CD
			wksyuka_hd.put("TOKUYAKUTEN_CD"        , orosi_hd_recs[0].getString("OROSITEN_CD_1JITEN"));
			//ﾃﾞﾎﾟCD
			wksyuka_hd.put("DEPO_CD"               , orosi_hd_recs[0].getString("OROSITEN_CD_DEPO"));
			//二次店CD
			wksyuka_hd.put("NIJITEN_CD"            , orosi_hd_recs[0].getString("OROSITEN_CD_2JITEN"));
			//三次店CD
			wksyuka_hd.put("SANJITEN_CD"           , orosi_hd_recs[0].getString("OROSITEN_CD_3JITEN"));
			//酒販店（統一）CD
			wksyuka_hd.put("SYUHANTEN_CD"          , kurac_hd_recs[i].getString("SYUHANTEN_CD"));

			//特約店名(略)
			if(!PbsUtil.isEmpty(orosi_hd_recs[0].getString("OROSITEN_CD_1JITEN"))) {
				PbsRecord[] orositenNm_rec = m_orositenNm_get(orosi_hd_recs[0].getString("OROSITEN_CD_1JITEN"));
				if(orositenNm_rec == null) {																		//対象データ無し
					setErrorMessageId("errors.unmatch.M.orosi.tokuyakutenNm", kurac_hd_recs[i].getString("TATESN_CD"),
																				orosi_hd_recs[0].getString("OROSITEN_CD_1JITEN"));
					continue;
				}else {
					wksyuka_hd.put("TOKUYAKUTEN_NM_RYAKU"  , orositenNm_rec[0].getString("TEN_NM1_URIDEN"));
				}
			}
			//ﾃﾞﾎﾟ名
			if(!PbsUtil.isEmpty(orosi_hd_recs[0].getString("OROSITEN_CD_DEPO"))) {
				PbsRecord[] orositenNm_rec = m_orositenNm_get(orosi_hd_recs[0].getString("OROSITEN_CD_DEPO"));
				if(orositenNm_rec == null) {																		//対象データ無し
					setErrorMessageId("errors.unmatch.M.orosi.depotenNm", kurac_hd_recs[i].getString("TATESN_CD"),
																			orosi_hd_recs[0].getString("OROSITEN_CD_DEPO"));
					continue;
				}else {
					wksyuka_hd.put("DEPO_NM"               , orositenNm_rec[0].getString("TEN_NM1_URIDEN"));
				}
			}
			//二次店名
			if(!PbsUtil.isEmpty(orosi_hd_recs[0].getString("OROSITEN_CD_2JITEN"))) {
				PbsRecord[] orositenNm_rec = m_orositenNm_get(orosi_hd_recs[0].getString("OROSITEN_CD_2JITEN"));
				if(orositenNm_rec == null) {																		//対象データ無し
					setErrorMessageId("errors.unmatch.M.orosi.nijitenNm", kurac_hd_recs[i].getString("TATESN_CD"),
																			orosi_hd_recs[0].getString("OROSITEN_CD_2JITEN"));
					continue;
				}else {
					wksyuka_hd.put("NIJITEN_NM"               , orositenNm_rec[0].getString("TEN_NM1_URIDEN"));
				}
			}
			//三次店名
			if(!PbsUtil.isEmpty(orosi_hd_recs[0].getString("OROSITEN_CD_3JITEN"))) {
				PbsRecord[] orositenNm_rec = m_orositenNm_get(orosi_hd_recs[0].getString("OROSITEN_CD_3JITEN"));
				if(orositenNm_rec == null) {																		//対象データ無し
					setErrorMessageId("errors.unmatch.M.orosi.sanjitenNm", kurac_hd_recs[i].getString("TATESN_CD"),
																			orosi_hd_recs[0].getString("OROSITEN_CD_3JITEN"));
					continue;
				}else {
					wksyuka_hd.put("SANJITEN_NM"               , orositenNm_rec[0].getString("TEN_NM1_URIDEN"));
				}
			}
			//最終卸店名
			if(!PbsUtil.isEmpty(kurac_hd_recs[i].getString("OROSITEN_CD_LAST"))) {
				PbsRecord[] orositenNm_rec = m_orositenNm_get(kurac_hd_recs[i].getString("OROSITEN_CD_LAST"));
				if(orositenNm_rec == null) {																		//対象データ無し
					setErrorMessageId("errors.unmatch.M.orosi.lastcdNm", kurac_hd_recs[i].getString("TATESN_CD"),
																		  kurac_hd_recs[i].getString("OROSITEN_CD_LAST"));
					continue;
				}else {
					wksyuka_hd.put("OROSITEN_NM"               , orositenNm_rec[0].getString("TEN_NM1_URIDEN"));
				}
			}
			//酒販店名
			if(!PbsUtil.isEmpty(kurac_hd_recs[i].getString("SYUHANTEN_CD").trim()) & !PbsUtil.isZero(kurac_hd_recs[i].getString("SYUHANTEN_CD"))) {
				PbsRecord[] syuhantenNm_rec = m_syuhantenNm_get(kurac_hd_recs[i].getString("SYUHANTEN_CD"));
				if(syuhantenNm_rec == null) {																		//対象データ無し
					setErrorMessageId("errors.unmatch.M.syuhantenNm", kurac_hd_recs[i].getString("SYUHANTEN_CD"));
					continue;
				}else {
					wksyuka_hd.put("SYUHANTEN_NM"               , syuhantenNm_rec[0].getString("TEN_NM1_URIDEN"));
				}
			}

			//倉入・直送区分
			wksyuka_hd.put("DELIVERY_KBN"          , DELIVERY1_KB_OROSITEN_CHOKUSO);

			//請求先名称(正)SEIKYUSAKI_NM01
			PbsRecord[] seikyusakiNm_rec = m_seikyusakiNm_get(orosi_hd_recs[0].getString("OROSITEN_CD_1JITEN"));
			if(seikyusakiNm_rec == null) {																		//対象データ無し
				setErrorMessageId("errors.unmatch.M.tokuyaku.seikyusakiNm", kurac_hd_recs[i].getString("TATESN_CD"),
																			  orosi_hd_recs[0].getString("OROSITEN_CD_1JITEN"));
				continue;
			}else {
				wksyuka_hd.put("SEIKYUSAKI_NM01"       , seikyusakiNm_rec[0].getString("SEIKYUSAKI_NM01"));
				wksyuka_hd.put("SEIKYUSAKI_NM02"       , seikyusakiNm_rec[0].getString("SEIKYUSAKI_NM02"));
			}

			//出荷先国CD
			wksyuka_hd.put("SYUKA_SAKI_COUNTRY_CD" , orositen_recs[0].getString("SYUKA_SAKI_COUNTRY_CD"));
			//JISCD
			wksyuka_hd.put("JIS_CD"                , orositen_recs[0].getString("JIS_CD"));
			//引取運賃換算単価
			wksyuka_hd.put("UNCHIN_CNV_TANKA"      , orositen_recs[0].getString("KANZAN_TANKA"));
			//摘要区分 (01)
			wksyuka_hd.put("TEKIYO_KBN1"           , orosi_hd_recs[0].getString("TEKIYO_KBN1"));
			//摘要区分 (02)
			wksyuka_hd.put("TEKIYO_KBN2"           , orosi_hd_recs[0].getString("TEKIYO_KBN2"));
			//摘要区分 (03)
			wksyuka_hd.put("TEKIYO_KBN3"           , orosi_hd_recs[0].getString("TEKIYO_KBN3"));
			//摘要区分 (04)
			wksyuka_hd.put("TEKIYO_KBN4"           , orosi_hd_recs[0].getString("TEKIYO_KBN4"));
			//摘要区分 (05)
			wksyuka_hd.put("TEKIYO_KBN5"           , orosi_hd_recs[0].getString("TEKIYO_KBN5"));
			//摘要内容 (01)
			wksyuka_hd.put("TEKIYO_NM1"            , tekiyo_get(orosi_hd_recs[0].getString("TEKIYO_KBN1")));
			//摘要内容 (02)
			wksyuka_hd.put("TEKIYO_NM2"            , tekiyo_get(orosi_hd_recs[0].getString("TEKIYO_KBN2")));
			//摘要内容 (03)
			wksyuka_hd.put("TEKIYO_NM3"            , tekiyo_get(orosi_hd_recs[0].getString("TEKIYO_KBN3")));
			//摘要内容 (04)
			wksyuka_hd.put("TEKIYO_NM4"            , tekiyo_get(orosi_hd_recs[0].getString("TEKIYO_KBN4")));
			//摘要内容 (05)
			wksyuka_hd.put("TEKIYO_NM5"            , tekiyo_get(orosi_hd_recs[0].getString("TEKIYO_KBN5")));
			//扱い区分
			wksyuka_hd.put("ATUKAI_KBN"            , kurac_hd_recs[i].getString("ATUKAI_KBN"));
			//伝票打分け区分
			wksyuka_hd.put("BILL_SPLITOUT_KBN"     , kurac_hd_recs[i].getString("BILL_SPLITOUT_KBN"));
			//売上伝票発行_発行日
			wksyuka_hd.put("URIDEN_HAKKO_BI"       , PbsUtil.getToday(""));
			//売上伝票発行_発行時刻
			wksyuka_hd.put("URIDEN_HAKKO_TIME"     , PbsUtil.getCurrentHHMM());
			//売上伝票発行_発行者ID
			wksyuka_hd.put("URIDEN_HAKKO_ID"       , cus.getLoginId());
			//振替ｸﾞﾙｰﾌﾟNO
			wksyuka_hd.put("FURIKAE_GRP_NO"        , "");
			//積荷伝票NO
			wksyuka_hd.put("TUMIDEN_NO"            , "");
			//積荷累積対象区分
			wksyuka_hd.put("TUMIDEN_SM_KBN"        , "");
			//請求明細グループCD_特約店CD
			wksyuka_hd.put("SMGP_TOKUYAKUTEN_CD"   , orosi_hd_recs[0].getString("TOKUYAKUTEN_CD_SMGP"));
			//請求明細グループCD_デポCD
			wksyuka_hd.put("SMGP_DEPO_CD"          , orosi_hd_recs[0].getString("OROSITEN_CD_SMGP_DEPO_CD"));
			//請求明細グループCD_二次店CD
			wksyuka_hd.put("SMGP_NIJITEN_CD"       , orosi_hd_recs[0].getString("OROSITEN_CD_SMGP_NIJITEN_CD"));
			//請求明細グループCD_三次店CD
			wksyuka_hd.put("SMGP_SANJITEN_CD"      , orosi_hd_recs[0].getString("OROSITEN_CD_SMGP_SANJITEN_CD"));
			//請求明細グループCD_酒販店（統一）CD
			wksyuka_hd.put("SMGP_SYUHANTEN_CD"     , "");
			//請求処理区分
			wksyuka_hd.put("SEIKYU_SYORI_KBN"      , seikyusakiNm_rec[0].getString("SEIKYU_SYORI_KBN"));
			//請求鑑NO
			wksyuka_hd.put("SEIKYU_KAGAMI_NO"      , "");
			//請求相殺区分
			wksyuka_hd.put("SEIKYU_SOSAI_KBN"      , "");
			//請求〆処理日
			wksyuka_hd.put("SEIKYU_SIME_BI"        , "");
			//請求〆処理時刻
			wksyuka_hd.put("SEIKYU_SIME_TIME"      , "");
			//請求〆処理_更新者ID
			wksyuka_hd.put("SEIKYU_SIME_ID"        , "");
			//請求明細NO
			wksyuka_hd.put("SEIKYU_MEISAI_NO"      , "");

			//（一時保管用）
			//最終送荷先卸CD（一時保管用）
			wksyuka_hd.put("OROSITEN_CD_LAST"      , kurac_hd_recs[i].getString("OROSITEN_CD_LAST"));
			//採番用管轄事業所ｷｰ
			wksyuka_hd.put("JUCHU_MNG_KEY"         , kurac_hd_recs[i].getString("JUCHU_MNG_KEY"));
			//発注No
			wksyuka_hd.put("HACYU_NO"              , kurac_hd_recs[i].getString("HACYU_NO"));

			// ﾃﾞｨﾃｰﾙ処理
			PbsRecord[] kurac_dt_recs= kurac_dt_Select(kurac_hd_recs[i].getString("OROSITEN_CD_LAST"),
													   kurac_hd_recs[i].getString("ATUKAI_KBN"),
													   kurac_hd_recs[i].getString("BILL_SPLITOUT_KBN"),
													   kurac_hd_recs[i].getString("JUCHU_MNG_KEY"),
													   kurac_hd_recs[i].getString("UNCHIN_KBN"),
													   kurac_hd_recs[i].getString("UNSOTEN_CD"),
													   kurac_hd_recs[i].getString("HASO_YOTEI_DT"),
													   kurac_hd_recs[i].getString("TATESN_CD"),
													   kurac_hd_recs[i].getString("HACYU_NO"),
													   kurac_hd_recs[i].getString("SYUHANTEN_CD"));


			recListD.clear();
			recListC.clear();

			det_line_no = 0;
			ctg_line_no = 0;

			for(int j=0; j<kurac_dt_recs.length; j++) {

				PbsRecord wksyuka_dt = new PbsRecord();

				/**=================================================================
				 * 出荷ﾃﾞｰﾀ_ﾃﾞｨﾃｰﾙ処理
				 *==================================================================**/

				//商品ﾏｽﾀｰ、製品ﾏｽﾀｰ参照
				PbsRecord[] syohin_recs = m_syohin_get(kurac_dt_recs[j].getString("SHOHIN_CD"));
				if(syohin_recs == null) {																		//対象データ無し
					setErrorMessageId("errors.unmatch.M.syohin", kurac_dt_recs[j].getString("SHOHIN_CD"));
					err_flg = true;
					break;
				}

				//卸店詳細ﾏｽﾀｰ_ﾃﾞｨﾃｰﾙ参照
				PbsRecord[] orosi_dt_recs = m_orosi_dt_get(kurac_hd_recs[i].getString("TATESN_CD"),
														   syohin_recs[0].getString("KTKSY_CD"),
														   kurac_dt_recs[j].getString("SHOHIN_CD"));
				if(orosi_dt_recs == null) {																		//対象データ無し
					setErrorMessageId("errors.unmatch.M.orosisyosaiDt", kurac_hd_recs[i].getString("TATESN_CD"),
					   														kurac_dt_recs[j].getString("SHOHIN_CD"));
					err_flg = true;
					break;
				}

				//販売部門、販売種別、販売分類区分ﾏｽﾀｰ参照
				PbsRecord[] kbn_hanbai_recs_dt = m_kbn_hanbai_get_dt(syohin_recs[0].getString("SEIHIN_CD"));
				if(kbn_hanbai_recs_dt == null) {																		//対象データ無し
					setErrorMessageId("errors.unmatch.M.hanbai.kakusyu1", kurac_dt_recs[j].getString("SHOHIN_CD"));
					err_flg = true;
					break;
				}

				//利用区分
				wksyuka_dt.put("RIYOU_KBN"        , AVAILABLE_KB_RIYO_KA);
				//会社CD
				wksyuka_dt.put("KAISYA_CD"        , cus.getKaisyaCd());
				//売上伝票行NO
				wksyuka_dt.put("URIDEN_LINE_NO"   , PbsUtil.iToS(++det_line_no));
				//蔵CD
				wksyuka_dt.put("KURA_CD"          , KBN_KURA_MISU);
				//倉庫CD
				wksyuka_dt.put("SOUKO_CD"         , EMPTY_SOUKO_CD);
				//寄託者CD
				wksyuka_dt.put("KTKSY_CD"         , syohin_recs[0].getString("KTKSY_CD"));
				//商品CD
				wksyuka_dt.put("SHOHIN_CD"        , kurac_dt_recs[j].getString("SHOHIN_CD"));
				//製品CD
				wksyuka_dt.put("SEIHIN_CD"        , syohin_recs[0].getString("SEIHIN_CD"));
				//仕入先CD
				wksyuka_dt.put("SIIRESAKI_CD"     , syohin_recs[0].getString("SIIRESAKI_CD"));
				//仕入品CD
				wksyuka_dt.put("SIIREHIN_CD"      , syohin_recs[0].getString("SIIREHIN_CD"));
				//製品日付
				wksyuka_dt.put("SEIHIN_DT"        , "");
				//商品名称_自社各帳票用(1)
				wksyuka_dt.put("SHNNM_REPORT1"    , syohin_recs[0].getString("SHOHIN_NM_URIDEN"));
				//容器名称_自社各帳票用
				wksyuka_dt.put("YOUKI_NM_REPORT"  , syohin_recs[0].getString("YOUKI_KIGO_NM1"));
				//ｹｰｽ入数
				wksyuka_dt.put("CASE_IRISU"       , syohin_recs[0].getString("CASE_IRISU"));
				//ﾊﾞﾗ容量(L)
				wksyuka_dt.put("BARA_YOURYO"      , syohin_recs[0].getString("TNPN_VOL"));
				//身入重量(KG)_ｹｰｽ
				wksyuka_dt.put("MIIRI_WEIGHT_CASE" , syohin_recs[0].getString("MIIRI_WEIGHT_BOX"));
				//身入重量(KG)_ﾊﾞﾗ
				wksyuka_dt.put("MIIRI_WEIGHT_BARA" , syohin_recs[0].getString("MIIRI_WEIGHT_BARA"));
				//運賃掛率(%)
				wksyuka_dt.put("UNCHIN_KAKERITU"  , syohin_recs[0].getString("UNCHIN_KEIGEN_RATE"));
				//販売単価
				wksyuka_dt.put("HANBAI_TANKA"     , kurac_dt_recs[j].getString("HANBAI_TANKA"));
				//補足欄
				wksyuka_dt.put("HOSOKURAN"        , "");

				//出荷数量_箱数
				wksyuka_dt.put("SYUKA_SU_CASE"    , "0");
				//出荷数量_ｾｯﾄ数
				wksyuka_dt.put("SYUKA_SU_BARA"    , kurac_dt_recs[j].getString("SHOHIN_SET"));
				//出荷数量_換算総ｾｯﾄ数
				wksyuka_dt.put("SYUKA_ALL_BARA"   , ComUtil.sobaraKeisan("0", kurac_dt_recs[j].getString("SHOHIN_SET"), "0"));
				//出荷重量(KG)
				wksyuka_dt.put("SYUKA_ALL_WEIGTH" , ComUtil.calcJyuryo("0", "0", kurac_dt_recs[j].getString("SHOHIN_SET"), syohin_recs[0].getString("MIIRI_WEIGHT_BARA")));

				//販売額
				wksyuka_dt.put("SYUKA_HANBAI_KINGAKU" , ComUtil.getGk(wksyuka_dt.getString("SYUKA_ALL_BARA"), kurac_dt_recs[j].getString("HANBAI_TANKA"), true));
				//出荷対応区分
				wksyuka_dt.put("SYUKA_TAIO_KBN"   , syohin_recs[0].getString("SYUKA_TAIO_KBN"));
				//扱い区分
				wksyuka_dt.put("ATUKAI_KBN"       , syohin_recs[0].getString("ATUKAI_KBN"));
				//物品区分
				wksyuka_dt.put("BUPIN_KBN"        , syohin_recs[0].getString("BUPIN_KBN"));
				//販売単価変更ﾌﾗｸﾞ
				wksyuka_dt.put("HTANKA_CHG_FLG"       , "0");
				//容量（L）_出荷総容量
				wksyuka_dt.put("SYUKA_SOUYOURYO"      , getYORYO(wksyuka_dt.getString("SYUKA_ALL_BARA"), syohin_recs[0].getString("TNPN_VOL")));
				//容量（L）_ﾘﾍﾞｰﾄⅠ類対象総容量
				wksyuka_dt.put("REBATE1_SOUYOURYO"    , getYORYO(wksyuka_dt.getString("SYUKA_ALL_BARA"), syohin_recs[0].getString("REBATE1_YOURYO")));
				//容量（L）_ﾘﾍﾞｰﾄⅡ類対象総容量
				wksyuka_dt.put("REBATE2_SOUYOURYO"    , getYORYO(wksyuka_dt.getString("SYUKA_ALL_BARA"), syohin_recs[0].getString("REBATE2_YOURYO")));
				//容量（L）_ﾘﾍﾞｰﾄⅢ類対象総容量
				wksyuka_dt.put("REBATE3_SOUYOURYO"    , getYORYO(wksyuka_dt.getString("SYUKA_ALL_BARA"), syohin_recs[0].getString("REBATE3_YOURYO")));
				//容量（L）_ﾘﾍﾞｰﾄⅣ類対象総容量
				wksyuka_dt.put("REBATE4_SOUYOURYO"    , getYORYO(wksyuka_dt.getString("SYUKA_ALL_BARA"), syohin_recs[0].getString("REBATE4_YOURYO")));
				//容量（L）_ﾘﾍﾞｰﾄⅤ類対象総容量
				wksyuka_dt.put("REBATE5_SOUYOURYO"    , getYORYO(wksyuka_dt.getString("SYUKA_ALL_BARA"), syohin_recs[0].getString("REBATE5_YOURYO")));
				//容量（L）_ﾘﾍﾞｰﾄ対象外総容量
				wksyuka_dt.put("REBATEO_SOUYOURYO"    , PbsUtil.sToSSubtraction(wksyuka_dt.getString("SYUKA_SOUYOURYO"),
			    		   								    PbsUtil.sToSAddition(wksyuka_dt.getString("REBATE1_SOUYOURYO"),
			    		   									   	 			     wksyuka_dt.getString("REBATE2_SOUYOURYO"),
			    		   										 			     wksyuka_dt.getString("REBATE3_SOUYOURYO"),
			    		   										 			     wksyuka_dt.getString("REBATE4_SOUYOURYO"),
			    		   										 			     wksyuka_dt.getString("REBATE5_SOUYOURYO"))));
				//特注指示区分
				wksyuka_dt.put("PB_TOKUCYU_KBN"       , orosi_dt_recs[0].getString("PB_TOKUCYU_KBN"));
				//PB OR 特注指示内容
				wksyuka_dt.put("PB_TOKUCYU"           , orosi_dt_recs[0].getString("PB_TOKUCYU"));
				//販売部門CD
				wksyuka_dt.put("HANBAI_BUMON_CD"      , kbn_hanbai_recs_dt[0].getString("HANBAI_BUMON_CD"));
				//販売部門名（略式）
				wksyuka_dt.put("HANBAI_BUMON_RNM"     , kbn_hanbai_recs_dt[0].getString("HANBAI_BUMON_RNM"));
				//販売種別CD
				wksyuka_dt.put("HANBAI_SYUBETU_CD"    , kbn_hanbai_recs_dt[0].getString("HANBAI_SYUBETU_CD"));
				//販売種別名（略式）
				wksyuka_dt.put("HANBAI_SYUBETU_RNM"   , kbn_hanbai_recs_dt[0].getString("HANBAI_SYUBETU_RNM"));
				//販売分類CD
				wksyuka_dt.put("HANBAI_BUNRUI_CD"     , kbn_hanbai_recs_dt[0].getString("HANBAI_BUNRUI_CD"));
				//販売分類名（略式）
				wksyuka_dt.put("HANBAI_BUNRUI_RNM"    , kbn_hanbai_recs_dt[0].getString("HANBAI_BUNRUI_RNM"));

				//EDI配送依頼(詳細)送信区分
				wksyuka_dt.put("EDI_HAISOUM_SEND_KB"  , YN_12_KB_MISOSIN);
				//EDI出荷案内送信区分
				wksyuka_dt.put("EDI_SYUKKA_SEND_KB"   , YN_12_KB_MISOSIN);

				//ﾍｯﾀﾞｰ用合計加算
				w_syuka_suryo_box    = PbsUtil.sToSAddition(w_syuka_suryo_box, wksyuka_dt.getString("SYUKA_SU_CASE"));				//出荷数量計_箱数
				w_syuka_suryo_set    = PbsUtil.sToSAddition(w_syuka_suryo_set, wksyuka_dt.getString("SYUKA_SU_BARA"));				//出荷数量計_ｾｯﾄ数
				w_syuka_kingaku_tot  = PbsUtil.sToSAddition(w_syuka_kingaku_tot, wksyuka_dt.getString("SYUKA_HANBAI_KINGAKU"));		//出荷金額計
				w_jyuryo_tot         = PbsUtil.sToSAddition(w_jyuryo_tot, wksyuka_dt.getString("SYUKA_ALL_WEIGTH"));				//重量計(KG)
				w_syuka_youryo_tot   = PbsUtil.sToSAddition(w_syuka_youryo_tot, wksyuka_dt.getString("SYUKA_SOUYOURYO"));			//容量計（L）_出荷容量計
				w_rebate1_youryo_tot = PbsUtil.sToSAddition(w_rebate1_youryo_tot, wksyuka_dt.getString("REBATE1_SOUYOURYO"));		//容量計（L）_ﾘﾍﾞｰﾄⅠ類対象容量計
				w_rebate2_youryo_tot = PbsUtil.sToSAddition(w_rebate2_youryo_tot, wksyuka_dt.getString("REBATE2_SOUYOURYO"));		//容量計（L）_ﾘﾍﾞｰﾄⅡ類対象容量計
				w_rebate3_youryo_tot = PbsUtil.sToSAddition(w_rebate3_youryo_tot, wksyuka_dt.getString("REBATE3_SOUYOURYO"));		//容量計（L）_ﾘﾍﾞｰﾄⅢ類対象容量計
				w_rebate4_youryo_tot = PbsUtil.sToSAddition(w_rebate4_youryo_tot, wksyuka_dt.getString("REBATE4_SOUYOURYO"));		//容量計（L）_ﾘﾍﾞｰﾄⅣ類対象容量計
				w_rebate5_youryo_tot = PbsUtil.sToSAddition(w_rebate5_youryo_tot, wksyuka_dt.getString("REBATE5_SOUYOURYO"));		//容量計（L）_ﾘﾍﾞｰﾄⅤ類対象容量計
				w_rebateo_youryo_tot = PbsUtil.sToSAddition(w_rebateo_youryo_tot, wksyuka_dt.getString("REBATEO_SOUYOURYO"));		//容量計（L）_ﾘﾍﾞｰﾄ対象外容量計

				recListD.add(wksyuka_dt);


				/**=================================================================
				 * 出荷先別商品ｶﾃｺﾞﾘﾃﾞｰﾀ処理
				 *==================================================================**/

				//製品構成ﾏｽﾀｰ参照

				PbsRecord[] seihin_gpr_recs = m_seihin_gpr_get(syohin_recs[0].getString("KTKSY_CD"),
															   syohin_recs[0].getString("SEIHIN_CD"));
				if(seihin_gpr_recs == null) {																		//対象データ無し
					setErrorMessageId("errors.unmatch.M.seihin_grp", kurac_dt_recs[j].getString("SHOHIN_CD"));
					err_flg = true;
					break;
				}

				for(int k=0; k< seihin_gpr_recs.length; k++){

					PbsRecord wksyuka_ctg = new PbsRecord();

					//販売部門、販売種別、販売分類、種区分ﾏｽﾀｰ参照
					PbsRecord[] kbn_hanbai_recs_ctg = kbn_hanbai_get_ctg(syohin_recs[0].getString("KTKSY_CD"),
																	     syohin_recs[0].getString("SEIHIN_CD"));
					if(kbn_hanbai_recs_ctg == null) {																		//対象データ無し
						setErrorMessageId("errors.unmatch.M.hanbai.kakusyu2", kurac_dt_recs[j].getString("SHOHIN_CD"));
						err_flg = true;
						break;
					}

					//酒税分類区分ﾏｽﾀｰ参照
					PbsRecord[] kbn_saketax_recs = kbn_saketax_get(syohin_recs[0].getString("KTKSY_CD"),
																   syohin_recs[0].getString("SEIHIN_CD"));
					if(kbn_saketax_recs == null) {																		//対象データ無し
						setErrorMessageId("errors.unmatch.M.saketax.bunrui", kurac_dt_recs[j].getString("SHOHIN_CD"));
						err_flg = true;
						break;
					}

					//製品容器ﾏｽﾀｰ参照
					PbsRecord[] youki_recs = seihin_youki_get(syohin_recs[0].getString("KTKSY_CD"),
															  syohin_recs[0].getString("SEIHIN_CD"));
					if(youki_recs == null) {																		//対象データ無し
						setErrorMessageId("errors.unmatch.M.seihinyouk", kurac_dt_recs[j].getString("SHOHIN_CD"));
						err_flg = true;
						break;
					}

					//製品ﾏｽﾀｰ参照
					PbsRecord[] seihin_recs = m_seihin_get(syohin_recs[0].getString("KTKSY_CD"),
							  								syohin_recs[0].getString("SEIHIN_CD"));
					if(seihin_recs == null) {																		//対象データ無し
						setErrorMessageId("errors.unmatch.M.seihin", kurac_dt_recs[j].getString("SHOHIN_CD"));
						err_flg = true;
						break;
					}

					//利用区分
					wksyuka_ctg.put("RIYOU_KBN"          , AVAILABLE_KB_RIYO_KA);
					//会社CD
					wksyuka_ctg.put("KAISYA_CD"          , cus.getKaisyaCd());
					//売上ｶﾃｺﾞﾘ行NO
					wksyuka_ctg.put("URI_CTGR_LINE_NO"   , PbsUtil.iToS(++ctg_line_no));
					//寄託者CD
					wksyuka_ctg.put("KTKSY_CD"           , syohin_recs[0].getString("KTKSY_CD"));
					//製品CD
					wksyuka_ctg.put("SEIHIN_CD"          , syohin_recs[0].getString("SEIHIN_CD"));
					//販売部門CD
					wksyuka_ctg.put("HANBAI_BUMON_CD"    , kbn_hanbai_recs_ctg[0].getString("HANBAI_BUMON_CD"));
					//販売部門名（略式）
					wksyuka_ctg.put("HANBAI_BUMON_RNM"   , kbn_hanbai_recs_ctg[0].getString("HANBAI_BUMON_RNM"));
					//販売種別CD
					wksyuka_ctg.put("HANBAI_SYUBETU_CD"  , kbn_hanbai_recs_ctg[0].getString("HANBAI_SYUBETU_CD"));
					//販売種別名（略式）
					wksyuka_ctg.put("HANBAI_SYUBETU_RNM" , kbn_hanbai_recs_ctg[0].getString("HANBAI_SYUBETU_RNM"));
					//販売分類CD
					wksyuka_ctg.put("HANBAI_BUNRUI_CD"   , kbn_hanbai_recs_ctg[0].getString("HANBAI_BUNRUI_CD"));
					//販売分類名（略式）
					wksyuka_ctg.put("HANBAI_BUNRUI_RNM"  , kbn_hanbai_recs_ctg[0].getString("HANBAI_BUNRUI_RNM"));
					//酒税分類CD
					wksyuka_ctg.put("SYUZEI_CD"          , kbn_saketax_recs[0].getString("SYUZEI_CD"));
					//酒税分類名（略式）
					wksyuka_ctg.put("SYUZEI_NM_RYAKU"    , kbn_saketax_recs[0].getString("SYUZEI_NM_RYAKU"));
					//種CD
					wksyuka_ctg.put("TANE_CD"            , kbn_hanbai_recs_ctg[0].getString("TANE_CD"));
					//種名称（略式）
					wksyuka_ctg.put("TANE_NM_RYAKU"      , kbn_hanbai_recs_ctg[0].getString("TANE_NM_RYAKU"));
					//素材区分
					wksyuka_ctg.put("SOZAI_KBN"          , youki_recs[0].getString("SOZAI_KBN"));
					//色区分
					wksyuka_ctg.put("COLOR_KBN"          , youki_recs[0].getString("COLOR_KBN"));
					//販売実績分類区分
					wksyuka_ctg.put("HJISEKI_BUNRUI_KBN" , youki_recs[0].getString("HJISEKI_BUNRUI_KBN"));
					//容量（L)
					wksyuka_ctg.put("VOL"                , seihin_recs[0].getString("TNPN_VOL"));
					//単価（円）
					wksyuka_ctg.put("TANKA"              , seihin_gpr_recs[k].getString("KOSEI_TANKA"));
					//出荷本数計
					wksyuka_ctg.put("SYUKA_HON"          , PbsUtil.iToS(PbsUtil.sToI(seihin_gpr_recs[k].getString("KOSEI_SEIHIN_RIYOU_TEISU")) * PbsUtil.sToI(wksyuka_dt.getString("SYUKA_ALL_BARA"))));

					int w_ctg_bara = PbsUtil.sToI(seihin_gpr_recs[k].getString("KOSEI_SEIHIN_RIYOU_TEISU")) * PbsUtil.sToI(wksyuka_dt.getString("SYUKA_ALL_BARA"));
					//出荷容量計（L)
					wksyuka_ctg.put("SYUKA_VOL"          , getYORYO(PbsUtil.iToS(w_ctg_bara), seihin_recs[0].getString("TNPN_VOL")));
					//販売金額計（円）
					wksyuka_ctg.put("HANBAI_KINGAKU"     , ComUtil.getGk(PbsUtil.iToS(w_ctg_bara), seihin_gpr_recs[k].getString("KOSEI_TANKA"), true));

					recListC.add(wksyuka_ctg);

				}	// ｶﾃｺﾞﾘｰ部 for exit

				if(err_flg) {
					break;
				}

				if((j%MAX_LINE_URIDEN)==(MAX_LINE_URIDEN - 1) || (j==kurac_dt_recs.length - 1)) {

					//出荷数量計_箱数
					wksyuka_hd.put("SYUKA_SURYO_BOX"       , ((new BigDecimal(w_syuka_suryo_box)).setScale(0, BigDecimal.ROUND_HALF_UP)).toString());
					//出荷数量計_ｾｯﾄ数
					wksyuka_hd.put("SYUKA_SURYO_SET"       , ((new BigDecimal(w_syuka_suryo_set)).setScale(0, BigDecimal.ROUND_HALF_UP)).toString());
					//出荷金額計
					wksyuka_hd.put("SYUKA_KINGAKU_TOT"     , ((new BigDecimal(w_syuka_kingaku_tot)).setScale(2, BigDecimal.ROUND_HALF_UP)).toString());
					//重量計(KG)
					wksyuka_hd.put("JYURYO_TOT"            , ((new BigDecimal(w_jyuryo_tot)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString());
					//容量計（L）_出荷容量計
					wksyuka_hd.put("SYUKA_YOURYO_TOT"      , ((new BigDecimal(w_syuka_youryo_tot)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString());
					//容量計（L）_ﾘﾍﾞｰﾄⅠ類対象容量計
					wksyuka_hd.put("REBATE1_YOURYO_TOT"    , ((new BigDecimal(w_rebate1_youryo_tot)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString());
					//容量計（L）_ﾘﾍﾞｰﾄⅡ類対象容量計
					wksyuka_hd.put("REBATE2_YOURYO_TOT"    , ((new BigDecimal(w_rebate2_youryo_tot)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString());
					//容量計（L）_ﾘﾍﾞｰﾄⅢ類対象容量計
					wksyuka_hd.put("REBATE3_YOURYO_TOT"    , ((new BigDecimal(w_rebate3_youryo_tot)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString());
					//容量計（L）_ﾘﾍﾞｰﾄⅣ類対象容量計
					wksyuka_hd.put("REBATE4_YOURYO_TOT"    , ((new BigDecimal(w_rebate4_youryo_tot)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString());
					//容量計（L）_ﾘﾍﾞｰﾄⅤ類対象容量計
					wksyuka_hd.put("REBATE5_YOURYO_TOT"    , ((new BigDecimal(w_rebate5_youryo_tot)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString());
					//容量計（L）_ﾘﾍﾞｰﾄ対象外容量計
					wksyuka_hd.put("REBATEO_YOURYO_TOT"    , ((new BigDecimal(w_rebateo_youryo_tot)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString());

					recListH.add(wksyuka_hd);

					// PbsRecに変換
					if(j<MAX_LINE_URIDEN) {
						kanseiRecH = (PbsRecord[]) recListH.toArray(new PbsRecord[recListH.size()] );
					}
					PbsRecord[] kanseiRecD = (PbsRecord[]) recListD.toArray(new PbsRecord[recListD.size()] );
					PbsRecord[] kanseiRecC = (PbsRecord[]) recListC.toArray(new PbsRecord[recListC.size()] );


					//売上伝票NOの決定
					// 自動採番
					SyukaJuchuUriageDenpyoHako_NumberClient numberClient = SyukaJuchuUriageDenpyoHako_NumberClient.getInstance();
					w_uriden_no = numberClient.getNextDenNo(db, SEQ_T_SYUKA_URIDEN_NO, MAX_LEN_URIDEN_NO);

					//出荷ﾃﾞｰﾀ_ﾍｯﾀﾞｰ 追加
					try {
						SqlInsertHd(kanseiRecH, w_uriden_no);
					} catch (SQLException e) {
						e.printStackTrace();
						return false;
					}

					//出荷ﾃﾞｰﾀ_ﾃﾞｨﾃｰﾙ 追加
					try {
						SqlInsertDt(kanseiRecD, w_uriden_no);
					} catch (SQLException e) {
						e.printStackTrace();
						return false;
					}

					//出荷先別商品ｶﾃｺﾞﾘﾃﾞｰﾀ 追加
					try {
						SqlInsertCtg(kanseiRecC, w_uriden_no);
					} catch (SQLException e) {
						e.printStackTrace();
						return false;
					}

					for(int m=0; m<kanseiRecD.length; m++) {
						//取得した売上伝票Noを蔵元ﾃﾞｰﾀ_ﾃﾞｨﾃｰﾙにセットする
						try {
							syukaNo_update(w_uriden_no,
										   kanseiRecH[0].getString("OROSITEN_CD_LAST"),
										   kanseiRecH[0].getString("ATUKAI_KBN"),
										   kanseiRecH[0].getString("BILL_SPLITOUT_KBN"),
										   kanseiRecH[0].getString("JUCHU_MNG_KEY"),
										   kanseiRecH[0].getString("UNCHIN_KBN"),
										   kanseiRecH[0].getString("UNSOTEN_CD"),
										   kanseiRecH[0].getString("SYUKA_DT"),
										   kanseiRecH[0].getString("TATESN_CD"),
										   kanseiRecH[0].getString("HACYU_NO"),
										   kanseiRecH[0].getString("SYUHANTEN_CD"),
										   kanseiRecD[m].getString("SHOHIN_CD"),
										   kanseiRecD[m].getString("HANBAI_TANKA"));
						} catch (SQLException e) {
							e.printStackTrace();
							return false;
						}
					}

					recListD.clear();
					recListC.clear();

					det_line_no = 0;
					ctg_line_no = 0;

					w_syuka_suryo_box = SU_ZERO;
					w_syuka_suryo_set = SU_ZERO;

					w_syuka_kingaku_tot  = SU_ZERO;
					w_jyuryo_tot         = SU_ZERO;
					w_syuka_youryo_tot   = SU_ZERO;
					w_rebate1_youryo_tot = SU_ZERO;
					w_rebate2_youryo_tot = SU_ZERO;
					w_rebate3_youryo_tot = SU_ZERO;
					w_rebate4_youryo_tot = SU_ZERO;
					w_rebate5_youryo_tot = SU_ZERO;
					w_rebateo_youryo_tot = SU_ZERO;

				}

			}	// ﾃﾞｨﾃｰﾙ部 for exit

			if(err_flg) {
				db.rollback();		//ロールバック
			}else {
				db.commit();		//コミット
			}
		}

		category__.info("出荷追加処理 END");

		return true;
	}


	/**
	 * 蔵元ﾃﾞｰﾀより対象ﾃﾞｰﾀのﾍｯﾀﾞｰ情報取得
	 * （未出荷ﾃﾞｰﾀの蔵直ﾃﾞｰﾀ（訂正+,-）取得）
	 * */
	public PbsRecord[] kurac_hd_Select() {

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/
		PbsDatabase db = getDatabase(); //DBコネクション

		StringBuilder sb = new StringBuilder();

		sb.append(" \n");
		sb.append("SELECT DISTINCT                                       \n");
		sb.append("       OHD.OROSITEN_CD_LAST   AS OROSITEN_CD_LAST     \n");		//最終送荷先卸CD
		sb.append("      ,MSY.ATUKAI_KBN         AS ATUKAI_KBN           \n");		//扱い区分
		sb.append("      ,CASE MSY.BUPIN_KBN                             \n");
		sb.append("            WHEN ? THEN ?                             \n");
		sb.append("            WHEN ? THEN ?                             \n");
		sb.append("            WHEN ? THEN ?                             \n");
		sb.append("            WHEN ? THEN ?                             \n");
		sb.append("            WHEN ? THEN ?                             \n");
		sb.append("            WHEN ? THEN ?                             \n");
		sb.append("            WHEN ? THEN ?                             \n");
		sb.append("            WHEN ? THEN ?                             \n");
		sb.append("            WHEN ? THEN ? END AS BILL_SPLITOUT_KBN    \n");		//伝票打分け区分
		sb.append("      ,JTA.JUCHU_MNG_KEY      AS JUCHU_MNG_KEY        \n");		//採番用管轄事業所ｷｰ
		sb.append("      ,KHD.UNCHIN_KBN         AS UNCHIN_KBN           \n");		//運賃区分
		sb.append("      ,KHD.UNSOTEN_CD         AS UNSOTEN_CD           \n");		//運送店CD
		sb.append("      ,KHD.HASO_YOTEI_DT      AS HASO_YOTEI_DT        \n");		//発送予定日
		sb.append("      ,KHD.TATESN_CD          AS TATESN_CD            \n");		//縦線CD
		sb.append("      ,KHD.HACYU_NO           AS HACYU_NO             \n");		//発注NO
		sb.append("      ,KHD.SYUHANTEN_CD       AS SYUHANTEN_CD         \n");		//酒販店（統一）CD
		sb.append("  FROM                                                \n");
		sb.append("       T_KURAC_DT KDT                                 \n");
		sb.append("  LEFT JOIN T_KURAC_HD KHD                            \n");
		sb.append("    ON(                                               \n");
		sb.append("       KDT.RIYOU_KBN     = KHD.RIYOU_KBN              \n");
		sb.append("   AND KDT.KAISYA_CD     = KHD.KAISYA_CD              \n");
		sb.append("   AND KDT.KURADATA_NO   = KHD.KURADATA_NO            \n");
		sb.append("      )                                               \n");
		sb.append("  LEFT JOIN M_SYOHIN MSY                              \n");
		sb.append("    ON(                                               \n");
		sb.append("       MSY.RIYOU_KBN     = KDT.RIYOU_KBN              \n");
		sb.append("   AND MSY.KAISYA_CD     = KDT.KAISYA_CD              \n");
		sb.append("   AND MSY.KTKSY_CD      = ?                          \n");
		sb.append("   AND MSY.SHOHIN_CD     = KDT.SHOHIN_CD              \n");
		sb.append("   AND F_GET_RIYOU_JOTAI_FLG                          \n");
		sb.append("      ( ?, MSY.JYUCYU_KAISI_DT, MSY.JYUCYU_SYURYO_DT, MSY.RIYOU_TEISI_DT ) IN ( ?, ? ) \n");
		sb.append("      )                                               \n");
		sb.append("  LEFT JOIN M_OROSISYOSAI_HD OHD                      \n");
		sb.append("    ON(                                               \n");
		sb.append("       KHD.RIYOU_KBN     = OHD.RIYOU_KBN              \n");
		sb.append("   AND KHD.KAISYA_CD     = OHD.KAISYA_CD              \n");
		sb.append("   AND KHD.TATESN_CD     = OHD.TATESN_CD              \n");
		sb.append("   AND F_GET_RIYOU_JOTAI_FLG                          \n");
		sb.append("      ( ?, OHD.NOUHIN_KAISI_DT, OHD.NOUHIN_SYURYO_DT, OHD.RIYOU_TEISI_DT ) IN ( ?, ? ) \n");
		sb.append("      )                                               \n");
		sb.append("  LEFT JOIN M_KBN_JISEKI_TANTO JTA                    \n");
		sb.append("    ON(                                               \n");
		sb.append("       JTA.RIYOU_KBN     = OHD.RIYOU_KBN              \n");
		sb.append("   AND JTA.KAISYA_CD     = OHD.KAISYA_CD              \n");
		sb.append("   AND JTA.TANTO_CD      = OHD.TANTOSYA_CD            \n");
		sb.append("      )                                               \n");
		sb.append(" WHERE                                                \n");
		sb.append("       KDT.RIYOU_KBN     = ?                          \n");		//利用区分
		sb.append("   AND KDT.KAISYA_CD     = ?                          \n");		//会社CD
		sb.append("   AND KHD.SYUBETU_CD   IN( ?, ?)                     \n");		//ﾃﾞｰﾀ種別CD
		sb.append("   AND KDT.SYUKADEN_NO  IS NULL                       \n");		//黄桜出荷伝票NO
		sb.append("   AND KDT.SHOHIN_SET   != 0                          \n");		//商品申込ｾｯﾄ数
		sb.append(" ORDER BY                                             \n");
		sb.append("       OROSITEN_CD_LAST                               \n");		//最終送荷先卸CD
		sb.append("      ,ATUKAI_KBN                                     \n");		//扱い区分
		sb.append("      ,BILL_SPLITOUT_KBN                              \n");		//伝票打分け区分
		sb.append("      ,JUCHU_MNG_KEY                                  \n");		//黄桜事業所区分
		sb.append("      ,UNCHIN_KBN                                     \n");		//運賃区分
		sb.append("      ,UNSOTEN_CD                                     \n");		//運送店CD
		sb.append("      ,HASO_YOTEI_DT                                  \n");		//発送予定日
		sb.append("      ,TATESN_CD                                      \n");		//縦線CD
		sb.append("      ,HACYU_NO                                       \n");		//発注NO
		sb.append("      ,SYUHANTEN_CD                                   \n");		//酒販店（統一）CD

		int iCc = db.prepare(true, sb.toString());
		int ii = 0;

		db.setString(iCc, ++ii, SYOHIN_KB_SYURUI);				// 物品区分:0:酒類群
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_SYURUI);		// 伝票打分け区分:01:酒類

		db.setString(iCc, ++ii, SYOHIN_KB_SYOKUHIN);				// 物品区分:1:食品（酒類外）
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_HI_SYURUI);	// 伝票打分け区分:02:非酒類

		db.setString(iCc, ++ii, SYOHIN_KB_SYOHIN);				// 物品区分:2:商品（食品外）
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_HI_SYURUI);	// 伝票打分け区分:02:非酒類

		db.setString(iCc, ++ii, SYOHIN_KB_TARU_HOSYOKIN);		// 物品区分:3:樽保証金
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_SYURUI);		// 伝票打分け区分:01:酒類

		db.setString(iCc, ++ii, SYOHIN_KB_BUPPIN);				// 物品区分:5:物品（商品外）
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_BUPIN);		// 伝票打分け区分:03:物品

		db.setString(iCc, ++ii, SYOHIN_KB_FUKUSANBUTU);			// 物品区分:6:副産物
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_HI_SYURUI);	// 伝票打分け区分:02:非酒類

		db.setString(iCc, ++ii, SYOHIN_KB_GENRYO);				// 物品区分:7:原料
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_HI_SYURUI);	// 伝票打分け区分:02:非酒類

		db.setString(iCc, ++ii, SYOHIN_KB_UNTIN);					// 物品区分:8:運賃
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_UNTIN);		// 伝票打分け区分:04:運賃

		db.setString(iCc, ++ii, SYOHIN_KB_SONOTA);				// 物品区分:9:その他
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_BUPIN);		// 伝票打分け区分:03:物品

		db.setString(iCc, ++ii, cus.getKtksyCd());					// 寄託者
		db.setString(iCc, ++ii, PbsUtil.getToday(""));				// 業務日付
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_KARI_TOROKU);		// 仮登録
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_RIYO_KA);			// 利用可能

		db.setString(iCc, ++ii, PbsUtil.getToday(""));				// 業務日付
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_KARI_TOROKU);		// 仮登録
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_RIYO_KA);			// 利用可能

		db.setString(iCc, ++ii, AVAILABLE_KB_RIYO_KA);			// 利用区分
		db.setString(iCc, ++ii, cus.getKaisyaCd());					// 会社CD
		db.setString(iCc, ++ii, KURAC_DATA_KB_TEISEIPLUS);		// ﾃﾞｰﾀ種別CD 5:訂正(+)
		db.setString(iCc, ++ii, KURAC_DATA_KB_TEISEIMINUS);		// ﾃﾞｰﾀ種別CD 6:訂正(-)

		/**=================================================================
		 *  SQL実行
		 *==================================================================**/
		db.execute(iCc);

		PbsRecord[] recs = null;
		try {
			recs = db.getPbsRecords(iCc,false);

		} catch (MaxRowsException e) {

		} catch (DataNotFoundException e) {

		}

		return recs;

	}

	/**
	 * 蔵元ﾃﾞｰﾀより対象ﾃﾞｰﾀ参照
	 * （未出荷ﾃﾞｰﾀの蔵直ﾃﾞｰﾀ（訂正+,-）取得）
	 * */
	public PbsRecord[] kurac_dt_Select(String orositen_cd_last,
										String atukai_kbn,
										String bill_splitout_kbn,
										String juchu_mng_key,
										String unchin_kbn,
										String unsoten_cd,
										String haso_yotei_dt,
										String tatesn_cd,
										String hacyu_no,
										String syuhanten_cd) {

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/
		PbsDatabase db = getDatabase(); //DBコネクション

		StringBuilder sb = new StringBuilder();

		sb.append(" \n");
		sb.append("SELECT                                                  \n");
		sb.append("       KDT.SHOHIN_CD AS SHOHIN_CD                       \n");		//商品CD
		sb.append("      ,KDT.HANBAI_TANKA AS HANBAI_TANKA                 \n");		//販売単価
		sb.append("      ,SUM(KDT.SHOHIN_SET) AS SHOHIN_SET                \n");		//商品申込ｾｯﾄ数
		sb.append("  FROM                                                  \n");
		sb.append("       T_KURAC_DT KDT                                   \n");
		sb.append("  LEFT JOIN T_KURAC_HD KHD                              \n");
		sb.append("    ON(                                                 \n");
		sb.append("       KDT.RIYOU_KBN     = KHD.RIYOU_KBN                \n");
		sb.append("   AND KDT.KAISYA_CD     = KHD.KAISYA_CD                \n");
		sb.append("   AND KDT.KURADATA_NO   = KHD.KURADATA_NO              \n");
		sb.append("      )                                                 \n");
		sb.append("  LEFT JOIN (                                           \n");
		sb.append("       SELECT                                           \n");
		sb.append("              RIYOU_KBN                                 \n");
		sb.append("             ,KAISYA_CD                                 \n");
		sb.append("             ,KTKSY_CD                                  \n");
		sb.append("             ,SHOHIN_CD                                 \n");
		sb.append("             ,ATUKAI_KBN                                \n");
		sb.append("             ,JYUCYU_KAISI_DT                           \n");
		sb.append("             ,JYUCYU_SYURYO_DT                          \n");
		sb.append("             ,RIYOU_TEISI_DT                            \n");
		sb.append("             ,CASE BUPIN_KBN                            \n");
		sb.append("                 WHEN ? THEN ?                          \n");
		sb.append("                 WHEN ? THEN ?                          \n");
		sb.append("                 WHEN ? THEN ?                          \n");
		sb.append("                 WHEN ? THEN ?                          \n");
		sb.append("                 WHEN ? THEN ?                          \n");
		sb.append("                 WHEN ? THEN ?                          \n");
		sb.append("                 WHEN ? THEN ?                          \n");
		sb.append("                 WHEN ? THEN ?                          \n");
		sb.append("                 WHEN ? THEN ? END AS BILL_SPLITOUT_KBN \n");
		sb.append("         FROM M_SYOHIN                                  \n");
		sb.append("            ) MSY                                       \n");
		sb.append("    ON(                                                 \n");
		sb.append("       MSY.RIYOU_KBN     = KDT.RIYOU_KBN                \n");
		sb.append("   AND MSY.KAISYA_CD     = KDT.KAISYA_CD                \n");
		sb.append("   AND MSY.KTKSY_CD      = ?                            \n");
		sb.append("   AND MSY.SHOHIN_CD     = KDT.SHOHIN_CD                \n");
		sb.append("   AND F_GET_RIYOU_JOTAI_FLG                            \n");
		sb.append("      ( ?, MSY.JYUCYU_KAISI_DT, MSY.JYUCYU_SYURYO_DT, MSY.RIYOU_TEISI_DT ) IN ( ?, ? ) \n");
		sb.append("      )                                                 \n");
		sb.append("  LEFT JOIN M_OROSISYOSAI_HD OHD                        \n");
		sb.append("    ON(                                                 \n");
		sb.append("       KHD.RIYOU_KBN     = OHD.RIYOU_KBN                \n");
		sb.append("   AND KHD.KAISYA_CD     = OHD.KAISYA_CD                \n");
		sb.append("   AND KHD.TATESN_CD     = OHD.TATESN_CD                \n");
		sb.append("   AND F_GET_RIYOU_JOTAI_FLG                            \n");
		sb.append("      ( ?, OHD.NOUHIN_KAISI_DT, OHD.NOUHIN_SYURYO_DT, OHD.RIYOU_TEISI_DT ) IN ( ?, ? ) \n");
		sb.append("      )                                                 \n");
		sb.append("  LEFT JOIN M_KBN_JISEKI_TANTO JTA                      \n");
		sb.append("    ON(                                                 \n");
		sb.append("       JTA.RIYOU_KBN     = OHD.RIYOU_KBN                \n");
		sb.append("   AND JTA.KAISYA_CD     = OHD.KAISYA_CD                \n");
		sb.append("   AND JTA.TANTO_CD      = OHD.TANTOSYA_CD              \n");
		sb.append("      )                                                 \n");
		sb.append(" WHERE                                                  \n");
		sb.append("       KDT.RIYOU_KBN         = ?                        \n");		//利用区分
		sb.append("   AND KDT.KAISYA_CD         = ?                        \n");		//会社CD
		sb.append("   AND KHD.SYUBETU_CD       IN( ?, ?)                   \n");		//ﾃﾞｰﾀ種別CD
		sb.append("   AND KDT.SYUKADEN_NO      IS NULL                     \n");		//黄桜出荷伝票NO
		sb.append("   AND KDT.SHOHIN_SET       != 0                        \n");		//商品申込ｾｯﾄ数
		sb.append("   AND OHD.OROSITEN_CD_LAST  = ?                        \n");		//最終送荷先卸CD
		sb.append("   AND MSY.ATUKAI_KBN        = ?                        \n");		//扱い区分
		sb.append("   AND MSY.BILL_SPLITOUT_KBN = ?                        \n");		//伝票打分け区分
		sb.append("   AND JTA.JUCHU_MNG_KEY     = ?                        \n");		//採番用管轄事業所ｷｰ
		sb.append("   AND KHD.UNCHIN_KBN        = ?                        \n");		//運賃区分
		sb.append("   AND KHD.UNSOTEN_CD        = ?                        \n");		//運送店CD
		sb.append("   AND KHD.HASO_YOTEI_DT     = ?                        \n");		//発送予定日
		sb.append("   AND KHD.TATESN_CD         = ?                        \n");		//縦線CD

		if(PbsUtil.isEmpty(hacyu_no)) {
			sb.append("   AND KHD.HACYU_NO         IS NULL                     \n");		// 発注NO
		}else {
			sb.append("   AND KHD.HACYU_NO          = ?                        \n");		// 発注NO
		}

		if(PbsUtil.isEmpty(syuhanten_cd)) {
			sb.append("   AND KHD.SYUHANTEN_CD     IS NULL                     \n");		// 酒販店（統一）CD
		}else {
			sb.append("   AND KHD.SYUHANTEN_CD      = ?                        \n");		// 酒販店（統一）CD
		}

		sb.append(" GROUP BY                                               \n");
		sb.append("       KDT.SHOHIN_CD                                    \n");		//商品CD
		sb.append("      ,KDT.HANBAI_TANKA                                 \n");		//販売単価
		sb.append(" ORDER BY                                               \n");
		sb.append("       KDT.SHOHIN_CD                                    \n");		//商品CD
		sb.append("      ,KDT.HANBAI_TANKA                                 \n");		//販売単価

		int iCc = db.prepare(true, sb.toString());
		int ii = 0;

		db.setString(iCc, ++ii, SYOHIN_KB_SYURUI);				// 物品区分:0:酒類群
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_SYURUI);		// 伝票打分け区分:01:酒類

		db.setString(iCc, ++ii, SYOHIN_KB_SYOKUHIN);				// 物品区分:1:食品（酒類外）
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_HI_SYURUI);	// 伝票打分け区分:02:非酒類

		db.setString(iCc, ++ii, SYOHIN_KB_SYOHIN);				// 物品区分:2:商品（食品外）
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_HI_SYURUI);	// 伝票打分け区分:02:非酒類

		db.setString(iCc, ++ii, SYOHIN_KB_TARU_HOSYOKIN);		// 物品区分:3:樽保証金
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_SYURUI);		// 伝票打分け区分:01:酒類

		db.setString(iCc, ++ii, SYOHIN_KB_BUPPIN);				// 物品区分:5:物品（商品外）
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_BUPIN);		// 伝票打分け区分:03:物品

		db.setString(iCc, ++ii, SYOHIN_KB_FUKUSANBUTU);			// 物品区分:6:副産物
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_HI_SYURUI);	// 伝票打分け区分:02:非酒類

		db.setString(iCc, ++ii, SYOHIN_KB_GENRYO);				// 物品区分:7:原料
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_HI_SYURUI);	// 伝票打分け区分:02:非酒類

		db.setString(iCc, ++ii, SYOHIN_KB_UNTIN);					// 物品区分:8:運賃
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_UNTIN);		// 伝票打分け区分:04:運賃

		db.setString(iCc, ++ii, SYOHIN_KB_SONOTA);				// 物品区分:9:その他
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_BUPIN);		// 伝票打分け区分:03:物品

		db.setString(iCc, ++ii, cus.getKtksyCd());					// 寄託者
		db.setString(iCc, ++ii, PbsUtil.getToday(""));				// 業務日付
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_KARI_TOROKU);		// 仮登録
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_RIYO_KA);			// 利用可能

		db.setString(iCc, ++ii, PbsUtil.getToday(""));				// 業務日付
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_KARI_TOROKU);		// 仮登録
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_RIYO_KA);			// 利用可能

		db.setString(iCc, ++ii, AVAILABLE_KB_RIYO_KA);
		db.setString(iCc, ++ii, cus.getKaisyaCd());
		db.setString(iCc, ++ii, KURAC_DATA_KB_TEISEIPLUS);		// ﾃﾞｰﾀ種別CD 5:訂正(+)
		db.setString(iCc, ++ii, KURAC_DATA_KB_TEISEIMINUS);		// ﾃﾞｰﾀ種別CD 6:訂正(-)

		db.setString(iCc, ++ii, orositen_cd_last);
		db.setString(iCc, ++ii, atukai_kbn);
		db.setString(iCc, ++ii, bill_splitout_kbn);
		db.setString(iCc, ++ii, juchu_mng_key);
		db.setString(iCc, ++ii, unchin_kbn);
		db.setString(iCc, ++ii, unsoten_cd);
		db.setString(iCc, ++ii, haso_yotei_dt);
		db.setString(iCc, ++ii, tatesn_cd);

		if(!PbsUtil.isEmpty(hacyu_no)) {
			db.setString(iCc, ++ii, hacyu_no);
		}

		if(!PbsUtil.isEmpty(syuhanten_cd)) {
			db.setString(iCc, ++ii, syuhanten_cd);
		}

		/**=================================================================
		 *  SQL実行
		 *==================================================================**/
		db.execute(iCc);

		PbsRecord[] recs = null;
		try {
			recs = db.getPbsRecords(iCc,false);

		} catch (MaxRowsException e) {

		} catch (DataNotFoundException e) {

		}

		return recs;

	}


	/**
	 * 商品ﾏｽﾀｰ、製品ﾏｽﾀｰ抽出
	 * */
	public PbsRecord[] m_syohin_get(String shohin_cd) {

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/
		PbsDatabase db = getDatabase(); //DBコネクション

		StringBuilder sb = new StringBuilder();

		sb.append(" \n");
		sb.append("SELECT                                                             \n");
		sb.append("       SYOM.KTKSY_CD                         AS KTKSY_CD           \n");		//寄託者
		sb.append("      ,SYOM.SHOHIN_NM_URIDEN                 AS SHOHIN_NM_URIDEN   \n");		//商品名_売上伝票用
		sb.append("      ,SYOM.YOUKI_KIGO_NM1                   AS YOUKI_KIGO_NM1     \n");		//売伝用 ｾｯﾄ記号/容量名(1)
		sb.append("      ,SYOM.SEIHIN_CD                        AS SEIHIN_CD          \n");		//製品CD
		sb.append("      ,CASE WHEN SYOM.MIIRI_WEIGHT_BOX IS NULL                     \n");
		sb.append("            THEN 0                                                 \n");
		sb.append("            ELSE SYOM.MIIRI_WEIGHT_BOX   END AS MIIRI_WEIGHT_BOX   \n");		//身入重量(KG)_箱
		sb.append("      ,CASE WHEN SYOM.MIIRI_WEIGHT_BARA IS NULL                    \n");
		sb.append("            THEN 0                                                 \n");
		sb.append("            ELSE SYOM.MIIRI_WEIGHT_BARA  END AS MIIRI_WEIGHT_BARA  \n");		//身入重量(KG)_バラ
		sb.append("      ,SYOM.SYUKA_TAIO_KBN                   AS SYUKA_TAIO_KBN     \n");		//出荷対応区分
		sb.append("      ,CASE WHEN SYOM.UNCHIN_KEIGEN_RATE IS NULL                   \n");
		sb.append("            THEN 0                                                 \n");
		sb.append("            ELSE SYOM.UNCHIN_KEIGEN_RATE END AS UNCHIN_KEIGEN_RATE \n");		//配送運賃軽減率(%)
		sb.append("      ,CASE WHEN SYOM.HKAKAKU_SEISANSYA IS NULL                    \n");
		sb.append("            THEN 0                                                 \n");
		sb.append("            ELSE SYOM.HKAKAKU_SEISANSYA  END AS HKAKAKU_SEISANSYA  \n");		//販売価格_生産者価格
		sb.append("      ,CASE WHEN SYOM.REBATE1_YOURYO IS NULL                       \n");
		sb.append("            THEN 0                                                 \n");
		sb.append("            ELSE SYOM.REBATE1_YOURYO     END AS REBATE1_YOURYO     \n");		//ﾘﾍﾞｰﾄ対象容量（L)_Ⅰ類
		sb.append("      ,CASE WHEN SYOM.REBATE2_YOURYO IS NULL                       \n");
		sb.append("            THEN 0                                                 \n");
		sb.append("            ELSE SYOM.REBATE2_YOURYO     END AS REBATE2_YOURYO     \n");		//ﾘﾍﾞｰﾄ対象容量（L)_Ⅱ類
		sb.append("      ,CASE WHEN SYOM.REBATE3_YOURYO IS NULL                       \n");
		sb.append("            THEN 0                                                 \n");
		sb.append("            ELSE SYOM.REBATE3_YOURYO     END AS REBATE3_YOURYO     \n");		//ﾘﾍﾞｰﾄ対象容量（L)_Ⅲ類
		sb.append("      ,CASE WHEN SYOM.REBATE4_YOURYO IS NULL                       \n");
		sb.append("            THEN 0                                                 \n");
		sb.append("            ELSE SYOM.REBATE4_YOURYO     END AS REBATE4_YOURYO     \n");		//ﾘﾍﾞｰﾄ対象容量（L)_Ⅳ類
		sb.append("      ,CASE WHEN SYOM.REBATE5_YOURYO IS NULL                       \n");
		sb.append("            THEN 0                                                 \n");
		sb.append("            ELSE SYOM.REBATE5_YOURYO     END AS REBATE5_YOURYO     \n");		//ﾘﾍﾞｰﾄ対象容量（L)_Ⅴ類
		sb.append("      ,SYOM.TUMIDEN_LINE_NO                  AS TUMIDEN_LINE_NO    \n");		//積荷伝票用ﾗｲﾝNO
		sb.append("      ,SYOM.BUPIN_KBN                        AS BUPIN_KBN          \n");		//物品区分
		sb.append("      ,SYOM.ATUKAI_KBN                       AS ATUKAI_KBN         \n");		//扱い区分
		sb.append("      ,SEIM.CASE_IRISU                       AS CASE_IRISU         \n");		//ｹｰｽ入数
		sb.append("      ,CASE WHEN SEIM.TNPN_VOL IS NULL                             \n");
		sb.append("            THEN 0                                                 \n");
		sb.append("            ELSE SEIM.TNPN_VOL           END AS TNPN_VOL           \n");		//単品総容量（L)@1ﾊﾞﾗ当り
		sb.append("      ,SYOM.SIIRESAKI_CD                     AS SIIRESAKI_CD       \n");		//仕入先CD
		sb.append("      ,SYOM.SIIREHIN_CD                      AS SIIREHIN_CD        \n");		//仕入品CD
		sb.append("      ,SEIM.TANE_CD                          AS TANE_CD            \n");		//種CD
		sb.append("  FROM                                                             \n");
		sb.append("       M_SYOHIN SYOM                                               \n");
		sb.append(" INNER JOIN M_SEIHIN SEIM                                          \n");
		sb.append("    ON(                                                            \n");
		sb.append("       SEIM.RIYOU_KBN         = SYOM.RIYOU_KBN                     \n");
		sb.append("   AND SEIM.KAISYA_CD         = SYOM.KAISYA_CD                     \n");
		sb.append("   AND SEIM.KTKSY_CD          = SYOM.KTKSY_CD                      \n");
		sb.append("   AND SEIM.SEIHIN_CD         = SYOM.SEIHIN_CD                     \n");
		sb.append("   AND  F_GET_RIYOU_JOTAI_FLG                  \n");
		sb.append("      ( ?, SEIM.SEISAN_KAISI_DT, SEIM.SEISAN_SYURYO_DT, SEIM.RIYOU_TEISI_DT ) IN ( ?, ? ) \n");
		sb.append("       )                                                           \n");
		sb.append(" WHERE                                                             \n");
		sb.append("       SYOM.RIYOU_KBN         = ?                                  \n");
		sb.append("   AND SYOM.KAISYA_CD         = ?                                  \n");
		sb.append("   AND SYOM.KTKSY_CD          = ?                                  \n");
		sb.append("   AND SYOM.SHOHIN_CD         = ?                                  \n");
		sb.append("   AND  F_GET_RIYOU_JOTAI_FLG                                      \n");
		sb.append("      ( ?, SYOM.JYUCYU_KAISI_DT, SYOM.JYUCYU_SYURYO_DT, SYOM.RIYOU_TEISI_DT ) IN ( ?, ? )  \n");

		int iCc = db.prepare(true, sb.toString());
		int ii = 0;

		db.setString(iCc, ++ii, PbsUtil.getToday(""));				// 業務日付
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_KARI_TOROKU);		// 仮登録
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_RIYO_KA);			// 利用可能

		db.setString(iCc, ++ii, AVAILABLE_KB_RIYO_KA);
		db.setString(iCc, ++ii, cus.getKaisyaCd());
		db.setString(iCc, ++ii, cus.getKtksyCd());
		db.setString(iCc, ++ii, shohin_cd);
		db.setString(iCc, ++ii, PbsUtil.getToday(""));				// 業務日付
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_KARI_TOROKU);		// 仮登録
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_RIYO_KA);			// 利用可能

		/**=================================================================
		 *  SQL実行
		 *==================================================================**/
		db.execute(iCc);

		PbsRecord[] recs = null;
		try {
			recs = db.getPbsRecords(iCc,false);

		} catch (MaxRowsException e) {

		} catch (DataNotFoundException e) {

		}

		return recs;

	}


	/**
	 * 卸店詳細ﾏｽﾀｰﾍｯﾀﾞｰ抽出
	 * */
	public PbsRecord[] m_orosi_hd_get(String tatesen_cd) {

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/
		PbsDatabase db = getDatabase(); //DBコネクション

		StringBuilder sb = new StringBuilder();

		sb.append(" \n");
		sb.append("SELECT                                     \n");
		sb.append("       TANTOSYA_CD                         \n");		//販売実績担当者CD
		sb.append("      ,OROSITEN_CD_1JITEN                  \n");		//特約店CD
		sb.append("      ,OROSITEN_CD_DEPO                    \n");		//デポ店CD
		sb.append("      ,OROSITEN_CD_2JITEN                  \n");		//二次店CD
		sb.append("      ,OROSITEN_CD_3JITEN                  \n");		//三次店CD
		sb.append("      ,NIUKE_TIME_KBN                      \n");		//荷受時間区分
		sb.append("      ,NIUKE_BIGIN_TIME                    \n");		//荷受時間_開始
		sb.append("      ,NIUKE_END_TIME                      \n");		//荷受時間_終了
		sb.append("      ,TANTOSYA_CD                         \n");		//販売実績担当者CD
		sb.append("      ,TEKIYO_KBN1                         \n");		//摘要区分1
		sb.append("      ,TEKIYO_KBN2                         \n");		//摘要区分2
		sb.append("      ,TEKIYO_KBN3                         \n");		//摘要区分3
		sb.append("      ,TEKIYO_KBN4                         \n");		//摘要区分4
		sb.append("      ,TEKIYO_KBN5                         \n");		//摘要区分5
		sb.append("      ,TOKUYAKUTEN_CD_SMGP                 \n");		//請求明細グループCD_特約店CD
		sb.append("      ,OROSITEN_CD_SMGP_DEPO_CD            \n");		//請求明細グループCD_デポCD
		sb.append("      ,OROSITEN_CD_SMGP_NIJITEN_CD         \n");		//請求明細グループCD_二次店CD
		sb.append("      ,OROSITEN_CD_SMGP_SANJITEN_CD        \n");		//請求明細グループCD_三次店CD
		sb.append("  FROM                                     \n");
		sb.append("       M_OROSISYOSAI_HD OHD                \n");
		sb.append(" WHERE                                     \n");
		sb.append("       OHD.RIYOU_KBN     = ?               \n");
		sb.append("   AND OHD.KAISYA_CD     = ?               \n");
		sb.append("   AND OHD.TATESN_CD     = ?               \n");
		sb.append("   AND F_GET_RIYOU_JOTAI_FLG               \n");
		sb.append("      ( ?, OHD.NOUHIN_KAISI_DT, OHD.NOUHIN_SYURYO_DT, OHD.RIYOU_TEISI_DT ) IN ( ?, ? ) \n");

		int iCc = db.prepare(true, sb.toString());
		int ii = 0;

		db.setString(iCc, ++ii, AVAILABLE_KB_RIYO_KA);
		db.setString(iCc, ++ii, cus.getKaisyaCd());
		db.setString(iCc, ++ii, tatesen_cd);
		db.setString(iCc, ++ii, PbsUtil.getToday(""));				// 業務日付
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_KARI_TOROKU);		// 仮登録
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_RIYO_KA);			// 利用可能

		/**=================================================================
		 *  SQL実行
		 *==================================================================**/
		db.execute(iCc);

		PbsRecord[] recs = null;
		try {
			recs = db.getPbsRecords(iCc,false);

		} catch (MaxRowsException e) {

		} catch (DataNotFoundException e) {

		}

		return recs;

	}


	/**
	 * 卸店詳細ﾏｽﾀｰﾃﾞｨﾃｰﾙ抽出
	 * */
	public PbsRecord[] m_orosi_dt_get(String tatesen_cd, String ktksy_cd, String shohin_cd) {

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/
		PbsDatabase db = getDatabase(); //DBコネクション

		StringBuilder sb = new StringBuilder();

		sb.append(" \n");
		sb.append("SELECT                                                  \n");
		sb.append("       PB_TOKUCYU_KBN                                   \n");		//特注指示区分
		sb.append("      ,PB_TOKUCYU                                       \n");		//PB OR 特注指示内容
	    sb.append("  FROM                                                  \n");
		sb.append("       M_OROSISYOSAI_DT                                 \n");
		sb.append(" WHERE                                                  \n");
		sb.append("       RIYOU_KBN         = ?                            \n");
		sb.append("   AND KAISYA_CD         = ?                            \n");
		sb.append("   AND TATESN_CD         = ?                            \n");
		sb.append("   AND KTKSY_CD          = ?                            \n");
		sb.append("   AND SHOHIN_CD         = ?                            \n");

		int iCc = db.prepare(true, sb.toString());
		int ii = 0;

		db.setString(iCc, ++ii, AVAILABLE_KB_RIYO_KA);
		db.setString(iCc, ++ii, cus.getKaisyaCd());
		db.setString(iCc, ++ii, tatesen_cd);
		db.setString(iCc, ++ii, ktksy_cd);
		db.setString(iCc, ++ii, shohin_cd);

		/**=================================================================
		 *  SQL実行
		 *==================================================================**/
		db.execute(iCc);

		PbsRecord[] recs = null;
		try {
			recs = db.getPbsRecords(iCc,false);

		} catch (MaxRowsException e) {

		} catch (DataNotFoundException e) {

		}

		return recs;

	}


	/**
	 * 販売分類区分、販売部門区分ﾏｽﾀｰ抽出(ﾃﾞｨﾃｨｰﾙﾃﾞｰﾀ用)
	 * */
	public PbsRecord[] m_kbn_hanbai_get_dt(String seihin_cd) {

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/
		PbsDatabase db = getDatabase(); //DBコネクション

		StringBuilder sb = new StringBuilder();

		sb.append(" \n");
		sb.append("SELECT                                                         \n");
		sb.append("       M_BUNRUI.HANBAI_BUNRUI_CD                               \n");		//販売分類CD
		sb.append("      ,M_BUNRUI.HANBAI_BUNRUI_RNM                              \n");		//販売分類名（略式）
		sb.append("      ,M_SBETU.HANBAI_SYUBETU_CD                               \n");		//販売種別CD
		sb.append("      ,M_SBETU.HANBAI_SYUBETU_RNM                              \n");		//販売種別名（略式）
		sb.append("      ,M_BUMON.HANBAI_BUMON_CD                                 \n");		//販売部門CD
		sb.append("      ,M_BUMON.HANBAI_BUMON_RNM                                \n");		//販売部門名（略式）
		sb.append("  FROM                                                         \n");
		sb.append("       M_SEIHIN                 M_SEI                          \n");		//製品ﾏｽﾀｰ
		sb.append("      ,M_KBN_TANE               M_TANE                         \n");		//種区分ﾏｽﾀｰ
		sb.append("      ,M_KBN_HANBAI_BUNRUI      M_BUNRUI                       \n");		//販売分類区分ﾏｽﾀｰ
		sb.append("      ,M_KBN_HANBAI_BUMON       M_BUMON                        \n");		//販売部門区分ﾏｽﾀｰ
		sb.append("      ,M_KBN_HANBAI_SYUBETU     M_SBETU                        \n");		//販売種別区分ﾏｽﾀｰ
		sb.append(" WHERE                                                         \n");
		sb.append("       M_SEI.RIYOU_KBN            = ?                          \n");
		sb.append("   AND M_SEI.KAISYA_CD            = ?                          \n");
		sb.append("   AND M_SEI.SEIHIN_CD            = ?                          \n");
		sb.append("   AND F_GET_RIYOU_JOTAI_FLG                                   \n");
		sb.append("      ( ?, M_SEI.SEISAN_KAISI_DT, M_SEI.SEISAN_SYURYO_DT, M_SEI.RIYOU_TEISI_DT ) IN ( ?, ? )  \n");
		sb.append("   AND M_TANE.TANE_CD             = M_SEI.TANE_CD              \n");
		sb.append("   AND M_TANE.KAISYA_CD           = M_SEI.KAISYA_CD            \n");
		sb.append("   AND M_TANE.RIYOU_KBN           = M_SEI.RIYOU_KBN            \n");
		sb.append("   AND M_TANE.HANBAI_BUNRUI_CD    = M_BUNRUI.HANBAI_BUNRUI_CD  \n");
		sb.append("   AND M_TANE.RIYOU_KBN           = M_BUNRUI.RIYOU_KBN         \n");
		sb.append("   AND M_BUNRUI.HANBAI_SYUBETU_CD = M_SBETU.HANBAI_SYUBETU_CD  \n");
		sb.append("   AND M_BUNRUI.RIYOU_KBN         = M_SBETU.RIYOU_KBN          \n");
		sb.append("   AND M_SBETU.HANBAI_BUMON_CD    = M_BUMON.HANBAI_BUMON_CD    \n");
		sb.append("   AND M_SBETU.RIYOU_KBN          = M_BUMON.RIYOU_KBN          \n");


		int iCc = db.prepare(true, sb.toString());
		int ii = 0;

		db.setString(iCc, ++ii, AVAILABLE_KB_RIYO_KA);
		db.setString(iCc, ++ii, cus.getKaisyaCd());
		db.setString(iCc, ++ii, seihin_cd);
		db.setString(iCc, ++ii, PbsUtil.getToday(""));				// 業務日付
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_KARI_TOROKU);		// 仮登録
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_RIYO_KA);			// 利用可能

		/**=================================================================
		 *  SQL実行
		 *==================================================================**/
		db.execute(iCc);

		PbsRecord[] recs = null;
		try {
			recs = db.getPbsRecords(iCc,false);

		} catch (MaxRowsException e) {

		} catch (DataNotFoundException e) {

		}

		return recs;

	}


	/**
	 * 販売分類区分、販売部門区分、種区分ﾏｽﾀｰ抽出(予定出荷先別商品ｶﾃｺﾞﾘﾃﾞｰﾀ用)
	 * */
	public PbsRecord[] kbn_hanbai_get_ctg(String ktksy_cd, String seihin_cd) {

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/
		PbsDatabase db = getDatabase(); //DBコネクション

		StringBuilder sb = new StringBuilder();

		sb.append(" \n");
		sb.append("SELECT                                                         \n");
		sb.append("       M_BUNRUI.HANBAI_BUNRUI_CD                               \n");		//販売分類CD
		sb.append("      ,M_BUNRUI.HANBAI_BUNRUI_RNM                              \n");		//販売分類名（略式）
		sb.append("      ,M_SBETU.HANBAI_SYUBETU_CD                               \n");		//販売種別CD
		sb.append("      ,M_SBETU.HANBAI_SYUBETU_RNM                              \n");		//販売種別名（略式）
		sb.append("      ,M_BUMON.HANBAI_BUMON_CD                                 \n");		//販売部門CD
		sb.append("      ,M_BUMON.HANBAI_BUMON_RNM                                \n");		//販売部門名（略式）
		sb.append("      ,M_TANE.TANE_CD                                          \n");		//種CD
		sb.append("      ,M_TANE.TANE_NM_RYAKU                                    \n");		//種名称（略式）
		sb.append("  FROM                                                         \n");
		sb.append("       M_SEIHIN                 M_SEI                          \n");		//製品ﾏｽﾀｰ
		sb.append("      ,M_KBN_TANE               M_TANE                         \n");		//種区分ﾏｽﾀｰ
		sb.append("      ,M_KBN_HANBAI_BUNRUI      M_BUNRUI                       \n");		//販売分類区分ﾏｽﾀｰ
		sb.append("      ,M_KBN_HANBAI_BUMON       M_BUMON                        \n");		//販売部門区分ﾏｽﾀｰ
		sb.append("      ,M_KBN_HANBAI_SYUBETU     M_SBETU                        \n");		//販売種別区分ﾏｽﾀｰ
		sb.append("      ,M_SEIHIN_GRP             M_SGRP                         \n");		//製品構成ﾏｽﾀｰ
		sb.append(" WHERE                                                         \n");
		sb.append("       M_SGRP.RIYOU_KBN           = ?                          \n");
		sb.append("   AND M_SGRP.KAISYA_CD           = ?                          \n");
		sb.append("   AND M_SGRP.KTKSY_CD            = ?                          \n");
		sb.append("   AND M_SGRP.SEIHIN_CD           = ?                          \n");
		sb.append("   AND M_SGRP.KOSEI_SEIHIN_CD     = M_SEI.SEIHIN_CD            \n");
		sb.append("   AND M_SGRP.RIYOU_KBN           = M_SEI.RIYOU_KBN            \n");
		sb.append("   AND M_SGRP.KAISYA_CD           = M_SEI.KAISYA_CD            \n");
		sb.append("   AND M_SGRP.KTKSY_CD            = M_SEI.KTKSY_CD             \n");
		sb.append("   AND M_SEI.TANE_CD              = M_TANE.TANE_CD             \n");
		sb.append("   AND M_SEI.RIYOU_KBN            = M_TANE.RIYOU_KBN           \n");
		sb.append("   AND M_SEI.KAISYA_CD            = M_TANE.KAISYA_CD           \n");
		sb.append("   AND F_GET_RIYOU_JOTAI_FLG                                   \n");
		sb.append("      ( ?, M_SEI.SEISAN_KAISI_DT, M_SEI.SEISAN_SYURYO_DT, M_SEI.RIYOU_TEISI_DT ) IN ( ?, ? )  \n");
		sb.append("   AND M_TANE.HANBAI_BUNRUI_CD    = M_BUNRUI.HANBAI_BUNRUI_CD  \n");
		sb.append("   AND M_TANE.RIYOU_KBN           = M_BUNRUI.RIYOU_KBN         \n");
		sb.append("   AND M_TANE.KAISYA_CD           = M_SGRP.KAISYA_CD           \n");
		sb.append("   AND M_BUNRUI.HANBAI_SYUBETU_CD = M_SBETU.HANBAI_SYUBETU_CD  \n");
		sb.append("   AND M_BUNRUI.RIYOU_KBN         = M_SBETU.RIYOU_KBN          \n");
		sb.append("   AND M_SBETU.HANBAI_BUMON_CD    = M_BUMON.HANBAI_BUMON_CD    \n");
		sb.append("   AND M_SBETU.RIYOU_KBN          = M_BUMON.RIYOU_KBN          \n");

		int iCc = db.prepare(true, sb.toString());
		int ii = 0;

		db.setString(iCc, ++ii, AVAILABLE_KB_RIYO_KA);
		db.setString(iCc, ++ii, cus.getKaisyaCd());
		db.setString(iCc, ++ii, ktksy_cd);
		db.setString(iCc, ++ii, seihin_cd);
		db.setString(iCc, ++ii, PbsUtil.getToday(""));				// 業務日付
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_KARI_TOROKU);		// 仮登録
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_RIYO_KA);			// 利用可能

		/**=================================================================
		 *  SQL実行
		 *==================================================================**/
		db.execute(iCc);

		PbsRecord[] recs = null;
		try {
			recs = db.getPbsRecords(iCc,false);

		} catch (MaxRowsException e) {

		} catch (DataNotFoundException e) {

		}

		return recs;

	}


	/**
	 * 製品構成ﾏｽﾀｰ抽出
	 * */
	public PbsRecord[] m_seihin_gpr_get(String ktksy_cd, String seihin_cd) {

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/
		PbsDatabase db = getDatabase(); //DBコネクション

		StringBuilder sb = new StringBuilder();

		sb.append(" \n");
		sb.append("SELECT                                                                   \n");
		sb.append("       M_SEGRP.KOSEI_SEIHIN_CD                                           \n");		//構成製品CD
		sb.append("      ,SUM(M_SEGRP.KOSEI_SEIHIN_RIYOU_TEISU) AS KOSEI_SEIHIN_RIYOU_TEISU \n");		//構成製品利用定数
		sb.append("      ,MIN(M_SEGRP.KOSEI_TANKA)              AS KOSEI_TANKA              \n");		//構成単価（生産者価格）
		sb.append("  FROM                                                                   \n");
		sb.append("       M_SEIHIN_GRP              M_SEGRP                                 \n");		//製品構成ﾏｽﾀｰ
		sb.append(" WHERE                                                                   \n");
		sb.append("       M_SEGRP.RIYOU_KBN         = ?                                     \n");
		sb.append("   AND M_SEGRP.KAISYA_CD         = ?                                     \n");
		sb.append("   AND M_SEGRP.KTKSY_CD          = ?                                     \n");
		sb.append("   AND M_SEGRP.SEIHIN_CD         = ?                                     \n");
		sb.append(" GROUP BY                                                                \n");
		sb.append("       M_SEGRP.KOSEI_SEIHIN_CD                                           \n");

		int iCc = db.prepare(true, sb.toString());
		int ii = 0;

		db.setString(iCc, ++ii, AVAILABLE_KB_RIYO_KA);
		db.setString(iCc, ++ii, cus.getKaisyaCd());
		db.setString(iCc, ++ii, ktksy_cd);
		db.setString(iCc, ++ii, seihin_cd);

		/**=================================================================
		 *  SQL実行
		 *==================================================================**/
		db.execute(iCc);

		PbsRecord[] recs = null;
		try {
			recs = db.getPbsRecords(iCc,false);

		} catch (MaxRowsException e) {

		} catch (DataNotFoundException e) {

		}

		return recs;

	}


	/**
	 * 酒税分類区分ﾏｽﾀｰ抽出
	 * */
	public PbsRecord[] kbn_saketax_get(String ktksy_cd, String seihin_cd) {

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/
		PbsDatabase db = getDatabase(); //DBコネクション

		StringBuilder sb = new StringBuilder();

		sb.append(" \n");
		sb.append("SELECT                                                       \n");
		sb.append("       M_SAKE.SYUZEI_CD                                      \n");		//酒税分類CD
		sb.append("      ,M_SAKE.SYUZEI_NM_RYAKU                                \n");		//酒税分類名（略式）
		sb.append("  FROM                                                       \n");
		sb.append("       M_SEIHIN                 M_SEI                        \n");		//製品ﾏｽﾀｰ
		sb.append("      ,M_KBN_SAKETAX_BUNRUI     M_SAKE                       \n");		//酒税分類区分ﾏｽﾀｰ
		sb.append("      ,M_SEIHIN_GRP             M_SGRP                       \n");		//製品構成ﾏｽﾀｰ
		sb.append(" WHERE                                                       \n");
		sb.append("       M_SGRP.RIYOU_KBN         = ?                          \n");
		sb.append("   AND M_SGRP.KAISYA_CD         = ?                          \n");
		sb.append("   AND M_SGRP.KTKSY_CD          = ?                          \n");
		sb.append("   AND M_SGRP.SEIHIN_CD         = ?                          \n");
		sb.append("   AND M_SGRP.KOSEI_SEIHIN_CD   = M_SEI.SEIHIN_CD            \n");
		sb.append("   AND M_SGRP.RIYOU_KBN         = M_SEI.RIYOU_KBN            \n");
		sb.append("   AND M_SGRP.KAISYA_CD         = M_SEI.KAISYA_CD            \n");
		sb.append("   AND M_SGRP.KTKSY_CD          = M_SEI.KTKSY_CD             \n");
		sb.append("   AND M_SEI.SYUZEI_CD          = M_SAKE.SYUZEI_CD           \n");
		sb.append("   AND M_SEI.RIYOU_KBN          = M_SAKE.RIYOU_KBN           \n");
		sb.append("   AND F_GET_RIYOU_JOTAI_FLG                                 \n");
		sb.append("      ( ?, M_SEI.SEISAN_KAISI_DT, M_SEI.SEISAN_SYURYO_DT, M_SEI.RIYOU_TEISI_DT ) IN ( ?, ? )  \n");

		int iCc = db.prepare(true, sb.toString());
		int ii = 0;

		db.setString(iCc, ++ii, AVAILABLE_KB_RIYO_KA);
		db.setString(iCc, ++ii, cus.getKaisyaCd());
		db.setString(iCc, ++ii, ktksy_cd);
		db.setString(iCc, ++ii, seihin_cd);
		db.setString(iCc, ++ii, PbsUtil.getToday(""));				// 業務日付
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_KARI_TOROKU);		// 仮登録
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_RIYO_KA);			// 利用可能

		/**=================================================================
		 *  SQL実行
		 *==================================================================**/
		db.execute(iCc);

		PbsRecord[] recs = null;
		try {
			recs = db.getPbsRecords(iCc,false);

		} catch (MaxRowsException e) {

		} catch (DataNotFoundException e) {

		}

		return recs;

	}


	/**
	 * 製品容器ﾏｽﾀｰ抽出
	 * */
	public PbsRecord[] seihin_youki_get(String ktksy_cd, String seihin_cd) {

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/
		PbsDatabase db = getDatabase(); //DBコネクション

		StringBuilder sb = new StringBuilder();

		sb.append(" \n");
		sb.append("SELECT                                                        \n");
		sb.append("       M_SYOUK.HJISEKI_BUNRUI_KBN                             \n");		//販売実績分類区分
		sb.append("      ,M_SYOUK.SOZAI_KBN                                      \n");		//素材区分
		sb.append("      ,M_SYOUK.COLOR_KBN                                      \n");		//色区分
		sb.append("  FROM                                                        \n");
		sb.append("       M_SEIHIN_GRP              M_SEGRP                      \n");		//製品構成ﾏｽﾀｰ
		sb.append("      ,M_SSIZAI_GRP              M_SIGRP                      \n");		//製品用資材構成ﾏｽﾀｰ
		sb.append("      ,M_SIIREHIN                M_SIRE                       \n");		//仕入品管理ﾏｽﾀｰ
		sb.append("      ,M_SEIHINYOUK              M_SYOUK                      \n");		//製品容器ﾏｽﾀｰ
		sb.append(" WHERE                                                        \n");
		sb.append("       M_SEGRP.RIYOU_KBN         = ?                          \n");
		sb.append("   AND M_SEGRP.KAISYA_CD         = ?                          \n");
		sb.append("   AND M_SEGRP.KTKSY_CD          = ?                          \n");
		sb.append("   AND M_SEGRP.SEIHIN_CD         = ?                          \n");

		sb.append("   AND M_SEGRP.KOSEI_SEIHIN_CD   = M_SIGRP.SEIHIN_CD          \n");
		sb.append("   AND M_SEGRP.RIYOU_KBN         = M_SIGRP.RIYOU_KBN          \n");
		sb.append("   AND M_SEGRP.KAISYA_CD         = M_SIGRP.KAISYA_CD          \n");
		sb.append("   AND M_SEGRP.KTKSY_CD          = M_SIGRP.KTKSY_CD           \n");

		sb.append("   AND M_SIGRP.RIYOU_KBN         = M_SIRE.RIYOU_KBN           \n");
		sb.append("   AND M_SIGRP.KAISYA_CD         = M_SIRE.KAISYA_CD           \n");
		sb.append("   AND M_SIGRP.KTKSY_CD          = M_SIRE.KTKSY_CD            \n");
		sb.append("   AND M_SIGRP.SIIRESAKI_CD      = M_SIRE.SIIRESAKI_CD        \n");
		sb.append("   AND M_SIGRP.SIIREHIN_CD       = M_SIRE.SIIREHIN_CD         \n");

		sb.append("   AND M_SIRE.RIYOU_KBN          = M_SYOUK.RIYOU_KBN          \n");
		sb.append("   AND M_SIRE.YOUKI_CD           = M_SYOUK.YOUKI_CD           \n");
		sb.append("   AND F_GET_RIYOU_JOTAI_FLG                                  \n");
		sb.append("      ( ?, M_SIRE.USE_START_DT, M_SIRE.USE_END_DT, M_SIRE.USE_STOP_DT ) IN ( ?, ? )  \n");

		int iCc = db.prepare(true, sb.toString());
		int ii = 0;

		db.setString(iCc, ++ii, AVAILABLE_KB_RIYO_KA);
		db.setString(iCc, ++ii, cus.getKaisyaCd());
		db.setString(iCc, ++ii, ktksy_cd);
		db.setString(iCc, ++ii, seihin_cd);
		db.setString(iCc, ++ii, PbsUtil.getToday(""));				// 業務日付
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_KARI_TOROKU);		// 仮登録
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_RIYO_KA);			// 利用可能

		/**=================================================================
		 *  SQL実行
		 *==================================================================**/
		db.execute(iCc);

		PbsRecord[] recs = null;
		try {
			recs = db.getPbsRecords(iCc,false);

		} catch (MaxRowsException e) {

		} catch (DataNotFoundException e) {

		}

		return recs;

	}


	/**
	 * 卸店ﾏｽﾀｰ、引取運賃区分ﾏｽﾀｰ抽出
	 * */
	public PbsRecord[] m_orositen_get(String tatesen_cd) {

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/
		PbsDatabase db = getDatabase(); //DBコネクション

		StringBuilder sb = new StringBuilder();

		sb.append(" \n");
		sb.append("SELECT                                                      \n");
		sb.append("       ORS.JIS_CD                                           \n");		//JISCD
		sb.append("      ,ORS.SYUKA_SAKI_COUNTRY_CD                            \n");		//出荷先国CD
		sb.append("      ,ORS.TEN_NM1_URIDEN                                   \n");		//店名_売上伝票表記用_(1/2)
		sb.append("      ,HUM.KANZAN_TANKA                                     \n");		//換算単価（円）
		sb.append("  FROM                                                      \n");
		sb.append("       M_OROSISYOSAI_HD OHD                                 \n");
		sb.append("	 LEFT JOIN M_OROSITEN ORS                                  \n");
		sb.append("	   ON(                                                     \n");
		sb.append("	      ORS.RIYOU_KBN           = OHD.RIYOU_KBN              \n");
		sb.append("	  AND ORS.KAISYA_CD           = OHD.KAISYA_CD              \n");
		sb.append("	  AND ORS.OROSITEN_CD         = OHD.OROSITEN_CD_LAST       \n");
		sb.append("	  AND F_GET_RIYOU_JOTAI_FLG                                \n");
		sb.append("	      ( ?, ORS.NOUHIN_KAISI_DT, ORS.NOUHIN_SYURYO_DT, ORS.RIYOU_TEISI_DT ) IN ( ?, ? ) \n");
		sb.append("	     )                                                     \n");
		sb.append("	 LEFT JOIN M_KBN_HIKITORI_UNCHIN HUM                       \n");
		sb.append("	   ON(                                                     \n");
		sb.append("	      HUM.RIYOU_KBN           = OHD.RIYOU_KBN              \n");
		sb.append("	  AND HUM.KAISYA_CD           = OHD.KAISYA_CD              \n");
		sb.append("	  AND HUM.HIKITORI_UNCHIN_KBN = ORS.HIKITORI_UNCHIN_KBN    \n");
		sb.append("	     )                                                     \n");
		sb.append(" WHERE                                                      \n");
		sb.append("       OHD.RIYOU_KBN         = ?                            \n");
		sb.append("   AND OHD.KAISYA_CD         = ?                            \n");
		sb.append("   AND OHD.TATESN_CD         = ?                            \n");
		sb.append("   AND F_GET_RIYOU_JOTAI_FLG                                \n");
		sb.append("      ( ?, OHD.NOUHIN_KAISI_DT, OHD.NOUHIN_SYURYO_DT, OHD.RIYOU_TEISI_DT ) IN ( ?, ? ) \n");

		int iCc = db.prepare(true, sb.toString());
		int ii = 0;

		db.setString(iCc, ++ii, PbsUtil.getToday(""));				// 業務日付
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_KARI_TOROKU);		// 仮登録
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_RIYO_KA);			// 利用可能
		db.setString(iCc, ++ii, AVAILABLE_KB_RIYO_KA);
		db.setString(iCc, ++ii, cus.getKaisyaCd());
		db.setString(iCc, ++ii, tatesen_cd);
		db.setString(iCc, ++ii, PbsUtil.getToday(""));				// 業務日付
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_KARI_TOROKU);		// 仮登録
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_RIYO_KA);			// 利用可能

		/**=================================================================
		 *  SQL実行
		 *==================================================================**/
		db.execute(iCc);

		PbsRecord[] recs = null;
		try {
			recs = db.getPbsRecords(iCc,false);

		} catch (MaxRowsException e) {

		} catch (DataNotFoundException e) {

		}

		return recs;

	}


	/**
	 * 卸店ﾏｽﾀｰより名称取得
	 * */
	public PbsRecord[] m_orositenNm_get(String orositen_cd) {

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/
		PbsDatabase db = getDatabase(); //DBコネクション

		StringBuilder sb = new StringBuilder();

		sb.append(" \n");
		sb.append("SELECT                                                      \n");
		sb.append("      TEN_NM1_URIDEN                                        \n");		//店名_売上伝票表記用_(1/2)
		sb.append("  FROM                                                      \n");
		sb.append("       M_OROSITEN                                           \n");
		sb.append(" WHERE                                                      \n");
		sb.append("       RIYOU_KBN             = ?                            \n");
		sb.append("   AND KAISYA_CD             = ?                            \n");
		sb.append("   AND OROSITEN_CD           = ?                            \n");
		sb.append("   AND F_GET_RIYOU_JOTAI_FLG                                \n");
		sb.append("      ( ?, NOUHIN_KAISI_DT, NOUHIN_SYURYO_DT, RIYOU_TEISI_DT ) IN ( ?, ? ) \n");

		int iCc = db.prepare(true, sb.toString());
		int ii = 0;

		db.setString(iCc, ++ii, AVAILABLE_KB_RIYO_KA);
		db.setString(iCc, ++ii, cus.getKaisyaCd());
		db.setString(iCc, ++ii, orositen_cd);
		db.setString(iCc, ++ii, PbsUtil.getToday(""));				// 業務日付
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_KARI_TOROKU);		// 仮登録
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_RIYO_KA);			// 利用可能

		/**=================================================================
		 *  SQL実行
		 *==================================================================**/
		db.execute(iCc);

		PbsRecord[] orositen_nm = null;
		try {
			orositen_nm = db.getPbsRecords(iCc, false);

		} catch (MaxRowsException e) {

		} catch (DataNotFoundException e) {

		}

		return orositen_nm;

	}


	/**
	 * 酒販店ﾏｽﾀｰより名称取得
	 * */
	public PbsRecord[] m_syuhantenNm_get(String syuhanten_cd) {

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/
		PbsDatabase db = getDatabase(); //DBコネクション

		StringBuilder sb = new StringBuilder();

		sb.append(" \n");
		sb.append("SELECT                                                      \n");
		sb.append("      TEN_NM_YAGO                                           \n");		//屋号店名（漢字）
		sb.append("  FROM                                                      \n");
		sb.append("       M_SYUHANTEN                                          \n");
		sb.append(" WHERE                                                      \n");
		sb.append("       RIYOU_KBN             = ?                            \n");
		//sb.append("   AND KAISYA_CD             = ?                            \n");
		sb.append("   AND SYUHANTEN_CD          = ?                            \n");

		int iCc = db.prepare(true, sb.toString());
		int ii = 0;

		db.setString(iCc, ++ii, AVAILABLE_KB_RIYO_KA);
		//db.setString(iCc, ++ii, cus.getKaisyaCd());
		db.setString(iCc, ++ii, syuhanten_cd);

		/**=================================================================
		 *  SQL実行
		 *==================================================================**/
		db.execute(iCc);

		PbsRecord[] syuhanten_nm = null;
		try {
			syuhanten_nm = db.getPbsRecords(iCc, false);

		} catch (MaxRowsException e) {

		} catch (DataNotFoundException e) {

		}

		return syuhanten_nm;

	}


	/**
	 * 特約店ﾏｽﾀｰより請求先名称取得
	 * */
	public PbsRecord[] m_seikyusakiNm_get(String tokuyakuten_cd) {

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/
		PbsDatabase db = getDatabase(); //DBコネクション

		StringBuilder sb = new StringBuilder();

		sb.append(" \n");
		sb.append("SELECT                                                      \n");
		sb.append("       SEIKYUSAKI_NM01                                      \n");		//請求先名称(漢字) 1/2
		sb.append("      ,SEIKYUSAKI_NM02                                      \n");		//請求先名称(漢字) 2/2
		sb.append("      ,SEIKYU_SYORI_KBN                                     \n");		//請求処理区分
		sb.append("  FROM                                                      \n");
		sb.append("       M_TOKUYAKUTEN                                        \n");
		sb.append(" WHERE                                                      \n");
		sb.append("       RIYOU_KBN             = ?                            \n");
		sb.append("   AND KAISYA_CD             = ?                            \n");
		sb.append("   AND TOKUYAKUTEN_CD        = ?                            \n");
		sb.append("   AND F_GET_RIYOU_JOTAI_FLG                                \n");
		sb.append("      ( ?, TORIHIKI_KAISI_DT, TORIHIKI_SYURYO_DT, RIYOU_TEISI_DT ) IN ( ?, ? ) \n");

		int iCc = db.prepare(true, sb.toString());
		int ii = 0;

		db.setString(iCc, ++ii, AVAILABLE_KB_RIYO_KA);
		db.setString(iCc, ++ii, cus.getKaisyaCd());
		db.setString(iCc, ++ii, tokuyakuten_cd);
		db.setString(iCc, ++ii, PbsUtil.getToday(""));				// 業務日付
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_KARI_TOROKU);		// 仮登録
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_RIYO_KA);			// 利用可能

		/**=================================================================
		 *  SQL実行
		 *==================================================================**/
		db.execute(iCc);

		PbsRecord[] seikyusaki_nm = null;
		try {
			seikyusaki_nm = db.getPbsRecords(iCc, false);

		} catch (MaxRowsException e) {

		} catch (DataNotFoundException e) {

		}

		return seikyusaki_nm;

	}


	/**
	 * 製品ﾏｽﾀｰ抽出(ｶﾃｺﾞﾘｰ用)
	 * */
	public PbsRecord[] m_seihin_get( String ktksy_cd, String seihin_cd) {

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/
		PbsDatabase db = getDatabase(); //DBコネクション

		StringBuilder sb = new StringBuilder();

		sb.append(" \n");
		sb.append("SELECT                                               \n");
		sb.append("       CASE WHEN M_SEI.TNPN_VOL IS NULL              \n");
		sb.append("            THEN 0                                   \n");
		sb.append("            ELSE TNPN_VOL END AS TNPN_VOL            \n");		//単品総容量（L)@1ﾊﾞﾗ当り
		sb.append("  FROM                                               \n");
		sb.append("       M_SEIHIN                M_SEI                 \n");		//製品ﾏｽﾀｰ
		sb.append("      ,M_SEIHIN_GRP            M_GRP                 \n");		//製品構成ﾏｽﾀｰ
		sb.append(" WHERE                                               \n");
		sb.append("       M_SEI.RIYOU_KBN         = ?                   \n");
		sb.append("   AND M_SEI.KAISYA_CD         = ?                   \n");
		sb.append("   AND M_SEI.KTKSY_CD          = ?                   \n");
		sb.append("   AND M_SEI.SEIHIN_CD         = ?                   \n");
		sb.append("   AND M_GRP.RIYOU_KBN         = M_SEI.RIYOU_KBN     \n");
		sb.append("   AND M_GRP.KAISYA_CD         = M_SEI.KAISYA_CD     \n");
		sb.append("   AND M_GRP.KTKSY_CD          = M_SEI.KTKSY_CD      \n");
		sb.append("   AND M_GRP.KOSEI_SEIHIN_CD   = M_SEI.SEIHIN_CD     \n");
		sb.append("   AND F_GET_RIYOU_JOTAI_FLG                         \n");
		sb.append("      ( ?, M_SEI.SEISAN_KAISI_DT, M_SEI.SEISAN_SYURYO_DT, M_SEI.RIYOU_TEISI_DT ) IN ( ?, ? ) \n");

		int iCc = db.prepare(true, sb.toString());
		int ii = 0;

		db.setString(iCc, ++ii, AVAILABLE_KB_RIYO_KA);
		db.setString(iCc, ++ii, cus.getKaisyaCd());
		db.setString(iCc, ++ii, ktksy_cd);
		db.setString(iCc, ++ii, seihin_cd);
		db.setString(iCc, ++ii, PbsUtil.getToday(""));				// 業務日付
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_KARI_TOROKU);		// 仮登録
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_RIYO_KA);			// 利用可能

		/**=================================================================
		 *  SQL実行
		 *==================================================================**/
		db.execute(iCc);

		PbsRecord[] recs = null;
		try {
			recs = db.getPbsRecords(iCc,false);

		} catch (MaxRowsException e) {

		} catch (DataNotFoundException e) {

		}

		return recs;

	}


	/**
	 * 摘要区分ﾏｽﾀｰ抽出
	 * */
	public String tekiyo_get( String tekiyo_kbn) {

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/
		PbsDatabase db = getDatabase(); //DBコネクション

		StringBuilder sb = new StringBuilder();

		sb.append(" \n");
		sb.append("SELECT                                         \n");
		sb.append("       TEKIYO_NM                               \n");			//摘要内容（漢字）
		sb.append("  FROM                                         \n");
		sb.append("       M_KBN_TEKIYO                            \n");
		sb.append(" WHERE                                         \n");
		sb.append("       RIYOU_KBN         = ?                   \n");
		sb.append("   AND TEKIYO_KBN        = ?                   \n");

		int iCc = db.prepare(true, sb.toString());
		int ii = 0;

		db.setString(iCc, ++ii, AVAILABLE_KB_RIYO_KA);
		db.setString(iCc, ++ii, tekiyo_kbn);

		/**=================================================================
		 *  SQL実行
		 *==================================================================**/
		db.execute(iCc);
		String tekiyo_nm = null;
		PbsRecord[] recs = null;

		try {
			if(PbsUtil.isEmpty(tekiyo_kbn)) {
				return tekiyo_nm;
			}
			recs = db.getPbsRecords(iCc, false);
			tekiyo_nm = recs[0].getString("TEKIYO_NM");

		} catch (MaxRowsException e) {

		} catch (DataNotFoundException e) {

		}

		return tekiyo_nm;

	}


	/**
	 *
	 * 出荷ﾃﾞｰﾀ_ﾍｯﾀﾞｰ部TBL登録
	 * @param recs
	 * @throws UpdateRowException
	 * @throws DeadLockException
	 * @throws SQLException
	 *
	 */
	private void SqlInsertHd(PbsRecord[] recs, String uriden_no) throws SQLException {

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/

		PbsDatabase db = getDatabase(); //DBコネクション

		StringBuilder sb = new StringBuilder();

		sb.append(" \n");
		sb.append("INSERT INTO T_SYUKA_HD               \n");
		sb.append("      (                              \n");
		sb.append("       RIYOU_KBN                     \n");        //利用区分
		sb.append("      ,KAISYA_CD                     \n");        //会社CD
		sb.append("      ,URIDEN_NO                     \n");        //売上伝票NO
		sb.append("      ,SYUKA_KBN                     \n");        //出荷先区分
		sb.append("      ,SYUBETU_CD                    \n");        //ﾃﾞｰﾀ種別CD
		sb.append("      ,KURA_CD                       \n");        //蔵CD
		sb.append("      ,UNCHIN_KBN                    \n");        //運賃区分
		sb.append("      ,UNSOTEN_CD                    \n");        //運送店CD
		sb.append("      ,SYUKA_DT                      \n");        //出荷日(売上伝票発行日)
		sb.append("      ,MINASI_DT                     \n");        //ﾐﾅｼ日付
		sb.append("      ,TEISEI_SYUKA_DT               \n");        //訂正時訂正元出荷日
		sb.append("      ,TEISEI_URIDEN_NO              \n");        //訂正時訂正元売上伝票NO
		sb.append("      ,CHACUNI_YOTEI_DT              \n");        //着荷予定日
		sb.append("      ,NIUKE_TIME_KBN                \n");        //荷受時間区分
		sb.append("      ,NIUKE_BIGIN_TIME              \n");        //荷受時間_開始
		sb.append("      ,NIUKE_END_TIME                \n");        //荷受時間_終了
		sb.append("      ,SENPO_HACYU_NO                \n");        //先方発注NO
		sb.append("      ,JYUCYU_NO                     \n");        //受注NO
		sb.append("      ,TATESN_CD                     \n");        //縦線CD
		sb.append("      ,TANTOSYA_CD                   \n");        //担当者CD
		sb.append("      ,TOKUYAKUTEN_CD                \n");        //特約店CD
		sb.append("      ,DEPO_CD                       \n");        //ﾃﾞﾎﾟCD
		sb.append("      ,NIJITEN_CD                    \n");        //二次店CD
		sb.append("      ,SANJITEN_CD                   \n");        //三次店CD
		sb.append("      ,SYUHANTEN_CD                  \n");        //酒販店（統一）CD
		sb.append("      ,TOKUYAKUTEN_NM_RYAKU          \n");        //特約店名(略)
		sb.append("      ,DEPO_NM                       \n");        //ﾃﾞﾎﾟ名
		sb.append("      ,NIJITEN_NM                    \n");        //二次店名
		sb.append("      ,SANJITEN_NM                   \n");        //三次店名
		sb.append("      ,OROSITEN_NM                   \n");        //最終卸店名
		sb.append("      ,SYUHANTEN_NM                  \n");        //酒販店名
		sb.append("      ,DELIVERY_KBN                  \n");        //倉入・直送区分
		sb.append("      ,SEIKYUSAKI_NM01               \n");        //請求先名称(正) 1/2
		sb.append("      ,SEIKYUSAKI_NM02               \n");        //請求先名称(正) 2/2
		sb.append("      ,SYUKA_SAKI_COUNTRY_CD         \n");        //出荷先国CD
		sb.append("      ,JIS_CD                        \n");        //JISCD
		sb.append("      ,UNCHIN_CNV_TANKA              \n");        //引取運賃換算単価
		sb.append("      ,TEKIYO_KBN1                   \n");        //摘要区分 (01)
		sb.append("      ,TEKIYO_NM1                    \n");        //摘要内容 (01)
		sb.append("      ,TEKIYO_KBN2                   \n");        //摘要区分 (02)
		sb.append("      ,TEKIYO_NM2                    \n");        //摘要内容 (02)
		sb.append("      ,TEKIYO_KBN3                   \n");        //摘要区分 (03)
		sb.append("      ,TEKIYO_NM3                    \n");        //摘要内容 (03)
		sb.append("      ,TEKIYO_KBN4                   \n");        //摘要区分 (04)
		sb.append("      ,TEKIYO_NM4                    \n");        //摘要内容 (04)
		sb.append("      ,TEKIYO_KBN5                   \n");        //摘要区分 (05)
		sb.append("      ,TEKIYO_NM5                    \n");        //摘要内容 (05)
		sb.append("      ,ATUKAI_KBN                    \n");        //伝票打分け区分
		sb.append("      ,BILL_SPLITOUT_KBN             \n");        //扱い区分
		sb.append("      ,SYUKA_SURYO_BOX               \n");        //出荷数量計_箱数
		sb.append("      ,SYUKA_SURYO_SET               \n");        //出荷数量計_ｾｯﾄ数
		sb.append("      ,SYUKA_KINGAKU_TOT             \n");        //出荷金額計
		sb.append("      ,JYURYO_TOT                    \n");        //重量計(KG)
		sb.append("      ,SYUKA_YOURYO_TOT              \n");        //容量計（L）_出荷容量計
		sb.append("      ,REBATE1_YOURYO_TOT            \n");        //容量計（L）_ﾘﾍﾞｰﾄⅠ類対象容量計
		sb.append("      ,REBATE2_YOURYO_TOT            \n");        //容量計（L）_ﾘﾍﾞｰﾄⅡ類対象容量計
		sb.append("      ,REBATE3_YOURYO_TOT            \n");        //容量計（L）_ﾘﾍﾞｰﾄⅢ類対象容量計
		sb.append("      ,REBATE4_YOURYO_TOT            \n");        //容量計（L）_ﾘﾍﾞｰﾄⅣ類対象容量計
		sb.append("      ,REBATE5_YOURYO_TOT            \n");        //容量計（L）_ﾘﾍﾞｰﾄⅤ類対象容量計
		sb.append("      ,REBATEO_YOURYO_TOT            \n");        //容量計（L）_ﾘﾍﾞｰﾄ対象外容量計
		sb.append("      ,URIDEN_HAKKO_BI               \n");        //売上伝票発行_発行日
		sb.append("      ,URIDEN_HAKKO_TIME             \n");        //売上伝票発行_発行時刻
		sb.append("      ,URIDEN_HAKKO_ID               \n");        //売上伝票発行_発行者ID
		sb.append("      ,FURIKAE_GRP_NO                \n");        //振替ｸﾞﾙｰﾌﾟNO
		sb.append("      ,TUMIDEN_NO                    \n");        //積荷伝票NO
		sb.append("      ,TUMIDEN_SM_KBN                \n");        //積荷累積対象区分
		sb.append("      ,SMGP_TOKUYAKUTEN_CD           \n");        //請求明細グループCD_特約店CD
		sb.append("      ,SMGP_DEPO_CD                  \n");        //請求明細グループCD_デポCD
		sb.append("      ,SMGP_NIJITEN_CD               \n");        //請求明細グループCD_二次店CD
		sb.append("      ,SMGP_SANJITEN_CD              \n");        //請求明細グループCD_三次店CD
		sb.append("      ,SMGP_SYUHANTEN_CD             \n");        //請求明細グループCD_酒販店（統一）CD
		sb.append("      ,SEIKYU_SYORI_KBN              \n");        //請求処理区分
		sb.append("      ,SEIKYU_KAGAMI_NO              \n");        //請求鑑NO
		sb.append("      ,SEIKYU_SOSAI_KBN              \n");        //請求相殺区分
		sb.append("      ,SEIKYU_SIME_BI                \n");        //請求〆処理日
		sb.append("      ,SEIKYU_SIME_TIME              \n");        //請求〆処理時刻
		sb.append("      ,SEIKYU_SIME_ID                \n");        //請求〆処理_更新者ID
		sb.append("      ,SEIKYU_MEISAI_NO              \n");        //請求明細No
		sb.append("      )                              \n");
		sb.append(" VALUES    \n");
		sb.append("      (    \n");
		sb.append("       ?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
	    sb.append("      )    \n");

	    for(int l=0; l<recs.length; l++) {

			int ii=0;
		    int iCc = db.prepare(true, sb.toString());

		    db.setString(iCc, ++ii, recs[l].getString("RIYOU_KBN"));			    //利用区分
			db.setString(iCc, ++ii, recs[l].getString("KAISYA_CD"));               	//会社CD
			db.setString(iCc, ++ii, uriden_no);							        	//売上伝票NO
			db.setString(iCc, ++ii, recs[l].getString("SYUKA_KBN"));               	//出荷先区分
			db.setString(iCc, ++ii, recs[l].getString("SYUBETU_CD"));              	//ﾃﾞｰﾀ種別CD
			db.setString(iCc, ++ii, recs[l].getString("KURA_CD"));  			    //蔵CD
			db.setString(iCc, ++ii, recs[l].getString("UNCHIN_KBN"));              	//運賃区分
			db.setString(iCc, ++ii, recs[l].getString("UNSOTEN_CD"));              	//運送店CD
			db.setString(iCc, ++ii, recs[l].getString("SYUKA_DT"));                	//出荷日(売上伝票発行予定日)
			db.setString(iCc, ++ii, recs[l].getString("MINASI_DT"));               	//ﾐﾅｼ日付
			db.setString(iCc, ++ii, recs[l].getString("TEISEI_SYUKA_DT"));          //訂正時訂正元出荷日
			db.setString(iCc, ++ii, recs[l].getString("TEISEI_URIDEN_NO"));         //訂正時訂正元売上伝票NO
			db.setString(iCc, ++ii, recs[l].getString("CHACUNI_YOTEI_DT"));        	//着荷予定日
			db.setString(iCc, ++ii, recs[l].getString("NIUKE_TIME_KBN"));          	//荷受時間区分
			db.setString(iCc, ++ii, recs[l].getString("NIUKE_BIGIN_TIME"));        	//荷受時間_開始
			db.setString(iCc, ++ii, recs[l].getString("NIUKE_END_TIME"));          	//荷受時間_終了
			db.setString(iCc, ++ii, recs[l].getString("SENPO_HACYU_NO"));          	//先方発注NO
			db.setString(iCc, ++ii, recs[l].getString("JYUCYU_NO"));           		//受注NO
			db.setString(iCc, ++ii, recs[l].getString("TATESN_CD"));               	//縦線CD
			db.setString(iCc, ++ii, recs[l].getString("TANTOSYA_CD"));             	//担当者CD
			db.setString(iCc, ++ii, recs[l].getString("TOKUYAKUTEN_CD"));          	//特約店CD
			db.setString(iCc, ++ii, recs[l].getString("DEPO_CD"));				    //ﾃﾞﾎﾟCD
			db.setString(iCc, ++ii, recs[l].getString("NIJITEN_CD"));              	//二次店CD
			db.setString(iCc, ++ii, recs[l].getString("SANJITEN_CD"));             	//三次店CD
			db.setString(iCc, ++ii, recs[l].getString("SYUHANTEN_CD"));            	//酒販店（統一）CD
			db.setNString(iCc, ++ii, recs[l].getString("TOKUYAKUTEN_NM_RYAKU"));     //特約店名(略)
			db.setNString(iCc, ++ii, recs[l].getString("DEPO_NM"));            		//ﾃﾞﾎﾟ名
			db.setNString(iCc, ++ii, recs[l].getString("NIJITEN_NM"));            	//二次店名
			db.setNString(iCc, ++ii, recs[l].getString("SANJITEN_NM"));            	//三次店名
			db.setNString(iCc, ++ii, recs[l].getString("OROSITEN_NM"));            	//最終卸店名
			db.setNString(iCc, ++ii, recs[l].getString("SYUHANTEN_NM"));            	//酒販店名
			db.setString(iCc, ++ii, recs[l].getString("DELIVERY_KBN"));   			//倉入・直送区分
			db.setNString(iCc, ++ii, recs[l].getString("SEIKYUSAKI_NM01"));   		//請求先名称(正) 1/2
			db.setNString(iCc, ++ii, recs[l].getString("SEIKYUSAKI_NM02"));   		//請求先名称(正) 2/2
			db.setString(iCc, ++ii, recs[l].getString("SYUKA_SAKI_COUNTRY_CD"));   	//出荷先国CD
			db.setString(iCc, ++ii, recs[l].getString("JIS_CD"));                  	//JISCD
			db.setString(iCc, ++ii, recs[l].getString("UNCHIN_CNV_TANKA"));        	//引取運賃換算単価
			db.setString(iCc, ++ii, recs[l].getString("TEKIYO_KBN1"));             	//摘要区分 (01)
			db.setNString(iCc, ++ii, recs[l].getString("TEKIYO_NM1"));              	//摘要内容 (01)
			db.setString(iCc, ++ii, recs[l].getString("TEKIYO_KBN2"));             	//摘要区分 (02)
			db.setNString(iCc, ++ii, recs[l].getString("TEKIYO_NM2"));              	//摘要内容 (02)
			db.setString(iCc, ++ii, recs[l].getString("TEKIYO_KBN3"));            	//摘要区分 (03)
			db.setNString(iCc, ++ii, recs[l].getString("TEKIYO_NM3"));              	//摘要内容 (03)
			db.setString(iCc, ++ii, recs[l].getString("TEKIYO_KBN4"));             	//摘要区分 (04)
			db.setNString(iCc, ++ii, recs[l].getString("TEKIYO_NM4"));              	//摘要内容 (04)
			db.setString(iCc, ++ii, recs[l].getString("TEKIYO_KBN5"));             	//摘要区分 (05)
			db.setNString(iCc, ++ii, recs[l].getString("TEKIYO_NM5"));              	//摘要内容 (05)
			db.setString(iCc, ++ii, recs[l].getString("ATUKAI_KBN"));		       	//扱い区分
			db.setString(iCc, ++ii, recs[l].getString("BILL_SPLITOUT_KBN"));        //伝票打分け区分
			db.setString(iCc, ++ii, recs[l].getString("SYUKA_SURYO_BOX"));			//出荷数量計_箱数
			db.setString(iCc, ++ii, recs[l].getString("SYUKA_SURYO_SET"));	      	//出荷数量計_ｾｯﾄ数
			db.setString(iCc, ++ii, recs[l].getString("SYUKA_KINGAKU_TOT"));      	//出荷金額計
			db.setString(iCc, ++ii, recs[l].getString("JYURYO_TOT"));           	//重量計(KG)
			db.setString(iCc, ++ii, recs[l].getString("SYUKA_YOURYO_TOT"));      	//出荷容量計
			db.setString(iCc, ++ii, recs[l].getString("REBATE1_YOURYO_TOT"));    	//容量計（L）_ﾘﾍﾞｰﾄⅠ類対象容量計
			db.setString(iCc, ++ii, recs[l].getString("REBATE2_YOURYO_TOT"));      	//容量計（L）_ﾘﾍﾞｰﾄⅡ類対象容量計
			db.setString(iCc, ++ii, recs[l].getString("REBATE3_YOURYO_TOT"));		//容量計（L）_ﾘﾍﾞｰﾄⅢ類対象容量計
			db.setString(iCc, ++ii, recs[l].getString("REBATE4_YOURYO_TOT"));      	//容量計（L）_ﾘﾍﾞｰﾄⅣ類対象容量計
			db.setString(iCc, ++ii, recs[l].getString("REBATE5_YOURYO_TOT"));     	//容量計（L）_ﾘﾍﾞｰﾄⅤ類対象容量計
			db.setString(iCc, ++ii, recs[l].getString("REBATEO_YOURYO_TOT"));      	//容量計（L）_ﾘﾍﾞｰﾄ対象外容量計
			db.setString(iCc, ++ii, recs[l].getString("URIDEN_HAKKO_BI"));      	//売上伝票発行_発行日
			db.setString(iCc, ++ii, recs[l].getString("URIDEN_HAKKO_TIME"));      	//売上伝票発行_発行時刻
			db.setString(iCc, ++ii, recs[l].getString("URIDEN_HAKKO_ID"));      	//売上伝票発行_発行者ID
			db.setString(iCc, ++ii, recs[l].getString("FURIKAE_GRP_NO"));      		//振替ｸﾞﾙｰﾌﾟNO
			db.setString(iCc, ++ii, recs[l].getString("TUMIDEN_NO"));        		//積荷伝票NO
			db.setString(iCc, ++ii, recs[l].getString("TUMIDEN_SM_KBN"));         	//積荷累積対象区分
			db.setString(iCc, ++ii, recs[l].getString("SMGP_TOKUYAKUTEN_CD"));      //請求明細グループCD_特約店CD
			db.setString(iCc, ++ii, recs[l].getString("SMGP_DEPO_CD"));         	//請求明細グループCD_デポCD
			db.setString(iCc, ++ii, recs[l].getString("SMGP_NIJITEN_CD"));        	//請求明細グループCD_二次店CD
			db.setString(iCc, ++ii, recs[l].getString("SMGP_SANJITEN_CD"));         //請求明細グループCD_三次店CD
			db.setString(iCc, ++ii, recs[l].getString("SMGP_SYUHANTEN_CD"));        //請求明細グループCD_酒販店（統一）CD
			db.setString(iCc, ++ii, recs[l].getString("SEIKYU_SYORI_KBN"));      	//請求処理区分
			db.setString(iCc, ++ii, recs[l].getString("SEIKYU_KAGAMI_NO"));      	//請求鑑NO
			db.setString(iCc, ++ii, recs[l].getString("SEIKYU_SOSAI_KBN"));      	//請求相殺区分
			db.setString(iCc, ++ii, recs[l].getString("SEIKYU_SIME_BI"));      		//請求〆処理日
			db.setString(iCc, ++ii, recs[l].getString("SEIKYU_SIME_TIME"));      	//請求〆処理時刻
			db.setString(iCc, ++ii, recs[l].getString("SEIKYU_SIME_ID"));      		//請求〆処理_更新者ID
			db.setString(iCc, ++ii, recs[l].getString("SEIKYU_MEISAI_NO"));      	//請求明細No

			try {

				/**=================================================================
				 *  SQL実行
				 *==================================================================**/
				//更新件数が1件でない場合はエラー
				db.executeUpdate(iCc,false);

			} catch (UpdateRowException e) {
				category__.error(e.getMessage(), e);
				db.rollback();
				throw e;
			} catch (DeadLockException e) {
				category__.error(e.getMessage(), e);
				db.rollback();
				throw e;
			} catch (UniqueKeyViolatedException e) {
				category__.error(e.getMessage(), e);
				db.rollback();
				throw e;
			} catch (SQLException e) {
				category__.error(e.getMessage(), e);
				db.rollback();
				throw e;
			}
	    }

	}

	/**
	 *
	 * 出荷ﾃﾞｰﾀ_ﾃﾞｨﾃｨｰﾙ部TBL登録
	 * @param recs
	 * @throws SQLException
	 *
	 */
	private void SqlInsertDt(PbsRecord[] recs, String uriden_no) throws SQLException {

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/

		PbsDatabase db = getDatabase(); //DBコネクション

		StringBuilder sb = new StringBuilder();

		sb.append(" \n");
		sb.append("INSERT INTO T_SYUKA_DT               \n");
		sb.append("      (                              \n");
		sb.append("       RIYOU_KBN                     \n");        //利用区分
		sb.append("      ,KAISYA_CD                     \n");        //会社CD
		sb.append("      ,URIDEN_NO                     \n");        //売上伝票NO
		sb.append("      ,URIDEN_LINE_NO                \n");        //売上伝票行NO
		sb.append("      ,KURA_CD                       \n");        //蔵CD
		sb.append("      ,SOUKO_CD                      \n");        //倉庫CD
		sb.append("      ,KTKSY_CD                      \n");        //寄託者CD
		sb.append("      ,SHOHIN_CD                     \n");        //商品CD
		sb.append("      ,SEIHIN_CD                     \n");        //製品CD
		sb.append("      ,SIIRESAKI_CD                  \n");        //仕入先CD
		sb.append("      ,SIIREHIN_CD                   \n");        //仕入品CD
		sb.append("      ,SEIHIN_DT                     \n");        //製品日付
		sb.append("      ,SHNNM_REPORT1                 \n");        //商品名称_自社各帳票用(1)
		sb.append("      ,YOUKI_NM_REPORT               \n");        //容器名称_自社各帳票用
		sb.append("      ,CASE_IRISU                    \n");        //ｹｰｽ入数
		sb.append("      ,BARA_YOURYO                   \n");        //ﾊﾞﾗ容量(L)
		sb.append("      ,MIIRI_WEIGHT_CASE             \n");        //身入重量(KG)_ｹｰｽ
		sb.append("      ,MIIRI_WEIGHT_BARA             \n");        //身入重量(KG)_ﾊﾞﾗ
		sb.append("      ,UNCHIN_KAKERITU               \n");        //運賃掛率(%)
		sb.append("      ,HANBAI_TANKA                  \n");        //販売単価
		sb.append("      ,HOSOKURAN                     \n");        //補足欄
		sb.append("      ,SYUKA_SU_CASE                 \n");        //出荷数量_箱数
		sb.append("      ,SYUKA_SU_BARA                 \n");        //出荷数量_ｾｯﾄ数
		sb.append("      ,SYUKA_ALL_BARA                \n");        //出荷数量_換算総ｾｯﾄ数
		sb.append("      ,SYUKA_ALL_WEIGTH              \n");        //出荷重量（KG)
		sb.append("      ,SYUKA_HANBAI_KINGAKU          \n");        //販売額
		sb.append("      ,SYUKA_TAIO_KBN                \n");        //出荷対応区分
		sb.append("      ,ATUKAI_KBN                    \n");        //扱い区分
		sb.append("      ,BUPIN_KBN                     \n");        //物品区分
		sb.append("      ,HTANKA_CHG_FLG                \n");        //販売単価変更ﾌﾗｸﾞ
		sb.append("      ,SYUKA_SOUYOURYO               \n");        //容量計（L）_出荷総容量
		sb.append("      ,REBATE1_SOUYOURYO             \n");        //容量計（L）_ﾘﾍﾞｰﾄⅠ類対象総容量
		sb.append("      ,REBATE2_SOUYOURYO             \n");        //容量計（L）_ﾘﾍﾞｰﾄⅡ類対象総容量
		sb.append("      ,REBATE3_SOUYOURYO             \n");        //容量計（L）_ﾘﾍﾞｰﾄⅢ類対象総容量
		sb.append("      ,REBATE4_SOUYOURYO             \n");        //容量計（L）_ﾘﾍﾞｰﾄⅣ類対象総容量
		sb.append("      ,REBATE5_SOUYOURYO             \n");        //容量計（L）_ﾘﾍﾞｰﾄⅤ類対象総容量
		sb.append("      ,REBATEO_SOUYOURYO             \n");        //容量計（L）_ﾘﾍﾞｰﾄ対象外総容量
		sb.append("      ,PB_TOKUCYU_KBN                \n");        //特注指示区分
		sb.append("      ,PB_TOKUCYU                    \n");        //PB OR 特注指示内容
		sb.append("      ,HANBAI_BUMON_CD               \n");        //販売部門CD
		sb.append("      ,HANBAI_BUMON_RNM              \n");        //販売部門名（略式）
		sb.append("      ,HANBAI_SYUBETU_CD             \n");        //販売種別CD
		sb.append("      ,HANBAI_SYUBETU_RNM            \n");        //販売種別名（略式）
		sb.append("      ,HANBAI_BUNRUI_CD              \n");        //販売分類CD
		sb.append("      ,HANBAI_BUNRUI_RNM             \n");        //販売分類名（略式）
		sb.append("      ,EDI_HAISOUM_SEND_KB           \n");        //EDI配送依頼(詳細)送信区分
		sb.append("      ,EDI_SYUKKA_SEND_KB            \n");        //EDI出荷案内送信区分
		sb.append("      )                              \n");
		sb.append(" VALUES    \n");
		sb.append("      (    \n");
		sb.append("       ?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
	    sb.append("      )    \n");

	    for(int l=0; l<recs.length; l++) {

	    	int ii=0;
	    	int iCc = db.prepare(true, sb.toString());

			db.setString(iCc, ++ii, recs[l].getString("RIYOU_KBN"));					//利用区分
			db.setString(iCc, ++ii, recs[l].getString("KAISYA_CD"));                    //会社CD
			db.setString(iCc, ++ii, uriden_no);						                    //売上伝票NO
			db.setString(iCc, ++ii, recs[l].getString("URIDEN_LINE_NO"));				//売上伝票行NO
			db.setString(iCc, ++ii, recs[l].getString("KURA_CD"));				    	//蔵CD
			db.setString(iCc, ++ii, recs[l].getString("SOUKO_CD"));                     //倉庫CD
			db.setString(iCc, ++ii, recs[l].getString("KTKSY_CD"));                     //寄託者CD
			db.setString(iCc, ++ii, recs[l].getString("SHOHIN_CD"));              		//商品CD
			db.setString(iCc, ++ii, recs[l].getString("SEIHIN_CD"));        			//製品CD
			db.setString(iCc, ++ii, recs[l].getString("SIIRESAKI_CD"));                 //仕入先CD
			db.setString(iCc, ++ii, recs[l].getString("SIIREHIN_CD"));                  //仕入品CD
			db.setString(iCc, ++ii, recs[l].getString("SEIHIN_DT"));                    //製品日付
			db.setNString(iCc, ++ii, recs[l].getString("SHNNM_REPORT1"));				//商品名称_自社各帳票用(1)
			db.setString(iCc, ++ii, recs[l].getString("YOUKI_NM_REPORT"));              //容器名称_自社各帳票用
			db.setString(iCc, ++ii, recs[l].getString("CASE_IRISU"));                   //ｹｰｽ入数
			db.setString(iCc, ++ii, recs[l].getString("BARA_YOURYO"));                  //ﾊﾞﾗ容量(L)
			db.setString(iCc, ++ii, recs[l].getString("MIIRI_WEIGHT_CASE"));            //身入り重量(KG)_ｹｰｽ
			db.setString(iCc, ++ii, recs[l].getString("MIIRI_WEIGHT_BARA"));            //身入り重量(KG)_ﾊﾞﾗ
			db.setString(iCc, ++ii, recs[l].getString("UNCHIN_KAKERITU"));              //運賃掛率(%)
			db.setString(iCc, ++ii, recs[l].getString("HANBAI_TANKA"));                 //販売単価
			db.setNString(iCc, ++ii, recs[l].getString("HOSOKURAN"));                    //補足欄
			db.setString(iCc, ++ii, recs[l].getString("SYUKA_SU_CASE"));                //出荷数量_箱数
			db.setString(iCc, ++ii, recs[l].getString("SYUKA_SU_BARA"));                //出荷数量_ｾｯﾄ数
			db.setString(iCc, ++ii, recs[l].getString("SYUKA_ALL_BARA"));               //出荷数量_換算総ｾｯﾄ数
			db.setString(iCc, ++ii, recs[l].getString("SYUKA_ALL_WEIGTH"));             //出荷重量（KG)
			db.setString(iCc, ++ii, recs[l].getString("SYUKA_HANBAI_KINGAKU"));         //販売額
			db.setString(iCc, ++ii, recs[l].getString("SYUKA_TAIO_KBN"));               //出荷対応区分
			db.setString(iCc, ++ii, recs[l].getString("ATUKAI_KBN"));                   //扱い区分
			db.setString(iCc, ++ii, recs[l].getString("BUPIN_KBN"));                    //物品区分
			db.setString(iCc, ++ii, recs[l].getString("HTANKA_CHG_FLG"));               //販売単価変更ﾌﾗｸﾞ
			db.setString(iCc, ++ii, recs[l].getString("SYUKA_SOUYOURYO"));              //容量計（L）_出荷総容量
			db.setString(iCc, ++ii, recs[l].getString("REBATE1_SOUYOURYO"));            //容量計（L）_ﾘﾍﾞｰﾄⅠ類対象総容量
			db.setString(iCc, ++ii, recs[l].getString("REBATE2_SOUYOURYO"));            //容量計（L）_ﾘﾍﾞｰﾄⅡ類対象総容量
			db.setString(iCc, ++ii, recs[l].getString("REBATE3_SOUYOURYO"));            //容量計（L）_ﾘﾍﾞｰﾄⅢ類対象総容量
			db.setString(iCc, ++ii, recs[l].getString("REBATE4_SOUYOURYO"));            //容量計（L）_ﾘﾍﾞｰﾄⅣ類対象総容量
			db.setString(iCc, ++ii, recs[l].getString("REBATE5_SOUYOURYO"));            //容量計（L）_ﾘﾍﾞｰﾄⅤ類対象総容量
			db.setString(iCc, ++ii, recs[l].getString("REBATEO_SOUYOURYO"));            //容量計（L）_ﾘﾍﾞｰﾄ対象外総容量
			db.setString(iCc, ++ii, recs[l].getString("PB_TOKUCYU_KBN"));               //特注指示区分
			db.setNString(iCc, ++ii, recs[l].getString("PB_TOKUCYU"));                   //PB OR 特注指示内容
			db.setString(iCc, ++ii, recs[l].getString("HANBAI_BUMON_CD"));              //販売部門CD
			db.setNString(iCc, ++ii, recs[l].getString("HANBAI_BUMON_RNM"));             //販売部門名（略式）
			db.setString(iCc, ++ii, recs[l].getString("HANBAI_SYUBETU_CD"));            //販売種別CD
			db.setNString(iCc, ++ii, recs[l].getString("HANBAI_SYUBETU_RNM"));           //販売種別名（略式）
			db.setString(iCc, ++ii, recs[l].getString("HANBAI_BUNRUI_CD"));             //販売分類CD
			db.setNString(iCc, ++ii, recs[l].getString("HANBAI_BUNRUI_RNM"));            //販売分類名（略式）
			db.setString(iCc, ++ii, recs[l].getString("EDI_HAISOUM_SEND_KB"));        	//EDI配送依頼(詳細)送信区分
			db.setString(iCc, ++ii, recs[l].getString("EDI_SYUKKA_SEND_KB"));			//EDI出荷案内送信区分

			try {

				/**=================================================================
				 *  SQL実行
				 *==================================================================**/
				//更新件数が1件でない場合はエラー
				db.executeUpdate(iCc,false);

			} catch (UpdateRowException e) {
				category__.error(e.getMessage(), e);
				db.rollback();
				throw e;
			} catch (DeadLockException e) {
				category__.error(e.getMessage(), e);
				db.rollback();
				throw e;
			} catch (UniqueKeyViolatedException e) {
				category__.error(e.getMessage(), e);
				db.rollback();
				throw e;
			} catch (SQLException e) {
				category__.error(e.getMessage(), e);
				db.rollback();
				throw e;
			}
	    }

	}

	/**
	 *
	 * 予定出荷先別商品ｶﾃｺﾞﾘﾃﾞｰﾀTBL登録
	 * @param recs
	 * @throws SQLException
	 *
	 */
	private void SqlInsertCtg(PbsRecord[] recs, String uriden_no) throws SQLException {

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/

		PbsDatabase db = getDatabase(); //DBコネクション

		StringBuilder sb = new StringBuilder();

		sb.append(" \n");
		sb.append("INSERT INTO T_SYUKA_JSKCTG           \n");
		sb.append("      (                              \n");
		sb.append("       RIYOU_KBN                     \n");        //利用区分
		sb.append("      ,KAISYA_CD                     \n");        //会社CD
		sb.append("      ,URIDEN_NO                     \n");        //売上伝票NO
		sb.append("      ,URI_CTGR_LINE_NO              \n");        //売上ｶﾃｺﾞﾘ行NO
		sb.append("      ,KTKSY_CD                      \n");        //寄託者CD
		sb.append("      ,SEIHIN_CD                     \n");        //製品CD
		sb.append("      ,HANBAI_BUMON_CD               \n");        //販売部門CD
		sb.append("      ,HANBAI_BUMON_RNM              \n");        //販売部門名（略式）
		sb.append("      ,HANBAI_SYUBETU_CD             \n");        //販売種別CD
		sb.append("      ,HANBAI_SYUBETU_RNM            \n");        //販売種別名（略式）
		sb.append("      ,HANBAI_BUNRUI_CD              \n");        //販売分類CD
		sb.append("      ,HANBAI_BUNRUI_RNM             \n");        //販売分類名（略式）
		sb.append("      ,SYUZEI_CD                     \n");        //酒税分類CD
		sb.append("      ,SYUZEI_NM_RYAKU               \n");        //酒税分類名（略式）
		sb.append("      ,TANE_CD                       \n");        //種CD
		sb.append("      ,TANE_NM_RYAKU                 \n");        //種名称（略式）
		sb.append("      ,SOZAI_KBN                     \n");        //素材区分
		sb.append("      ,COLOR_KBN                     \n");        //色区分
		sb.append("      ,HJISEKI_BUNRUI_KBN            \n");        //販売実績分類区分
		sb.append("      ,VOL                           \n");        //容量（L)
		sb.append("      ,TANKA                         \n");        //単価（円）
		sb.append("      ,SYUKA_HON                     \n");        //出荷本数計
		sb.append("      ,SYUKA_VOL                     \n");        //出荷容量計（L)
		sb.append("      ,HANBAI_KINGAKU                \n");        //販売金額計（円）
		sb.append("      )                              \n");
		sb.append(" VALUES    \n");
		sb.append("      (    \n");
		sb.append("       ?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
		sb.append("      ,?   \n");
	    sb.append("      )    \n");

		for(int l=0; l<recs.length; l++){

			int ii=0;
			int iCc = db.prepare(true, sb.toString());

			db.setString(iCc, ++ii, recs[l].getString("RIYOU_KBN"));				//利用区分
			db.setString(iCc, ++ii, recs[l].getString("KAISYA_CD"));                //会社CD
			db.setString(iCc, ++ii, uriden_no);						                //売上伝票NO
			db.setString(iCc, ++ii, recs[l].getString("URI_CTGR_LINE_NO"));         //売上ｶﾃｺﾞﾘ行NO
			db.setString(iCc, ++ii, recs[l].getString("KTKSY_CD"));					//寄託者CD
			db.setString(iCc, ++ii, recs[l].getString("SEIHIN_CD"));                //製品CD
			db.setString(iCc, ++ii, recs[l].getString("HANBAI_BUMON_CD"));          //販売部門CD
			db.setNString(iCc, ++ii, recs[l].getString("HANBAI_BUMON_RNM"));         //販売部門名（略式）
			db.setString(iCc, ++ii, recs[l].getString("HANBAI_SYUBETU_CD"));        //販売種別CD
			db.setNString(iCc, ++ii, recs[l].getString("HANBAI_SYUBETU_RNM"));       //販売種別名（略式）
			db.setString(iCc, ++ii, recs[l].getString("HANBAI_BUNRUI_CD"));         //販売分類CD
			db.setNString(iCc, ++ii, recs[l].getString("HANBAI_BUNRUI_RNM"));        //販売分類名（略式）
			db.setString(iCc, ++ii, recs[l].getString("SYUZEI_CD"));                //酒税分類CD
			db.setNString(iCc, ++ii, recs[l].getString("SYUZEI_NM_RYAKU"));          //酒税分類名（略式）
			db.setString(iCc, ++ii, recs[l].getString("TANE_CD"));                  //種CD
			db.setNString(iCc, ++ii, recs[l].getString("TANE_NM_RYAKU"));            //種名称（略式）
			db.setString(iCc, ++ii, recs[l].getString("SOZAI_KBN"));                //素材区分
			db.setString(iCc, ++ii, recs[l].getString("COLOR_KBN"));                //色区分
			db.setString(iCc, ++ii, recs[l].getString("HJISEKI_BUNRUI_KBN"));       //販売実績分類区分
			db.setString(iCc, ++ii, recs[l].getString("VOL"));               		//容量（L)
			db.setString(iCc, ++ii, recs[l].getString("TANKA"));            		//単価（円）
			db.setString(iCc, ++ii, recs[l].getString("SYUKA_HON"));              	//出荷本数計
			db.setString(iCc, ++ii, recs[l].getString("SYUKA_VOL"));              	//出荷対応区分
			db.setString(iCc, ++ii, recs[l].getString("HANBAI_KINGAKU"));           //販売金額計（円）

			try {

				/**=================================================================
				 *  SQL実行
				 *==================================================================**/
				//更新件数が1件でない場合はエラー
				db.executeUpdate(iCc,false);

			} catch (UpdateRowException e) {
				category__.error(e.getMessage(), e);
				db.rollback();
				throw e;
			} catch (DeadLockException e) {
				category__.error(e.getMessage(), e);
				db.rollback();
				throw e;
			} catch (UniqueKeyViolatedException e) {
				category__.error(e.getMessage(), e);
				db.rollback();
				throw e;
			} catch (SQLException e) {
				category__.error(e.getMessage(), e);
				db.rollback();
				throw e;
			}
		}

	}


	/**
	 *
	 * 出荷Noを対象となった蔵元ﾃﾞｰﾀ_ﾃﾞｨﾃｰﾙにセット
	 * @param
	 * @throws SQLException
	 *
	 */
	public void syukaNo_update(String syuka_no,
								 String orositen_cd_last,
								 String atukai_kbn,
								 String bill_splitout_kbn,
								 String juchu_mng_key,
								 String unchin_kbn,
								 String unsoten_cd,
								 String haso_yotei_dt,
								 String tatesn_cd,
								 String hacyu_no,
								 String syuhanten_cd,
								 String shohin_cd,
								 String hanbai_tanka) throws SQLException {

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/
		PbsDatabase db = getDatabase();
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();

		sb1.append(" \n");
		sb1.append("SELECT                                                  \n");
		sb1.append("       KDT.KURADATA_NO AS KURADATA_NO                   \n");		//蔵直ﾃﾞｰﾀ連番
		sb1.append("      ,KDT.KURADEN_LINE_NO AS KURADEN_LINE_NO           \n");		//商品行NO
		sb1.append("  FROM                                                  \n");
		sb1.append("       T_KURAC_DT KDT                                   \n");
		sb1.append("  LEFT JOIN T_KURAC_HD KHD                              \n");
		sb1.append("    ON(                                                 \n");
		sb1.append("       KDT.RIYOU_KBN     = KHD.RIYOU_KBN                \n");
		sb1.append("   AND KDT.KAISYA_CD     = KHD.KAISYA_CD                \n");
		sb1.append("   AND KDT.KURADATA_NO   = KHD.KURADATA_NO              \n");
		sb1.append("      )                                                 \n");
		sb1.append("  LEFT JOIN (                                           \n");
		sb1.append("       SELECT                                           \n");
		sb1.append("              RIYOU_KBN                                 \n");
		sb1.append("             ,KAISYA_CD                                 \n");
		sb1.append("             ,KTKSY_CD                                  \n");
		sb1.append("             ,SHOHIN_CD                                 \n");
		sb1.append("             ,ATUKAI_KBN                                \n");
		sb1.append("             ,JYUCYU_KAISI_DT                           \n");
		sb1.append("             ,JYUCYU_SYURYO_DT                          \n");
		sb1.append("             ,RIYOU_TEISI_DT                            \n");
		sb1.append("             ,CASE BUPIN_KBN                            \n");
		sb1.append("                 WHEN ? THEN ?                          \n");
		sb1.append("                 WHEN ? THEN ?                          \n");
		sb1.append("                 WHEN ? THEN ?                          \n");
		sb1.append("                 WHEN ? THEN ?                          \n");
		sb1.append("                 WHEN ? THEN ?                          \n");
		sb1.append("                 WHEN ? THEN ?                          \n");
		sb1.append("                 WHEN ? THEN ?                          \n");
		sb1.append("                 WHEN ? THEN ?                          \n");
		sb1.append("                 WHEN ? THEN ? END AS BILL_SPLITOUT_KBN \n");
		sb1.append("         FROM M_SYOHIN                                  \n");
		sb1.append("            ) MSY                                       \n");
		sb1.append("    ON(                                                 \n");
		sb1.append("       MSY.RIYOU_KBN     = KDT.RIYOU_KBN                \n");
		sb1.append("   AND MSY.KAISYA_CD     = KDT.KAISYA_CD                \n");
		sb1.append("   AND MSY.KTKSY_CD      = ?                            \n");
		sb1.append("   AND MSY.SHOHIN_CD     = KDT.SHOHIN_CD                \n");
		sb1.append("   AND F_GET_RIYOU_JOTAI_FLG                            \n");
		sb1.append("      ( ?, MSY.JYUCYU_KAISI_DT, MSY.JYUCYU_SYURYO_DT, MSY.RIYOU_TEISI_DT ) IN ( ?, ? ) \n");
		sb1.append("      )                                                 \n");
		sb1.append("  LEFT JOIN M_OROSISYOSAI_HD OHD                        \n");
		sb1.append("    ON(                                                 \n");
		sb1.append("       KHD.RIYOU_KBN     = OHD.RIYOU_KBN                \n");
		sb1.append("   AND KHD.KAISYA_CD     = OHD.KAISYA_CD                \n");
		sb1.append("   AND KHD.TATESN_CD     = OHD.TATESN_CD                \n");
		sb1.append("   AND F_GET_RIYOU_JOTAI_FLG                            \n");
		sb1.append("      ( ?, OHD.NOUHIN_KAISI_DT, OHD.NOUHIN_SYURYO_DT, OHD.RIYOU_TEISI_DT ) IN ( ?, ? ) \n");
		sb1.append("      )                                                 \n");
		sb1.append("  LEFT JOIN M_KBN_JISEKI_TANTO JTA                      \n");
		sb1.append("    ON(                                                 \n");
		sb1.append("       JTA.RIYOU_KBN     = OHD.RIYOU_KBN                \n");
		sb1.append("   AND JTA.KAISYA_CD     = OHD.KAISYA_CD                \n");
		sb1.append("   AND JTA.TANTO_CD      = OHD.TANTOSYA_CD              \n");
		sb1.append("      )                                                 \n");
		sb1.append(" WHERE                                                  \n");
		sb1.append("       KDT.RIYOU_KBN         = ?                        \n");		// 利用区分
		sb1.append("   AND KDT.KAISYA_CD         = ?                        \n");		// 会社CD
		sb1.append("   AND KHD.SYUBETU_CD       IN( ?, ?)                   \n");		// ﾃﾞｰﾀ種別CD
		sb1.append("   AND KDT.SYUKADEN_NO      IS NULL                     \n");		// 黄桜出荷伝票NO
		sb1.append("   AND KDT.SHOHIN_SET       != 0                        \n");		// 商品申込ｾｯﾄ数
		sb1.append("   AND OHD.OROSITEN_CD_LAST  = ?                        \n");		// 最終送荷先卸CD
		sb1.append("   AND MSY.ATUKAI_KBN        = ?                        \n");		// 扱い区分
		sb1.append("   AND MSY.BILL_SPLITOUT_KBN = ?                        \n");		// 伝票打分け区分
		sb1.append("   AND JTA.JUCHU_MNG_KEY     = ?                        \n");		// 採番用管轄事業所ｷｰ
		sb1.append("   AND KHD.UNCHIN_KBN        = ?                        \n");		// 運賃区分
		sb1.append("   AND KHD.UNSOTEN_CD        = ?                        \n");		// 運送店CD
		sb1.append("   AND KHD.HASO_YOTEI_DT     = ?                        \n");		// 発送予定日
		sb1.append("   AND KHD.TATESN_CD         = ?                        \n");		// 縦線CD
		sb1.append("   AND KDT.SHOHIN_CD         = ?                        \n");		// 商品CD
		sb1.append("   AND KDT.HANBAI_TANKA      = ?                        \n");		// 販売単価

		if(PbsUtil.isEmpty(hacyu_no)) {
			sb1.append("   AND KHD.HACYU_NO         IS NULL                     \n");		// 発注NO
		}else {
			sb1.append("   AND KHD.HACYU_NO          = ?                        \n");		// 発注NO
		}

		if(PbsUtil.isEmpty(syuhanten_cd)) {
			sb1.append("   AND KHD.SYUHANTEN_CD     IS NULL                     \n");		// 酒販店（統一）CD
		}else {
			sb1.append("   AND KHD.SYUHANTEN_CD      = ?                        \n");		// 酒販店（統一）CD
		}

		int iCc = db.prepare(true, sb1.toString());
		int ii = 0;

		db.setString(iCc, ++ii, SYOHIN_KB_SYURUI);				// 物品区分:0:酒類群
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_SYURUI);		// 伝票打分け区分:01:酒類

		db.setString(iCc, ++ii, SYOHIN_KB_SYOKUHIN);				// 物品区分:1:食品（酒類外）
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_HI_SYURUI);	// 伝票打分け区分:02:非酒類

		db.setString(iCc, ++ii, SYOHIN_KB_SYOHIN);				// 物品区分:2:商品（食品外）
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_HI_SYURUI);	// 伝票打分け区分:02:非酒類

		db.setString(iCc, ++ii, SYOHIN_KB_TARU_HOSYOKIN);		// 物品区分:3:樽保証金
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_SYURUI);		// 伝票打分け区分:01:酒類

		db.setString(iCc, ++ii, SYOHIN_KB_BUPPIN);				// 物品区分:5:物品（商品外）
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_BUPIN);		// 伝票打分け区分:03:物品

		db.setString(iCc, ++ii, SYOHIN_KB_FUKUSANBUTU);			// 物品区分:6:副産物
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_HI_SYURUI);	// 伝票打分け区分:02:非酒類

		db.setString(iCc, ++ii, SYOHIN_KB_GENRYO);				// 物品区分:7:原料
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_HI_SYURUI);	// 伝票打分け区分:02:非酒類

		db.setString(iCc, ++ii, SYOHIN_KB_UNTIN);					// 物品区分:8:運賃
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_UNTIN);		// 伝票打分け区分:04:運賃

		db.setString(iCc, ++ii, SYOHIN_KB_SONOTA);				// 物品区分:9:その他
		db.setString(iCc, ++ii, DENPYO_UTIWAKE_KB_BUPIN);		// 伝票打分け区分:03:物品

		db.setString(iCc, ++ii, cus.getKtksyCd());					// 寄託者
		db.setString(iCc, ++ii, PbsUtil.getToday(""));				// 業務日付
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_KARI_TOROKU);		// 仮登録
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_RIYO_KA);			// 利用可能

		db.setString(iCc, ++ii, PbsUtil.getToday(""));				// 業務日付
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_KARI_TOROKU);		// 仮登録
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_RIYO_KA);			// 利用可能

		db.setString(iCc, ++ii, AVAILABLE_KB_RIYO_KA);
		db.setString(iCc, ++ii, cus.getKaisyaCd());
		db.setString(iCc, ++ii, KURAC_DATA_KB_TEISEIPLUS);		// ﾃﾞｰﾀ種別CD 5:訂正(+)
		db.setString(iCc, ++ii, KURAC_DATA_KB_TEISEIMINUS);		// ﾃﾞｰﾀ種別CD 6:訂正(-)

		db.setString(iCc, ++ii, orositen_cd_last);
		db.setString(iCc, ++ii, atukai_kbn);
		db.setString(iCc, ++ii, bill_splitout_kbn);
		db.setString(iCc, ++ii, juchu_mng_key);
		db.setString(iCc, ++ii, unchin_kbn);
		db.setString(iCc, ++ii, unsoten_cd);
		db.setString(iCc, ++ii, haso_yotei_dt);
		db.setString(iCc, ++ii, tatesn_cd);
		db.setString(iCc, ++ii, shohin_cd);
		db.setString(iCc, ++ii, hanbai_tanka);

		if(!PbsUtil.isEmpty(hacyu_no)) {
			db.setString(iCc, ++ii, hacyu_no);
		}

		if(!PbsUtil.isEmpty(syuhanten_cd)) {
			db.setString(iCc, ++ii, syuhanten_cd);
		}

		sb2.append(" \n");
		sb2.append("UPDATE T_KURAC_DT                \n");
		sb2.append("   SET SYUKADEN_NO      = ?      \n");		//黄桜出荷伝票NO
		sb2.append(" WHERE                           \n");
		sb2.append("       RIYOU_KBN        = ?      \n");
		sb2.append("   AND KAISYA_CD        = ?      \n");
		sb2.append("   AND KURADATA_NO      = ?      \n");
		sb2.append("   AND KURADEN_LINE_NO  = ?      \n");

		/**=================================================================
		 *  SQL実行
		 *==================================================================**/
		db.execute(iCc);

		PbsRecord[] recs = null;
		try {
			recs = db.getPbsRecords(iCc,false);

		} catch (MaxRowsException e) {

		} catch (DataNotFoundException e) {

		}

		for(int i=0; i<recs.length; i++) {
			try {

				/**=================================================================
				 *  SQL実行
				 *==================================================================**/
				//
				int jCc = db.prepare(true, sb2.toString());
				int jj = 0;

				db.setString(jCc, ++jj, syuka_no);
				db.setString(jCc, ++jj, AVAILABLE_KB_RIYO_KA);
				db.setString(jCc, ++jj, cus.getKaisyaCd());
				db.setString(jCc, ++jj, recs[i].getString("KURADATA_NO"));
				db.setString(jCc, ++jj, recs[i].getString("KURADEN_LINE_NO"));

				db.executeUpdate(jCc,true);

			} catch (UpdateRowException e) {
				category__.error(e.getMessage(), e);
				db.rollback();
				throw e;
			} catch (DeadLockException e) {
				category__.error(e.getMessage(), e);
				db.rollback();
				throw e;
			} catch (UniqueKeyViolatedException e) {
				category__.error(e.getMessage(), e);
				db.rollback();
				throw e;
			} catch (SQLException e) {
				category__.error(e.getMessage(), e);
				db.rollback();
				throw e;
			}
		}
	}


	/**
	 *
	 * 容量計算
	 *
	 */
	public static String getYORYO(String soubaraSu, String yoryo){

		// 容量計算
		String ret = PbsUtil.sToSMultiplication(soubaraSu, yoryo);

		// 所定スケールで切り捨て
		return PbsUtil.sToSRoundDown(ret, 3);
	}


	/**
	 *
	 *  double => String変換
	 *
	 */

	public static String dToS(double doubleValue) {

		String strValue = null; //戻り値

		try {
			strValue = Double.toString(doubleValue); //doubleをStringに変換
			if ((("0.0").equals(strValue))||(("-0.0").equals(strValue))) {
				// ZEROの変換
				strValue = "0";
			}
		} catch (NumberFormatException e) {
			if(category__.isInfoEnabled()) {
				// category__.info("cast error double => String ");
				return null;
			}
		}
		return strValue;
	}



}
