package kr.or.bit.dto.board;

public class Board_List {
	private int bcode; /* 게시판코드 */		
	private String bname; /* 게시판이름 */		
	private int	btype; /* 게시판형식번호 */		
	private int ccode; /* 카테고리코드 */		

	public Board_List() {}

	public int getBcode() {
		return bcode;
	}

	public void setBcode(int bcode) {
		this.bcode = bcode;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public int getBtype() {
		return btype;
	}

	public void setBtype(int btype) {
		this.btype = btype;
	}

	public int getCcode() {
		return ccode;
	}

	public void setCcode(int ccode) {
		this.ccode = ccode;
	}

	@Override
	public String toString() {
		return "Board_List [bcode=" + bcode + ", bname=" + bname + ", btype=" + btype + ", ccode=" + ccode + "]";
	}

}
