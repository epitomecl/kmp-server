package example.oauth.multictx.parent;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:/parent.properties")
public class ParentCtxConfig {

    @Bean(name = "parent_bean")
    public String getParentBean() {
        return "parent_bean";
    }
}
