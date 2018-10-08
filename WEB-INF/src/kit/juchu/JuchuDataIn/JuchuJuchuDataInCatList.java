package kit.juchu.JuchuDataIn;

import java.util.HashMap;

import fb.inf.KitList;
import fb.inf.KitRecord;
import fb.inf.pbs.PbsRecord;

/**
 * 受注入力（予定出荷先別商品カテゴリデータ）専用のリスト構造を提供する。
 */
public class JuchuJuchuDataInCatList extends KitList {

	/** シリアルID */
	private static final long serialVersionUID = -3229451484271523045L;

	/**
	 * コンストラクタ。
	 */
	public JuchuJuchuDataInCatList() {
		super();
	}

	/**
	 * コンストラクタ 指定された件数分空のレコードをセット
	 *
	 * @param initSize
	 */
	public JuchuJuchuDataInCatList(int initSize) {
		for (int ii = 0; ii < initSize; ii++) {
			this.add(this.createRecord());
		}
	}

	/**
	 * コンストラクタ。
	 *
	 * @param records PbsRecord[]
	 */
	public JuchuJuchuDataInCatList(PbsRecord[] records) {
		super(records);
	}

	/**
	 * コンストラクタ
	 *
	 * @param record JuchuJuchuDataInCatRecord
	 */
	public JuchuJuchuDataInCatList(JuchuJuchuDataInCatRecord record) {
		super(record);
	}


	/**
	 * 新規リストを作成し返却する。
	 *
	 * @return 新規リスト
	 */
	@Override
	public KitList createList() {
		return new JuchuJuchuDataInCatList();
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
		JuchuJuchuDataInCatList list = (JuchuJuchuDataInCatList) super.copy(from, to);
		return list;
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord() {
		return new JuchuJuchuDataInCatRecord();
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @param record HashMap
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord(HashMap<String, Object> record) {
		return new JuchuJuchuDataInCatRecord(record);
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @param record KitRecord
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord(KitRecord record) {
		return new JuchuJuchuDataInCatRecord((JuchuJuchuDataInCatRecord) record);
	}

	/**
	 * 受注カテゴリ行NO.を設定する。
	 */
	public void setShnCtgrLineNos() {
		int idx = 0;
		JuchuJuchuDataInCatRecord rec = null;
		for (PbsRecord tmp : this) {
			idx++;
			rec = (JuchuJuchuDataInCatRecord) tmp;
			rec.setShnCtgrLineNo(Integer.toString(idx));
		}
	}

}	// -- class
