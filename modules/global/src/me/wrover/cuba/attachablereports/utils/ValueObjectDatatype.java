package me.wrover.cuba.attachablereports.utils;

import com.haulmont.chile.core.annotations.JavaClass;
import com.haulmont.chile.core.datatypes.Datatype;
import com.haulmont.cuba.core.entity.EmbeddableEntity;
import com.haulmont.cuba.core.entity.Entity;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;

@SuppressWarnings("unused")
@JavaClass(EmbeddableEntity.class)
public class ValueObjectDatatype implements Datatype<EmbeddableEntity> {

    @Nonnull
    @Override
    public String format(@Nullable Object value) {
        if (value != null)
            return ((Entity) value).getInstanceName();
        else
            return StringUtils.EMPTY;
    }

    @Nonnull
    @Override
    public String format(@Nullable Object value, Locale locale) {
        return format(value);
    }

    @Nullable
    @Override
    public EmbeddableEntity parse(@Nullable String value) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    public EmbeddableEntity parse(@Nullable String value, Locale locale) {
        return parse(value);
    }
}