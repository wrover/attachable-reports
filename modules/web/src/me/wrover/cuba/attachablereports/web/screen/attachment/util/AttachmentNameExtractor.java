package me.wrover.cuba.attachablereports.web.screen.attachment.util;

import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.data.Datasource;
import me.wrover.cuba.attachablereports.domain.Attachment;

import java.util.Objects;

public class AttachmentNameExtractor implements Datasource.ItemPropertyChangeListener<Attachment> {

    private TextField destination;

    public AttachmentNameExtractor(TextField destination) {
        this.destination = destination;
    }

    @Override
    public void itemPropertyChanged(Datasource.ItemPropertyChangeEvent<Attachment> e) {
        if (fileChanged(e)) {
            destination.setValue(getNameFromFile(e));
        }
    }

    private String getNameFromFile(Datasource.ItemPropertyChangeEvent<Attachment> e) {
        return ((FileDescriptor) Objects.requireNonNull(e.getValue())).getName();
    }

    private boolean fileChanged(Datasource.ItemPropertyChangeEvent<Attachment> e) {
        return (e.getProperty().equals("file") && e.getValue() != null);
    }
}
