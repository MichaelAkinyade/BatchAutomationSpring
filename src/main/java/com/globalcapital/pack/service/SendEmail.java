package com.globalcapital.pack.service;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.globalcapital.database.datasource.H2DatabaseLuncher;
import com.globalcapital.pack.bean.EmailBean;
import com.globalcapital.utility.DateUtility;

public class SendEmail {

	public SendEmail() {

	}

	private static InternetAddress[] buildRecipientToAddress() throws AddressException, IOException {
		int counter = 0;
		int recipientLength = H2DatabaseLuncher.getRecipient().size();
		InternetAddress[] result = new InternetAddress[recipientLength];

		for (EmailBean emailBean : H2DatabaseLuncher.getRecipient()) {

			result[counter] = new InternetAddress(emailBean.getEmaillAddress());
			counter++;
		}

		return result;

	}

	public static void sendMail(String operationType) throws IOException {

		// Recipient's email ID needs to be mentioned.
		// String to = "michael.akinyade@globalcapital.com.mt";

		// Sender's email ID needs to be mentioned
		String from = "noreply@globalcapital.com.mt";
		final String username = "";
		final String password = "";

		// SMTP host address
		String host = "192.168.0.3";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "false");
		// props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "25");
		Session session;
		// Get the Session object.
		session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}

		});

		try {
			// Create a default MimeMessage object.
			Message message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, buildRecipientToAddress());

			// Set Subject: header field
			message.setSubject("Batch Report Error Notification --" + operationType);
			message.setContent("<div><img src=\"cid:" + ">" + "<h2 text-align=\"justify\">"
					+ "Error Generation Notitification<h2/></div>"
					+ "<br><div><div><h3> Report name:     &nbsp &nbsp <i>" + operationType + "</i> </h3>"
					+ "<h3> Report Date:    &nbsp &nbsp <i>" + DateUtility.DateAndTimeNowToString() + "</i> </h3>"
					+ "<h3> Report Time:    &nbsp &nbsp <i>" + DateUtility.DateNowToString() + "</i> </h3></div></div>",
					"text/html");
			;

			Transport.send(message);

			System.out.println("Sent message successfully....");

		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
