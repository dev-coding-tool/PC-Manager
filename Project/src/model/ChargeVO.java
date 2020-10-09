package model;

public class ChargeVO {
	private int enterno;
	private String starttime;
	private int prepaidmoney; //���ҿ��
	private String id;
	private String startdate;
	private String seatno;
	private int availabletime;
	
	public ChargeVO() {
	}
	
	//��������
	public ChargeVO(int prepaidmoney, int availabletime, String id) {
		super();
		this.prepaidmoney = prepaidmoney;
		this.availabletime = availabletime;
		this.id = id;
	}
	
	//�߰�����
	public ChargeVO(int prepaidmoney, int availabletime) {
		super();
		this.prepaidmoney = prepaidmoney;
		this.availabletime = availabletime;
	}
	
	//�ð�����
	public ChargeVO(int availabletime, String id) {
		super();
		this.availabletime = availabletime;
		this.id = id;
	}
	
	//���̵�(ȸ�����Խ� ���)
	public ChargeVO(String id) {
		super();
		this.id = id;
	}
	/*
	//�ڸ���
	public ChargeVO(String seatno) {
		super();
		this.seatno = seatno;
	}
	*/
	public ChargeVO(int enterno, String starttime, int prepaidmoney, String id, String startdate, String seatno, int availabletime) {
		super();
		this.enterno = enterno;
		this.starttime = starttime;
		this.prepaidmoney = prepaidmoney;
		this.id = id;
		this.startdate = startdate;
		this.seatno = seatno;
		this.availabletime = availabletime;
	}
	
	//enterno ����
	public ChargeVO(String starttime, int prepaidmoney, String id, String startdate, String seatno, int availabletime) {
		super();
		this.starttime = starttime;
		this.prepaidmoney = prepaidmoney;
		this.id = id;
		this.startdate = startdate;
		this.seatno = seatno;
		this.availabletime = availabletime;
	}
	
	//���۽ð�, ���ҿ��, ���̵�, ���۳�¥, ��밡�ɽð�  
	public ChargeVO(String starttime, int prepaidmoney, String id, String startdate, int availabletime) {
		super();
		this.starttime = starttime;
		this.prepaidmoney = prepaidmoney;
		this.id = id;
		this.startdate = startdate;
		this.availabletime = availabletime;
	}
	
	
	public int getEnterno() {
		return enterno;
	}
	public void setEnterno(int enterno) {
		this.enterno = enterno;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public int getPrepaidmoney() {
		return prepaidmoney;
	}
	public void setPrepaidmoney(int prepaidmoney) {
		this.prepaidmoney = prepaidmoney;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getSeatno() {
		return seatno;
	}
	public void setSeatno(String seatno) {
		this.seatno = seatno;
	}
	public int getAvailabletime() {
		return availabletime;
	}
	public void setAvailabletime(int availabletime) {
		this.availabletime = availabletime;
	}
	
}
	
