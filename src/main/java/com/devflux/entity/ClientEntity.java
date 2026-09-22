
package com.devflux.entity;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "clients", uniqueConstraints = @UniqueConstraint(name = "mailAddress_unique", columnNames = "mailAddress"))
public class ClientEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String firstName;

	private String lastName;

	@Column(unique = true, nullable = false)
	private String mailAddress;

	private String phoneNumber;

	private LocalDateTime createdAt;

	private boolean isActive;
	
	 @Column(length = 500)
    private String attribute1;

    @Column(length = 500)
    private String attribute2;

    @Column(length = 500)
    private String attribute3;

    @Column(length = 500)
    private String attribute4;

    @Column(length = 500)
    private String attribute5;

    
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	

	public String getAttribute1()
	{
		return attribute1;
	}

	public void setAttribute1(String attribute1)
	{
		this.attribute1 = attribute1;
	}

	public String getAttribute2()
	{
		return attribute2;
	}

	public void setAttribute2(String attribute2)
	{
		this.attribute2 = attribute2;
	}

	public String getAttribute3()
	{
		return attribute3;
	}

	public void setAttribute3(String attribute3)
	{
		this.attribute3 = attribute3;
	}

	public String getAttribute4()
	{
		return attribute4;
	}

	public void setAttribute4(String attribute4)
	{
		this.attribute4 = attribute4;
	}

	public String getAttribute5()
	{
		return attribute5;
	}

	public void setAttribute5(String attribute5)
	{
		this.attribute5 = attribute5;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("ClientEntity [id=");
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
		builder.append(", isActive=");
		builder.append(isActive);
		builder.append(", attribute1=");
		builder.append(attribute1);
		builder.append(", attribute2=");
		builder.append(attribute2);
		builder.append(", attribute3=");
		builder.append(attribute3);
		builder.append(", attribute4=");
		builder.append(attribute4);
		builder.append(", attribute5=");
		builder.append(attribute5);
		builder.append("]");
		return builder.toString();
	}

}