package kit.jiseki.ESienList;

import java.util.HashMap;

import fb.inf.KitList;
import fb.inf.KitRecord;
import fb.inf.pbs.PbsRecord;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.MidasiClass;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.MidasiColInfo;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.MidasiNameClass;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.TableHeaderInfo;

/**
 * 営業支援実績一覧のリスト
 * @author t_kimura
 *
 */
public class JisekiESienListList extends KitList {

	/** serialVersionUID */
	private static final long serialVersionUID = -7299557090772945298L;

	/** HtmlのTable（一覧表示）ヘッダ情報 */
	private TableHeaderInfo tableHeaderInfo;

	/**
	 * コンストラクタ
	 */
	public JisekiESienListList() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param initSize
	 */
	public JisekiESienListList(int initSize) {
		for (int ii = 0; ii < initSize; ii++) {
			this.add(this.createRecord());
		}
	}

	/**
	 * コンストラクタ
	 *
	 * @param records PbsRecord[]
	 */
	public JisekiESienListList(PbsRecord[] records) {
		super(records);
	}

	/**
	 * コンストラクタ
	 *
	 * @param records PbsRecord[]
	 * @param tableHeaderTagInfo HtmlのTable（一覧表示）ヘッダ情報
	 */
	public JisekiESienListList(PbsRecord[] records, TableHeaderInfo tableHeaderTagInfo) {
		super(records);
		this.tableHeaderInfo = tableHeaderTagInfo;
	}

	/**
	 * コンストラクタ
	 * @param record
	 */
	public JisekiESienListList(JisekiESienListRecord record) {
		super(record);
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitList#createList()
	 */
	@Override
	public KitList createList() {
		return new JisekiESienListList();
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitList#createRecord()
	 */
	@Override
	public KitRecord createRecord() {
		return new JisekiESienListRecord();
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitList#createRecord(java.util.HashMap)
	 */
	@Override
	public KitRecord createRecord(HashMap<String, Object> record) {
		return new JisekiESienListRecord(record);
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitList#createRecord(fb.inf.KitRecord)
	 */
	@Override
	public KitRecord createRecord(KitRecord record) {
		return new JisekiESienListRecord((JisekiESienListRecord) record);
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitList#copy(int, int)
	 */
	@Override
	public KitList copy(int from, int to) {
		JisekiESienListList list = (JisekiESienListList) super.copy(from,to);
		return list;
	}

	/**
	 * 1レコード目取得
	 * @return 1レコード
	 */
	public JisekiESienListRecord getOneRec() {
		return (JisekiESienListRecord)get(0);
	}

	/**
	 * 一覧ヘッダ部幅取得
	 * @return 幅
	 */
	public int getHeaderWidth() {
		return (getMidasiCnt(MidasiClass.LARGE_WIDTH_MJ) * 230) + (getMidasiCnt(MidasiClass.MEDIUM_WIDTH_MJ) * 170) + (getMidasiCnt(MidasiClass.SMALL_WIDTH_MJ, MidasiClass.SMALL_WIDTH_NM) * 80) + (getRuikeiDateCnt() * 130) + 10;
	}

	/**
	 * @return tableHeaderInfo
	 */
	public TableHeaderInfo getTableHeaderInfo() {
		return tableHeaderInfo;
	}

	/**
	 * 累計・日付エリアの件数取得
	 * @return 件数
	 */
	private int getRuikeiDateCnt() {
		if(tableHeaderInfo.isHeader1Line()) {
			return tableHeaderInfo.getDateHeaderList().size() + 1;
		} else {
			if(tableHeaderInfo.isRuikeiHeader1Line()) {
				return (tableHeaderInfo.getDateHeaderList().size() * 3) + 1;
			} else {
				return (tableHeaderInfo.getDateHeaderList().size() * 3) + tableHeaderInfo.getRuikeiHeader().getRow2ColList().size();
			}
		}
	}

	/**
	 * 見出しの各幅件数取得
	 * @param midasiClasses 見出しクラス定義
	 */
	private int getMidasiCnt(MidasiClass... midasiClasses) {
		int cnt = 0;
		for (MidasiColInfo midasiColInfo : tableHeaderInfo.getMidasiHeaderList()) {
			for (MidasiNameClass midasiNameClass : midasiColInfo.getNameColList()) {
				for (MidasiClass midasiClass : midasiClasses) {
					if(midasiNameClass.getMidasiClass() == midasiClass) {
						cnt++;
					}
				}
			}
		}
		return cnt;
	}
}

