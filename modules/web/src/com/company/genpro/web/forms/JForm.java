///*
//package com.company.genpro.web.forms;
//
//import com.haulmont.cuba.gui.UiComponents;
//import com.haulmont.cuba.gui.components.Component;
//import com.haulmont.cuba.gui.components.Form;
//import com.haulmont.cuba.gui.components.HasValue;
//import com.haulmont.cuba.gui.components.TabSheet;
//import com.haulmont.cuba.web.gui.components.WebForm;
//import com.haulmont.cuba.web.gui.components.WebTabSheet;
//import lombok.extern.slf4j.Slf4j;
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//@Slf4j
//public class JForm {
//
//    private final JSONObject jsonObject;
//    private final UiComponents uiComponents;
//    private final Form formTab1 = new WebForm();
//    private final Form formTab2 = new WebForm();
//
//    public JForm(String jsonStr, UiComponents uiComponents) {
////        this.jsonObject = new JSONObject((String) (new JSONObject(jsonStr)).get("value"));
//        this.jsonObject = (jsonStr == null) ? new JSONObject("{}") : new JSONObject(jsonStr);
//        this.uiComponents = uiComponents;
//        formTab1.setId("formTab1");
//    }
//
//
//    private void clearForm(Form form) {
//        Component components = form.getComponent("TabSheet");
//        form.remove(components);
////        for (Component component : components) {
////            String id = component.getId();
////            if (id == null)
////                continue;
////            String idd = component.getId().substring(0, 4);
////            if ("add_".equals(idd))
////                formTab1.remove(component);
////        }
//    }
//
//    @SuppressWarnings("unchecked")
//    public void createForm(Form form) {
//        if (!jsonObject.has("components"))
//            return;
//        clearForm(form);
//        JSONArray objects = jsonObject.getJSONArray("components");
//        TabSheet tabSheet = new WebTabSheet();
//        tabSheet.setId("TabSheet");
//
//        tabSheet.addTab("Поля", formTab1);
//        Form formTab2 = new WebForm();
//        tabSheet.addTab("JSON", formTab2);
//        form.add(tabSheet);
//        for (int count = 0; count < objects.length(); count++) {
//            JSONObject o = (JSONObject) objects.get(count);
//            String uiClass = (String) o.get("uiClass");
//            Object component = uiComponents.create(uiClass);// main
//            Object value = null;
//            if (o.has("value")) {
//                value = o.get("value");
//            }
//            if (component instanceof HasValue)
//                ((HasValue) component).setValue(value);
//
//            String caption = null;
//            if (o.has("caption"))
//                caption = (String) o.get("caption");
//            if (component instanceof Component.HasCaption)
//                ((Component.HasCaption) component).setCaption(caption);
//            if (component instanceof Component.Editable)
//                ((Component.Editable) component).setEditable(true);
//
//            ((Component) component).setId("add_" + count);
//            ((Component) component).setEnabled(true);
//
//            formTab1.add((Component) component);
//        }
//    }
//
//    public static JSONObject createJSONObjectFromComponents(Collection<Component> components) {
//        JSONContainer componentsContainer = new JSONContainer();
//        List<ComponentDTO> componentsList = new ArrayList<>();
//        for (Component component : components) {
//            ComponentDTO componentDTO = new ComponentDTO();
//            try {
//                Field uiClassField = component.getClass().getField("NAME");
//                componentDTO.setUiClass((String) uiClassField.get(component));
//            } catch (NoSuchFieldException | IllegalAccessException e) {
//                log.error("no such field NAME in component");
//            }
//
//            if (component instanceof HasValue)
//                componentDTO.setValue(((HasValue) component).getValue());
//
//            if (component instanceof Component.HasCaption)
//                componentDTO.setCaption(((Component.HasCaption) component).getCaption());
//            componentsList.add(componentDTO);
//        }
//        componentsContainer.setComponents(componentsList);
//        return  new JSONObject(componentsContainer);
//    }
//
//}
//*/

package com.company.genpro.web.forms;
import lombok.extern.slf4j.Slf4j;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.web.gui.components.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Slf4j
public class JForm {

    private JSONObject jsonObject;
    private final UiComponents uiComponents;
    private final TabSheet tabSheet = new WebTabSheet();
    private final Form formTab1 = new WebForm();
    private final Form formTab2 = new WebForm();

    private final TextArea jsonText = new WebTextArea();
    private final VBoxLayout boxLayout = new WebVBoxLayout();

    //    WebButton webButton = new WebButton();
    public JForm(Form form, UiComponents uiComponents,Button button) {
        this.uiComponents = uiComponents;
        form.add(tabSheet);
        tabSheet.addTab("Поля", formTab1);
        tabSheet.addTab("JSON", formTab2);
        formTab2.add(boxLayout);
//        webButton.setId("button");
        button.setParent(boxLayout);
        button.setVisible(true);
        boxLayout.add(button);
        boxLayout.add(jsonText);
        jsonText.setSizeFull();
    }

    private void clearForm() {
        formTab1.removeAll();
    }


    @SuppressWarnings("unchecked")
    public void loadJson(String jsonStr) {
        this.jsonObject = (jsonStr == null) ? new JSONObject("{}") : new JSONObject(jsonStr);
        JSONArray objects = jsonObject.getJSONArray("components");

        clearForm();

        for (int count = 0; count < objects.length(); count++) {
            JSONObject o = (JSONObject) objects.get(count);
            String uiClass = (String) o.get("uiClass");
            Component component = uiComponents.create(uiClass);
            Object value = null;
            if (o.has("value")) {
                value = o.get("value");
            }
            if (component instanceof HasValue)
                ((HasValue) component).setValue(value);

            String caption = null;
            if (o.has("caption"))
                caption = (String) o.get("caption");
            if (component instanceof Component.HasCaption)
                ((Component.HasCaption) component).setCaption(caption);
            if (component instanceof Component.Editable)
                ((Component.Editable) component).setEditable(true);

            component.setId("add_" + count);

            formTab1.add(component);
        }
        jsonText.setValue(jsonObject.toString());
    }

    public void reloadJson(){
        loadJson(jsonText.getValue().toString());
    }


        public static JSONObject createJSONObjectFromComponents(Collection<Component> components) {
        JSONContainer componentsContainer = new JSONContainer();
        List<ComponentDTO> componentsList = new ArrayList<>();
        for (Component component : components) {
            ComponentDTO componentDTO = new ComponentDTO();
//            try {
//                Field uiClassField = component.getClass().getField("NAME");
//                componentDTO.setUiClass((String)uiClassField.get(component));
//            } catch (NoSuchFieldException | IllegalAccessException e) {
//                log.error("no such field NAME in component");
//            }

            if (component instanceof HasValue)
                componentDTO.setValue(((HasValue) component).getValue());

            if (component instanceof Component.HasCaption)
                componentDTO.setCaption(((Component.HasCaption) component).getCaption());
            componentsList.add(componentDTO);
        }
        componentsContainer.setComponents(componentsList);
        return  new JSONObject(componentsContainer);
    }


}
