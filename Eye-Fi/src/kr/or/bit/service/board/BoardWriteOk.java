package kr.or.bit.service.board;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dto.board.Album;
import kr.or.bit.dto.board.Board;
import kr.or.bit.dto.board.Reboard;

public class BoardWriteOk implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1. 데이터 받기
		MultipartRequest multi;
		
		ServletContext application = request.getServletContext();
		String imgpath = application.getRealPath("board_img"); //이미지 저장 실경로
		
		int size = 1024*1024*10; //10M 네이버 계산기

		String save_file=""; //저장되는 파일 명 변수
		String orign_file=""; //원 파일명 변수
		
		try {
			multi = new MultipartRequest(
					request, //기전에 있는  request 객체의 주소값 
					imgpath, //실 저장 경로 (배포경로)
					size, //10M
					"UTF-8",
					new DefaultFileRenamePolicy() //파일 중복(upload 폴더 안에:a.jpg -> a_1.jpg(업로드 파일 변경) )
			);
			
			String userid = multi.getParameter("userid");
			String subject = multi.getParameter("subject");
			String content = multi.getParameter("content");
			String classify = multi.getParameter("classify");
			String notice = multi.getParameter("notice");
			String bcode = multi.getParameter("bcode");
			String arcode = multi.getParameter("arcode");

			//2. 데이터 확인
			System.out.println(userid +"/"+ subject +"/"+ content +"/"+ classify +"/"+ notice +"/ bcode: "+ bcode);
			
			if( classify==null || classify.equals("")) { //|| classify.equals("") || classify == null
				classify = "전체";
			}
			
			if(notice == null || notice.equals("null")) {
				notice = "false";
			}

			//3. 처리
			BoardDao boarddao = new BoardDao();
			int btype = boarddao.btypeSel(Integer.parseInt(bcode));
			int result = 0;
			
			String msg = "";
			String url = "";
			
			if( btype == 1 ) {
				
				//공지게시판 글쓰기(댓X, 답X)
				Board board = new Board();
				board.setUserid(userid);
				board.setSubject(subject);
				board.setContent(content);
				board.setClassify(classify);
				board.setNotice(notice);
				board.setBcode(Integer.parseInt(bcode));
				//System.out.println(board);

				result = boarddao.noticeWrite(board);
				
			} else if( btype == 2 ) {
				Reboard reboard = new Reboard();
				reboard.setUserid(userid);
				reboard.setSubject(subject);
				reboard.setContent(content);
				reboard.setClassify(classify);
				reboard.setNotice(notice);
				reboard.setBcode(Integer.parseInt(bcode));
				//System.out.println(reboard);
				
				result = boarddao.write(reboard);
				
			} else if( btype == 3 ) {
				Enumeration filenames = multi.getFileNames();
				
				String file1 = (String)filenames.nextElement(); //파일명 : 뒤에서부터 읽어옴..
				save_file = multi.getFilesystemName(file1); //저장된 파일명
				orign_file = multi.getOriginalFileName(file1); //원 파일명
				
				//앨범게시판 글쓰기...
				Album album = new Album();
				album.setUserid(userid);
				album.setSubject(subject);
				album.setContent(content);
				album.setClassify(classify);
				album.setNotice(notice);
				album.setBcode(Integer.parseInt(bcode));
				
				album.setOrign_file(orign_file);
				album.setSave_file(save_file);
				
				result = boarddao.albumwrite(album);
			}
			
			if(result > 0 ) {
				msg = "글이 등록되었습니다.";
				url = "boardList.bdo?bcode="+bcode+"&arcode="+arcode+"&btype="+btype;
			}else {		
				msg = "글쓰기가 실패하였습니다. 다시 작성 부탁드립니다.";
				url = "boardWrite.bdo?bcode="+bcode+"&arcode="+arcode+"&btype="+btype;
			}
			
			request.setAttribute("msg", msg);
			request.setAttribute("url", url);			
	
		} catch(Exception e) {
			System.out.println("multi : " + e.getMessage());
		}
		
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/process/redirect.jsp");
		
		return forward;
	}

}
