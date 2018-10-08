package kit.kurac.KuraChokuDataIn;

import static fb.com.IKitComConst.*;
import static fb.com.IKitComConstHM.*;
import static fb.inf.pbs.IPbsConst.*;

import java.util.HashMap;

import fb.inf.KitList;
import fb.inf.KitRecord;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 蔵直入力（受注データ／ヘッダー部）専用のリスト構造を提供する。
 */
public class KuracKuraChokuDataInList extends KitList {

	/**
	 *
	 */

	/** シリアルID */
	private static final long serialVersionUID = -992960815016836642L;

	/**
	 * コンストラクタ。
	 */
	public KuracKuraChokuDataInList() {
		super();
	}

	/**
	 * コンストラクタ 指定された件数分空のレコードをセット
	 *
	 * @param initSize
	 */
	public KuracKuraChokuDataInList(int initSize) {
		for (int ii = 0; ii < initSize; ii++) {
			this.add(this.createRecord());
		}
	}

	/**
	 * コンストラクタ。
	 *
	 * @param records PbsRecord[]
	 */
	public KuracKuraChokuDataInList(PbsRecord[] records) {
		super(records);
	}

	/**
	 * コンストラクタ
	 *
	 * @param record JuchuJuchuDataInRecord
	 */
	public KuracKuraChokuDataInList(KuracKuraChokuDataInRecord record) {
		super(record);
	}


	/**
	 * 新規リストを作成し返却する。
	 *
	 * @return 新規リスト
	 */
	@Override
	public KitList createList() {
		return new KuracKuraChokuDataInList();
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
		KuracKuraChokuDataInList list = (KuracKuraChokuDataInList) super.copy(from, to);
		return list;
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord() {
		return new KuracKuraChokuDataInRecord();
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @param record HashMap
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord(HashMap<String, Object> record) {
		return new KuracKuraChokuDataInRecord(record);
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @param record KitRecord
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord(KitRecord record) {
		return new KuracKuraChokuDataInRecord((KuracKuraChokuDataInRecord) record);
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

		KuracKuraChokuDataInRecord rec = getRecByCode(keys[0]);
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
	public KuracKuraChokuDataInRecord getRecByCode(String kuradataNo){

		KuracKuraChokuDataInRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (KuracKuraChokuDataInRecord) tmp;
			if (!PbsUtil.isEqual(kuradataNo, rec.getKuradataNo())) {
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
		KuracKuraChokuDataInRecord rec = (KuracKuraChokuDataInRecord) this.getRecSelectedRow(selectedRowId);
		rec.setModified(IS_MODIFIED_TRUE);
	}


	/**
	 * リクエストに応じたリストに成型する
	 *
	 * @param reqType
	 */
	public void format(String reqType){

		// 抹消、復活、参照追加、訂正(+), 訂正(－)以外は、スキップ
		if (!PbsUtil.isIncluded(reqType, REQ_TYPE_DELETE, REQ_TYPE_REBIRTH, REQ_TYPE_REFERENCE,
				REQ_TYPE_REFPLUS, REQ_TYPE_REFMINUS)) {
			return;
		}

		// 参照追加、訂正(+), 訂正(－)は、追加モードフラグを立てる
		if (PbsUtil.isIncluded(reqType, REQ_TYPE_REFERENCE,
				REQ_TYPE_REFPLUS, REQ_TYPE_REFMINUS)) {
			setAddMode();
		}

		KuracKuraChokuDataInRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (KuracKuraChokuDataInRecord) tmp;
			// 抹消
			if (PbsUtil.isEqual(reqType, REQ_TYPE_DELETE)) {
				rec.setDelete(RECORD_DELETE_ON);
				rec.setRiyouKbn(AVAILABLE_KB_RIYO_TEISI); 		// 利用区分（利用不可）
			}
			// 参照追加
			if (PbsUtil.isEqual(reqType, REQ_TYPE_REFERENCE)) {
				rec.setRiyouKbn(AVAILABLE_KB_RIYO_KA); 			// 利用区分（利用可）
				rec.setSyubetuCd(KURAC_DATA_KB_TUJYO);			// データ種別CD（通常）
				rec.setTeiseiKuradataNo(CHAR_BLANK);			// 訂正時訂正元蔵直ﾃﾞｰﾀ連番
			}
			// 訂正(+)
			if (PbsUtil.isEqual(reqType, REQ_TYPE_REFPLUS)) {
				rec.setRiyouKbn(AVAILABLE_KB_RIYO_KA); 			// 利用区分（利用可）
				rec.setSyubetuCd(KURAC_DATA_KB_TEISEIPLUS);		//  "5":訂正(+)
			}
			// 訂正(-)
			if (PbsUtil.isEqual(reqType, REQ_TYPE_REFMINUS)) {
				rec.setRiyouKbn(AVAILABLE_KB_RIYO_KA); 			// 利用区分（利用可）
				rec.setSyubetuCd(KURAC_DATA_KB_TEISEIMINUS);	//  "6":訂正(-)
			}
		}

	}

	/**
	 * ヘッダー部の付加情報を設定する
	 *
	 * @param db_
	 * @param checkServ
	 * @param kaisyaCd
	 * @param isShortName
	 */
	public void setHdInfo() {

		KuracKuraChokuDataInRecord hdRec = (KuracKuraChokuDataInRecord)this.get(0);
		// メーカNoを設定
		StringBuffer makerNo = new StringBuffer();

		// 黄桜事業所区分
		makerNo.append(hdRec.getJigyosyoKbn());
		makerNo.append(PbsUtil.HYPHEN);
		// 蔵直商品ｸﾞﾙｰﾌﾟCD
		makerNo.append(hdRec.getShoninGrpCd());
		makerNo.append(PbsUtil.HYPHEN);
		// ﾃﾞｰﾀ種別CD
		makerNo.append(hdRec.getSyubetuCd());
		makerNo.append(PbsUtil.HYPHEN);
		// 整理No
		makerNo.append(hdRec.getKuradenNo());
		makerNo.append(PbsUtil.HYPHEN);
		// 届先No
		makerNo.append(hdRec.getTodokesakiLineNo());

		// [黄桜事業所区分]+[蔵直商品ｸﾞﾙｰﾌﾟCD]+【ﾃﾞｰﾀ種別CD】+[整理No]+[届先No]
		hdRec.setMakerNo(makerNo.toString());
	}

}	// -- class
