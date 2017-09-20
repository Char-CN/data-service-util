package org.blazer.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.blazer.common.conf.Conf;
import org.blazer.common.conf.ConfUtil;
import org.blazer.common.util.PathUtil;

public class EmailUtil {

	public static void main(String[] args) throws IOException {
//		 args = new String[] { "25958235@qq.com,blazerhe@gmail.com",
//		 "aaaaaaa", "ccccccc" };
		// args = new String[] { "25958235@qq.com", "你好222", "aaaaaaa" };
		Conf conf = ConfUtil.getConf(PathUtil.root + "email.conf");
		if (conf.isEmpty()) {
			conf = ConfUtil.getConf("/email.conf");
		}
		// System.out.println(PathUtil.root + "email.conf");
		// System.out.println(conf.toString());
		if (args.length < 3) {
			System.err.println("Usage: java -jar xxx.jar ${toMail} ${subject} ${content} [${filePath_1} ${filePath_2} ...]");
		}
		EmailUtil.config(conf.get("fromMail"), conf.get("fromMailCN"), conf.get("pwd"), conf.get("host"));
		boolean debug = Boolean.parseBoolean(conf.get("debug"));
		boolean flag = EmailUtil.sendMail(debug, args[0], args[1], args[2], args.length > 3 ? args[3].split(",") : null);
		if (flag) {
			System.exit(0);
		} else {
			System.exit(-1);
		}
	}

	// 发件邮箱
	private static String fromMail = "blazerhehe@163.com";
	// 发件名称
	private static String fromMailCN = "测试服务器";
	// 密码
	private static String pwd = "blazer222";
	// 服务器
	private static String host = "smtp.163.com";

	public static void config(String fromMail, String fromMailCN, String pwd, String host) {
		EmailUtil.fromMail = fromMail;
		EmailUtil.fromMailCN = fromMailCN;
		EmailUtil.pwd = pwd;
		EmailUtil.host = host;
	}

	public static boolean sendMail(boolean debug, String toMail, String subject, String content, String... filePaths) {
		if (debug) {
			System.out.println("收件人：" + toMail);
			System.out.println("主题：" + subject);
			System.out.println("内容：" + "<xmp>" + content + "</xmp>");
			if (filePaths != null) {
				for (int i = 0; i < filePaths.length; i++) {
					System.out.println("附件" + (i + 1) + "：" + filePaths[i]);
				}
			}
		}
		boolean flag = false;
		// 端口
		int port = 25;
		try {
			Properties props = new Properties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", "true");
			Session session = Session.getDefaultInstance(props);
			session.setDebug(false);
			MimeMessage message = new MimeMessage(session);
			/* 标题 */
			message.setFrom(new InternetAddress(fromMail, fromMailCN));
			/* 发送给多人，用 逗号","分隔。 */
			for (String toMailOne : toMail.split(",")) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(toMailOne));
			}
			message.setSubject(subject);
			message.addHeader("charset", "UTF-8");

			/* 添加正文内容 */
			Multipart multipart = new MimeMultipart();
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setText(content);
			contentPart.setHeader("Content-Type", "text/html; charset=UTF-8");
			multipart.addBodyPart(contentPart);

			/* 添加附件 */
			if (filePaths != null) {
				for (String filePath : filePaths) {
					File uploadFile = new File(filePath);
					MimeBodyPart fileBody = new MimeBodyPart();
					fileBody.setDataHandler(new DataHandler(new FileDataSource(uploadFile)));
					fileBody.setFileName(MimeUtility.encodeText(uploadFile.getName()));
					multipart.addBodyPart(fileBody);
				}
			}
			message.setContent(multipart);
			message.setSentDate(new Date());
			message.saveChanges();
			Transport transport = session.getTransport("smtp");
			transport.connect(host, port, fromMail, pwd);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			flag = true;
			System.out.println("发送成功！");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("发送失败！");
		}
		return flag;
	}

}
