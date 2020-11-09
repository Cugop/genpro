package com.company.genpro.web.screens.doc;

import com.company.genpro.entity.Country;
import com.company.genpro.web.forms.JForm;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.company.genpro.entity.Doc;
import com.haulmont.cuba.gui.screen.LookupComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.*;

@UiController("genpro_Doc.browse")
@UiDescriptor("doc-browse.xml")
@LookupComponent("table")
@LoadDataBeforeShow
public class DocBrowse extends MasterDetailScreen<Doc> {

    private static final Logger log = LoggerFactory.getLogger(DocBrowse.class);
    @Inject
    private Form form;

    @Inject
    private CollectionContainer<Country> countryDc;

    @Inject
    private CollectionLoader<Country> countryDl;
    @Inject
    UiComponents uiComponents;

    @Inject
    private InstanceContainer<Doc> docDc;
    @Inject
    private CollectionLoader<Doc> docsDl;

//    @Inject
//    private Button button;

    @Inject
    DataManager dataManager;

    private JForm jForm;

    @Subscribe("table")
    public void onTableSelection(Table.SelectionEvent<Doc> event) {
        String jsonBody = event.getSelected().iterator().next().getDocJ();
        log.info(jsonBody);
        jForm.loadJson(jsonBody);
    }

    @Subscribe
    public void onAfterInit(AfterInitEvent event) {
        // jForm = new JForm(form, uiComponents, button, countryDc, countryDl);
        // jForm = new JForm(form, uiComponents, button);
        jForm = new JForm(form, uiComponents);
    }

    @Subscribe("saveBtn")
    public void onSaveBtnClick(Button.ClickEvent event) {
        enableEditControls(true);
        String json = jForm.saveStates();
        Doc doc = docDc.getItem();
        // doc.setJsonBody(json);
        doc.setDocJ(json);
        dataManager.commit(doc);
        getEditedEntity().setDocJ(json);
        docsDl.load();
    }

//    @Subscribe("button")
//    public void onTableSelection(Button.ClickEvent event) {
//        jForm.reloadJson();
//        log.info("button reaction");
//    }

}