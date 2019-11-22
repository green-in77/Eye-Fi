package kr.or.bit.dto.board;

public class Category {
	private int ccode; /* 카테고리코드 */
	private String cname; /* 카테고리이름 */
	
	public Category() {}

	public int getCcode() {
		return ccode;
	}

	public void setCcode(int ccode) {
		this.ccode = ccode;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Override
	public String toString() {
		return "Category [ccode=" + ccode + ", cname=" + cname + "]";
	}

}
