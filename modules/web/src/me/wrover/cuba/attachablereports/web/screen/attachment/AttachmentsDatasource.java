package me.wrover.cuba.attachablereports.web.screen.attachment;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.gui.data.impl.CustomGroupDatasource;
import me.wrover.cuba.attachablereports.domain.Attachment;
import me.wrover.cuba.attachablereports.domain.AttachmentSourceEnum;
import me.wrover.cuba.attachablereports.service.AttachableReportsService;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class AttachmentsDatasource extends CustomGroupDatasource<Attachment, UUID> {

    static final String DS_PARAM_ENTITY = "entity";
    static final String DS_PARAM_KEY = "key";
    static final String DS_PARAM_SOURCE = "source";

    private AttachableReportsService attachmentService = AppBeans.get(AttachableReportsService.class);

    @Override
    protected Collection<Attachment> getEntities(Map<String, Object> params) {
        if (params.containsKey(DS_PARAM_KEY))
            // FIXME key-based attachments
            return Collections.emptyList();
//            return attachmentService.getAttachmentsByKey(
//                    (EmbeddableEntity) params.get(DS_PARAM_KEY),
//                    (AttachmentSourceEnum) params.get(DS_PARAM_SOURCE));
        else
            return attachmentService.getAttachmentsByEntity(
                    (Entity) params.get(DS_PARAM_ENTITY),
                    (AttachmentSourceEnum) params.get(DS_PARAM_SOURCE));
    }
}
