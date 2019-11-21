package kr.or.bit.dto;

public class Member { //회원정보를 담을 DTO
	//member 테이블
	private String userid; /* 아이디 */
	private String userpw; /* 비밀번호 */
	private String email; /* 이메일 */
	private	int admin; /* 관리자 */ //관리자 1 , 일반회원 0 
	private int code; /* 코드 */
	
	//active 테이블
	private String status; /* 회원상태*/ // 활동 1, 정지 2, 탈퇴 3
	
	public Member() {}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserpw() {
		return userpw;
	}

	public void setUserpw(String userpw) {
		this.userpw = userpw;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Member [userid=" + userid + ", userpw=" + userpw + ", email=" + email + ", admin=" + admin + ", code="
				+ code + ", status=" + status + "]";
	}
	
}
