package me.wrover.cuba.attachablereports.web.impl.attachmentframe;

import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.gui.components.ComponentContainer;
import com.haulmont.cuba.gui.components.Window;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.editor.EditorFieldAnnotationExecutor;
import me.wrover.cuba.attachablereports.web.api.AttachmentFrame;

import java.lang.annotation.Annotation;
import java.util.Map;

import static me.wrover.cuba.attachablereports.web.screen.attachment.util.Params.*;

/**
 * <code>@</code>{@link AttachmentFrame} annotation processor.
 */
@org.springframework.stereotype.Component("mwattreps_AttachmentsFrameEditorFieldExecutor")
public class AttachmentFrameEditorFieldExecutor implements
        EditorFieldAnnotationExecutor<AttachmentFrame, ComponentContainer> {

    @Override
    public boolean supports(Annotation annotation) {
        return annotation instanceof AttachmentFrame;
    }

    @Override
    public void init(AttachmentFrame annotation,
                     Window.Editor editor,
                     ComponentContainer target,
                     Map<String, Object> params) {
    }

    @Override
    public void postInit(AttachmentFrame annotation,
                         Window.Editor editor,
                         ComponentContainer target) {

        editor.openFrame(target, frames(annotation.source()),
                ParamsMap.of(
                        PARAM_ENTITY, editor.getItem(),
                        PARAM_SOURCE, annotation.source(),
                        PARAM_KEY, annotation.key(),
                        PARAM_SHOW_BY_KEY, annotation.showByKey()
                )
        );
    }
}
