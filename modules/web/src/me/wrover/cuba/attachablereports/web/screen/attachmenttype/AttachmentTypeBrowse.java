package me.wrover.cuba.attachablereports.web.screen.attachmenttype;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetadataObject;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.EntityCombinedScreen;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.screen.MessageBundle;
import com.haulmont.cuba.gui.sys.AttributeAccessSupport;
import me.wrover.cuba.attachablereports.domain.AttachmentType;

import javax.inject.Inject;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class AttachmentTypeBrowse extends EntityCombinedScreen {

    @Inject
    private Metadata metadata;

    @Inject
    private LookupField<String> lookupField;

    @Inject
    private Datasource<AttachmentType> attachmentTypeDs;

    @Inject
    private UiComponents uiComponents;

    @Inject
    private AttributeAccessSupport attributeAccessSupport;

    @Inject
    private MessageBundle messageBundle;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        attachmentTypeDs.addItemChangeListener(this::onItemChange);
    }

    private void onItemChange(Datasource.ItemChangeEvent<AttachmentType> event) {
        if (event.getItem() != null)
            attributeAccessSupport.applyAttributeAccess(getFrame().getFrameOwner(), true, event.getItem());
    }

    @Override
    protected void initBrowseEditAction() {
        super.initBrowseEditAction();

        lookupField.setOptionsMap(
                metadata.getTools().getAllPersistentMetaClasses()
                        .stream()
                        .collect(toMap(
                                MetadataObject::getName,
                                MetadataObject::getName
                                )
                        )
        );
    }

    public Component entityCaptionGenerator(AttachmentType entity) {
        final Label<String> label = uiComponents.create(Label.TYPE_DEFAULT);

        final String metaClassName = entity.getTargetMetaClass();
        if (metaClassName != null) {
            final MetaClass metaClass = metadata.getClass(metaClassName);
            if (metaClass != null)
                label.setValue(messages.getTools().getEntityCaption(metaClass));
            else
                label.setValue(messageBundle.getMessage("unknownMetaClass"));
        } else {
            label.setValue(messageBundle.getMessage("allTypes"));
        }


        return label;
    }
}