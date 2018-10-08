package kit.jiseki.ESienList.torokuGroup;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import fb.inf.KitEditForm;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 各種登録グループの編集フォーム
 * @author t_kimura
 *
 */
public class JisekiESienListTorokuGroup_EditForm extends KitEditForm {

	/** serialVersionUID */
	private static final long serialVersionUID = -5150103774485282130L;

	/** 登録グループレコード */
	private JisekiESienListTorokuGroupList torokuGroupList = new JisekiESienListTorokuGroupList();

	/**
	 * @return torokuGroupList
	 */
	public JisekiESienListTorokuGroupList getTorokuGroupList() {
		return torokuGroupList;
	}

	/**
	 * @param torokuGroupList セットする torokuGroupList
	 */
	public void setTorokuGroupList(JisekiESienListTorokuGroupList torokuGroupList) {
		this.torokuGroupList = torokuGroupList;
	}

	/**
	 * 登録グループレコード取得
	 * @param index インデックス
	 * @return 登録グループレコード
	 */
	public JisekiESienListTorokuGroupRecord getJisekiESienListTorokuGroupRecord(int index) {
		return ((JisekiESienListTorokuGroupRecord) torokuGroupList.get(index));
	}

	/**
	 * 登録グループコードリスト取得
	 * @return 登録グループコードリスト
	 */
	public List<String> getTorokuCdList() {
		 Set<String> torokuCdList = new LinkedHashSet<String>();
		for (PbsRecord record : this.torokuGroupList) {
			String cd = ((JisekiESienListTorokuGroupRecord)record).getTorokuCd();
			if(!PbsUtil.isEmpty(cd)) {
				torokuCdList.add(cd);
			}
		}
		return new ArrayList<String>(torokuCdList);
	}
}
