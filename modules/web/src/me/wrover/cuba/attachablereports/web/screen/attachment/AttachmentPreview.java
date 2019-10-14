package me.wrover.cuba.attachablereports.web.screen.attachment;

import com.haulmont.cuba.gui.AppConfig;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.export.ExportFormat;
import com.haulmont.cuba.gui.export.FileDataProvider;
import com.haulmont.cuba.web.WebConfig;
import me.wrover.cuba.attachablereports.domain.Attachment;

import javax.inject.Inject;


public class AttachmentPreview extends AbstractEditor<Attachment> {

    @SuppressWarnings("deprecation")
    @Inject
    com.haulmont.cuba.gui.components.Embedded attachmentPreviewViewer;

    @Inject
    WebConfig webConfig;

    @Override
    public void ready() {
        super.ready();

        setPreviewCaption();

        if (isFileExtensionSupported())
            previewFile();
        else
            downloadFile();

    }

    private void setPreviewCaption() {
        setCaption(formatMessage("attachmentPreview", getItem().getInstanceName()));
    }

    private void previewFile() {
        attachmentPreviewViewer.setSource(getItem().getFile().getName(), new FileDataProvider(getItem().getFile()));
    }

    private boolean isFileExtensionSupported() {
        return webConfig.getViewFileExtensions().contains(getItem().getFile().getExtension());
    }

    public void downloadFile() {
        AppConfig.createExportDisplay(this).show(getItem().getFile(), ExportFormat.OCTET_STREAM);
    }
}