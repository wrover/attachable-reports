package me.wrover.cuba.attachablereports.service;

import com.haulmont.cuba.core.entity.EmbeddableEntity;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.reports.entity.Report;
import me.wrover.cuba.attachablereports.domain.Attachment;
import me.wrover.cuba.attachablereports.domain.AttachmentSourceEnum;
import me.wrover.cuba.attachablereports.utils.AttachableReportOutputDocument;

import java.util.Collection;

public interface AttachableReportsService {
    String NAME = "mwattreps_AttachableReportsService";

    /**
     * Get number of attachments for the entity.
     *
     * @param entity target entity
     * @param source source type
     * @return
     */
    int countAttachmentsByEntity(Entity entity, AttachmentSourceEnum source);

//    /**
//     * Get number of attachments for an entity's key.
//     *
//     * @param entity target entity
//     * @param source source type
//     * @return
//     */
    int countAttachmentsByKey(EmbeddableEntity entity, AttachmentSourceEnum source);

    /**
     * Get attachments for an entity.
     *
     * @param entity target entity
     * @param source source type
     * @return
     */
    Collection<Attachment> getAttachmentsByEntity(Entity entity, AttachmentSourceEnum source);

//    /**
//     * Get attachments for a particular key value object.
//     *
//     * @param key key value object
//     * @param source source type
//     * @return
//     */
    Collection<Attachment> getAttachmentsByKey(EmbeddableEntity key, AttachmentSourceEnum source);

    /**
     * Save a generated report and link it to an entity.
     *
     * @param document a document and its metadata to be bound with via soft link mechanism
     * @param entity   a target entity
     * @param report   a report to be referenced from Attachment
     * @return a newly created persisted attachment
     */
    Attachment saveReport(AttachableReportOutputDocument document, Entity entity, Report report);
}