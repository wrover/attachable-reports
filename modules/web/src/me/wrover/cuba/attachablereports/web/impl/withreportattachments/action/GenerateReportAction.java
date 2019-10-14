package me.wrover.cuba.attachablereports.web.impl.withreportattachments.action;

import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.ComponentsHelper;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.ListComponent;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.components.Window;
import com.haulmont.reports.gui.actions.TablePrintFormAction;

public class GenerateReportAction extends TablePrintFormAction {

    private static final String DEFAULT_ACTION_ID = "generateReport";

    public GenerateReportAction(ListComponent listComponent) {
        super(DEFAULT_ACTION_ID, (Table) listComponent);
        setCaption(AppBeans.get(Messages.class).getMessage(TablePrintFormAction.class, "actions.GenerateReport"));
    }

    @Override
    public void refreshState() {
        // enable for single selection only
        // also see comment in #actionPerform
        this.setEnabled(listComponent.getSelected().size() == 1);
    }

    @Override
    public void actionPerform(Component component) {
        // Since so far we use custom ReportGuiManager to save generated reports
        // it's not possible to save bulk reports
        // someone have to modify ReportingBean to make this possible
        if (listComponent.getSelected().size() > 1) {
            Window window = ComponentsHelper.getWindow(listComponent);
            if (window != null)
                window.showNotification("Bulk reporting is not supported.");
            return;
        }
        super.actionPerform(component);
    }
}
