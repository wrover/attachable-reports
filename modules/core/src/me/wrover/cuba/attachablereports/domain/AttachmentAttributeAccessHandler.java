package me.wrover.cuba.attachablereports.domain;

import com.haulmont.cuba.core.app.SetupAttributeAccessHandler;
import com.haulmont.cuba.core.app.events.SetupAttributeAccessEvent;
import org.springframework.stereotype.Component;

import static me.wrover.cuba.attachablereports.domain.Attachment.*;

@Component("mwattreps_AttachmentAttributeAccessHandler")
public class AttachmentAttributeAccessHandler implements SetupAttributeAccessHandler<Attachment> {

    @Override
    public void setupAccess(SetupAttributeAccessEvent<Attachment> event) {
        event.addReadOnly(FIELD_ENTITY);
        event.addReadOnly(FIELD_KEY);
        event.addReadOnly(FIELD_TYPE);
        event.addReadOnly(FIELD_FILE);
        event.addReadOnly(FIELD_SOURCE);
        event.addReadOnly(FIELD_REPORT);
        event.addReadOnly(FIELD_TEMPLATE_CODE);
        event.addReadOnly(FIELD_NAME);
    }

    @Override
    public boolean supports(Class clazz) {
        return Attachment.class.isAssignableFrom(clazz);
    }
}
