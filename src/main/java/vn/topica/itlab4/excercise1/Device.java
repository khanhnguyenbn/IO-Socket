package vn.topica.itlab4.excercise1;

import java.util.Date;

public class Device {
	private String code;
	private String name;
	private String owner;
	private Date inputDate;
	private int warrantyYear;
	
	public Device() {
		
	}
	
	public Device(String code, String name, String owner, Date inputDate, int warrantyYear) {
		this.code = code;
		this.name = name;
		this.owner = owner;
		this.inputDate = inputDate;
		this.warrantyYear = warrantyYear;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public Date getInputDate() {
		return inputDate;
	}
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	public int getWarrantyYear() {
		return warrantyYear;
	}
	public void setWarrantyYear(int warrantyYear) {
		this.warrantyYear = warrantyYear;
	}
	
	@Override
	public String toString() {
		return code + "," + name + "," + owner + "," + Utils.dateToString(inputDate) + "," + warrantyYear;
	}
	
	
}
