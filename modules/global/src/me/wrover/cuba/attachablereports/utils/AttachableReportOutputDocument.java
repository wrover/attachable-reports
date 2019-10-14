package me.wrover.cuba.attachablereports.utils;

import com.haulmont.yarg.reporting.ReportOutputDocument;
import com.haulmont.yarg.reporting.ReportOutputDocumentImpl;
import me.wrover.cuba.attachablereports.domain.Attachment;

/**
 * Wraps standard {@link ReportOutputDocument} object by adding the attachment
 * and the template code which has been used during report generation.
 */
public class AttachableReportOutputDocument extends ReportOutputDocumentImpl {

    private Attachment attachment;
    private final String templateCode;

    public AttachableReportOutputDocument(ReportOutputDocument o, String templateCode) {
        super(o.getReport(), o.getContent(), o.getDocumentName(), o.getReportOutputType());

        this.templateCode = templateCode;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }
}