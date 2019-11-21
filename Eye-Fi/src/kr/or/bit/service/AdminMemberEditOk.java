package kr.or.bit.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dto.Member;

public class AdminMemberEditOk implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1. 데이터 받기
		String userid = request.getParameter("userid");
		String userpw = request.getParameter("userpw");
		String email = request.getParameter("email");
		int admin = Integer.parseInt(request.getParameter("admin"));
		int code = Integer.parseInt(request.getParameter("code"));
		
		//2. 데이터 확인
		//System.out.println(userid+"/"+userpw+"/"+email+"/"+admin+"/"+code);
		
		//3. 처리
		Member member = new Member();
		member.setUserid(userid);
		member.setUserpw(userpw);
		member.setEmail(email);
		member.setAdmin(admin);
		member.setCode(code);
		
		MemberDao memberdao = new MemberDao();
		memberdao.adminMemberEdit(member);
		
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/views/memberList.jsp");
		return forward;
	}

}
