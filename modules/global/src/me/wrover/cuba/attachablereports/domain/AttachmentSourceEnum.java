package me.wrover.cuba.attachablereports.domain;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;

public enum AttachmentSourceEnum implements EnumClass<String> {

    ATTACHED("ATTACHED"),   // external file attached by users
    GENERATED("GENERATED"); // generated by CUBA reports

    private String id;

    AttachmentSourceEnum(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static AttachmentSourceEnum fromId(String id) {
        for (AttachmentSourceEnum at : AttachmentSourceEnum.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}