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
@Table(name = "customer_attribute_options")
public class CustomerAttributeOptionEntity {

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
    private CustomerAttributeDefinitionEntity attributeDefinition;

    private String optionValue;


    // =========================
    // Getters and Setters
    // =========================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerAttributeDefinitionEntity getAttributeDefinition() {
        return attributeDefinition;
    }

    public void setAttributeDefinition(
            CustomerAttributeDefinitionEntity attributeDefinition) {
        this.attributeDefinition = attributeDefinition;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }
}