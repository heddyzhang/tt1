package kit.jiseki.ESienList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fb.inf.KitRecord;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.MidasiClass;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.MidasiColInfo;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.MidasiNameClass;

/**
 * 営業支援実績一覧のレコードクラス
 * @author t_kimura
 *
 */
public class JisekiESienListRecord extends KitRecord {

	/** serialVersionUID */
	private static final long serialVersionUID = 3313403155317936661L;

	/** 見出しリスト */
	private List<MidasiColValue> midasiColValueList = new ArrayList<MidasiColValue>();
	/** 累計リスト */
	private List<ColValue> ruikeiColValueList = new ArrayList<ColValue>();
	/** 日付リスト */
	private List<ColValue> dateColValueList = new ArrayList<ColValue>();

	/**
	 * コンストラクタ
	 */
	public JisekiESienListRecord() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param input
	 */
	public JisekiESienListRecord(Map<String, Object> input) {
		super(input);
	}

	/**
	 * コンストラクタ
	 * @param input
	 */
	public JisekiESienListRecord(HashMap<String, Object> input) {
		this((Map<String, Object>) input);
	}

	/**
	 * コンストラクタ
	 * @param input
	 */
	public JisekiESienListRecord(JisekiESienListRecord input) {
		super((KitRecord) input);
		midasiColValueList = input.getMidasiColValueList();
		ruikeiColValueList = input.getRuikeiColValueList();
		dateColValueList = input.getDateColValueList();
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
	 * @return midasiColValueList
	 */
	public List<MidasiColValue> getMidasiColValueList() {
		return midasiColValueList;
	}

	/**
	 * @param midasiColValueList セットする midasiColValueList
	 */
	public void setMidasiColValueList(List<MidasiColValue> midasiColValueList) {
		this.midasiColValueList = midasiColValueList;
	}

	/**
	 * @return ruikeiColValueList
	 */
	public List<ColValue> getRuikeiColValueList() {
		return ruikeiColValueList;
	}

	/**
	 * @param ruikeiColValueList セットする ruikeiColValueList
	 */
	public void setRuikeiColValueList(List<ColValue> ruikeiColValueList) {
		this.ruikeiColValueList = ruikeiColValueList;
	}

	/**
	 * @return dateColValueList
	 */
	public List<ColValue> getDateColValueList() {
		return dateColValueList;
	}

	/**
	 * @param dateColValueList セットする dateColValueList
	 */
	public void setDateColValueList(List<ColValue> dateColValueList) {
		this.dateColValueList = dateColValueList;
	}


	/**
	 * 見出し情報設定
	 * @param midasiColNmList 見出し列情報リスト
	 */
	public void setMidasiColInfo(List<MidasiColInfo> midasiColNmList) {
		for (MidasiColInfo midasiColCdNm : midasiColNmList) {
			midasiColValueList.add(new MidasiColValue(midasiColCdNm));
		}
	}

	/**
	 * 累計情報設定
	 * @param col 列名
	 * @param val 値
	 */
	public void setRuikeiColInfo(String col, String val) {
		put(col, val);
		ruikeiColValueList.add(new ColValue(col));
	}

	/**
	 * 日付情報設定
	 * @param col 列名
	 * @param val 値
	 */
	public void setDateColInfo(String col, String val) {
		put(col, val);
		dateColValueList.add(new ColValue(col));
	}

	/**
	 * 値取得
	 * @param col 列名
	 * @return 値
 	 */
	public String getValue(String col) {
		return (String)get(col);
	}

	/**
	 * 見出し列情報
	 * @author t_kimura
	 *
	 */
	public class MidasiColValue {
		/** コード列情報 */
		ColValue cd;
		/** 名称列情報リスト */
		List<ColValue> nmList = new ArrayList<ColValue>();

		/**
		 * コンストラクタ
		 * @param midasiColCodeName 見出し列情報
		 */
		public MidasiColValue(MidasiColInfo midasiColCodeName) {
			this.cd = new ColValue(midasiColCodeName.getCodeCol());
			for (MidasiNameClass midasiNameClass : midasiColCodeName.getNameColList()) {
				this.nmList.add(new ColValue(midasiNameClass.getNm(), midasiNameClass.getMidasiClass()));
			}
		}

		/**
		 * @return cd
		 */
		public ColValue getCd() {
			return cd;
		}

		/**
		 * @param cd セットする cd
		 */
		public void setCd(ColValue cd) {
			this.cd = cd;
		}

		/**
		 * @return nmList
		 */
		public List<ColValue> getNmList() {
			return nmList;
		}

		/**
		 * @param nmList セットする nmList
		 */
		public void setNmList(List<ColValue> nmList) {
			this.nmList = nmList;
		}
	}

	/**
	 * 列情報
	 * @author t_kimura
	 *
	 */
	public class ColValue {
		/** 列名 */
		private String col;
		/** クラス名 */
		private MidasiClass midasiClass;

		/**
		 * コンストラクタ
		 * @param col 列名
		 */
		public ColValue(String col) {
			this.col = col;
		}

		/**
		 * コンストラクタ
		 * @param col 列名
		 * @param classNm クラス名
		 */
		public ColValue(String col, MidasiClass midasiClass) {
			this.col = col;
			this.midasiClass = midasiClass;
		}

		/**
		 * @return col
		 */
		public String getCol() {
			return col;
		}
		/**
		 * @param col セットする col
		 */
		public void setCol(String col) {
			this.col = col;
		}

		/**
		 * @return midasiClass
		 */
		public MidasiClass getMidasiClass() {
			return midasiClass;
		}

		/**
		 * @param midasiClass セットする midasiClass
		 */
		public void setMidasiClass(MidasiClass midasiClass) {
			this.midasiClass = midasiClass;
		}

		/**
		 * @return value
		 */
		public String getValue() {
			return (String) get(col);
		}
	}
}
