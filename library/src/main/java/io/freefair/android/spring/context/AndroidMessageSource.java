package io.freefair.android.spring.context;

import android.app.Application;
import android.support.annotation.StringRes;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

public class AndroidMessageSource implements MessageSource {

    Application application;

    @Override
    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        int i = resolveId(code);

        return i != 0 ? application.getResources().getString(i, args) : defaultMessage;
    }

    @Override
    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        int i = resolveId(code);

        if (i != 0) {
            return application.getResources().getString(i, args);
        } else {
            throw new NoSuchMessageException(code);
        }
    }

    @Override
    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        return null;
    }

    @StringRes
    protected int resolveId(String code) {
        try {
            return Integer.parseInt(code);
        } catch (NumberFormatException e) {
            return application.getResources().getIdentifier(code, "string", null);
        }
    }
}
