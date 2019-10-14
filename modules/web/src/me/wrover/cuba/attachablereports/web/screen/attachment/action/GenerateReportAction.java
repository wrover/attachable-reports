package me.wrover.cuba.attachablereports.web.screen.attachment.action;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.Frame;
import com.haulmont.cuba.gui.components.Window;
import com.haulmont.cuba.gui.icons.CubaIcon;
import com.haulmont.reports.gui.ReportGuiManager;
import com.haulmont.reports.gui.actions.AbstractPrintFormAction;

import javax.annotation.Nullable;

/**
 * Action to run report generator in attached reports table.
 */
public class GenerateReportAction extends AbstractPrintFormAction {

    private final Window window;
    private final Entity entity;
    private final String reportOutputName;

    public GenerateReportAction(Window window, Entity entity, @Nullable String reportOutputName) {
        this("editorReport", window, entity, reportOutputName);
    }

    public GenerateReportAction(String id, Window window, Entity entity, @Nullable String reportOutputName) {
        super(id);

        this.window = window;
        this.entity = entity;
        this.caption = AppBeans.get(Messages.class).getMessage(getClass(), "actions.Report");
        this.reportOutputName = reportOutputName;
        this.icon = CubaIcon.PRINT.source();
    }

    @Override
    public void actionPerform(Component component) {
        if (beforeActionPerformedHandler != null) {
            if (!beforeActionPerformedHandler.beforeActionPerformed())
                return;
        }
        if (entity != null) {
            MetaClass metaClass = entity.getMetaClass();
            // TODO check fix
            openRunReportScreen(window.getFrameOwner(), entity, metaClass, reportOutputName);
        } else {
            window.showNotification(AppBeans.get(Messages.class).getMessage(ReportGuiManager.class, "notifications.noSelectedEntity"),
                    Frame.NotificationType.HUMANIZED);
        }
    }
}