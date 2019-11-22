package kr.or.bit.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import kr.or.bit.dto.board.Board;
import kr.or.bit.dto.board.Board_List;
import kr.or.bit.dto.board.Board_Type;
import kr.or.bit.dto.board.Category;
import kr.or.bit.dto.member.Member;
import kr.or.bit.utils.ConnectionHelper;
import kr.or.bit.utils.DB_Close;

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
public class BoardDao {
	//게시판 형식조회
	public List<Board_Type> btypeSel() {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board_Type> typeList = new ArrayList<Board_Type>();
		
		try {
			String sql_btype = "select btype, btype_name from board_type";
			pstmt = conn.prepareStatement(sql_btype);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Board_Type type = new Board_Type();
				type.setBtype(rs.getInt("btype"));
				type.setBtype_name(rs.getString("btype_name"));
				
				typeList.add(type);
			}
		} catch(Exception e) {
			System.out.println("btype sel : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}
		return typeList;
	}

	//게시판 카테고리 조회
	public List<Category> cateSel() {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Category> cateList = new ArrayList<Category>();
		
		try {
			String sql_cate = "select ccode, cname from category";
			pstmt = conn.prepareStatement(sql_cate);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Category cate = new Category();
				cate.setCcode(rs.getInt("ccode"));
				cate.setCname(rs.getString("cname"));
				
				cateList.add(cate);
			}
		} catch(Exception e) {
			System.out.println("cate sel : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}
		return cateList;
	}
	
	//게시판종류 리스트 조회
	public List<Board_List> listSel(){
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board_List> boardList = new ArrayList<Board_List>();
		
		try {
			String sql = "select bcode, bname, btype, ccode from board_list";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Board_List board = new Board_List();
				board.setBcode(rs.getInt("bcode"));
				board.setBname(rs.getString("bname"));
				board.setBtype(rs.getInt("btype"));
				board.setCcode(rs.getInt("ccode"));
				
				boardList.add(board);
			}
		} catch(Exception e) {
			System.out.println("boardList sel : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}
		return boardList;
	}

	//게시판 글쓰기
	public int write(Board board) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		int row = 0;
		
		try {
			String sql_write = "insert into board (seq, userid, subject, content, classify, notice, bcode) "
								+ "values (board_seq.nextval, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql_write);
			
			pstmt.setString(1, board.getUserid());
			pstmt.setString(2, board.getSubject());
			pstmt.setString(3, board.getContent());
			pstmt.setString(4, board.getClassify());
			pstmt.setString(5, board.getNotice());
			pstmt.setInt(6, board.getBcode());
			
			row = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("write : " + e.getMessage());
		} finally{
			DB_Close.close(pstmt);
			DB_Close.close(conn);
		}
		return row;
	}
	
	//공지게시판 리스트 조회
	public List<Board> noticeboardList(int cp, int bcode) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> boardlist = new ArrayList<Board>();
		
		try {
			String sql_selectAll = "select * from (select rownum rn, seq, userid, subject, content, hit, logtime, classify, del, notice, bcode from ( SELECT * FROM board ORDER BY logtime DESC ) where bcode = ? and del=0 and rownum <= ?) where rn >= ?";
			
			pstmt = conn.prepareStatement(sql_selectAll);
			
			pstmt.setInt(1, bcode);
			if ( cp != -1) {
				int start = cp * 4 - (4-1); //1 * 5 - (5 - 1) >> 1
				int end = cp * 4; // 1 * 5 >> 5
				
				pstmt.setInt(2, end);
				pstmt.setInt(3, start);
			}			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Board board = new Board();
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
	            java.util.Date date = sdf.parse(rs.getString("logtime"));
	            Date logtime = new Date(date.getTime());
				
				
				board.setSeq(rs.getInt("seq"));
				board.setUserid(rs.getString("userid"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setHit(rs.getInt("hit"));
				board.setLogtime(logtime);
				board.setClassify(rs.getString("classify"));
				board.setDel(rs.getString("del"));
				board.setNotice(rs.getString("notice"));
				board.setBcode(rs.getInt("bcode"));
				
				boardlist.add(board);
			}
			
		} catch(Exception e) {
			System.out.println("selectAll :" + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}
		return boardlist;
	}

	//게시판 총 건수 조회
	public int  boardTotalCount(int bcode) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalcount = 0;
		
		try {
			String sql_count = "select count(seq) from board where bcode = ?";
			pstmt = conn.prepareStatement(sql_count);
			pstmt.setInt(1, bcode);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				totalcount = rs.getInt(1);
			}
		} catch(Exception e) {
			System.out.println("boardTotalCount : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}
		return totalcount;
	}
	
	//글상세보기
	public Board boardContent(int seq) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Board board = null;
		
		try {
			String sql = "select * from board where seq=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, seq);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				board = new Board();
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
	            java.util.Date date = sdf.parse(rs.getString("logtime"));
	            Date logtime = new Date(date.getTime());
				
				board.setSeq(rs.getInt("seq"));
				board.setUserid(rs.getString("userid"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setHit(rs.getInt("hit"));
				board.setLogtime(logtime);
				board.setClassify(rs.getString("classify"));
				board.setDel(rs.getString("del"));
				board.setNotice(rs.getString("notice"));
				board.setBcode(rs.getInt("bcode"));
			}
			
		}catch (Exception e) {
			System.out.println("content : " + e.getMessage());
		}finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}
		return board;
	}

	//조회수 증가
	public boolean hitAdd(String seq) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		boolean result = false;
		
		try {
			String sql="update board set hit = hit + 1 where seq=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, seq);
			
			int row = pstmt.executeUpdate();
			
			if(row > 0 ) {
				result = true;
			}
					
		} catch (Exception e) {
			System.out.println("hitAdd : " + e.getMessage());
		}finally {
			DB_Close.close(pstmt);
			DB_Close.close(conn);	
		}
		return result;
	}
	
}
