package Scripts;

import java.io.IOException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail {
	ExcelReadData ReadDrv=new ExcelReadData();
	String ExcelData;
	String FromXLSNDMLArrayDataset[]=new String[100];
	public void CustomSendMail(String FilePath,String SendMailPath,String sheetname,String ZipFile) throws IOException, MessagingException {
		int i=0;
		FromXLSNDMLArrayDataset=ReadDrv.XLSendMailData(SendMailPath, sheetname);
		Properties props=new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.host", FromXLSNDMLArrayDataset[i+1]);
		props.put("mail.transport.protocol", "smtp");
		props.put("$emailSmtpServerPort", FromXLSNDMLArrayDataset[i+2]);
		props.put("java.net.preferIPv4Stack", "true");
		props.put("mail.debug", "true");
		props.put("mail.store.protocol", "pop3");
		props.put("mail.smtp.EnableSSL.enable", "true");
		Session mailSession=Session.getDefaultInstance(props);
		MimeMessage message=new MimeMessage(mailSession);
		message.setFrom(new InternetAddress(FromXLSNDMLArrayDataset[i+7]));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(FromXLSNDMLArrayDataset[i+8]));
		message.addRecipient(Message.RecipientType.CC, new InternetAddress(FromXLSNDMLArrayDataset[i+9]));
		
		message.setSubject(FromXLSNDMLArrayDataset[i+10]);
		BodyPart objMessagePart1=new MimeBodyPart();
		objMessagePart1.setText(FromXLSNDMLArrayDataset[i+11]);
		Multipart multipart=new MimeMultipart();
		
		BodyPart objMessageBodyPart2=new MimeBodyPart();
		objMessageBodyPart2.setText(FilePath);
		
		DataSource TestScenarioExecutionLog=new FileDataSource(FilePath);
		objMessageBodyPart2.setDataHandler(new DataHandler(TestScenarioExecutionLog));
		objMessageBodyPart2.setFileName("TestScenarioExecutionLog.xlsx");
		
		BodyPart objMessageBodyPart3=new MimeBodyPart();
		objMessageBodyPart3.setText(ZipFile);
		
		DataSource source2=new FileDataSource(ZipFile);
		objMessageBodyPart3.setDataHandler(new DataHandler(source2));
		objMessageBodyPart3.setFileName("TestReports.zip");
		
		multipart.addBodyPart(objMessagePart1);
		multipart.addBodyPart(objMessageBodyPart2);
		multipart.addBodyPart(objMessageBodyPart3);
		message.setContent(multipart);
		
		Transport transport=mailSession.getTransport("smtp");
		transport.connect(FromXLSNDMLArrayDataset[i+1],FromXLSNDMLArrayDataset[i+5],FromXLSNDMLArrayDataset[i+6]);
		
		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
	}

}
