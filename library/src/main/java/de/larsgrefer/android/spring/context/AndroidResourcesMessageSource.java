package de.larsgrefer.android.spring.context;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Locale;

import static de.larsgrefer.android.spring.context.LocaleUtil.getLocale;

@Component(AbstractApplicationContext.MESSAGE_SOURCE_BEAN_NAME)
public class AndroidResourcesMessageSource implements MessageSource {

    private final Context context;

    public AndroidResourcesMessageSource(@NonNull Context context) {
        this.context = context;
    }

    @Override
    public String getMessage(@NonNull String code, @Nullable Object[] args, String defaultMessage, @Nullable Locale locale) {
        String messageInternal = getMessageInternal(code, args);

        if (messageInternal != null) {
            return messageInternal;
        } else {
            return formatDefaultMessage(args, defaultMessage);
        }
    }

    @Override
    public String getMessage(@NonNull String code, @Nullable Object[] args, @Nullable Locale locale) throws NoSuchMessageException {
        String message = getMessageInternal(code, args);

        if (message == null) {
            throw new NoSuchMessageException(code, getLocale(context));
        } else {
            return message;
        }
    }

    @Override
    public String getMessage(@NonNull MessageSourceResolvable resolvable, @Nullable Locale locale) throws NoSuchMessageException {
        for (String code : resolvable.getCodes()) {
            String message = getMessageInternal(code, resolvable.getArguments());

            if (message != null) {
                return message;
            }
        }

        return formatDefaultMessage(resolvable.getArguments(), resolvable.getDefaultMessage());
    }

    protected String getMessageInternal(String code, Object[] args) {
        int i = resolveId(code);

        if (i == 0) {
            return null;
        }

        if (valid(args)) {
            return context.getResources().getString(i, args);
        } else {
            return context.getResources().getString(i);
        }
    }

    private String formatDefaultMessage(Object[] args, String defaultMessage) {
        if (valid(args)) {
            return String.format(getLocale(context), defaultMessage, args);
        } else {
            return defaultMessage;
        }
    }

    private boolean valid(@Nullable Object[] args) {
        return args != null && args.length > 0;
    }

    @StringRes
    protected int resolveId(@NonNull String code) {
        try {
            return Integer.parseInt(code);
        } catch (NumberFormatException e) {
            return context.getResources().getIdentifier(code, "string", context.getPackageName());
        }
    }
}
