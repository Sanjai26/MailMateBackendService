package com.devflux.entity;

public class SenderAccount
{

	private String	email;

	private String	password;

	public SenderAccount(String email, String password)
	{
		this.email = email;
		this.password = password;
	}

	public SenderAccount()
	{

	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getEmail()
	{
		return email.trim();
	}

	public String getPassword()
	{
		return password;
	}

	@Override
	public String toString()
	{
		return "SenderAccount [email=" + email + ", password=" + password + "]";
	}

}
