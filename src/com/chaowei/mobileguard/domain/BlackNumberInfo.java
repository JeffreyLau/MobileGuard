package com.chaowei.mobileguard.domain;

public class BlackNumberInfo {

	public BlackNumberInfo() {
		// TODO Auto-generated constructor stub
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String id;
	private String number;
	private String mode;

	@Override
	public String toString() {
		return "BlackNumber [id=" + id + " number=" + number + ", mode=" + mode
				+ "]";
	}

}
