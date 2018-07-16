package com.epitomecl.kmp.cc.config;

import com.epitomecl.kmp.core.common.HomeConfigurator;
import com.epitomecl.kmp.core.common.MyProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

import java.lang.invoke.MethodHandles;
import java.util.Properties;

public class PaprikaPropertiesFactoryBean implements FactoryBean<Properties> {
    private static Class<?> clazz = MethodHandles.lookup().lookupClass();
    private static final Logger logger = LoggerFactory.getLogger(clazz);

    private static PaprikaPropertiesFactoryBean paprikaPropertiesFactoryBean;

    public static PaprikaPropertiesFactoryBean getInstance() {
        if (paprikaPropertiesFactoryBean == null) {
            paprikaPropertiesFactoryBean = new PaprikaPropertiesFactoryBean();
        }
        return paprikaPropertiesFactoryBean;
    }

    private final Properties properties;


    public PaprikaPropertiesFactoryBean() {
        HomeConfigurator.changeLogConfiguration();

        // [4] Result Properties
        MyProperties myProperties = new MyProperties();
        this.properties = myProperties.getObject();
    }

    @Override
    public Properties getObject() {
        return properties;
    }

    @Override
    public Class<Properties> getObjectType() {
        return Properties.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
