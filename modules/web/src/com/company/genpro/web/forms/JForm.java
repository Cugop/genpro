package com.company.genpro.web.forms;

import com.haulmont.chile.core.model.MetaClass;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.actions.picker.ClearAction;
import com.haulmont.cuba.gui.actions.picker.LookupAction;
import lombok.extern.slf4j.Slf4j;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.web.gui.components.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Slf4j
public class JForm {

    private JSONObject jsonObject;
    private final UiComponents uiComponents;

    private final TabSheet tabSheet = new WebTabSheet();
    private final Form formTab1 = new WebForm();
    private final Form formTab2 = new WebForm();

    private final TextArea<String> jsonText = new WebTextArea<String>();
    private final VBoxLayout boxLayout = new WebVBoxLayout();

    private Component component;
    private Form form;

    public JForm(Form form, UiComponents uiComponents, Button button) {
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
        Образцы json-ов для UI-компонентов
        {"type": "jsonb", "components": [{"value": false, "caption": "check", "uiClass": "checkBox"}, {"value": "Название", "caption": "label", "uiClass": "label"}, {"caption": "кнопка", "uiClass": "button"}, {"caption": "combo", "uiClass": "lookupField"}]}
        {"type": "jsonb", "components": [{"value": true, "caption": "check", "uiClass": "checkBox"}, {"value": "Название", "caption": "label", "uiClass": "label"}]}
        {"type": "jsonb", "components": [{"value": true, "caption": "check", "uiClass": "checkBox"}, {"value": "Hello! Hello! Hello! Hello! Hello!", "caption": "Text", "uiClass": "textField"}]}
        {"type": "jsonb", "components": [{"value": "Country&d1600cf6-b7fb-44d5-abb4-3a3188f68fd7", "uiClass": "pickerField"}]}
    */

    @SuppressWarnings("unchecked")
    public void loadJson(String jsonStr) {

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
                } else {
                    value = jsonObject.get("value");
                }
            }
            // filling component
            if (component instanceof HasValue) {
                if (!uiClass.equals("pickerField")) {
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
            formTab1.add(component);
        }
        jsonText.setValue(jsonObject.toString());
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
                    String id = ((Entity)((HasValue) component).getValue()).getId().toString();
                    // Проверка Аргентина: fb45224d-af82-450b-8a85-35500e1d95d7
                    log.info("id: " + id);
                    String complexValue = stringClassSimpleName + "&" +  id;
                    // componentDTO.setValue(
                    componentDTO.setValue(complexValue);

                    // componentDTO.setValue(((HasValue) component).getValue());
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
