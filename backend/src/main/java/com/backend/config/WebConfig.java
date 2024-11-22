package com.backend.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for setting up the message source for internationalization (i18n).
 *
 * This class configures a `MessageSource` bean used for resolving messages from resource bundles,
 * allowing for multi-language support throughout the application. It sets up the base name for the message
 * resource file and the default character encoding.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures the message source bean for the application.
     *
     * This method sets up the resource bundle that contains the application messages. The message source is used
     * to fetch messages based on the user's locale, allowing for internationalization (i18n). It specifies the
     * base name of the message file (without the file extension) and the default encoding (UTF-8).
     *
     * @return a configured `MessageSource` bean.
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
