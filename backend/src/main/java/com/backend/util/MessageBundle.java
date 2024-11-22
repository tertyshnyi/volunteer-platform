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

    /**
     * Retrieves the message associated with the provided message link.
     * The message is automatically localized based on the current locale.
     *
     * @param messageLink The enum representing the message to fetch.
     * @return The localized message string.
     */
    public String getMsg(MessageLink messageLink) {
        return messageSource.getMessage(messageLink.name(), null, LocaleContextHolder.getLocale());
    }

    /**
     * Retrieves the message associated with the provided message link,
     * and replaces the placeholder {0} with the provided number.
     *
     * @param messageLink The enum representing the message to fetch.
     * @param number The number to replace in the message.
     * @return The localized message with the number inserted into the placeholder.
     */
    public String getMsg(MessageLink messageLink, int number) {
        return messageSource.getMessage(messageLink.name(), null, LocaleContextHolder.getLocale()).replace("{0}", String.valueOf(number));
    }
}
