package infrastructure.configuration;

import infrastructure.api.EmailConfigurator;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Singleton
public class EmailConfiguratorBean implements EmailConfigurator{

    private static final String EMAIL_CONFIG_RESOURCE = "/properties/email-config.properties";

    private static final String EMAIL_LOGIN_PROPERTY = "mail.login";

    private static final String EMAIL_PASSWORD_PROPERTY = "mail.password";

    @Override
    public Session loadEmailSession() throws IOException {
        try(InputStream inputStream = getClass().getResourceAsStream(EMAIL_CONFIG_RESOURCE)){
            Properties properties = new Properties();
            properties.load(inputStream);
            return generateSessionForEmail(properties);
        }

    }

    private Session generateSessionForEmail(Properties props){
        String login = getLoginFromEmailAccount(props);
        String password = getPasswordFromEmailAccount(props);
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(login, password);
                    }
                });
        return session;
    }

    private String getLoginFromEmailAccount(Properties properties){
        return properties.getProperty(EMAIL_LOGIN_PROPERTY);
    }

    private String getPasswordFromEmailAccount(Properties properties){
        return properties.getProperty(EMAIL_PASSWORD_PROPERTY);
    }

}
