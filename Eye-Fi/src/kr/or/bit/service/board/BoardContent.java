package kr.or.bit.service.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dto.board.Album;
import kr.or.bit.dto.board.Board;

public class BoardContent implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1. 데이터 받기
		String cp = request.getParameter("cp");
		String bcode = request.getParameter("bcode");
		String seq = request.getParameter("seq").trim();
		String btype = request.getParameter("btype");
		String arcode = request.getParameter("arcode");
		
		//2. 데이터 확인
		//System.out.println("bcode : " + bcode + "/seq : " + seq + "/cp : "+cp + " / arcode : " + arcode);
		
		String url = "";
		if( seq == null || seq.equals("")){
			url="boardList.bdo?bcode="+bcode+"&btype="+btype+"&arcode="+arcode; //돌아갈 경우 왔던 게시판의 1페이지로..
			
		}else {
			BoardDao boarddao = new BoardDao();
			boarddao.hitAdd(seq);
			
			if(btype.equals("3")) {
				Album album = boarddao.albumContent(Integer.parseInt(seq));
				request.setAttribute("board", album);
			}else {
				Board board = boarddao.boardContent(Integer.parseInt(seq));
				request.setAttribute("board", board);
			}

			request.setAttribute("arcode", arcode);
			request.setAttribute("btype", btype);
			request.setAttribute("cp", cp);
			
			url="/WEB-INF/views/boardContent.jsp";
		}
		
		ActionForward forward = new ActionForward();
		forward.setPath(url);
		
		return forward;
	}

}
