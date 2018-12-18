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
 * ���샍�O�Ɖ���Service
 * @author NPC
 * @version 1.0
 */
public class K040Service extends CommonService {

	/** �e���v���[�g�� */
	private static final String SHEET_NAME_TEMP = "K040.xlsx";
	/** �V�[�g���i���N�f�f�j*/
	private static final String SHEET_NAME_KENSHIN = "���N�f�f";
	/** ���y�[�W�\���f�[�^���i���N�f�f�j*/
	private static final int PAGE_SIZE_KENSHIN = 6;
	/** ���y�[�W�\���s���i���N�f�f�j*/
	private static final int PAGE_ROWS_KENSHIN = 42;
	/** �o�͊J�n�s�i���N�f�f�j*/
	private static final int 	START_ROW_IDX_KENSHIN = 4;
	/** �o�͊J�n��i���N�f�f�j*/
	private static final int 	START_COL_IDX_KENSHIN = 11;
	/** �o�̓J�����̃Z�[�������i���N�f�f�j*/
	private static final int 	COL_CELL_LEN_KENSHIN = 8;
    /**
     * �����\��
     * @throws Exception
     *
     */
    public void init(K040Form k040Form) throws Exception {

        // S2Container������
        SingletonS2ContainerFactory.init();

        /*********************************
         * ��ʏ�����
        /*********************************
       	//�s�q�`�b�d�r���O�C���҂h�c�������\��
         */
		k040Form.inputUserId1 = k040Form.cmnLoginID;

		// TODO �f�[�^���͎҂����͂������ڂ����O�Ɏ����`�F�b�N
		// �o�͍��ڂ̏����l
		List<String> outputList = new ArrayList<String>();
		// ���N�f�f
		outputList.add("1");
		// ����
		outputList.add("2");
		// �����ޏ�
		outputList.add("3");
		// ������
		outputList.add("4");
		// �O������
		outputList.add("5");
		// ��������
		outputList.add("6");

		k040Form.outputItems = (String[]) outputList.toArray(new String[0]);
    }


    /**
     * Excel�o��
     * @param form
     * @throws Exception
     */
    public void outputExcel(K040Form form, String fileName) throws Exception {

        List<K040KenshinListItem>  k040KenshinList  = new ArrayList<K040KenshinListItem>();
        // �����������쐬
        Map<String, Object> param = new HashMap<String, Object>();

        // ���͓��iFROM�j
		param.put("dateFrom", LibStringUtil.getDatetoString(LibStringUtil.getDateWa(form.inputDateFrom), "yyyy/MM/dd"));
        // ���͓��i�s�n�j
        param.put("dateTo", LibStringUtil.getDatetoString(LibStringUtil.getDateWa(form.inputDateTo), "yyyy/MM/dd"));
        // TODO ���͎�ID
        param.put("userID", "03910000', '03910000");

    	try {

            LibEnv env = new LibEnv();
            // Excel�e���v���[�g�p�X�̎擾
            String strExcelDirPath = env.getExcelPath();
            // EXCEL�e���v���[�g�t�@�C���ǂݍ���
            LibXSSFWorkbook libWorkbook = new LibXSSFWorkbook();
            XSSFWorkbook workbook = libWorkbook.openXSSFWorkbook(strExcelDirPath + "/"+ SHEET_NAME_TEMP);

            // �o�̓��X�g
            List<String> outputlist = Arrays.asList(form.outputItems);

			// ���N�f�f���o�͂����ꍇ
			if (outputlist.contains("1")) {
				// ���N�f�f�̃f�[�^���X�g�擾
				k040KenshinList = jdbcManager.selectBySqlFile(K040KenshinListItem.class,
						"data/k040Kenshin.sql", param).getResultList();

				// ���N�f�f�V�[�g�̓��e���o��
				outputKenshin(k040KenshinList, workbook.getSheet(SHEET_NAME_KENSHIN));
			} else {
            	// �Y���V�[�g���폜���܂��B
            	workbook.removeName(SHEET_NAME_KENSHIN);
			}

	        // Excel�t�@�C�������X�|���X�ɏo��
	        libWorkbook.outputExcel(ResponseUtil.getResponse(), workbook, fileName);

		} catch (Exception e) {
			// TODO
			throw e;
		}

    }

    /**
     * ���N�f�f���o��
     * @param k040KenshinList
     * @param sheet
     * @throws Exception
     */
    private void outputKenshin(List<K040KenshinListItem> k040KenshinList, XSSFSheet sheet) throws Exception {

    	// �ϐ��̏�����
        int pageSum= 0;
        int pPositioin= 0;
        int nowPage = 0;
        int rowIdx = 0;
        int colIdx = 0;
        LibXSSFWorkbook libWorkbook = new LibXSSFWorkbook();

        // �f�[�^���s���݂����ꍇ
        if (k040KenshinList.size() == 0) {
        	return;
        }

        // ���y�[�W�����v�Z
		pageSum = (k040KenshinList.size() + PAGE_SIZE_KENSHIN - 1) / PAGE_SIZE_KENSHIN;

		// �y�[�W�����J��Ԃ�
		for (int i = 2; i <= pageSum; i++) {

			// ���y�[�W��̍s�ʒu���v�Z
			pPositioin = (i - 1) * PAGE_ROWS_KENSHIN;
			// ���ڃ^�C�g�����ƃR�s�[
			copyRows(0, PAGE_ROWS_KENSHIN - 1, pPositioin, sheet);
			// ���y�[�W�����ݒ�
		}

		// ���݂̃y�[�W
		nowPage = 1;
		// �o�͊J�n�s
		rowIdx = START_ROW_IDX_KENSHIN;
		// �o�͊J�n��
		colIdx = START_COL_IDX_KENSHIN;

		for (K040KenshinListItem k040Kenshin : k040KenshinList) {

			// ���y�[�W�̏ꍇ
			if (colIdx == START_COL_IDX_KENSHIN + PAGE_SIZE_KENSHIN * COL_CELL_LEN_KENSHIN) {
				// ���݂̃y�[�W��
				nowPage++;
				// �o�͊J�n�s���v�Z
				rowIdx = START_ROW_IDX_KENSHIN + PAGE_ROWS_KENSHIN * (nowPage - 1);
				// �o�͊J�n���������
				colIdx = START_COL_IDX_KENSHIN;
			}

			// �Ǘ��ԍ��i���o�ԍ��j
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.chutoNo);
			// ����
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.fullname);
			// ���f�敪
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.kenshinKbn);
			// ��f�敪
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.jukenKbn);
			// �d�����N�f�f�N����
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.kenshinDate);
			// ��������
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.hakekyuSu);
			// �����p��
			libWorkbook.setCellVal(sheet, rowIdx++, colIdx, k040Kenshin.rinpa);
			// �P��
			libWorkbook.setCellVal(sheet, rowIdx++, colIdx, k040Kenshin.tankyu);
			// �ٌ`�����p��
			libWorkbook.setCellVal(sheet, rowIdx++, colIdx, k040Kenshin.sonota);
			// �D��������j
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.kanjo);
			// �D�������t�j
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.bunyo);
			// �D�������v
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.kochukyukei);
			// �D�_��
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.kosan);
			// �D���
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.koenki);
			// �Ԍ�����
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.sekekyu);
			// ���F�f��
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.shikiso);
			// �w�}�g�N���b�g
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.hemato);
			// ���̑�
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.ketsuta);
			// �����̂̍���
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.coMe);
			// ����
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.coHaseki);
			// �������͏c����
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.coKanso);
			// ���
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.coKaiyo);
			// �܂ُ̈�
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.coTsume);
			// ���̑��̌���
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.coSonota);
			// �S�g�I����
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.coZenshin);
			// ���o�I�i��
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.coJikaku);
			// �Q�l����
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.coSanko);
			// ��t�̐f�f
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.coShindan);
			// �a�@�E�����@�֖�
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.kensaKikanMei);
			// �f�f���s������t��
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.shindanIshi);
			// ��t�̈ӌ�
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.coIken);
			// �a�@�E�����@�֖�
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.ikenKensaKikanMei);
			// �ӌ����q�ׂ���t��
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.ikenIshi);
			// ����
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.hanteiKbn);
			// �J��񍐁i�T�C�g�j
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.rokiSiteNo);
			// �J��񍐁i�����j
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.shozokuCd);
			// ���́i�X�V�j����
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.kosinDate);
			// ���͎�
			libWorkbook.setCellValue(sheet, rowIdx++, colIdx, k040Kenshin.kosinName);

			// ���̏o�͊J�n�s���v�Z
			rowIdx = START_ROW_IDX_KENSHIN + PAGE_ROWS_KENSHIN * (nowPage - 1);

			// ���̏o�͊J�n����v�Z
			colIdx = colIdx + COL_CELL_LEN_KENSHIN;
		}
    }

    /**
     * �����s���R�s�[
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
	 * �Z�[�����R�s�|
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
