package com.company.genpro.entity;

import com.company.genpro.types.json.SignType;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Table(name = "GENPRO_DOC")
@Entity(name = "genpro_Doc")
@NamePattern("%s|name")
@Data
public class Doc extends StandardEntity {
    private static final long serialVersionUID = 8620314949152327121L;
    @Column(name = "NAME")
    private String name;

    @Column(name = "JSON_BODY")
    @Lob
    private String jsonBody;

    @MetaProperty(datatype = "json_orm")
    @Column(name = "doc_j")
    protected String docJ;

    public void setDocJ(String j){
        docJ = (String) SignType.SIGN_TYPE_JSON.convert(j);
    }
}