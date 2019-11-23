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
import kr.or.bit.service.board.Ajax;
import kr.or.bit.service.board.BoardCreate;
import kr.or.bit.service.board.BoardList;
import kr.or.bit.service.board.BoardListOk;
import kr.or.bit.service.board.BoardTotalCount;
import kr.or.bit.service.board.BoardWrite;
import kr.or.bit.service.board.BoardWriteOk;
import kr.or.bit.service.board.NoticeboardContent;
import kr.or.bit.service.board.Rewrite;
import kr.or.bit.service.board.RewriteOk;

@WebServlet("*.bdo")
public class BoardFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardFrontController() {
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

		if( url_Command.equals("/boardCreate.bdo")) {//기술미구현
			//게시판생성
			action = new BoardCreate();
			forward = action.execute(request, response);
			
		}else if( url_Command.equals("/boardWrite.bdo")) {//완료
			//게시판 글쓰기 화면
			action = new BoardWrite();
			forward = action.execute(request, response);
			
		}else if( url_Command.equals("/boardWriteOk.bdo")) {//완료
			//게시판 글등록
			action = new BoardWriteOk();
			forward = action.execute(request, response);
			
		}else if( url_Command.equals("/boardListOk.bdo")) {//완료
			//게시판글 가져오기(비동기)
			action = new BoardListOk();
			forward = action.execute(request, response);
			
		}else if( url_Command.equals("/boardTotalCount.bdo")) {//완료
			//공지게시판 페이징(비동기)
			action = new BoardTotalCount();
			forward = action.execute(request, response);
		}else if( url_Command.equals("/boardContent.bdo")) {//완료
			//게시글 상세보기
			action = new NoticeboardContent();
			forward = action.execute(request, response);
			
		}else if( url_Command.equals("/boardList.bdo")) {
			//게시판보기
			action = new BoardList();
			forward = action.execute(request, response);
			
		}else if( url_Command.equals("/boardRewrite.bdo")) {
			//답글쓰기화면
			action = new Rewrite();
			forward = action.execute(request, response);
			
		}else if( url_Command.equals("/reboardWriteOk.bdo")) {
			//답글쓰기
			action = new RewriteOk();
			forward = action.execute(request, response);
			
		}
		
		else if( url_Command.equals("/listajax.bdo")) {
			action = new Ajax();
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
