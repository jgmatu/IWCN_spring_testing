package es.urjc.javsan.services;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class Product {

    @Min(0)
	private int code;

    @Size(min=2, max=30)
	private String name;
    
    @Size(min=0, max=120)
    private String desc;
	
    private float price;
	
	public Product() {
		this.code = -1;
		this.name = "";
		this.desc = "";
		this.price = -1.0f;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	@Override 
	public String toString() {
		return String.format("Code : %d Name : %s, Desc : %s, Price : %f", code, name, desc, price);
	}
}
