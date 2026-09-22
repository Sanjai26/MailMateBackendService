package com.devflux.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "client_attribute_definitions",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_attribute_number",
            columnNames = "attributeNumber"
        ),
        @UniqueConstraint(
            name = "uk_attribute_name",
            columnNames = "attributeName"
        )
    }
)
public class ClientAttributeDefinitionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * 1 = clients.attribute1
     * 2 = clients.attribute2
     * ...
     * 5 = clients.attribute5
     */
    private Integer attributeNumber;

    private String attributeName;

    /*
     * TEXT
     * DROPDOWN
     */
    private String attributeType;

    private Integer isRequired;

    private Integer displayOrder;

    private Integer status;

    /*
     * Dropdown options.
     *
     * TEXT attributes will simply have an empty list.
     */
    @OneToMany(
        mappedBy = "attributeDefinition",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private List<ClientAttributeOptionEntity> options = new ArrayList<>();


    // =========================
    // Getters and Setters
    // =========================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAttributeNumber() {
        return attributeNumber;
    }

    public void setAttributeNumber(Integer attributeNumber) {
        this.attributeNumber = attributeNumber;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }

    public Integer getIsRequired() {
        return isRequired;
    }

    public void setIsRequired(Integer isRequired) {
        this.isRequired = isRequired;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ClientAttributeOptionEntity> getOptions() {
        return options;
    }

    public void setOptions(List<ClientAttributeOptionEntity> options) {
        this.options = options;
    }
}