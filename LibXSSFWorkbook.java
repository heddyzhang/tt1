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
 * �������������� ���q�͋��ʃt�@�C��(EXCEL�p)�A�N�Z�X�N���X(xlsx�p)�@��������������
 *
 * @author  NPC
 * @version 2018-12
 *
 */
public class LibXSSFWorkbook {

	/**
	 * book�擾
	 * @param strTmpPath	�e���v���[�g�t�@�C��Path
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
	 * row�擾
	 * @param XSSFSheet	XSSFSheet
	 * @param idx		�s�ԍ�
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
	 * �Z���擾
	 * @param row	XSSFRow
	 * @param idx	�Z���ԍ�
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
	 * �l�擾
	 * @param XSSFSheet	XSSFSheet
	 * @param y_idx	�s�ԍ�
	 * @param x_idx	�Z���ԍ�
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
	 * �l�擾
	 * @param row		XSSFRow
	 * @param x_idx	�Z���ԍ�
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
	 * �l�擾
	 * @param cell		XSSFCell
	 * @return	Object
	 */
	public Object getCellValue(XSSFCell cell)
	throws Exception
	{
		Object	cellData	=	null;

		try{
			// �Z���ɂ���f�[�^�̎�ނ��擾
			int cellType = cell.getCellType();

			// �Z�����f�[�^�擾
			// �f�[�^��ޖ��Ɏ擾���@���قȂ�̂ŁAswitch�ŕ��򂳂���B
			switch( cellType ){

				// �Z���l���^�U�l�̏ꍇ
				case XSSFCell.CELL_TYPE_BOOLEAN:
					boolean bdate = cell.getBooleanCellValue();
					cellData = String.valueOf( bdate );
					break;

				// �Z���l�����l�̏ꍇ
				case XSSFCell.CELL_TYPE_NUMERIC:
					// ���l�����t
					short format = cell.getCellStyle().getDataFormat();
					if( DateUtil.isInternalDateFormat( format ) ){
						// ���t
						Date d = cell.getDateCellValue();
						if( format == 22 || format == 176 ){
							// ���t����(java.sql.Timestamp�ŃI�u�W�F�N�g�쐬)
							//cellData = String.valueOf( DateFormat.getDateTimeInstance().format(d) );
							cellData = new Timestamp( d.getTime() );
						} else {
							// ���t(YYYY/MM/DD�`���Ńf�[�^���擾)
							cellData = String.valueOf( DateFormat.getDateInstance().format(d) );
						}
					} else {
						// ���l�̏ꍇ
						double ddata = cell.getNumericCellValue();
						long ldata = (long)ddata;

						if( ddata - (double)ldata == 0 ){
							cellData = String.valueOf( ldata );
						} else {
							cellData = String.valueOf( ddata );
						}
					}
					break;

				// �Z���l��������̏ꍇ
				case XSSFCell.CELL_TYPE_STRING:
					// �Z���l�̌��ɋ󔒃X�y�[�X�������I�ɓ����Ă��܂��\�������邽�߁A
					// ���󔒃X�y�[�X����菜��
					//cellData = libStringUtil.Rtrim( cell.getStringCellValue() );
					cellData = cell.getRichStringCellValue().getString();
					break;

				// �Z���l���v�Z���̏ꍇ
				case XSSFCell.CELL_TYPE_FORMULA:
					double ddata = cell.getNumericCellValue();
					if( !Double.isNaN( ddata ) ){
						// ���l�����t
						short format2 = cell.getCellStyle().getDataFormat();
						if( DateUtil.isInternalDateFormat( format2 ) ){
							// ���t
							Date d = cell.getDateCellValue();
							if( format2 == 22 || format2 == 176 ){
								// ���t����(java.sql.Timestamp�ŃI�u�W�F�N�g�쐬)
								//cellData = String.valueOf( DateFormat.getDateTimeInstance().format(d) );
								cellData = new Timestamp( d.getTime() );
							} else {
								// ���t(YYYY/MM/DD�`���Ńf�[�^���擾)
								cellData = String.valueOf( DateFormat.getDateInstance().format(d) );
							}
						} else {
							// ���l�̏ꍇ
							long ldata = (long)ddata;

							if( ddata - (double)ldata == 0 ){
								cellData = String.valueOf( ldata );
							} else {
								cellData = String.valueOf( ddata );
							}
						}
					} else {
						// ���l�ł͂Ȃ��Ƃ�
						// �Z���l�̌��ɋ󔒃X�y�[�X�������I�ɓ����Ă��܂��\�������邽�߁A
						// ���󔒃X�y�[�X����菜��
						//cellData = libUtil.Rtrim( cell.getStringCellValue() );
						cellData = cell.getStringCellValue();
					}
					break;

				// �Z���l���󔒁E�G���[�̏ꍇ�͋󕶎�����Z�b�g����B
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
	 * �Z���ɒl���Z�b�g
	 * @param XSSFSheet	XSSFSheet
	 * @param y_idx	�s�ԍ�
	 * @param x_idx	�Z���ԍ�
	 * @param value	�l
	 */
	public void setCellValue(XSSFSheet XSSFSheet, int y_idx, int x_idx, Object value)
	throws Exception
	{
		setCellValue(XSSFSheet, y_idx, x_idx, value, null);

		return;
	}

	/**
	 * �Z���ɒl���Z�b�g
	 * @param XSSFSheet	XSSFSheet
	 * @param y_idx	�s�ԍ�
	 * @param x_idx	�Z���ԍ�
	 * @param value	�l
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
	 * �Z���ɒl���Z�b�g
	 * @param row		XSSFRow
	 * @param x_idx	�Z���ԍ�
	 * @param value	�l
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
	 * �Z���ɒl���Z�b�g
	 * @param cell		XSSFCell
	 * @param objVal	Object	�l
	 * @param style	XSSFCellStyle
	 */
	public void setCellValue(XSSFCell cell, Object objVal, XSSFCellStyle style)
	throws Exception
	{
		String	className	=	"";

		double	dobleValue	=	0;

		Date	dateValue	=	null;

		try{
			//�X�^�C���̎w��
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
	 * �Z���Ɍv�Z�����Z�b�g
	 * @param XSSFSheet		XSSFSheet
	 * @param y_idx		�s�ԍ�
	 * @param x_idx		�Z���ԍ�
	 * @param strFormula	�v�Z��
	 * @param style		XSSFCellStyle
	 * @param formulaCell	���łɌv�Z�����Z�b�g����Ă���Z��(���ł�OK)
	 */
	public void setCellFormulaString(XSSFSheet XSSFSheet, int y_idx, int x_idx, String strFormula, XSSFCellStyle style, XSSFCell formulaCell)
	throws Exception
	{
		setCellFormulaString(XSSFSheet, y_idx, x_idx, strFormula, y_idx +1, style, formulaCell);

		return;
	}

	/**
	 * �Z���Ɍv�Z�����Z�b�g
	 * (@��rep_idx�Œu��)
	 * @param XSSFSheet		XSSFSheet
	 * @param y_idx		�s�ԍ�
	 * @param x_idx		�Z���ԍ�
	 * @param strFormula	�v�Z��
	 * @param rep_idx		�u���s�ԍ�
	 * @param style		XSSFCellStyle
	 * @param formulaCell	���łɌv�Z�����Z�b�g����Ă���Z��(���ł�OK)
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
	 * �Z���Ɍv�Z�����Z�b�g
	 * @param row			XSSFRow
	 * @param x_idx		�Z���ԍ�
	 * @param strFormula	�v�Z��
	 * @param rep_idx		�u���s�ԍ�
	 * @param style		XSSFCellStyle
	 * @param formulaCell	���łɌv�Z�����Z�b�g����Ă���Z��(���ł�OK)
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
	 * �Z���Ɍv�Z�����Z�b�g
	 * @param cell			Cell
	 * @param strFormula	�v�Z��
	 * @param style		XSSFCellStyle
	 * @param formulaCell	���łɌv�Z�����Z�b�g����Ă���Z��(���ł�OK)
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
	 * �Z���ɃX�^�C�����Z�b�g
	 * @param XSSFSheet	XSSFSheet
	 * @param y_idx	�s�ԍ�
	 * @param x_idx	�Z���ԍ�
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
	 * �Z���ɃX�^�C�����Z�b�g
	 * @param row		XSSFRow
	 * @param x_idx	�Z���ԍ�
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
	 * �Z���ɃX�^�C�����Z�b�g
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
	 * book���X�g���[���ɏo��
	 * @param res			HttpServletResponse
	 * @param book			XSSFWorkbook
	 * @param strFileName	�t�@�C����
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
	 * book���t�@�C���ɏo��
	 * @param filePath �o�͐�t�@�C���p�X
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
	 * String��Object(double)�ɕϊ�
	 * @param strVal	�l
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
	 * String��Object(Date)�ɕϊ�
	 * @param strVal	�l
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

