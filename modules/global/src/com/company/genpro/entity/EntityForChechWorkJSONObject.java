package com.company.genpro.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;

@Table(name = "GENPRO_ENTITY_FOR_CHECH_WORK_JSON_OBJECT")
@Entity(name = "genpro_EntityForChechWorkJSONObject")
@NamePattern("%s|name")
public class EntityForChechWorkJSONObject extends StandardEntity {
    private static final long serialVersionUID = 4448595978992107552L;

    @Column(name = "NAME")
    private String name;

    /**
     * Гражданство
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizenship_id")
    protected Country country;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

}