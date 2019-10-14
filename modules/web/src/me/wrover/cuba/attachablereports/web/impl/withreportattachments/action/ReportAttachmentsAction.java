package me.wrover.cuba.attachablereports.web.impl.withreportattachments.action;

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
import org.apache.commons.lang3.StringUtils;

import static me.wrover.cuba.attachablereports.web.screen.attachment.util.Params.*;

public class ReportAttachmentsAction extends ItemTrackingAction implements Action.HasOpenType {

    private static final String ACTION_ID = "reportAttachment";
    private static final String ATTACHMENTS_ICON = CubaIcon.PAPERCLIP.source();

    private ReportAttachmentsActionHelper helper = AppBeans.get(ReportAttachmentsActionHelper.class);

    private WindowManager.OpenType openType;

    public ReportAttachmentsAction(ListComponent target) {
        super(target, ACTION_ID);
        setIcon(ATTACHMENTS_ICON);
        updateCaption();
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
                        PARAM_SOURCE, AttachmentSourceEnum.GENERATED,
                        PARAM_SHOW_BY_KEY, false,
                        PARAM_KEY, StringUtils.EMPTY
                ));

        browse.addCloseListener(actionId -> updateCaption());
    }

    private void updateCaption() {
        helper.updateCaption(this, target.getSingleSelected());
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