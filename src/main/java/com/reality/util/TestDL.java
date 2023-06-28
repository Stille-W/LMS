package com.reality.util;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

@RestController
public class TestDL {

    @GetMapping("/getExcel")
    public void testURL(HttpServletResponse response) throws IOException {

        String templatePath = "excel/ExcelMM_template.xlsx";
        InputStream tmpFile = this.getClass().getResourceAsStream(templatePath);

//        File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
//        String outputFilePath = desktopDir.getAbsolutePath()+"\\";

        String outputFileName = "_月報_" + ".xlsx";

        XSSFWorkbook wb = new XSSFWorkbook(tmpFile);

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

        wb.close();
    }

//    public void testURL() throws IOException {
//        String urlPath = "http://localhost:8080/goodsunlms/getExcel";
//        File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
//        String outputFilePath = desktopDir.getAbsolutePath()+"\\";
//
//        URL url = new URL(urlPath);
//
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("get");
//        conn.setDoInput(true);
//        conn.setDoOutput(true);
//        conn.setUseCaches(false);
//        conn.connect();
//        InputStream is = conn.getInputStream();
//        BufferedInputStream bis = new BufferedInputStream(is);
//
//        String templatePath = "excel/ExcelMM_template.xlsx";
//        InputStream tmpFile = this.getClass().getResourceAsStream(templatePath);
//
//        XSSFWorkbook wb = new XSSFWorkbook(tmpFile);
//        FileOutputStream os = new FileOutputStream(outputFilePath + "test.xlsx");
//        wb.write(os);
//
//
//
//    }

//    @RequestMapping(value = "/getExcel", method = RequestMethod.GET)
//    public void createBoxListExcel(HttpServletResponse response) throws Exception {
//
//        /**
//         *  这部分是刚刚导入 Excel 文件的代码，省略
//         */
//
//        //创建文件本地文件
//        //直接将文件创建在项目目录中
//        String filePath = "人员数据.xlsx";
//        String templatePath = "excel/ExcelMM_template.xlsx";
//        InputStream tmpFile = this.getClass().getResourceAsStream(templatePath);
//        File dbfFile = new File(templatePath);
////        if (!dbfFile.exists() || dbfFile.isDirectory()) {
////            dbfFile.createNewFile();
////        }
//        //使用 Workbook 类的工厂方法创建一个可写入的工作薄(Workbook)对象
//        XSSFWorkbook wwb = new XSSFWorkbook(dbfFile);
//        //如果文件不存在，则创建一个新的文件
//
//
//
//
//        String fileName = new String("人员数据.xlsx".getBytes(), "ISO-8859-1");
//        //设置文件名
//        response.addHeader("Content-Disposition", "filename=" + fileName);
//        OutputStream outputStream = response.getOutputStream();
//        FileInputStream fileInputStream = new FileInputStream(filePath);
//        byte[] b = new byte[1024];
//        int j;
//        while ((j = fileInputStream.read(b)) > 0) {
//            outputStream.write(b, 0, j);
//        }
//        fileInputStream.close();
//        outputStream.flush();
//        outputStream.close();
//    }

    /*
    // 生成
// 		File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
//		String outputFilePath = desktopDir.getAbsolutePath()+"\\";
//		System.out.println(fileSdf.format(new Date()));
		String outputFileName = yyyy+mm+"_月報_"+ session.getAttribute("fullName").toString() + ".xlsx";
//		Files.createDirectories(new File(outputFilePath).toPath());
//		FileOutputStream stream = new FileOutputStream(outputFilePath + outputFileName);





//		HttpServletResponse resp = response;
//		response.reset();
//		response.setContentType("application/octet-stream");
//		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(outputFileName, "UTF-8"));
//		String fileNameURL = URLEncoder.encode(outputFileName, "UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		response.setHeader("Content-disposition", "attachment;filename=" + fileNameURL + ";" + "filename*=utf-8''" + fileNameURL);
//		response.setContentType("application/octet-stream");
//		response.flushBuffer();
//		OutputStream os = response.getOutputStream();


//		FileSystemResource resource = new FileSystemResource((Path) wb);
//		InputStream in = resource.getInputStream();
//		byte[] bytes = new byte[in.available()];
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Access=Control-Expose-Headers", "Content-Disposition");
//		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//		headers.add("Content-Disposition", "attachment;filename=" + URLEncoder.encode(outputFileName, "UTF-8"));
//		headers.add("Pragma", "no-cache");
//		headers.add("Expires", "0");



//		wb.write(os);
//		os.flush();
//		os.close();

//		wb.write(stream);
//		stream.close();

		wb.close();
     */
}
