<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="../common/admin.jsp" />

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<c:import url="../common/top.jsp" />
	<script type="text/javascript">
		$(function(){
			//말머리 ajax
			/*$.ajax({
					url:"http://api.childcare.go.kr/mediate/rest/cpmsapi030/cpmsapi030/request",
					data : {"key" : "3cf25a2b59744f50a35d241f64ec6248&arcode=11260"},
					dataType: "json",
					type:"GET",
					success: function(list){
						console.log(list);
					},
					error : function(xhr){
						console.log(xhr.status);
					}
			
			}); */ //말머리 ajax 끝
		});//onload 끝
	</script>
    <div class="content-wrapper text-center margin-top0" style="background-image: url('${pageContext.request.contextPath}/assets/img/background.jpg');">
		<!-- 여기부터 -->
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h1 class="page-head-line">게시판</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-md-2" style="text-align:left;">
				<!-- 게시판 종류 선택 -->
					<select class="btn btn-primary" id="board">
					<c:forEach var="board" items="${requestScope.boardList}">
						<option value="${board.bcode}">${board.bname}<input type="hidden" value="${board.ccode}"></option>
					</c:forEach>
					</select>
				</div>
				
				<div class="col-md-2" style="text-align:left;">
				<!-- 말머리 선택 -->
					<select class="btn btn-primary" id="stcode">
						<option value="">어린이집 선택</option>
					</select>
				</div>
				
				<div class="col-md-4" style="text-align:left;">
				
				</div>
				
				<div class="col-md-3" style="text-align:right;">
				<!-- 검색 -->
					<input type="text" class="form-control" id="search" name="search" style="width: 100%">
				</div>
				<div class="col-md-1" style="text-align:right;">
					<a id="searchsubmit"><i class="fa fa-search" style="font-size:2.3vw;"></i></a>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12">
					<!--    Hover Rows  -->
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-hover">
									<thead>
										<tr>
											<th>#</th>
											<th>말머리</th>
											<th>글제목</th>
											<th>작성자</th>
											<th>작성일</th>
											<th>조회수</th>
										</tr>
									</thead>
									<tbody id="tbody">
										
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<!-- End  Hover Rows  -->
					<!-- page 처리 -->
					<div style="margin-bottom:15px; text-align:center" id="page">
						
					</div>
					<!-- page 처리끝 -->
					
					<!-- 글쓰기 -->
					<a class="editok btn btn-primary">완료</a></td>'
									+'<td><a class="cancel btn btn-primary">취소</a></td>';
				</div>	
			</div>
    	</div>

		<!-- 여기까지 -->
	</div>
    <!-- CONTENT-WRAPPER SECTION END-->
    <c:import url="../common/bottom.jsp" />
</body>
</html>
