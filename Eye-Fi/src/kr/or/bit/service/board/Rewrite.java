package kr.or.bit.service.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;

public class Rewrite implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1. 값 받기
		String seq = request.getParameter("seq").trim();
		String subject = request.getParameter("subject");
		String bcode = request.getParameter("bcode").trim();
		String cp = request.getParameter("cp").trim();
		
		String url = "/WEB-INF/views/boardRewrite.jsp";
		
		if(seq == null || subject == null || subject.trim().equals("")) {
			url = "boardListOk.bdo";
		}
		
		request.setAttribute("seq", seq);
		request.setAttribute("subject", subject);
		request.setAttribute("bcode", bcode);
		request.setAttribute("cp", cp);
		
		ActionForward forward = new ActionForward();
		forward.setPath(url);
		
		return forward;
	}

}
