<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%	//관리자 1 , 일반회원 0
	if(session.getAttribute("admin").equals("0")){ //관리자 여부가 0이면(일반회원)
		//강제로 페이지 이동
		//response.sendRedirect("index.jsp");
		out.print("<script>");
			out.print("alert('관리자만 접근 가능합니다.');");	
			out.print("location.href='index.jsp'");	
		out.print("</script>");
	}
%>