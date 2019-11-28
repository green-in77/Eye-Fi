package kr.or.bit.service.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.BoardDao;
import kr.or.bit.dto.board.Reply;

public class ReplyDelete implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1. 데이터 받기
		String rseq = request.getParameter("rseq");
		
		//2.데이터 확인
		//System.out.println(rseq);
		
		//3. 처리

		BoardDao boarddao = new BoardDao();
		int result = boarddao.replyDelete(rseq);
		
		request.setAttribute("result", result);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/boardAjaxJsp/reply.jsp");
		return forward;
	}
	

}
