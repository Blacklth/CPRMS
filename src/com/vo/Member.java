package com.vo;

public class Member extends Person {
	private String pc_code;
	private boolean is_chair = false;


	public boolean isIs_chair() {
		return is_chair;
	}

	public void setIs_chair(boolean is_chair) {
		this.is_chair = is_chair;
	}

	public String getPc_code() {
		return pc_code;
	}

	public void setPc_code(String pc_code) {
		this.pc_code = pc_code;
	}
}
