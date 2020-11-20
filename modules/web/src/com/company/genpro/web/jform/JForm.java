package com.company.genpro.web.jform;


import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.web.gui.components.WebForm;
import com.haulmont.cuba.web.gui.components.WebTabSheet;
import com.haulmont.cuba.web.gui.components.WebTextArea;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Slf4j
public class JForm {

    private JSONObject jsonObject;
    private final UiComponents uiComponents;

    private final TabSheet tabSheet = new WebTabSheet();
    private final Form formTab1 = new WebForm();
//    private final Form formTab2 = new WebForm();

    private final TextArea<String> jsonText = new WebTextArea<String>();
//    private final VBoxLayout boxLayout = new WebVBoxLayout();

    private Component component;
    private Form form;

//    public JForm(Form form, UiComponents uiComponents, Button button) {
//        this.uiComponents = uiComponents;
//
//        this.form = form;
//        this.form.add(tabSheet);
//        tabSheet.addTab("Поля", formTab1);
//        tabSheet.addTab("JSON", formTab2);
//        formTab2.add(boxLayout);
//        // webButton.setId("button");
//        // button.setParent(boxLayout);
//        // button.setVisible(true);
//        // boxLayout.add(button);
//        boxLayout.add(jsonText);
//        jsonText.setSizeFull();
//    }

    public JForm(Form form, UiComponents uiComponents) {
        this.uiComponents = uiComponents;
        this.form = form;

//        this.form.add(tabSheet);
//        tabSheet.addTab("Поля", formTab1);
//        tabSheet.addTab("JSON", formTab2);
//        formTab2.add(boxLayout);
//        boxLayout.add(jsonText);
//        jsonText.setSizeFull();
    }


//    private void clearForm() {
//        formTab1.removeAll();
//    }

    private void clearForm() {
        form.removeAll();
    }


    /*
        Образцы json-ов для UI-компонентов
        {"type": "jsonb", "components": [{"value": false, "caption": "check", "uiClass": "checkBox"}, {"value": "Название", "caption": "label", "uiClass": "label"}, {"caption": "кнопка", "uiClass": "button"}, {"caption": "combo", "uiClass": "lookupField"}]}
        {"type": "jsonb", "components": [{"value": true, "caption": "check", "uiClass": "checkBox"}, {"value": "Название", "caption": "label", "uiClass": "label"}]}
        {"type": "jsonb", "components": [{"value": true, "caption": "check", "uiClass": "checkBox"}, {"value": "Hello! Hello! Hello! Hello! Hello!", "caption": "Text", "uiClass": "textField"}]}
        {"type": "jsonb", "components": [{"value": "Country&d1600cf6-b7fb-44d5-abb4-3a3188f68fd7", "uiClass": "pickerField", "caption": "captionPickerField"}]}
        {"type": "jsonb", "components": [{"value": "29.10.2020", "uiClass": "dateField", "caption": "captionDateField"}]}
    */
    /*
    {"end_date": {"type": "8", "value": "13.11.2020", "name_ru": "Действителен до", "required": "1"}, "doc_number": {"type": "5", "value": "8888", "name_ru": "Номер ИНН", "required": "false"}}
    * */

    @SuppressWarnings("unchecked")
    public void loadJson(String jsonStr) {

        this.jsonObject = (jsonStr == null) ? new JSONObject("{}") : new JSONObject(jsonStr);
        clearForm();
        createForm(this.jsonObject);

/*
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
                } else if (uiClass.equals("dateField")) {
                    ((DateField) component).setResolution(DateField.Resolution.DAY);
                    complexValue = (String) jsonObject.get("value");
                } else {
                    value = jsonObject.get("value");
                }

            }
            // filling component
            if (component instanceof HasValue) {
                if (!uiClass.equals("pickerField") && !uiClass.equals("dateField")) {
                    ((HasValue) component).setValue(value);
                } else if (uiClass.equals("pickerField")) {
                    //
                    //String[] values = complexValue.split("//|");
                    assert complexValue != null;
                    String[] values = complexValue.split("&");
                    MetaClass metaEntityClass = AppBeans.get(Metadata.class).getClassNN(values[0]);
                    Entity entity = AppBeans.get(DataManager.class).load(metaEntityClass.getJavaClass()).id(UUID.fromString(values[1])).one();
                    PickerField pickerField = (PickerField) component;
                    pickerField.setMetaClass(metaEntityClass);
                    pickerField.setValue(entity);
                    Actions actions = AppBeans.get(Actions.class);
                    // It works
                    Action lookupAction = actions.create(LookupAction.class);
                    pickerField.addAction(lookupAction);
                    Action clearAction = actions.create(ClearAction.class);
                    pickerField.addAction(clearAction);
                    for (String v : values)
                        log.info(v);
                } else if (uiClass.equals("dateField")) {
                    DateField dateField = (DateField) component;
                    assert complexValue != null;
                    String[] values = complexValue.split("\\.");
                    ZoneId defaultZoneId = ZoneId.systemDefault();
                    LocalDate localDate = LocalDate.of(Integer.parseInt(values[2]), Integer.parseInt(values[1]), Integer.parseInt(values[0]));
                    Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
                    dateField.setValue(date);
                }
            }
            String caption = null;
            if (jsonObject.has("caption"))
                caption = (String) jsonObject.get("caption");
            if (component instanceof Component.HasCaption)
                ((Component.HasCaption) component).setCaption(caption);
            if (component instanceof Component.Editable)
                ((Component.Editable) component).setEditable(true);
            component.setId("add_" + count);
            // component.set
//            formTab1.add(component);
            form.add(component);
        }
//        jsonText.setValue(jsonObject.toString());
*/
    }

    private void createForm(JSONObject jsonObject) {
        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            createElement((JSONObject) jsonObject.get(iterator.next()));
        }
    }

    private void createElement(JSONObject jsonObject) {
        String uiClass = null;
        String typeField = null;
        String caption = null;
        String value = null;
        boolean required = true;
        if (jsonObject.has("type")) {
            typeField = (String) jsonObject.get("type");
        } else {
            typeField = "0";
        }
        if (typeField == null) typeField = "0";
        switch (typeField) {
            case "5":
                uiClass = "textField";
                required = false;
                component = uiComponents.create(uiClass);
                TextField textField = (TextField) component;
                if (jsonObject.has("value") && component instanceof HasValue) {
                    Object valueTextField = null;
                    valueTextField = jsonObject.get("value");
                    textField.setValue(valueTextField);
                }
                textField.setRequired(required);
                textField.setWidth("50%");
                if (jsonObject.has("name_ru"))
                    caption = (String) jsonObject.get("name_ru");
                if (component instanceof Component.HasCaption)
                    ((Component.HasCaption) component).setCaption(caption);
                if (component instanceof Component.Editable)
                    ((Component.Editable) component).setEditable(true);
                component.setId("add_" + uiClass + "5");
                form.add(component);
                break;

            case "8":
                uiClass = "dateField";
                required = true;
                component = uiComponents.create(uiClass);
                DateField dateField = (DateField) component;
                dateField.setResolution(DateField.Resolution.DAY);
                if (jsonObject.has("value") && component instanceof HasValue) {
                    value = (String) jsonObject.get("value");
                    String[] values = value.split("\\.");
                    ZoneId defaultZoneId = ZoneId.systemDefault();
                    LocalDate localDate = LocalDate.of(Integer.parseInt(values[2]), Integer.parseInt(values[1]), Integer.parseInt(values[0]));
                    Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
                    dateField.setValue(date);
                }
                dateField.setRequired(required);
                if (jsonObject.has("name_ru"))
                    caption = (String) jsonObject.get("name_ru");
                if (component instanceof Component.HasCaption)
                    ((Component.HasCaption) component).setCaption(caption);
                if (component instanceof Component.Editable)
                    ((Component.Editable) component).setEditable(true);
                component.setId("add_" + uiClass + "8");
                form.add(component);
                break;
            case "0":
                break;
            default:
                break;
        }

//        if (uiClass != null) {
//            component = uiComponents.create(uiClass);
//        }
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
            if (component instanceof HasValue) {
                // сохранение в json - Value
                if (component instanceof PickerField) {
                    Object objectValue = ((HasValue) component).getValue();
                    assert objectValue != null;
                    Class classObject = objectValue.getClass();
                    String stringClassSimpleName = classObject.getSimpleName();
                    String componentDTOName = componentDTO.getUiClass();

                    log.info("stringClassSimpleName: " + stringClassSimpleName);
                    log.info("componentDTOName: " + componentDTOName);
                    String id = ((Entity) ((HasValue) component).getValue()).getId().toString();
                    // Проверка Аргентина - id: fb45224d-af82-450b-8a85-35500e1d95d7;
                    log.info("id: " + id);
                    String complexValue = stringClassSimpleName + "&" + id;
                    componentDTO.setValue(complexValue);

                    // componentDTO.setValue(((HasValue) component).getValue());
                } else if (component instanceof DateField) {
//                    Object objectValue = ((HasValue) component).getValue();
//                    assert objectValue != null;
//                    Class classObject = objectValue.getClass();
                    Date date = (Date) ((HasValue) component).getValue();
                    String pattern = "dd.MM.yyyy";
                    DateFormat df = new SimpleDateFormat(pattern);
                    String string = df.format(date);
                    //  String string = ((Date) ((HasValue) component).getValue()).toString();
                    componentDTO.setValue(string);

                } else
                    componentDTO.setValue(((HasValue) component).getValue());
            }
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
