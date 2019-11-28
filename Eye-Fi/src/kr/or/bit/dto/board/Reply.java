package kr.or.bit.dto.board;

import java.text.SimpleDateFormat;

public class Reply {
	private int	rseq; /* 댓글식별번호 */
	private int seq; /* 글번호 */
	private String rcontent; /* 글내용 */
	private String reuserid; /* 작성자 */
	private String retime; /* 작성일 */
	private int redel; /* 삭제 */
	
	public Reply() {}

	public int getRseq() {
		return rseq;
	}

	public void setRseq(int rseq) {
		this.rseq = rseq;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getRcontent() {
		return rcontent;
	}

	public void setRcontent(String rcontent) {
		this.rcontent = rcontent;
	}

	public String getReuserid() {
		return reuserid;
	}

	public void setReuserid(String reuserid) {
		this.reuserid = reuserid;
	}

	public String getRetime() {
		return retime;
	}

	public void setRetime(java.sql.Date retime) {
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
		this.retime = formatter.format(retime);
	}

	public int getRedel() {
		return redel;
	}

	public void setRedel(int redel) {
		this.redel = redel;
	}

	@Override
	public String toString() {
		return "Reply [rseq=" + rseq + ", seq=" + seq + ", rcontent=" + rcontent + ", reuserid=" + reuserid
				+ ", retime=" + retime + ", redel=" + redel + "]";
	}

}
