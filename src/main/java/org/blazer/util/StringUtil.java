package org.blazer.util;

public class StringUtil {

	public static void main(String[] args) {
		String[] sss = new String[] { "a", "b", "c", "d" };

		for (String s : StringUtil.removeIndex(sss, 2)) {
			System.out.println(s);
		}
	}

	public static String[] removeIndex(String[] args, int index) {
		if (args == null || args.length == 0) {
			return null;
		} else if (args.length <= index) {
			return null;
		} else if (index == 0) {
			String[] retArgs = new String[args.length - 1];
			System.arraycopy(args, 1, retArgs, 0, retArgs.length);
			return retArgs;
		}
		String[] retArgs = new String[args.length - 1];
		int successIndex = 0;
		for (int i = 0; i < args.length; i++) {
			if (index != i) {
				retArgs[successIndex] = args[i];
				successIndex++;
			}
		}
		return retArgs;
	}

	public static String getString(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}

	public static boolean isBlank(String str) {
		return str == null || "".equals(str) || "".equals(str.trim());
	}

}
