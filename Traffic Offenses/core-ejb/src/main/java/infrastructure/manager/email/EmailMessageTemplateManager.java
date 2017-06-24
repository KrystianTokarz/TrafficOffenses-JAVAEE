package infrastructure.manager.email;

import domainmodel.embaddable.UserData;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.persistence.Temporal;
import java.io.StringWriter;
import java.util.Properties;

@Singleton
@LocalBean
public class EmailMessageTemplateManager {

    private VelocityEngine velocityEngine;


    @PostConstruct
    private void init(){
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "class");
        properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine = new VelocityEngine();
        velocityEngine.init(properties);
    }

    public Template loadTemplate(String templatePath){
        return velocityEngine.getTemplate(templatePath, "UTF-8");
    }


}
