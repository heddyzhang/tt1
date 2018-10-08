package kit.jiseki.ESienList;

import static kit.jiseki.ESienList.IJisekiESienList.SYUKEI_NAIYO_KB_SAKADANE;
import static kit.jiseki.ESienList.IJisekiESienList.SYUKEI_NAIYO_KB_SAKESITU;
import static kit.jiseki.ESienList.IJisekiESienList.SYUKEI_NAIYO_KB_SYOHIN;
import static kit.jiseki.ESienList.IJisekiESienList.TOROKU_NO_MAX;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import fb.inf.KitSearchForm;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;
import kit.jiseki.ESienList.torokuGroup.JisekiESienListTorokuGroupList;
import kit.jiseki.ESienList.torokuGroup.JisekiESienListTorokuGroupRecord;

/**
 * 営業支援実績一覧の検索フォーム
 * @author t_kimura
 *
 */
public class JisekiESienList_SearchForm extends KitSearchForm {

	/** serialVersionUID */
	private static final long serialVersionUID = -2787830877459292304L;

	/** 対象データ */
	private String taisyoData;
	/** 計算日付基準 */
	private String keisanDateKijun;
	/** 自家用・小売有無 */
	private String jikakouriUmu;
	/** 表示単位 */
	private String hyojiTani;
	/** 抽出条件 */
	private String cyusyutuJyoken;
	/** 事業所 */
	private String jigyosyoCd;
	/** 課 */
	private String kaCd;
	/** 担当者 */
	private String tantosyaCd;
	/** 都道府県 */
	private String countryCd;
	/** 全国卸 */
	private String zenkokuOrosiCd;
	/** 特約 */
	private String tokuyakuCd;
	/** 特約名 */
	private String tokuyakuNm;
	/** 送荷先 */
	private String sounisakiCd;
	/** 送荷先名 */
	private String sounisakiNm;
	/** 酒販店 */
	private String syuhantenCd;
	/** 酒販店名 */
	private String syuhantenNm;
	/** 対象表示期間 */
	private String taisyoHyojiKikan;
	/** 対象日 */
	private String taisyoDate;
	/** 対象年From */
	private String taisyoYearFrom;
	/** 対象年To */
	private String taisyoYearTo;
	/** 対象月From */
	private String taisyoMonthFrom;
	/** 対象月To */
	private String taisyoMonthTo;
	/** 対象日From */
	private String taisyoDateFrom;
	/** 対象日To */
	private String taisyoDateTo;
	/** 対比有無 */
	private String taihiUmu;
	/** 予算対比 */
	private String yosanTaihi;
	/** 集計内容 */
	private String syukeiNaiyo;
	/** 酒質 */
	private String sakesituOp;
	/** 充填容器 */
	private String jyutenyoukiOp;
	/** 酒種レコード */
	private JisekiESienListTorokuGroupList sakadaneList = new JisekiESienListTorokuGroupList(TOROKU_NO_MAX);
	/** 酒質CD */
	private JisekiESienListTorokuGroupList sakesituList = new JisekiESienListTorokuGroupList(TOROKU_NO_MAX);
	/** 商品CD */
	private JisekiESienListTorokuGroupList syohinList = new JisekiESienListTorokuGroupList(TOROKU_NO_MAX);
	/** 業務日付の月の1日 */
	private String gymMonthDateFrom;
	/** 業務日付の月の末日 */
	private String gymMonthDateTo;

	/** 事業所(エラー) */
	private boolean jigyosyoCdError;
	/** 課(エラー) */
	private boolean kaCdError;
	/** 担当者(エラー) */
	private boolean tantosyaCdError;
	/** 都道府県(エラー) */
	private boolean countryCdError;
	/** 全国卸(エラー) */
	private boolean zenkokuOrosiCdError;
	/** 特約(エラー) */
	private boolean tokuyakuCdError;
	/** 送荷先(エラー) */
	private boolean sounisakiCdError;
	/** 酒販店(エラー) */
	private boolean syuhantenCdError;
	/** 対象日(エラー) */
	private boolean taisyoDateError;
	/** 対象年From(エラー) */
	private boolean taisyoYearFromError;
	/** 対象年To(エラー) */
	private boolean taisyoYearToError;
	/** 対象月From(エラー) */
	private boolean taisyoMonthFromError;
	/** 対象月To(エラー) */
	private boolean taisyoMonthToError;
	/** 対象日From(エラー) */
	private boolean taisyoDateFromError;
	/** 対象日To(エラー) */
	private boolean taisyoDateToError;
	/**
	 * @return taisyoData
	 */
	public String getTaisyoData() {
		return taisyoData;
	}
	/**
	 * @param taisyoData セットする taisyoData
	 */
	public void setTaisyoData(String taisyoData) {
		this.taisyoData = taisyoData;
	}
	/**
	 * @return keisanDateKijun
	 */
	public String getKeisanDateKijun() {
		return keisanDateKijun;
	}
	/**
	 * @param keisanDateKijun セットする keisanDateKijun
	 */
	public void setKeisanDateKijun(String keisanDateKijun) {
		this.keisanDateKijun = keisanDateKijun;
	}
	/**
	 * @return jikakouriUmu
	 */
	public String getJikakouriUmu() {
		return jikakouriUmu;
	}
	/**
	 * @param jikakouriUmu セットする jikakouriUmu
	 */
	public void setJikakouriUmu(String jikakouriUmu) {
		this.jikakouriUmu = jikakouriUmu;
	}
	/**
	 * @return hyojiTani
	 */
	public String getHyojiTani() {
		return hyojiTani;
	}
	/**
	 * @param hyojiTani セットする hyojiTani
	 */
	public void setHyojiTani(String hyojiTani) {
		this.hyojiTani = hyojiTani;
	}
	/**
	 * @return cyusyutuJyoken
	 */
	public String getCyusyutuJyoken() {
		return cyusyutuJyoken;
	}
	/**
	 * @param cyusyutuJyoken セットする cyusyutuJyoken
	 */
	public void setCyusyutuJyoken(String cyusyutuJyoken) {
		this.cyusyutuJyoken = cyusyutuJyoken;
	}
	/**
	 * @return jigyosyoCd
	 */
	public String getJigyosyoCd() {
		return jigyosyoCd;
	}
	/**
	 * @param jigyosyoCd セットする jigyosyoCd
	 */
	public void setJigyosyoCd(String jigyosyoCd) {
		this.jigyosyoCd = jigyosyoCd;
	}
	/**
	 * @return kaCd
	 */
	public String getKaCd() {
		return kaCd;
	}
	/**
	 * @param kaCd セットする kaCd
	 */
	public void setKaCd(String kaCd) {
		this.kaCd = kaCd;
	}
	/**
	 * @return tantosyaCd
	 */
	public String getTantosyaCd() {
		return tantosyaCd;
	}
	/**
	 * @param tantosyaCd セットする tantosyaCd
	 */
	public void setTantosyaCd(String tantosyaCd) {
		this.tantosyaCd = tantosyaCd;
	}
	/**
	 * @return countryCd
	 */
	public String getCountryCd() {
		return countryCd;
	}
	/**
	 * @param countryCd セットする countryCd
	 */
	public void setCountryCd(String countryCd) {
		this.countryCd = countryCd;
	}
	/**
	 * @return zenkokuOrosiCd
	 */
	public String getZenkokuOrosiCd() {
		return zenkokuOrosiCd;
	}
	/**
	 * @param zenkokuOrosiCd セットする zenkokuOrosiCd
	 */
	public void setZenkokuOrosiCd(String zenkokuOrosiCd) {
		this.zenkokuOrosiCd = zenkokuOrosiCd;
	}
	/**
	 * @return tokuyakuCd
	 */
	public String getTokuyakuCd() {
		return tokuyakuCd;
	}
	/**
	 * @param tokuyakuCd セットする tokuyakuCd
	 */
	public void setTokuyakuCd(String tokuyakuCd) {
		this.tokuyakuCd = tokuyakuCd;
	}
	/**
	 * @return tokuyakuNm
	 */
	public String getTokuyakuNm() {
		return tokuyakuNm;
	}
	/**
	 * @param tokuyakuNm セットする tokuyakuNm
	 */
	public void setTokuyakuNm(String tokuyakuNm) {
		this.tokuyakuNm = tokuyakuNm;
	}
	/**
	 * @return sounisakiCd
	 */
	public String getSounisakiCd() {
		return sounisakiCd;
	}
	/**
	 * @param sounisakiCd セットする sounisakiCd
	 */
	public void setSounisakiCd(String sounisakiCd) {
		this.sounisakiCd = sounisakiCd;
	}
	/**
	 * @return sounisakiNm
	 */
	public String getSounisakiNm() {
		return sounisakiNm;
	}
	/**
	 * @param sounisakiNm セットする sounisakiNm
	 */
	public void setSounisakiNm(String sounisakiNm) {
		this.sounisakiNm = sounisakiNm;
	}
	/**
	 * @return syuhantenCd
	 */
	public String getSyuhantenCd() {
		return syuhantenCd;
	}
	/**
	 * @param syuhantenCd セットする syuhantenCd
	 */
	public void setSyuhantenCd(String syuhantenCd) {
		this.syuhantenCd = syuhantenCd;
	}
	/**
	 * @return syuhantenNm
	 */
	public String getSyuhantenNm() {
		return syuhantenNm;
	}
	/**
	 * @param syuhantenNm セットする syuhantenNm
	 */
	public void setSyuhantenNm(String syuhantenNm) {
		this.syuhantenNm = syuhantenNm;
	}
	/**
	 * @return taisyoHyojiKikan
	 */
	public String getTaisyoHyojiKikan() {
		return taisyoHyojiKikan;
	}
	/**
	 * @param taisyoHyojiKikan セットする taisyoHyojiKikan
	 */
	public void setTaisyoHyojiKikan(String taisyoHyojiKikan) {
		this.taisyoHyojiKikan = taisyoHyojiKikan;
	}
	/**
	 * @return taisyoDate
	 */
	public String getTaisyoDate() {
		return taisyoDate;
	}
	/**
	 * @param taisyoDate セットする taisyoDate
	 */
	public void setTaisyoDate(String taisyoDate) {
		this.taisyoDate = taisyoDate;
	}
	/**
	 * @return taisyoYearFrom
	 */
	public String getTaisyoYearFrom() {
		return taisyoYearFrom;
	}
	/**
	 * @param taisyoYearFrom セットする taisyoYearFrom
	 */
	public void setTaisyoYearFrom(String taisyoYearFrom) {
		this.taisyoYearFrom = taisyoYearFrom;
	}
	/**
	 * @return taisyoYearTo
	 */
	public String getTaisyoYearTo() {
		return taisyoYearTo;
	}
	/**
	 * @param taisyoYearTo セットする taisyoYearTo
	 */
	public void setTaisyoYearTo(String taisyoYearTo) {
		this.taisyoYearTo = taisyoYearTo;
	}
	/**
	 * @return taisyoMonthFrom
	 */
	public String getTaisyoMonthFrom() {
		return taisyoMonthFrom;
	}
	/**
	 * @param taisyoMonthFrom セットする taisyoMonthFrom
	 */
	public void setTaisyoMonthFrom(String taisyoMonthFrom) {
		this.taisyoMonthFrom = taisyoMonthFrom;
	}
	/**
	 * @return taisyoMonthTo
	 */
	public String getTaisyoMonthTo() {
		return taisyoMonthTo;
	}
	/**
	 * @param taisyoMonthTo セットする taisyoMonthTo
	 */
	public void setTaisyoMonthTo(String taisyoMonthTo) {
		this.taisyoMonthTo = taisyoMonthTo;
	}
	/**
	 * @return taisyoDateFrom
	 */
	public String getTaisyoDateFrom() {
		return taisyoDateFrom;
	}
	/**
	 * @param taisyoDateFrom セットする taisyoDateFrom
	 */
	public void setTaisyoDateFrom(String taisyoDateFrom) {
		this.taisyoDateFrom = taisyoDateFrom;
	}
	/**
	 * @return taisyoDateTo
	 */
	public String getTaisyoDateTo() {
		return taisyoDateTo;
	}
	/**
	 * @param taisyoDateTo セットする taisyoDateTo
	 */
	public void setTaisyoDateTo(String taisyoDateTo) {
		this.taisyoDateTo = taisyoDateTo;
	}
	/**
	 * @return taihiUmu
	 */
	public String getTaihiUmu() {
		return taihiUmu;
	}
	/**
	 * @param taihiUmu セットする taihiUmu
	 */
	public void setTaihiUmu(String taihiUmu) {
		this.taihiUmu = taihiUmu;
	}
	/**
	 * @return yosanTaihi
	 */
	public String getYosanTaihi() {
		return yosanTaihi;
	}
	/**
	 * @param yosanTaihi セットする yosanTaihi
	 */
	public void setYosanTaihi(String yosanTaihi) {
		this.yosanTaihi = yosanTaihi;
	}
	/**
	 * @return syukeiNaiyo
	 */
	public String getSyukeiNaiyo() {
		return syukeiNaiyo;
	}
	/**
	 * @param syukeiNaiyo セットする syukeiNaiyo
	 */
	public void setSyukeiNaiyo(String syukeiNaiyo) {
		this.syukeiNaiyo = syukeiNaiyo;
	}
	/**
	 * @return sakesituOp
	 */
	public String getSakesituOp() {
		return sakesituOp;
	}
	/**
	 * @param sakesituOp セットする sakesituOp
	 */
	public void setSakesituOp(String sakesituOp) {
		this.sakesituOp = sakesituOp;
	}
	/**
	 * @return jyutenyoukiOp
	 */
	public String getJyutenyoukiOp() {
		return jyutenyoukiOp;
	}
	/**
	 * @param jyutenyoukiOp セットする jyutenyoukiOp
	 */
	public void setJyutenyoukiOp(String jyutenyoukiOp) {
		this.jyutenyoukiOp = jyutenyoukiOp;
	}
	/**
	 * @return sakadaneList
	 */
	public JisekiESienListTorokuGroupList getSakadaneList() {
		return sakadaneList;
	}
	/**
	 * @param sakadaneList セットする sakadaneList
	 */
	public void setSakadaneList(JisekiESienListTorokuGroupList sakadaneList) {
		this.sakadaneList = sakadaneList;
	}
	/**
	 * @return sakesituList
	 */
	public JisekiESienListTorokuGroupList getSakesituList() {
		return sakesituList;
	}
	/**
	 * @param sakesituList セットする sakesituList
	 */
	public void setSakesituList(JisekiESienListTorokuGroupList sakesituList) {
		this.sakesituList = sakesituList;
	}
	/**
	 * @return syohinList
	 */
	public JisekiESienListTorokuGroupList getSyohinList() {
		return syohinList;
	}
	/**
	 * @param syohinList セットする syohinList
	 */
	public void setSyohinList(JisekiESienListTorokuGroupList syohinList) {
		this.syohinList = syohinList;
	}
	public JisekiESienListTorokuGroupRecord getSakadaneRecord(int index) {
		return ((JisekiESienListTorokuGroupRecord) sakadaneList.get(index));
	}
	public JisekiESienListTorokuGroupRecord getSakesituRecord(int index) {
		return ((JisekiESienListTorokuGroupRecord) sakesituList.get(index));
	}
	public JisekiESienListTorokuGroupRecord getSyohinRecord(int index) {
		return ((JisekiESienListTorokuGroupRecord) syohinList.get(index));
	}

	/**
	 *エラークリア
	 */
	public void clearError() {
		// エラークリア
		sakadaneList.clearRecordError();
		sakesituList.clearRecordError();
		syohinList.clearRecordError();
		jigyosyoCdError = false;
		kaCdError = false;
		tantosyaCdError = false;
		countryCdError = false;
		zenkokuOrosiCdError = false;
		tokuyakuCdError = false;
		sounisakiCdError = false;
		syuhantenCdError = false;
		taisyoDateError = false;
		taisyoYearFromError = false;
		taisyoYearToError = false;
		taisyoMonthFromError = false;
		taisyoMonthToError = false;
		taisyoDateFromError = false;
		taisyoDateToError = false;
	}

	/**
	 * エラーが存在するかどうか
	 * @return チェック結果
	 */
	public boolean hasError() {
		if(jigyosyoCdError || kaCdError || tantosyaCdError || countryCdError || zenkokuOrosiCdError ||
				tokuyakuCdError || sounisakiCdError || syuhantenCdError || taisyoDateError || taisyoYearFromError ||
				taisyoYearToError || taisyoMonthFromError || taisyoMonthToError || taisyoDateFromError || taisyoDateToError) {
			return true;
		}
		if(sakadaneList.hasError()) return true;
		if(sakesituList.hasError()) return true;
		if(syohinList.hasError()) return true;
		return false;
	}

	/**
	 * @return gymMonthDateFrom
	 */
	public String getGymMonthDateFrom() {
		return gymMonthDateFrom;
	}
	/**
	 * @param gymMonthDateFrom セットする gymMonthDateFrom
	 */
	public void setGymMonthDateFrom(String gymMonthDateFrom) {
		this.gymMonthDateFrom = gymMonthDateFrom;
	}
	/**
	 * @return gymMonthDateTo
	 */
	public String getGymMonthDateTo() {
		return gymMonthDateTo;
	}
	/**
	 * @param gymMonthDateTo セットする gymMonthDateTo
	 */
	public void setGymMonthDateTo(String gymMonthDateTo) {
		this.gymMonthDateTo = gymMonthDateTo;
	}

	/**
	 * @return jigyosyoCdError
	 */
	public boolean isJigyosyoCdError() {
		return jigyosyoCdError;
	}
	/**
	 * @param jigyosyoCdError セットする jigyosyoCdError
	 */
	public void setJigyosyoCdError(boolean jigyosyoCdError) {
		this.jigyosyoCdError = jigyosyoCdError;
	}
	/**
	 * @return kaCdError
	 */
	public boolean isKaCdError() {
		return kaCdError;
	}
	/**
	 * @param kaCdError セットする kaCdError
	 */
	public void setKaCdError(boolean kaCdError) {
		this.kaCdError = kaCdError;
	}
	/**
	 * @return tantosyaCdError
	 */
	public boolean isTantosyaCdError() {
		return tantosyaCdError;
	}
	/**
	 * @param tantosyaCdError セットする tantosyaCdError
	 */
	public void setTantosyaCdError(boolean tantosyaCdError) {
		this.tantosyaCdError = tantosyaCdError;
	}
	/**
	 * @return countryCdError
	 */
	public boolean isCountryCdError() {
		return countryCdError;
	}
	/**
	 * @param countryCdError セットする countryCdError
	 */
	public void setCountryCdError(boolean countryCdError) {
		this.countryCdError = countryCdError;
	}
	/**
	 * @return zenkokuOrosiCdError
	 */
	public boolean isZenkokuOrosiCdError() {
		return zenkokuOrosiCdError;
	}
	/**
	 * @param zenkokuOrosiCdError セットする zenkokuOrosiCdError
	 */
	public void setZenkokuOrosiCdError(boolean zenkokuOrosiCdError) {
		this.zenkokuOrosiCdError = zenkokuOrosiCdError;
	}
	/**
	 * @return tokuyakuCdError
	 */
	public boolean isTokuyakuCdError() {
		return tokuyakuCdError;
	}
	/**
	 * @param tokuyakuCdError セットする tokuyakuCdError
	 */
	public void setTokuyakuCdError(boolean tokuyakuCdError) {
		this.tokuyakuCdError = tokuyakuCdError;
	}
	/**
	 * @return sounisakiCdError
	 */
	public boolean isSounisakiCdError() {
		return sounisakiCdError;
	}
	/**
	 * @param sounisakiCdError セットする sounisakiCdError
	 */
	public void setSounisakiCdError(boolean sounisakiCdError) {
		this.sounisakiCdError = sounisakiCdError;
	}
	/**
	 * @return syuhantenCdError
	 */
	public boolean isSyuhantenCdError() {
		return syuhantenCdError;
	}
	/**
	 * @param syuhantenCdError セットする syuhantenCdError
	 */
	public void setSyuhantenCdError(boolean syuhantenCdError) {
		this.syuhantenCdError = syuhantenCdError;
	}
	/**
	 * @return taisyoDateError
	 */
	public boolean isTaisyoDateError() {
		return taisyoDateError;
	}
	/**
	 * @param taisyoDateError セットする taisyoDateError
	 */
	public void setTaisyoDateError(boolean taisyoDateError) {
		this.taisyoDateError = taisyoDateError;
	}
	/**
	 * @return taisyoYearFromError
	 */
	public boolean isTaisyoYearFromError() {
		return taisyoYearFromError;
	}
	/**
	 * @param taisyoYearFromError セットする taisyoYearFromError
	 */
	public void setTaisyoYearFromError(boolean taisyoYearFromError) {
		this.taisyoYearFromError = taisyoYearFromError;
	}
	/**
	 * @return taisyoYearToError
	 */
	public boolean isTaisyoYearToError() {
		return taisyoYearToError;
	}
	/**
	 * @param taisyoYearToError セットする taisyoYearToError
	 */
	public void setTaisyoYearToError(boolean taisyoYearToError) {
		this.taisyoYearToError = taisyoYearToError;
	}
	/**
	 * @return taisyoMonthFromError
	 */
	public boolean isTaisyoMonthFromError() {
		return taisyoMonthFromError;
	}
	/**
	 * @param taisyoMonthFromError セットする taisyoMonthFromError
	 */
	public void setTaisyoMonthFromError(boolean taisyoMonthFromError) {
		this.taisyoMonthFromError = taisyoMonthFromError;
	}
	/**
	 * @return taisyoMonthToError
	 */
	public boolean isTaisyoMonthToError() {
		return taisyoMonthToError;
	}
	/**
	 * @param taisyoMonthToError セットする taisyoMonthToError
	 */
	public void setTaisyoMonthToError(boolean taisyoMonthToError) {
		this.taisyoMonthToError = taisyoMonthToError;
	}
	/**
	 * @return taisyoDateFromError
	 */
	public boolean isTaisyoDateFromError() {
		return taisyoDateFromError;
	}
	/**
	 * @param taisyoDateFromError セットする taisyoDateFromError
	 */
	public void setTaisyoDateFromError(boolean taisyoDateFromError) {
		this.taisyoDateFromError = taisyoDateFromError;
	}
	/**
	 * @return taisyoDateToError
	 */
	public boolean isTaisyoDateToError() {
		return taisyoDateToError;
	}
	/**
	 * @param taisyoDateToError セットする taisyoDateToError
	 */
	public void setTaisyoDateToError(boolean taisyoDateToError) {
		this.taisyoDateToError = taisyoDateToError;
	}

	/**
	 * 登録グループリストを取得
	 * @return 登録グループリスト
	 */
	public JisekiESienListTorokuGroupList getTorokuGroupList() {
		// 集計内容が酒種、酒質、商品の場合のみ返す
		if(PbsUtil.isEqual(this.syukeiNaiyo, SYUKEI_NAIYO_KB_SAKADANE)) {
			return sakadaneList;
		} else if(PbsUtil.isEqual(this.syukeiNaiyo, SYUKEI_NAIYO_KB_SAKESITU)) {
			return sakesituList;
		} else if(PbsUtil.isEqual(this.syukeiNaiyo, SYUKEI_NAIYO_KB_SYOHIN)) {
			return syohinList;
		} else {
			return null;
		}
	}

	/**
	 * 登録のコード値リストを取得
	 * @return コード値
	 */
	public List<String> getTorokuGroupCdList() {
		List<String> ret = new ArrayList<String>();
		JisekiESienListTorokuGroupList list;
		// 集計内容が酒種、酒質、商品の場合のみ返す
		if(PbsUtil.isEqual(this.syukeiNaiyo, SYUKEI_NAIYO_KB_SAKADANE)) {
			list = sakadaneList;
		} else if(PbsUtil.isEqual(this.syukeiNaiyo, SYUKEI_NAIYO_KB_SAKESITU)) {
			list = sakesituList;
		} else if(PbsUtil.isEqual(this.syukeiNaiyo, SYUKEI_NAIYO_KB_SYOHIN)) {
			list = syohinList;
		} else {
			return ret;
		}
		Set<String> set = new TreeSet<String>();
		for (PbsRecord record : list) {
			if(!PbsUtil.isEmpty(((JisekiESienListTorokuGroupRecord)record).getTorokuCd())) {
				set.add(((JisekiESienListTorokuGroupRecord)record).getTorokuCd());
			}
		}
		ret.addAll(set);
		return ret;
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.jigyosyoCd = null;
		this.kaCd = null;
		this.tantosyaCd = null;
		this.countryCd = null;
		this.zenkokuOrosiCd = null;
		this.yosanTaihi = null;
		this.sakesituOp = null;
		this.jyutenyoukiOp = null;
	}
}
