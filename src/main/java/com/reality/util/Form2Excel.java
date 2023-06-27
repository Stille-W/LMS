package com.reality.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.filechooser.FileSystemView;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.reality.form.DailyReportForm;

import jakarta.servlet.http.HttpSession;

public class Form2Excel {
	
	/*
	 * DailyReportForm Class
	 * 	- DoneThings
	 * 		- things
	 * 		- Completeness
	 * 	- String
	 * やったこと: Map<やったこと, 〇△×> ......String,String
	 * 所感......String
	 * 
	 */
	
	XSSFWorkbook wb;
	XSSFSheet ws;
	
	private DailyReportForm dailyReportForms = new DailyReportForm();
	
	public void runForm2Excel(DailyReportForm drf) throws Exception {
		doExcel(drf);
		buildExcel();
	}
	
	
	private void buildExcel() throws Exception {
		// excel生成
		
		// template利用
		String templatePath = "excel/Excel_template.xlsx";
		System.out.println(this.getClass().getResourceAsStream(templatePath).toString());
		InputStream tmpFile = this.getClass().getResourceAsStream(templatePath);

		wb = new XSSFWorkbook(tmpFile);
		ws = wb.getSheetAt(0);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat fileSdf = new SimpleDateFormat("MMdd");
		
		// Cell処理...
		int row_pos = 4;
		XSSFCellStyle wrapStyle = wb.createCellStyle();
		wrapStyle.setWrapText(true);
		// 日付
		this.setValue(2, 1, sdf.format(new Date()));
		// 今やったこと
		for (int i = 0; i < dailyReportForms.getDoneThingsList().size(); i++) {
			int col_pos = 1;
			if (dailyReportForms.getDoneThingsList().get(i).getThings()==null || dailyReportForms.getDoneThingsList().get(i).getThings().isEmpty()) {
				continue;
			}
			// 1行15字
			this.setValue(row_pos, col_pos++, dailyReportForms.getDoneThingsList().get(i).getThings());
			this.setValue(row_pos, col_pos++, dailyReportForms.getDoneThingsList().get(i).getCompleteness());
			// 1行20字
			this.setValue(row_pos, col_pos++, dailyReportForms.getDoneThingsList().get(i).getImprovement());

			row_pos++;
		}
		// 所感 1行40字
//		this.setValue(11, 1, dailyReportForms.getReflection().replaceAll("(.{40})", "$1\n"));	
		this.setValue(11, 1, dailyReportForms.getReflection());	

		
		// output
		
 		File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
		String outputFilePath = desktopDir.getAbsolutePath()+"\\";
		System.out.println(fileSdf.format(new Date()));
		String outputFileName = "新入社員研修_"+"日報_"+fileSdf.format(new Date()) + ".xlsx";
		Files.createDirectories(new File(outputFilePath).toPath());
		FileOutputStream stream = new FileOutputStream(outputFilePath + outputFileName);
		wb.write(stream);
		stream.close();

		wb.close();

		System.out.println("JOB_DONE");
	}
	
	private void doExcel(DailyReportForm drf) throws IOException {
		// form情報整え
		
		dailyReportForms.setDoneThings(drf.getDoneThings());
		dailyReportForms.setReflection(drf.getReflection().replaceAll("(.{40})", "$1\n"));
		
//		dailyReportForms.getDoneThingsList().forEach(s->{System.out.println(s.getThings());});
				
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
			System.err.println("Value is null");
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
}
