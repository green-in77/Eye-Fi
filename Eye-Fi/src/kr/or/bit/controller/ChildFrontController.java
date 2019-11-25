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
import kr.or.bit.service.child.Child;
import kr.or.bit.service.child.ChildContent;

@WebServlet("*.ch")
public class ChildFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ChildFrontController() {
        super();   
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//들어오는 요청을 URI로 판단해서 service로 ....
    	String requestURI = request.getRequestURI(); //전체경로
    	String contextPath = request.getContextPath(); //home 경로
    	String url_Command = requestURI.substring(contextPath.length()); //자르면 /*.do 만 나온다...
    			
//    	System.out.println("requestURI : " + requestURI);
//    	System.out.println("contextPath : " + contextPath);
//    	System.out.println("url_Command : " + url_Command);
    			
    	ActionForward forward = null; //path
    	Action action = null; //action인터페이스를 사용하는 모든 자식객체(service)의 주소를 가질 수 있다.
    	
    	if( url_Command.equals("/childListAjax.ch")) {
			//어린이집 정보 비동기
			action = new Child();
			forward = action.execute(request, response);
			
		}else if( url_Command.equals("/childList.ch")) {
			//어린이집리스트 화면
			forward = new ActionForward();
			forward.setPath("/WEB-INF/views/childList.jsp");
			
		}else if( url_Command.equals("/childContent.ch")) {
			//어린이집 상세보기
			action = new ChildContent();
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
