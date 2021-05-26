package eu.aheads.demo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "REGISTER")
public class Register implements Serializable {

    @Id
    @Column(name = "REG_CODE")
    private String regCode;

    @Column(name = "SEPA")
    private String sepa;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NAME_BEFORE_QUT")
    private String nameBeforeQuotes;

    @Column(name = "NAME_IN_QUT")
    private String nameInQuotes;

    @Column(name = "NAME_AFTER_QUT")
    private String nameAfterQuotes;

    @Column(name = "WITHOUT_QUT")
    private String withoutQuotes;

    @Column(name = "REG_TYPE")
    private String regType;

    @Column(name = "REG_TYPE_TEST")
    private String regTypeText;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "TYPE_TEXT")
    private String typeText;

    @Column(name = "REGISTERED")
    @Temporal(TemporalType.DATE)
    private Date registered;

    @Column(name = "TERMINATED_DATE")
    @Temporal(TemporalType.DATE)
    private Date terminated;

    @Column(name = "CLOSED")
    private String closed;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "POST_INDEX")
    private String index;

    @Column(name = "ADDRESS_ID")
    private String addressId;

    @Column(name = "REGION")
    private String region;

    @Column(name = "CITY")
    private String city;

    @Column(name = "ATVK")
    private String atvk;

    @Column(name = "REGISTRATION_TERM")
    private String reRegistrationTerm;

    @OneToMany
    @JoinColumn(name = "LE_REG_CODE", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
    private Set<Beneficial> beneficials;

}
