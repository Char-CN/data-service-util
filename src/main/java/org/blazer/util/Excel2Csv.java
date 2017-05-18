package org.blazer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.monitorjbl.xlsx.StreamingReader;

public class Excel2Csv {

	public static void main(String[] args) {
		if (args.length == 0) {
			args = new String[] { "/Users/hyy/Downloads/big.xlsx" };
		}
		if (args.length == 0) {
			System.err.println("Usage: java -jar xxx.jar $ExcelPath [$Delimeter]");
			System.exit(-1);
		}
		TimeUtil tu = TimeUtil.createAndPoint();
		tu.printMs("开始执行");
		String delimeter = "\t";
		String path = args[0];
		if (args.length > 1) {
			delimeter = args[1];
		}
		String outPath = path + ".csv";
		OutputStreamWriter out = null;
		try {
			out = new OutputStreamWriter(new FileOutputStream(outPath), "UTF-8");
			InputStream is = new FileInputStream(new File(path));
			Workbook workbook = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(is);
			Sheet sheet = workbook.getSheetAt(0);
			tu.printMs("加载excel文件");
			StringBuilder sb = new StringBuilder();
			int count = 0;
			for (Row r : sheet) {
				if (r == null) {
					System.out.println("空行:" + count);
					continue;
				}
				sb.setLength(0);
				if (r.getRowNum() != 0) {
					sb.append("\n");
				}
				for (Cell c : r) {
					if (c.getColumnIndex() != 0) {
						sb.append(delimeter);
					}
					// 强制过滤换行符
					String value = c.getStringCellValue().replaceAll("\r", "").replaceAll("\n", "");
					sb.append(value);
				}
				out.write(sb.toString());
				count++;
			}
			tu.printMs("读取excel并且写入csv，行数[" + count + "]");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		} finally {
			try {
				out.close();
			} catch (IOException e) {
			}
		}
		System.exit(0);
	}

}
