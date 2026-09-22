package com.devflux.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "client_attribute_options")
public class ClientAttributeOptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Which attribute definition this option belongs to.
     *
     * Example:
     *
     * Country -> India
     * Country -> USA
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "attribute_definition_id",
        nullable = false
    )
    @JsonIgnore
    private ClientAttributeDefinitionEntity attributeDefinition;

    private String optionValue;

    private Integer displayOrder;

    private Integer status;


    // =========================
    // Getters and Setters
    // =========================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientAttributeDefinitionEntity getAttributeDefinition() {
        return attributeDefinition;
    }

    public void setAttributeDefinition(
            ClientAttributeDefinitionEntity attributeDefinition) {
        this.attributeDefinition = attributeDefinition;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
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
}