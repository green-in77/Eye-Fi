package kr.or.bit.service.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.MemberDao;

public class MemberTotalCount implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		String search = request.getParameter("search");
		String searchtag = request.getParameter("searchtag");
		
		//System.out.println(" search : " + search + " searchtag : " + searchtag);
		
		MemberDao memberdao = new MemberDao();
		int memberTotalCount = 0;
		
		if(searchtag == null) {
			memberTotalCount = memberdao.memberTotalCount();
			
		}else if(searchtag.equals("idemail")) {
			memberTotalCount = memberdao.memberIdEmailCount(search);
			
		}else if(searchtag.equals("admin")) {
			memberTotalCount = memberdao.memberAdminCount(search);
			
		}else if(searchtag.equals("code")) {
			memberTotalCount = memberdao.memberCodeCount(search);
			
		}
		
		
		request.setAttribute("memberTotalCount", memberTotalCount);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/memberAjaxJsp/memberTotalCount.jsp");
		return forward;
	}

}
