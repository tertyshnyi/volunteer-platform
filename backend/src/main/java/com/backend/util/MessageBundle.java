package com.backend.util;

import com.backend.models.enums.MessageLink;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageBundle {
    private final MessageSource messageSource;

    public MessageBundle(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMsg(MessageLink messageLink) {
        return messageSource.getMessage(messageLink.name(), null, LocaleContextHolder.getLocale());
    }

    public String getMsg(MessageLink messageLink, int number) {
        return messageSource.getMessage(messageLink.name(), null, LocaleContextHolder.getLocale()).replace("{0}", String.valueOf(number));
    }
}
