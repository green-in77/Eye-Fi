<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="../common/admin.jsp" />

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<c:import url="../common/top.jsp" />
	<!-- 초기페이지 설정 -->
	<c:set var="cp" value="${requestScope.cp}"/>
	<c:if test="${cp == null }">
		<c:set var="cp" value="1"/>	
	</c:if>
	
	<script type="text/javascript">
		$(function(){			
			//var url = "http://api.childcare.go.kr/mediate/rest/cpmsapi030/cpmsapi030/request?key=3cf25a2b59744f50a35d241f64ec6248&arcode=11260";
			/* $.ajax({
				url : "http://api.childcare.go.kr/mediate/rest/cpmsapi030/cpmsapi030/request?key=3cf25a2b59744f50a35d241f64ec6248&arcode=11260",
				dataType: "jsonp",
				jsonpCallback : 'myCallback',
				success: callback				
			}); //ajax 끝
 */			
 			/* $.getJSON(url, "?collback=?", callback); */
			
			var cp = ${cp};
			
			var bcode = 1;
			var boardlist = "";
			
			var url = "boardListOk.bdo";
			
			list(url,cp, bcode);
			totalcount("boardTotalCount.bdo",bcode);

			//회원리스트 관련
			function list(url,cp,bcode){
				$.ajax({ //리스트 비동기 조회
					url : url,
					data : {
						"cp" : cp,
						"bcode" : bcode
					},
					dataType: "JSON",
					type: "POST",
					success: function(list){
						//console.log(list);

						//console.log(cp);
						$('#tbody').empty();
						boardlist = "";
						if(list != ""){
							$.each(list, function(index, board){
								//console.log(board);
								boardlist += '<tr>'
												+'<td>'+board.seq+'</td>'
												+'<td><a href="boardContent.bdo?bcode=1&cp='+cp+'&seq='+board.seq+'">'+board.subject+'</td>'
												+'<td>'+board.userid+'</td>'
												+'<td>'+board.logtime+'</td>'
												+'<td>'+board.hit+'</td>'
											  +'</tr>'
							});//each 끝
	
						}else {
							boardlist = "데이터가 없습니다.";
						}
						//document.getElementById('tbody').innerHTML = memberlist;
						$('#tbody').append(boardlist);
								
					},
					error : function(xhr){
						console.log(xhr.status);
					}
				});//ajax
			};//list function 끝
			
			//페이징처리
			function totalcount(totalcount,bcode){
				$.ajax({
					url:totalcount,
					data : {"bcode" : bcode},
					dataType: "text",
					type:"POST",
					success: function(count){
						//console.log(count.trim());
						$('#page').empty();
						count = count.trim();
						
						//console.log(count);
						count = count/4;
						
						count = Math.ceil(count);
						//console.log(count);
						var page = '<span style="padding:4px;" class="page btn btn-primary" id="first">처음</span>';
						for(var i = 1; i <= count ; i++){
							page += '<span style="padding:4px;" class="page btn btn-primary" id="paging'+i+'">'+i+'</span>'
						}
						page += '<span style="padding:4px;" class="page btn btn-primary" id="next">마지막</span>'
						
						$('#page').append(page);
						
						//페이지 클릭 이벤트
						$('.page').click(function(){
							//console.log(url);
							var cur = $(this).text(); //클릭한 페이지
							
							//console.log(cur);
							
							if(cur == "처음"){
								cp = 1;
							}else if(cur == "마지막"){
								cp = count;								
							}else {
								cp = (cur);
							}
							//console.log(cp);
							
							list(url,cp,bcode);
						});
					},
					error : function(xhr){
						console.log(xhr.status);
					}
				});//ajax 끝
			}//totalcount 끝
		});//onload 끝	
	</script>
    <div class="content-wrapper text-center margin-top0" style="background-image: url('${pageContext.request.contextPath}/assets/img/background.jpg');">
		<!-- 여기부터 -->
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h1 class="page-head-line">공지게시판</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-md-2" style="text-align:left;">

				</div>
				
				<div class="col-md-2" style="text-align:left;">

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
					<div class="row">
						<div class="col-md-12">
							<div style="margin-bottom:15px; text-align:center" id="page">
						
							</div>
						</div>
					</div>
					<!-- page 처리끝 -->
					
					<div class="row">
						<div class="col-md-12" style="text-align:right;">
						<!-- 글쓰기 버튼-->
							<c:if test="${sessionScope.admin == 1}">
								<form action="boardWrite.bdo" method="post">
									<input type="hidden" name="bcode" value="1">
									<input type="submit" class="btn btn-primary" value="글쓰기">
								</form>
							</c:if>
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
