package kr.or.bit.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.MemberDao;

public class MemberTotalCount implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		MemberDao memberdao = new MemberDao();
		int memberTotalCount = memberdao.memberTotalCount();
		
		request.setAttribute("memberTotalCount", memberTotalCount);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/memberAjaxJsp/memberTotalCount.jsp");
		return forward;
	}

}
