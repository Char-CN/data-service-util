package org.blazer.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.blazer.common.file.FHandler;
import org.blazer.common.file.FReader;
import org.blazer.common.util.StringUtil;

public class FilterEmptyLines {

	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			// args = new String[] { "/Users/hyy/Downloads/big.csv" };
		}
		if (args.length == 0) {
			System.err.println("Usage: java -jar xxx.jar " + FilterEmptyLines.class.getName() + " $FilePath $NewFilePath");
			System.exit(-1);
		}
		String filePath = args[0];
		String newFilePath = args[1];
		final FileWriter fw = new FileWriter(new File(newFilePath));
		FReader.create(filePath, new FHandler() {
			@Override
			public void handle(String row) throws IOException {
				if (StringUtil.isNotBlank(row)) {
					fw.write(row);
				}
			}
		});
		fw.close();
	}

}
