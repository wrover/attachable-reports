package me.wrover.cuba.attachablereports.config;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.Default;

@Source(type = SourceType.DATABASE)
public interface Configuration extends Config {

    @Property("mwattreps.countAttachmentInBrowser")
    @Default("true")
    Boolean getCountAttachmentInBrowser();
}