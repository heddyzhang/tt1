package kit.jiseki.Nenpou;

import static fb.com.IKitComConstHM.*;

import java.util.HashMap;
import java.util.Map;

import fb.com.ComUtil;
import fb.inf.KitRecord;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsUtil;

/**
 * 【販売実績】年報のレコードクラス
 * @author nishikawa@kz
 * @version 2015/11/13
 *
 */
public class JisekiNenpouRecord extends KitRecord {

	/** シリアルID */
	private static final long serialVersionUID = -7293551814194835933L;

	/** デバッグ. */
	boolean isDebug_ = false;

	/** コンストラクタ */
	public JisekiNenpouRecord() {
		super();
		// マスタ排他:親に主キーのありかをセットする
		//setPrimaryKeys();

	}

	/**
	 * コンストラクタ。
	 *
	 * @param input
	 *            Map
	 */
	public JisekiNenpouRecord(Map<String, Object> input) {
		super(input);
		// マスタ排他:親に主キーのありかをセットする
		//setPrimaryKeys();

	}

	/**
	 * コンストラクタ。
	 *
	 * @param input
	 *            HashMap
	 */
	public JisekiNenpouRecord(HashMap<String, Object> input) {
		this((Map<String, Object>) input);
		// マスタ排他:親に主キーのありかをセットする
		//setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input
	 *            MstMeigaraRecord
	 */
	public JisekiNenpouRecord(JisekiNenpouRecord input) {
		super((KitRecord) input);
		// マスタ排他:親に主キーのありかをセットする
		//setPrimaryKeys();

		isDebug_ = input.isDebug_;
		sDelete_ = input.sDelete_;
		sCheckClass_ = input.sCheckClass_;
		sDeleteClass_ = input.sDeleteClass_;
	}

	/**
	 * レコードの変更フラグを設定する
	 *
	 * @param b
	 */
	public void setModified(boolean b) {
		this.isModified_ = b;
	}


	/**
	 * XXX:画面入力値で編集リストを更新する
	 *
	 * @param reco
	 *            KZRecordオブジェクト
	 */
	public void modifyRecord(KitRecord reco) {
		JisekiNenpouRecord newRec = (JisekiNenpouRecord) reco;
		// 追加モード
		if (isAddMode()) {
//			setRiyouKbn(newRec.getRiyouKbn());													// 利用区分

		// 変更モード
		} else {
//			setRiyouKbn(newRec.getRiyouKbn());													// 利用区分

		}
	}


	/**
	 * XXX:空白かどうか判定.
	 *
	 * @return true
	 */
	public boolean isEmpty() {
		boolean bEmpty = true;
		// 追加モードの場合
//		if (this.isAddMode()) {
//			if (!PbsUtil.isEmpty(getTorihikiKaisiDt())) {
//				bEmpty = false;
//			}
//
//		// 変更モードの場合
//		} else {
//			bEmpty = false;
//		}
		return bEmpty;
	}



	/**
	 * XXX:レコードが同じかどうか判定する。
	 *
	 * @param reco paramName
	 *
	 * @return 判定
	 */
	public boolean equals(KitRecord reco) {
		boolean bEquals = true;
//		JisekiNenpouRecord target = (JisekiNenpouRecord) reco;
//		// 追加モードの場合
//		if (this.isAddMode()) {
//			// 利用区分
//			if (!PbsUtil.isEqual(getRiyouKbn(), target.getRiyouKbn())) {
//				bEquals = false;
//			}

		// 変更モードの場合
		// 入力される項目には、変更箇所が分かるようにするため
		// setTantosyaNmClass(STYLE_CLASS_MODIFIED);をつける

//		} else {

//			// 利用区分
//			if (!PbsUtil.isEqual(getRiyouKbn(), target.getRiyouKbn())) {
//				bEquals = false;
//			}

//		}
//
 		return bEquals;

	}



	/**
	 * 項目別にエラー有無フラグ<br/>
	 * エラーチェック対象項目のキーを配列化して評価する
	 */
	public boolean isHasErrorByElements() {
		// レコードクラスに保持しているフラグを初期化
		String[] flgs = {
//				 "SHOHIN_CD_CLASS"
//				,"SEIHIN_CD_CLASS"
//				,"SHNNM_REPORT1_CLASS"
//				,"YOUKI_NM_REPORT_CLASS"

		};

		for(String flg : flgs){
			if(STYLE_CLASS_ERROR.equals(getString(flg))){
				return true;
			}
		}
		return false;
	}


	/**
	 * 入力重複チェック
	 * @param rec
	 * @return TRUE:主キー項目が重複している / FALSE:重複無し
	 */
	public boolean isDuplicated(JisekiNenpouRecord rec){
		boolean ret = true;

		// 単票パターンなので重複チェックなし
		return ret;
	}


	/**
	 * エラー、警告等表示用フラグ初期化
	 * <br>※ ジェネレーターで作成するメソッドを初期化する
	 */
	public void initClasses(){
		// 親クラスに保持しているフラグを初期化
		setCheckClass(STYLE_CLASS_INIT);
		setDeleteClass(STYLE_CLASS_INIT);

		// レコードクラスに保持しているフラグを初期化
		String[] flgs = {
//				 "SHOHIN_CD_CLASS"
//				,"SEIHIN_CD_CLASS"
//				,"SHNNM_REPORT1_CLASS"
//				,"YOUKI_NM_REPORT_CLASS"

		};
		for(String flg : flgs){
			put(flg, STYLE_CLASS_INIT);
		}
	}



	// -----------------------------------------
	// 固有の処理

	// -----------------------------------------
	// Getter / Setter
	// ※ ジェネレーターで作成する

	// ====出荷データ・戻入データ共通====
	/**
	 * 商品CDを返します。
	 *
	 * @return 商品CD
	 */
	public String getShohinCd() {
		return (String) get("SHOHIN_CD");
	}

	/**
	 * 商品CDをレコードに格納します。
	 *
	 * @param shohinCd 商品CD
	 */
	public void setShohinCd(String shohinCd) {
		put("SHOHIN_CD", shohinCd);
	}


	/**
	 * 製品CDを返します。
	 *
	 * @return 製品CD
	 */
	public String getSeihinCd() {
		return (String) get("SEIHIN_CD");
	}

	/**
	 * 製品CDをレコードに格納します。
	 *
	 * @param seihinCd 製品CD
	 */
	public void setSeihinCd(String seihinCd) {
		put("SEIHIN_CD", seihinCd);
	}


	/**
	 * 商品名称_自社各帳票用(1)(論理名SHNNM_REPORT1)を返します。
	 *
	 * @return 商品名称_自社各帳票用(1)
	 */
	public String getShohinNm() {
		return (String) get("SHOHIN_NM");
	}

	/**
	 * 商品名称_自社各帳票用(1)をレコードに格納します。
	 *
	 * @param shohinNm 商品名称_自社各帳票用(1)
	 */
	public void setShohinNm(String shohinNm) {
		put("SHOHIN_NM", shohinNm);
	}


	/**
	 * 容器名称_自社各帳票用(論理名YOUKI_NM_REPORT)を返します。
	 *
	 * @return 容器名称_自社各帳票用
	 */
	public String getYoukiNm() {
		return (String) get("YOUKI_NM");
	}

	/**
	 * 容器名称_自社各帳票用をレコードに格納します。
	 *
	 * @param youkiNm 容器名称_自社各帳票用
	 */
	public void setYoukiNm(String youkiNm) {
		put("YOUKI_NM", youkiNm);
	}


	/**
	 * ｹｰｽ入数(論理名CASE_IRISU)を返します。
	 *
	 * @return ｹｰｽ入数
	 */
	public String getIrisu() {
		return (String) get("IRISU");
	}

	/**
	 * ｹｰｽ入数をレコードに格納します。
	 *
	 * @param irisu ｹｰｽ入数
	 */
	public void setIrisu(String irisu) {
		put("IRISU", irisu);
	}


	/**
	 * 出荷数量換算総ｾｯﾄ数_受入数換算総本数を返します。
	 * SYUKA_ALL_BARA UKEIRE_ALL_BARA
	 *
	 * @return 出荷数量換算総ｾｯﾄ数_受入数換算総本数
	 */
	public String getSobara() {
		return (String) get("SOBARA");
	}

	/**
	 * 出荷数量換算総ｾｯﾄ数_受入数換算総本数をレコードに格納します。
	 *
	 * @param sobara 出荷数量換算総ｾｯﾄ数_受入数換算総本数
	 */
	public void setSobara(String sobara) {
		put("SOBARA", sobara);
	}


	/**
	 * 販売額_受入数金額を返します。
	 * SYUKA_HANBAI_KINGAKU UKEIRE_KINGAKU_
	 *
	 * @return 販売額_受入数金額
	 */
	public String getSokingaku() {
		return (String) get("SOKINGAKU");
	}

	/**
	 * 販売額_受入数金額をレコードに格納します。
	 *
	 * @param sokingaku 販売額_受入数金額
	 */
	public void setSokingaku(String sokingaku) {
		put("SOKINGAKU", sokingaku);
	}

	/**
	 * 容量（L）出荷総容量_受入容量（L）出荷総容量を返します。
	 * SYUKA_SOUYOURYO
	 *
	 * @return 容量（L）出荷総容量_受入容量（L）出荷総容量
	 */
	public String getSoyouryo() {
		return (String) get("SOYOURYO");
	}

	/**
	 * 容量（L）出荷総容量_受入容量（L）出荷総容量をレコードに格納します。
	 *
	 * @param soyouryo 容量（L）出荷総容量_受入容量（L）出荷総容量
	 */
	public void setSoyouryo(String soyouryo) {
		put("SOYOURYO", soyouryo);
	}


	/**
	 * 年フラグを返します。
	 *
	 * @return 年フラグ
	 */
	public String getNenFlg() {
		return (String) get("NEN_FLG");
	}

	/**
	 * 年フラグをレコードに格納します。
	 *
	 * @param  NenFlg 年フラグ
	 */
	public void setNenFlg(String nenFlg) {
		put("NEN_FLG", nenFlg);
	}

	/**
	 * 累計を返します。
	 *
	 * @return 累計
	 */
	public String getSumTounen() {
		return (String) get("SUM_TOUNEN");
	}

	/**
	 * 累計をレコードに格納します。
	 *
	 * @param  SumTounen 累計
	 */
	public void setRuikei(String sumTounen) {
		put("SUM_TOUNEN", sumTounen);
	}


	/**
	 * 累計前年を返します。
	 *
	 * @return 累計前年
	 */
	public String getSumZennen() {
		return (String) get("SUM_ZENNEN");
	}

	/**
	 * 累計前年をレコードに格納します。
	 *
	 * @param sumZennen 累計前年
	 */
	public void setSumZennen(String sumZennen) {
		put("SUM_ZENNEN", sumZennen);
	}


	/**
	 * 1月を返します。
	 *
	 * @return 1月
	 */
	public String getSum01Tounen() {
		return (String) get("SUM_01_TOUNEN");
	}

	/**
	 * 1月をレコードに格納します。
	 *
	 * @param Sum01Tounen 1月
	 */
	public void setSum01Tounen(String sum01Tounen) {
		put("SUM_01_TOUNEN", sum01Tounen);
	}


	/**
	 * 1月前年を返します。
	 *
	 * @return 1月前年
	 */
	public String getSum01Zennen() {
		return (String) get("SUM_01_ZENNEN");
	}

	/**
	 * 1月前年をレコードに格納します。
	 *
	 * @param Sum01Zennen 1月前年
	 */
	public void setSum01Zennen(String sum01Zennen) {
		put("SUM_01_ZENNEN", sum01Zennen);
	}

	/**
	 * 2月を返します。
	 *
	 * @return 2月
	 */
	public String getSum02Tounen() {
		return (String) get("SUM_02_TOUNEN");
	}

	/**
	 * 2月をレコードに格納します。
	 *
	 * @param Sum02Tounen 2月
	 */
	public void setSum02Tounen(String sum02Tounen) {
		put("SUM_02_TOUNEN", sum02Tounen);
	}


	/**
	 * 2月前年を返します。
	 *
	 * @return 2月前年
	 */
	public String getSum02Zennen() {
		return (String) get("SUM_02_ZENNEN");
	}

	/**
	 * 2月前年をレコードに格納します。
	 *
	 * @param Sum02Zennen 2月前年
	 */
	public void setSum02Zennen(String sum02Zennen) {
		put("SUM_02_ZENNEN", sum02Zennen);
	}

	/**
	 * 3月を返します。
	 *
	 * @return 3月
	 */
	public String getSum03Tounen() {
		return (String) get("SUM_03_TOUNEN");
	}

	/**
	 * 3月をレコードに格納します。
	 *
	 * @param Sum03Tounen 3月
	 */
	public void setSum03Tounen(String sum03Tounen) {
		put("SUM_03_TOUNEN", sum03Tounen);
	}


	/**
	 * 3月前年を返します。
	 *
	 * @return 3月前年
	 */
	public String getSum03Zennen() {
		return (String) get("SUM_03_ZENNEN");
	}

	/**
	 * 3月前年をレコードに格納します。
	 *
	 * @param Sum03Zennen 3月前年
	 */
	public void setSum03Zennen(String sum03Zennen) {
		put("SUM_03_ZENNEN", sum03Zennen);
	}

	/**
	 * 4月を返します。
	 *
	 * @return 4月
	 */
	public String getSum04Tounen() {
		return (String) get("SUM_04_TOUNEN");
	}

	/**
	 * 4月をレコードに格納します。
	 *
	 * @param Sum04Tounen 4月
	 */
	public void setSum04Tounen(String sum04Tounen) {
		put("SUM_04_TOUNEN", sum04Tounen);
	}


	/**
	 * 4月前年を返します。
	 *
	 * @return 4月前年
	 */
	public String getSum04Zennen() {
		return (String) get("SUM_04_ZENNEN");
	}

	/**
	 * 4月前年をレコードに格納します。
	 *
	 * @param Sum04Zennen 4月前年
	 */
	public void setSum04Zennen(String sum04Zennen) {
		put("SUM_04_ZENNEN", sum04Zennen);
	}

	/**
	 * 5月を返します。
	 *
	 * @return 5月
	 */
	public String getSum05Tounen() {
		return (String) get("SUM_05_TOUNEN");
	}

	/**
	 * 5月をレコードに格納します。
	 *
	 * @param Sum05Tounen 5月
	 */
	public void setSum05Tounen(String sum05Tounen) {
		put("SUM_05_TOUNEN", sum05Tounen);
	}


	/**
	 * 5月前年を返します。
	 *
	 * @return 5月前年
	 */
	public String getSum05Zennen() {
		return (String) get("SUM_05_ZENNEN");
	}

	/**
	 * 5月前年をレコードに格納します。
	 *
	 * @param Sum05Zennen 5月前年
	 */
	public void setSum05Zennen(String sum05Zennen) {
		put("SUM_05_ZENNEN", sum05Zennen);
	}

	/**
	 * 6月を返します。
	 *
	 * @return 6月
	 */
	public String getSum06Tounen() {
		return (String) get("SUM_06_TOUNEN");
	}

	/**
	 * 6月をレコードに格納します。
	 *
	 * @param Sum06Tounen 6月
	 */
	public void setSun06Tounen(String sum06Tounen) {
		put("SUM_06_TOUNEN", sum06Tounen);
	}


	/**
	 * 6月前年を返します。
	 *
	 * @return 6月前年
	 */
	public String getSum06Zennen() {
		return (String) get("SUM_06_ZENNEN");
	}

	/**
	 * 6月前年をレコードに格納します。
	 *
	 * @param Sum06Zennen 6月前年
	 */
	public void setSum06Zennen(String sum06Zennen) {
		put("SUM_06_ZENNEN", sum06Zennen);
	}

	/**
	 * 7月を返します。
	 *
	 * @return 7月
	 */
	public String getSum07Tounen() {
		return (String) get("SUM_07_TOUNEN");
	}

	/**
	 * 7月をレコードに格納します。
	 *
	 * @param Sum07Tounen 7月
	 */
	public void setSun07Tounen(String sum07Tounen) {
		put("SUM_07_TOUNEN", sum07Tounen);
	}


	/**
	 * 7月前年を返します。
	 *
	 * @return 7月前年
	 */
	public String getSum07Zennen() {
		return (String) get("SUM_07_ZENNEN");
	}

	/**
	 * 7月前年をレコードに格納します。
	 *
	 * @param Sum07Zennen 7月前年
	 */
	public void setSum07Zennen(String sum07Zennen) {
		put("SUM_07_ZENNEN", sum07Zennen);
	}

	/**
	 * 8月を返します。
	 *
	 * @return 8月
	 */
	public String getSum08Tounen() {
		return (String) get("SUM_08_TOUNEN");
	}

	/**
	 * 8月をレコードに格納します。
	 *
	 * @param Sum08Tounen 8月
	 */
	public void setSun08Tounen(String sum08Tounen) {
		put("SUM_08_TOUNEN", sum08Tounen);
	}


	/**
	 * 8月前年を返します。
	 *
	 * @return 8月前年
	 */
	public String getSum08Zennen() {
		return (String) get("SUM_08_ZENNEN");
	}

	/**
	 * 8月前年をレコードに格納します。
	 *
	 * @param Sum08Zennen 8月前年
	 */
	public void setSum08Zennen(String sum08Zennen) {
		put("SUM_08_ZENNEN", sum08Zennen);
	}

	/**
	 * 9月を返します。
	 *
	 * @return 9月
	 */
	public String getSum09Tounen() {
		return (String) get("SUM_09_TOUNEN");
	}

	/**
	 * 9月をレコードに格納します。
	 *
	 * @param Sum09Tounen 9月
	 */
	public void setSun09Tounen(String sum09Tounen) {
		put("SUM_09_TOUNEN", sum09Tounen);
	}


	/**
	 * 9月前年を返します。
	 *
	 * @return 9月前年
	 */
	public String getSum09Zennen() {
		return (String) get("SUM_09_ZENNEN");
	}

	/**
	 * 9月前年をレコードに格納します。
	 *
	 * @param Sum09Zennen 9月前年
	 */
	public void setSum09Zennen(String sum09Zennen) {
		put("SUM_09_ZENNEN", sum09Zennen);
	}

	/**
	 * 10月を返します。
	 *
	 * @return 10月
	 */
	public String getSum10Tounen() {
		return (String) get("SUM_10_TOUNEN");
	}

	/**
	 * 10月をレコードに格納します。
	 *
	 * @param Sum10Tounen 10月
	 */
	public void setSun10Tounen(String sum10Tounen) {
		put("SUM_10_TOUNEN", sum10Tounen);
	}


	/**
	 * 10月前年を返します。
	 *
	 * @return 10月前年
	 */
	public String getSum10Zennen() {
		return (String) get("SUM_10_ZENNEN");
	}

	/**
	 * 10月前年をレコードに格納します。
	 *
	 * @param Sum10Zennen 10月前年
	 */
	public void setSum10Zennen(String sum10Zennen) {
		put("SUM_10_ZENNEN", sum10Zennen);
	}

	/**
	 * 11月を返します。
	 *
	 * @return 11月
	 */
	public String getSum11Tounen() {
		return (String) get("SUM_11_TOUNEN");
	}

	/**
	 * 11月をレコードに格納します。
	 *
	 * @param Sum11Tounen 11月
	 */
	public void setSun11Tounen(String sum11Tounen) {
		put("SUM_11_TOUNEN", sum11Tounen);
	}


	/**
	 * 11月前年を返します。
	 *
	 * @return 11月前年
	 */
	public String getSum11Zennen() {
		return (String) get("SUM_11_ZENNEN");
	}

	/**
	 * 11月前年をレコードに格納します。
	 *
	 * @param Sum11Zennen 11月前年
	 */
	public void setSum11Zennen(String sum11Zennen) {
		put("SUM_11_ZENNEN", sum11Zennen);
	}

	/**
	 * 12月を返します。
	 *
	 * @return 12月
	 */
	public String getSum12Tounen() {
		return (String) get("SUM_12_TOUNEN");
	}

	/**
	 * 12月をレコードに格納します。
	 *
	 * @param Sum12Tounen 12月
	 */
	public void setSun12Tounen(String sum12Tounen) {
		put("SUM_12_TOUNEN", sum12Tounen);
	}


	/**
	 * 12月前年を返します。
	 *
	 * @return 12月前年
	 */
	public String getSum12Zennen() {
		return (String) get("SUM_12_ZENNEN");
	}

	/**
	 * 12月前年をレコードに格納します。
	 *
	 * @param Sum12Zennen 12月前年
	 */
	public void setSum12Zennen(String sum12Zennen) {
		put("SUM_12_ZENNEN", sum12Zennen);
	}


	// ========================
	// フラグ用Getter / Setter （CLASS）



	// ====================================
	// 変更前の情報を保持させるGetter / Setter （VIEW）




}	// -- class
