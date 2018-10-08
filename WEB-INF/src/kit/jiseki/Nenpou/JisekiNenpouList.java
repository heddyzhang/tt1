package kit.jiseki.Nenpou;

import static fb.com.IKitComConst.*;
import static fb.com.IKitComConstHM.*;
import static fb.inf.pbs.IPbsConst.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fb.com.ComDecode;
import fb.inf.KitList;
import fb.inf.KitRecord;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 【販売実績】年報 専用のリスト構造を提供する。
 */
public class JisekiNenpouList extends KitList {


	/** シリアルID */
	private static final long serialVersionUID = 3104038151421015798L;

	/**
	 * コンストラクタ。
	 */
	public JisekiNenpouList() {
		super();
	}

	/**
	 * コンストラクタ 指定された件数分空のレコードをセット
	 *
	 * @param initSize
	 */
	public JisekiNenpouList(int initSize) {
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
	public JisekiNenpouList(PbsRecord[] records) {
		super(records);
	}

	/**
	 * コンストラクタJisekiNenpouRecord()
	 * @param record
	 */
	public JisekiNenpouList(JisekiNenpouRecord record) {
		super(record);
	}


	/**
	 * 新規リストを作成し返却する。
	 *
	 * @return 新規リスト
	 */
	@Override
	public KitList createList() {
		return new JisekiNenpouList();
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
		JisekiNenpouList list = (JisekiNenpouList) super.copy(from,to);
		return list;
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord() {
		return new JisekiNenpouRecord();
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
		return new JisekiNenpouRecord(record);
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
		return new JisekiNenpouRecord((JisekiNenpouRecord) record);
	}


///**
//	 * リクエストに応じたリストに成型する
//	 * @param reqType
//	 */
//	public void formatAdd(String reqType){
//
//		JisekiNenpouRecord rec = null;
//		for(PbsRecord tmp:this){
//			rec = (JisekiNenpouRecord) tmp;
//			// 新規
//			if(PbsUtil.isEqual(reqType, REQ_TYPE_ADD)){
//
//				// 利用区分を「利用可」にする(初期表示)
//				rec.setRiyouKbn(AVAILABLE_KB_RIYO_KA);
//			}
//		}
//	}
//
//	/**
//	 * リクエストに応じたリストに成型する
//	 * @param reqType
//	 */
//	public void format(String reqType){
//
//		// 抹消、参照追加以外は、スキップ
//		if(!PbsUtil.isIncluded(reqType
//			, REQ_TYPE_DELETE, REQ_TYPE_REFERENCE))return;
//
//		// 参照追加は、追加モードフラグを立てる
//		if(PbsUtil.isIncluded(reqType
//				, REQ_TYPE_REFERENCE)){
//			setAddMode();
//		}
//
//
//		JisekiNenpouRecord rec = null;
//		for(PbsRecord tmp:this){
//			rec = (JisekiNenpouRecord) tmp;
//			// 抹消
//			if(PbsUtil.isEqual(reqType, REQ_TYPE_DELETE)){
//				rec.setDelete(RECORD_DELETE_ON);
//			}
//			// 参照追加
//			if(PbsUtil.isEqual(reqType, REQ_TYPE_REFERENCE)){
//
//				// 特約店コードを空にする
//				rec.setTokuyakutenCd(CHAR_BLANK);
//
//				// 利用区分を「利用可」にする(初期表示)
//				rec.setRiyouKbn(AVAILABLE_KB_RIYO_KA);
//
//				// 日付３兄弟を空にする
//				rec.setTorihikiKaisiDt(CHAR_BLANK);
//				rec.setTorihikiSyuryoDt(CHAR_BLANK);
//				rec.setRiyouTeisiDt(CHAR_BLANK);
//			}
//			// 新規
//			if(PbsUtil.isEqual(reqType, REQ_TYPE_ADD)){
//
//				// 利用区分を「利用可」にする(初期表示)
//				rec.setRiyouKbn(AVAILABLE_KB_RIYO_KA);
//			}
//		}
//	}




} // -- class

