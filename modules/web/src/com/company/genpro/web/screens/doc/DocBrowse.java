package com.company.genpro.web.screens.doc;

import com.company.genpro.web.forms.JForm;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.company.genpro.entity.Doc;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.web.gui.components.WebForm;
import com.haulmont.cuba.web.gui.components.WebTabSheet;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.*;

@UiController("genpro_Doc.browse")
@UiDescriptor("doc-browse.xml")
@LookupComponent("table")
@LoadDataBeforeShow
public class DocBrowse extends MasterDetailScreen<Doc> {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(DocBrowse.class);
    @Inject
    private Form form;

    @Inject
    UiComponents uiComponents;

    @Inject
    private InstanceContainer<Doc> docDc;

    @Inject
    private Button button;

    @Inject
    DataManager dataManager;

    private JForm jForm;

    @Subscribe("table")
    public void onTableSelection(Table.SelectionEvent<Doc> event) {
        String jsonBody = event.getSelected().iterator().next().getDocJ();
        jForm.loadJson(jsonBody);
//        JForm jForm = new JForm(jsonBody,uiComponents);
//        jForm.createForm(form);
    }
    @Subscribe
    public void onAfterInit(AfterInitEvent event) {
        jForm = new JForm(form, uiComponents, button);
    }

    @Subscribe("saveBtn")
    public void onSaveBtnClick(Button.ClickEvent event) {
        enableEditControls(true);
        WebForm webForm = (WebForm) form.getComponent("formTab1");
        assert webForm != null;
        Collection<Component> components = webForm.getComponents(0);
        String json = JForm.createJSONObjectFromComponents(components).toString();
        log.info(json);
        Doc doc = docDc.getItem();
        doc.setJsonBody(json);
        //doc.setDocJ(json);
        dataManager.commit(doc);
        getEditedEntity().setDocJ(json);
    }

    @Subscribe("button")
    public void onTableSelection(Button.ClickEvent event) {
        jForm.reloadJson();
        log.info("button reaction");
    }

}

//public class DocBrowse extends MasterDetailScreen<Doc> {
//
//    @Inject
//    private Form form;
//    @Inject
//    private UiComponents uiComponents;
//
//
//    private JForm jForm;
//
//    @Subscribe("table")
//    public void onTableSelection(Table.SelectionEvent<Doc> event) {
//        String jsonBody = event.getSelected().iterator().next().getJsonBody();
//        jForm.loadJson(jsonBody);
//    }
//
//    @Subscribe
//    public void onAfterInit(AfterInitEvent event) {
//        jForm = new JForm(form, uiComponents, button);
//    }
//
//    @Subscribe("button")
//    public void onTableSelection(Button.ClickEvent event) {
//        jForm.reloadJson();
//        System.out.println();
//    }
//
//}