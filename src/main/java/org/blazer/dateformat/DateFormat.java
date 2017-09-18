package org.blazer.dateformat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.blazer.common.util.IntegerUtil;

/**
 * 格式：
 * 
 * today-1|yyyy-MM-dd 00:59:59
 * 
 * yesterday-1|yyyy-MM-dd 00:59:59
 * 
 * @author hyy
 *
 */
public class DateFormat {

	private static Calendar c = Calendar.getInstance();
	private static String format = "yyyy-MM-dd";

	public static void main(String[] args) {
		// args = new String[] { "adsasdasd|asdss" };
		// args = new String[] { "today-1|yyyy-MM-dd 00:59:59" };
		// args = new String[] { "yesterday-1|yyyy-MM-dd 00:59:59" };
		if (args.length != 1) {
			System.out.println("Params length error.");
			System.out.println("Usage : java -jar xxx.jar df:today:yyyyMMdd");
			System.exit(-1);
		}
		String str = args[0];
		String[] strs = str.split("[|]");
		if (strs.length == 1) {
			System.out.println(str);
			System.exit(0);
		}
		if (strs.length == 2) {
			format = strs[1];
		}
		// 函数
		String func = strs[0];
		if (func.startsWith("today")) {
			today(func);
		} else if (func.startsWith("yesterday")) {
			yesterday(func);
		} else if (func.startsWith("month")) {
			month(func);
		} else {
			System.out.println(str);
			System.exit(0);
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		System.out.println(sdf.format(c.getTime()));
		System.exit(0);
	}

	private static void yesterday(String func) {
		c.add(Calendar.DAY_OF_YEAR, -1);
		if (func.indexOf("-") != -1) {
			c.add(Calendar.DAY_OF_YEAR, IntegerUtil.getInt(func.substring(func.indexOf("-"))));
		}
		if (func.indexOf("+") != -1) {
			c.add(Calendar.DAY_OF_YEAR, IntegerUtil.getInt(func.substring(func.indexOf("+"))));
		}
	}

	private static void today(String func) {
		if (func.indexOf("-") != -1) {
			c.add(Calendar.DAY_OF_YEAR, IntegerUtil.getInt(func.substring(func.indexOf("-"))));
		}
		if (func.indexOf("+") != -1) {
			c.add(Calendar.DAY_OF_YEAR, IntegerUtil.getInt(func.substring(func.indexOf("+"))));
		}
	}

	private static void month(String func) {
		if (func.indexOf("-") != -1) {
			c.add(Calendar.MONTH, IntegerUtil.getInt(func.substring(func.indexOf("-"))));
		}
		if (func.indexOf("+") != -1) {
			c.add(Calendar.MONTH, IntegerUtil.getInt(func.substring(func.indexOf("+"))));
		}
	}

}
