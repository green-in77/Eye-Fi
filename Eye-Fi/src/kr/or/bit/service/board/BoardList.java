package kr.or.bit.service.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;

public class BoardList implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1.데이터 받기
		String cp = request.getParameter("cp");
		String bcode = request.getParameter("bcode");
		
		//System.out.println("cp : " + cp + " / bcode : " + bcode);
		
		request.setAttribute("cp", cp);
		request.setAttribute("bcode", bcode);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/views/noticeboardList.jsp");
		
		return forward;
	}

}
