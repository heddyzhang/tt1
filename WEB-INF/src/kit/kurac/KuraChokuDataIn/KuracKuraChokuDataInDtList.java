package kit.kurac.KuraChokuDataIn;

import static fb.com.IKitComConst.*;
import static fb.com.IKitComConstHM.*;
import static fb.inf.pbs.IPbsConst.*;

import java.math.BigDecimal;
import java.util.HashMap;

import fb.inf.KitList;
import fb.inf.KitRecord;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 蔵元入力（蔵元データ／ディテール部）専用のリスト構造を提供する。
 */
public class KuracKuraChokuDataInDtList extends KitList {

	/**
	 *
	 */

	/** シリアルID */
	private static final long serialVersionUID = 1538248038854186123L;

	/**
	 * コンストラクタ。
	 */
	public KuracKuraChokuDataInDtList() {
		super();
	}

	/**
	 * コンストラクタ 指定された件数分空のレコードをセット
	 *
	 * @param initSize
	 */
	public KuracKuraChokuDataInDtList(int initSize) {
		for (int ii = 0; ii < initSize; ii++) {
			this.add(this.createRecord());
		}
	}

	/**
	 * コンストラクタ。
	 *
	 * @param records PbsRecord[]
	 */
	public KuracKuraChokuDataInDtList(PbsRecord[] records) {
		super(records);
	}

	/**
	 * コンストラクタ
	 *
	 * @param record JuchuJuchuDataInDtRecord
	 */
	public KuracKuraChokuDataInDtList(KuracKuraChokuDataInDtRecord record) {
		super(record);
	}


	/**
	 * 新規リストを作成し返却する。
	 *
	 * @return 新規リスト
	 */
	@Override
	public KitList createList() {
		return new KuracKuraChokuDataInDtList();
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
		KuracKuraChokuDataInDtList list = (KuracKuraChokuDataInDtList) super.copy(from, to);
		return list;
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord() {
		return new KuracKuraChokuDataInDtRecord();
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @param record HashMap
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord(HashMap<String, Object> record) {
		return new KuracKuraChokuDataInDtRecord(record);
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @param record KitRecord
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord(KitRecord record) {
		return new KuracKuraChokuDataInDtRecord((KuracKuraChokuDataInDtRecord) record);
	}


	/**
	 * リクエストに応じたリストに成型する
	 *
	 * @param reqType
	 */
	public void format(String reqType){

		// 抹消、新規、参照追加、訂正(+)、 訂正(－)以外は、スキップ
		if (!PbsUtil.isIncluded(reqType, REQ_TYPE_DELETE, REQ_TYPE_ADD, REQ_TYPE_REFERENCE,
				REQ_TYPE_REFPLUS, REQ_TYPE_REFMINUS)) {
			return;
		}

		// 新規、参照追加、訂正(+), 訂正(－)は、追加モードフラグを立てる
		if (PbsUtil.isIncluded(reqType, REQ_TYPE_ADD, REQ_TYPE_REFERENCE,
				REQ_TYPE_REFPLUS, REQ_TYPE_REFMINUS)) {
			setAddMode();
		}

		KuracKuraChokuDataInDtRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (KuracKuraChokuDataInDtRecord) tmp;

			// 抹消
			if (PbsUtil.isEqual(reqType, REQ_TYPE_DELETE)) {
				rec.setDelete(RECORD_DELETE_ON);
				rec.setRiyouKbn(AVAILABLE_KB_RIYO_TEISI);	// 利用区分（利用不可）
			}
			// 参照追加
			if (PbsUtil.isEqual(reqType, REQ_TYPE_REFERENCE)) {
				rec.setRiyouKbn(AVAILABLE_KB_RIYO_KA);		// 利用区分（利用可）
				rec.setKuradataNo(CHAR_BLANK);				// 蔵直ﾃﾞｰﾀ連番（値をクリア）
				rec.setSyukadenNo(CHAR_BLANK);				//黄桜出荷伝票NO
				rec.setTeiseiSyukaDt(CHAR_BLANK);			// 訂正時訂正元出荷日（値をクリア）
				rec.setTeiseiUridenNo(CHAR_BLANK);			// 訂正時訂正元売上伝票NO（値をクリア）
			}
			// 訂正(+)
			if (PbsUtil.isEqual(reqType, REQ_TYPE_REFPLUS)) {
				rec.setRiyouKbn(AVAILABLE_KB_RIYO_KA);		// 利用区分（利用可）
				rec.setKuradataNo(CHAR_BLANK);				// 蔵直ﾃﾞｰﾀ連番（値をクリア）
				// 商品コードが存在した場合
				if (!PbsUtil.isEmpty(rec.getShohinCd())) {
					// 商品セットdefault値
					rec.setShohinSet(SU_ZERO);
					rec.setHanbaiGaku(SU_ZERO);
				}
			}
			// 訂正(-)
			if (PbsUtil.isEqual(reqType, REQ_TYPE_REFMINUS)) {
				rec.setRiyouKbn(AVAILABLE_KB_RIYO_KA);		// 利用区分（利用可）
				rec.setKuradataNo(CHAR_BLANK);				// 蔵直ﾃﾞｰﾀ連番（値をクリア）
				// 商品コードが存在した場合
				if (!PbsUtil.isEmpty(rec.getShohinCd())) {
					// 商品セットdefault値
					rec.setShohinSet(SU_ZERO);
					rec.setHanbaiGaku(SU_ZERO);
				}
			}
			// 新規
			if (PbsUtil.isEqual(reqType, REQ_TYPE_ADD)) {
				rec.setRiyouKbn(AVAILABLE_KB_RIYO_KA);		// 利用区分（利用可）
			}
		}
	}

	/**
	 * 商品行NOを設定する。
	 */
	public void setKuradenLineNos() {
		int idx = 0;
		KuracKuraChokuDataInDtRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (KuracKuraChokuDataInDtRecord) tmp;
			// 商品申込ｾｯﾄ数がある場合のみ
			if (!PbsUtil.isEmpty(rec.getShohinSet())) {
				idx++;
				rec.setKuradenLineNo(Integer.toString(idx));
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
	public void setHanbaiTanka(KuracKuraChokuDataIn_CheckService checkServ, String kaisyaCd, String ktksyCd) {
		KuracKuraChokuDataInDtRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (KuracKuraChokuDataInDtRecord) tmp;
			// 商品CDがある場合のみ
			if (!PbsUtil.isEmpty(rec.getShohinCd())) {
				if (PbsUtil.isEmpty(rec.getHanbaiTanka())) {
					//rec.setHanbaiTanka(checkServ.getHanbaiTankaByShohinCd(rec.getShohinCd(), kaisyaCd, ktksyCd));
				}
			}
		}
	}

	/**
	 * 作業ページ用リストを取得する
	 *
	 * @param from 開始インデックス
	 * @param to 終了インデックス
	 * @return 作業ページ用リスト（fromからtoまでのリスト）
	 */
	public KuracKuraChokuDataInDtList getWorkPageList(int from, int to) {
		KuracKuraChokuDataInDtList ret = new KuracKuraChokuDataInDtList();
		for (int i = 0; i < this.size(); i++) {
			KuracKuraChokuDataInDtRecord rec = (KuracKuraChokuDataInDtRecord) this.get(i);
			if (from <= i && i <= to) {
				ret.add(rec);
			}
		}
		return ret;
	}

	/**
	 * ディテール部の表示用情報を設定する。
	 *
	 */
	public void setDtInfo() {

		for (int i = 0; i < this.size(); i++) {
			// セット数
			String setSu = "";
			// 販売単価
			String hanbaiTanka ="";
			// 販売額
			BigDecimal decHanbaiGaku = BigDecimal.ZERO;

			KuracKuraChokuDataInDtRecord rec = (KuracKuraChokuDataInDtRecord) this.get(i);

			// 未入力行はスキップ
			if (rec.isEmpty()) {
				continue;
			}

			// セット数
			if (!PbsUtil.isEmpty(rec.getShohinSet())) {
				setSu = rec.getShohinSet();
			}

			// 販売単価
			if (!PbsUtil.isEmpty(rec.getHanbaiTanka())) {
				hanbaiTanka = rec.getHanbaiTanka();
				// 販売単価を設定
				rec.setHanbaiTanka(new BigDecimal(hanbaiTanka).setScale(2, BigDecimal.ROUND_DOWN).toString());
			}

			// 販売額の計算
			if (!PbsUtil.isEmpty(hanbaiTanka) && !PbsUtil.isEmpty(setSu)) {
				decHanbaiGaku = new BigDecimal(hanbaiTanka).multiply(new BigDecimal(setSu));
				// 販売額を設定
				rec.setHanbaiGaku(decHanbaiGaku.setScale(2, BigDecimal.ROUND_DOWN).toString());
			}

			// 販売額View
			if (!PbsUtil.isEmpty(rec.getShohinCdView()) && PbsUtil.isEmpty(rec.getHanbaiGakuView()) ) {
				rec.setHanbaiTankaView(rec.getHanbaiTanka());
				rec.setHanbaiGakuView(rec.getHanbaiGaku());
			}

		}

	}
}	// -- class
