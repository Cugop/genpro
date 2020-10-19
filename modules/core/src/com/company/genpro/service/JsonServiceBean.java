//package com.company.genpro.service;
//
//import com.haulmont.cuba.core.EntityManager;
//import com.haulmont.cuba.core.Persistence;
//import com.haulmont.cuba.core.Query;
//import com.haulmont.cuba.core.Transaction;
//import org.json.JSONObject;
//import org.springframework.stereotype.Service;
//
//import javax.inject.Inject;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service(JsonService.NAME)
//public class JsonServiceBean implements JsonService {
//    private Map<String, JSONObject> objectMap = new HashMap<>();
//    @Inject
//    private Persistence persistence;
//
//    @Override
//    public String getJsonById(String uuid) {
//        if (objectMap.isEmpty())
//            loadData();
//        JSONObject ajsonObject = objectMap.get(uuid);
//        return ajsonObject.toString();
//    }
//
//    @Override
//    public void setJsonById(String uuid, String json) {
////        try (Transaction tx = persistence.createTransaction()) {
////            EntityManager em = persistence.getEntityManager();
////            Query query = em.createNativeQuery("UPDATE genpro_json set ");
////            List <Object[]>list = query.getResultList();
////            for (Object[] s : list) {
////                JSONObject json = new JSONObject(s[1]);
////                objectMap.put(s[0].toString(), json);
////            }
////            tx.commit();
////        }
//    }
//
//    private void loadData() {
//        try (Transaction tx = persistence.createTransaction()) {
//            EntityManager em = persistence.getEntityManager();
//            Query query = em.createNativeQuery("SELECT * FROM genpro_json");
//            List <Object[]>list = query.getResultList();
//            for (Object[] s : list) {
//                JSONObject json = new JSONObject(s[1]);
//                objectMap.put(s[0].toString(), json);
//            }
//            tx.commit();
//        }
//    }
//}