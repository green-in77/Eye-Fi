package kr.or.bit.service.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dto.board.Board;
import kr.or.bit.dto.board.Board_List;

public class BoardEdit implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1. 데이터 받기
		String seq = request.getParameter("seq").trim();
		String bcode = request.getParameter("bcode").trim();
		String cp = request.getParameter("cp");
		
		//2. 데이터 확인
		//System.out.println(seq + "/" + bcode + "/" + cp);
		
		String url = "/WEB-INF/views/boardEdit.jsp";
		
		if(seq == null || bcode == null) {
			url = "boardListOk.bdo";
		}
		
		BoardDao boarddao = new BoardDao();
		Board board = boarddao.boardContent(Integer.parseInt(seq));
		List<Board_List> boardList = boarddao.listSel();
		
		request.setAttribute("boardList", boardList);
		request.setAttribute("board", board);
		request.setAttribute("bcode", bcode);
		request.setAttribute("cp", cp);
		
		ActionForward forward = new ActionForward();
		forward.setPath(url);
		return forward;
	}

}
