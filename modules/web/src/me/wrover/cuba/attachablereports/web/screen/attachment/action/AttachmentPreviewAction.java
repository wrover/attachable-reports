package me.wrover.cuba.attachablereports.web.screen.attachment.action;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.actions.ItemTrackingAction;
import com.haulmont.cuba.gui.icons.CubaIcon;
import me.wrover.cuba.attachablereports.domain.Attachment;

public class AttachmentPreviewAction extends ItemTrackingAction {

    private static final String ACTION_ID = "preview";

    public AttachmentPreviewAction() {
        super(ACTION_ID);
        this.caption = AppBeans.get(Messages.class).getMessage(getClass(), "actions.Preview");
        this.icon = CubaIcon.SEARCH.source();
    }

    @Override
    public void actionPerform(Component component) {
        Attachment attachment = (Attachment) target.getSingleSelected();
        target.getFrame().openEditor("mwattreps$Attachment.preview", attachment, WindowManager.OpenType.DIALOG);
    }
}