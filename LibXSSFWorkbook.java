package jp.co.toshiba.traces.libs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
//import java.io.OutputStreamWriter;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import javax.servlet.http.HttpServletResponse;

/**
 * ＊＊＊＊＊＊＊ 原子力共通ファイル(EXCEL用)アクセスクラス(xlsx用)　＊＊＊＊＊＊＊
 *
 * @author  NPC
 * @version 2018-12
 *
 */
public class LibXSSFWorkbook {

	/**
	 * book取得
	 * @param strTmpPath	テンプレートファイルPath
	 * @return		XSSFWorkbook
	 */
	public XSSFWorkbook openXSSFWorkbook(String strTmpPath)
	throws Exception
	{
		XSSFWorkbook	book	=	null;

		FileInputStream		xls_is	=	null;

		try{
			xls_is = new FileInputStream(new File(strTmpPath));

			book = new XSSFWorkbook(xls_is);

//		}catch(Exception e){
//			libException le = e instanceof libException ? (libException)e : new libException(e);
//			throw le;
	    } catch (Throwable e) {
	    	e.printStackTrace();

		}finally{
			try{xls_is.close();}catch(Exception ee){}
		}

		return book;
	};



	/**
	 * row取得
	 * @param XSSFSheet	XSSFSheet
	 * @param idx		行番号
	 * @return	XSSFRow
	 */
	public XSSFRow getXSSFRow(XSSFSheet Sheet, int idx)
	throws Exception
	{
		XSSFRow		row		=	null;

		try{
			row = Sheet.getRow(idx);
			if(row == null){
				row = Sheet.createRow(idx);
			}
		}catch(Exception e){
			throw e;
		}finally{
		}

		return row;
	}

	/**
	 * セル取得
	 * @param row	XSSFRow
	 * @param idx	セル番号
	 * @return	XSSFCell
	 */
	public XSSFCell getCell(XSSFRow row, int idx)
	throws Exception
	{
		XSSFCell	cell	=	null;

		try{
			cell = row.getCell(idx);
			if(cell == null){
				cell = row.createCell(idx);
			}
		}catch(Exception e){
			throw e;
		}finally{
		}

		return cell;
	}

	/**
	 * 値取得
	 * @param XSSFSheet	XSSFSheet
	 * @param y_idx	行番号
	 * @param x_idx	セル番号
	 * @return	Object
	 */
	public Object getCellValue(XSSFSheet XSSFSheet, int y_idx, int x_idx)
	throws Exception
	{
		Object		objRtn	=	null;

		XSSFRow		row		=	null;

		XSSFCell	cell	=	null;

		try{
			row = getXSSFRow(XSSFSheet, y_idx);

			cell = getCell(row, x_idx);

			objRtn = getCellValue(cell);

		}catch(Exception e){
			throw e;
		}finally{
		}

		return objRtn;
	}

	/**
	 * 値取得
	 * @param row		XSSFRow
	 * @param x_idx	セル番号
	 * @return	Object
	 */
	public Object getCellValue(XSSFRow row, int x_idx)
	throws Exception
	{
		Object		objRtn	=	null;

		XSSFCell	cell	=	null;

		try{
			cell = getCell(row, x_idx);

			objRtn = getCellValue(cell);

		}catch(Exception e){
			throw e;
		}finally{
		}

		return objRtn;
	}

	/**
	 * 値取得
	 * @param cell		XSSFCell
	 * @return	Object
	 */
	public Object getCellValue(XSSFCell cell)
	throws Exception
	{
		Object	cellData	=	null;

		try{
			// セルにあるデータの種類を取得
			int cellType = cell.getCellType();

			// セル内データ取得
			// データ種類毎に取得方法が異なるので、switchで分岐させる。
			switch( cellType ){

				// セル値が真偽値の場合
				case XSSFCell.CELL_TYPE_BOOLEAN:
					boolean bdate = cell.getBooleanCellValue();
					cellData = String.valueOf( bdate );
					break;

				// セル値が数値の場合
				case XSSFCell.CELL_TYPE_NUMERIC:
					// 数値か日付
					short format = cell.getCellStyle().getDataFormat();
					if( DateUtil.isInternalDateFormat( format ) ){
						// 日付
						Date d = cell.getDateCellValue();
						if( format == 22 || format == 176 ){
							// 日付時刻(java.sql.Timestampでオブジェクト作成)
							//cellData = String.valueOf( DateFormat.getDateTimeInstance().format(d) );
							cellData = new Timestamp( d.getTime() );
						} else {
							// 日付(YYYY/MM/DD形式でデータを取得)
							cellData = String.valueOf( DateFormat.getDateInstance().format(d) );
						}
					} else {
						// 数値の場合
						double ddata = cell.getNumericCellValue();
						long ldata = (long)ddata;

						if( ddata - (double)ldata == 0 ){
							cellData = String.valueOf( ldata );
						} else {
							cellData = String.valueOf( ddata );
						}
					}
					break;

				// セル値が文字列の場合
				case XSSFCell.CELL_TYPE_STRING:
					// セル値の後ろに空白スペースが強制的に入ってしまう可能性があるため、
					// 後ろ空白スペースを取り除く
					//cellData = libStringUtil.Rtrim( cell.getStringCellValue() );
					cellData = cell.getRichStringCellValue().getString();
					break;

				// セル値が計算式の場合
				case XSSFCell.CELL_TYPE_FORMULA:
					double ddata = cell.getNumericCellValue();
					if( !Double.isNaN( ddata ) ){
						// 数値か日付
						short format2 = cell.getCellStyle().getDataFormat();
						if( DateUtil.isInternalDateFormat( format2 ) ){
							// 日付
							Date d = cell.getDateCellValue();
							if( format2 == 22 || format2 == 176 ){
								// 日付時刻(java.sql.Timestampでオブジェクト作成)
								//cellData = String.valueOf( DateFormat.getDateTimeInstance().format(d) );
								cellData = new Timestamp( d.getTime() );
							} else {
								// 日付(YYYY/MM/DD形式でデータを取得)
								cellData = String.valueOf( DateFormat.getDateInstance().format(d) );
							}
						} else {
							// 数値の場合
							long ldata = (long)ddata;

							if( ddata - (double)ldata == 0 ){
								cellData = String.valueOf( ldata );
							} else {
								cellData = String.valueOf( ddata );
							}
						}
					} else {
						// 数値ではないとき
						// セル値の後ろに空白スペースが強制的に入ってしまう可能性があるため、
						// 後ろ空白スペースを取り除く
						//cellData = libUtil.Rtrim( cell.getStringCellValue() );
						cellData = cell.getStringCellValue();
					}
					break;

				// セル値が空白・エラーの場合は空文字列をセットする。
				case XSSFCell.CELL_TYPE_BLANK:
				case XSSFCell.CELL_TYPE_ERROR:
				default:
			}
		}catch(Exception e){
			throw e;
		}finally{
		}

		return cellData;
	}

	/**
	 * セルに値をセット
	 * @param XSSFSheet	XSSFSheet
	 * @param y_idx	行番号
	 * @param x_idx	セル番号
	 * @param value	値
	 */
	public void setCellValue(XSSFSheet XSSFSheet, int y_idx, int x_idx, Object value)
	throws Exception
	{
		setCellValue(XSSFSheet, y_idx, x_idx, value, null);

		return;
	}

	/**
	 * セルに値をセット
	 * @param XSSFSheet	XSSFSheet
	 * @param y_idx	行番号
	 * @param x_idx	セル番号
	 * @param value	値
	 * @param style	XSSFCellStyle
	 */
	public void setCellValue(XSSFSheet XSSFSheet, int y_idx, int x_idx, Object value, XSSFCellStyle style)
	throws Exception
	{
		XSSFRow		row		=	null;
		XSSFCell	cell	=	null;

		try{
			row = getXSSFRow(XSSFSheet, y_idx);

			if(value == null || "".equals(value)){
				row.removeCell(getCell(row, x_idx));
			}

			cell = getCell(row, x_idx);

			setCellValue(cell, value, style);
		}catch(Exception e){
			throw e;
		}finally{
		}

		return;
	}

	/**
	 * セルに値をセット
	 * @param row		XSSFRow
	 * @param x_idx	セル番号
	 * @param value	値
	 * @param style	XSSFCellStyle
	 */
	public void setCellValue(XSSFRow row, int x_idx, Object value, XSSFCellStyle style)
	throws Exception
	{
		XSSFCell	cell	=	null;

		try{
			if(value == null || "".equals(value)){
				row.removeCell(getCell(row, x_idx));
			}

			cell = getCell(row, x_idx);

			setCellValue(cell, value, style);
		}catch(Exception e){
			throw e;
		}finally{
		}

		return;
	}

	/**
	 * セルに値をセット
	 * @param cell		XSSFCell
	 * @param objVal	Object	値
	 * @param style	XSSFCellStyle
	 */
	public void setCellValue(XSSFCell cell, Object objVal, XSSFCellStyle style)
	throws Exception
	{
		String	className	=	"";

		double	dobleValue	=	0;

		Date	dateValue	=	null;

		try{
			//スタイルの指定
			if(style != null){
				cell.setCellStyle(style);
			}

			if(objVal != null){
				className = objVal.getClass().getName();
				if( className.equals("java.lang.String") ){
					cell.setCellValue( (String)objVal );
					//RichTextString rts = new RichTextString( (String)objVal );
					//cell.setCellValue( rts );
				} else if( className.equals("java.lang.Double") ){
					dobleValue = ( (Double)objVal ).doubleValue();
					cell.setCellValue( dobleValue );
				} else if( className.equals("java.util.Date") ){
					dateValue = (Date)objVal;
					cell.setCellValue( dateValue );
				}
			}
		}catch(Exception e){
			throw e;
		}finally{
		}

		return;
	}

	/**
	 * セルに計算式をセット
	 * @param XSSFSheet		XSSFSheet
	 * @param y_idx		行番号
	 * @param x_idx		セル番号
	 * @param strFormula	計算式
	 * @param style		XSSFCellStyle
	 * @param formulaCell	すでに計算式がセットされているセル(何でもOK)
	 */
	public void setCellFormulaString(XSSFSheet XSSFSheet, int y_idx, int x_idx, String strFormula, XSSFCellStyle style, XSSFCell formulaCell)
	throws Exception
	{
		setCellFormulaString(XSSFSheet, y_idx, x_idx, strFormula, y_idx +1, style, formulaCell);

		return;
	}

	/**
	 * セルに計算式をセット
	 * (@をrep_idxで置換)
	 * @param XSSFSheet		XSSFSheet
	 * @param y_idx		行番号
	 * @param x_idx		セル番号
	 * @param strFormula	計算式
	 * @param rep_idx		置換行番号
	 * @param style		XSSFCellStyle
	 * @param formulaCell	すでに計算式がセットされているセル(何でもOK)
	 */
	public void setCellFormulaString(XSSFSheet XSSFSheet, int y_idx, int x_idx, String strFormula, int rep_idx, XSSFCellStyle style, XSSFCell formulaCell)
	throws Exception
	{
		XSSFRow		row		=	null;
		XSSFCell	cell	=	null;

		String		strVal	=	"";

		try{
			row = getXSSFRow(XSSFSheet, y_idx);
			cell = getCell(row, x_idx);

			strVal = strFormula.replace("@", String.valueOf(rep_idx));

			setCellFormulaString(cell, strVal, style, formulaCell);

		}catch(Exception e){
			throw e;
		}finally{
		}

		return;
	}

	/**
	 * セルに計算式をセット
	 * @param row			XSSFRow
	 * @param x_idx		セル番号
	 * @param strFormula	計算式
	 * @param rep_idx		置換行番号
	 * @param style		XSSFCellStyle
	 * @param formulaCell	すでに計算式がセットされているセル(何でもOK)
	 */
	public void setCellFormulaString(XSSFRow row, int x_idx, String strFormula, int rep_idx, XSSFCellStyle style, XSSFCell formulaCell)
	throws Exception
	{
		XSSFCell	cell	=	null;

		String		strVal	=	"";

		try{
			cell = getCell(row, x_idx);

			strVal = strFormula.replace("@", String.valueOf(rep_idx));

			setCellFormulaString(cell, strVal, style, formulaCell);

		}catch(Exception e){
			throw e;
		}finally{
		}

		return;
	}

	/**
	 * セルに計算式をセット
	 * @param cell			Cell
	 * @param strFormula	計算式
	 * @param style		XSSFCellStyle
	 * @param formulaCell	すでに計算式がセットされているセル(何でもOK)
	 */
	public void setCellFormulaString(XSSFCell cell, String strFormula, XSSFCellStyle style, XSSFCell formulaCell)
	throws Exception
	{
		try{
			cell.setCellFormula(formulaCell.getCellFormula());
			cell.setCellFormula(strFormula);

			if(style != null){
				cell.setCellStyle(style);
			}

		}catch(Exception e){
			throw e;
		}finally{
		}

		return;
	}

	/**
	 * セルにスタイルをセット
	 * @param XSSFSheet	XSSFSheet
	 * @param y_idx	行番号
	 * @param x_idx	セル番号
	 * @param style	XSSFCellStyle
	 */
	public void setXSSFCellStyle(XSSFSheet XSSFSheet, int y_idx, int x_idx, XSSFCellStyle style)
	throws Exception
	{
		XSSFRow		row		=	null;
		XSSFCell	cell	=	null;

		try{
			row = getXSSFRow(XSSFSheet, y_idx);
			cell = getCell(row, x_idx);

			setXSSFCellStyle(cell, style);

		}catch(Exception e){
			throw e;
		}finally{
		}

		return;
	}

	/**
	 * セルにスタイルをセット
	 * @param row		XSSFRow
	 * @param x_idx	セル番号
	 * @param style	XSSFCellStyle
	 */
	public void setXSSFCellStyle(XSSFRow row, int x_idx, XSSFCellStyle style)
	throws Exception
	{
		XSSFCell	cell	=	null;

		try{
			cell = getCell(row, x_idx);

			setXSSFCellStyle(cell, style);

		}catch(Exception e){
			throw e;
		}finally{
		}

		return;
	}

	/**
	 * セルにスタイルをセット
	 * @param cell		XSSFCell
	 * @param style	XSSFCellStyle
	 */
	public void setXSSFCellStyle(XSSFCell cell, XSSFCellStyle style)
	throws Exception
	{
		try{
			if(style != null){
				cell.setCellStyle(style);
			}

		}catch(Exception e){
			throw e;
		}finally{
		}

		return;
	}

	/**
	 * bookをストリームに出力
	 * @param res			HttpServletResponse
	 * @param book			XSSFWorkbook
	 * @param strFileName	ファイル名
	 */
	public void outputExcel(HttpServletResponse res, XSSFWorkbook book, String strFileName)
	throws Exception
	{
		OutputStreamWriter os = null;

		try{
			res.reset();
			res.setContentType("application/vnd.ms-excel");
			res.setHeader("Content-Disposition", "attachment;filename=" + new String( strFileName.getBytes("Shift_JIS"), "ISO8859_1" ) );
			book.write(res.getOutputStream());

		}catch(Exception e){
			throw e;
		}finally{
			try{os.close();}catch(Exception ee){}
		}

		return;
	}

	/**
	 * bookをファイルに出力
	 * @param filePath 出力先ファイルパス
	 */
	public void outputExcel( XSSFWorkbook book, String filePath )
	throws Exception{
		FileOutputStream fos = null;
		try{
			fos = new FileOutputStream( filePath );
			book.write( fos );
		} catch( Exception e ){
			throw new Exception(e);
		} finally{
			try{
				if( fos != null ){
					fos.close();
				}
			} catch( IOException ioe ){
				throw new Exception(ioe);
			}
		}
	}


	/**
	 * StringをObject(double)に変換
	 * @param strVal	値
	 * @return	Object
	 */
	public Object parseNumber(String strVal)
	throws Exception
	{
		Object	objRtn	=	null;

		try{
			if(strVal != null && !"".equals(strVal)){

				objRtn = new Double(strVal);
			}
		}catch(Exception e){
			throw e;
		}finally{
		}

		return objRtn;
	}

	/**
	 * StringをObject(Date)に変換
	 * @param strVal	値
	 * @return	Object
	 */
	public Object parseDate(String strVal)
	throws Exception
	{
		Object	objRtn	=	null;

		try{
			if(strVal != null && !"".equals(strVal)){

				objRtn = DateFormat.getDateInstance().parse(strVal);
			}
		}catch(Exception e){
			throw e;
		}finally{
		}

		return objRtn;
	}

}

