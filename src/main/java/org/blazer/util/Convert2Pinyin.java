package org.blazer.util;

public class Convert2Pinyin {

	public static void main(String[] args) {
		if (args.length == 0) {
			System.err.println("Usage: java -jar xxx.jar $String");
			System.exit(-1);
		}
		StringBuffer sb = new StringBuffer();
		for (String arg : args) {
			if (sb.length() != 0) {
				sb.append(" ");
			}
			sb.append(arg);
		}
		System.out.println(PinyinUtil.get(sb.toString()));
	}

}
