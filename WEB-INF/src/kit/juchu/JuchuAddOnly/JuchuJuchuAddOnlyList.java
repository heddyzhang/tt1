package kit.juchu.JuchuAddOnly;

import static fb.com.IKitComConst.*;
import static fb.com.IKitComConstHM.*;
import static fb.inf.pbs.IPbsConst.*;

import java.util.HashMap;

import fb.com.ComDecode;
import fb.inf.KitList;
import fb.inf.KitRecord;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 受付専用受注追加入力（受注データ／ヘッダー部）専用のリスト構造を提供する。
 */
public class JuchuJuchuAddOnlyList extends KitList {

	/** シリアルID */
	private static final long serialVersionUID = -1754752736459088521L;

	/**
	 * コンストラクタ。
	 */
	public JuchuJuchuAddOnlyList() {
		super();
	}

	/**
	 * コンストラクタ 指定された件数分空のレコードをセット
	 *
	 * @param initSize
	 */
	public JuchuJuchuAddOnlyList(int initSize) {
		for (int ii = 0; ii < initSize; ii++) {
			this.add(this.createRecord());
		}
	}

	/**
	 * コンストラクタ。
	 *
	 * @param records PbsRecord[]
	 */
	public JuchuJuchuAddOnlyList(PbsRecord[] records) {
		super(records);
	}

	/**
	 * コンストラクタ
	 *
	 * @param record JuchuJuchuAddOnlyRecord
	 */
	public JuchuJuchuAddOnlyList(JuchuJuchuAddOnlyRecord record) {
		super(record);
	}


	/**
	 * 新規リストを作成し返却する。
	 *
	 * @return 新規リスト
	 */
	@Override
	public KitList createList() {
		return new JuchuJuchuAddOnlyList();
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
		JuchuJuchuAddOnlyList list = (JuchuJuchuAddOnlyList) super.copy(from, to);
		return list;
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord() {
		return new JuchuJuchuAddOnlyRecord();
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @param record HashMap
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord(HashMap<String, Object> record) {
		return new JuchuJuchuAddOnlyRecord(record);
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @param record KitRecord
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord(KitRecord record) {
		return new JuchuJuchuAddOnlyRecord((JuchuJuchuAddOnlyRecord) record);
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

		JuchuJuchuAddOnlyRecord rec = getRecByCode(keys[0]);
		if (rec != null) {
			rec.setModified(IS_MODIFIED_TRUE);
		}

	}

	/**
	 * コードを指定してレコードを取得する
	 *
	 * @param juchuNo
	 * @return JuchuJuchuAddOnlyRecord
	 */
	public JuchuJuchuAddOnlyRecord getRecByCode(String juchuNo){

		JuchuJuchuAddOnlyRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuJuchuAddOnlyRecord) tmp;
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
		JuchuJuchuAddOnlyRecord rec = (JuchuJuchuAddOnlyRecord) this.getRecSelectedRow(selectedRowId);
		rec.setModified(IS_MODIFIED_TRUE);
	}


	/**
	 * リクエストに応じたリストに成型する
	 *
	 * @param reqType
	 */
	public void format(String reqType){

		// 抹消、復活、参照追加以外は、スキップ
		if (!PbsUtil.isIncluded(reqType, REQ_TYPE_DELETE, REQ_TYPE_REBIRTH, REQ_TYPE_REFERENCE)) {
			return;
		}

		// 参照追加は、追加モードフラグを立てる
		if (PbsUtil.isIncluded(reqType, REQ_TYPE_REFERENCE)) {
			setAddMode();
		}

		JuchuJuchuAddOnlyRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuJuchuAddOnlyRecord) tmp;
			// 抹消
			if (PbsUtil.isEqual(reqType, REQ_TYPE_DELETE)) {
				rec.setDelete(RECORD_DELETE_ON);
				rec.setRiyouKbn(AVAILABLE_KB_RIYO_TEISI); // 利用区分（利用不可）
				rec.setSyubetuCd(JDATAKIND_KB_JYUCYU_CANCEL);// データ種別CD（受注キャンセル）
			}
			// 復活
			if (PbsUtil.isEqual(reqType, REQ_TYPE_REBIRTH)) {
				rec.setRiyouKbn(AVAILABLE_KB_RIYO_KA); // 利用区分（利用可）
			}
			// 参照追加
			if (PbsUtil.isEqual(reqType, REQ_TYPE_REFERENCE)) {
				rec.setRiyouKbn(AVAILABLE_KB_RIYO_KA); // 利用区分（利用可）
				rec.setSyubetuCd(JDATAKIND_KB_TUJO);// データ種別CD（通常）
				rec.setJyucyuNo(CHAR_BLANK); // 受注NO（値をクリア）
				rec.setJyucyuNoFormatted(CHAR_BLANK); // 整形済み受注NO（値をクリア）
			}
		}

	}

	/**
	 * 整形済み受注NOを設定する。
	 *
	 * @param checkServ
	 */
	public void setFormatJyucyuNo(JuchuJuchuAddOnly_CheckService checkServ) {
		JuchuJuchuAddOnlyRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuJuchuAddOnlyRecord) tmp;
			rec.setJyucyuNoFormatted(checkServ.formatJyucyuNo(rec.getJyucyuNo()));
		}
	}

	/**
	 * 入力者名を設定する。
	 *
	 * @param db_
	 * @param kaisyaCd
	 */
	public void setNyuryokusyaNm(PbsDatabase db_, String kaisyaCd) {
		JuchuJuchuAddOnlyRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuJuchuAddOnlyRecord) tmp;
			rec.setNyuryokusyaNm(ComDecode.getSgyosyaNm(db_, kaisyaCd, rec.getNyuryokusyaCd()));
		}
	}

	/**
	 * 最終送荷先卸名を設定する。
	 *
	 * @param checkServ
	 * @param kaisyaCd
	 * @param isShortName
	 */
	public void setOrositenNmLast(JuchuJuchuAddOnly_CheckService checkServ, String kaisyaCd, boolean isShortName) {
		JuchuJuchuAddOnlyRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuJuchuAddOnlyRecord) tmp;
			rec.setOrositenNmLast(checkServ.getOrositenNmByCd(rec.getOrositenCdLast(), kaisyaCd, isShortName, true));
		}
	}

	/**
	 * 特約店名を設定する。
	 *
	 * @param checkServ
	 * @param kaisyaCd
	 * @param isShortName
	 */
	public void setTokuyakutenNm(JuchuJuchuAddOnly_CheckService checkServ, String kaisyaCd, boolean isShortName) {
		JuchuJuchuAddOnlyRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuJuchuAddOnlyRecord) tmp;
			rec.setTokuyakutenNm(checkServ.getOrositenNmByCd(rec.getTokuyakutenCd(), kaisyaCd, isShortName, true));
		}
	}

	/**
	 * 運送店名を設定する。
	 *
	 * @param checkServ
	 * @param kaisyaCd
	 * @param isShortName
	 */
	public void setUnsotenNm(JuchuJuchuAddOnly_CheckService checkServ, String kaisyaCd, boolean isShortName) {
		JuchuJuchuAddOnlyRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuJuchuAddOnlyRecord) tmp;
			rec.setUnsotenNm(checkServ.getUnsotenNmByCd(rec.getUnsotenCd(), kaisyaCd, isShortName, true));
		}
	}

	/**
	 * マスター上の運送店名（通常）を設定する。
	 *
	 * @param checkServ
	 * @param kaisyaCd
	 * @param isShortName
	 */
	public void setUnsotenNmTujyo(JuchuJuchuAddOnly_CheckService checkServ, String kaisyaCd, boolean isShortName) {
		JuchuJuchuAddOnlyRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuJuchuAddOnlyRecord) tmp;
			rec.setUnsotenNmTujyo(checkServ.getUnsotenNmByCd(rec.getUnsotenCd(), kaisyaCd, isShortName, true));
		}
	}

	/**
	 * マスター上の運送店名（当日）を設定する。
	 *
	 * @param checkServ
	 * @param kaisyaCd
	 * @param isShortName
	 */
	public void setUnsotenNmToujitu(JuchuJuchuAddOnly_CheckService checkServ, String kaisyaCd, boolean isShortName) {
		JuchuJuchuAddOnlyRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuJuchuAddOnlyRecord) tmp;
			rec.setUnsotenNmToujitu(checkServ.getUnsotenNmByCd(rec.getUnsotenCd(), kaisyaCd, isShortName, true));
		}
	}

	/**
	 * マスター上の運送店名（引取）を設定する。
	 *
	 * @param checkServ
	 * @param kaisyaCd
	 * @param isShortName
	 */
	public void setUnsotenNmHikitori(JuchuJuchuAddOnly_CheckService checkServ, String kaisyaCd, boolean isShortName) {
		JuchuJuchuAddOnlyRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuJuchuAddOnlyRecord) tmp;
			rec.setUnsotenNmHikitori(checkServ.getUnsotenNmByCd(rec.getUnsotenCd(), kaisyaCd, isShortName, true));
		}
	}

	/**
	 * ヘッダー部の付加情報を設定する。
	 *
	 * @param db_
	 * @param checkServ
	 * @param kaisyaCd
	 * @param isShortName
	 */
	public void setHdInfo(PbsDatabase db_, JuchuJuchuAddOnly_CheckService checkServ, String kaisyaCd, boolean isShortName) {
		// 整形済み受注NO
		this.setFormatJyucyuNo(checkServ); // 受注NOをフォーマット

// 以下、SQLに変更
//		// 入力者名を設定
//		this.setNyuryokusyaNm(db_, kaisyaCd);
//
//		// 最終送荷先卸名を設定
//		this.setOrositenNmLast(checkServ, kaisyaCd, isShortName); // 略称
//
//		// 特約店名を設定
//		this.setTokuyakutenNm(checkServ, kaisyaCd, isShortName); // 略称
//
//		// 運送店名を設定
//		this.setUnsotenNm(checkServ, kaisyaCd, isShortName); // 略称
//
//		// マスター上の運送店名（通常）を設定
//		this.setUnsotenNmTujyo(checkServ, kaisyaCd, isShortName); // 略称
//
//		// マスター上の運送店名（当日）を設定
//		this.setUnsotenNmToujitu(checkServ, kaisyaCd, isShortName); // 略称
//
//		// マスター上の運送店名（引取）を設定
//		this.setUnsotenNmHikitori(checkServ, kaisyaCd, isShortName); // 略称
	}

}	// -- class
