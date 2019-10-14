package me.wrover.cuba.attachablereports.web.screen.attachment.util;

import me.wrover.cuba.attachablereports.domain.AttachmentSourceEnum;

import java.util.EnumMap;
import java.util.Map;

public class Params {

    public static final String PARAM_ENTITY = "entity";
    public static final String PARAM_SOURCE = "source";
    public static final String PARAM_KEY = "key";
    public static final String PARAM_SHOW_BY_KEY = "showByKey";

    private static final Map<AttachmentSourceEnum, String> frames =
            new EnumMap<AttachmentSourceEnum, String>(AttachmentSourceEnum.class) {{
                put(AttachmentSourceEnum.ATTACHED, "mwattreps$attachmentBrowseFrame");
                put(AttachmentSourceEnum.GENERATED, "mwattreps$reportBrowseFrame");
            }};

    public static String frames(AttachmentSourceEnum source) {
        String frame = frames.get(source);

        if (frame == null)
            throw new IllegalStateException("no frame defined for source " + source.getId());

        return frame;
    }

    private Params() {
    }
}
