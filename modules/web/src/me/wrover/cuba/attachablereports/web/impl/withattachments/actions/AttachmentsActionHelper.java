package me.wrover.cuba.attachablereports.web.impl.withattachments.actions;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.components.Action;
import me.wrover.cuba.attachablereports.config.Configuration;
import me.wrover.cuba.attachablereports.domain.AttachmentSourceEnum;
import me.wrover.cuba.attachablereports.service.AttachableReportsService;
import me.wrover.cuba.attachablereports.web.api.WithAttachments;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component("mwattreps_AttachmentsActionHelper")
public class AttachmentsActionHelper {

    private static final String ACTION_MSG_KEY = "actions.Attachments";
    private static final String ACTION_COUNT_MSG_KEY = "actions.Attachments.count";

    @Inject
    private AttachableReportsService attachmentService;

    @Inject
    private Configuration attachableConfiguration;

    @Inject
    private Messages messages;

    void updateCaption(Action action, Entity entity, WithAttachments annotation) {

        final String caption;

        if (entity != null && attachableConfiguration.getCountAttachmentInBrowser()) {

            final int attachmentCount;

            if (annotation.showByKey())
                attachmentCount = 0;
//                attachmentCount = attachmentService.countAttachmentsByKey(entity.getValue(annotation.key()),
//                        AttachmentSourceEnum.ATTACHED);
            else
                attachmentCount = attachmentService.countAttachmentsByEntity(entity, AttachmentSourceEnum.ATTACHED);

            caption = messages.formatMainMessage(ACTION_COUNT_MSG_KEY, attachmentCount);
        } else {
            caption = messages.getMainMessage(ACTION_MSG_KEY);
        }

        action.setCaption(caption);
    }
}
