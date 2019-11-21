package kr.or.bit.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dto.Member;

public class MemberEditOK implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1. 값 받기
		String userid = request.getParameter("userid");
		String userpw = request.getParameter("userpw");
				
		//2. 값 확인
		//System.out.println(userid+"/"+userpw);
		
		//3. 처리
		Member member = new Member();
		member.setUserid(userid);
		member.setUserpw(userpw);
		
		MemberDao memberdao = new MemberDao();
		int row = memberdao.edit(member);
		String result = "";
		
		if(row > 0 ) {
			result = "true";
		}else {
			result = "false";
		}

		request.setAttribute("result", result);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/ajaxJsp/memberEdit.jsp");
		
		return forward;
	}

}
