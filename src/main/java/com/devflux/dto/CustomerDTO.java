package com.devflux.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CustomerDTO
{
	private long id;

	private String firstName;

	private String lastName;
	private String mailAddress;

	private String phoneNumber;

	private LocalDateTime createdAt;

    private List<CustomerAttributeDTO> attributes;
    
    private boolean isActive;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public LocalDateTime getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt)
	{
		this.createdAt = createdAt;
	}

	public List<CustomerAttributeDTO> getAttributes()
	{
		return attributes;
	}

	public void setAttributes(List<CustomerAttributeDTO> attributes)
	{
		this.attributes = attributes;
	}
	
	public boolean isActive()
	{
		return isActive;
	}

	public void setActive(boolean isActive)
	{
		this.isActive = isActive;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("CustomerDTO [id=");
		builder.append(id);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", mailAddress=");
		builder.append(mailAddress);
		builder.append(", phoneNumber=");
		builder.append(phoneNumber);
		builder.append(", createdAt=");
		builder.append(createdAt);
		builder.append(", attributes=");
		builder.append(attributes);
		builder.append(", isActive=");
		builder.append(isActive);
		builder.append("]");
		return builder.toString();
	}

	


}
