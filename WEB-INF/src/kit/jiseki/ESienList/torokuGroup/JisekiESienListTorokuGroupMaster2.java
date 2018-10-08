package kit.jiseki.ESienList.torokuGroup;

import static fb.inf.pbs.IPbsConst.SQL_CRLF;

import java.util.List;

import fb.com.ComUserSession;
import fb.com.Records.FbMastrKbnHanbaiBunruiRecord;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;
import kit.jiseki.ESienList.JisekiESienList_CheckService;

/**
 * 酒質用の登録グループ実装クラス
 * @author t_kimura
 *
 */
public class JisekiESienListTorokuGroupMaster2 implements IJisekiESienListTorokuGroupMaster {

	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.torokuGroup.IJisekiESienListTorokuGroupMaster#getTitle()
	 */
	@Override
	public String getTitle() {
		return PbsUtil.getMessageResourceString("com.title.torokuGroupSakesitu");
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
		sql.append("  , BUNRUI.HANBAI_BUNRUI_RNM AS TOROKU_NM \n");
		sql.append("  , BUNRUI.HANBAI_BUNRUI_RNM AS TOROKU_NM_VIEW \n");
		sql.append("FROM \n");
		sql.append("    T_TOROKU_GROUP GRP \n");
		sql.append("    INNER JOIN M_KBN_HANBAI_BUNRUI BUNRUI ON GRP.TOROKU_CD = BUNRUI.HANBAI_BUNRUI_CD \n");
		sql.append("WHERE \n");
		sql.append("    GRP.KAISYA_CD = ? \n");
		sql.append("    AND GRP.SGYOSYA_CD = ? \n");
		sql.append("    AND GRP.TOROKU_MASTER = ? \n");
		sql.append("    AND GRP.TOROKU_PTN = ? \n");
		sql.append("ORDER BY \n");
		sql.append("    GRP.TOROKU_NO \n");
		bindList.add(comUserSession.getKaisyaCd());
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
		return checkService.isExistMastrKbnHanbaiBunrui(torokuCd, index, parent);
	}


	/* (非 Javadoc)
	 * @see kit.jiseki.ESienList.torokuGroup.IJisekiESienListTorokuGroupMaster#createXmlTagByTorokuCd(kit.jiseki.ESienList.JisekiESienList_CheckService, fb.com.ComUserSession, java.lang.String)
	 */
	@Override
	public String createXmlTagByTorokuCd(JisekiESienList_CheckService checkService, ComUserSession comUserSession, String torokuCd) {
		FbMastrKbnHanbaiBunruiRecord record = checkService.getMastrKbnHanbaiBunruiRecord(torokuCd);
		// checkService.getMstRiyoKaValue(record, record.getHanbaiBunruiRnm())
		return "<torokuNm>" + checkService.getMstExistValue(record, record.getHanbaiBunruiRnm()) + "</torokuNm>\n";
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
				FbMastrKbnHanbaiBunruiRecord hanbaiBunruiRecord = checkService.getMastrKbnHanbaiBunruiRecord(rec.getTorokuCd());
				rec.setTorokuNm(checkService.getMstExistValue(hanbaiBunruiRecord, hanbaiBunruiRecord.getHanbaiBunruiRnm()));
			}
		}
	}
}
