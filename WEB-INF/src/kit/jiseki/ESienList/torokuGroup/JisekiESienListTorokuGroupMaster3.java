package kit.jiseki.ESienList.torokuGroup;

import static fb.com.IKitComConst.SYOHIN_KB_SYOHIN;
import static fb.com.IKitComConst.SYOHIN_KB_SYOKUHIN;
import static fb.com.IKitComConst.SYOHIN_KB_SYURUI;
import static fb.com.IKitComConstHM.MST_REC_NOT_EXISTENCE;
import static fb.inf.pbs.IPbsConst.SQL_CRLF;

import java.util.List;

import fb.com.ComUserSession;
import fb.com.ComUtil;
import fb.com.Records.FbMastrSeihinRecord;
import fb.com.Records.FbMastrSiirehinRecord;
import fb.com.Records.FbMastrSyohinRecord;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;
import kit.jiseki.ESienList.JisekiESienList_CheckService;

/**
 * 商品用の登録グループ実装クラス
 * @author t_kimura
 *
 */
public class JisekiESienListTorokuGroupMaster3 implements IJisekiESienListTorokuGroupMaster {

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.torokuGroup.IJisekiESienListTorokuGroupMaster#getTitle()
	 */
	@Override
	public String getTitle() {
		return PbsUtil.getMessageResourceString("com.title.torokuGroupSyohin");
	}

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.torokuGroup.IJisekiESienListTorokuGroupMaster#getSearchSql(fb.com.ComUserSession, kit.jiseki.ESienList.torokuGroup.JisekiESienListTorokuGroup_SearchForm, java.util.List)
	 */
	@Override
	public String getSearchSql(ComUserSession comUserSession, JisekiESienListTorokuGroup_SearchForm searchForm, List<String> bindList) {
		StringBuilder sql = new StringBuilder(SQL_CRLF);
		sql.append("SELECT \n");
		sql.append("    GRP.TOROKU_CD AS TOROKU_CD \n");
		sql.append("  , GRP.TOROKU_CD AS TOROKU_CD_VIEW \n");
		sql.append("  , GRP.KOUSIN_NITIZI_DT AS KOUSIN_NITIZI_DT \n");
		sql.append("  , CASE WHEN SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYURUI).append("' OR SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYOKUHIN).append("' OR SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYOHIN).append("' THEN SEIHIN.SEIHIN_NM_REPORT2 ELSE SIIREHIN.SIIREHIN_NM_REPORT2 END AS TOROKU_NM \n");
		sql.append("  , CASE WHEN SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYURUI).append("' OR SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYOKUHIN).append("' OR SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYOHIN).append("' THEN SEIHIN.YOUKI_KIGO_NM2 ELSE NULL END AS YOUKI_NM \n");
		sql.append("  , CASE WHEN SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYURUI).append("' OR SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYOKUHIN).append("' OR SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYOHIN).append("' THEN SEIHIN.CASE_IRISU ELSE SIIREHIN.CASE_IRISU END AS CASE_IRISU \n");
		sql.append("  , CASE WHEN SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYURUI).append("' OR SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYOKUHIN).append("' OR SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYOHIN).append("' THEN LTRIM(TO_CHAR(SEIHIN.TNPN_VOL,'99990.000')) ELSE NULL END AS TNPN_VOL \n");
		sql.append("  , CASE WHEN SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYURUI).append("' OR SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYOKUHIN).append("' OR SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYOHIN).append("' THEN SEIHIN.SEIHIN_NM_REPORT2 ELSE SIIREHIN.SIIREHIN_NM_REPORT2 END AS TOROKU_NM_VIEW \n");
		sql.append("  , CASE WHEN SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYURUI).append("' OR SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYOKUHIN).append("' OR SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYOHIN).append("' THEN SEIHIN.YOUKI_KIGO_NM2 ELSE NULL END AS YOUKI_NM_VIEW \n");
		sql.append("  , CASE WHEN SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYURUI).append("' OR SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYOKUHIN).append("' OR SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYOHIN).append("' THEN SEIHIN.CASE_IRISU ELSE SIIREHIN.CASE_IRISU END AS CASE_IRISU_VIEW \n");
		sql.append("  , CASE WHEN SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYURUI).append("' OR SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYOKUHIN).append("' OR SYOHIN.BUPIN_KBN = '").append(SYOHIN_KB_SYOHIN).append("' THEN LTRIM(TO_CHAR(SEIHIN.TNPN_VOL,'99990.000')) ELSE NULL END AS TNPN_VOL_VIEW \n");
		sql.append("FROM \n");
		sql.append("    T_TOROKU_GROUP GRP \n");
		sql.append("    INNER JOIN M_SYOHIN SYOHIN ON GRP.KAISYA_CD = SYOHIN.KAISYA_CD AND GRP.TOROKU_CD = SYOHIN.SHOHIN_CD \n");
		sql.append("    LEFT OUTER JOIN M_SEIHIN SEIHIN ON SYOHIN.KAISYA_CD = SEIHIN.KAISYA_CD AND SYOHIN.KTKSY_CD = SEIHIN.KTKSY_CD AND SYOHIN.SEIHIN_CD = SEIHIN.SEIHIN_CD \n");
		sql.append("    LEFT OUTER JOIN M_SIIREHIN SIIREHIN ON SYOHIN.KAISYA_CD = SIIREHIN.KAISYA_CD AND SYOHIN.KTKSY_CD = SIIREHIN.KTKSY_CD AND SYOHIN.SIIRESAKI_CD = SIIREHIN.SIIRESAKI_CD AND SYOHIN.SIIREHIN_CD = SIIREHIN.SIIREHIN_CD \n");
		sql.append("WHERE \n");
		sql.append("    GRP.KAISYA_CD = ? \n");
		sql.append("    AND SYOHIN.KAISYA_CD = ? \n");
		sql.append("    AND SYOHIN.KTKSY_CD = ? \n");
//		sql.append("    AND SEIHIN.KAISYA_CD = ? \n");
//		sql.append("    AND SEIHIN.KTKSY_CD = ? \n");
		sql.append("    AND GRP.SGYOSYA_CD = ? \n");
		sql.append("    AND GRP.TOROKU_MASTER = ? \n");
		sql.append("    AND GRP.TOROKU_PTN = ? \n");
		sql.append("ORDER BY \n");
		sql.append("    GRP.TOROKU_NO \n");
		bindList.add(comUserSession.getKaisyaCd());
		bindList.add(comUserSession.getKaisyaCd());
		bindList.add(comUserSession.getKtksyCd());
//		bindList.add(comUserSession.getKaisyaCd());
//		bindList.add(comUserSession.getKtksyCd());
		bindList.add(searchForm.getSgyosyaCd());
		bindList.add(searchForm.getTorokuMaster());
		bindList.add(searchForm.getTorokuPtn());
		return sql.toString();
	}

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.torokuGroup.IJisekiESienListTorokuGroupMaster#checkTorokuCd(kit.jiseki.ESienList.JisekiESienList_CheckService, fb.com.ComUserSession, java.lang.String, int, boolean)
	 */
	@Override
	public boolean checkTorokuCd(JisekiESienList_CheckService checkService, ComUserSession comUserSession, String torokuCd, int index, boolean parent) {
		return checkService.isExistMastrSyohin(comUserSession.getKaisyaCd(), comUserSession.getKtksyCd(), torokuCd, index, parent);
	}

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.torokuGroup.IJisekiESienListTorokuGroupMaster#createXmlTagByTorokuCd(kit.jiseki.ESienList.JisekiESienList_CheckService, fb.com.ComUserSession, java.lang.String)
	 */
	@Override
	public String createXmlTagByTorokuCd(JisekiESienList_CheckService checkService, ComUserSession comUserSession, String torokuCd) {
		StringBuilder tag = new StringBuilder();
		JisekiESienListTorokuGroupRecord syohinInfo = getSyohinInfoByTorokuCd(checkService, comUserSession, torokuCd);
		tag.append("<torokuNm>" + syohinInfo.getTorokuNm() + "</torokuNm>\n");
		tag.append("<youkiNm>" + syohinInfo.getYoukiNm() + "</youkiNm>\n");
		tag.append("<caseIrisu>" + syohinInfo.getCaseIrisu() + "</caseIrisu>\n");
		tag.append("<tnpnVol>" + syohinInfo.getTnpnVol() + "</tnpnVol>\n");
		return tag.toString();
	}

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.torokuGroup.IJisekiESienListTorokuGroupMaster#setTorokuNms(kit.jiseki.ESienList.JisekiESienList_CheckService, fb.com.ComUserSession, kit.jiseki.ESienList.torokuGroup.JisekiESienListTorokuGroupList)
	 */
	@Override
	public void setTorokuNms(JisekiESienList_CheckService checkService, ComUserSession comUserSession, JisekiESienListTorokuGroupList torokuGroupList) {
		// postされなかった未設定の名称のみ設定する
		for (PbsRecord record : torokuGroupList) {
			JisekiESienListTorokuGroupRecord rec = (JisekiESienListTorokuGroupRecord)record;
			if(checkService.judgeGetNm(rec.getTorokuCd(), rec.getTorokuNm())) {
				JisekiESienListTorokuGroupRecord syohinInfo = getSyohinInfoByTorokuCd(checkService, comUserSession, rec.getTorokuCd());
				rec.setTorokuNm(syohinInfo.getTorokuNm());
				rec.setYoukiNm(syohinInfo.getYoukiNm());
				rec.setCaseIrisu(syohinInfo.getCaseIrisu());
				rec.setTnpnVol(syohinInfo.getTnpnVol());
			}
		}
	}

	/**
	 * 商品情報取得
	 * @param checkService チェックサービス
	 * @param comUserSession ログインユーザセッション情報
	 * @param torokuCd 登録コード
	 * @return 商品情報
	 */
	private JisekiESienListTorokuGroupRecord getSyohinInfoByTorokuCd(JisekiESienList_CheckService checkService, ComUserSession comUserSession, String torokuCd) {
		JisekiESienListTorokuGroupRecord rec = new JisekiESienListTorokuGroupRecord();
		String torokuNm = "";
		String youkiNm = "";
		String caseIrisu = "";
		String tnpnVol = "";
		FbMastrSyohinRecord syohinRecord = checkService.getMastrSyohinRecord(comUserSession.getKaisyaCd(), comUserSession.getKtksyCd(), torokuCd);
		if (ComUtil.isMstExistence(syohinRecord, MST_REC_NOT_EXISTENCE)) {
			torokuNm = PbsUtil.getMessageResourceString("com.text.noValue");
		} else {
			if(PbsUtil.isEqual(syohinRecord.getBupinKbn(), SYOHIN_KB_SYURUI) || PbsUtil.isEqual(syohinRecord.getBupinKbn(), SYOHIN_KB_SYOKUHIN) || PbsUtil.isEqual(syohinRecord.getBupinKbn(), SYOHIN_KB_SYOHIN)) {
				FbMastrSeihinRecord seihinRecord = checkService.getMastrSeihinnRecord(comUserSession.getKaisyaCd(), syohinRecord.getKtksyCd(), syohinRecord.getSeihinCd());
				if (!ComUtil.isMstExistence(seihinRecord, MST_REC_NOT_EXISTENCE)) {
					torokuNm =  seihinRecord.getSeihinNmReport2();
					youkiNm = seihinRecord.getYoukiKigoNm2();
					caseIrisu = seihinRecord.getCaseIrisu() == null ? "" : seihinRecord.getCaseIrisu();
					tnpnVol = seihinRecord.getTnpnVol() == null ? "" : seihinRecord.getTnpnVol();
				} else {
					torokuNm = PbsUtil.getMessageResourceString("com.text.noValue");
				}
			} else {
				FbMastrSiirehinRecord siirehinRecord = checkService.getMastrSiirehinRecord(comUserSession.getKaisyaCd(), syohinRecord.getKtksyCd(), syohinRecord.getSiiresakiCd(), syohinRecord.getSiirehinCd());
				if (!ComUtil.isMstExistence(siirehinRecord, MST_REC_NOT_EXISTENCE)) {
					torokuNm =  siirehinRecord.getSiirehinNmReport2();
					youkiNm = "";
					caseIrisu = siirehinRecord.getCaseIrisu() == null ? "" : siirehinRecord.getCaseIrisu();
					tnpnVol = "";
				} else {
					torokuNm = PbsUtil.getMessageResourceString("com.text.noValue");
				}
			}
		}
		rec.setTorokuNm(torokuNm);
		rec.setYoukiNm(youkiNm);
		rec.setCaseIrisu(caseIrisu);
		rec.setTnpnVol(tnpnVol);
		return rec;
	}
}
