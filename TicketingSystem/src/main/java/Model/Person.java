package Model;

import java.io.Serializable;

public class Person implements Serializable{

	private static final long serialVersionUID = 1L;
	private  int ID;
	private String name;
	private String password;
	private String email;
	private Address address;
	private String picture;
	private Role role;
    
	public Person() {
		super();
	}
	
	public Person(int iD, String name, String password, String email, Address address, String picture, Role role) {
		super();
		ID = iD;
		this.name = name;
		this.password = password;
		this.email = email;
		this.address = address;
		this.picture = picture;
		this.role= role;
		
	}
	public  int getID() {
		return ID;
	}
	public  void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}

	public  Role getRole() {
		return role;
	}

	public  void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Person [ID=" + ID + ", name=" + name + ", email=" + email + ", address="
				+ address + ", picture=" + picture + ", role=" + role + "]";
	}

	


	
}
