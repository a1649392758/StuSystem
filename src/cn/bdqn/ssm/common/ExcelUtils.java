package cn.bdqn.ssm.common;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

/**
 * @description excel工具类
 * @author 李洋
 * @address 北大青鸟沈阳三好中心
 * @created 2017年7月12日 下午1:32:32
 * @version 1.0.0
 */
public class ExcelUtils {
	
	public static void createExcelTitle(HSSFSheet sheet,HSSFCellStyle styleTitle,String[][] titleContent) {
		HSSFRow row = sheet.createRow((short) 0);// 建立新行
		for(int i=0;i<titleContent.length;i++){
			String[] title = titleContent[i];
			createCell(row, i, styleTitle, HSSFCell.CELL_TYPE_STRING, title[0]);
			sheet.setColumnWidth(i, Integer.parseInt(title[1]));
		}
	}
	
	// 创建Excel单元
	public static void createCell(HSSFRow row, int column, HSSFCellStyle style, int cellType, Object value) {
		HSSFCell cell = row.createCell(column);
		if (style != null) {
			cell.setCellStyle(style);
		}
		switch (cellType) {
		case HSSFCell.CELL_TYPE_BLANK: {
			cell.setCellValue("");
		}
			break;
		case HSSFCell.CELL_TYPE_STRING: {
			cell.setCellValue(value.toString());
		}
			break;
		case HSSFCell.CELL_TYPE_NUMERIC: {
			cell.setCellValue(Double.parseDouble(value.toString()));
		}
			break;
		default:
			break;
		}
	}
}
