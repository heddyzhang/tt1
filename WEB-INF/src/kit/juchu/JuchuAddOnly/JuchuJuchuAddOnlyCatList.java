package kit.juchu.JuchuAddOnly;

import java.util.HashMap;

import fb.inf.KitList;
import fb.inf.KitRecord;
import fb.inf.pbs.PbsRecord;

/**
 * 受付専用受注追加入力（予定出荷先別商品カテゴリデータ）専用のリスト構造を提供する。
 */
public class JuchuJuchuAddOnlyCatList extends KitList {

	/** シリアルID */
	private static final long serialVersionUID = -2272888665767366535L;

	/**
	 * コンストラクタ。
	 */
	public JuchuJuchuAddOnlyCatList() {
		super();
	}

	/**
	 * コンストラクタ 指定された件数分空のレコードをセット
	 *
	 * @param initSize
	 */
	public JuchuJuchuAddOnlyCatList(int initSize) {
		for (int ii = 0; ii < initSize; ii++) {
			this.add(this.createRecord());
		}
	}

	/**
	 * コンストラクタ。
	 *
	 * @param records PbsRecord[]
	 */
	public JuchuJuchuAddOnlyCatList(PbsRecord[] records) {
		super(records);
	}

	/**
	 * コンストラクタ
	 *
	 * @param record JuchuJuchuAddOnlyCatRecord
	 */
	public JuchuJuchuAddOnlyCatList(JuchuJuchuAddOnlyCatRecord record) {
		super(record);
	}


	/**
	 * 新規リストを作成し返却する。
	 *
	 * @return 新規リスト
	 */
	@Override
	public KitList createList() {
		return new JuchuJuchuAddOnlyCatList();
	}

	/**
	 * 指定番号のリストをコピーする。
	 *
	 * @param from int
	 * @param to int
	 * @return リストのコピー
	 */
	@Override
	public KitList copy(int from, int to) {
		JuchuJuchuAddOnlyCatList list = (JuchuJuchuAddOnlyCatList) super.copy(from, to);
		return list;
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord() {
		return new JuchuJuchuAddOnlyCatRecord();
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @param record HashMap
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord(HashMap<String, Object> record) {
		return new JuchuJuchuAddOnlyCatRecord(record);
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @param record KitRecord
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord(KitRecord record) {
		return new JuchuJuchuAddOnlyCatRecord((JuchuJuchuAddOnlyCatRecord) record);
	}

	/**
	 * 受注カテゴリ行NO.を設定する。
	 */
	public void setShnCtgrLineNos() {
		int idx = 0;
		JuchuJuchuAddOnlyCatRecord rec = null;
		for (PbsRecord tmp : this) {
			idx++;
			rec = (JuchuJuchuAddOnlyCatRecord) tmp;
			rec.setShnCtgrLineNo(Integer.toString(idx));
		}
	}

}	// -- class
