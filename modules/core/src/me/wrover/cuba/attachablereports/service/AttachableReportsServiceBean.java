package me.wrover.cuba.attachablereports.service;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.app.FileStorageAPI;
import com.haulmont.cuba.core.entity.EmbeddableEntity;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.TimeSource;
import com.haulmont.reports.entity.Report;
import com.haulmont.reports.exception.ReportingException;
import de.diedavids.cuba.entitysoftreference.SoftReferenceService;
import me.wrover.cuba.attachablereports.domain.Attachment;
import me.wrover.cuba.attachablereports.domain.AttachmentSourceEnum;
import me.wrover.cuba.attachablereports.utils.AttachableReportOutputDocument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Collection;
import java.util.stream.Collectors;

@Service(AttachableReportsService.NAME)
public class AttachableReportsServiceBean implements AttachableReportsService {

    @Inject
    private SoftReferenceService softReferenceService;

    @Inject
    private Persistence persistence;

    @Inject
    private Metadata metadata;

    @Inject
    private TimeSource timeSource;

    @Inject
    private FileStorageAPI fileStorageAPI;

    @Override
    public int countAttachmentsByEntity(Entity entity, AttachmentSourceEnum source) {
        return getAttachmentsByEntity(entity, source).size(); // TODO not really effective
    }

    @Override
    @Transactional(readOnly = true)
    public int countAttachmentsByKey(EmbeddableEntity key, AttachmentSourceEnum source) {
        return getAttachmentsByKey(key, source).size(); // TODO not really effective
    }

    @Override
    public Collection<Attachment> getAttachmentsByEntity(Entity entity, AttachmentSourceEnum source) {
        return softReferenceService
                .loadEntitiesForSoftReference(
                        Attachment.class,
                        entity,
                        Attachment.FIELD_ENTITY,
                        "attachment-view"
                )
                .stream()
                .filter(attachment -> attachment.getSource().equals(source))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Attachment> getAttachmentsByKey(EmbeddableEntity key, AttachmentSourceEnum source) {
        throw new UnsupportedOperationException("not ported to CUBA 7");
//        return persistence.getEntityManager()
//                .createQuery("select e from mwattreps$Attachment e where e.key = :key and e.source = :source", Attachment.class)
//                .setParameter("key", key)
//                .setParameter("source", source)
//                .setViewName("attachment-view")
//                .getResultList();
    }

    @Override
    public Attachment saveReport(AttachableReportOutputDocument document, Entity entity, Report report) {

        try (Transaction tx = persistence.createTransaction()) {

            EntityManager em = persistence.getEntityManager();

            FileDescriptor file = metadata.create(FileDescriptor.class);
            file.setCreateDate(timeSource.currentTimestamp());
            file.setName(document.getDocumentName());
            file.setExtension(document.getReportOutputType().getId());
            file.setSize((long) document.getContent().length);

            try {
                fileStorageAPI.saveFile(file, document.getContent());
            } catch (FileStorageException e) {
                throw new ReportingException("An error occurred while saving the report to the file storage", e);
            }

            Attachment attachment = metadata.create(Attachment.class);
            attachment.setEntity(entity);
            attachment.setFile(file);
            attachment.setName(file.getName());
            attachment.setSource(AttachmentSourceEnum.GENERATED);
            attachment.setReport(report);
            attachment.setTemplateCode(document.getTemplateCode());

            em.persist(file);
            em.persist(attachment);
            tx.commit();

            return attachment;
        }
    }
}