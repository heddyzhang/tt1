package kit.jiseki.CategoryList;

import java.util.HashMap;

import fb.inf.KitList;
import fb.inf.KitRecord;
import fb.inf.pbs.PbsRecord;

/**
 * カテゴリ別販売実績 専用のリスト構造を提供する。
 */
public class JisekiCategoryList extends KitList {


	/**
	 *
	 */
	private static final long serialVersionUID = 6493686401856374353L;

	/**
	 * コンストラクタ。
	 */
	public JisekiCategoryList() {
		super();
	}

	/**
	 * コンストラクタ 指定された件数分空のレコードをセット
	 *
	 * @param initSize
	 */
	public JisekiCategoryList(int initSize) {
		for (int ii = 0; ii < initSize; ii++) {
			this.add(this.createRecord());
		}
	}

	/**
	 * コンストラクタ。
	 *
	 * @param records
	 *            PbsRecord[]
	 */
	public JisekiCategoryList(PbsRecord[] records) {
		super(records);
	}

	/**
	 * コンストラクタJisekiNenpouRecord()
	 * @param record
	 */
	public JisekiCategoryList(JisekiCategoryListRecord record) {
		super(record);
	}


	/**
	 * 新規リストを作成し返却する。
	 *
	 * @return 新規リスト
	 */
	@Override
	public KitList createList() {
		return new JisekiCategoryList();
	}

	/**
	 * 指定番号のリストをコピーする。
	 *
	 * @param from
	 *            int
	 * @param to
	 *            int
	 *
	 * @return リストのコピー
	 */
	@Override
	public KitList copy(int from, int to) {
		JisekiCategoryList list = (JisekiCategoryList) super.copy(from,to);
		return list;
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord() {
		return new JisekiCategoryListRecord();
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @param record
	 *            HashMap
	 *
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord(HashMap<String, Object> record) {
		return new JisekiCategoryListRecord(record);
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @param record
	 *            KitRecord
	 *
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord(KitRecord record) {
		return new JisekiCategoryListRecord((JisekiCategoryListRecord) record);
	}
} // -- class

