package kr.or.bit.service.child;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;

public class ChildContent implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1. 데이터 받기
		String stcode = request.getParameter("stcode");
		
		request.setAttribute("stcode", stcode);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/views/childContent.jsp");
		return forward;
	}

}
