package kr.or.bit.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.MemberDao;

public class MemberDel implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1. 데이터 받기
		String userid = request.getParameter("userid");
		
		//2. 데이터 확인
		//System.out.println(userid);
		
		//3. 처리
		MemberDao memberdao = new MemberDao();
		memberdao.memberDel(userid);
		
		ActionForward forward = new ActionForward();
		forward.setPath("logout.do");
		return forward;
	}

}
