package me.wrover.cuba.attachablereports.web.impl.withreportattachments;

import com.haulmont.cuba.gui.components.*;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.browse.BrowseFieldAnnotationExecutor;
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.editor.EditorFieldAnnotationExecutor;
import de.balvi.cuba.declarativecontrollers.web.helper.ButtonsPanelHelper;
import me.wrover.cuba.attachablereports.web.api.WithReportAttachments;
import me.wrover.cuba.attachablereports.web.impl.withreportattachments.action.GenerateReportAction;
import me.wrover.cuba.attachablereports.web.impl.withreportattachments.action.ReportAttachmentsAction;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Map;

@org.springframework.stereotype.Component("mwattreps_WithReportAttachmentsFieldExecutor")
public class WithReportAttachmentsFieldExecutor implements
        BrowseFieldAnnotationExecutor<WithReportAttachments, Component>,
        EditorFieldAnnotationExecutor<WithReportAttachments, Component> {

    @Inject
    private ButtonsPanelHelper buttonsPanelHelper;

    @Override
    public boolean supports(Annotation annotation) {
        return annotation instanceof WithReportAttachments;
    }

    @Override
    public void init(WithReportAttachments annotation, Window.Lookup browse, Component target, Map<String, Object> params) {
        _init(annotation, browse, target, params);
    }

    @Override
    public void init(WithReportAttachments annotation, Window.Editor editor, Component target, Map<String, Object> params) {
        _init(annotation, editor, target, params);
    }

    private void _init(WithReportAttachments annotation, Window window, Component target, Map<String, Object> params) {

        if (!(target instanceof HasButtonsPanel
                && target instanceof ListComponent))
            throw new IllegalArgumentException("@WithAttachments target should implement " +
                    "ListComponent or HasButtonsPanel");

        HasButtonsPanel elementWithButtonsPanel = (HasButtonsPanel) target;
        ActionsHolder actionsHolder = (ActionsHolder) target;
        ListComponent listComponent = (ListComponent) target;

        ReportAttachmentsAction action = new ReportAttachmentsAction(listComponent);
        actionsHolder.addAction(action);

        Button attachmentsBtn = buttonsPanelHelper.createButton(annotation.buttonId(), elementWithButtonsPanel.getButtonsPanel(), new ArrayList<>());
        attachmentsBtn.setAction(action);

        // Generate Button is supported only for the Table element
        if (target instanceof Table) {
            GenerateReportAction generateAction = new GenerateReportAction(listComponent);
            actionsHolder.addAction(generateAction);

            Button generateReportBtn = buttonsPanelHelper.createButton(null, elementWithButtonsPanel.getButtonsPanel(), new ArrayList<>());
            generateReportBtn.setAction(generateAction);
        }
    }

    @Override
    public void ready(WithReportAttachments annotation, Window.Lookup browse, Component target, Map<String, Object> params) {
    }

    @Override
    public void postInit(WithReportAttachments annotation, Window.Editor editor, Component target) {
    }
}