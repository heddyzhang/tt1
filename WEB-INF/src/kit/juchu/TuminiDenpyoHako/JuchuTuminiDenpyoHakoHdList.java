package kit.juchu.TuminiDenpyoHako;

import java.util.HashMap;

import fb.inf.KitList;
import fb.inf.KitRecord;
import fb.inf.pbs.PbsRecord;

/**
 * 積荷データ／ヘッダー部専用のリスト構造を提供する。
 */
public class JuchuTuminiDenpyoHakoHdList extends KitList {

	/** シリアルID */
	private static final long serialVersionUID = -5527554483667003067L;

	/**
	 * コンストラクタ。
	 */
	public JuchuTuminiDenpyoHakoHdList() {
		super();
	}

	/**
	 * コンストラクタ 指定された件数分空のレコードをセット
	 *
	 * @param initSize
	 */
	public JuchuTuminiDenpyoHakoHdList(int initSize) {
		for (int ii = 0; ii < initSize; ii++) {
			this.add(this.createRecord());
		}
	}

	/**
	 * コンストラクタ。
	 *
	 * @param records PbsRecord[]
	 */
	public JuchuTuminiDenpyoHakoHdList(PbsRecord[] records) {
		super(records);
	}

	/**
	 * コンストラクタ
	 *
	 * @param record JuchuTuminiDenpyoHakoHdRecord
	 */
	public JuchuTuminiDenpyoHakoHdList(JuchuTuminiDenpyoHakoHdRecord record) {
		super(record);
	}


	/**
	 * 新規リストを作成し返却する。
	 *
	 * @return 新規リスト
	 */
	@Override
	public KitList createList() {
		return new JuchuTuminiDenpyoHakoHdList();
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
		JuchuTuminiDenpyoHakoHdList list = (JuchuTuminiDenpyoHakoHdList) super.copy(from, to);
		return list;
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord() {
		return new JuchuTuminiDenpyoHakoHdRecord();
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @param record HashMap
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord(HashMap<String, Object> record) {
		return new JuchuTuminiDenpyoHakoHdRecord(record);
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @param record KitRecord
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord(KitRecord record) {
		return new JuchuTuminiDenpyoHakoHdRecord((JuchuTuminiDenpyoHakoHdRecord) record);
	}

}	// -- class
