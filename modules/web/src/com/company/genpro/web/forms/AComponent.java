package com.company.genpro.web.forms;

import lombok.Data;

@Data
public class AComponent  {
    private String uiClass;
    private String caption;
    private Object value;

    public AComponent(String uiClass, String caption, Object value) {
        this.uiClass = uiClass;
        this.caption = caption;
        this.value = value;
    }
}
