import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 
 */

class MyAuthenticator extends Authenticator {

    public PasswordAuthentication getPasswordAuthentication() {
        String username = "name@host.hu";
        String password = "****";
        return new PasswordAuthentication(username, password);
    }
}