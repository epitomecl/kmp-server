package example.oauth.multictx;

import example.oauth.multictx.child.first.ChildFirstCtxConfig;
import example.oauth.multictx.child.second.ChildSecondCtxConfig;
import example.oauth.multictx.parent.ParentCtxConfig;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class MultiCtxApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(ParentCtxConfig.class)
                .child(ChildFirstCtxConfig.class)
                .sibling(ChildSecondCtxConfig.class)
                .run(args);
    }
}
