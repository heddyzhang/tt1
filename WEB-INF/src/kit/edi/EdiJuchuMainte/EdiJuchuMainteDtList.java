package kit.edi.EdiJuchuMainte;

import java.util.HashMap;

import fb.inf.KitList;
import fb.inf.KitRecord;
import fb.inf.pbs.PbsRecord;

/**
 * EDI受発注受信データ処理（EDI受発注データ／ﾃﾞｨﾃｰﾙ部）専用のリスト構造を提供する。
 */
public class EdiJuchuMainteDtList extends KitList {

	/** シリアルID */
	private static final long serialVersionUID = 1538248038854186123L;

	/**
	 * コンストラクタ。
	 */
	public EdiJuchuMainteDtList() {
		super();
	}

	/**
	 * コンストラクタ 指定された件数分空のレコードをセット
	 *
	 * @param initSize
	 */
	public EdiJuchuMainteDtList(int initSize) {
		for (int ii = 0; ii < initSize; ii++) {
			this.add(this.createRecord());
		}
	}

	/**
	 * コンストラクタ。
	 *
	 * @param records PbsRecord[]
	 */
	public EdiJuchuMainteDtList(PbsRecord[] records) {
		super(records);
	}

	/**
	 * コンストラクタ
	 *
	 * @param record JuchuJuchuDataInDtRecord
	 */
	public EdiJuchuMainteDtList(EdiJuchuMainteDtRecord record) {
		super(record);
	}


	/**
	 * 新規リストを作成し返却する。
	 *
	 * @return 新規リスト
	 */
	@Override
	public KitList createList() {
		return new EdiJuchuMainteDtList();
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
		EdiJuchuMainteDtList list = (EdiJuchuMainteDtList) super.copy(from, to);
		return list;
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord() {
		return new EdiJuchuMainteDtRecord();
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @param record HashMap
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord(HashMap<String, Object> record) {
		return new EdiJuchuMainteDtRecord(record);
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @param record KitRecord
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord(KitRecord record) {
		return new EdiJuchuMainteDtRecord((EdiJuchuMainteDtRecord) record);
	}

}	// -- class
