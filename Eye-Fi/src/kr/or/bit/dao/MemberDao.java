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

import kr.or.bit.dto.member.Active;
import kr.or.bit.dto.member.Member;
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
			String sql_login = "select userid, email, admin, code from member where userid=? and userpw=?";
			pstmt = conn.prepareStatement(sql_login);
			
			//System.out.println(userid + "/" + userpw);
			pstmt.setString(1, userid);
			pstmt.setString(2, userpw);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // ID와 PW가 모두 맞았다
				member = new Member();
				member.setUserid(rs.getString("userid"));
				member.setEmail(rs.getString("email"));
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

			sql_selectAll ="select * from (select rownum rn, m.userid, m.userpw, m.email, m.admin, m.code, a.STATUS from member m join active a on m.CODE = a.CODE where rownum <= ?) where rn >= ?";
		
			pstmt = conn.prepareStatement(sql_selectAll);
		
			int start = cp * 4 - (4-1); //1 * 5 - (5 - 1) >> 1
			int end = cp * 4; // 1 * 5 >> 5
				
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);
						
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
	
	//회원 admin 조회
	public List<Member> selectAdmin(int cp, String admin){
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Member> memberList = new ArrayList<Member>();
		
		try {
			String sql_selectAll = "";

			sql_selectAll ="select * from (select rownum rn, m.userid, m.userpw, m.email, m.admin, m.code, a.STATUS from member m join active a on m.CODE = a.CODE where admin = ? and rownum <= ?) where rn >= ?";
			
			pstmt = conn.prepareStatement(sql_selectAll);
			
			pstmt.setString(1, admin);
			int start = cp * 4 - (4-1); //1 * 5 - (5 - 1) >> 1
			int end = cp * 4; // 1 * 5 >> 5
				
			pstmt.setInt(2, end);
			pstmt.setInt(3, start);
						
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
	
	//회원 id,email 조회
	public List<Member> selectIdEmail(int cp, String search){
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Member> memberList = new ArrayList<Member>();
		
		try {
			String sql_selectAll = "";

			sql_selectAll ="select * from (select rownum rn, m.userid, m.userpw, m.email, m.admin, m.code, a.STATUS from member m join active a on m.CODE = a.CODE where rownum <= ? and userid like ? or email like ?) where rn >= ?";
			
			pstmt = conn.prepareStatement(sql_selectAll);
			pstmt.setString(2, "%"+search+"%");
			pstmt.setString(3, "%"+search+"%");
			
			int start = cp * 4 - (4-1); //1 * 5 - (5 - 1) >> 1
			int end = cp * 4; // 1 * 5 >> 5
				
			pstmt.setInt(1, end);
			pstmt.setInt(4, start);
						
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
	
	//회원 code 조회
	public List<Member> selectCode(int cp, String code){
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Member> memberList = new ArrayList<Member>();
		
		try {
			String sql_selectAll = "";

			sql_selectAll ="select * from (select rownum rn, m.userid, m.userpw, m.email, m.admin, m.code, a.STATUS from member m join active a on m.CODE = a.CODE where m.code = ? and rownum <= ?) where rn >= ?";
			
			pstmt = conn.prepareStatement(sql_selectAll);
			pstmt.setString(1, code);
			
			int start = cp * 4 - (4-1); //1 * 5 - (5 - 1) >> 1
			int end = cp * 4; // 1 * 5 >> 5
				
			pstmt.setInt(2, end);
			pstmt.setInt(3, start);
						
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
	
	//총 회원수 조회
	public int memberTotalCount() {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalcount = 0;
		
		try {
			String sql_count = "select count(userid) from member";
			pstmt = conn.prepareStatement(sql_count);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				totalcount = rs.getInt(1);
			}
		} catch(Exception e) {
			System.out.println("membetTotalCount : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}
		return totalcount;
	}
	
	//admin 회원수 조회
	public int memberAdminCount(String admin) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalcount = 0;
		
		try {
			String sql_count = "select count(userid) from member where admin=?";
			pstmt = conn.prepareStatement(sql_count);
			pstmt.setString(1, admin);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				totalcount = rs.getInt(1);
			}
		} catch(Exception e) {
			System.out.println("membetTotalCount : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}
		return totalcount;
	}
	
	//id, email 회원수 조회
	public int memberIdEmailCount(String search) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalcount = 0;
		
		try {
			String sql_count = "select count(userid) from member where userid like ? or email like ?";
			pstmt = conn.prepareStatement(sql_count);
			//System.out.println(search);
			
			pstmt.setString(1, "%"+search+"%");
			pstmt.setString(2, "%"+search+"%");
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				totalcount = rs.getInt(1);
				//System.out.println(totalcount);
			}
		} catch(Exception e) {
			System.out.println("membetTotalCount : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}
		return totalcount;
	}
	
	//code 회원수 조회
	public int memberCodeCount(String code) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalcount = 0;
		
		try {
			String sql_count = "select count(userid) from member where code=?";
			pstmt = conn.prepareStatement(sql_count);
			pstmt.setString(1, code);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				totalcount = rs.getInt(1);
			}
		} catch(Exception e) {
			System.out.println("membetTotalCount : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}
		return totalcount;
	}
	
	//id 조회
	public List<Member> memberSelectId(String userid) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Member> memberList = new ArrayList<Member>();
		
		try {
			String sql_id = "select m.userid, m.userpw, m.email, m.admin, m.code, a.STATUS from member m join active a on m.CODE = a.CODE where m.userid like ?";
			pstmt = conn.prepareStatement(sql_id);
			
			pstmt.setString(1, "%" + userid + "%");
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Member member = new Member();
				member.setUserid(rs.getString("userid"));
				member.setUserpw(rs.getString("userpw"));
				member.setEmail(rs.getString("email"));
				member.setAdmin(rs.getInt("admin"));
				member.setCode(rs.getInt("code"));
				member.setStatus(rs.getString("status"));
				
				memberList.add(member);
			}
		} catch (Exception e) {
			System.out.println("ID 조회 : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}
		return memberList;
	}
	
	//활성화 코드 조회
	public List<Active> code() {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Active> codeList = new ArrayList<Active>();

		try {
			String sql_code = "select code, status from ACTIVE";
			pstmt = conn.prepareStatement(sql_code);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Active code = new Active();
				code.setCode(rs.getInt("code"));
				code.setStatus(rs.getString("status"));
				
				codeList.add(code);
			}
			
		} catch(Exception e) {
			System.out.println("avtice : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}
		return codeList;
	}
	
	//관리자 정보수정
	public int adminMemberEdit(Member member) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			String sql_edit = "update member set userpw=?, email=?, admin=?, code=? where userid=?";
			pstmt = conn.prepareStatement(sql_edit);
				
			pstmt.setString(1, member.getUserpw());
			pstmt.setString(2, member.getEmail());
			pstmt.setInt(3, member.getAdmin());
			pstmt.setInt(4, member.getCode());
			pstmt.setString(5, member.getUserid());
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("adminupdate : " + e.getMessage());
		}finally {
			DB_Close.close(pstmt);
			DB_Close.close(conn);
		}
		return result;
	}
	
	//회원 탈퇴
	public int memberDel(String userid) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			String sql_edit = "update member set code=3 where userid=?";
			pstmt = conn.prepareStatement(sql_edit);
			
			pstmt.setString(1, userid);
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("memberDel : " + e.getMessage());
		}finally {
			DB_Close.close(pstmt);
			DB_Close.close(conn);
		}
		return result;
	}
}
