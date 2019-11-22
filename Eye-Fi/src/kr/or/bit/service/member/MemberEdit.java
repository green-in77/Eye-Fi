package kr.or.bit.service.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dto.member.Member;

//회원 본인정보 수정 : 모달
public class MemberEdit implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1. 데이터 받기
		String userid = request.getParameter("userid");
		
		//2. 데이터 확인
		//System.out.println(userid);
		
		//3. 처리하기
		MemberDao memberdao = new MemberDao();
		Member member = memberdao.select(userid);
		
		//4. 저장하기
		request.setAttribute("member", member);
		
		//5. 페이지 설정
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/views/memberEditModal.jsp");
		
		return forward;
	}

}
