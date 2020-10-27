package com.company.genpro.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.CaseConversion;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@NamePattern("%s|name")
@Table(name = "Country")
@Entity(name = "Country")
public class Country extends StandardEntity {
    private static final long serialVersionUID = -6371819367355850697L;


    // Азербайджан
    public static final String AZ = "200ccde5-60cc-411a-bd8f-408bcfc9964c";

    // Армения
    public static final String AM = "c09581dd-8f96-4813-b88b-e3fd454a403e";

    // Белоруссия
    public static final String BY = "512045bb-3a66-431c-9c03-407ee4a6a5fa";

    // Грузия
    public static final String GE = "385631fc-b3ef-433f-9601-f2d2f2b14555";

    // Казахстан
    public static final String KZ = "d8ecbf42-9dea-4eb6-888b-ca148145ea7b";

    // Киргизия
    public static final String KG = "2dd2f863-66ee-4f2e-8e9c-3ec10bda912d";

    // Латвия
    public static final String LV = "eb5af533-22a3-4828-8a77-59059aa8eaf8";

    // Литва
    public static final String LT = "378d4faf-0d0d-4534-b74a-2883ff8cee89";

    // Молдавия
    public static final String MD = "d0e8948a-5d15-4043-8d75-da7e199cfba7";

    // Россия
    public static final String RU = "3395ae92-f997-4fe2-ac01-eef27df1891c";

    // Таджикистан
    public static final String TJ = "839fee58-27ef-49b2-8540-4ee0c2c90072";

    // Туркмения
    public static final String TM = "7f6cac2b-24e2-4dcb-b020-40667bcaaded";

    // Узбекистан
    public static final String UZ = "8f774caa-eac6-4556-939c-0238b7eb68ec";

    // Украина
    public static final String UA = "738a39a8-a280-4bbc-a02e-fc11ce066358";

    // Эстония
    public static final String EE = "bcc8d9b9-2e49-414d-9188-4f4925b28fc7";

    /**
     * Название страны
     */
    @NotNull(message = "Not null")
    @NotBlank(message = "Not Blank")
    @Column(name = "name", nullable = false, unique = true, length = 127)
    protected String name;

    /**
     * Название страны в именительном падеже для документов, если пустое - берётся из name
     */
    @Column(name = "name_im", length = 127)
    protected String nameIm;

    /**
     * Название страны в родительном падеже для документов, если пустое - берётся из name
     */
    @Column(name = "name_rod", length = 127)
    protected String nameRod;

    /**
     * Код страны цифровой
     */
    @NotBlank
    @NotEmpty
    @Length(max = 7)
    @Column(name = "code_numeric", length = 7)
    protected String codeNumeric;

    /**
     * Код страны код нарусском языке
     */
    @CaseConversion
    @Pattern(message = "Use only RU letters", regexp = "^([-\u0410-\u042F])*$")//буквы А-Я
    @Length(message = "Length max 2 letter", max = 3)
    @NotBlank(message = "Not Blank")
    @Column(name = "code_2_letter_ru", length = 7)
    protected String code2LetterRU;

    /**
     * Код страны 2-х букв. код на англ. яз
     */
    @CaseConversion
    @Pattern(message = "Use only EN latters", regexp = "^([-A-Z])*$")
    @Length(message = "Length max 2 letter", max = 2)
    @NotBlank(message = "Not Blank")
    @Column(name = "code_2_letter_en", length = 7)
    protected String code2LetterEN;

    /**
     * Код страны 3-х букв. код на англ. яз
     */
    @CaseConversion
    @Pattern(message = "Use only EN latters", regexp = "^([-A-Z])*$")
    @Length(message = "Length max 3 letter", max = 3)
    @NotBlank(message = "Not Blank")
    @Column(name = "code_3_letter_en", length = 7)
    protected String code3LetterEN;

    public String getNameIm() {
        return nameIm;
    }

    public void setNameIm(String nameIm) {
        this.nameIm = nameIm;
    }
}