package kr.or.bit.dto.board;

import java.text.SimpleDateFormat;

public class Board {
	private int seq; /* 글번호 */		
	private String	userid; /* 아이디 */		
	private String subject; /* 글제목 */		
	private String content; /* 글내용 */		
	private int hit; /* 조회수 */		
	private String logtime; /* 작성일 */		
	private String classify; /* 말머리 */		
	private String del; /* 삭제 */		
	private String notice; /* 공지 */		
	private int bcode; /* 게시판코드 */		
	
	public Board() {}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getLogtime() {
		return logtime;
	}

	public void setLogtime(java.sql.Date logtime) {
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		this.logtime = formatter.format(logtime);
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public String getDel() {
		return del;
	}

	public void setDel(String del) {
		this.del = del;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public int getBcode() {
		return bcode;
	}

	public void setBcode(int bcode) {
		this.bcode = bcode;
	}

	@Override
	public String toString() {
		return "Board [seq=" + seq + ", userid=" + userid + ", subject=" + subject + ", content=" + content + ", hit="
				+ hit + ", logtime=" + logtime + ", classify=" + classify + ", del=" + del + ", notice=" + notice
				+ ", bcode=" + bcode + "]";
	}

}
