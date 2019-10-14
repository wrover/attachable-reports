package me.wrover.cuba.attachablereports.web.bean;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.WindowManagerProvider;
import com.haulmont.cuba.gui.config.WindowConfig;
import com.haulmont.cuba.gui.config.WindowInfo;
import com.haulmont.cuba.gui.screen.FrameOwner;
import com.haulmont.reports.entity.Report;
import com.haulmont.reports.entity.ReportOutputType;
import com.haulmont.reports.gui.ReportGuiManager;
import com.haulmont.yarg.reporting.ReportOutputDocument;
import me.wrover.cuba.attachablereports.domain.Attachment;
import me.wrover.cuba.attachablereports.service.AttachableReportsService;
import me.wrover.cuba.attachablereports.utils.AttachableReportOutputDocument;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Map;

/**
 * Custom bean to modify generating behaviour -- we have to save the report as a generated attachment.
 * Should be registered as a Spring bean named "cuba_ReportGuiManager".
 *
 * @implNote The convention: the report has to have a parameter called `entity`. This is the default for
 * CUBA Reporting for entity-related reports.
 */
public class CustomReportGuiManager extends ReportGuiManager {

    private static final String REPORT_PARAM_ENTITY = "entity";

    @Inject
    private AttachableReportsService attachmentService;

    @Inject
    private WindowManagerProvider windowManagerProvider;

    @Inject
    protected WindowConfig windowConfig;

    @Override
    public ReportOutputDocument getReportResult(Report report,
                                                Map<String, Object> params,
                                                @Nullable String templateCode,
                                                @Nullable ReportOutputType outputType) {

        ReportOutputDocument document = super.getReportResult(report, params, templateCode, outputType);

        // wrap report result
        AttachableReportOutputDocument adapter = new AttachableReportOutputDocument(document, templateCode);

        Attachment attachment = attachmentService.saveReport(
                adapter,
                (Entity) params.get(REPORT_PARAM_ENTITY),
                report
        );

        adapter.setAttachment(attachment);

        return adapter;
    }

    @Override
    protected void showReportResult(ReportOutputDocument document,
                                    Map<String, Object> params,
                                    @Nullable String templateCode,
                                    @Nullable String outputFileName,
                                    @Nullable ReportOutputType outputType,
                                    @Nullable FrameOwner screen) {
        if (document instanceof AttachableReportOutputDocument && screen != null) {
            AttachableReportOutputDocument ref = (AttachableReportOutputDocument) document;
            WindowInfo info = windowConfig.findWindowInfo("mwattreps$Attachment.preview");
            windowManagerProvider
                    .get()
                    .openEditor(info
                            , ref.getAttachment()
                            , WindowManager.OpenType.DIALOG);
        } else {
            super.showReportResult(document, params, templateCode, outputFileName, outputType, screen);
        }
    }
}
