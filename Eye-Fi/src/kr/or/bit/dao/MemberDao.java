package kr.or.bit.dao;
/*
DB작업
CRUD 작업을 하기위한 함수를 생성하는 곳

memo table 에 데이터 에 대해서
전제조회 : select id, email ,content from memo
조건조회 : select id, email ,content from memo where id=?
수정 : update memo set email=? , content=? where id=?
삭제 : delete from memo where id=?
삽입 : insert into memo(id,email,content) values(?,?,?)

default 5개
필요하시면 함수는 추가 .....^^
*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.or.bit.dto.Member;
import kr.or.bit.utils.ConnectionHelper;
import kr.or.bit.utils.DB_Close;

public class MemberDao {
	//ID중복체크
	public String idCheck(String userid) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String result = "true";
		
		try {
			String sql_id = "select count(userid) from member where userid=?";
			pstmt = conn.prepareStatement(sql_id);
			
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				if(rs.getInt(1) > 0) {//아이디가 있으면
					result="false";
				}
			}
			
		}catch(Exception e) {
			System.out.println("IdCheck : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}
		return result;
	}
	
	//회원가입
	public int join(Member member) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		int row = 0;
		
		try {
			String sql_join = "insert into member (userid, userpw, email) values(?, ?, ?)";
			pstmt = conn.prepareStatement(sql_join);
			
			pstmt.setString(1, member.getUserid());
			pstmt.setString(2, member.getUserpw());
			pstmt.setString(3, member.getEmail());
			
			row = pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("join : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(conn);
		}
		return row;
	}
	
	//로그인
	public Member login(String userid, String userpw) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member member = null;
		
		try {
			String sql_login = "select userid, admin, code from member where userid=? and userpw=?";
			pstmt = conn.prepareStatement(sql_login);
			
			System.out.println(userid + "/" + userpw);
			pstmt.setString(1, userid);
			pstmt.setString(2, userpw);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // ID와 PW가 모두 맞았다
				member = new Member();
				member.setUserid(rs.getString("userid"));
				member.setAdmin(rs.getInt("admin"));
				member.setCode(rs.getInt("code"));
			}
			
		}catch(Exception e) {
			System.out.println("login : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}
		return member;
	}

	//회원 1명 조회
	public Member select(String userid) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Member member = null;
		
		try {
			String sql_select = "select m.userid, m.userpw, m.email, m.admin, m.code, a.STATUS from member m join active a on m.CODE = a.CODE where userid=?";
			pstmt = conn.prepareStatement(sql_select);
			
			pstmt.setString(1, userid);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				member = new Member();
				
				member.setUserid(rs.getString("userid"));
				member.setUserpw(rs.getString("userpw"));
				member.setEmail(rs.getString("email"));
				member.setAdmin(rs.getInt("admin"));
				member.setCode(rs.getInt("code"));
				member.setStatus(rs.getString("status"));
				//System.out.println(member);
			}
			
		}catch(Exception e) {
			System.out.println("select 1건 : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}
		return member;
	}
	
	//정보수정
	public int edit(Member member) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			String sql_edit = "update member set userpw=? where userid=?";
			pstmt = conn.prepareStatement(sql_edit);
			
			pstmt.setString(1, member.getUserpw());
			pstmt.setString(2, member.getUserid());
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("update : " + e.getMessage());
		}finally {
			DB_Close.close(pstmt);
			DB_Close.close(conn);
		}
		return result;
	}
	
	//회원 전체조회
	public List<Member> selectAll(int cp) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Member> memberList = new ArrayList<Member>();
		
		try {
			String sql_selectAll = "";
			
			if(cp == -1) {
				sql_selectAll = "select m.userid, m.userpw, m.email, m.admin, m.code, a.STATUS from member m join active a on m.CODE = a.CODE";
			 } else {
				 sql_selectAll ="select * from (select rownum rn, m.userid, m.userpw, m.email, m.admin, m.code, a.STATUS from member m join active a on m.CODE = a.CODE where rownum <= ?) where rn >= ?";
			 }
			
			pstmt = conn.prepareStatement(sql_selectAll);
			
			if ( cp != -1) {
				int start = cp * 4 - (4-1); //1 * 5 - (5 - 1) >> 1
				int end = cp * 4; // 1 * 5 >> 5
				
				pstmt.setInt(1, end);
				pstmt.setInt(2, start);
			}			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Member member = new Member();
				member.setUserid(rs.getString("userid"));
				member.setUserpw(rs.getString("userpw"));
				member.setEmail(rs.getString("email"));
				member.setAdmin(rs.getInt("admin"));
				member.setCode(rs.getInt("code"));
				member.setStatus(rs.getString("status"));
				
				memberList.add(member);
			}
			
		} catch(Exception e) {
			System.out.println("selectAll :" + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}
		return memberList;
	}
}
