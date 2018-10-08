package kit.jiseki.AriaList;

import java.util.HashMap;

import fb.inf.KitList;
import fb.inf.KitRecord;
import fb.inf.pbs.PbsRecord;

/**
 * エリア別販売実績 専用のリスト構造を提供する。
 */
public class JisekiAriaList extends KitList {


	/**
	 *
	 */
	private static final long serialVersionUID = 6493686401856374353L;

	/**
	 * コンストラクタ。
	 */
	public JisekiAriaList() {
		super();
	}

	/**
	 * コンストラクタ 指定された件数分空のレコードをセット
	 *
	 * @param initSize
	 */
	public JisekiAriaList(int initSize) {
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
	public JisekiAriaList(PbsRecord[] records) {
		super(records);
	}

	/**
	 * コンストラクタJisekiNenpouRecord()
	 * @param record
	 */
	public JisekiAriaList(JisekiAriaListRecord record) {
		super(record);
	}


	/**
	 * 新規リストを作成し返却する。
	 *
	 * @return 新規リスト
	 */
	@Override
	public KitList createList() {
		return new JisekiAriaList();
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
		JisekiAriaList list = (JisekiAriaList) super.copy(from,to);
		return list;
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord() {
		return new JisekiAriaListRecord();
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
		return new JisekiAriaListRecord(record);
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
		return new JisekiAriaListRecord((JisekiAriaListRecord) record);
	}
} // -- class

