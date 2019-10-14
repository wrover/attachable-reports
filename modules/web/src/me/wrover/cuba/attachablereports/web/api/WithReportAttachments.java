package me.wrover.cuba.attachablereports.web.api;

import com.haulmont.cuba.gui.components.Window;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>This annotation provides access to reports functionality directly from a browser table or datagrid components.</p>
 * <p>For Table it renders two buttons. One is for browsing attached generated reports, dnd the other is for starting new reports.</p>
 * <p>For DataGrid only the former is currently supported.</p>
 * <p>Can be used with both {@link Window.Lookup} and {@link Window.Editor} subclasses controllers.</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface WithReportAttachments {
    String buttonId() default "";
}
