package com.manny.bus;

public class BusDetails {
	private int BUS_ID;
	private String BOOKING_FROM;
	private	String BOOKING_TO;
	private String TIME;
	private Double PRICE;
	
	public int getBUS_ID() {
		return BUS_ID;
	}
	public void setBUS_ID(int bUS_ID) {
		BUS_ID = bUS_ID;
	}
	public String getBOOKING_FROM() {
		return BOOKING_FROM;
	}
	public void setBOOKING_FROM(String bOOKING_FROM) {
		BOOKING_FROM = bOOKING_FROM;
	}
	public String getBOOKING_TO() {
		return BOOKING_TO;
	}
	public void setBOOKING_TO(String bOOKING_TO) {
		BOOKING_TO = bOOKING_TO;
	}
	public String getTIME() {
		return TIME;
	}
	public void setTIME(String tIME) {
		TIME = tIME;
	}
	public Double getPRICE() {
		return PRICE;
	}
	public void setPRICE(Double pRICE) {
		PRICE = pRICE;
	}
	public BusDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BusDetails(int bUS_ID, String bOOKING_FROM, String bOOKING_TO, String tIME, Double pRICE) {
		super();
		BUS_ID = bUS_ID;
		BOOKING_FROM = bOOKING_FROM;
		BOOKING_TO = bOOKING_TO;
		TIME = tIME;
		PRICE = pRICE;
	}
	
	

}
