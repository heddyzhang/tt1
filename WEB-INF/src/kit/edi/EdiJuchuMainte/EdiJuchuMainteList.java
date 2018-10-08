package kit.edi.EdiJuchuMainte;

import static fb.com.IKitComConstHM.*;

import java.util.HashMap;

import fb.inf.KitList;
import fb.inf.KitRecord;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * EDI受発注受信データ処理（EDI受発注データ／ﾍｯﾀﾞｰ部）専用のリスト構造を提供する。
 */
public class EdiJuchuMainteList extends KitList {

	/** シリアルID */
	private static final long serialVersionUID = -992960815016836642L;

	/**
	 * コンストラクタ。
	 */
	public EdiJuchuMainteList() {
		super();
	}

	/**
	 * コンストラクタ 指定された件数分空のレコードをセット
	 *
	 * @param initSize
	 */
	public EdiJuchuMainteList(int initSize) {
		for (int ii = 0; ii < initSize; ii++) {
			this.add(this.createRecord());
		}
	}

	/**
	 * コンストラクタ。
	 *
	 * @param records PbsRecord[]
	 */
	public EdiJuchuMainteList(PbsRecord[] records) {
		super(records);
	}

	/**
	 * コンストラクタ
	 *
	 * @param record JuchuJuchuDataInRecord
	 */
	public EdiJuchuMainteList(EdiJuchuMainteRecord record) {
		super(record);
	}

	/**
	 * 新規リストを作成し返却する。
	 *
	 * @return 新規リスト
	 */
	@Override
	public KitList createList() {
		return new EdiJuchuMainteList();
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
		EdiJuchuMainteList list = (EdiJuchuMainteList) super.copy(from, to);
		return list;
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord() {
		return new EdiJuchuMainteRecord();
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @param record HashMap
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord(HashMap<String, Object> record) {
		return new EdiJuchuMainteRecord(record);
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @param record KitRecord
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord(KitRecord record) {
		return new EdiJuchuMainteRecord((EdiJuchuMainteRecord) record);
	}

	/**
	 * 一致するレコードに更新対象フラグをセットする
	 *
	 * @param keys id
	 */
	public void setModifiedByCd(String ... keys) {

		// 引数のいずれかが空のときは何もしない
		if (PbsUtil.isEmptyOr(keys)) {
			return;
		}

		// 引数が２つ未満のときは何もしない
		if (keys.length < 2) {
			return;
		}

		// 初期化
		initFlgs();

		EdiJuchuMainteRecord rec = getRecByCode(keys[0]);
		if (rec != null) {
			rec.setModified(IS_MODIFIED_TRUE);
		}

	}

	/**
	 * コードを指定してレコードを取得する
	 *
	 * @param juchuNo
	 * @return JuchuJuchuDataInRecord
	 */
	public EdiJuchuMainteRecord getRecByCode(String ediJyuhacyuId){

		// 値取得フラグ
		Boolean flg = false;
		EdiJuchuMainteRecord rec = null;

		for (PbsRecord tmp : this) {
			rec = (EdiJuchuMainteRecord) tmp;
			if (!PbsUtil.isEqual(ediJyuhacyuId, rec.getEdiJyuhacyuId())) {
				continue;
			}
			flg = true; // 対象レコードを発見したのでflgをtrueにする
			break;
		}

		// 対象レコードを発見できなかったので、レコードを空にする
		if (!flg){
			rec = null;
		}

		return rec;
	}

	/**
	 * 行選択IDに一致するレコードに更新対象フラグをセットする
	 *
	 * @param selectedRowId
	 */
	public void setModifiedById(String selectedRowId){
		// 引数が空のとき何もしない
		if (PbsUtil.isEmpty(selectedRowId)) {
			return;
		}
		// 引数が数字変換できないとき何もしない
		if (!PbsUtil.isNumber(selectedRowId)) {
			return;
		}

		// 初期化
		initFlgs();

		// 指定したIDでレコードを取得する
		EdiJuchuMainteRecord rec = (EdiJuchuMainteRecord) this.getRecSelectedRow(selectedRowId);
		rec.setModified(IS_MODIFIED_TRUE);
	}

}	// -- class
