package com.company.genpro.web.screens.entityforchechworkjsonobject;

import com.company.genpro.entity.Country;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.screen.*;
import com.company.genpro.entity.EntityForChechWorkJSONObject;
import org.json.JSONObject;
import org.slf4j.Logger;


import javax.inject.Inject;

@UiController("genpro_EntityForChechWorkJSONObject.browse")
@UiDescriptor("entity-for-chech-work-json-object-browse.xml")
@LookupComponent("table")
@LoadDataBeforeShow
public class EntityForChechWorkJSONObjectBrowse extends MasterDetailScreen<EntityForChechWorkJSONObject> {
    @Inject
    private PickerField<Country> countryField;
    @Inject
    private Logger log;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
//        JSONObject jsonObjectPickerField = new JSONObject(this.countryField);
//        log.info("jsonObjectPickerField: " + jsonObjectPickerField.toString());
//        System.out.println("jsonObjectPickerField: " + jsonObjectPickerField.toString());
    }

    @Subscribe("saveBtn")
    public void onSaveBtnClick(Button.ClickEvent event) {
        log.info("saveBtn");
        log.info("this.countryField" + this.countryField);
//        JSONObject jsonObjectPickerField = new JSONObject(this.countryField);
//        log.info("jsonObjectPickerField: " + jsonObjectPickerField.toString());
    }

}