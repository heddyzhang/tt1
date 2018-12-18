package jp.co.toshiba.traces.service.k;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.struts.util.ResponseUtil;

import jp.co.toshiba.traces.entity.k.K040KenshinListItem;
import jp.co.toshiba.traces.form.k.K040Form;
import jp.co.toshiba.traces.libs.LibEnv;
import jp.co.toshiba.traces.libs.LibStringUtil;
import jp.co.toshiba.traces.libs.LibXSSFWorkbook;
import jp.co.toshiba.traces.service.common.CommonService;
/**
 * 操作ログ照会画面Service
 * @author NPC
 * @version 1.0
 */
public class K040Service extends CommonService {

	/** テンプレート名 */
	private static final String SHEET_NAME_TEMP = "K040.xlsx";
	/** シート名（健康診断）*/
	private static final String SHEET_NAME_KENSHIN = "健康診断";
	/** 毎ページ表示データ数（健康診断）*/
	private static final int PAGE_SIZE_KENSHIN = 6;
	/** 毎ページ表示行数（健康診断）*/
	private static final int PAGE_ROWS_KENSHIN = 42;
	/** 出力開始行（健康診断）*/
	private static final int 	START_ROW_IDX_KENSHIN = 4;
	/** 出力開始列（健康診断）*/
	private static final int 	START_COL_IDX_KENSHIN = 11;
	/** 出力カラムのセール長さ（健康診断）*/
	private static final int 	COL_CELL_LEN_KENSHIN = 8;
    /**
     * 初期表示
     * @throws Exception
     *
     */
    public void init(K040Form k040Form) throws Exception {

        // S2Container初期化
        SingletonS2ContainerFactory.init();

        /*********************************
         * 画面初期化
        /*********************************
       	//ＴＲＡＣＥＳログイン者ＩＤを自動表示
         */
		k040Form.inputUserId1 = k040Form.cmnLoginID;

		// TODO データ入力者が入力した項目を事前に自動チェック
		// 出力項目の初期値
		List<String> outputList = new ArrayList<String>();
		// 健康診断
		outputList.add("1");
		// 教育
		outputList.add("2");
		// 入所退所
		outputList.add("3");
		// 日線量
		outputList.add("4");
		// 外部線量
		outputList.add("5");
		// 内部線量
		outputList.add("6");

		k040Form.outputItems = (String[]) outputList.toArray(new String[0]);
    }


    /**
     * Excel出力
     * @param form
     * @throws Exception
     */
    public void outputExcel(K040Form form, String fileName) throws Exception {

        List<K040KenshinListItem>  k040KenshinList  = new ArrayList<K040KenshinListItem>();
        // 検索条件を作成
        Map<String, Object> param = new HashMap<String, Object>();

        // 入力日（FROM）
		param.put("dateFrom", LibStringUtil.getDatetoString(LibStringUtil.getDateWa(form.inputDateFrom), "yyyy/MM/dd"));
        // 入力日（ＴＯ）
        param.put("dateTo", LibStringUtil.getDatetoString(LibStringUtil.getDateWa(form.inputDateTo), "yyyy/MM/dd"));
        // TODO 入力者ID
        param.put("userID", "03910000', '03910000");

    	try {

            LibEnv env = new LibEnv();
            // Excelテンプレートパスの取得
            String strExcelDirPath = env.getExcelPath();
            // EXCELテンプレートファイル読み込み
            LibXSSFWorkbook libWorkbook = new LibXSSFWorkbook();
            XSSFWorkbook workbook = libWorkbook.openXSSFWorkbook(strExcelDirPath + "/"+ SHEET_NAME_TEMP);

            // 出力リスト
            List<String> outputlist = Arrays.asList(form.outputItems);

			// 健康診断を出力した場合
			if (outputlist.contains("1")) {
				// 健康診断のデータリスト取得
				k040KenshinList = jdbcManager.selectBySqlFile(K040KenshinListItem.class,
						"data/k040Kenshin.sql", param).getResultList();

				// 健康診断シートの内容を出力
				outputKenshin(k040KenshinList, workbook.getSheet(SHEET_NAME_KENSHIN));
			} else {
            	// 該当シートを削除します。
            	workbook.removeName(SHEET_NAME_KENSHIN);
			}

	        // Excelファイルをレスポンスに出力
	        libWorkbook.outputExcel(ResponseUtil.getResponse(), workbook, fileName);

		} catch (Exception e) {
			// TODO
			throw e;
		}

    }

    /**
     * 健康診断を出力
     * @param k040KenshinList
     * @param sheet
     * @throws Exception
     */
    private void outputKenshin(List<K040KenshinListItem> k040KenshinList, XSSFSheet sheet) throws Exception {

    	// 変数の初期化
        int pageSum= 0;
        int pPositioin= 0;
        int nowPage = 0;
        int rowIdx = 0;
        int colIdx = 0;
        LibXSSFWorkbook libWorkbook = new LibXSSFWorkbook();

        // データが不存在した場合
        if (k040KenshinList.size() == 0) {
        	return;
        }

        // 総ページ数を計算
		pageSum = (k040KenshinList.size() + PAGE_SIZE_KENSHIN - 1) / PAGE_SIZE_KENSHIN;

		// ページ数を繰り返し
		for (int i = 2; i <= pageSum; i++) {

			// 改ページ後の行位置を計算
			pPositioin = (i - 1) * PAGE_ROWS_KENSHIN;
			// 項目タイトルごとコピー
			copyRows(0, PAGE_ROWS_KENSHIN - 1, pPositioin, sheet);
			// 改ページ印刷を設定
		}

		// 現在のページ
		nowPage = 1;
		// 出力開始行
		rowIdx = START_ROW_IDX_KENSHIN;
		// 出力開始列
		colIdx = START_COL_IDX_KENSHIN;

		for (K040KenshinListItem k040Kenshin : k040KenshinList) {

			// 改ページの場合
			if (colIdx == START_COL_IDX_KENSHIN + PAGE_SIZE_KENSHIN * COL_CELL_LEN_KENSHIN) {
				// 現在のページ数
				nowPage++;
				// 出力開始行を計算
				rowIdx = START_ROW_IDX_KENSHIN + PAGE_ROWS_KENSHIN * (nowPage - 1);
				// 出力開始列を初期化
				colIdx = START_COL_IDX_KENSHIN;
			}

			// 管理番号（中登番号）
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.chutoNo);
			// 氏名
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.fullname);
			// 健診区分
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.kenshinKbn);
			// 受診区分
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.jukenKbn);
			// 電離健康診断年月日
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.kenshinDate);
			// 白血球数
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.hakekyuSu);
			// リンパ球
			libWorkbook.setCellVal(sheet, rowIdx++, colIdx, k040Kenshin.rinpa);
			// 単球
			libWorkbook.setCellVal(sheet, rowIdx++, colIdx, k040Kenshin.tankyu);
			// 異形リンパ球
			libWorkbook.setCellVal(sheet, rowIdx++, colIdx, k040Kenshin.sonota);
			// 好中球桿状核
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.kanjo);
			// 好中球分葉核
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.bunyo);
			// 好中球合計
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.kochukyukei);
			// 好酸球
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.kosan);
			// 好塩基球
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.koenki);
			// 赤血球数
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.sekekyu);
			// 血色素量
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.shikiso);
			// ヘマトクリット
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.hemato);
			// その他
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.ketsuta);
			// 水晶体の混濁
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.coMe);
			// 発赤
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.coHaseki);
			// 乾燥又は縦じわ
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.coKanso);
			// 潰瘍
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.coKaiyo);
			// 爪の異常
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.coTsume);
			// その他の検査
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.coSonota);
			// 全身的所見
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.coZenshin);
			// 自覚的訴え
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.coJikaku);
			// 参考事項
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.coSanko);
			// 医師の診断
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.coShindan);
			// 病院・検査機関名
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.kensaKikanMei);
			// 診断を行った医師名
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.shindanIshi);
			// 医師の意見
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.coIken);
			// 病院・検査機関名
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.ikenKensaKikanMei);
			// 意見を述べた医師名
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.ikenIshi);
			// 判定
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.hanteiKbn);
			// 労基報告（サイト）
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.rokiSiteNo);
			// 労基報告（所属）
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.shozokuCd);
			// 入力（更新）日時
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.kosinDate);
			// 入力者
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.kosinName);

			// 次の出力開始行を計算
			rowIdx = START_ROW_IDX_KENSHIN + PAGE_ROWS_KENSHIN * (nowPage - 1);

			// 次の出力開始列を計算
			colIdx = colIdx + COL_CELL_LEN_KENSHIN;
		}
    }

    /**
     * 複数行をコピー
     * @param pStartRow
     * @param pEndRow
     * @param pPosition
     * @param sheet
     */
	public void copyRows(int pStartRow, int pEndRow, int pPosition, XSSFSheet sheet) {

		int targetRowFrom;
		int targetRowTo;
		int columnCount;
		CellRangeAddress region = null;
		int i;
		int j;
		if (pStartRow == -1 || pEndRow == -1) {
			return;
		}
		//
		for (i = 0; i < sheet.getNumMergedRegions(); i++) {
			region = sheet.getMergedRegion(i);
			if ((region.getFirstRow() >= pStartRow)
					&& (region.getLastRow() <= pEndRow)) {
				targetRowFrom = region.getFirstRow() - pStartRow + pPosition;
				targetRowTo = region.getLastRow() - pStartRow + pPosition;
				CellRangeAddress newRegion = region.copy();
				newRegion.setFirstRow(targetRowFrom);
				newRegion.setFirstColumn(region.getFirstColumn());
				newRegion.setLastRow(targetRowTo);
				newRegion.setLastColumn(region.getLastColumn());
				sheet.addMergedRegion(newRegion);
			}
		}

		for (i = pStartRow; i <= pEndRow; i++) {
			XSSFRow sourceRow = sheet.getRow(i);
			columnCount = sourceRow.getLastCellNum();
			if (sourceRow != null) {
				XSSFRow newRow = sheet.createRow(pPosition - pStartRow + i);
				newRow.setHeight(sourceRow.getHeight());
				for (j = 0; j < columnCount; j++) {
					XSSFCell templateCell = sourceRow.getCell(j);
					if (templateCell != null) {
						XSSFCell newCell = newRow.createCell(j);
						copyCell(templateCell, newCell);
					}
				}
			}
		}
	}

	/**
	 * セールをコピ－
	 * @param srcCell
	 * @param distCell
	 */
	private void copyCell(XSSFCell srcCell, XSSFCell distCell) {
		distCell.setCellStyle(srcCell.getCellStyle());
		if (srcCell.getCellComment() != null) {
			distCell.setCellComment(srcCell.getCellComment());
		}
		int srcCellType = srcCell.getCellType();
		distCell.setCellType(srcCellType);
		if (srcCellType == XSSFCell.CELL_TYPE_NUMERIC) {
			if (HSSFDateUtil.isCellDateFormatted(srcCell)) {
				distCell.setCellValue(srcCell.getDateCellValue());
			} else {
				distCell.setCellValue(srcCell.getNumericCellValue());
			}
		} else if (srcCellType == XSSFCell.CELL_TYPE_STRING) {
			distCell.setCellValue(srcCell.getRichStringCellValue());
		} else if (srcCellType == XSSFCell.CELL_TYPE_BLANK) {
			// nothing21
		} else if (srcCellType == XSSFCell.CELL_TYPE_BOOLEAN) {
			distCell.setCellValue(srcCell.getBooleanCellValue());
		} else if (srcCellType == XSSFCell.CELL_TYPE_ERROR) {
			distCell.setCellErrorValue(srcCell.getErrorCellValue());
		} else if (srcCellType == XSSFCell.CELL_TYPE_FORMULA) {
			distCell.setCellFormula(srcCell.getCellFormula());
		} else { // nothing

		}
	}

}
