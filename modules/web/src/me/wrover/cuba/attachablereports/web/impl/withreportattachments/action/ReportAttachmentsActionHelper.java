package me.wrover.cuba.attachablereports.web.impl.withreportattachments.action;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.components.Action;
import me.wrover.cuba.attachablereports.config.Configuration;
import me.wrover.cuba.attachablereports.domain.AttachmentSourceEnum;
import me.wrover.cuba.attachablereports.service.AttachableReportsService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component("mwattreps_ReportAttachmentsActionHelper")
public class ReportAttachmentsActionHelper {

    private static final String ACTION_MSG_KEY = "actions.ReportAttachments";
    private static final String ACTION_COUNT_MSG_KEY = "actions.ReportAttachments.count";

    @Inject
    private AttachableReportsService attachmentService;

    @Inject
    private Configuration attachableConfiguration;

    @Inject
    private Messages messages;

    void updateCaption(Action action, Entity entity) {
        if (entity != null && attachableConfiguration.getCountAttachmentInBrowser()) {
            int attachmentCount = attachmentService.countAttachmentsByEntity(entity, AttachmentSourceEnum.GENERATED);
            action.setCaption(messages.formatMainMessage(ACTION_COUNT_MSG_KEY, attachmentCount));
        } else {
            action.setCaption(messages.getMainMessage(ACTION_MSG_KEY));
        }
    }
}
