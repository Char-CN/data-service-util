package org.blazer.run;

import java.lang.reflect.Method;

import org.blazer.util.StringUtil;

public class RunManager {

	public static void main(String[] args) {
		// args = new String[] { "com.blazer.convert.Excel2Csv", "D:\\data\\",
		// "bbb.xls" };
		if (args.length < 1) {
			System.err.println("Parameter is not valid , args[0] must be pkg.mainclass");
			System.exit(-1);
		}
		String pkgMainClass = args[0];
		try {
			Method mainMethod = Class.forName(pkgMainClass).getMethod("main", String[].class);
			mainMethod.invoke(null, (Object) StringUtil.removeIndex(args, 0));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

}
