package me.wrover.cuba.attachablereports.web.impl.withattachments;

import com.haulmont.cuba.gui.components.*;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.browse.BrowseFieldAnnotationExecutor;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.editor.EditorFieldAnnotationExecutor;
import de.balvi.cuba.declarativecontrollers.web.helper.ButtonsPanelHelper;
import me.wrover.cuba.attachablereports.web.api.WithAttachments;
import me.wrover.cuba.attachablereports.web.impl.withattachments.actions.AttachmentsAction;
import org.apache.commons.compress.utils.Lists;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.util.Map;

@org.springframework.stereotype.Component("mwattreps_WithAttachmentsFieldExecutor")
public class WithAttachmentsFieldExecutor implements
        BrowseFieldAnnotationExecutor<WithAttachments, Component>,
        EditorFieldAnnotationExecutor<WithAttachments, Component> {

    @Inject
    private ButtonsPanelHelper buttonsPanelHelper;

    @Override
    public boolean supports(Annotation annotation) {
        return annotation instanceof WithAttachments;
    }

    @Override
    public void init(WithAttachments annotation, Window.Lookup browse, Component target, Map<String, Object> params) {
        _init(annotation, browse, target, params);
    }

    @Override
    public void init(WithAttachments annotation, Window.Editor editor, Component target, Map<String, Object> params) {
        _init(annotation, editor, target, params);
    }

    private void _init(WithAttachments annotation, Window editor, Component target, Map<String, Object> params) {

        if (!(target instanceof HasButtonsPanel
                && target instanceof ListComponent))
            throw new IllegalArgumentException("@WithAttachments target should implement" +
                    " ListComponent and HasButtonsPanel");

        HasButtonsPanel elementWithButtonsPanel = (HasButtonsPanel) target;
        ActionsHolder actionsHolder = (ActionsHolder) target;
        ListComponent listComponent = (ListComponent) target;

        AttachmentsAction action = new AttachmentsAction(listComponent, annotation);
        actionsHolder.addAction(action);

        Button attachmentsBtn = buttonsPanelHelper.createButton(annotation.buttonId(),
                elementWithButtonsPanel.getButtonsPanel(),
                Lists.newArrayList());
        attachmentsBtn.setAction(action);
    }

    @Override
    public void ready(WithAttachments annotation, Window.Lookup browse, Component target, Map<String, Object> params) {
    }

    @Override
    public void postInit(WithAttachments annotation, Window.Editor editor, Component target) {
    }
}