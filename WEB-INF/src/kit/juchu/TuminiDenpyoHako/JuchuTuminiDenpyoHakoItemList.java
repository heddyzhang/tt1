package kit.juchu.TuminiDenpyoHako;

import static fb.com.IKitComConstHM.*;
import static kit.juchu.TuminiDenpyoHako.IJuchuTuminiDenpyoHako.*;

import java.util.HashMap;

import fb.inf.KitList;
import fb.inf.KitRecord;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 積荷伝票発行
 * 　受注データ／DT部のリストクラス
 */
public class JuchuTuminiDenpyoHakoItemList extends KitList {

	/** シリアルID */
	private static final long serialVersionUID = 6165694273889215672L;

	/**
	 * コンストラクタ。
	 */
	public JuchuTuminiDenpyoHakoItemList() {
		super();
	}

	/**
	 * コンストラクタ 指定された件数分空のレコードをセット
	 *
	 * @param initSize
	 */
	public JuchuTuminiDenpyoHakoItemList(int initSize) {
		for (int ii = 0; ii < initSize; ii++) {
			this.add(this.createRecord());
		}
	}

	/**
	 * コンストラクタ。
	 *
	 * @param records PbsRecord[]
	 */
	public JuchuTuminiDenpyoHakoItemList(PbsRecord[] records) {
		super(records);
	}

	/**
	 * コンストラクタ
	 *
	 * @param record JuchuTuminiDenpyoHakoItemRecord
	 */
	public JuchuTuminiDenpyoHakoItemList(JuchuTuminiDenpyoHakoItemRecord record) {
		super(record);
	}


	/**
	 * 新規リストを作成し返却する。
	 *
	 * @return 新規リスト
	 */
	@Override
	public KitList createList() {
		return new JuchuTuminiDenpyoHakoItemList();
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
		JuchuTuminiDenpyoHakoItemList list = (JuchuTuminiDenpyoHakoItemList) super.copy(from, to);
		return list;
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord() {
		return new JuchuTuminiDenpyoHakoItemRecord();
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @param record HashMap
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord(HashMap<String, Object> record) {
		return new JuchuTuminiDenpyoHakoItemRecord(record);
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @param record KitRecord
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord(KitRecord record) {
		return new JuchuTuminiDenpyoHakoItemRecord((JuchuTuminiDenpyoHakoItemRecord) record);
	}

	/**
	 * 一致するレコードに更新対象フラグをセットする
	 *
	 * @param keys juchuNo
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

		JuchuTuminiDenpyoHakoRecord rec = getRecByCode(keys[0]);
		if (rec != null) {
			rec.setModified(IS_MODIFIED_TRUE);
		}

	}

	/**
	 * コードを指定してレコードを取得する
	 *
	 * @param juchuNo
	 * @return JuchuTuminiDenpyoHakoRecord
	 */
	public JuchuTuminiDenpyoHakoRecord getRecByCode(String juchuNo){

		JuchuTuminiDenpyoHakoRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuTuminiDenpyoHakoRecord) tmp;
			if (!PbsUtil.isEqual(juchuNo, rec.getJyucyuNo())) {
				continue;
			}
			break;
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
		if (PbsUtil.isNumber(selectedRowId)) {
			return;
		}

		// 初期化
		initFlgs();

		// 指定したIDでレコードを取得する
		JuchuTuminiDenpyoHakoRecord rec = (JuchuTuminiDenpyoHakoRecord) this.getRecSelectedRow(selectedRowId);
		rec.setModified(IS_MODIFIED_TRUE);
	}


	/**
	 * チェックされたレコードに更新対象フラグをセットする
	 */
	public void setModifiedByChk() {
		// 初期化
		initFlgs();

		// チェック状態からフラグを更新
		JuchuTuminiDenpyoHakoRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuTuminiDenpyoHakoRecord) tmp;
			if (PbsUtil.isIncluded(rec.getDenpyoHakoFlg(), CHECKBOX_ON, CHECKBOX_TRUE, CHECKBOX_YES)) {
				rec.setModified(IS_MODIFIED_TRUE);
			} else {
				rec.setModified(IS_MODIFIED_FALSE);
			}
		}
	}




}	// -- class
