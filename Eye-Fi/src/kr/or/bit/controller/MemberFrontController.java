package kr.or.bit.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.bit.action.Action;
import kr.or.bit.action.ActionForward;
import kr.or.bit.service.member.AdminMemberEditOk;
import kr.or.bit.service.member.Code;
import kr.or.bit.service.member.EmailCheck;
import kr.or.bit.service.member.IdCheck;
import kr.or.bit.service.member.Join;
import kr.or.bit.service.member.Login;
import kr.or.bit.service.member.Logout;
import kr.or.bit.service.member.MemberDel;
import kr.or.bit.service.member.MemberEdit;
import kr.or.bit.service.member.MemberEditOK;
import kr.or.bit.service.member.MemberListOk;
import kr.or.bit.service.member.MemberSelectId;
import kr.or.bit.service.member.MemberTotalCount;

@WebServlet("*.do")
public class MemberFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MemberFrontController() {
        super();
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//들어오는 요청을 URI로 판단해서 service로 ....
    			String requestURI = request.getRequestURI(); //전체경로
    			String contextPath = request.getContextPath(); //home 경로
    			String url_Command = requestURI.substring(contextPath.length()); //자르면 /*.do 만 나온다...
    			
//    	    	System.out.println("requestURI : " + requestURI);
//    	    	System.out.println("contextPath : " + contextPath);
//    	    	System.out.println("url_Command : " + url_Command);
    			
    			ActionForward forward = null; //path
    			Action action = null; //action인터페이스를 사용하는 모든 자식객체(service)의 주소를 가질 수 있다.
    			
    			if( url_Command.equals("/login.do")) {
    				//로그인 //로직처리
    				action = new Login();
    				forward = action.execute(request, response);
    				
    			}else if( url_Command.equals("/idCheck.do") ) {
    				//중복ID체크 //로직처리
    				action = new IdCheck();
    				forward = action.execute(request, response);
    				
    			}else if( url_Command.equals("/emailCheck.do")) {
    				//이메일인증 //로직처리
    				action = new EmailCheck();
    				forward = action.execute(request, response);

    			}else if ( url_Command.equals("/join.do")) {
    				//가입 //로직처리
    				action = new Join();
    				forward = action.execute(request, response);
    				
    			}else if( url_Command.equals("/logout.do")) {
    				//로그아웃 //로직처리
    				action = new Logout();
    				forward = action.execute(request, response);
    				
    			}else if( url_Command.equals("/memberEdit.do")) {
    				//회원수정창 //로직처리
    				action = new MemberEdit();
    				forward = action.execute(request, response);
    				
    			}else if( url_Command.equals("/memberEditOk.do")) {
    				//회원수정 //로직처리
    				action = new MemberEditOK();
    				forward = action.execute(request, response);
    				
    			}else if( url_Command.equals("/memberList.do")) {
    				//회원리스트 //화면처리
    				forward = new ActionForward();
    				forward.setPath("/WEB-INF/views/memberList.jsp");
    				
    			}else if( url_Command.equals("/memberTotalCount.do")) {
    				//총회원수 //로직처리
    				action = new MemberTotalCount();
    				forward = action.execute(request, response);
    				
    			}else if( url_Command.equals("/memberListOk.do")) {
    				//회원리스트 //로직처리
    				action = new MemberListOk();
    				forward = action.execute(request, response);
    				
    			}else if( url_Command.equals("/memberSelectId.do")) {
    				//id로 회원조회
    				action = new MemberSelectId();
    				forward = action.execute(request, response);
    				
    			}else if( url_Command.equals("/code.do")) {
    				//코드조회
    				action = new Code();
    				forward = action.execute(request, response);
    				
    			}else if( url_Command.equals("/adminMemberEditOk.do")) {
    				//관리자 회원정보 수정
    				action = new AdminMemberEditOk();
    				forward = action.execute(request, response);
    				
    			}else if( url_Command.equals("/memberDel.do")) {
    				//회원탈퇴
    				action = new MemberDel();
    				forward = action.execute(request, response);
    				
    			}
//----------------------------------------------------------------------------------------------------------------------------------------------------
    			if( forward!=null ) {
    				RequestDispatcher dis = request.getRequestDispatcher(forward.getPath());
    				dis.forward(request, response);
    			}
    			
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
}
