package com.company.genpro.entity;

import com.company.genpro.types.json.SignType;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

//    /**
//     * Гражданство
//     */
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "citizenship_id")
//    protected Country country;


    public void setDocJ(String j){
        docJ = (String) SignType.SIGN_TYPE_JSON.convert(j);
    }
}