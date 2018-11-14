package com.ytjr.api.config;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.beans.PropertyEditorSupport;

public class StringEscapeEditor extends PropertyEditorSupport {

    private boolean escapeHTML;
    private boolean escapeJavaScript;
    private boolean escapeSQL;

    public StringEscapeEditor() {
        super();
    }

    StringEscapeEditor(boolean escapeHTML, boolean escapeJavaScript, boolean escapeSQL) {
        super();
        this.escapeHTML = escapeHTML;
        this.escapeJavaScript = escapeJavaScript;
        this.escapeSQL = escapeSQL;
    }

    @Override
    public void setAsText(String text) {
        if (text == null) {
            setValue(null);
        } else {
            String value = text;
            if (escapeHTML) {
                value = StringEscapeUtils.escapeHtml4(value);
            }
            if (escapeJavaScript) {
                value = this.escapeScript(value);
            }
            if (escapeSQL) {
                value = this.escapeSql(value);
            }
            setValue(value);
        }
    }

    @Override
    public String getAsText() {
        Object value = getValue();
        return value != null ? value.toString() : "";
    }

    /**
     * 剥离SQL注入部分代码
     */
    private String escapeSql(String value) {
        return value.replaceAll("('.+--)|(--)|(\\|)|(%7C)", "");
    }

    /**
     * 剥离js注入
     */
    private String escapeScript(String value) {
        if (StringUtils.isBlank(value)) {
            return value;
        }
        value = value.replace("script", "\\script").replace("/script", "\\/script");
        return value;
    }

}
