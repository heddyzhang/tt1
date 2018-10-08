package kit.kurac.KuraChokuDataIn;

import static fb.com.IKitComConst.*;
import static fb.com.IKitComConstHM.*;
import static fb.com.SerialNumber.ComJuchuRnbnKanri.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionErrors;

import fb.com.ComHaitaService;
import fb.com.ComUserSession;
import fb.com.ComUtil;
import fb.inf.KitRrkUpdateService;
import fb.inf.exception.DataNotFoundException;
import fb.inf.exception.DeadLockException;
import fb.inf.exception.MaxRowsException;
import fb.inf.exception.ResourceBusyNowaitException;
import fb.inf.exception.UniqueKeyViolatedException;
import fb.inf.exception.UpdateRowException;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * データベースへの書き込みを実装するビジネスロジッククラスです。
 *
 */
public class KuracKuraChokuDataIn_JyucyuService extends KitRrkUpdateService {

	/**
	 * シリアルID
	 */
	private static final long serialVersionUID = -972054795100312620L;

	/** クラス名. */
	private static String className__ = KuracKuraChokuDataIn_JyucyuService.class.getName();
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
	public KuracKuraChokuDataIn_JyucyuService(ComUserSession cus, PbsDatabase db_, boolean isTran, ActionErrors ae) {
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
	 * 受注追加処理
	 * 　蔵元入力（蔵元データ／ヘッダー部）・蔵元入力（蔵元データ／ディテール部）
	 * 　を同一トランザクションで処理
	 *
	 * @return TRUE:成功 / FALSE:失敗
	 */
	public boolean executeJyucyu() {

		category__.info("受注追加処理 START");

		db_.setTransaction();
		// 基盤側で操作ログを出力する際にはtrueを与える迂回
		db_.setTranLog(true);

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		PbsDatabase db = getDatabase(); //DBコネクション

		// ==================================================
		// 受注作成蔵元ﾃﾞｰﾀ抽出
		// ==================================================

		boolean err_flg = false;

		List<PbsRecord> recListH = new ArrayList<PbsRecord>();
		List<PbsRecord> recListD = new ArrayList<PbsRecord>();
		List<PbsRecord> recListC = new ArrayList<PbsRecord>();

		String w_jyucyu_no = null;
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

			PbsRecord wkjyucyu_hd = new PbsRecord();

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
			 * 受注ﾃﾞｰﾀ_ﾍｯﾀﾞｰ部処理
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
			wkjyucyu_hd.put("RIYOU_KBN"             , AVAILABLE_KB_RIYO_KA);
			//会社CD
			wkjyucyu_hd.put("KAISYA_CD"             , cus.getKaisyaCd());
			//出荷先区分
			wkjyucyu_hd.put("SYUKA_KBN"             , SHIPMENT_KB_KAZEI);
			//ﾃﾞｰﾀ種別CD
			wkjyucyu_hd.put("SYUBETU_CD"            , JDATAKIND_KB_TANKA_TIGAI);
			//蔵CD
			wkjyucyu_hd.put("KURA_CD"               , KBN_KURA_MISU);
			//運賃区分
			wkjyucyu_hd.put("UNCHIN_KBN"            , kurac_hd_recs[i].getString("UNCHIN_KBN"));
			//運送店CD
			wkjyucyu_hd.put("UNSOTEN_CD"            , kurac_hd_recs[i].getString("UNSOTEN_CD"));
			//出荷日(売上伝票発行予定日)
			wkjyucyu_hd.put("SYUKA_DT"              , kurac_hd_recs[i].getString("HASO_YOTEI_DT"));
			//ミナシ日付
			wkjyucyu_hd.put("MINASI_DT"             , kurac_hd_recs[i].getString("HASO_YOTEI_DT"));
			//着荷予定日
			wkjyucyu_hd.put("CHACUNI_YOTEI_DT"      , "");
			//荷受時間区分
			wkjyucyu_hd.put("NIUKE_TIME_KBN"        , orosi_hd_recs[0].getString("NIUKE_TIME_KBN"));
			//荷受時間_開始
			wkjyucyu_hd.put("NIUKE_BIGIN_TIME"      , orosi_hd_recs[0].getString("NIUKE_BIGIN_TIME"));
			//荷受時間_終了
			wkjyucyu_hd.put("NIUKE_END_TIME"        , orosi_hd_recs[0].getString("NIUKE_END_TIME"));
			//先方発注NO
			wkjyucyu_hd.put("SENPO_HACYU_NO"        , kurac_hd_recs[i].getString("HACYU_NO"));
			//SDN受注ﾍｯﾀﾞｰｴﾗｰ区分
			wkjyucyu_hd.put("SDN_HDERR_KBN"         , "");
			//縦線CD
			wkjyucyu_hd.put("TATESN_CD"             , kurac_hd_recs[i].getString("TATESN_CD"));
			//担当者CD
			wkjyucyu_hd.put("TANTOSYA_CD"           , orosi_hd_recs[0].getString("TANTOSYA_CD"));
			//特約店CD
			wkjyucyu_hd.put("TOKUYAKUTEN_CD"        , orosi_hd_recs[0].getString("OROSITEN_CD_1JITEN"));
			//ﾃﾞﾎﾟCD
			wkjyucyu_hd.put("DEPO_CD"               , orosi_hd_recs[0].getString("OROSITEN_CD_DEPO"));
			//二次店CD
			wkjyucyu_hd.put("NIJITEN_CD"            , orosi_hd_recs[0].getString("OROSITEN_CD_2JITEN"));
			//三次店CD
			wkjyucyu_hd.put("SANJITEN_CD"           , orosi_hd_recs[0].getString("OROSITEN_CD_3JITEN"));

			//酒販店（統一）CD
			wkjyucyu_hd.put("SYUHANTEN_CD"          , kurac_hd_recs[i].getString("SYUHANTEN_CD"));
			//倉入・直送区分
			wkjyucyu_hd.put("DELIVERY_KBN"          , DELIVERY1_KB_OROSITEN_CHOKUSO);
			//出荷先国CD
			wkjyucyu_hd.put("SYUKA_SAKI_COUNTRY_CD" , orositen_recs[0].getString("SYUKA_SAKI_COUNTRY_CD"));
			//JISCD
			wkjyucyu_hd.put("JIS_CD"                , orositen_recs[0].getString("JIS_CD"));
			//引取運賃換算単価
			wkjyucyu_hd.put("UNCHIN_CNV_TANKA"      , orositen_recs[0].getString("KANZAN_TANKA"));
			//摘要区分 (01)
			wkjyucyu_hd.put("TEKIYO_KBN1"           , orosi_hd_recs[0].getString("TEKIYO_KBN1"));
			//摘要区分 (02)
			wkjyucyu_hd.put("TEKIYO_KBN2"           , orosi_hd_recs[0].getString("TEKIYO_KBN2"));
			//摘要区分 (03)
			wkjyucyu_hd.put("TEKIYO_KBN3"           , orosi_hd_recs[0].getString("TEKIYO_KBN3"));
			//摘要区分 (04)
			wkjyucyu_hd.put("TEKIYO_KBN4"           , orosi_hd_recs[0].getString("TEKIYO_KBN4"));
			//摘要区分 (05)
			wkjyucyu_hd.put("TEKIYO_KBN5"           , orosi_hd_recs[0].getString("TEKIYO_KBN5"));
			//摘要内容 (01)
			wkjyucyu_hd.put("TEKIYO_NM1"            , tekiyo_get(orosi_hd_recs[0].getString("TEKIYO_KBN1")));
			//摘要内容 (02)
			wkjyucyu_hd.put("TEKIYO_NM2"            , tekiyo_get(orosi_hd_recs[0].getString("TEKIYO_KBN2")));
			//摘要内容 (03)
			wkjyucyu_hd.put("TEKIYO_NM3"            , tekiyo_get(orosi_hd_recs[0].getString("TEKIYO_KBN3")));
			//摘要内容 (04)
			wkjyucyu_hd.put("TEKIYO_NM4"            , tekiyo_get(orosi_hd_recs[0].getString("TEKIYO_KBN4")));
			//摘要内容 (05)
			wkjyucyu_hd.put("TEKIYO_NM5"            , tekiyo_get(orosi_hd_recs[0].getString("TEKIYO_KBN5")));
			//扱い区分
			wkjyucyu_hd.put("ATUKAI_KBN"            , kurac_hd_recs[i].getString("ATUKAI_KBN"));
			//最低配送ロット数
			wkjyucyu_hd.put("LOW_HAISO_LOT"         , orositen_recs[0].getString("LOW_HAISO_LOT"));
			//小ロット出荷承認申請NO
			wkjyucyu_hd.put("SYUKA_SYONIN_NO"       , "");
			//積荷伝票NO
			wkjyucyu_hd.put("SYUKA_SYONIN_NO"       , "");
			//積荷累積対象区分
			wkjyucyu_hd.put("SYUKA_SYONIN_NO"       , "");
			//積荷伝票発行日
			wkjyucyu_hd.put("SYUKA_SYONIN_NO"       , "");
			//積荷伝票発行時刻
			wkjyucyu_hd.put("SYUKA_SYONIN_NO"       , "");
			//積荷伝票発行回数
			wkjyucyu_hd.put("SYUKA_SYONIN_NO"       , "0");
			//積荷伝票発行者ID
			wkjyucyu_hd.put("SYUKA_SYONIN_NO"       , "");


			// ﾃﾞｨﾃｰﾙ処理
			PbsRecord[] kurac_dt_recs= kurac_dt_Select(kurac_hd_recs[i].getString("OROSITEN_CD_LAST"),
													   kurac_hd_recs[i].getString("ATUKAI_KBN"),
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

				PbsRecord wkjyucyu_dt = new PbsRecord();

				/**=================================================================
				 * 受注ﾃﾞｰﾀ_ﾃﾞｨﾃｰﾙ処理
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
				wkjyucyu_dt.put("RIYOU_KBN"        , AVAILABLE_KB_RIYO_KA);
				//会社CD
				wkjyucyu_dt.put("KAISYA_CD"        , cus.getKaisyaCd());
				//受注行NO
				wkjyucyu_dt.put("INPUT_LINE_NO"    , PbsUtil.iToS(++det_line_no));
				//蔵CD
				wkjyucyu_dt.put("KURA_CD"          , KBN_KURA_MISU);
				//倉庫CD
				wkjyucyu_dt.put("SOUKO_CD"         , EMPTY_SOUKO_CD);
				//寄託者CD
				wkjyucyu_dt.put("KTKSY_CD"         , syohin_recs[0].getString("KTKSY_CD"));
				//商品CD
				wkjyucyu_dt.put("SHOHIN_CD"        , kurac_dt_recs[j].getString("SHOHIN_CD"));
				//製品CD
				wkjyucyu_dt.put("SEIHIN_CD"        , syohin_recs[0].getString("SEIHIN_CD"));
				//仕入先CD
				wkjyucyu_dt.put("SIIRESAKI_CD"     , syohin_recs[0].getString("SIIRESAKI_CD"));
				//仕入品CD
				wkjyucyu_dt.put("SIIREHIN_CD"      , syohin_recs[0].getString("SIIREHIN_CD"));
				//製品日付
				wkjyucyu_dt.put("SEIHIN_DT"        , "");
				//積荷伝票用ﾗｲﾝNO
				wkjyucyu_dt.put("TUMIDEN_LINE_NO"  , syohin_recs[0].getString("TUMIDEN_LINE_NO"));
				//ｹｰｽ入数
				wkjyucyu_dt.put("CASE_IRISU"       , syohin_recs[0].getString("CASE_IRISU"));
				//ﾊﾞﾗ容量(L)
				wkjyucyu_dt.put("BARA_YOURYO"      , syohin_recs[0].getString("TNPN_VOL"));
				//重量(KG)_ｹｰｽ
				wkjyucyu_dt.put("WEIGHT_CASE"      , syohin_recs[0].getString("MIIRI_WEIGHT_BOX"));
				//重量(KG)_ﾊﾞﾗ
				wkjyucyu_dt.put("WEIGHT_BARA"      , syohin_recs[0].getString("MIIRI_WEIGHT_BARA"));
				//運賃掛率(%)
				wkjyucyu_dt.put("UNCHIN_KAKERITU"  , syohin_recs[0].getString("UNCHIN_KEIGEN_RATE"));
				//販売単価
				wkjyucyu_dt.put("HANBAI_TANKA"     , kurac_dt_recs[j].getString("HANBAI_TANKA"));
				//補足欄
				wkjyucyu_dt.put("HOSOKURAN"        , "");

				//出荷数量_箱数
				wkjyucyu_dt.put("SYUKA_SU_CASE"    , "0");
				//出荷数量_ｾｯﾄ数
				wkjyucyu_dt.put("SYUKA_SU_BARA"    , kurac_dt_recs[j].getString("SHOHIN_SET"));
				//出荷数量_換算総ｾｯﾄ数
				wkjyucyu_dt.put("SYUKA_ALL_BARA"   , ComUtil.sobaraKeisan("0", kurac_dt_recs[j].getString("SHOHIN_SET"), "0"));
				//出荷重量(KG)
				wkjyucyu_dt.put("SYUKA_ALL_WEIGTH" , ComUtil.calcJyuryo("0", "0", kurac_dt_recs[j].getString("SHOHIN_SET"), syohin_recs[0].getString("MIIRI_WEIGHT_BARA")));

				//販売額
				wkjyucyu_dt.put("SYUKA_HANBAI_KINGAKU" , ComUtil.getGk(wkjyucyu_dt.getString("SYUKA_ALL_BARA"), kurac_dt_recs[j].getString("HANBAI_TANKA"), true));
				//出荷対応区分
				wkjyucyu_dt.put("SYUKA_TAIO_KBN"   , syohin_recs[0].getString("SYUKA_TAIO_KBN"));
				//扱い区分
				wkjyucyu_dt.put("ATUKAI_KBN"       , syohin_recs[0].getString("ATUKAI_KBN"));
				//物品区分
				wkjyucyu_dt.put("BUPIN_KBN"        , syohin_recs[0].getString("BUPIN_KBN"));
				//販売単価変更ﾌﾗｸﾞ
				wkjyucyu_dt.put("HTANKA_CHG_FLG"       , "0");
				//容量（L）_出荷総容量
				wkjyucyu_dt.put("SYUKA_SOUYOURYO"      , getYORYO(wkjyucyu_dt.getString("SYUKA_ALL_BARA"), syohin_recs[0].getString("TNPN_VOL")));
				//容量（L）_ﾘﾍﾞｰﾄⅠ類対象総容量
				wkjyucyu_dt.put("REBATE1_SOUYOURYO"    , getYORYO(wkjyucyu_dt.getString("SYUKA_ALL_BARA"), syohin_recs[0].getString("REBATE1_YOURYO")));
				//容量（L）_ﾘﾍﾞｰﾄⅡ類対象総容量
				wkjyucyu_dt.put("REBATE2_SOUYOURYO"    , getYORYO(wkjyucyu_dt.getString("SYUKA_ALL_BARA"), syohin_recs[0].getString("REBATE2_YOURYO")));
				//容量（L）_ﾘﾍﾞｰﾄⅢ類対象総容量
				wkjyucyu_dt.put("REBATE3_SOUYOURYO"    , getYORYO(wkjyucyu_dt.getString("SYUKA_ALL_BARA"), syohin_recs[0].getString("REBATE3_YOURYO")));
				//容量（L）_ﾘﾍﾞｰﾄⅣ類対象総容量
				wkjyucyu_dt.put("REBATE4_SOUYOURYO"    , getYORYO(wkjyucyu_dt.getString("SYUKA_ALL_BARA"), syohin_recs[0].getString("REBATE4_YOURYO")));
				//容量（L）_ﾘﾍﾞｰﾄⅤ類対象総容量
				wkjyucyu_dt.put("REBATE5_SOUYOURYO"    , getYORYO(wkjyucyu_dt.getString("SYUKA_ALL_BARA"), syohin_recs[0].getString("REBATE5_YOURYO")));
				//容量（L）_ﾘﾍﾞｰﾄ対象外総容量
				wkjyucyu_dt.put("REBATEO_SOUYOURYO"    , PbsUtil.sToSSubtraction(wkjyucyu_dt.getString("SYUKA_SOUYOURYO"),
			    		   								    PbsUtil.sToSAddition(wkjyucyu_dt.getString("REBATE1_SOUYOURYO"),
			    		   									   	 			     wkjyucyu_dt.getString("REBATE2_SOUYOURYO"),
			    		   										 			     wkjyucyu_dt.getString("REBATE3_SOUYOURYO"),
			    		   										 			     wkjyucyu_dt.getString("REBATE4_SOUYOURYO"),
			    		   										 			     wkjyucyu_dt.getString("REBATE5_SOUYOURYO"))));
				//特注指示区分
				wkjyucyu_dt.put("PB_TOKUCYU_KBN"       , orosi_dt_recs[0].getString("PB_TOKUCYU_KBN"));
				//PB OR 特注指示内容
				wkjyucyu_dt.put("PB_TOKUCYU"           , orosi_dt_recs[0].getString("PB_TOKUCYU"));
				//販売部門CD
				wkjyucyu_dt.put("HANBAI_BUMON_CD"      , kbn_hanbai_recs_dt[0].getString("HANBAI_BUMON_CD"));
				//販売部門名（略式）
				wkjyucyu_dt.put("HANBAI_BUMON_RNM"     , kbn_hanbai_recs_dt[0].getString("HANBAI_BUMON_RNM"));
				//販売種別CD
				wkjyucyu_dt.put("HANBAI_SYUBETU_CD"    , kbn_hanbai_recs_dt[0].getString("HANBAI_SYUBETU_CD"));
				//販売種別名（略式）
				wkjyucyu_dt.put("HANBAI_SYUBETU_RNM"   , kbn_hanbai_recs_dt[0].getString("HANBAI_SYUBETU_RNM"));
				//販売分類CD
				wkjyucyu_dt.put("HANBAI_BUNRUI_CD"     , kbn_hanbai_recs_dt[0].getString("HANBAI_BUNRUI_CD"));
				//販売分類名（略式）
				wkjyucyu_dt.put("HANBAI_BUNRUI_RNM"    , kbn_hanbai_recs_dt[0].getString("HANBAI_BUNRUI_RNM"));

				//SDN受注ﾃﾞｨﾃｰﾙｴﾗｰ区分
				wkjyucyu_dt.put("SDN_DTERR_KBN"        , "");
				//EDI配送依頼(集約)送信区分
				wkjyucyu_dt.put("EDI_HAISOUG_SEND_KB"  , YN_12_KB_MISOSIN);

				//ﾍｯﾀﾞｰ用合計加算
				w_syuka_suryo_box    = PbsUtil.sToSAddition(w_syuka_suryo_box   , wkjyucyu_dt.getString("SYUKA_SU_CASE"));			//出荷数量計_箱数
				w_syuka_suryo_set    = PbsUtil.sToSAddition(w_syuka_suryo_set   , wkjyucyu_dt.getString("SYUKA_SU_BARA"));			//出荷数量計_ｾｯﾄ数
				w_syuka_kingaku_tot  = PbsUtil.sToSAddition(w_syuka_kingaku_tot , wkjyucyu_dt.getString("SYUKA_HANBAI_KINGAKU"));	//出荷金額計
				w_jyuryo_tot         = PbsUtil.sToSAddition(w_jyuryo_tot        , wkjyucyu_dt.getString("SYUKA_ALL_WEIGTH"));		//重量計(KG)
				w_syuka_youryo_tot   = PbsUtil.sToSAddition(w_syuka_youryo_tot  , wkjyucyu_dt.getString("SYUKA_SOUYOURYO"));		//容量計（L）_出荷容量計
				w_rebate1_youryo_tot = PbsUtil.sToSAddition(w_rebate1_youryo_tot, wkjyucyu_dt.getString("REBATE1_SOUYOURYO"));		//容量計（L）_ﾘﾍﾞｰﾄⅠ類対象容量計
				w_rebate2_youryo_tot = PbsUtil.sToSAddition(w_rebate2_youryo_tot, wkjyucyu_dt.getString("REBATE2_SOUYOURYO"));		//容量計（L）_ﾘﾍﾞｰﾄⅡ類対象容量計
				w_rebate3_youryo_tot = PbsUtil.sToSAddition(w_rebate3_youryo_tot, wkjyucyu_dt.getString("REBATE3_SOUYOURYO"));		//容量計（L）_ﾘﾍﾞｰﾄⅢ類対象容量計
				w_rebate4_youryo_tot = PbsUtil.sToSAddition(w_rebate4_youryo_tot, wkjyucyu_dt.getString("REBATE4_SOUYOURYO"));		//容量計（L）_ﾘﾍﾞｰﾄⅣ類対象容量計
				w_rebate5_youryo_tot = PbsUtil.sToSAddition(w_rebate5_youryo_tot, wkjyucyu_dt.getString("REBATE5_SOUYOURYO"));		//容量計（L）_ﾘﾍﾞｰﾄⅤ類対象容量計
				w_rebateo_youryo_tot = PbsUtil.sToSAddition(w_rebateo_youryo_tot, wkjyucyu_dt.getString("REBATEO_SOUYOURYO"));		//容量計（L）_ﾘﾍﾞｰﾄ対象外容量計


				recListD.add(wkjyucyu_dt);


				/**=================================================================
				 * 予定出荷先別商品ｶﾃｺﾞﾘﾃﾞｰﾀ処理
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

					PbsRecord wkjyucyu_ctg = new PbsRecord();

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
					wkjyucyu_ctg.put("RIYOU_KBN"          , AVAILABLE_KB_RIYO_KA);
					//会社CD
					wkjyucyu_ctg.put("KAISYA_CD"          , cus.getKaisyaCd());
					//受注ｶﾃｺﾞﾘ行NO
					wkjyucyu_ctg.put("SHN_CTGR_LINE_NO"   , PbsUtil.iToS(++ctg_line_no));
					//寄託者CD
					wkjyucyu_ctg.put("KTKSY_CD"           , syohin_recs[0].getString("KTKSY_CD"));
					//製品CD
					wkjyucyu_ctg.put("SEIHIN_CD"          , syohin_recs[0].getString("SEIHIN_CD"));
					//販売部門CD
					wkjyucyu_ctg.put("HANBAI_BUMON_CD"    , kbn_hanbai_recs_ctg[0].getString("HANBAI_BUMON_CD"));
					//販売部門名（略式）
					wkjyucyu_ctg.put("HANBAI_BUMON_RNM"   , kbn_hanbai_recs_ctg[0].getString("HANBAI_BUMON_RNM"));
					//販売種別CD
					wkjyucyu_ctg.put("HANBAI_SYUBETU_CD"  , kbn_hanbai_recs_ctg[0].getString("HANBAI_SYUBETU_CD"));
					//販売種別名（略式）
					wkjyucyu_ctg.put("HANBAI_SYUBETU_RNM" , kbn_hanbai_recs_ctg[0].getString("HANBAI_SYUBETU_RNM"));
					//販売分類CD
					wkjyucyu_ctg.put("HANBAI_BUNRUI_CD"   , kbn_hanbai_recs_ctg[0].getString("HANBAI_BUNRUI_CD"));
					//販売分類名（略式）
					wkjyucyu_ctg.put("HANBAI_BUNRUI_RNM"  , kbn_hanbai_recs_ctg[0].getString("HANBAI_BUNRUI_RNM"));
					//酒税分類CD
					wkjyucyu_ctg.put("SYUZEI_CD"          , kbn_saketax_recs[0].getString("SYUZEI_CD"));
					//酒税分類名（略式）
					wkjyucyu_ctg.put("SYUZEI_NM_RYAKU"    , kbn_saketax_recs[0].getString("SYUZEI_NM_RYAKU"));
					//種CD
					wkjyucyu_ctg.put("TANE_CD"            , kbn_hanbai_recs_ctg[0].getString("TANE_CD"));
					//種名称（略式）
					wkjyucyu_ctg.put("TANE_NM_RYAKU"      , kbn_hanbai_recs_ctg[0].getString("TANE_NM_RYAKU"));
					//素材区分
					wkjyucyu_ctg.put("SOZAI_KBN"          , youki_recs[0].getString("SOZAI_KBN"));
					//色区分
					wkjyucyu_ctg.put("COLOR_KBN"          , youki_recs[0].getString("COLOR_KBN"));
					//販売実績分類区分
					wkjyucyu_ctg.put("HJISEKI_BUNRUI_KBN" , youki_recs[0].getString("HJISEKI_BUNRUI_KBN"));
					//容量（L)
					wkjyucyu_ctg.put("VOL"                , seihin_recs[0].getString("TNPN_VOL"));
					//単価（円）
					wkjyucyu_ctg.put("TANKA"              , seihin_gpr_recs[k].getString("KOSEI_TANKA"));
					//出荷本数計
					wkjyucyu_ctg.put("SYUKA_HON"          , PbsUtil.iToS(PbsUtil.sToI(seihin_gpr_recs[k].getString("KOSEI_SEIHIN_RIYOU_TEISU")) * PbsUtil.sToI(wkjyucyu_dt.getString("SYUKA_ALL_BARA"))));

					int w_ctg_bara = PbsUtil.sToI(seihin_gpr_recs[k].getString("KOSEI_SEIHIN_RIYOU_TEISU")) * PbsUtil.sToI(wkjyucyu_dt.getString("SYUKA_ALL_BARA"));
					//出荷容量計（L)
					wkjyucyu_ctg.put("SYUKA_VOL"          , getYORYO(PbsUtil.iToS(w_ctg_bara), seihin_recs[0].getString("TNPN_VOL")));
					//販売金額計（円）
					wkjyucyu_ctg.put("HANBAI_KINGAKU"     , ComUtil.getGk(PbsUtil.iToS(w_ctg_bara), seihin_gpr_recs[k].getString("KOSEI_TANKA"), true));

					recListC.add(wkjyucyu_ctg);

				}

				if(err_flg) {
					break;
				}

				//出荷数量計_箱数
				wkjyucyu_hd.put("SYUKA_SURYO_BOX"       , ((new BigDecimal(w_syuka_suryo_box)).setScale(0, BigDecimal.ROUND_HALF_UP)).toString());
				//出荷数量計_ｾｯﾄ数
				wkjyucyu_hd.put("SYUKA_SURYO_SET"       , ((new BigDecimal(w_syuka_suryo_set)).setScale(0, BigDecimal.ROUND_HALF_UP)).toString());
				//出荷金額計
				wkjyucyu_hd.put("SYUKA_KINGAKU_TOT"     , ((new BigDecimal(w_syuka_kingaku_tot)).setScale(2, BigDecimal.ROUND_HALF_UP)).toString());
				//重量計(KG)
				wkjyucyu_hd.put("JYURYO_TOT"            , ((new BigDecimal(w_jyuryo_tot)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString());
				//容量計（L）_出荷容量計
				wkjyucyu_hd.put("SYUKA_YOURYO_TOT"      , ((new BigDecimal(w_syuka_youryo_tot)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString());
				//容量計（L）_ﾘﾍﾞｰﾄⅠ類対象容量計
				wkjyucyu_hd.put("REBATE1_YOURYO_TOT"    , ((new BigDecimal(w_rebate1_youryo_tot)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString());
				//容量計（L）_ﾘﾍﾞｰﾄⅡ類対象容量計
				wkjyucyu_hd.put("REBATE2_YOURYO_TOT"    , ((new BigDecimal(w_rebate2_youryo_tot)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString());
				//容量計（L）_ﾘﾍﾞｰﾄⅢ類対象容量計
				wkjyucyu_hd.put("REBATE3_YOURYO_TOT"    , ((new BigDecimal(w_rebate3_youryo_tot)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString());
				//容量計（L）_ﾘﾍﾞｰﾄⅣ類対象容量計
				wkjyucyu_hd.put("REBATE4_YOURYO_TOT"    , ((new BigDecimal(w_rebate4_youryo_tot)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString());
				//容量計（L）_ﾘﾍﾞｰﾄⅤ類対象容量計
				wkjyucyu_hd.put("REBATE5_YOURYO_TOT"    , ((new BigDecimal(w_rebate5_youryo_tot)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString());
				//容量計（L）_ﾘﾍﾞｰﾄ対象外容量計
				wkjyucyu_hd.put("REBATEO_YOURYO_TOT"    , ((new BigDecimal(w_rebateo_youryo_tot)).setScale(3, BigDecimal.ROUND_HALF_UP)).toString());

				recListH.add(wkjyucyu_hd);

			}

			if(err_flg) {
				continue;
			}

			// PbsRecに変換
			PbsRecord[] kanseiRecH = (PbsRecord[]) recListH.toArray(new PbsRecord[recListH.size()] );
			PbsRecord[] kanseiRecD = (PbsRecord[]) recListD.toArray(new PbsRecord[recListD.size()] );
			PbsRecord[] kanseiRecC = (PbsRecord[]) recListC.toArray(new PbsRecord[recListC.size()] );


			//受注Noの決定
			try {
				w_jyucyu_no = nextNumber(db, cus.getKaisyaCd(), kurac_hd_recs[i].getString("JUCHU_MNG_KEY"), "Z");

			} catch (ResourceBusyNowaitException | SQLException e) {
				e.printStackTrace();
				return false;
			}

			//受注ﾃﾞｰﾀ_ﾍｯﾀﾞｰ 追加
			try {
				SqlInsertHd(kanseiRecH, w_jyucyu_no);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}

			//受注ﾃﾞｰﾀ_ﾃﾞｨﾃｰﾙ 追加
			try {
				SqlInsertDt(kanseiRecD, w_jyucyu_no);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}

			//予定出荷先別商品ｶﾃｺﾞﾘﾃﾞｰﾀ 追加
			try {
				SqlInsertCtg(kanseiRecC, w_jyucyu_no);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}

			//取得した受注Noを蔵元ﾃﾞｰﾀ_ﾍｯﾀﾞｰにセットする
			try {
				jyucyuNo_update(w_jyucyu_no,
								kurac_hd_recs[i].getString("OROSITEN_CD_LAST"),
								kurac_hd_recs[i].getString("ATUKAI_KBN"),
								kurac_hd_recs[i].getString("JUCHU_MNG_KEY"),
								kurac_hd_recs[i].getString("UNCHIN_KBN"),
								kurac_hd_recs[i].getString("UNSOTEN_CD"),
								kurac_hd_recs[i].getString("HASO_YOTEI_DT"),
								kurac_hd_recs[i].getString("TATESN_CD"),
								kurac_hd_recs[i].getString("HACYU_NO"),
								kurac_hd_recs[i].getString("SYUHANTEN_CD"));
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}

			db.commit();		//コミット

		}

		category__.info("受注追加処理 END");


		return true;
	}


	/**
	 * 蔵元ﾃﾞｰﾀより対象ﾃﾞｰﾀのﾍｯﾀﾞｰ情報取得
	 * （未受注＆未出荷ﾃﾞｰﾀの蔵直ﾃﾞｰﾀ取得）
	 * */
	public PbsRecord[] kurac_hd_Select() {

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/
		PbsDatabase db = getDatabase(); //DBコネクション

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT DISTINCT                                         \n");
		sb.append("       OHD.OROSITEN_CD_LAST AS OROSITEN_CD_LAST         \n");		//最終送荷先卸CD
		sb.append("      ,MSY.ATUKAI_KBN       AS ATUKAI_KBN               \n");		//扱い区分
		sb.append("      ,JTA.JUCHU_MNG_KEY    AS JUCHU_MNG_KEY            \n");		//採番用管轄事業所ｷｰ
		sb.append("      ,KHD.UNCHIN_KBN       AS UNCHIN_KBN               \n");		//運賃区分
		sb.append("      ,KHD.UNSOTEN_CD       AS UNSOTEN_CD               \n");		//運送店CD
		sb.append("      ,KHD.HASO_YOTEI_DT    AS HASO_YOTEI_DT            \n");		//発送予定日
		sb.append("      ,KHD.TATESN_CD        AS TATESN_CD                \n");		//縦線CD
		sb.append("      ,KHD.HACYU_NO         AS HACYU_NO                 \n");		//発注NO
		sb.append("      ,KHD.SYUHANTEN_CD     AS SYUHANTEN_CD             \n");		//酒販店（統一）CD
		sb.append("  FROM                                                  \n");
		sb.append("       T_KURAC_DT KDT                                   \n");
		sb.append("  LEFT JOIN T_KURAC_HD KHD                              \n");
		sb.append("    ON(                                                 \n");
		sb.append("       KDT.RIYOU_KBN     = KHD.RIYOU_KBN                \n");
		sb.append("   AND KDT.KAISYA_CD     = KHD.KAISYA_CD                \n");
		sb.append("   AND KDT.KURADATA_NO   = KHD.KURADATA_NO              \n");
		sb.append("      )                                                 \n");
		sb.append("  LEFT JOIN M_SYOHIN MSY                                \n");
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
		sb.append("       KDT.RIYOU_KBN     = ?                            \n");		//利用区分
		sb.append("   AND KDT.KAISYA_CD     = ?                            \n");		//会社CD
		sb.append("   AND KHD.SYUBETU_CD    = ?                            \n");		//ﾃﾞｰﾀ種別CD
		sb.append("   AND KHD.JYUCYU_NO    IS NULL                         \n");		//黄桜受注NO
		sb.append("   AND KDT.SYUKADEN_NO  IS NULL                         \n");		//黄桜出荷伝票NO
		sb.append("   AND KDT.SHOHIN_SET   != 0                            \n");		//商品申込ｾｯﾄ数
		sb.append(" ORDER BY                                               \n");
		sb.append("       OHD.OROSITEN_CD_LAST                             \n");		//最終送荷先卸CD
		sb.append("      ,MSY.ATUKAI_KBN                                   \n");		//扱い区分
		sb.append("      ,JTA.JUCHU_MNG_KEY                                \n");		//黄桜事業所区分
		sb.append("      ,KHD.UNCHIN_KBN                                   \n");		//運賃区分
		sb.append("      ,KHD.UNSOTEN_CD                                   \n");		//運送店CD
		sb.append("      ,KHD.HASO_YOTEI_DT                                \n");		//発送予定日
		sb.append("      ,KHD.TATESN_CD                                    \n");		//縦線CD
		sb.append("      ,KHD.HACYU_NO                                     \n");		//発注NO
		sb.append("      ,KHD.SYUHANTEN_CD                                 \n");		//酒販店（統一）CD

		int iCc = db.prepare(true, sb.toString());
		int ii = 0;

		db.setString(iCc, ++ii, cus.getKtksyCd());					// 寄託者
		db.setString(iCc, ++ii, PbsUtil.getToday(""));				// 業務日付
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_KARI_TOROKU);		// 仮登録
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_RIYO_KA);			// 利用可能

		db.setString(iCc, ++ii, PbsUtil.getToday(""));				// 業務日付
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_KARI_TOROKU);		// 仮登録
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_RIYO_KA);			// 利用可能

		db.setString(iCc, ++ii, AVAILABLE_KB_RIYO_KA);
		db.setString(iCc, ++ii, cus.getKaisyaCd());
		db.setString(iCc, ++ii, KURAC_DATA_KB_TUJYO);

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
	 * （未受注＆未出荷ﾃﾞｰﾀの蔵直ﾃﾞｰﾀ連番取得）
	 * */
	public PbsRecord[] kurac_dt_Select(String orositen_cd_last,
										String atukai_kbn,
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
		sb.append("  LEFT JOIN M_SYOHIN MSY                                \n");
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
		sb.append("   AND KHD.SYUBETU_CD        = ?                        \n");		//ﾃﾞｰﾀ種別CD
		sb.append("   AND KHD.JYUCYU_NO        IS NULL                     \n");		//黄桜受注NO
		sb.append("   AND KDT.SYUKADEN_NO      IS NULL                     \n");		//黄桜出荷伝票NO
		sb.append("   AND KDT.SHOHIN_SET       != 0                        \n");		//商品申込ｾｯﾄ数
		sb.append("   AND OHD.OROSITEN_CD_LAST  = ?                        \n");		//最終送荷先卸CD
		sb.append("   AND MSY.ATUKAI_KBN        = ?                        \n");		//扱い区分
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

		db.setString(iCc, ++ii, cus.getKtksyCd());					// 寄託者
		db.setString(iCc, ++ii, PbsUtil.getToday(""));				// 業務日付
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_KARI_TOROKU);		// 仮登録
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_RIYO_KA);			// 利用可能

		db.setString(iCc, ++ii, PbsUtil.getToday(""));				// 業務日付
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_KARI_TOROKU);		// 仮登録
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_RIYO_KA);			// 利用可能

		db.setString(iCc, ++ii, AVAILABLE_KB_RIYO_KA);
		db.setString(iCc, ++ii, cus.getKaisyaCd());
		db.setString(iCc, ++ii, KURAC_DATA_KB_TUJYO);

		db.setString(iCc, ++ii, orositen_cd_last);
		db.setString(iCc, ++ii, atukai_kbn);
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

		sb.append("SELECT                                                             \n");
		sb.append("       SYOM.KTKSY_CD                         AS KTKSY_CD           \n");		//寄託者
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
		sb.append("      ( ?, SYOM.JYUCYU_KAISI_DT, SYOM.JYUCYU_SYURYO_DT, SYOM.RIYOU_TEISI_DT ) IN ( ?, ? ) \n");

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
		sb.append("      ( ?, M_SEI.SEISAN_KAISI_DT, M_SEI.SEISAN_SYURYO_DT, M_SEI.RIYOU_TEISI_DT ) IN ( ?, ? ) \n");
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
		sb.append("      ( ?, M_SEI.SEISAN_KAISI_DT, M_SEI.SEISAN_SYURYO_DT, M_SEI.RIYOU_TEISI_DT ) IN ( ?, ? ) \n");
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
		sb.append("      ( ?, M_SIRE.USE_START_DT, M_SIRE.USE_END_DT, M_SIRE.USE_STOP_DT ) IN ( ?, ? ) \n");

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
	 * 卸店ﾏｽﾀｰ抽出
	 * */
	public PbsRecord[] m_orositen_get(String tatesen_cd) {

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/
		PbsDatabase db = getDatabase(); //DBコネクション

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT                                                      \n");
		sb.append("       ORS.JIS_CD                                           \n");		//JISCD
		sb.append("      ,ORS.SYUKA_SAKI_COUNTRY_CD                            \n");		//出荷先国CD
		sb.append("      ,ORS.LOW_HAISO_LOT                                    \n");		//最低配送ロット数
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
	 * 受注ﾃﾞｰﾀ_ﾍｯﾀﾞｰ部TBL登録
	 * @param recs
	 * @throws UpdateRowException
	 * @throws DeadLockException
	 * @throws SQLException
	 *
	 */
	private void SqlInsertHd(PbsRecord[] recs, String jyucyu_no) throws SQLException {

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/

		PbsDatabase db = getDatabase(); //DBコネクション

		StringBuilder sb = new StringBuilder();

		sb.append("INSERT INTO T_JYUCYU_HD              \n");
		sb.append("      (                              \n");
		sb.append("       RIYOU_KBN                     \n");        //利用区分
		sb.append("      ,KAISYA_CD                     \n");        //会社CD
		sb.append("      ,JYUCYU_NO                     \n");        //受注NO
		sb.append("      ,SYUKA_KBN                     \n");        //出荷先区分
		sb.append("      ,SYUBETU_CD                    \n");        //ﾃﾞｰﾀ種別CD
		sb.append("      ,KURA_CD                       \n");        //蔵CD
		sb.append("      ,UNCHIN_KBN                    \n");        //運賃区分
		sb.append("      ,UNSOTEN_CD                    \n");        //運送店CD
		sb.append("      ,SYUKA_DT                      \n");        //出荷日(売上伝票発行予定日)
		sb.append("      ,MINASI_DT                     \n");        //ﾐﾅｼ日付
		sb.append("      ,CHACUNI_YOTEI_DT              \n");        //着荷予定日
		sb.append("      ,NIUKE_TIME_KBN                \n");        //荷受時間区分
		sb.append("      ,NIUKE_BIGIN_TIME              \n");        //荷受時間_開始
		sb.append("      ,NIUKE_END_TIME                \n");        //荷受時間_終了
		sb.append("      ,SENPO_HACYU_NO                \n");        //先方発注NO
		sb.append("      ,SDN_HDERR_KBN                 \n");        //SDN受注ﾍｯﾀﾞｰｴﾗｰ区分
		sb.append("      ,TATESN_CD                     \n");        //縦線CD
		sb.append("      ,TANTOSYA_CD                   \n");        //担当者CD
		sb.append("      ,TOKUYAKUTEN_CD                \n");        //特約店CD
		sb.append("      ,DEPO_CD                       \n");        //ﾃﾞﾎﾟCD
		sb.append("      ,NIJITEN_CD                    \n");        //二次店CD
		sb.append("      ,SANJITEN_CD                   \n");        //三次店CD
		sb.append("      ,SYUHANTEN_CD                  \n");        //酒販店（統一）CD
		sb.append("      ,DELIVERY_KBN                  \n");        //倉入・直送区分
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
		sb.append("      ,ATUKAI_KBN                    \n");        //扱い区分
		sb.append("      ,LOW_HAISO_LOT                 \n");        //最低配送ロット数
		sb.append("      ,SYUKA_SYONIN_NO               \n");        //小ロット出荷承認申請NO
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
		sb.append("      ,TUMIDEN_NO                    \n");        //積荷伝票NO
		sb.append("      ,TUMIDEN_SM_KBN                \n");        //積荷累積対象区分
		sb.append("      ,TUMIDEN_HAKO_DT               \n");        //積荷伝票発行日
		sb.append("      ,TUMIDEN_HAKO_TM               \n");        //積荷伝票発行時刻
		sb.append("      ,TUMIDEN_HAKO_CNT              \n");        //積荷伝票発行回数
		sb.append("      ,TUMIDEN_HAKOSYA               \n");        //積荷伝票発行者ID
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
	    sb.append("      )    \n");

	    for(int l=0; l<recs.length; l++) {

			int ii=0;
		    int iCc = db.prepare(true, sb.toString());

		    db.setString(iCc, ++ii, recs[l].getString("RIYOU_KBN"));			    //利用区分
			db.setString(iCc, ++ii, recs[l].getString("KAISYA_CD"));               	//会社CD
			db.setString(iCc, ++ii, jyucyu_no);							        	//受注NO
			db.setString(iCc, ++ii, recs[l].getString("SYUKA_KBN"));               	//出荷先区分
			db.setString(iCc, ++ii, recs[l].getString("SYUBETU_CD"));              	//ﾃﾞｰﾀ種別CD
			db.setString(iCc, ++ii, recs[l].getString("KURA_CD"));  			    //蔵CD
			db.setString(iCc, ++ii, recs[l].getString("UNCHIN_KBN"));              	//運賃区分
			db.setString(iCc, ++ii, recs[l].getString("UNSOTEN_CD"));              	//運送店CD
			db.setString(iCc, ++ii, recs[l].getString("SYUKA_DT"));                	//出荷日(売上伝票発行予定日)
			db.setString(iCc, ++ii, recs[l].getString("MINASI_DT"));               	//ﾐﾅｼ日付
			db.setString(iCc, ++ii, recs[l].getString("CHACUNI_YOTEI_DT"));        	//着荷予定日
			db.setString(iCc, ++ii, recs[l].getString("NIUKE_TIME_KBN"));          	//荷受時間区分
			db.setString(iCc, ++ii, recs[l].getString("NIUKE_BIGIN_TIME"));        	//荷受時間_開始
			db.setString(iCc, ++ii, recs[l].getString("NIUKE_END_TIME"));          	//荷受時間_終了
			db.setString(iCc, ++ii, recs[l].getString("SENPO_HACYU_NO"));          	//先方発注NO
			db.setString(iCc, ++ii, recs[l].getString("SDN_HDERR_KBN"));           	//SDN受注ﾍｯﾀﾞｰｴﾗｰ区分
			db.setString(iCc, ++ii, recs[l].getString("TATESN_CD"));               	//縦線CD
			db.setString(iCc, ++ii, recs[l].getString("TANTOSYA_CD"));             	//担当者CD
			db.setString(iCc, ++ii, recs[l].getString("TOKUYAKUTEN_CD"));          	//特約店CD
			db.setString(iCc, ++ii, recs[l].getString("DEPO_CD"));				    //ﾃﾞﾎﾟCD
			db.setString(iCc, ++ii, recs[l].getString("NIJITEN_CD"));              	//二次店CD
			db.setString(iCc, ++ii, recs[l].getString("SANJITEN_CD"));             	//三次店CD
			db.setString(iCc, ++ii, recs[l].getString("SYUHANTEN_CD"));            	//酒販店（統一）CD
			db.setString(iCc, ++ii, recs[l].getString("DELIVERY_KBN"));   			//倉入・直送区分
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
			db.setString(iCc, ++ii, recs[l].getString("LOW_HAISO_LOT"));           	//最低配送ロット数
			db.setString(iCc, ++ii, recs[l].getString("SYUKA_SYONIN_NO"));         	//小ロット出荷承認申請NO
			db.setString(iCc, ++ii, recs[l].getString("SYUKA_SURYO_BOX"));			//出荷数量計_箱数
			db.setString(iCc, ++ii, recs[l].getString("SYUKA_SURYO_SET"));	      	//出荷数量計_ｾｯﾄ数
			db.setString(iCc, ++ii, recs[l].getString("SYUKA_KINGAKU_TOT"));      	//出荷金額計
			db.setString(iCc, ++ii, recs[l].getString("JYURYO_TOT"));           	//重量計(KG)
			db.setString(iCc, ++ii, recs[l].getString("SYUKA_YOURYO_TOT"));      	//容量計（L）_出荷容量計
			db.setString(iCc, ++ii, recs[l].getString("REBATE1_YOURYO_TOT"));    	//容量計（L）_ﾘﾍﾞｰﾄⅠ類対象容量計
			db.setString(iCc, ++ii, recs[l].getString("REBATE2_YOURYO_TOT"));      	//容量計（L）_ﾘﾍﾞｰﾄⅡ類対象容量計
			db.setString(iCc, ++ii, recs[l].getString("REBATE3_YOURYO_TOT"));		//容量計（L）_ﾘﾍﾞｰﾄⅢ類対象容量計
			db.setString(iCc, ++ii, recs[l].getString("REBATE4_YOURYO_TOT"));      	//容量計（L）_ﾘﾍﾞｰﾄⅣ類対象容量計
			db.setString(iCc, ++ii, recs[l].getString("REBATE5_YOURYO_TOT"));     	//容量計（L）_ﾘﾍﾞｰﾄⅤ類対象容量計
			db.setString(iCc, ++ii, recs[l].getString("REBATEO_YOURYO_TOT"));      	//容量計（L）_ﾘﾍﾞｰﾄ対象外容量計
			db.setString(iCc, ++ii, recs[l].getString("TUMIDEN_NO"));        		//積荷伝票NO
			db.setString(iCc, ++ii, recs[l].getString("TUMIDEN_SM_KBN"));         	//積荷累積対象区分
			db.setString(iCc, ++ii, recs[l].getString("TUMIDEN_HAKO_DT"));         	//積荷伝票発行日
			db.setString(iCc, ++ii, recs[l].getString("TUMIDEN_HAKO_TM"));         	//積荷伝票発行時刻
			db.setString(iCc, ++ii, recs[l].getString("TUMIDEN_HAKO_CNT"));        	//積荷伝票発行回数
			db.setString(iCc, ++ii, recs[l].getString("TUMIDEN_HAKOSYA"));         	//積荷伝票発行者ID

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
	 * 受注ﾃﾞｰﾀ_ﾃﾞｨﾃｨｰﾙ部TBL登録
	 * @param recs
	 * @throws SQLException
	 *
	 */
	private void SqlInsertDt(PbsRecord[] recs, String jyucyu_no) throws SQLException {

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/

		PbsDatabase db = getDatabase(); //DBコネクション

		StringBuilder sb = new StringBuilder();

		sb.append("INSERT INTO T_JYUCYU_DT              \n");
		sb.append("      (                              \n");
		sb.append("       RIYOU_KBN                     \n");        //利用区分
		sb.append("      ,KAISYA_CD                     \n");        //会社CD
		sb.append("      ,JYUCYU_NO                     \n");        //受注NO
		sb.append("      ,INPUT_LINE_NO                 \n");        //受注行NO
		sb.append("      ,KURA_CD                       \n");        //蔵CD
		sb.append("      ,SOUKO_CD                      \n");        //倉庫CD
		sb.append("      ,KTKSY_CD                      \n");        //寄託者CD
		sb.append("      ,SHOHIN_CD                     \n");        //商品CD
		sb.append("      ,SEIHIN_CD                     \n");        //製品CD
		sb.append("      ,SIIRESAKI_CD                  \n");        //仕入先CD
		sb.append("      ,SIIREHIN_CD                   \n");        //仕入品CD
		sb.append("      ,SEIHIN_DT                     \n");        //製品日付
		sb.append("      ,TUMIDEN_LINE_NO               \n");        //積荷伝票用ﾗｲﾝNO
		sb.append("      ,CASE_IRISU                    \n");        //ｹｰｽ入数
		sb.append("      ,BARA_YOURYO                   \n");        //ﾊﾞﾗ容量(L)
		sb.append("      ,WEIGHT_CASE                   \n");        //重量(KG)_ｹｰｽ
		sb.append("      ,WEIGHT_BARA                   \n");        //重量(KG)_ﾊﾞﾗ
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
		sb.append("      ,SDN_DTERR_KBN                 \n");        //SDN受注ﾃﾞｨﾃｰﾙｴﾗｰ区分
		sb.append("      ,EDI_HAISOUG_SEND_KB           \n");        //EDI配送依頼(集約)送信区分
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
	    sb.append("      )    \n");

	    for(int l=0; l<recs.length; l++) {

	    	int ii=0;
	    	int iCc = db.prepare(true, sb.toString());

			db.setString(iCc, ++ii, recs[l].getString("RIYOU_KBN"));					//利用区分
			db.setString(iCc, ++ii, recs[l].getString("KAISYA_CD"));                    //会社CD
			db.setString(iCc, ++ii, jyucyu_no);						                    //受注NO
			db.setString(iCc, ++ii, recs[l].getString("INPUT_LINE_NO"));				//受注行NO
			db.setString(iCc, ++ii, recs[l].getString("KURA_CD"));				    	//蔵CD
			db.setString(iCc, ++ii, recs[l].getString("SOUKO_CD"));                     //倉庫CD
			db.setString(iCc, ++ii, recs[l].getString("KTKSY_CD"));                     //寄託者CD
			db.setString(iCc, ++ii, recs[l].getString("SHOHIN_CD"));              		//商品CD
			db.setString(iCc, ++ii, recs[l].getString("SEIHIN_CD"));        			//製品CD
			db.setString(iCc, ++ii, recs[l].getString("SIIRESAKI_CD"));                 //仕入先CD
			db.setString(iCc, ++ii, recs[l].getString("SIIREHIN_CD"));                  //仕入品CD
			db.setString(iCc, ++ii, recs[l].getString("SEIHIN_DT"));                    //製品日付
			db.setString(iCc, ++ii, recs[l].getString("TUMIDEN_LINE_NO"));              //積荷伝票用ﾗｲﾝNO
			db.setString(iCc, ++ii, recs[l].getString("CASE_IRISU"));                   //ｹｰｽ入数
			db.setString(iCc, ++ii, recs[l].getString("BARA_YOURYO"));                  //ﾊﾞﾗ容量(L)
			db.setString(iCc, ++ii, recs[l].getString("WEIGHT_CASE"));                  //重量(KG)_ｹｰｽ
			db.setString(iCc, ++ii, recs[l].getString("WEIGHT_BARA"));                  //重量(KG)_ﾊﾞﾗ
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
			db.setString(iCc, ++ii, recs[l].getString("SDN_DTERR_KBN"));        		//SDN受注ﾃﾞｨﾃｰﾙｴﾗｰ区分
			db.setString(iCc, ++ii, recs[l].getString("EDI_HAISOUG_SEND_KB"));			//EDI配送依頼(集約)送信区分

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
	private void SqlInsertCtg(PbsRecord[] recs, String jyucyu_no) throws SQLException {

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/

		PbsDatabase db = getDatabase(); //DBコネクション

		StringBuilder sb = new StringBuilder();

		sb.append("INSERT INTO T_JYUCYU_JSKCTG          \n");
		sb.append("      (                              \n");
		sb.append("       RIYOU_KBN                     \n");        //利用区分
		sb.append("      ,KAISYA_CD                     \n");        //会社CD
		sb.append("      ,JYUCYU_NO                     \n");        //受注NO
		sb.append("      ,SHN_CTGR_LINE_NO              \n");        //受注ｶﾃｺﾞﾘ行NO
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
			db.setString(iCc, ++ii, jyucyu_no);						                //受注NO
			db.setString(iCc, ++ii, recs[l].getString("SHN_CTGR_LINE_NO"));         //受注ｶﾃｺﾞﾘ行NO
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
	 * 受注Noを対象となった蔵元ﾃﾞｰﾀ_ﾍｯﾀﾞｰにセット
	 * @param
	 * @throws SQLException
	 *
	 */
	public void jyucyuNo_update(String jyucyu_no,
								 String orositen_cd_last,
								 String atukai_kbn,
								 String juchu_mng_key,
								 String unchin_kbn,
								 String unsoten_cd,
								 String haso_yotei_dt,
								 String tatesn_cd,
								 String hacyu_no,
								 String syuhanten_cd) throws SQLException {

		// ユーザー情報
		ComUserSession cus = getComUserSession();

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/
		PbsDatabase db = getDatabase();
		StringBuilder sb = new StringBuilder();

		sb.append("UPDATE                                                  \n");
		sb.append("       T_KURAC_HD KH                                    \n");
		sb.append("   SET                                                  \n");
		sb.append("       KH.JYUCYU_NO          = ?                        \n"); 		//黄桜受注NO
		sb.append(" WHERE                                                  \n");
		sb.append("       KH.KURADATA_NO IN                                \n");		//蔵直ﾃﾞｰﾀ連番
		sb.append("      (                                                 \n");

		sb.append("SELECT DISTINCT                                         \n");
		sb.append("       KHD.KURADATA_NO AS KURADATA_NO                   \n");		//蔵直ﾃﾞｰﾀ連番
		sb.append("  FROM                                                  \n");
		sb.append("       T_KURAC_DT KDT                                   \n");
		sb.append("  LEFT JOIN T_KURAC_HD KHD                              \n");
		sb.append("    ON(                                                 \n");
		sb.append("       KDT.RIYOU_KBN     = KHD.RIYOU_KBN                \n");
		sb.append("   AND KDT.KAISYA_CD     = KHD.KAISYA_CD                \n");
		sb.append("   AND KDT.KURADATA_NO   = KHD.KURADATA_NO              \n");
		sb.append("      )                                                 \n");
		sb.append("  LEFT JOIN M_SYOHIN MSY                                \n");
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
		sb.append("   AND KHD.SYUBETU_CD        = ?                        \n");		//ﾃﾞｰﾀ種別CD
		sb.append("   AND KHD.JYUCYU_NO        IS NULL                     \n");		//黄桜受注NO
		sb.append("   AND KDT.SYUKADEN_NO      IS NULL                     \n");		//黄桜出荷伝票NO
		sb.append("   AND KDT.SHOHIN_SET       != 0                        \n");		//商品申込ｾｯﾄ数
		sb.append("   AND OHD.OROSITEN_CD_LAST  = ?                        \n");		//最終送荷先卸CD
		sb.append("   AND MSY.ATUKAI_KBN        = ?                        \n");		//扱い区分
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

		sb.append("      )                                                 \n");

		int iCc = db.prepare(true, sb.toString());
		int ii= 0;


		db.setString(iCc, ++ii, jyucyu_no);

		db.setString(iCc, ++ii, cus.getKtksyCd());					// 寄託者
		db.setString(iCc, ++ii, PbsUtil.getToday(""));				// 業務日付
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_KARI_TOROKU);		// 仮登録
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_RIYO_KA);			// 利用可能

		db.setString(iCc, ++ii, PbsUtil.getToday(""));				// 業務日付
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_KARI_TOROKU);		// 仮登録
		db.setString(iCc, ++ii, DATA_JYOTAI_KB_RIYO_KA);			// 利用可能

		db.setString(iCc, ++ii, AVAILABLE_KB_RIYO_KA);			//利用区分
		db.setString(iCc, ++ii, cus.getKaisyaCd());					//会社CD
		db.setString(iCc, ++ii, KURAC_DATA_KB_TUJYO);				//ﾃﾞｰﾀ種別CD

		db.setString(iCc, ++ii, orositen_cd_last);					//最終送荷先卸CD
		db.setString(iCc, ++ii, atukai_kbn);						//扱い区分
		db.setString(iCc, ++ii, juchu_mng_key);						//採番用管轄事業所ｷｰ
		db.setString(iCc, ++ii, unchin_kbn);						//運賃区分
		db.setString(iCc, ++ii, unsoten_cd);						//運送店CD
		db.setString(iCc, ++ii, haso_yotei_dt);						//発送予定日
		db.setString(iCc, ++ii, tatesn_cd);							//縦線CD

		if(!PbsUtil.isEmpty(hacyu_no)) {
			db.setString(iCc, ++ii, hacyu_no);
		}

		if(!PbsUtil.isEmpty(syuhanten_cd)) {
			db.setString(iCc, ++ii, syuhanten_cd);
		}

		try {

			/**=================================================================
			 *  SQL実行
			 *==================================================================**/
			//
			db.executeUpdate(iCc,true);

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
