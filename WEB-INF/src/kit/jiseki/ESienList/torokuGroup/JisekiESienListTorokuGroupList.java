package kit.jiseki.ESienList.torokuGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import fb.inf.KitList;
import fb.inf.KitRecord;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 各種登録グループのリスト構造
 * @author t_kimura
 *
 */
public class JisekiESienListTorokuGroupList extends KitList {

	/** serialVersionUID */
	private static final long serialVersionUID = -428129516214091967L;

	/**
	 * コンストラクタ
	 */
	public JisekiESienListTorokuGroupList() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param initSize
	 */
	public JisekiESienListTorokuGroupList(int initSize) {
		super();
		for (int i = 0; i < initSize; i++) {
			this.add(this.createRecord());
		}
	}

	/**
	 * コンストラクタ
	 * @param records PbsRecord[]
	 */
	public JisekiESienListTorokuGroupList(PbsRecord[] records) {
		super(records);
	}

	/**
	 * コンストラクタ
	 * @param record
	 */
	public JisekiESienListTorokuGroupList(JisekiESienListTorokuGroupRecord record) {
		super(record);
	}

	/**
	 * 更新日時取得
	 * @return 更新日時
	 */
	public Date getKousinNitiziDt() {
		if(this.isEmpty()) return null;

		String dt = ((JisekiESienListTorokuGroupRecord)this.get(0)).getKousinNitiziDt();
		if(PbsUtil.isEmpty(dt)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
		try {
			return sdf.parse(dt);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * レコードのエラークリア
	 */
	public void clearRecordError() {
		for (PbsRecord record : this) {
			JisekiESienListTorokuGroupRecord rec = (JisekiESienListTorokuGroupRecord)record;
			rec.setHasError(false);
			rec.setModified(false);
			rec.clearClass();
			//rec.setModifiedClass();
		}
	}

	/**
	 * エラーが存在するかどうか
	 * @return チェック結果
	 */
	public boolean hasError() {
		for (PbsRecord record : this) {
			KitRecord rec = (KitRecord)record;
			if(rec.getHasError()) return true;
		}
		return false;
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitList#createList()
	 */
	@Override
	public KitList createList() {
		return new JisekiESienListTorokuGroupList();
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitList#createRecord()
	 */
	@Override
	public KitRecord createRecord() {
		return new JisekiESienListTorokuGroupRecord();
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitList#createRecord(java.util.HashMap)
	 */
	@Override
	public KitRecord createRecord(HashMap<String, Object> hashmap) {
		return new JisekiESienListTorokuGroupRecord(hashmap);
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitList#createRecord(fb.inf.KitRecord)
	 */
	@Override
	public KitRecord createRecord(KitRecord record) {
		return new JisekiESienListTorokuGroupRecord((JisekiESienListTorokuGroupRecord) record);
	}
}
