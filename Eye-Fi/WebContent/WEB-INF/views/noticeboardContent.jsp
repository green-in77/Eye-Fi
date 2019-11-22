<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="../common/admin.jsp" />

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<c:import url="../common/top.jsp" />

    <div class="content-wrapper text-center margin-top0" style="background-image: url('${pageContext.request.contextPath}/assets/img/background.jpg');">
		<!-- 여기부터 -->
		<c:set var = "board" value="${requestScope.board}" />
		
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h1 class="page-head-line">${board.subject}</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<!--    Hover Rows  -->
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table">
									<tr>
										<c:choose>
											<c:when test="${board.bcode == 1}">
												<td colspan="3">${board.subject}</td>	
											</c:when>
											<c:otherwise>
												<td>${board.classify}</td>
												<td colspan="2">${board.subject}</td>
											</c:otherwise>										
										</c:choose>
									</tr>
									<tr>
										<td>${board.userid}</td>
										<td>${board.logtime}</td>
										<td>${board.hit}</td>
									</tr>
									<tr>
										<td colspan="3">${board.content}</td>
									</tr>
								</table>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12" style="text-align:center;">
							<form action="noticeboardList.bdo" method="post">
								<input type="hidden" name="bcode" value="${board.bcode}">
								<input type="hidden" name="cp" value="${requestScope.cp}">
								<input type="submit" class="btn btn-primary" value="목록가기">
							</form>
						</div>
					</div>
					
				</div>	
			</div>
    	</div>

		<!-- 여기까지 -->
	</div>
    <!-- CONTENT-WRAPPER SECTION END-->
    <c:import url="../common/bottom.jsp" />
</body>
</html>
