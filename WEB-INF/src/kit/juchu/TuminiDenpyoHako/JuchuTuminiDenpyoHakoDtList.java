package kit.juchu.TuminiDenpyoHako;

import java.util.HashMap;

import fb.inf.KitList;
import fb.inf.KitRecord;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 積荷データ／DT部のリストクラス
 */
public class JuchuTuminiDenpyoHakoDtList extends KitList {

	/** シリアルID */
	private static final long serialVersionUID = 5619627631500240844L;

	/**
	 * コンストラクタ。
	 */
	public JuchuTuminiDenpyoHakoDtList() {
		super();
	}

	/**
	 * コンストラクタ 指定された件数分空のレコードをセット
	 *
	 * @param initSize
	 */
	public JuchuTuminiDenpyoHakoDtList(int initSize) {
		for (int ii = 0; ii < initSize; ii++) {
			this.add(this.createRecord());
		}
	}

	/**
	 * コンストラクタ。
	 *
	 * @param records PbsRecord[]
	 */
	public JuchuTuminiDenpyoHakoDtList(PbsRecord[] records) {
		super(records);
	}

	/**
	 * コンストラクタ
	 *
	 * @param record JuchuTuminiDenpyoHakoDtRecord
	 */
	public JuchuTuminiDenpyoHakoDtList(JuchuTuminiDenpyoHakoDtRecord record) {
		super(record);
	}


	/**
	 * 新規リストを作成し返却する。
	 *
	 * @return 新規リスト
	 */
	@Override
	public KitList createList() {
		return new JuchuTuminiDenpyoHakoDtList();
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
		JuchuTuminiDenpyoHakoDtList list = (JuchuTuminiDenpyoHakoDtList) super.copy(from, to);
		return list;
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord() {
		return new JuchuTuminiDenpyoHakoDtRecord();
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @param record HashMap
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord(HashMap<String, Object> record) {
		return new JuchuTuminiDenpyoHakoDtRecord(record);
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @param record KitRecord
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord(KitRecord record) {
		return new JuchuTuminiDenpyoHakoDtRecord((JuchuTuminiDenpyoHakoDtRecord) record);
	}


	/**
	 * レコードNOを設定する。
	 */
	public void setInputLineNos() {
		int idx = 0;
		JuchuTuminiDenpyoHakoDtRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuTuminiDenpyoHakoDtRecord) tmp;
			// 受注NOがある場合のみ
			if (!PbsUtil.isEmpty(rec.getJyucyuNo())) {
				idx++;
				rec.setInputLineNo(Integer.toString(idx));
			}
		}
	}


	/**
	 * 積荷伝票No.を設定する。
	 *
	 * @param denNo
	 */
	public void setTumidenNos(String denNo) {
		JuchuTuminiDenpyoHakoDtRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuTuminiDenpyoHakoDtRecord) tmp;
			// 受注NOがある場合のみ
			if (!PbsUtil.isEmpty(rec.getJyucyuNo())) {
				rec.setTumidenNo(denNo);
			}
		}
	}

	/**
	 * 積み残しフラグを設定する。
	 *
	 * @param tumidenList
	 */
	public void setTuminokosiFlgs(JuchuTuminiDenpyoHakoList tumidenList) {
		JuchuTuminiDenpyoHakoDtRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuTuminiDenpyoHakoDtRecord) tmp;
			// 受注NOがある場合のみ
			if (!PbsUtil.isEmpty(rec.getJyucyuNo())) {
				for (PbsRecord pTmp : tumidenList) {
					JuchuTuminiDenpyoHakoRecord pRec = (JuchuTuminiDenpyoHakoRecord) pTmp;
					if (PbsUtil.isEqual(rec.getJyucyuNo(), pRec.getJyucyuNo())) {
						rec.setTuminokosiFlg(pRec.getTuminokosiFlg());
					}
				}
			}
		}
	}


}	// -- class
