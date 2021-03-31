package com.techasians.doctor.subscriber.utils;

import com.techasians.doctor.common.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Translator class.
 * translate language and get message for current context
 *
 * @author ngocquang
 * @since 20/1/2021
 */

@Component
public class BaseComponent {
    @Autowired
    private  MessageSource messageSource;
    public  String translate(String msgCode) {
        Locale locale = LocaleContextHolder.getLocale();
        if (CommonUtils.isNullOrEmpty(msgCode)) return null;
        try {
            return messageSource.getMessage(msgCode, null, locale);
        } catch (NoSuchMessageException ex) {
            return msgCode;
        }

    }

    public  String translate(String msgCode, Object... data) {
        Locale locale = LocaleContextHolder.getLocale();
        if (CommonUtils.isNullOrEmpty(msgCode)) return null;
        try {
            return messageSource.getMessage(msgCode, data, locale);
        } catch (NoSuchMessageException ex) {
            return msgCode;
        }
    }

    public  String translate(String msgCode, String... data) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msgCode, data, locale);
    }

    public  String getMessage(String key, String langCode, String... data) {
        langCode = CommonUtils.convertLangMessage(langCode);
        try {
            return messageSource.getMessage(key, data, Locale.forLanguageTag(langCode));
        } catch (Exception e) {
            return null;
        }
    }

    public  String getMessage(String key, String langCode) {
        langCode = CommonUtils.convertLangMessage(langCode);
        try {
            return messageSource.getMessage(key, null, Locale.forLanguageTag(langCode));
        } catch (Exception e) {
            return null;
        }
    }


}
