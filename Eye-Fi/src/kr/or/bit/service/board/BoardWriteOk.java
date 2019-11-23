package kr.or.bit.service.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dto.board.Board;
import kr.or.bit.dto.board.Reboard;

public class BoardWriteOk implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1. 데이터 받기
		String userid = request.getParameter("userid");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String classify = request.getParameter("classify");
		String notice = request.getParameter("notice");
		String bcode = request.getParameter("bcode");
		
		//2. 데이터 확인
		//System.out.println(userid +"/"+ subject +"/"+ content +"/"+ classify +"/"+ notice +"/ bcode: "+ bcode);
		
		if(notice == null) {
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
			//앨범게시판 글쓰기...
		}
		
		if(result > 0 ) {
			msg = "글이 등록되었습니다.";
			url = "boardList.bdo?bcode="+bcode;
		}else {		
			msg = "글쓰기가 실패하였습니다. 다시 작성 부탁드립니다.";
			url = "boardWrite.bdo";
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/process/redirect.jsp");
		
		return forward;
	}

}
