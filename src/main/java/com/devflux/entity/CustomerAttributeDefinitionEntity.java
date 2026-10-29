package com.devflux.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
    name = "customer_attribute_definitions",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_attribute_name",
            columnNames = "attributeName"
        )
    }
)
public class CustomerAttributeDefinitionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String attributeName;

    /*
     * TEXT
     * DROPDOWN
     */
    private String attributeType;

    private Integer displayOrder;
    
    @Column(name = "status")
    private boolean status;

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
    private List<CustomerAttributeOptionEntity> options = new ArrayList<>();


    // =========================
    // Getters and Setters
    // =========================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public boolean isStatus()
	{
		return status;
	}

	public void setStatus(boolean status)
	{
		this.status = status;
	}

	public List<CustomerAttributeOptionEntity> getOptions() {
        return options;
    }

    public void setOptions(List<CustomerAttributeOptionEntity> options) {
        this.options = options;
    }
}