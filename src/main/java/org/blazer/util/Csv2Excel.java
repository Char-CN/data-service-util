package org.blazer.util;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class Csv2Excel {

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			args = new String[] { "/Users/hyy/Downloads/big.csv" };
		}
		if (args.length == 0) {
			System.err.println("Usage: java -jar xxx.jar $CsvPath [$Delimeter]");
			System.exit(-1);
		}
		String delimeter = "\t";
		String path = args[0];
		if (args.length > 1) {
			delimeter = args[1];
		}
		TimeUtil time = TimeUtil.createAndPoint();
		time.printMs("开始执行");
		SXSSFWorkbook wb = new SXSSFWorkbook();
		FileOutputStream out = null;
		String outPath = path + ".xlsx";
		try {
			wb.setCompressTempFiles(true);
			SXSSFSheet sh = wb.createSheet("Sheet");
			sh.setRandomAccessWindowSize(100);
			time.printMs("初始化excel");
			new FReader(path, new FHandler(sh, delimeter) {

				SXSSFSheet sh = (SXSSFSheet) getParameter(0);
				String delimeter = (String) getParameter(1);

				public void handle(String row) throws IOException {
					String[] strs = row.split(delimeter);
					Row r = sh.createRow(index());
					for (int i = 0; i < strs.length; i++) {
						Cell cell = r.createCell(i);
						cell.setCellValue(strs[i]);
					}
				}

			});
			time.printMs("读取文件csv文件并且写入excel");
			out = new FileOutputStream(outPath);
			wb.write(out);
			time.printMs("生成excel");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		} finally {
			try {
				wb.close();
			} catch (Exception e){
			}
			try {
				out.close();
			} catch (Exception e){
			}
		}
		System.exit(0);
	}

	public static void convert(String path, String delimeter) throws IOException {
		TimeUtil time = TimeUtil.createAndPoint();
		String outPath = path + ".xlsx";
		SXSSFWorkbook wb = new SXSSFWorkbook();
		wb.setCompressTempFiles(true);
		SXSSFSheet sh = wb.createSheet("Sheet");
		sh.setRandomAccessWindowSize(100);
		time.printMs("初始化excel");
		new FReader(path, new FHandler(sh, delimeter) {

			SXSSFSheet sh = (SXSSFSheet) getParameter(0);
			String delimeter = (String) getParameter(1);

			public void handle(String row) throws IOException {
				String[] strs = row.split(delimeter);
				Row r = sh.createRow(index());
				for (int i = 0; i < strs.length; i++) {
					Cell cell = r.createCell(i);
					cell.setCellValue(strs[i]);
				}
			}

		});
		time.printMs("读取文件csv文件并且写入excel");
		FileOutputStream out = new FileOutputStream(outPath);
		wb.write(out);
		wb.close();
		time.printMs("生成excel");
		out.close();
	}

}
