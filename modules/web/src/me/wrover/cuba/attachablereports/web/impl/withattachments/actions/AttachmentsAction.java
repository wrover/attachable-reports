package me.wrover.cuba.attachablereports.web.impl.withattachments.actions;

import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.ListComponent;
import com.haulmont.cuba.gui.components.Window;
import com.haulmont.cuba.gui.components.actions.ItemTrackingAction;
import com.haulmont.cuba.gui.icons.CubaIcon;
import me.wrover.cuba.attachablereports.domain.AttachmentSourceEnum;
import me.wrover.cuba.attachablereports.web.api.WithAttachments;

import static me.wrover.cuba.attachablereports.web.screen.attachment.util.Params.*;


public class AttachmentsAction extends ItemTrackingAction implements Action.HasOpenType {

    private static final String DEFAULT_ACTION_ID = "attachment";
    private static final String ATTACHMENTS_ICON = CubaIcon.PAPERCLIP.source();

    private AttachmentsActionHelper helper = AppBeans.get(AttachmentsActionHelper.class);

    private WindowManager.OpenType openType;

    private WithAttachments annotation;

    public AttachmentsAction(ListComponent target, WithAttachments annotation) {
        this(DEFAULT_ACTION_ID, target, annotation);
    }

    private AttachmentsAction(String id, ListComponent target, WithAttachments annotation) {
        super(target, id);
        this.annotation = annotation;
        updateCaption();
        setIcon();
    }

    private void updateCaption() {
        helper.updateCaption(this, target.getSingleSelected(), annotation);
    }

    private void setIcon() {
        setIcon(AttachmentsAction.ATTACHMENTS_ICON);
    }

    @Override
    public void refreshState() {
        super.refreshState();
        updateCaption();
    }

    @Override
    public void actionPerform(Component component) {

        Window browse = target.getFrame().openWindow(
                "mwattreps$Attachment.browse",
                (openType != null ? openType : WindowManager.OpenType.DIALOG),
                ParamsMap.of(
                        PARAM_ENTITY, target.getSingleSelected(),
                        PARAM_SOURCE, AttachmentSourceEnum.ATTACHED,
                        PARAM_KEY, annotation.key(),
                        PARAM_SHOW_BY_KEY, annotation.showByKey()
                ));

        browse.addCloseListener(actionId -> updateCaption());
    }

    @Override
    public WindowManager.OpenType getOpenType() {
        return openType;
    }

    @Override
    public void setOpenType(WindowManager.OpenType openType) {
        this.openType = openType;
    }
}