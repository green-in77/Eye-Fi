package kr.or.bit.dto.board;

public class Board_Type {
	private int	btype; /* 게시판형식번호 */
	private String btype_name; /* 게시판형식이름 */
	
	public Board_Type() {}

	public int getBtype() {
		return btype;
	}

	public void setBtype(int btype) {
		this.btype = btype;
	}

	public String getBtype_name() {
		return btype_name;
	}

	public void setBtype_name(String btype_name) {
		this.btype_name = btype_name;
	}

	@Override
	public String toString() {
		return "Board_Type [btype=" + btype + ", btype_name=" + btype_name + "]";
	}
	
}
