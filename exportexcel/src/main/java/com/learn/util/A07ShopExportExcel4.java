package com.learn.util;

import com.alibaba.fastjson.JSON;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class A07ShopExportExcel4 {
    private static final Logger LOGGER = LoggerFactory.getLogger(A07ShopExportExcel4.class);
    
	String sheetTitle;
	List<Map<String,String>> list;
	Workbook workbook = null;
	Sheet sheet;
	Map<String, CellStyle> styles;
 
	public A07ShopExportExcel4(String sheetTitle, List<Map<String,String>> list) {
		LOGGER.info("===>标题="+sheetTitle +"---数据=="+ JSON.toJSONString(list));
		this.sheetTitle = sheetTitle;
		this.list = list;
		// 初始化表
		initSheet();
		// 初始化表头
		creatSheetHead();
		// 填充数据
		fillSheetData();
	}
 
	/**
	 * 初始化表
	 */
	private void initSheet() {
		try {
			workbook = new XSSFWorkbook();
		} catch (Exception e) {
			LOGGER.info("======>>创建workbook异常e=",e);
			e.printStackTrace();
		}
		styles = createStyles(workbook);
 
		sheet = workbook.createSheet(sheetTitle);
 
		sheet.setDisplayGridlines(false);
		sheet.setPrintGridlines(false);
		sheet.setFitToPage(true);
		sheet.setHorizontallyCenter(true);
		sheet.createFreezePane(0, 2, 0, 2);
		PrintSetup printSetup = sheet.getPrintSetup();
		printSetup.setLandscape(true);
		// 初始化列宽
		sheet.setColumnWidth(0, 24 * 256);
		sheet.setColumnWidth(1, 12 * 256);
		sheet.setColumnWidth(2, 12 * 256);
		sheet.setColumnWidth(3, 12 * 256);
		sheet.setColumnWidth(4, 36 * 256);
		sheet.setColumnWidth(5, 12 * 256);
		
	}
 
	/**
	 * 初始表头
	 */
	private void creatSheetHead() {
		Row headRow_0 = sheet.createRow(0);
		headRow_0.setHeightInPoints(20);
		CellStyle headCellStyle = styles.get("head");
		Cell titleCell = headRow_0.createCell(0);
		titleCell.setCellValue(sheetTitle);
		CellStyle headTitleStyle = styles.get("headTitle");
		titleCell.setCellStyle(headTitleStyle);
		//sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$AO$1"));
		// 表头一
		Row headRow_1 = sheet.createRow(1);
		int headCellIndex = 0;
		Cell headCell = headRow_1.createCell(headCellIndex++);
		headCell.setCellValue("序号");
		headCell.setCellStyle(headCellStyle);
		
		headCell = headRow_1.createCell(headCellIndex++);
		headCell.setCellValue("收款人名称");
		headCell.setCellStyle(headCellStyle);
		headCell = headRow_1.createCell(headCellIndex++);
		headCell.setCellValue("开户银行");
		headCell.setCellStyle(headCellStyle);
		headCell = headRow_1.createCell(headCellIndex++);
		headCell.setCellValue("银行账号");
		headCell.setCellStyle(headCellStyle);
		headCell = headRow_1.createCell(headCellIndex++);
		headCell.setCellValue("金额");
		headCell.setCellStyle(headCellStyle);
		headCell = headRow_1.createCell(headCellIndex++);
		headCell.setCellValue("店铺名称");
		headCell.setCellStyle(headCellStyle);
		headCell = headRow_1.createCell(headCellIndex++);
		
	}
 
	/**
	 * 填充数据
	 */
	private void fillSheetData() {
		double sum = 0.0;
		int rowNum = 2;
		CellStyle cellStyle = styles.get("cell");
		CellStyle urlStyle = styles.get("urlStyle");
		for (int i = 0, length = list.size(); i < length; i++) {
			Map<String,String> map = list.get(i);
		    
			Row row = sheet.createRow(rowNum++);
			row.setHeightInPoints(2 * sheet.getDefaultRowHeightInPoints());
			
			int cellIndex = 0;
			Cell itemCell = row.createCell(cellIndex++);
			itemCell.setCellValue(map.get("xh"));
			itemCell.setCellStyle(cellStyle);
			
			itemCell = row.createCell(cellIndex++);
			itemCell.setCellValue(map.get("skrmc") );
			itemCell.setCellStyle(cellStyle);
			
			itemCell = row.createCell(cellIndex++);
			itemCell.setCellValue(map.get("khyh") );
			itemCell.setCellStyle(cellStyle);
			
			itemCell = row.createCell(cellIndex++);
			itemCell.setCellValue(map.get("yhzh"));
			itemCell.setCellStyle(cellStyle);
			
			itemCell = row.createCell(cellIndex++);
			itemCell.setCellValue(map.get("bxje"));
			itemCell.setCellStyle(cellStyle);
			
			itemCell = row.createCell(cellIndex++);
			itemCell.setCellValue(map.get("sqdpName"));
			itemCell.setCellStyle(cellStyle);
			if(!"".equals(map.get("bxje")) && !"null".equals(map.get("bxje"))) {
				sum += Double.parseDouble(map.get("bxje"));
			}
		}
		LOGGER.info("总计=====>>>>" +sum);
		/*Row row = sheet.createRow(list.size()+1);
		row.setHeightInPoints(2 * sheet.getDefaultRowHeightInPoints());
		Cell itemCell = row.createCell(0);
		itemCell.setCellValue("合计");
		itemCell.setCellStyle(cellStyle);
		
		Cell itemCell1 = row.createCell(5);
		itemCell1.setCellValue(sum);
		itemCell1.setCellStyle(cellStyle);*/
	}
	
	private String fmtString(String str) {
	    if (StringUtils.isEmpty(str)) {
	        return "";
	    }
	    return str;
	}
 
	/**
	 * 单元格样式
	 * @param wb
	 * @return
	 */
	private Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
 
		Font headFont = wb.createFont();
		headFont.setFontHeightInPoints((short) 12);
		headFont.setColor(IndexedColors.BLACK.getIndex());
 
		Font cellFont = wb.createFont();
		cellFont.setFontHeightInPoints((short) 10);
		cellFont.setColor(IndexedColors.BLACK.getIndex());
 
		CellStyle headCellStyle = createBorderedStyle(wb);
		headCellStyle.setWrapText(true);
		headCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT
				.getIndex());
		headCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headCellStyle.setFont(cellFont);
		styles.put("head", headCellStyle);
		
		CellStyle headTitleStyle = createBorderedStyle(wb);
		headTitleStyle.setWrapText(true);
		headTitleStyle.setAlignment(CellStyle.ALIGN_LEFT);
		headTitleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		headTitleStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT
				.getIndex());
		headTitleStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headTitleStyle.setFont(headFont);
		styles.put("headTitle", headTitleStyle);
 
		CellStyle cellStyle = createBorderedStyle(wb);
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setFont(cellFont);
		styles.put("cell", cellStyle);
		
		CellStyle urlStyle = createBorderedStyle(wb);
		Font urlFont = wb.createFont();
		urlFont.setColor(IndexedColors.BLUE.getIndex());
		urlFont.setUnderline(HSSFFont.U_SINGLE);
		urlStyle.setFont(urlFont);
        //cellStyle.setWrapText(true);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        styles.put("urlStyle", urlStyle);
 
		return styles;
	}
 
	private CellStyle createBorderedStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		return style;
	}
 
	public void export(HttpServletResponse response) throws IOException {
		//response.setContentType("application/x-download");//下面三行是关键代码，处理乱码问题 
				/*response.setCharacterEncoding("UTF-8"); 
				response.setHeader("Content-Disposition", "attachment;filename="
						+new String(sheetTitle.getBytes("gbk"), "iso8859-1")+".xlsx"); */
		OutputStream ouputStream = null;
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/vnd.ms-excel");
			String excelFileName = sheetTitle + ".xlsx";
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(excelFileName.getBytes("GB2312"), "iso8859-1"));
			ouputStream = response.getOutputStream();
			workbook.write(ouputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ouputStream.flush();
		ouputStream.close();
	}
}
