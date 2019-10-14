package me.wrover.cuba.attachablereports.domain;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.EmbeddableEntity;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.entity.annotation.SystemLevel;
import com.haulmont.cuba.core.global.DeletePolicy;
import com.haulmont.cuba.core.global.validation.groups.UiCrossFieldChecks;
import com.haulmont.reports.entity.Report;
import de.diedavids.cuba.entitysoftreference.EntitySoftReferenceConverter;
import me.wrover.cuba.attachablereports.utils.ValueObjectConverter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

/**
 * An attachment.
 */
@javax.persistence.Entity(name = "mwattreps$Attachment")
@Table(name = "MWATTREPS_ATTACHMENT")
@NamePattern("#getCaption|type,name,report")
@AttachmentTypedConstraint(groups = {Default.class, UiCrossFieldChecks.class})
public class Attachment extends StandardEntity {
    private static final long serialVersionUID = -5502239640404810396L;

    /**
     * A field to store soft reference to an embracing entity (mandatory)
     */
    public static final String FIELD_ENTITY = "entity";

    /**
     * A field to store an optional embedded key value object.
     * This allows to bind an attachment not only to an entity but as well to a key value object.
     * Could be useful when dealing with temporary entities which have the same key identity but
     * different surrogate keys.
     */
    public static final String FIELD_KEY = "key";

    public static final String FIELD_TYPE = "type";
    public static final String FIELD_FILE = "file";
    public static final String FIELD_SOURCE = "source";
    public static final String FIELD_REPORT = "report";
    public static final String FIELD_TEMPLATE_CODE = "templateCode";
    public static final String FIELD_NAME = "name";

    @NotNull
    @Convert(converter = EntitySoftReferenceConverter.class)
    @MetaProperty(datatype = "EntitySoftReference", mandatory = true)
    @Column(name = "ATTACHABLE", nullable = false)
    @SystemLevel
    protected Entity entity;

// FIXME STUDIO 7 couldn't generate scripts for such an attribute
//    @Convert(converter = ValueObjectConverter.class)
//    @MetaProperty(datatype = "ValueObjectDataType")
//    @Column(name = "EMBEDDED_KEY")
//    @SystemLevel
//    protected EmbeddableEntity key;

    @NotNull
    @Column(name = "NAME", nullable = false)
    protected String name;

    @NotNull
    @OnDelete(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "FILE_ID")
    protected FileDescriptor file;

    @NotNull
    @Column(name = "SOURCE", nullable = false)
    protected String source = AttachmentSourceEnum.ATTACHED.getId();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPE_ID")
    protected AttachmentType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REPORT_ID")
    protected Report report;

    @Column(name = "TEMPLATE_CODE")
    protected String templateCode;

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

//    public EmbeddableEntity getKey() {
//        return key;
//    }
//
//    public void setKey(EmbeddableEntity key) {
//        this.key = key;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFile(FileDescriptor file) {
        this.file = file;
    }

    public FileDescriptor getFile() {
        return file;
    }

    public void setSource(AttachmentSourceEnum source) {
        this.source = source == null ? null : source.getId();
    }

    public AttachmentSourceEnum getSource() {
        return source == null ? null : AttachmentSourceEnum.fromId(source);
    }

    public AttachmentType getType() {
        return type;
    }

    public void setType(AttachmentType type) {
        this.type = type;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Report getReport() {
        return report;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getCaption() {
        if (getSource() != null) {
            switch (getSource()) {
                case ATTACHED:
                    return getType().getInstanceName() + " / " + getName();
                case GENERATED:
                    return getReport().getInstanceName() + " / " + getName();
                default:
                    return StringUtils.EMPTY;
            }
        } else {
            return StringUtils.EMPTY;
        }
    }
}