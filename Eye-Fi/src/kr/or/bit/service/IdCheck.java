package kr.or.bit.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.MemberDao;

public class IdCheck implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1. 데이터 받기
		String userid = request.getParameter("userid");
		
		//2. 데이터 확인
		//System.out.println(userid);
		
		//3. 처리
		MemberDao memberdao = new MemberDao();
		String result = memberdao.idCheck(userid);
		
		//4. 저장
		request.setAttribute("result", result);
		
		//5. 이동경로 설정
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/ajaxJsp/idCheck.jsp");
		
		return forward;
	}
}
