package com.company.genpro.web.forms;

import com.company.genpro.entity.Country;
import com.haulmont.cuba.gui.components.data.value.ContainerValueSource;
import com.haulmont.cuba.gui.model.*;
import lombok.extern.slf4j.Slf4j;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.web.gui.components.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Slf4j
public class JForm {

//        @Inject
//        private DataComponents dataComponents;
//    @Inject
//    private DataContext dataContext;

    private JSONObject jsonObject;
    private final UiComponents uiComponents;
    private final TabSheet tabSheet = new WebTabSheet();
    private final Form formTab1 = new WebForm();
    private final Form formTab2 = new WebForm();

    private final TextArea<String> jsonText = new WebTextArea<String>();
    private final VBoxLayout boxLayout = new WebVBoxLayout();

    private Component component;
    private Form form;


    private CollectionContainer<Country> countryDc;
    private CollectionLoader<Country> countryDl;

    public JForm(Form form, UiComponents uiComponents, Button button, CollectionContainer<Country> countryDc,CollectionLoader<Country> countryDl ) {
        this.countryDc=countryDc;
        this.countryDl=countryDl;
        this.uiComponents = uiComponents;
        this.form = form;
        this.form.add(tabSheet);
        tabSheet.addTab("Поля", formTab1);
        tabSheet.addTab("JSON", formTab2);
        formTab2.add(boxLayout);
        // webButton.setId("button");
        button.setParent(boxLayout);
        button.setVisible(true);
        boxLayout.add(button);
        boxLayout.add(jsonText);
        jsonText.setSizeFull();
    }

    private void clearForm() {
        formTab1.removeAll();
    }

    /*
        {"type": "jsonb", "components": [{"value": false, "caption": "check", "uiClass": "checkBox"}, {"value": "Название", "caption": "label", "uiClass": "label"}, {"caption": "кнопка", "uiClass": "button"}, {"caption": "combo", "uiClass": "lookupField"}]}
        {"type": "jsonb", "components": [{"value": true, "caption": "check", "uiClass": "checkBox"}, {"value": "Название", "caption": "label", "uiClass": "label"}]}
        {"type": "jsonb", "components": [{"value": true, "caption": "check", "uiClass": "checkBox"}, {"value": "Hello! Hello! Hello! Hello! Hello!", "caption": "Text", "uiClass": "textField"}]}
        "uiClass": "textField"
    * */

    @SuppressWarnings("unchecked")
    public void loadJson(String jsonStr) {
        // create JSONObject
        this.jsonObject = (jsonStr == null) ? new JSONObject("{}") : new JSONObject(jsonStr);
        // convert jsonObject to JSONArray
        JSONArray jsonArray = jsonObject.getJSONArray("components");
        // ?
        clearForm();
        // iterating components
        for (int count = 0; count < jsonArray.length(); count++) {
            // get JSONObject
            JSONObject jsonObject = (JSONObject) jsonArray.get(count);
            // found name uiClass for creating component
            String uiClass = (String) jsonObject.get("uiClass");
            component = uiComponents.create(uiClass);
            // found and get value
            Object value = null;
            String complexValue = null;
            if (jsonObject.has("value")) {
                // value = jsonObject.get("value");
                if (uiClass.equals("pickerField")) {
                    complexValue = (String) jsonObject.get("value");
                    // ((PickerField)component).set
                    // ((PickerField)component).setCaptionProperty("country");
                    ((PickerField)component).setValueSource(new ContainerValueSource<>(countryDc, "country"));
                } else {
                    value = jsonObject.get("value");
                }
            }
            // filling component
            if (component instanceof HasValue) {
                if (!uiClass.equals("pickerField")) {
                    ((HasValue) component).setValue(value);
                } else if (uiClass.equals("pickerField")) {
                    //String[] values = complexValue.split("|");
                    String[] values = complexValue.split("&");
                    for (String v : values)
                        log.info(v);
                    // DataComponents dataComponents = beanLocator.get(DataComponents.class);
//                    DataComponents dataComponents = new DataComponents();
//                    DataContext dataContext = dataComponents.createDataContext();
//
//                    countryDc = dataComponents.createCollectionContainer(Country.class);
//                    countryDl = dataComponents.createCollectionLoader();
//                    countryDl.setContainer(countryDc);
//                    countryDl.setDataContext(dataContext);
                    // countryDl.setView("order-edit");
                    countryDl.load();

//                    List<Country> countryList = countryDc.getItems();
//                    UUID uuidCountry0 = countryList.get(0).getId();
//
//                    log.info(uuidCountry0.toString());
//
//                    UUID uuid = UUID.fromString(values[1]);
//                    log.info(uuid.toString());
//
//
//                    Country country = countryDc.getItemOrNull(values[1]);
//                    log.info(country.toString());
//                    assert country != null;
//                    log.info(country.toString());
                    ((HasValue) component).setValue(countryDc.getItemOrNull(UUID.fromString(values[1])));

                }
//                ((HasValue) component).addValueChangeListener(new Consumer<HasValue.ValueChangeEvent>() {
//                    @Override
//                    public void accept(HasValue.ValueChangeEvent valueChangeEvent) {
//                        // log.info();
//                        //System.out.println(valueChangeEvent.toString());
//                    }
//                });
            }
            String caption = null;
            if (jsonObject.has("caption"))
                caption = (String) jsonObject.get("caption");
            if (component instanceof Component.HasCaption)
                ((Component.HasCaption) component).setCaption(caption);
            if (component instanceof Component.Editable)
                ((Component.Editable) component).setEditable(true);
            component.setId("add_" + count);
            formTab1.add(component);
        }
        jsonText.setValue(jsonObject.toString());

//        PickerField<Country> pickerField = null;
//        Country country = pickerField.getValue();
//        pickerField.getValueSource();

    }

    public void reloadJson() {
        loadJson(jsonText.getValue().toString());
    }

    public String saveStates() {
        WebForm webForm = (WebForm) formTab1;
        Collection<Component> components = webForm.getComponents(0);
        String jsonString = createJSONObjectFromComponents(components).toString();
        log.info(jsonString);
        jsonText.setValue(jsonString);
        return jsonString;
    }

    public static JSONObject createJSONObjectFromComponents(Collection<Component> components) {
        JSONContainer componentsContainer = new JSONContainer();
        List<ComponentDTO> componentsList = new ArrayList<>();
        for (Component component : components) {
            ComponentDTO componentDTO = new ComponentDTO();
            try {
                Field uiClassField = component.getClass().getField("NAME");
                componentDTO.setUiClass((String) uiClassField.get(component));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                log.error("no such field NAME in component");
            }
            if (component instanceof HasValue)
                componentDTO.setValue(((HasValue) component).getValue());
            if (component instanceof Component.HasCaption)
                componentDTO.setCaption(((Component.HasCaption) component).getCaption());
            componentsList.add(componentDTO);
        }
        componentsContainer.setComponents(componentsList);
        return new JSONObject(componentsContainer);
    }

    public static void jsonViewJsonText(TextArea<String> jsonText, JSONObject jsonObject) {
        jsonText.setValue(jsonObject.toString());
    }
}
