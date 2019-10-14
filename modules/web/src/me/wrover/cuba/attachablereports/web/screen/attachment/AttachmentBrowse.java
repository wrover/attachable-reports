package me.wrover.cuba.attachablereports.web.screen.attachment;

import com.haulmont.cuba.core.entity.EmbeddableEntity;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.WindowParam;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.VBoxLayout;
import me.wrover.cuba.attachablereports.domain.AttachmentSourceEnum;

import javax.inject.Inject;
import java.util.Map;
import java.util.Objects;

import static me.wrover.cuba.attachablereports.web.screen.attachment.util.Params.frames;

public class AttachmentBrowse extends AbstractLookup {

    @WindowParam
    protected Entity entity;

    @WindowParam
    protected AttachmentSourceEnum source;

    @WindowParam
    protected String key;

    @WindowParam
    protected boolean showByKey;

    @Inject
    protected VBoxLayout container;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        initCaption();
        initFrame(params);
    }

    private void initCaption() {
        final String msgKey = "browseCaption" + "." + source.name().toLowerCase();
        final String caption;

        if (showByKey)
            caption = formatMessage(msgKey, ((EmbeddableEntity) Objects.requireNonNull(entity.getValue(key))).getInstanceName());
        else
            caption = formatMessage(msgKey, entity.getInstanceName());

        setCaption(caption);
    }

    private void initFrame(Map<String, Object> params) {
        openFrame(container, frames(source), params);
    }

    @Override
    public void ready() {
        // FIXME expand tables using CUBA 7 nested component model
        //((GroupTable) getComponent("mwattreps$attachmentBrowseFrame.attachmentsTable")).expandAll();
        //((GroupTable) getComponent("mwattreps$reportBrowseFrame.attachmentsTable")).expandAll();
    }
}