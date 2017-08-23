package de.larsgrefer.android.spring.context;

import android.content.Context;
import android.support.annotation.StringRes;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Locale;

@AllArgsConstructor
@Component(AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME)
public class AndroidMessageSource implements MessageSource {

    private Context context;

    @Override
    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        int i = resolveId(code);

        return i != 0 ? context.getResources().getString(i, args) : defaultMessage;
    }

    @Override
    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        int i = resolveId(code);

        if (i != 0) {
            return context.getResources().getString(i, args);
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
            return context.getResources().getIdentifier(code, "string", null);
        }
    }
}
