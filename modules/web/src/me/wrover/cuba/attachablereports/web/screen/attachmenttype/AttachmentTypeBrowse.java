package me.wrover.cuba.attachablereports.web.screen.attachmenttype;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.chile.core.model.MetadataObject;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.EntityCombinedScreen;
import com.haulmont.cuba.gui.components.Label;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.sys.AttributeAccessSupport;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
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
    private ComponentsFactory componentsFactory;

    @Inject
    private AttributeAccessSupport attributeAccessSupport;

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
                                messages.getTools()::getEntityCaption,
                                MetadataObject::getName
                                )
                        )
        );
    }

    public Component entityCaptionGenerator(AttachmentType entity) {
        @SuppressWarnings("unchecked")
        Label<String> caption = (Label<String>) componentsFactory.createComponent(Label.class);

        final String metaClassName = entity.getTargetMetaClass();
        if (metaClassName != null) {
            final MetaClass metaClass = metadata.getClass(metaClassName);
            if (metaClass != null)
                caption.setValue(messages.getTools().getEntityCaption(metaClass));
            else
                caption.setValue(getMessage("unknownMetaClass"));
        } else {
            caption.setValue(getMessage("allTypes"));
        }

        return caption;
    }
}