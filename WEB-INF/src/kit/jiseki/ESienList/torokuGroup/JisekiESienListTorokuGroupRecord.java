package kit.jiseki.ESienList.torokuGroup;

import java.util.HashMap;

import fb.inf.KitRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 各種登録グループのレコード
 * @author t_kimura
 *
 */
public class JisekiESienListTorokuGroupRecord extends KitRecord {

	/** serialVersionUID */
	private static final long serialVersionUID = 3914522802851587115L;

	/**
	 * コンストラクタ
	 */
	public JisekiESienListTorokuGroupRecord() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param input
	 */
	public JisekiESienListTorokuGroupRecord(HashMap<String, Object> input) {
		super(input);
	}

	/**
	 * コンストラクタ
	 * @param input
	 */
	public JisekiESienListTorokuGroupRecord(JisekiESienListTorokuGroupRecord input) {
		super((KitRecord) input);

	}

	/* (非 Javadoc)
	 * @see fb.inf.KitRecord#isHasErrorByElements()
	 */
	@Override
	public boolean isHasErrorByElements() {
		return false;
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitRecord#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return false;
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitRecord#equals(fb.inf.KitRecord)
	 */
	@Override
	public boolean equals(KitRecord record) {
		return false;
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitRecord#modifyRecord(fb.inf.KitRecord)
	 */
	@Override
	public void modifyRecord(KitRecord record) {

	}

	/* (非 Javadoc)
	 * @see fb.inf.KitRecord#initClasses()
	 */
	@Override
	public void initClasses() {
	}

	/**
	 * クラス定義クリア処理
	 */
	public void clearClass() {
		setTorokuCdClass(null);
		setTorokuNmClass(null);
		setYoukiNmClass(null);
		setCaseIrisuClass(null);
		setTnpnVolClass(null);
	}

	/**
	 * 編集済みクラス定義設定
	 */
	public void setModifiedClass() {
		if (!PbsUtil.isEqual(getTorokuCd(), getTorokuCdView())) {
			if(!getHasError()) {
				super.setModified();
			}
			//setTorokuCdClass(STYLE_CLASS_MODIFIED);
		}
//		if (!PbsUtil.isEqual(getTorokuNm(), getTorokuNmView())) {
//			setTorokuNmClass(STYLE_CLASS_MODIFIED);
//		}
//		if (!PbsUtil.isEqual(getYoukiNm(), getYoukiNmView())) {
//			setYoukiNmClass(STYLE_CLASS_MODIFIED);
//		}
//		if (!PbsUtil.isEqual(getCaseIrisu(), getCaseIrisuView())) {
//			setCaseIrisuClass(STYLE_CLASS_MODIFIED);
//		}
//		if (!PbsUtil.isEqual(getTnpnVol(), getTnpnVolView())) {
//			setTnpnVolClass(STYLE_CLASS_MODIFIED);
//		}
	}

	/**
	 * （各マスタ共通）登録コード（VIEW）
	 * @return
	 */
	public String getTorokuCdView() {
		return (String) get("TOROKU_CD_VIEW");
	}
	/**
	 * （各マスタ共通）登録コード（VIEW）
	 * @param torokuCdView
	 */
	public void setTorokuCdView(String torokuCdView) {
		put("TOROKU_CD_VIEW", torokuCdView);
	}
	/**
	 * （各マスタ共通）登録名（VIEW）
	 * @return
	 */
	public String getTorokuNmView() {
		return (String) get("TOROKU_NM_VIEW");
	}
	/**
	 * （各マスタ共通）登録名（VIEW）
	 * @param torokuNmView
	 */
	public void setTorokuNmView(String torokuNmView) {
		put("TOROKU_NM_VIEW", torokuNmView);
	}
	/**
	 * （商品用）容器名（VIEW）
	 * @return
	 */
	public String getYoukiNmView() {
		return (String) get("YOUKI_NM_VIEW");
	}
	/**
	 * （商品用）容器名（VIEW）
	 * @param youkiNmView
	 */
	public void setYoukiNmView(String youkiNmView) {
		put("YOUKI_NM_VIEW", youkiNmView);
	}
	/**
	 * （商品用）入数（VIEW）
	 * @return
	 */
	public String getCaseIrisuView() {
		return (String) get("CASE_IRISU_VIEW");
	}
	/**
	 * （商品用）入数（VIEW）
	 * @param caseIrisuView
	 */
	public void setCaseIrisuView(String caseIrisuView) {
		put("CASE_IRISU_VIEW", caseIrisuView);
	}
	/**
	 * （商品用）容量（VIEW）
	 * @return
	 */
	public String getTnpnVolView() {
		return (String) get("TNPN_VOL_VIEW");
	}
	/**
	 * （商品用）容量（VIEW）
	 * @param tnpnVolView
	 */
	public void setTnpnVolView(String tnpnVolView) {
		put("TNPN_VOL_VIEW", tnpnVolView);
	}

	/**
	 * （各マスタ共通）登録コード
	 * @return
	 */
	public String getTorokuCd() {
		return (String) get("TOROKU_CD");
	}
	/**
	 *（各マスタ共通） 登録コード
	 * @param torokuCd
	 */
	public void setTorokuCd(String torokuCd) {
		put("TOROKU_CD", torokuCd);
	}
	/**
	 * （各マスタ共通）登録名
	 * @return
	 */
	public String getTorokuNm() {
		return (String) get("TOROKU_NM");
	}
	/**
	 * （各マスタ共通）登録名
	 * @param torokuNm
	 */
	public void setTorokuNm(String torokuNm) {
		put("TOROKU_NM", torokuNm);
	}
	/**
	 * （商品用）容器名
	 * @return
	 */
	public String getYoukiNm() {
		return (String) get("YOUKI_NM");
	}
	/**
	 * （商品用）容器名
	 * @param youkiNm
	 */
	public void setYoukiNm(String youkiNm) {
		put("YOUKI_NM", youkiNm);
	}
	/**
	 * （商品用）入数
	 * @return
	 */
	public String getCaseIrisu() {
		return (String) get("CASE_IRISU");
	}
	/**
	 * （商品用）入数
	 * @param caseIrisu
	 */
	public void setCaseIrisu(String caseIrisu) {
		put("CASE_IRISU", caseIrisu);
	}
	/**
	 * （商品用）容量
	 * @return
	 */
	public String getTnpnVol() {
		return (String) get("TNPN_VOL");
	}
	/**
	 * （商品用）容量
	 * @param tnpnVol
	 */
	public void setTnpnVol(String tnpnVol) {
		put("TNPN_VOL", tnpnVol);
	}

	/**
	 * （各マスタ共通）登録コード（CLASS）
	 * @return
	 */
	public String getTorokuCdClass() {
		return (String) get("TOROKU_CD_CLASS");
	}
	/**
	 * （各マスタ共通）登録コード（CLASS）
	 * @param torokuCdClass
	 */
	public void setTorokuCdClass(String torokuCdClass) {
		put("TOROKU_CD_CLASS", torokuCdClass);
	}
	/**
	 * （各マスタ共通）登録名（CLASS）
	 * @return
	 */
	public String getTorokuNmClass() {
		return (String) get("TOROKU_NM_CLASS");
	}
	/**
	 * （各マスタ共通）登録名（CLASS）
	 * @param torokuNmClass
	 */
	public void setTorokuNmClass(String torokuNmClass) {
		put("TOROKU_NM_CLASS", torokuNmClass);
	}
	/**
	 * （商品用）容器名（CLASS）
	 * @return
	 */
	public String getYoukiNmClass() {
		return (String) get("YOUKI_NM_CLASS");
	}
	/**
	 *（商品用） 容器名（CLASS）
	 * @param youkiNmClass
	 */
	public void setYoukiNmClass(String youkiNmClass) {
		put("YOUKI_NM_CLASS", youkiNmClass);
	}
	/**
	 * （商品用）入数（CLASS）
	 * @return
	 */
	public String getCaseIrisuClass() {
		return (String) get("CASE_IRISU_CLASS");
	}
	/**
	 * （商品用）入数（CLASS）
	 * @param caseIrisuClass
	 */
	public void setCaseIrisuClass(String caseIrisuClass) {
		put("CASE_IRISU_CLASS", caseIrisuClass);
	}
	/**
	 * （商品用）容量（CLASS）
	 * @return
	 */
	public String getTnpnVolClass() {
		return (String) get("TNPN_VOL_CLASS");
	}
	/**
	 * （商品用）容量（CLASS）
	 * @param tnpnVolClass
	 */
	public void setTnpnVolClass(String tnpnVolClass) {
		put("TNPN_VOL_CLASS", tnpnVolClass);
	}
}
