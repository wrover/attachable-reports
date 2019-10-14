package me.wrover.cuba.attachablereports.web.api;

import com.haulmont.cuba.gui.components.Window;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>This annotation adds `Files` button to provide access to attachments functionality directly from a browser table or datagrid components.</p>
 * <p>Can be used with both {@link Window.Lookup} and {@link Window.Editor} subclasses controllers.</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface WithAttachments {

    /**
     * @return id of an existing button to use instead of creating a new one
     */
    String buttonId() default "";

    /**
     * @return see {@link ru.beas.cuba.attachable2.web.AttachmentFrame#key()}
     */
    String key() default StringUtils.EMPTY;

    /**
     * @return see {@link ru.beas.cuba.attachable2.web.AttachmentFrame#showByKey()}
     */
    boolean showByKey() default false;
}
