package kr.or.bit.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import kr.or.bit.dto.board.Album;
import kr.or.bit.dto.board.Board;
import kr.or.bit.dto.board.Board_List;
import kr.or.bit.dto.board.Board_Type;
import kr.or.bit.dto.board.Category;
import kr.or.bit.dto.board.Reboard;
import kr.or.bit.dto.board.Reply;
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
			String sql_cate = "select ccode, cname from category order by ccode";
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
	
	//btype 조회
	public int btypeSel(int bcode) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int btype = 0;
		
		try {
			String sql = "select btype from board_list where bcode = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bcode);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				btype = rs.getInt("btype");
			}
		} catch(Exception e) {
			System.out.println("btype : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}
		return btype;
	}
	
	//일반게시판 글쓰기
	public int noticeWrite(Board board) {
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
			System.out.println("noticewrite : " + e.getMessage());
		} finally{
			DB_Close.close(pstmt);
			DB_Close.close(conn);
		}
		return row;
	}
	
	//일반,계층형 게시판 글수정
	public int noticeEditOk(Board board) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;

		int row = 0;
		
		try {			
			String sql_edit = "update board set subject=?, content=?, notice=?, bcode=? where seq = ?";
						
			pstmt = conn.prepareStatement(sql_edit);
			
			pstmt.setString(1, board.getSubject());
			pstmt.setString(2, board.getContent());
			pstmt.setString(3, board.getNotice());
			pstmt.setInt(4, board.getBcode());
			pstmt.setInt(5, board.getSeq());

			row = pstmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("noticeedit : " + e.getMessage());
		} finally{
			DB_Close.close(pstmt);
			DB_Close.close(conn);
		}
		return row;
	}
	
	//계층형게시판 글쓰기
	public int write(Reboard reboard) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		int row = 0;
		
		try {
			int refer = getMaxRefer() +1;
			
			String sql_write = "insert all "
					+"into board (seq, userid, subject, content, classify, notice, bcode) "
					+"values (board_seq.nextval, ?, ?, ?, ?, ?, ?) "
					+"into reboard(col, seq, refer, depth, step, pseq) "
					+"values (reboard_seq.nextval, board_seq.currval, ?, 0, 0, board_seq.currval) "
					+"SELECT * FROM DUAL";
			
			pstmt = conn.prepareStatement(sql_write);
			
			pstmt.setString(1, reboard.getUserid());
			pstmt.setString(2, reboard.getSubject());
			pstmt.setString(3, reboard.getContent());
			pstmt.setString(4, reboard.getClassify());
			pstmt.setString(5, reboard.getNotice());
			pstmt.setInt(6, reboard.getBcode());
			
			pstmt.setInt(7, refer);

			row = pstmt.executeUpdate();
		}catch(Exception e) {
			System.out.println("write : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(conn);
		}
		return row;
	}
	
	//앨범게시판 글쓰기
	public int albumwrite(Album album) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		int row = 0;
		
		try {
			String sql_write = "insert all "
					+"into board (seq, userid, subject, content, classify, notice, bcode) "
					+"values (board_seq.nextval, ?, ?, ?, ?, ?, ?) "
					+"into album(aseq, seq, orign_file, save_file) "
					+"values (album_seq.nextval, board_seq.currval, ?, ?) "
					+"SELECT * FROM DUAL";
			
			pstmt = conn.prepareStatement(sql_write);
			
			pstmt.setString(1, album.getUserid());
			pstmt.setString(2, album.getSubject());
			pstmt.setString(3, album.getContent());
			pstmt.setString(4, album.getClassify());
			pstmt.setString(5, album.getNotice());
			pstmt.setInt(6, album.getBcode());
			
			pstmt.setString(7, album.getOrign_file());
			pstmt.setString(8, album.getSave_file());
			
			row = pstmt.executeUpdate();
		}catch(Exception e) {
			System.out.println("albumwrite : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(conn);
		}
		return row;
	}
	
	//앨범게시판 글수정
	public int albumEditOk(Album album) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;

		int row = 0;
		
		try {			
			conn.setAutoCommit(false); //트랜잭션 : commit 수동처리..
			
			String sql_boardedit = "update board set subject=?, content=?, notice=?, bcode=? where seq = ?";
			pstmt = conn.prepareStatement(sql_boardedit);
			
			pstmt.setString(1, album.getSubject());
			pstmt.setString(2, album.getContent());
			pstmt.setString(3, album.getNotice());
			pstmt.setInt(4, album.getBcode());
			pstmt.setInt(5, album.getSeq());

			row = pstmt.executeUpdate();
			
			String sql_albumedit = "update album set orign_file =?, save_file =? where seq = ?";
			pstmt = conn.prepareStatement(sql_albumedit);
			
			pstmt.setString(1, album.getOrign_file());
			pstmt.setString(2, album.getSave_file());
			pstmt.setInt(3, album.getSeq());
			
			row = pstmt.executeUpdate();
			
			if(row > 0 ) {
				conn.commit(); //2개의 insert 완료시
			}
		} catch(Exception e) {
			System.out.println("noticeedit : " + e.getMessage());
		} finally{
			DB_Close.close(pstmt);
			DB_Close.close(conn);
		}
		return row;
		
	}
	
	//refer 생성하기
	private int getMaxRefer() {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int refer_max = 0;
		
		try {
			String sql = "select nvl(max(refer),0) from reboard";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				refer_max = rs.getInt(1);
			}
		}
		catch(Exception e) {
			System.out.println("maxrefer : " + e.getMessage());
		}finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}
		return refer_max;
	}
		
	//답글쓰기
	public int reWrite(Reboard reboard) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int row = 0;
		
		try {
			int refer = getMaxRefer();
			int step = 1;
			int depth = 1;
			
			//읽은글의 최신답글 찾기..
			String sql_seq = "select refer, depth, step from reboard where seq = ? order by col desc";
			pstmt = conn.prepareStatement(sql_seq);
			pstmt.setInt(1, reboard.getPseq());
			rs = pstmt.executeQuery();
		
			if(rs.next()) {
				refer = rs.getInt("refer");
				step = rs.getInt("step");
				depth = rs.getInt("depth");
				
				//나중에 쓴 답글이 위로 올라오게 step +1
				String sql_step_update = "update reboard set step = step+1 where step > ? and refer = ?";
				pstmt = conn.prepareStatement(sql_step_update);
				pstmt.setInt(1, step);
				pstmt.setInt(2, refer);
				pstmt.executeUpdate();
			}
			
			String sql_rewrite = "insert all "
					+"into board (seq, userid, subject, content, classify, notice, bcode) "
					+"values (board_seq.nextval, ?, ?, ?, ?, ?, ?) "
					+"into reboard(col, seq, refer, depth, step, pseq) "
					+"values (reboard_seq.nextval, board_seq.currval, ?, ?, ?, ?) "
					+"SELECT * FROM DUAL";
			
			pstmt = conn.prepareStatement(sql_rewrite);
			pstmt.setString(1, reboard.getUserid());
			pstmt.setString(2, reboard.getSubject());
			pstmt.setString(3, reboard.getContent());
			pstmt.setString(4, reboard.getClassify());
			pstmt.setString(5, reboard.getNotice());
			pstmt.setInt(6, reboard.getBcode());
			
			pstmt.setInt(7, refer);
			pstmt.setInt(8, depth+1); //현재 읽은 글의 depth +1
			pstmt.setInt(9, step+1); //순서 update 통해서 자리확보 +1
			
			pstmt.setInt(10, reboard.getPseq());
			
			row = pstmt.executeUpdate();
			
		} catch(Exception e) {
			System.out.println("reWrite : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}		
		return row;
	}
	
	//일반게시판 리스트 조회
	public List<Board> noticeboardList(int cp, int bcode) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> boardlist = new ArrayList<Board>();
		
		try {
			String sql_selectAll = "select * from (select rownum rn, seq, userid, subject, content, hit, logtime, classify, del, notice, bcode from ( SELECT * FROM board ORDER BY logtime DESC ) where bcode = ? and del=0 and rownum <= ?) where rn >= ?";
			
			pstmt = conn.prepareStatement(sql_selectAll);
			
			pstmt.setInt(1, bcode);

			int start = cp * 4 - (4-1); //1 * 5 - (5 - 1) >> 1
			int end = cp * 4; // 1 * 5 >> 5
				
			pstmt.setInt(2, end);
			pstmt.setInt(3, start);
						
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

	//일반게시판 어린이집 선택조회
	public List<Board> noticeclassifyboardList(int cp, int bcode, String classify){
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> boardlist = new ArrayList<Board>();
		
		try {
			String sql_selectAll = "select * from (select rownum rn, seq, userid, subject, content, hit, logtime, classify, del, notice, bcode from ( SELECT * FROM board ORDER BY logtime DESC ) where bcode = ? and del=0 and classify=? and rownum <= ?) where rn >= ?";
			
			pstmt = conn.prepareStatement(sql_selectAll);
			
			pstmt.setInt(1, bcode);
			pstmt.setString(2, classify);
			
			int start = cp * 4 - (4-1); //1 * 5 - (5 - 1) >> 1
			int end = cp * 4; // 1 * 5 >> 5
				
			pstmt.setInt(3, end);
			pstmt.setInt(4, start);
						
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

	//계층형게시판 리스트 조회
	public List<Reboard> boardList(int cp, int bcode){
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Reboard> reboardList = new ArrayList<Reboard>();
		
		try {
			
			String sql = "select * from (select rownum rn, a.* "
										+ "from (SELECT * FROM board b join reboard r on b.seq = r.seq ORDER BY refer DESC , step ASC) a "
										+" where bcode = ? and del=0 and rownum <= ?)" 
						+" where rn >= ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, bcode);
			
			int start = cp * 4 - (4-1); //1 * 5 - (5 - 1) >> 1
			int end = cp * 4; // 1 * 5 >> 5
				
			pstmt.setInt(2, end);
			pstmt.setInt(3, start);
		
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Reboard reboard = new Reboard();
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
	            java.util.Date date = sdf.parse(rs.getString("logtime"));
	            Date logtime = new Date(date.getTime());
				
				
				reboard.setSeq(rs.getInt("seq"));
				reboard.setUserid(rs.getString("userid"));
				reboard.setSubject(rs.getString("subject"));
				reboard.setContent(rs.getString("content"));
				reboard.setHit(rs.getInt("hit"));
				reboard.setLogtime(logtime);
				reboard.setClassify(rs.getString("classify"));
				reboard.setDel(rs.getString("del"));
				reboard.setNotice(rs.getString("notice"));
				reboard.setBcode(rs.getInt("bcode"));
				
				reboard.setCOL(rs.getInt("cOL"));
				reboard.setSeq(rs.getInt("seq"));
				reboard.setRefer(rs.getInt("refer"));
				reboard.setDepth(rs.getInt("depth"));
				reboard.setStep(rs.getInt("step"));
				reboard.setRebdel(rs.getInt("rebdel"));
				reboard.setPseq(rs.getInt("pseq"));
				
				reboardList.add(reboard);
			}
			//System.out.println("rebo : " + reboardList);
			
		} catch(Exception e) {
			System.out.println("boardlist : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}
		
		return reboardList;
	}	

	//계층형게시판 어린이집 선택 조회
	public List<Reboard> classifyboardList(int cp, int bcode, String classify){
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Reboard> reboardList = new ArrayList<Reboard>();
		
		try {
			
			String sql = "select * from (select rownum rn, a.* "
										+ "from (SELECT * FROM board b join reboard r on b.seq = r.seq ORDER BY refer DESC , step ASC) a "
										+" where bcode = ? and del=0 and classify=? and rownum <= ?)" 
						+" where rn >= ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, bcode);
			pstmt.setString(2, classify);
			
			int start = cp * 4 - (4-1); //1 * 5 - (5 - 1) >> 1
			int end = cp * 4; // 1 * 5 >> 5
				
			pstmt.setInt(3, end);
			pstmt.setInt(4, start);
		
			rs = pstmt.executeQuery();
			
			
			while(rs.next()){
				Reboard reboard = new Reboard();
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
	            java.util.Date date = sdf.parse(rs.getString("logtime"));
	            Date logtime = new Date(date.getTime());
				
				
				reboard.setSeq(rs.getInt("seq"));
				reboard.setUserid(rs.getString("userid"));
				reboard.setSubject(rs.getString("subject"));
				reboard.setContent(rs.getString("content"));
				reboard.setHit(rs.getInt("hit"));
				reboard.setLogtime(logtime);
				reboard.setClassify(rs.getString("classify"));
				reboard.setDel(rs.getString("del"));
				reboard.setNotice(rs.getString("notice"));
				reboard.setBcode(rs.getInt("bcode"));
				
				reboard.setCOL(rs.getInt("cOL"));
				reboard.setSeq(rs.getInt("seq"));
				reboard.setRefer(rs.getInt("refer"));
				reboard.setDepth(rs.getInt("depth"));
				reboard.setStep(rs.getInt("step"));
				reboard.setRebdel(rs.getInt("rebdel"));
				reboard.setPseq(rs.getInt("pseq"));
				
				reboardList.add(reboard);
			}
			//System.out.println("rebo : " + reboardList);
			
		} catch(Exception e) {
			System.out.println("boardlist : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}
		
		return reboardList;
	}
	
	//앨범 게시판 리스트 조회
	public List<Album> albumboardList(int cp, int bcode){
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Album> albumlist = new ArrayList<Album>();
		
		try {
			String sql = "select * from (select rownum rn, a.* from (SELECT * FROM board b join album a on b.seq = a.seq ORDER BY logtime DESC) a where bcode = ? and del=0 and rownum <= ?) where rn >= ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bcode);
			
			int start = cp * 4 - (4-1); //1 * 5 - (5 - 1) >> 1
			int end = cp * 4; // 1 * 5 >> 5
				
			pstmt.setInt(2, end);
			pstmt.setInt(3, start);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Album album = new Album();
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
	            java.util.Date date = sdf.parse(rs.getString("logtime"));
	            Date logtime = new Date(date.getTime());
				
	            album.setSeq(rs.getInt("seq"));
	            album.setUserid(rs.getString("userid"));
	            album.setSubject(rs.getString("subject"));
	            album.setContent(rs.getString("content"));
	            album.setHit(rs.getInt("hit"));
	            album.setLogtime(logtime);
	            album.setClassify(rs.getString("classify"));
	            album.setDel(rs.getString("del"));
	            album.setNotice(rs.getString("notice"));
	            album.setBcode(rs.getInt("bcode"));
	            
	            album.setOrign_file(rs.getString("orign_file"));
	            album.setSave_file(rs.getString("save_file"));
	            
	            albumlist.add(album);
			}
			
		}catch (Exception e) {
			System.out.println("content : " + e.getMessage());
		}finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}
		return albumlist;
	}
	
	//앨범게시판 어린이집 선택 조회
	public List<Album> classifyalbumboardList(int cp, int bcode, String classify){
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Album> albumlist = new ArrayList<Album>();
		
		try {
			String sql = "select * from (select rownum rn, a.* from (SELECT * FROM board b join album a on b.seq = a.seq ORDER BY logtime DESC) a where bcode = ? and del=0 and classify=? and rownum <= ?) where rn >= ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bcode);
			pstmt.setString(2, classify);
			
			int start = cp * 4 - (4-1); //1 * 5 - (5 - 1) >> 1
			int end = cp * 4; // 1 * 5 >> 5
				
			pstmt.setInt(3, end);
			pstmt.setInt(4, start);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Album album = new Album();
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
	            java.util.Date date = sdf.parse(rs.getString("logtime"));
	            Date logtime = new Date(date.getTime());
				
	            album.setSeq(rs.getInt("seq"));
	            album.setUserid(rs.getString("userid"));
	            album.setSubject(rs.getString("subject"));
	            album.setContent(rs.getString("content"));
	            album.setHit(rs.getInt("hit"));
	            album.setLogtime(logtime);
	            album.setClassify(rs.getString("classify"));
	            album.setDel(rs.getString("del"));
	            album.setNotice(rs.getString("notice"));
	            album.setBcode(rs.getInt("bcode"));
	            
	            album.setOrign_file(rs.getString("orign_file"));
	            album.setSave_file(rs.getString("save_file"));
	            
	            albumlist.add(album);
			}
			
		}catch (Exception e) {
			System.out.println("content : " + e.getMessage());
		}finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}
		return albumlist;
	}
	
	//게시판 총 건수 조회
	public int boardTotalCount(int bcode) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalcount = 0;
		
		try {
			String sql_count = "select count(seq) from board where bcode = ? and del=0";
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
	
	//게시판 어린이집 건수 조회
	public int boardclassifyCount(int bcode, String classify) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalcount = 0;
		
		try {
			String sql_count = "select count(seq) from board where bcode=? and classify = ? and del=0";
			pstmt = conn.prepareStatement(sql_count);
			pstmt.setInt(1, bcode);
			pstmt.setString(2, classify);
			
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
	
	//앨범 상세보기
	public Album albumContent(int seq) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Album album = null;
		
		try {
			String sql = "select b.seq, b.userid, b.subject, b.content, b.hit, b.logtime, b.classify, b.del, b.notice, b.bcode, a.aseq, a.seq, a.orign_file, a.save_file from board b join album a on b.seq = a.seq where b.seq=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, seq);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				album = new Album();
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
	            java.util.Date date = sdf.parse(rs.getString("logtime"));
	            Date logtime = new Date(date.getTime());
				
	            album.setSeq(rs.getInt("seq"));
	            album.setUserid(rs.getString("userid"));
	            album.setSubject(rs.getString("subject"));
	            album.setContent(rs.getString("content"));
	            album.setHit(rs.getInt("hit"));
	            album.setLogtime(logtime);
	            album.setClassify(rs.getString("classify"));
	            album.setDel(rs.getString("del"));
	            album.setNotice(rs.getString("notice"));
	            album.setBcode(rs.getInt("bcode"));
	            
	            album.setOrign_file(rs.getString("orign_file"));
	            album.setSave_file(rs.getString("save_file"));
			}
			
		}catch (Exception e) {
			System.out.println("content : " + e.getMessage());
		}finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}
		return album;
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
	
	//글 삭제 
	public int boardDeleteOk(String seq) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int row = 0;
		
		try {
			String sql_del = "update board set del=1 where seq = ?";
			pstmt = conn.prepareStatement(sql_del);
			
			pstmt.setString(1, seq);
			
			row = pstmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("boarddelete : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}
		
		return row;
	}

	//게시판 추가
	public int boardCreate(Board_List boardlist, Category cate) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int row = 0;
		
		try {
			conn.setAutoCommit(false); //트랜잭션 : commit 수동처리..
			
			String sql_catesel = "select ccode from category where ccode = ?";
			pstmt = conn.prepareStatement(sql_catesel);
			pstmt.setInt(1, cate.getCcode());
			rs = pstmt.executeQuery();
			
			if(!rs.next()) {
				String sql_cate = "insert into category(ccode, cname) values(?, ?)";
				pstmt = conn.prepareStatement(sql_cate);
				pstmt.setInt(1, cate.getCcode());
				pstmt.setString(2, cate.getCname());
				
				row = pstmt.executeUpdate();
			}
			
			String sql_boardlist = "insert into board_list(bcode, bname, btype, ccode) values(blist_seq.nextval, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql_boardlist);
			
			pstmt.setString(1, boardlist.getBname());
			pstmt.setInt(2, boardlist.getBtype());
			pstmt.setInt(3, boardlist.getCcode());
			
			row = pstmt.executeUpdate();
			
			if(row > 0 ) {
				conn.commit(); //2개의 insert 완료시
			}
			
		} catch(Exception e) {
			System.out.println("boardlist : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}
		return row;
	}
	
	//댓글쓰기
	public int replyWrite(Reply reply) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		int row = 0;
		
		try {			
			String sql_reply = "insert into reply(rseq, seq, rcontent, reuserid) values(reply_seq.nextval,?,?,?)";

			pstmt = conn.prepareStatement(sql_reply);
			//System.out.println(reply);
			
			pstmt.setInt(1, reply.getSeq());
			pstmt.setString(2, reply.getRcontent());
			pstmt.setString(3, reply.getReuserid());
			
			row = pstmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("replyWrite : " + e.getMessage());
		} finally{
			DB_Close.close(pstmt);
			DB_Close.close(conn);
		}
		return row;
	}
	
	//댓글조회
	public List<Reply> replyList(String seq){
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Reply> replyList = new ArrayList<Reply>();
		
		try {			
			String sql_reply = "select rseq, seq, rcontent, reuserid, retime from reply where seq=? and redel=0 order by rseq desc";

			pstmt = conn.prepareStatement(sql_reply);
			//System.out.println(reply);
			
			pstmt.setString(1, seq);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Reply reply = new Reply();
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
	            java.util.Date date = sdf.parse(rs.getString("retime"));
	            Date retime = new Date(date.getTime());
				
				reply.setRseq(rs.getInt("rseq"));
				reply.setSeq(rs.getInt("seq"));
				reply.setRcontent(rs.getString("rcontent"));
				reply.setReuserid(rs.getString("reuserid"));
				reply.setRetime(retime);
				
				replyList.add(reply);
			}
		
		} catch(Exception e) {
			System.out.println("replyWrite : " + e.getMessage());
		} finally{
			DB_Close.close(pstmt);
			DB_Close.close(conn);
		}
		return replyList;	
	}
	
	//댓글삭제
	public int replyDelete(String rseq) {
		Connection conn = ConnectionHelper.getConnection("oracle");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int row = 0;
		
		try {
			String sql_del = "update reply set redel=1 where rseq = ?";
			pstmt = conn.prepareStatement(sql_del);
			
			pstmt.setString(1, rseq);
			
			row = pstmt.executeUpdate();
		} catch(Exception e) {
			System.out.println("replydelete : " + e.getMessage());
		} finally {
			DB_Close.close(pstmt);
			DB_Close.close(rs);
			DB_Close.close(conn);
		}
		return row;
	}
	
}
