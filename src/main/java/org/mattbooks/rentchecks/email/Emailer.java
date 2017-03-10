package org.mattbooks.rentchecks.email;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Emailer implements RequestHandler<Request, Response> {
  private final Properties properties = new Properties();

  public Emailer() {
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.socketFactory.port", "465");
    properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.port", "465");
  }

  public Response handleRequest(Request request, Context context) {
    send(StatusMapping.humanReadableStatus(request.getEventType()));

    return Response.success();
  }

  private void send(String eventMessage) {
    Session session = Session.getDefaultInstance(properties,
        new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(System.getenv().get("EMAIL"),
                System.getenv().get("EMAIL_PASSWORD"));
          }
        });

    try {
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress("from@no-spam.com"));
      message.setRecipients(Message.RecipientType.TO,
          InternetAddress.parse(System.getenv().get("TO_EMAIL")));
      message.setSubject(eventMessage);
      message.setText("");

      Transport.send(message);
    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }
  }
}
