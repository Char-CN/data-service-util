package org.blazer.util;

import java.io.IOException;

import org.blazer.common.conf.Conf;
import org.blazer.common.conf.ConfUtil;
import org.blazer.common.file.FClassReader;
import org.blazer.common.file.FHandler;
import org.blazer.common.file.FReader;
import org.blazer.common.util.PathUtil;

public class EmailUtil_Csv2HtmlTable {

	public static void main(String[] args) throws IOException {
//		 args = new String[] { "25958235@qq.com,blazerhe@gmail.com", "你好222", "aaaaaaa", "/Users/hyy/AutoWork/spider/data/data_pos_organize_20170918.csv", "/Users/hyy/AutoWork/spider/data/data_pos_organize_20170918.csv" };
		Conf conf = ConfUtil.getConf(PathUtil.root + "email.conf");
		if (conf.isEmpty()) {
			conf = ConfUtil.getConf("/email.conf");
		}
		System.out.println("email配置文件路径:" + PathUtil.root + "email.conf");
		// System.out.println(conf.toString());
		if (args.length < 3) {
			System.err.println("Usage: java -jar xxx.jar ${toMail} ${subject} ${content} ${csvFilePath} [${filePath_1} ${filePath_2} ...]");
		}
		EmailUtil.config(conf.get("fromMail"), conf.get("fromMailCN"), conf.get("pwd"), conf.get("host"));

		final StringBuilder content = new StringBuilder();
		FClassReader.create("/email_template.html", new FHandler() {
			@Override
			public void handle(String row) throws IOException {
				content.append(row);
			}
		});
		final StringBuilder moreThan100 = new StringBuilder("false");
		content.append("<p>").append(args[2]).append("</p>");
		content.append("<table>");
		FReader.create(args[3], new FHandler() {
			@Override
			public void handle(String row) throws IOException {
				if (index() >= 100) {
					moreThan100.setLength(0);
					moreThan100.append("true");
					return;
				}
				String[] strs = row.split("\t");
				if (index() == 0) {
					content.append("<thead><tr>");
					for (String str : strs) {
						content.append("<th>").append(str).append("</th>");
					}
					content.append("</tr></thead>");
				} else {
					if (index() == 1) {
						content.append("<tbody>");
					}
					content.append("<tr>");
					for (String str : strs) {
						content.append("<td>").append(str).append("</td>");
					}
					content.append("</tr>");
				}
			}
		});
		content.append("</tbody>");
		content.append("</table>");
		// 大于100行
		if (Boolean.parseBoolean(moreThan100.toString())) {
			content.append("<h3>结果行数大于100，不予以展示，请查收附件！</h3>");
		} else {
			content.append("<h4>结果展示完毕！</h4>");
		}
		boolean debug = Boolean.parseBoolean(conf.get("debug"));
		boolean flag = EmailUtil.sendMail(debug, args[0], args[1], content.toString(), args.length > 3 ? args[3].split(",") : null);
		if (flag) {
			System.exit(0);
		} else {
			System.exit(-1);
		}
	}

}
