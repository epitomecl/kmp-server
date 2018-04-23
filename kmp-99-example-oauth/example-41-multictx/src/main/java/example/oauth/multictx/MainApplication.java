package example.oauth.multictx;

import demo.AuthserverApplication;
import demo.ResourceApplication;
import demo.UiApplication;
import example.oauth.multictx.parent.ParentCtxConfig;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class MainApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(ParentCtxConfig.class)
                .child(AuthserverApplication.class)
                .sibling(ResourceApplication.class)
                .sibling(UiApplication.class)
                .run(args);
    }
}
