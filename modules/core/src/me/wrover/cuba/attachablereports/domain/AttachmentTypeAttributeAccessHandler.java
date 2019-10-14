package me.wrover.cuba.attachablereports.domain;

import com.haulmont.cuba.core.app.SetupAttributeAccessHandler;
import com.haulmont.cuba.core.app.events.SetupAttributeAccessEvent;
import com.haulmont.cuba.core.global.PersistenceHelper;
import org.springframework.stereotype.Component;

@Component("mwattreps_AttachmentTypeAttributeAccessHandler")
public class AttachmentTypeAttributeAccessHandler implements SetupAttributeAccessHandler<AttachmentType> {

    @Override
    public void setupAccess(SetupAttributeAccessEvent<AttachmentType> event) {
        if (!PersistenceHelper.isNew(event.getEntity()))
            event.addReadOnly(AttachmentType.FIELD_TARGET_META_CLASS);
    }

    @Override
    public boolean supports(Class clazz) {
        return AttachmentType.class.isAssignableFrom(clazz);
    }
}
