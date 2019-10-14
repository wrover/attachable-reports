package me.wrover.cuba.attachablereports.web.api;

import com.haulmont.cuba.core.entity.EmbeddableEntity;
import com.haulmont.cuba.gui.components.Window;
import me.wrover.cuba.attachablereports.domain.AttachmentSourceEnum;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation to add attachment functionality to a container.
 * Can be used only with {@link Window.Lookup} subclasses.
 * Adds an attachment table with controls.
 * See parameters comments for more details.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface AttachmentFrame {
    /**
     * @return attachment source
     */
    AttachmentSourceEnum source() default AttachmentSourceEnum.ATTACHED;

    /**
     * <p>A mandatory entity identity attribute name holding an embedded key value object of
     * {@link EmbeddableEntity} type.</p>
     *
     * <p>This identity is to be saved along with the entity surrogate id within an UPLOADED attachment
     * and is to be used when querying attachments if {@link #showByKey()} is set to <code>true</code>.</p>
     *
     * <p>For GENERATED attachments is not implemented and could be done in the future using an imaginary
     * {@link GeneratedAttachmentKey} annotation. Actually using special annotations on entities for that
     * end may be clearer by making this parameter purely query-related.</p>
     * <p>
     * TODO implement GeneratedAttachmentKey
     *
     * @return attribute name
     */
    String key() default StringUtils.EMPTY;

    /**
     * @return force using key identity instead or entity one when querying attachments. The mandatory {@link #key()} should be provided.
     */
    boolean showByKey() default false;
}
