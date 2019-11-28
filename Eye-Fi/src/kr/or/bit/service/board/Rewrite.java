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
		String content = request.getParameter("content");
		String classify = request.getParameter("classify");
		String btype = request.getParameter("btype");
		String arcode = request.getParameter("arcode");

		String url = "/WEB-INF/views/boardRewrite.jsp";
		
		if(seq == null || subject == null || subject.trim().equals("")) {
			url = "boardListOk.bdo";
		}
		
		request.setAttribute("classify", classify);
		request.setAttribute("content", content);
		request.setAttribute("seq", seq);
		request.setAttribute("subject", subject);
		request.setAttribute("bcode", bcode);
		request.setAttribute("cp", cp);
		request.setAttribute("btype", btype);
		request.setAttribute("arcode", arcode);
		
		ActionForward forward = new ActionForward();
		forward.setPath(url);
		
		return forward;
	}

}
