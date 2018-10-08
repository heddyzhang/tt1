package kit.juchu.JuchuAddOnly;

import static fb.com.IKitComConst.*;
import static fb.inf.pbs.IPbsConst.*;
import static kit.juchu.JuchuAddOnly.IJuchuJuchuAddOnly.*;

import java.math.BigDecimal;
import java.util.HashMap;

import kit.mastr.OrositenSyosai.MastrOrositenSyosaiDtList;
import kit.mastr.OrositenSyosai.MastrOrositenSyosaiDtRecord;
import fb.inf.KitList;
import fb.inf.KitRecord;
import fb.inf.KitSelectList;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 受付専用受注追加入力（受注データ／ディテール部）専用のリスト構造を提供する。
 */
public class JuchuJuchuAddOnlyDtList extends KitList {

	/** シリアルID */
	private static final long serialVersionUID = -5176393335580738367L;

	/**
	 * コンストラクタ。
	 */
	public JuchuJuchuAddOnlyDtList() {
		super();
	}

	/**
	 * コンストラクタ 指定された件数分空のレコードをセット
	 *
	 * @param initSize
	 */
	public JuchuJuchuAddOnlyDtList(int initSize) {
		for (int ii = 0; ii < initSize; ii++) {
			this.add(this.createRecord());
		}
	}

	/**
	 * コンストラクタ。
	 *
	 * @param records PbsRecord[]
	 */
	public JuchuJuchuAddOnlyDtList(PbsRecord[] records) {
		super(records);
	}

	/**
	 * コンストラクタ
	 *
	 * @param record JuchuJuchuAddOnlyDtRecord
	 */
	public JuchuJuchuAddOnlyDtList(JuchuJuchuAddOnlyDtRecord record) {
		super(record);
	}


	/**
	 * 新規リストを作成し返却する。
	 *
	 * @return 新規リスト
	 */
	@Override
	public KitList createList() {
		return new JuchuJuchuAddOnlyDtList();
	}

	/**
	 * 指定位置から指定最大件数まで空のレコードをセット
	 *
	 * @param start
	 * @param maxSize
	 */
	public void initListToMax(int start, int maxSu) {
		for (int ii = start; ii < maxSu; ii++) {
			this.add(this.createRecord());
		}
	}

	/**
	 * 指定番号のリストをコピーする。
	 *
	 * @param from int
	 * @param to int
	 *
	 * @return リストのコピー
	 */
	@Override
	public KitList copy(int from, int to) {
		JuchuJuchuAddOnlyDtList list = (JuchuJuchuAddOnlyDtList) super.copy(from, to);
		return list;
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord() {
		return new JuchuJuchuAddOnlyDtRecord();
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @param record HashMap
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord(HashMap<String, Object> record) {
		return new JuchuJuchuAddOnlyDtRecord(record);
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @param record KitRecord
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord(KitRecord record) {
		return new JuchuJuchuAddOnlyDtRecord((JuchuJuchuAddOnlyDtRecord) record);
	}


	/**
	 * リクエストに応じたリストに成型する
	 *
	 * @param reqType
	 */
	public void format(String reqType){

		// 抹消、復活、参照追加以外は、スキップ
		if (!PbsUtil.isIncluded(reqType, REQ_TYPE_DELETE, REQ_TYPE_REBIRTH, REQ_TYPE_REFERENCE)) {
			return;
		}

		// 参照追加は、追加モードフラグを立てる
		if (PbsUtil.isIncluded(reqType, REQ_TYPE_REFERENCE)) {
			setAddMode();
		}

		JuchuJuchuAddOnlyDtRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuJuchuAddOnlyDtRecord) tmp;
			// 抹消
			if (PbsUtil.isEqual(reqType, REQ_TYPE_DELETE)) {
				rec.setDelete(RECORD_DELETE_ON);
				rec.setRiyouKbn(AVAILABLE_KB_RIYO_TEISI); // 利用区分（利用不可）
			}
			// 復活
			if (PbsUtil.isEqual(reqType, REQ_TYPE_REBIRTH)) {
				rec.setRiyouKbn(AVAILABLE_KB_RIYO_KA); // 利用区分（利用可）
			}
			// 参照追加
			if (PbsUtil.isEqual(reqType, REQ_TYPE_REFERENCE)) {
				rec.setRiyouKbn(AVAILABLE_KB_RIYO_KA); // 利用区分（利用可）
				rec.setJyucyuNo(CHAR_BLANK); // 受注NO（値をクリア）
				rec.setInputLineNo(CHAR_BLANK); // 受注行NO（値をクリア）
			}
		}

	}


	/**
	 * 受注行No.を設定する。
	 */
	public void setInputLineNos() {
		int idx = 0;
		JuchuJuchuAddOnlyDtRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuJuchuAddOnlyDtRecord) tmp;
			// 商品CDがある場合のみ
			if (!PbsUtil.isEmpty(rec.getShohinCd())) {
				idx++;
				rec.setInputLineNo(Integer.toString(idx));
			}
		}
	}

	/**
	 * 特注指示情報（特注指示区分、PB OR 特注指示内容）を設定する。
	 *
	 * @param cautionList
	 */
	public void setCautionInfos(MastrOrositenSyosaiDtList cautionList) {
		JuchuJuchuAddOnlyDtRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuJuchuAddOnlyDtRecord) tmp;
			// 商品CDがある場合のみ
			if (!PbsUtil.isEmpty(rec.getShohinCd())) {
				// 特注指示情報設定
				for (PbsRecord ctmp : cautionList) {
					MastrOrositenSyosaiDtRecord cRec = (MastrOrositenSyosaiDtRecord) ctmp;
					// 特注指示区分
					if ((rec.getShohinCd()).equals(cRec.getShohinCd())) {
						rec.setPbTokucyuKbn(TOKUCYU_ARI); // 特注指示あり
						rec.setPbTokucyu(cRec.getPbTokucyu()); // PB OR 特注指示内容
					} else {
						rec.setPbTokucyuKbn(TOKUCYU_NASI); // 特注指示なし
						rec.setPbTokucyu(CHAR_BLANK); // PB OR 特注指示内容
					}
				}
			}
		}
	}

	/**
	 * 商品名を設定する。
	 *
	 * @param checkServ
	 * @param kaisyaCd
	 * @param ktksyCd
	 * @param isShortName
	 */
	public void setShohinNms(JuchuJuchuAddOnly_CheckService checkServ, String kaisyaCd, String ktksyCd, boolean isShortName) {
		JuchuJuchuAddOnlyDtRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuJuchuAddOnlyDtRecord) tmp;
			// 商品CDがある場合のみ
			if (!PbsUtil.isEmpty(rec.getShohinCd())) {
				rec.setShohinNm(checkServ.getShohinNmByCd(rec.getShohinCd(), kaisyaCd, ktksyCd, isShortName, true));
			}
		}
	}

	/**
	 * 容量(ml)を設定する。
	 *
	 * @param checkServ
	 * @param kaisyaCd
	 * @param ktksyCd
	 */
	public void setTnpnVolMls(JuchuJuchuAddOnly_CheckService checkServ, String kaisyaCd, String ktksyCd) {
		JuchuJuchuAddOnlyDtRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuJuchuAddOnlyDtRecord) tmp;
			// 商品CDがある場合のみ
			if (!PbsUtil.isEmpty(rec.getShohinCd())) {
				rec.setBaraYouryo(checkServ.getTnpnVolByShohinCd(rec.getShohinCd(), kaisyaCd, ktksyCd)); // バラ容量(L)
				rec.setTnpnVolMl(checkServ.getTnpnVolMlByShohinCd(rec.getShohinCd(), kaisyaCd, ktksyCd)); // 単品総容量(ML)@1バラ当り
			}
		}
	}

	/**
	 * 容量(ml)を設定する。
	 *
	 */
	public void setTnpnVolMls() {
		JuchuJuchuAddOnlyDtRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuJuchuAddOnlyDtRecord) tmp;
			// 商品CDがある場合のみ
			if (!PbsUtil.isEmpty(rec.getShohinCd())) {
				// 1000倍して小数点以下を四捨五入
				BigDecimal tnpnVol = new BigDecimal(rec.getBaraYouryo()); // 単品総容量(L)@1バラ当り
				BigDecimal tnpnVolMl = (tnpnVol.multiply(new BigDecimal(1000))).setScale(0, BigDecimal.ROUND_HALF_UP);
				// 単品総容量(ML)@1バラ当り
				if (PbsUtil.isEmpty(tnpnVolMl.toString())) {
					rec.setTnpnVolMl(CHAR_BLANK);
				} else {
					rec.setTnpnVolMl(tnpnVolMl.toString());
				}
			}
		}
	}

	/**
	 * 入数を設定する。
	 *
	 * @param checkServ
	 * @param kaisyaCd
	 * @param ktksyCd
	 */
	public void setCaseIrisus(JuchuJuchuAddOnly_CheckService checkServ, String kaisyaCd, String ktksyCd) {
		JuchuJuchuAddOnlyDtRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuJuchuAddOnlyDtRecord) tmp;
			// 商品CDがある場合のみ
			if (!PbsUtil.isEmpty(rec.getShohinCd())) {
				rec.setCaseIrisu(checkServ.getCaseIrisuByShohinCd(rec.getShohinCd(), kaisyaCd, ktksyCd));
			}
		}
	}

	/**
	 * 出荷対応を設定する。
	 *
	 * @param checkServ
	 * @param kaisyaCd
	 * @param ktksyCd
	 * @param decodeList
	 */
	public void setSyukaTaioKbns(JuchuJuchuAddOnly_CheckService checkServ, String kaisyaCd, String ktksyCd, KitSelectList decodeList) {
		JuchuJuchuAddOnlyDtRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuJuchuAddOnlyDtRecord) tmp;
			// 商品CDがある場合のみ
			if (!PbsUtil.isEmpty(rec.getShohinCd())) {
				String syukaTaioKbn = checkServ.getSyukaTaioKbnByShohinCd(rec.getShohinCd(), kaisyaCd, ktksyCd);
				rec.setSyukaTaioKbn(syukaTaioKbn); // 出荷対応区分
				// 出荷対応名
				String syukaTaioNm = decodeList.decodeCode(syukaTaioKbn);
				if (PbsUtil.isEmpty(syukaTaioNm)) {
					rec.setSyukaTaioNm(CHAR_BLANK);
				} else {
					rec.setSyukaTaioNm(syukaTaioNm);
				}
			}
		}
	}

	/**
	 * 扱い区分を設定する。
	 *
	 * @param checkServ
	 * @param kaisyaCd
	 * @param ktksyCd
	 * @param decodeList
	 */
	public void setAtukaiKbns(JuchuJuchuAddOnly_CheckService checkServ, String kaisyaCd, String ktksyCd, KitSelectList decodeList) {
		JuchuJuchuAddOnlyDtRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuJuchuAddOnlyDtRecord) tmp;
			// 商品CDがある場合のみ
			if (!PbsUtil.isEmpty(rec.getShohinCd())) {
				String atukaiKbn = checkServ.getAtukaiKbnByShohinCd(rec.getShohinCd(), kaisyaCd, ktksyCd);
				rec.setAtukaiKbn(atukaiKbn); // 扱い区分
				// 扱い区分名
				String atukaiKbnNm = decodeList.decodeCode(atukaiKbn);
				if (PbsUtil.isEmpty(atukaiKbnNm)) {
					rec.setAtukaiKbnNm(CHAR_BLANK);
				} else {
					rec.setAtukaiKbnNm(atukaiKbnNm);
				}
			}
		}
	}

	/**
	 * 扱い区分を設定する。
	 *
	 * @param decodeList
	 */
	public void setAtukaiKbns(KitSelectList decodeList) {
		JuchuJuchuAddOnlyDtRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuJuchuAddOnlyDtRecord) tmp;
			// 商品CDがある場合のみ
			if (!PbsUtil.isEmpty(rec.getShohinCd())) {
				// 扱い区分名
				String atukaiKbnNm = decodeList.decodeCode(rec.getAtukaiKbn());
				if (PbsUtil.isEmpty(atukaiKbnNm)) {
					rec.setAtukaiKbnNm(CHAR_BLANK);
				} else {
					rec.setAtukaiKbnNm(atukaiKbnNm);
				}
			}
		}
	}

	/**
	 * 在庫残数（箱数、セット数、総バラ数、取得日時）を設定する。
	 *
	 * @param checkServ
	 * @param kuraCd
	 * @param syukaDt
	 * @param kaisyaCd
	 * @param ktksyCd
	 */
	public void setZaikoZansu(JuchuJuchuAddOnly_CheckService checkServ, String kuraCd, String syukaDt, String kaisyaCd, String ktksyCd) {
		JuchuJuchuAddOnlyDtRecord rec = null;
		String[] zaikoInfo;
		for (PbsRecord tmp : this) {
			rec = (JuchuJuchuAddOnlyDtRecord) tmp;
			// 商品CDと蔵CDと出荷日がある場合のみ
			if (!PbsUtil.isEmptyOr(rec.getShohinCd(), kuraCd, syukaDt)) {
				zaikoInfo = (checkServ.getZaikoZansu(rec.getShohinCd(), kuraCd, syukaDt, kaisyaCd, ktksyCd)).split(",");
				rec.setZaikoZansuCase(zaikoInfo[0]); // 箱数
				rec.setZaikoZansuBara(zaikoInfo[1]); // セット数
				rec.setZaikoZansu(zaikoInfo[2]); // 総バラ数
				rec.setZaikoGetDtTm(zaikoInfo[3]); // 取得日時
			}
		}
	}

	/**
	 * 販売単価を設定する。（未入力時のデフォルト値）
	 *
	 * @param checkServ
	 * @param kaisyaCd
	 * @param ktksyCd
	 */
	public void setHanbaiTanka(JuchuJuchuAddOnly_CheckService checkServ, String kaisyaCd, String ktksyCd) {
		JuchuJuchuAddOnlyDtRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuJuchuAddOnlyDtRecord) tmp;
			// 商品CDがある場合のみ
			if (!PbsUtil.isEmpty(rec.getShohinCd())) {
				if (PbsUtil.isEmpty(rec.getHanbaiTanka())) {
					rec.setHanbaiTanka(checkServ.getHanbaiTankaByShohinCd(rec.getShohinCd(), kaisyaCd, ktksyCd));
				}
			}
		}
	}

	/**
	 * ディテール部の付加情報を設定する。（在庫情報なし）
	 *
	 * @param checkServ
	 * @param kaisyaCd
	 * @param ktksyCd
	 * @param isShortName
	 * @param atukaiKbnList
	 */
	public void setDtInfoForSelect(JuchuJuchuAddOnly_CheckService checkServ, String kaisyaCd, String ktksyCd, boolean isShortName, KitSelectList atukaiKbnList) {
		// 商品名を設定
		this.setShohinNms(checkServ, kaisyaCd, ktksyCd, isShortName);

		// 容量(ml)を設定
		this.setTnpnVolMls();

		// 扱い区分を設定
		this.setAtukaiKbns(atukaiKbnList);
	}

	/**
	 * ディテール部の付加情報を設定する。（在庫情報あり）
	 *
	 * @param checkServ
	 * @param hdRec
	 * @param kaisyaCd
	 * @param ktksyCd
	 * @param isShortName
	 * @param atukaiKbnList
	 */
	public void setDtInfoWithZaiko(JuchuJuchuAddOnly_CheckService checkServ, JuchuJuchuAddOnlyRecord hdRec, String kaisyaCd, String ktksyCd, boolean isShortName, KitSelectList atukaiKbnList) {
		// 商品名を設定
		this.setShohinNms(checkServ, kaisyaCd, ktksyCd, isShortName);

		// 容量(ml)を設定
		this.setTnpnVolMls(checkServ, kaisyaCd, ktksyCd);

		// 入数を設定
		this.setCaseIrisus(checkServ, kaisyaCd, ktksyCd);

		// 扱い区分を設定
		this.setAtukaiKbns(checkServ, kaisyaCd, ktksyCd, atukaiKbnList);

		// 在庫残数（箱数、セット数、総バラ数、取得日時）を設定
		this.setZaikoZansu(checkServ, hdRec.getKuraCd(), hdRec.getSyukaDt(), kaisyaCd, ktksyCd);

		// 販売単価を設定（未入力時のデフォルト値）
		this.setHanbaiTanka(checkServ, kaisyaCd, ktksyCd);
	}

	/**
	 * 作業ページ用リストを取得する
	 *
	 * @param from 開始インデックス
	 * @param to 終了インデックス
	 * @return 作業ページ用リスト（fromからtoまでのリスト）
	 */
	public JuchuJuchuAddOnlyDtList getWorkPageList(int from, int to) {
		JuchuJuchuAddOnlyDtList ret = new JuchuJuchuAddOnlyDtList();
		for (int i = 0; i < this.size(); i++) {
			JuchuJuchuAddOnlyDtRecord rec = (JuchuJuchuAddOnlyDtRecord) this.get(i);
			if (from <= i && i <= to) {
				ret.add(rec);
			}
		}
		return ret;
	}

	/**
	 * 指定した扱い区分・物品区分に該当するリストを取得する
	 *
	 * @param atukaiKbn 扱い区分
	 * @param bupinKbn 物品区分
	 * @return 抽出リスト
	 */
	public JuchuJuchuAddOnlyDtList getListContainKbn(String[] atukaiKbn, String[] bupinKbn) {
		JuchuJuchuAddOnlyDtList ret = new JuchuJuchuAddOnlyDtList();
		for (int i = 0; i < this.size(); i++) {
			JuchuJuchuAddOnlyDtRecord rec = (JuchuJuchuAddOnlyDtRecord) this.get(i);
			if (PbsUtil.isIncluded(rec.getAtukaiKbn(), atukaiKbn)
					&& PbsUtil.isIncluded(rec.getBupinKbn(), bupinKbn)) {
				ret.add(rec);
			}
		}
		return ret;
	}

}	// -- class
