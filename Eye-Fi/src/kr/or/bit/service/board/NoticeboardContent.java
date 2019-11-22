package kr.or.bit.service.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dto.board.Board;

public class NoticeboardContent implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1. 데이터 받기
		String cp = request.getParameter("cp");
		String bcode = request.getParameter("bcode");
		String seq = request.getParameter("seq").trim();
		
		//2. 데이터 확인
		//System.out.println("bcode : " + bcode + "/seq : " + seq + "/cp : "+cp);
		String url = "";
		if( seq == null || seq.equals("")){
			url="noticeboardList.bdo?bcode="+bcode; //돌아갈 경우 왔던 게시판의 1페이지로..
		}else {
			BoardDao boarddao = new BoardDao();
			boarddao.hitAdd(seq);
			
			Board board = boarddao.boardContent(Integer.parseInt(seq));
			
			request.setAttribute("board", board);
			request.setAttribute("cp", cp);
			
			url="/WEB-INF/views/noticeboardContent.jsp";
		}
		
		ActionForward forward = new ActionForward();
		forward.setPath(url);
		
		return forward;
	}

}
