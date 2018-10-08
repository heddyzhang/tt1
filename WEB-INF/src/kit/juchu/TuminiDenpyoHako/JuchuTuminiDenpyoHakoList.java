package kit.juchu.TuminiDenpyoHako;

import static fb.com.IKitComConstHM.*;

import java.util.HashMap;

import fb.com.ComUtil;
import fb.inf.KitList;
import fb.inf.KitRecord;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 積荷伝票発行
 * 　受注データ／HD部のリストクラス
 */
public class JuchuTuminiDenpyoHakoList extends KitList {

	/** シリアルID */
	private static final long serialVersionUID = -6459150732920610915L;

	/**
	 * コンストラクタ。
	 */
	public JuchuTuminiDenpyoHakoList() {
		super();
	}

	/**
	 * コンストラクタ 指定された件数分空のレコードをセット
	 *
	 * @param initSize
	 */
	public JuchuTuminiDenpyoHakoList(int initSize) {
		for (int ii = 0; ii < initSize; ii++) {
			this.add(this.createRecord());
		}
	}

	/**
	 * コンストラクタ。
	 *
	 * @param records PbsRecord[]
	 */
	public JuchuTuminiDenpyoHakoList(PbsRecord[] records) {
		super(records);
	}

	/**
	 * コンストラクタ
	 *
	 * @param record JuchuTuminiDenpyoHakoRecord
	 */
	public JuchuTuminiDenpyoHakoList(JuchuTuminiDenpyoHakoRecord record) {
		super(record);
	}


	/**
	 * 新規リストを作成し返却する。
	 *
	 * @return 新規リスト
	 */
	@Override
	public KitList createList() {
		return new JuchuTuminiDenpyoHakoList();
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
		JuchuTuminiDenpyoHakoList list = (JuchuTuminiDenpyoHakoList) super.copy(from, to);
		return list;
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord() {
		return new JuchuTuminiDenpyoHakoRecord();
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @param record HashMap
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord(HashMap<String, Object> record) {
		return new JuchuTuminiDenpyoHakoRecord(record);
	}

	/**
	 * 新規レコードを作成する。
	 *
	 * @param record KitRecord
	 * @return レコード
	 */
	@Override
	public KitRecord createRecord(KitRecord record) {
		return new JuchuTuminiDenpyoHakoRecord((JuchuTuminiDenpyoHakoRecord) record);
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

		JuchuTuminiDenpyoHakoRecord rec = getRecByCode(keys[0]);
		if (rec != null) {
			rec.setModified(IS_MODIFIED_TRUE);
		}

	}

	/**
	 * コードを指定してレコードを取得する
	 *
	 * @param juchuNo
	 * @return JuchuTuminiDenpyoHakoRecord
	 */
	public JuchuTuminiDenpyoHakoRecord getRecByCode(String juchuNo){

		JuchuTuminiDenpyoHakoRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuTuminiDenpyoHakoRecord) tmp;
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
		JuchuTuminiDenpyoHakoRecord rec = (JuchuTuminiDenpyoHakoRecord) this.getRecSelectedRow(selectedRowId);
		rec.setModified(IS_MODIFIED_TRUE);
	}


	/**
	 * チェックされたレコードに更新対象フラグをセットする
	 */
	public void setModifiedByChk() {
		// 初期化
		initFlgs();

		// チェック状態からフラグを更新
		JuchuTuminiDenpyoHakoRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuTuminiDenpyoHakoRecord) tmp;
			if (PbsUtil.isIncluded(rec.getDenpyoHakoFlg(), CHECKBOX_ON, CHECKBOX_TRUE, CHECKBOX_YES)) {
				rec.setModified(IS_MODIFIED_TRUE);
			} else {
				rec.setModified(IS_MODIFIED_FALSE);
			}
		}
	}


	/**
	 * 伝票発行対象リストを作成
	 *
	 * @return 伝票発行対象リスト
	 */
	public JuchuTuminiDenpyoHakoList getDenpyoHakoList() {
		JuchuTuminiDenpyoHakoList denpyoHakoList = new JuchuTuminiDenpyoHakoList();
		JuchuTuminiDenpyoHakoRecord rec = null;

		for (PbsRecord tmp : this) {
			rec = (JuchuTuminiDenpyoHakoRecord) tmp;
			if (PbsUtil.isIncluded(rec.getDenpyoHakoFlg(), CHECKBOX_ON, CHECKBOX_TRUE, CHECKBOX_YES)) {
				// 伝票発行フラグがチェックされているもの
				denpyoHakoList.add(rec);
			}
		}

		return denpyoHakoList;
	}


	/**
	 * 個別発行対象リストを作成
	 *
	 * @return 個別発行対象リスト
	 */
	public JuchuTuminiDenpyoHakoList getKobetuHakoList() {
		JuchuTuminiDenpyoHakoList kobetuList = new JuchuTuminiDenpyoHakoList();
		JuchuTuminiDenpyoHakoRecord rec = null;

		for (PbsRecord tmp : this) {
			rec = (JuchuTuminiDenpyoHakoRecord) tmp;
			if (PbsUtil.isIncluded(rec.getSyuyakuKbn(), CHECKBOX_ON, CHECKBOX_TRUE, CHECKBOX_YES)) {
				// 個別フラグがチェックされているもの
				kobetuList.add(rec);
			}
		}

		return kobetuList;
	}


	/**
	 * 集約発行対象リストを作成
	 *
	 * @return 集約発行対象リスト
	 */
	public JuchuTuminiDenpyoHakoList getSyuyakuHakoList() {
		JuchuTuminiDenpyoHakoList syuyakuList = new JuchuTuminiDenpyoHakoList();
		JuchuTuminiDenpyoHakoRecord rec = null;

		for (PbsRecord tmp : this) {
			rec = (JuchuTuminiDenpyoHakoRecord) tmp;
			if (!PbsUtil.isIncluded(rec.getSyuyakuKbn(), CHECKBOX_ON, CHECKBOX_TRUE, CHECKBOX_YES)) {
				// 個別フラグがチェックされていないもの
				syuyakuList.add(rec);
			}
		}

		return syuyakuList;
	}

	/**
	 * 受注NOのみの配列を作成する
	 *
	 * @param targetList
	 * @return 受注NOのみの配列
	 */
	public String[] getJyucyuNos(JuchuTuminiDenpyoHakoList targetList) {
		String[] jyucyuNos = new String[targetList.size()];
		for (int i = 0; i < targetList.size(); i++) {
			JuchuTuminiDenpyoHakoRecord rec = (JuchuTuminiDenpyoHakoRecord)targetList.get(i);
			jyucyuNos[i] = rec.getJyucyuNo();
		}
		return jyucyuNos;
	}

	/**
	 * 集約発行対象の受注NOをSQLのIN句に渡すカッコを作成・・・IN(?, ?, ... , ?)
	 *
	 * @return 集約発行対象の受注NOをSQLのIN句に渡すカッコ
	 */
	public String getJyucyuNoList(JuchuTuminiDenpyoHakoList syuyakuList) {
		String inStr = PbsUtil.getInClauseStr(getJyucyuNos(syuyakuList));
		return inStr;
	}


	/**
	 * 運送店CD単位の集約発行対象の受注NOをSQLのIN句に渡すカッコを作成・・・IN(?, ?, ... , ?)
	 *
	 * @return 運送店CD単位の集約発行対象の受注NOをSQLのIN句に渡すカッコ
	 */
	public String getJyucyuNoListByUnsotenCd(JuchuTuminiDenpyoHakoList syuyakuListByUnsotenCd) {
		String inStrU = PbsUtil.getInClauseStr(getJyucyuNos(syuyakuListByUnsotenCd));
		return inStrU;
	}


	/**
	 * 整形済み受注NOを設定する。
	 */
	public void setFormatJyucyuNo() {
		JuchuTuminiDenpyoHakoRecord rec = null;
		for (PbsRecord tmp : this) {
			rec = (JuchuTuminiDenpyoHakoRecord) tmp;
			rec.setJyucyuNoFormatted(ComUtil.getFormattedJuchuNo(rec.getJyucyuNo()));
		}
	}

	/**
	 * 伝票発行ボタンのタブインデックスを計算して取得する
	 *
	 * @return TabIndex
	 */
	public String getPrintBtnTabIndex() {

		// 通常の確認ボタン用インデックスを取得する
		String tmp = getConfirmBtnTabIndex();

		// ページ内全チェック分ひとつずらす
		return PbsUtil.sToSAddition(tmp, "1");
	}



}	// -- class
