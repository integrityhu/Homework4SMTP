import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * MailSSLSocketFactory socketFactory= new MailSSLSocketFactory();
 * socketFactory.setTrustAllHosts(true);
 * prop.put("mail.pop3s.ssl.socketFactory", socketFactory);
 */

/**
 * @author pzoli
 * 
 */
public class Homewor4SMTP {
    TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws CertificateException {
        }

        public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1) throws CertificateException {
        }

    } };

    /**
     * @param args
     */
    public static void main(String[] args) {
        Properties props = System.getProperties();
        //props.setProperty("mail.transport.protocol", "smtps");
        props.setProperty("mail.smtp.port", "587"); // TLS //465
        props.setProperty("mail.smtp.host", "smtp.host.hu");
        props.setProperty("mail.smtp.starttls.enable", "true");
        //props.setProperty("mail.smtp.ssl.enable", "true");
        MailSSLSocketFactory socketFactory;
        try {
            socketFactory = new MailSSLSocketFactory();
            socketFactory.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.socketFactory", socketFactory);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        props.setProperty("mail.smtp.ssl.checkserveridentity", "false");
        props.setProperty("mail.smtp.ssl.trust", "*");
        props.setProperty("mail.smtp.socketFactory.fallback", "true");
        props.setProperty("mail.smtp.auth", "true");

        Authenticator auth = new MyAuthenticator();
        Session smtpSession = Session.getInstance(props, auth);
        smtpSession.setDebug(true);
        
        MimeMessage message = new MimeMessage(smtpSession);
        try {
            String email = "name@source.hu";
            String to = "name@target.hu";
            message.setFrom(new InternetAddress(email));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            final String encoding = "UTF-8";

            message.setSubject("teszt", encoding);
            message.setContent("Mail content", "text/plain");
            message.setText("árvíztűrő tükörfúró gép", encoding);
            Transport.send(message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
