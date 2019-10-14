package me.wrover.cuba.attachablereports.web.screen.attachment;

import com.haulmont.cuba.client.AttributeAccessUpdater;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.ComponentsHelper;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.WindowParam;
import com.haulmont.cuba.gui.components.AbstractFrame;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.components.actions.RemoveAction;
import com.haulmont.cuba.gui.data.GroupDatasource;
import me.wrover.cuba.attachablereports.domain.Attachment;
import me.wrover.cuba.attachablereports.domain.AttachmentSourceEnum;
import me.wrover.cuba.attachablereports.web.screen.attachment.action.AttachmentDownloadAction;
import me.wrover.cuba.attachablereports.web.screen.attachment.action.AttachmentPreviewAction;
import me.wrover.cuba.attachablereports.web.screen.attachment.action.GenerateReportAction;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static me.wrover.cuba.attachablereports.domain.Attachment.FIELD_ENTITY;
import static me.wrover.cuba.attachablereports.web.screen.attachment.AttachmentsDatasource.*;

/**
 * The universal controller for both frames.
 */
public class AttachmentUniFrame extends AbstractFrame {

    @Inject
    private GroupDatasource<Attachment, UUID> attachmentsDs;

    @Inject
    private GroupTable<Attachment> attachmentsTable;

    @Named("attachmentsTable.create")
    private CreateAction createAction;

    @Named("attachmentsTable.remove")
    private RemoveAction attachmentsTableRemove;

    @WindowParam
    protected Entity entity;

    @WindowParam
    protected AttachmentSourceEnum source;

    @WindowParam
    protected String key;

    @WindowParam
    protected boolean showByKey;

    @Inject
    AttributeAccessUpdater attributeAccessUpdater;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        refreshAttachmentDs();

        initCreateAction();
        initGenerateAction();
        initDownloadAction();

        attachmentsTableRemove.setBeforeActionPerformedHandler(this::loadSecurityToken);
    }

    private boolean loadSecurityToken() {
        Attachment selected = attachmentsTable.getSingleSelected();
        if (selected != null) {
            attributeAccessUpdater.updateAttributeAccess(selected);
            return true;
        } else {
            return false;
        }
    }

    private void refreshAttachmentDs() {
        HashMap<String, Object> params = new HashMap<>();
        params.put(DS_PARAM_ENTITY, entity);
        params.put(DS_PARAM_SOURCE, source);

        if (showByKey)
            params.put(DS_PARAM_KEY, entity.getValue(key));

        attachmentsDs.refresh(params);
    }

    private void initCreateAction() {
        Map<String, Object> params = new HashMap<>();
        params.put(FIELD_ENTITY, entity);

        // FIXME key-based attachments
        //        if (!key.isEmpty())
        //            params.put(FIELD_KEY, entity.getValue(key));

        createAction.setInitialValues(params);
    }

    private void initGenerateAction() {
        attachmentsTable.addAction(new GenerateReportAction(ComponentsHelper.getWindow(this), entity, null));
    }

    private void initDownloadAction() {
        attachmentsTable.addAction(new AttachmentDownloadAction());
        attachmentsTable.addAction(new AttachmentPreviewAction());
    }

    public void previewFile(Entity item, String columnId) {
        Attachment attachment = (Attachment) item;
        frame.openEditor("mwattreps$Attachment.preview", attachment, WindowManager.OpenType.DIALOG);
    }
}