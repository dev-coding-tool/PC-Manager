package model;

public class MemberVO {
	private int no;
	private String id;
	private String pw1;
	private String name;
	private String gender;
	private String phone;
	private String birth;
	private String status;
	//로그인 입력
	public MemberVO(String id, String pw1) {
		super();
		this.id = id;
		this.pw1 = pw1;
	}
	
	//회원가입 입력(7개)
	public MemberVO(String id, String name, String gender, String phone, String birth, String pw1, String status) {
		super();
		this.id = id;
		this.pw1 = pw1;
		this.name = name;
		this.gender = gender;
		this.phone = phone;
		this.birth = birth;
		this.status = status;
	}
	
	//비밀번호 빼고(6개) - 회원정보 테이블뷰에 나타내는것
	public MemberVO(String id, String name, String gender, String phone, String birth,  String status) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.phone = phone;
		this.birth = birth;
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw1() {
		return pw1;
	}
	public void setPw1(String pw1) {
		this.pw1 = pw1;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}

	@Override
	public String toString() {
		return "MemberVO [id=" + id + ", pw1=" + pw1 + ", name=" + name + ", gender=" + gender + ", phone=" + phone
				+ ", birth=" + birth + ", status=" + status + "]";
	}
	
}
