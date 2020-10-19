package com.company.genpro.types.json;

import com.haulmont.chile.core.annotations.JavaClass;
import com.haulmont.chile.core.datatypes.Datatype;

import javax.annotation.Nullable;
import java.text.ParseException;
import java.util.Locale;

@JavaClass(String.class)
public class PgJsonBDataType implements Datatype<String> {
    @Override
    public String format(@Nullable Object value) {
        return null;
    }

    @Override
    public String format(@Nullable Object value, Locale locale) {
        return null;
    }

    @Nullable
    @Override
    public String parse(@Nullable String value) throws ParseException {
        return null;
    }

    @Nullable
    @Override
    public String parse(@Nullable String value, Locale locale) throws ParseException {
        return null;
    }

    @Override
    public Class getJavaClass() {
        return null;
    }
}
