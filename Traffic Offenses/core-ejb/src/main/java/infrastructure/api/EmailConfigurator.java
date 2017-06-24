package infrastructure.api;

import javax.ejb.Local;
import javax.mail.Session;
import java.io.IOException;

@Local
public interface EmailConfigurator {

    Session loadEmailSession() throws IOException;
}
