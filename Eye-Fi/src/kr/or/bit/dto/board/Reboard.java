package kr.or.bit.dto.board;

public class Reboard extends Board{
	private int COL; /* 답변식별번호 */		
	private int seq; /* 글번호 */		
	private int refer; /* 그룹번호 */		
	private int depth; /* 들여쓰기 */		
	private int step; /* 답변정렬 */		
	private int rebdel; /* 삭제 */		
	private int pseq; /* 원본글번호 */
	
	public Reboard() {}

	public int getCOL() {
		return COL;
	}

	public void setCOL(int cOL) {
		COL = cOL;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getRefer() {
		return refer;
	}

	public void setRefer(int refer) {
		this.refer = refer;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getRebdel() {
		return rebdel;
	}

	public void setRebdel(int rebdel) {
		this.rebdel = rebdel;
	}

	public int getPseq() {
		return pseq;
	}

	public void setPseq(int pseq) {
		this.pseq = pseq;
	}

	@Override
	public String toString() {
		return "Board [seq=" + seq + ", userid=" + getUserid() + ", subject=" + getSubject() + ", content=" + getContent() + ", hit="
				+ getHit() + ", logtime=" + getLogtime() + ", classify=" + getClassify() + ", del=" + getDel() + ", notice=" + getNotice()
				+ ", bcode=" + getBcode() + "]"+"Reboard [COL=" + COL + ", seq=" + seq + ", refer=" + refer + ", depth=" + depth + ", step=" + step
				+ ", rebdel=" + rebdel + "]";
	}

}
