package me.wrover.cuba.attachablereports.web.screen.attachment;

import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;
import me.wrover.cuba.attachablereports.domain.Attachment;
import me.wrover.cuba.attachablereports.domain.AttachmentType;
import me.wrover.cuba.attachablereports.web.screen.attachment.util.AttachmentNameExtractor;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.UUID;

public class AttachmentUpload extends AbstractEditor<Attachment> {

    @Named("fieldGroup.name")
    private
    TextField<String> nameTextField;

    @Inject
    Datasource<Attachment> attachmentDs;

    @Inject
    private CollectionDatasource<AttachmentType, UUID> attachmentTypesDs;

    @Override
    public void init(Map<String, Object> params) {
        initNameExtractor();
    }

    @Override
    protected void postInit() {
        super.postInit();
        initTypes();
    }

    private void initTypes() {
        attachmentTypesDs.refresh(ParamsMap.of("metaClassName", getItem().getEntity().getMetaClass().getName()));
    }

    private void initNameExtractor() {
        attachmentDs.addItemPropertyChangeListener(
                new AttachmentNameExtractor(nameTextField)
        );
    }
}