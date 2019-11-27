package kr.or.bit.dto.board;

public class Album extends Board{
	private int	aseq; /* 앨범식별번호 */
	private int seq; /* 글번호 */
	private String orign_file; /* 원본사진이름 */
	private String save_file; /* 저장사진이름 */
	
	public Album() {}

	public int getAseq() {
		return aseq;
	}

	public void setAseq(int aseq) {
		this.aseq = aseq;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getOrign_file() {
		return orign_file;
	}

	public void setOrign_file(String orign_file) {
		this.orign_file = orign_file;
	}

	public String getSave_file() {
		return save_file;
	}

	public void setSave_file(String save_file) {
		this.save_file = save_file;
	}

	@Override
	public String toString() {
		return "Board [seq=" + seq + ", userid=" + getUserid() + ", subject=" + getSubject() + ", content=" + getContent() + ", hit="
				+ getHit() + ", logtime=" + getLogtime() + ", classify=" + getClassify() + ", del=" + getDel() + ", notice=" + getNotice()
				+ ", bcode=" + getBcode() + "]" + "Album [aseq=" + aseq + ", seq=" + seq + ", orign_file=" + orign_file + ", save_file=" + save_file + "]";
	}

}
