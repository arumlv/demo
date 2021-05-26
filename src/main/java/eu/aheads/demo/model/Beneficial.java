package eu.aheads.demo.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "BENEFICIAL", indexes = @Index(name = "BENEFICIAL_IDX", columnList = "LE_REG_CODE, FORE_NAME, SUR_NAME, BIRTH_DATE"))
public class Beneficial implements Serializable {

    @Id
    private Long id;

    @Column(name = "LE_REG_CODE")
    private String leRegCode;

    @Column(name = "FORE_NAME")
    private String foreName;

    @Column(name = "SUR_NAME")
    private String surName;

    @Column(name = "LV_ID_NUMBER")
    private String latvianIdentityNumber;

    @Column(name = "BIRTH_DATE")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name = "NATIONALITY")
    private String nationality;

    @Column(name = "RESIDENCE")
    private String residence;

    @Column(name = "REGISTERED_ON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registeredOn;

    @Column(name = "LAST_MODIFIED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedAt;
}
