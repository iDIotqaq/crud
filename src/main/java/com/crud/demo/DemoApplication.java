package com.crud.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * @author: zxx
 * @Date: 2018/11/28 20:49
 * @Version 1.0
 */
@SpringBootApplication
public class DemoApplication {

    @Value(value = "${spring.messages.basename}")
    private String basename;
    @Value(value="${lang}")
    private String lang;


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean(name = "messageSource")
    public ResourceBundleMessageSource getMessageResource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(basename);
        return messageSource;
    }
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        //设置默认区域,
        slr.setDefaultLocale(Locale.CHINA);
        return slr;
    }
    @Bean
    public LocalValidatorFactoryBean defaultValidator(){
        LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean();
        factoryBean.setValidationMessageSource(getMessageResource());
        return factoryBean;
    }
}
