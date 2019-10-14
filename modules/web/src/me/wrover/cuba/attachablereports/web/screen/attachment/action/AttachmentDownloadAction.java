package me.wrover.cuba.attachablereports.web.screen.attachment.action;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.AppConfig;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.actions.ItemTrackingAction;
import com.haulmont.cuba.gui.icons.CubaIcon;
import me.wrover.cuba.attachablereports.domain.Attachment;

public class AttachmentDownloadAction extends ItemTrackingAction {

    private static final String ACTION_ID = "download";

    public AttachmentDownloadAction() {
        super(ACTION_ID);
        this.caption = AppBeans.get(Messages.class).getMessage(getClass(), "actions.Download");
        this.icon = CubaIcon.DOWNLOAD.source();
    }

    @Override
    public void actionPerform(Component component) {
        Attachment attachment = (Attachment) target.getSingleSelected();
        if (attachment != null && attachment.getFile() != null) {
            AppConfig.createExportDisplay(target.getFrame()).show(attachment.getFile());
        }
    }
}
