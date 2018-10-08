package kit.jiseki.ESienList;

import static fb.com.IKitComCsvConst.CSV_NO_CSV_JISSEKI_E_SIEN_LIST;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import fb.com.ComUserSession;
import fb.com.exception.KitComException;
import fb.inf.KitPrintWriter;
import fb.inf.exception.DataNotFoundException;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;
import kit.jiseki.ESienList.JisekiESienListRecord.ColValue;
import kit.jiseki.ESienList.JisekiESienListRecord.MidasiColValue;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.ColInfo;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.MidasiColInfo;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.MidasiNameClass;
import kit.jiseki.ESienList.JisekiESienList_SearchHelper.TableHeaderInfo;

/**
 * 営業支援実績一覧の帳票印刷用データファイルの出力機能クラス
 * @author t_kimura
 *
 */
public class JisekiESienList_KitPrintWriter extends KitPrintWriter {

	/** serialVersionUID */
	private static final long serialVersionUID = -82609912524621120L;

	// DecimalFormat
	private DecimalFormat df = new DecimalFormat("#,###");

	/**
	 * コンストラクタ
	 * @param comUserSession ログインユーザセッション情報
	 * @throws KitComException
	 */
	public JisekiESienList_KitPrintWriter(ComUserSession comUserSession) throws KitComException {
		super(KitPrintWriter.DOWNLOAD_REQ, CSV_NO_CSV_JISSEKI_E_SIEN_LIST, comUserSession, false, true, "xlsx");
	}

	/**
	 * Excelファイル出力
	 * @param list 営業支援実績一覧のリスト
	 * @param searchConditionHeaderList 検索条件ヘッダリスト
	 * @param searchConditionList 検索条件リスト
	 * @throws DataNotFoundException
	 * @throws KitComException
	 */
	public void addOutputRecordAndExecutePoi(JisekiESienListList list, List<String> searchConditionHeaderList, List<String> searchConditionList) throws DataNotFoundException, KitComException {
		if(list == null || list.size() == 0) {
			throw new DataNotFoundException("DATA NOT FOUND!!");
		}
		// ファイル書き出し用準備
		OutputStream os = null;
		File tmpFile = new File(getTemporaryPrintFileFullPath());
		try {
		    // ---------------------
			// Excelオブジェクト作成
		    // ---------------------
			Workbook book =  new SXSSFWorkbook(); // Excel2007以降（XLSX形式）メモリ消費量を抑える

			// シートを作成
			Sheet sheet = book.createSheet(getCsvName());

			// ヘッダ部出力
			writeHeader(list, book, sheet, searchConditionHeaderList);

			// データ部出力
			writeData(list, book, sheet, searchConditionList);

		    // ファイル出力
		    os = new FileOutputStream(tmpFile);
		    book.write(os);
		    os.close();
		} catch (IOException ex) {
			throw new RuntimeException("io exception", ex);
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					throw new RuntimeException("io exception when close", e);
				}
			}
			os=null;

		}
		copyAndRename(tmpFile);
	}


	/**
	 * ヘッダ部出力
	 * @param list 営業支援実績一覧のリスト
	 * @param book Book
	 * @param sheet Sheet
	 * @param searchConditionHeaderList 検索条件ヘッダリスト
	 */
	private void writeHeader(JisekiESienListList list, Workbook book, Sheet sheet, List<String> searchConditionHeaderList) {

		// ヘッダ情報
		TableHeaderInfo tableHeaderInfo = list.getTableHeaderInfo();

	    // スタイル
		CellStyle cellStyle = getHeaderCellStyle(book);

		// ヘッダ１行
		if(tableHeaderInfo.isHeader1Line()) {
			// ヘッダ出力
			write1LineHeader(tableHeaderInfo, sheet, searchConditionHeaderList, cellStyle);
			// ヘッダ1行固定
		    sheet.createFreezePane(0, 1, 0, 1);
		} else {
			// ヘッダ出力
			write2LineHeader(tableHeaderInfo, sheet, searchConditionHeaderList, cellStyle);
			// ヘッダ2行固定
		    sheet.createFreezePane(0, 2, 0, 2);
		}
	}

	/**
	 * 1行用ヘッダ部出力
	 * @param tableHeaderInfo ヘッダ情報
	 * @param sheet Sheet
	 * @param searchConditionHeaderList 検索条件ヘッダリスト
	 * @param cellStyle セルスタイル
	 */
	private void write1LineHeader(TableHeaderInfo tableHeaderInfo, Sheet sheet, List<String> searchConditionHeaderList, CellStyle cellStyle) {

		List<String> headerList = new ArrayList<>();

		// 見出し
		List<MidasiColInfo> midasiHeaderList = tableHeaderInfo.getMidasiHeaderList();
		// 累計
		ColInfo ruikeiHeader = tableHeaderInfo.getRuikeiHeader();
		// 日付
		List<ColInfo> dateHeaderList = tableHeaderInfo.getDateHeaderList();

		// 1行目
		Row rowHd = sheet.createRow(0);

		// 見出し
		for (MidasiColInfo codeName : midasiHeaderList) {
			headerList.add(codeName.getCodeCol());
			for (MidasiNameClass nm : codeName.getNameColList()) {
				headerList.add(nm.getNm());
			}
		}

		// 累計
		headerList.add(ruikeiHeader.getRow1Col());

		// 日付
		for (ColInfo colInfo : dateHeaderList) {
			headerList.add(colInfo.getRow1Col());
		}

		// 検索条件
		for (String s : searchConditionHeaderList) {
			headerList.add(s);
		}

		// 出力
		for (int i = 0; i < headerList.size(); i++) {
			writeCell(rowHd, cellStyle, i, headerList.get(i));
	    }
	}

	/**
	 * 2行用ヘッダ部出力
	 * @param tableHeaderInfo ヘッダ情報
	 * @param sheet Sheet
	 * @param searchConditionHeaderList 検索条件ヘッダリスト
	 * @param cellStyle セルスタイル
	 */
	private void write2LineHeader(TableHeaderInfo tableHeaderInfo, Sheet sheet, List<String> searchConditionHeaderList, CellStyle cellStyle) {
		// 見出し
		List<MidasiColInfo> midasiHeaderList = tableHeaderInfo.getMidasiHeaderList();
		// 累計
		ColInfo ruikeiHeader = tableHeaderInfo.getRuikeiHeader();
		// 日付
		List<ColInfo> dateHeaderList = tableHeaderInfo.getDateHeaderList();

		 // 1,2行目を作成
		Row rowHd1 = sheet.createRow(0);
		Row rowHd2 = sheet.createRow(1);

		List<String> header1List = new ArrayList<>();
		List<String> header2List = new ArrayList<>();

		// 見出し
		for (MidasiColInfo codeName : midasiHeaderList) {
			header1List.add(codeName.getCodeCol());
			header2List.add("");
			for (MidasiNameClass nm : codeName.getNameColList()) {
				header1List.add(nm.getNm());
				header2List.add("");
			}
		}

		// 累計
		if(tableHeaderInfo.isRuikeiHeader1Line()) {
			// 累計1行の場合
			header1List.add(ruikeiHeader.getRow1Col());
			header2List.add("");
		} else {
			// 累計2行の場合
			for (int i = 0; i < ruikeiHeader.getRow2ColList().size(); i++) {
				if(i==0) {
					header1List.add(ruikeiHeader.getRow1Col());
				} else {
					header1List.add("");
				}
				header2List.add(ruikeiHeader.getRow2ColList().get(i));
			}
		}

		// 日付
		for (ColInfo colInfo : dateHeaderList) {
			for (int i = 0; i < colInfo.getRow2ColList().size(); i++) {
				if(i==0) {
					header1List.add(colInfo.getRow1Col());
				} else {
					header1List.add("");
				}
				header2List.add(colInfo.getRow2ColList().get(i));
			}
		}

		// 検索条件
		for (String s : searchConditionHeaderList) {
			header1List.add(s);
			header2List.add("");
		}

		// 出力
		for (int i = 0; i < header1List.size(); i++) {
			writeCell(rowHd1, cellStyle, i, header1List.get(i));
	    }
		for (int i = 0; i < header2List.size(); i++) {
			writeCell(rowHd2, cellStyle, i, header2List.get(i));
		}


		// 結合

		// 列Index
		int colIdx = 0;

		// 見出し
		for (MidasiColInfo codeName : midasiHeaderList) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, colIdx, colIdx));
			colIdx++;
			for (int i = 0; i < codeName.getNameColList().size(); i++) {
				sheet.addMergedRegion(new CellRangeAddress(0, 1, colIdx, colIdx));
				colIdx++;
			}
		}

		// 累計
		if(!tableHeaderInfo.isRuikeiHeader1Line()) {
			// 累計2行の場合
			int colSpan = ruikeiHeader.getHeaderColspan();
			sheet.addMergedRegion(new CellRangeAddress(0, 0, colIdx, colIdx + colSpan -1));
			colIdx += colSpan;
		} else {
			// 累計1行の場合
			sheet.addMergedRegion(new CellRangeAddress(0, 1, colIdx, colIdx));
			colIdx++;
		}

		// 日付
		for (ColInfo colInfo : dateHeaderList) {
			int colSpan = colInfo.getHeaderColspan();
			sheet.addMergedRegion(new CellRangeAddress(0, 0, colIdx, colIdx + colSpan -1));
			colIdx += colSpan;
		}

		// 検索条件
		for (int i = 0; i < searchConditionHeaderList.size(); i++) {
			sheet.addMergedRegion(new CellRangeAddress(0, 1, colIdx, colIdx));
			colIdx++;
		}
	}

	/**
	 * データ部出力
	 * @param list 営業支援実績一覧のリスト
	 * @param book Book
	 * @param sheet Sheet
	 * @param searchConditionList 検索条件リスト
	 */
	private void writeData(JisekiESienListList list, Workbook book, Sheet sheet, List<String> searchConditionList) {
		// スタイル
		CellStyle cellStyle = getDataCellStyle(book);
		CellStyle numCellStyle = getDataNumCellStyle(book);

		// 1件目のレコードから出力列名を取得
		JisekiESienListRecord firstRec = (JisekiESienListRecord)list.get(0);
		// 見出し
		List<MidasiColValue> midasiColList = firstRec.getMidasiColValueList();
		// 累計
		List<ColValue> ruikeiColList = firstRec.getRuikeiColValueList();
		// 日付
		List<ColValue> dateColList = firstRec.getDateColValueList();

		// 行Index
		int rowIdx = list.getTableHeaderInfo().isHeader1Line() ? 1 : 2;

		// 各行出力
    	for (PbsRecord record : list) {
    		// 行作成
	    	Row row = sheet.createRow(rowIdx);
    		writeData(record, row, midasiColList, ruikeiColList, dateColList, searchConditionList, cellStyle, numCellStyle);
    		rowIdx++;
		}
	}

	/**
	 * データ部出力
	 * @param rec レコード
	 * @param row 行
	 * @param midasiColList 見出し列リスト
	 * @param ruikeiColList 累計列リスト
	 * @param dateColList 日付列リスト
	 * @param searchConditionList 検索条件リスト
	 * @param cellStyle セルスタイル
	 * @param numCellStyle 数値用セルスタイル
	 */
	private void writeData(PbsRecord rec, Row row, List<MidasiColValue> midasiColList, List<ColValue> ruikeiColList, List<ColValue> dateColList, List<String> searchConditionList, CellStyle cellStyle, CellStyle numCellStyle) {
		// 列Index
		int colIdx = 0;
		// 見出し
		for (MidasiColValue codeName : midasiColList) {
			writeCell(row, cellStyle, colIdx, rec.getString(codeName.getCd().getCol()));
			colIdx++;
			for (ColValue nm : codeName.getNmList()) {
				writeCell(row, cellStyle, colIdx, rec.getString(nm.getCol()));
				colIdx++;
			}
		}
		// 累計
		for (ColValue colValue : ruikeiColList) {
			writeCell(row, numCellStyle, colIdx, numFormat(rec.getString(colValue.getCol())));
			colIdx++;
		}
		// 日付
		for (ColValue colValue : dateColList) {
			writeCell(row, numCellStyle, colIdx, numFormat(rec.getString(colValue.getCol())));
			colIdx++;
		}
		// 検索条件
		for (String s : searchConditionList) {
			writeCell(row, cellStyle, colIdx, s);
			colIdx++;
		}
	}

	/**
	 * 数値フォーマット
	 * @param val 値
	 * @return フォーマット後の値
	 */
	private String numFormat(String val) {
		if(PbsUtil.isEmpty(val)) return null;

		String[] vals = val.split("\\.");
		// 整数部
		BigDecimal intDecimal;
		try {
			intDecimal = new BigDecimal(vals[0]);
		} catch (NumberFormatException e) {
			return val;
		}
		String intVal = df.format(intDecimal);
		if(vals.length == 1) {
			return intVal;
		} else {
			return intVal + "." + vals[1];
		}
	}

	/**
	 * セル出力
	 * @param row 行
	 * @param cellStyle セルスタイル
	 * @param colIdx 列インデックス
	 * @param val 値
	 */
	private void writeCell(Row row, CellStyle cellStyle, int colIdx, String val) {
		Cell cell = row.createCell(colIdx);
		if(!PbsUtil.isEmpty(val)) cell.setCellValue(val.trim());
		cell.setCellStyle(cellStyle);
	}

	/**
	 * セルスタイルの基本設定
	 * @param book Book
	 * @param style cellStyle
	 */
	private void setBaseCellStyle(Workbook book, CellStyle cellStyle) {
		cellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex()); // 背景色
		// 罫線の種類
		cellStyle.setBorderTop(CellStyle.BORDER_THIN); // 上
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN); // 下
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN); // 左
		cellStyle.setBorderRight(CellStyle.BORDER_THIN); // 右
		// 罫線の色
		cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex()); // 上
		cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex()); // 下
		cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex()); // 左
		cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex()); // 右
		// 書式
		DataFormat formatMidasiHd = book.createDataFormat();
		cellStyle.setDataFormat(formatMidasiHd.getFormat("text")); // 文字列
	};

	/**
	 * ヘッダ用セルスタイル取得
	 * @param book Book
	 * @return セルスタイル
	 */
	private CellStyle getHeaderCellStyle(Workbook book) {
		CellStyle cellStyle = book.createCellStyle();
		setBaseCellStyle(book, cellStyle);
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		return cellStyle;
	}

	/**
	 * データ用セルスタイル取得
	 * @param book Book
	 * @return セルスタイル
	 */
	private CellStyle getDataCellStyle(Workbook book) {
	    CellStyle cellStyle = book.createCellStyle();
	    setBaseCellStyle(book, cellStyle);
	    cellStyle.setFillPattern(CellStyle.NO_FILL);
	    cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
	    cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
	    return cellStyle;
	}

	/**
	 * データ(数値)用セルスタイル取得
	 * @param book Book
	 * @return セルスタイル
	 */
	private CellStyle getDataNumCellStyle(Workbook book) {
	    CellStyle cellStyle = book.createCellStyle();
	    setBaseCellStyle(book, cellStyle);
	    cellStyle.setFillPattern(CellStyle.NO_FILL);
	    cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
	    cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
	    return cellStyle;
	}
}
