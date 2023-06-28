package com.reality.util;

import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.reality.repository.AttendanceRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import com.reality.entity.Attendance;


import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Form2ExcelMM {

	@Autowired
	AttendanceRepository attendanceRepository;

	XSSFWorkbook wb;
	XSSFSheet ws;
	boolean isCal = false;

	@GetMapping("/genMonth")
	public void buildExcel(Integer month, HttpSession session, HttpServletResponse response) throws Exception {

		List<Attendance> list = attendanceRepository.findByMMAndUserIdOrderByDateAsc(
				month, Integer.parseInt(session.getAttribute("userId").toString()));
		String date = Calendar.getInstance().get(Calendar.YEAR)+"/"+month;
		
		// excel生成
		
		// template利用
		String templatePath = "excel/ExcelMM_template.xlsx";
		InputStream tmpFile = this.getClass().getResourceAsStream(templatePath);

		wb = new XSSFWorkbook(tmpFile);
		ws = wb.getSheetAt(0);
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
		SimpleDateFormat sdfE = new SimpleDateFormat("E");
		XSSFCreationHelper createHelper = wb.getCreationHelper();	

		String yyyy = date.split("/")[0];
		String mm = Integer.parseInt(date.split("/")[1])<10?"0"+date.split("/")[1]:date.split("/")[1];
			
		// Cell処理...
		int row_pos = 4;
		
		XSSFCellStyle wrapStyle = wb.createCellStyle();
		wrapStyle.setWrapText(true);
		// 日付
		this.setValue(1, 0, yyyy+"年度"+mm+"月度");
		// 氏名
		this.setValue(1, 7, session.getAttribute("fullName").toString());
		for (int i = 0; i < list.size(); i++) {
			if (row_pos>34) {
				insertRow(wb, ws, row_pos-1, 1);
				isCal = true;
			}
			int col_pos = 0;
			// 日付
			this.setValue(row_pos, col_pos++, sdf.format(list.get(i).getDate()));
			this.setValue(row_pos, col_pos++, sdfE.format(list.get(i).getDate()));
			// 開始終了
			this.setValue(row_pos, col_pos++, list.get(i).getStartTime());			
			this.setValue(row_pos, col_pos++, list.get(i).getEndTime());
			
			// 区分
			this.setValue(row_pos, col_pos++, list.get(i).getDivision());
			// 時間
//			System.out.println("row: "+row_pos+";cell: "+col_pos);
			ws.getRow(row_pos).getCell(col_pos).getCellStyle().setDataFormat(createHelper.createDataFormat().getFormat("[h]:mm"));
			ws.getRow(row_pos).getCell(col_pos++).setCellValue(DateUtil.convertTime(list.get(i).getWorkHours()));		
			// プロジェクト
			this.setValue(row_pos, col_pos++, list.get(i).getProject());
			// 作業場所
			this.setValue(row_pos, col_pos++, list.get(i).getPlace());
			// 備考
			this.setValue(row_pos, col_pos++, list.get(i).getRemarks());

			row_pos++;
		}
		
		if (isCal) {
			ws.getRow(row_pos).getCell(5).setCellFormula("SUM(F5:F"+Integer.toString(row_pos)+")");
		}
		
		wb.setForceFormulaRecalculation(true);
		wb.getCreationHelper().createFormulaEvaluator().evaluateAll();
		// output

		// 生成
		String outputFileName = yyyy+mm+"_月報_"+ session.getAttribute("fullName").toString() + ".xlsx";
		// local
// 		File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
//		String outputFilePath = desktopDir.getAbsolutePath()+"\\";
//		System.out.println(fileSdf.format(new Date()));		
//		Files.createDirectories(new File(outputFilePath).toPath());
//		FileOutputStream stream = new FileOutputStream(outputFilePath + outputFileName);

		// download
		response.reset();
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(outputFileName, "UTF-8"));
		String fileNameURL = URLEncoder.encode(outputFileName, "UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-disposition", "attachment;filename=" + fileNameURL + ";" + "filename*=utf-8''" + fileNameURL);
		response.setContentType("application/octet-stream");
		response.flushBuffer();
		OutputStream os = response.getOutputStream();

		wb.write(os);
		os.flush();
		os.close();

		// local用
//		wb.write(stream);
//		stream.close();

		wb.close();

		System.out.println("JOB_DONE");
	}
	
	private void setValue(int row_pos, int col_pos, Object value) throws Exception {
		// create and set cell
		if (ws.getRow(row_pos) == null) {
			ws.createRow(row_pos);
		}
		if (ws.getRow(row_pos).getCell(col_pos) == null) {
			XSSFCell newCell = ws.getRow(row_pos).createCell(col_pos);
			try {
				ws.getRow(row_pos).getCell(col_pos).getCellStyle().setWrapText(true);
				newCell.setCellStyle(ws.getRow(row_pos).getCell(col_pos).getCellStyle());
			} catch (Exception ex) {
				String exm = ex.getMessage();
				System.out.println(exm);
				System.out.println(ex.getStackTrace());
			}
		}

		// if NULL
		if (value == null) {
//			System.err.println("Value is null");
			value = "";
			return;
		}
		String className = value.getClass().getName();
//		System.out.println(value + " is " + className);

		if (className == "java.lang.String") {
			ws.getRow(row_pos).getCell(col_pos).setCellValue((String) value);
		} else {
			throw new Exception("Cell format not supported: " + className);
		}		
	}
	
	private void insertRow(XSSFWorkbook workbook, XSSFSheet sheet, int startRow , int rows) {
		sheet.shiftRows(startRow+1, sheet.getLastRowNum(), rows, true, false);
		System.out.println(sheet.getLastRowNum());
		
		for (int i = 0; i < rows; i++) {
			XSSFRow srcRow = null;
			XSSFRow tgtRow = null;
			XSSFCell srcCell = null;
			XSSFCell tgtCell = null;
			
					
			srcRow = sheet.getRow(startRow-1);
			tgtRow = sheet.createRow(startRow+1);
			tgtRow.setHeight(srcRow.getHeight());
			
			for (int j = srcRow.getFirstCellNum(); j < 9; j++) {
				srcCell = srcRow.getCell(j);
				tgtCell = tgtRow.createCell(j);
				
				tgtCell.setCellStyle(srcCell.getCellStyle());
			}
			
		}
		
		
	}
}
