package kr.or.bit.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.dao.MemberDao;
import kr.or.bit.dto.Member;

public class Login implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) {
		//1. 데이터 받기
		String userid = request.getParameter("userid");
		String userpw = request.getParameter("userpw");
		
		//2. 데이터 확인
		//System.out.println(userid + "/" + userpw);
		
		//3. 처리
		MemberDao memberdao = new MemberDao();
		Member member = memberdao.login(userid, userpw);
		
		String msg = "";
		String url = request.getContextPath()+"/index.jsp";
		
		//System.out.println(member);
		
		if( member != null ) { //아이디와 비밀번호가 일치 했다면..
			
			if(member.getCode() == 1) {  //code 1 : 활동 ,  3 : 탈퇴
				msg = "로그인 되었습니다.";
				
				HttpSession session = request.getSession();
				session.setAttribute("userid", userid);
				session.setAttribute("admin", member.getAdmin());
				
			}else if(member.getCode() == 2) {// 2 : 정지
				msg = "정지된 아이디입니다. 관리자에게 문의바랍니다.";
				
			}else if(member.getCode() == 3) {
				msg = "탈퇴한 아이디입니다. 가입 후 이용바랍니다.";
			}
			
		}else { //아이디와 비밀번호가 일치하지 않는다면..
			msg = "아이디 또는 비밀번호가 잘못되었습니다. 다시 확인바랍니다.";
		}
		
		//4. 값 저장
		request.setAttribute("msg", msg);
		request.setAttribute("url", url);
		
		//5. 이동경로 지정
		ActionForward forward = new ActionForward();
		forward.setPath("/WEB-INF/process/redirect.jsp");
		return forward;
	}

}
