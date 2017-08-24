package de.larsgrefer.android.spring.context;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.HierarchicalMessageSource;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

public class AndroidLocaleAwareMessageSourceWrapper implements HierarchicalMessageSource {

    @Getter
    @Setter
    private boolean ignoreGivenLocale;

    private MessageSource parentMessageSource;

    private final Context context;

    public AndroidLocaleAwareMessageSourceWrapper(Context context) {
        this.context = context;
    }

    @Override
    public void setParentMessageSource(MessageSource parent) {
        this.parentMessageSource = parent;
    }

    @Override
    public MessageSource getParentMessageSource() {
        return this.parentMessageSource;
    }

    @Override
    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return parentMessageSource.getMessage(code, args, defaultMessage, resolveLocale(locale));
    }

    @Override
    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        return parentMessageSource.getMessage(code, args, resolveLocale(locale));
    }

    @Override
    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        return parentMessageSource.getMessage(resolvable, resolveLocale(locale));
    }

    @NonNull
    private Locale resolveLocale(@Nullable Locale locale) {
        if (locale != null && !ignoreGivenLocale) {
            return locale;
        } else {
            return LocaleUtil.getLocale(context);
        }
    }

}
