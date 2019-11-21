package kr.or.bit.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dto.Member;

public class Join implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1. 데이터 받기
		String userid = request.getParameter("userid");
		String userpw = request.getParameter("userpw");
		String email = request.getParameter("email");
		
		//2. 데이터 확인
		//System.out.println(userid + "/" + userpw + "/" + email);
		
		//3. 처리
		Member member = new Member();
		member.setUserid(userid);
		member.setUserpw(userpw);
		member.setEmail(email);
		
		MemberDao memberdao = new MemberDao();
		int result = memberdao.join(member);
		
		String msg = "";
		String url = request.getContextPath()+"/index.jsp";
				
		if(result > 0) {
			msg = "가입되었습니다. 반갑습니다 :)";
		}else {
			msg = "가입처리가 완료되지 않았습니다. 다시 확인바랍니다.";
		}
		
		//4.값 저장
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		
		//5.이동경로설정
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/process/redirect.jsp");
		return forward;
	}

}
