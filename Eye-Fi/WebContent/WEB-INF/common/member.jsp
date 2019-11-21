<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%	
	if(session.getAttribute("userid") == null){  //로그인이 되어있지 않으면..
		//강제로 페이지 이동
		//response.sendRedirect("index.jsp");
		out.print("<script>");
			out.print("alert('회원만 접근 가능합니다.');");	
			out.print("location.href='index.jsp'");	
		out.print("</script>");		
	}
%>