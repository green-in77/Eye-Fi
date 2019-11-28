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
		String classify = request.getParameter("classify");
		
		//System.out.println(bcode + " / " + classify);
		
		BoardDao boarddao = new BoardDao();
		int boardTotalCount = 0;
		
		if(classify == null || classify.equals("")) {
			boardTotalCount = boarddao.boardTotalCount(Integer.parseInt(bcode));
		}else {
			boardTotalCount = boarddao.boardclassifyCount(classify);
		}
		
		request.setAttribute("boardTotalCount", boardTotalCount);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/boardAjaxJsp/boardTotalCount.jsp");
		
		return forward;
	}

}
