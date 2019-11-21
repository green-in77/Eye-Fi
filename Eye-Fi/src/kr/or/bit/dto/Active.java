package kr.or.bit.dto;

public class Active { //활성화코드
	private int code; //코드
	private String status; //상태
	
	public Active() {}

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
		return "Active [code=" + code + ", status=" + status + "]";
	}

}
