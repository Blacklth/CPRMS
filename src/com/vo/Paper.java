package com.vo;

public class Paper {
	private int paper_number;
	private String title;
	private String paper_type;
	private String abs;
	private String is_pc;
	private double spread;
	private String decision = null;
	public int getPaper_number() {
		return paper_number;
	}
	public void setPaper_number(int paper_number) {
		this.paper_number = paper_number;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPaper_type() {
		return paper_type;
	}
	public void setPaper_type(String paper_type) {
		this.paper_type = paper_type;
	}
	public String getAbs() {
		return abs;
	}
	public void setAbs(String abs) {
		this.abs = abs;
	}
	public String getIs_pc() {
		return is_pc;
	}
	public void setIs_pc(String is_pc) {
		this.is_pc = is_pc;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public double getSpread() {
		return spread;
	}
	public void setSpread(double spread) {
		this.spread = spread;
	}
}
