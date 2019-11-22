package kr.or.bit.service.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dao.MemberDao;

public class BoardTotalCount implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		String bcode = request.getParameter("bcode");
		
		BoardDao boarddao = new BoardDao();
		int boardTotalCount = boarddao.boardTotalCount(Integer.parseInt(bcode));
		
		request.setAttribute("boardTotalCount", boardTotalCount);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/boardAjaxJsp/boardTotalCount.jsp");
		
		return forward;
	}

}
