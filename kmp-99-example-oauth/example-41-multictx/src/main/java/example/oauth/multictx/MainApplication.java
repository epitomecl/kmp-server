package example.oauth.multictx;

import example.oauth.multictx.parent.ParentCtxConfig;
import org.baeldung.config.AuthorizationServerApplication;
import org.baeldung.config.ResourceServerApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class MainApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(ParentCtxConfig.class)
                .child(AuthorizationServerApplication.class)
                .sibling(ResourceServerApplication.class)
                .run(args);
    }
}
