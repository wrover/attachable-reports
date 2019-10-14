package me.wrover.cuba.attachablereports.domain;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NamePattern("%s|name")
@Table(name = "MWATTREPS_ATTACHMENT_TYPE")
@Entity(name = "mwattreps$AttachmentType")
public class AttachmentType extends StandardEntity {
    private static final long serialVersionUID = 557643724049018296L;

    public static final String FIELD_TARGET_META_CLASS = "targetMetaClass";

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @Column(name = "TARGET_META_CLASS")
    protected String targetMetaClass;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTargetMetaClass(String targetMetaClass) {
        this.targetMetaClass = targetMetaClass;
    }

    public String getTargetMetaClass() {
        return targetMetaClass;
    }

}