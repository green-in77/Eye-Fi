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
	
	<c:set var="bcode" value="${requestScope.bcode}"/>
	<c:if test="${bcode == null }">
		<c:set var="bcode" value="1"/>	
	</c:if>
	
	<script type="text/javascript">
		$(function(){			
			var cp = ${cp};
			
			var bcode = ${bcode};
			var boardlist = "";
			
			var url = "boardListOk.bdo";
			var counturl = "boardTotalCount.bdo";
			list(url,cp, bcode);
			totalcount(counturl,bcode);

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
							//console.log(list[0].bcode);
							
							if(list[0].bcode == 1){
								$.each(list, function(index, board){
									//console.log(board);
									boardlist += '<tr>'
													+'<td>'+board.seq+'</td>'
													+'<td><a href="boardContent.bdo?bcode='+bcode+'&cp='+cp+'&seq='+board.seq+'">'+board.subject+'</td>'
													+'<td>'+board.userid+'</td>'
													+'<td>'+board.logtime+'</td>'
													+'<td>'+board.hit+'</td>'
												  +'</tr>'
								});//each 끝
								
							} if(list[0].bcode == 2){
								$.each(list, function(index, board){
									boardlist += '<tr>'
													+'<td>'+board.seq+'</td>'
													+'<td style="text-align:left;">';
										
									for(var i = 1 ;i<=board.depth;i++){
										boardlist += '&nbsp;&nbsp;&nbsp';
										
										if(board.depth>0){
											boardlist += '<img src = "${pageContext.request.contextPath}/assets/img/re.gif">';
										}
									}
									
									boardlist += 	'<a href="boardContent.bdo?bcode='+bcode+'&cp='+cp+'&seq='+board.seq+'">'+board.subject+'</td>'
													+'<td>'+board.userid+'</td>'
													+'<td>'+board.logtime+'</td>'
													+'<td>'+board.hit+'</td>'
								  				+'</tr>'
								});//each 끝
							}
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
				//console.log(bcode);
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
							console.log(cp);
							console.log(bcode);
							
							list(url,cp,bcode);
						});
					},
					error : function(xhr){
						console.log(xhr.status);
					}
				});//ajax 끝
			}//totalcount 끝
			
			$('.boardmove').click(function() {
				//console.log($(this));
				//console.log(url);
				bcode = $(this).val();
				//console.log(bcode)
				//console.log($(this).closest('div').find("input:eq(0)").val());
				btype = $('#'+bcode).val()
				ccode = $('#'+bcode+'c').val()
				//console.log(ccode);
				
				list(url,1,bcode);
				totalcount(counturl,bcode);
				
				if(bcode != 1){
					$.ajax({
						url : 'childListAjax.ch',
						data : {"arcode" : ccode},
						dataType : "json",
						type : "get",
						success : function(res){
							console.log(res);
							crname = "";
							$('#crname').empty();
							
							var crname = '<th colspan="5"><select id="crname"><option value="">어린이집 선택</option>';
							$.each(res, function(index, list) {
								console.log(list.crname);	
								crname += '<option value="'+list.crname+'">'+list.crname+'</option>';
							})
							crname += "</select></th>";
							$('#crname').append(crname);
						},
						error : function(xhr){
							console.log(xhr.status);
						}
					});//외부데이터 끝
				}
				
				$('#writebutton').empty();
				//console.log(bcode == 1 && ${sessionScope.admin}==1);
				//console.log(bcode != 1 && '${sessionScope.userid}' != null);
				writebutton = "";
				if((bcode == 1 && ${sessionScope.admin}==1) || (bcode != 1 && '${sessionScope.userid}' != null) ){
					writebutton = '<form action="boardWrite.bdo" method="post" id="writefrom">'
									+ '<input type="hidden" id="btype" name="btype" value="">'
									+ '<input type="hidden" id="cp" name="cp" value="">'
									+ '<input type="hidden" id="bcode" name="bcode" value="">'
									+ '<input type="submit" class="btn btn-primary" id="write" value="글쓰기">'
								+ '</form>';
				}
				$('#writebutton').append(writebutton);
				
				$('#write').click(function() {
					console.log(bcode);
					$('#btype').val(btype)
					$('#bcode').val(bcode);
					$('#cp').val(cp);
				});//글쓰기 클릭 끝
				
			});//게시판이동 클릭 끝
	
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
				<div class="col-md-2">
					<!-- 게시판 리스트 -->
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="panel-heading">
                            	게시판 리스트
                        	</div>
							<div class="panel-body">
	                            <div class="panel-group" id="accordion">
	                            	<!-- 카페고리1개 -->
	                            	<c:forEach var="cate" items="${requestScope.cateList}">
	                            	
		                                <div class="panel panel-default">
		                                    <div class="panel-heading">
		                                        <h4 class="panel-title">
		                                        	<!-- 카테고리  -->
		                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapse${cate.ccode}" class="collapsed">${cate.cname}</a>
		                                        </h4>
		                                    </div>
		                                    
		                                    <div id="collapse${cate.ccode}" class="panel-collapse collapse" style="height: 0px;">
		                                    	<div class="panel-body">
		                                    		<!-- 카테고리에 맞는 보드리스트 출력 -->
			                                    	<c:forEach var="board" items="${requestScope.boardList}">
			                                    		<c:forEach var="type" items="${requestScope.typeList}">
			                                    			<c:if test="${board.ccode == cate.ccode && board.btype == type.btype}">
			                                    				<li class="boardmove"  value="${board.bcode}" style="list-style-type:none;">${type.btype_name}</li>
			                                    				<input type="hidden" id="${board.bcode}" value="${type.btype}">
			                                    				<input type="hidden" id="${board.bcode}c" value="${cate.ccode}">
					                                    	</c:if>		                                    
				                                    	</c:forEach>
				                                	</c:forEach>
				                                	
		                                        </div>
	                                    	</div>
		                                </div>
	                                </c:forEach>
	                                <!-- 카페고리1개 -->
								</div>
							</div>
							
						</div>
					</div>
				</div>
				<div class="col-md-10">
					<!--    Hover Rows  -->
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-hover">
									<thead>
										<tr id="crname"></tr>
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
				</div>
			</div>	
			
			<!-- page 처리 -->
			<div class="row">
				<div class="col-md-12">
					<div style="margin-bottom:15px; text-align:center" id="page">
					
					</div>
				</div>
			</div>
			<!-- page 처리끝 -->
					
			<div class="row">
				<div class="col-md-12" style="text-align:right;" id="writebutton">
				<!-- 글쓰기 버튼-->
					<c:if test="${(bcode == 1 && sessionScope.admin == 1) || (bcode != 1 && sessionScope.userid != null)}">
						<form action="boardWrite.bdo" method="post" id="writefrom">
							<input type="hidden" id="btype" name="btype" value="">
							<input type="hidden" id="cp" name="cp" value="">
							<input type="hidden" id="bcode" name="bcode" value="">
							<input type="submit" class="btn btn-primary" id="write" value="글쓰기">
						</form>
					</c:if> 
				</div>
			</div>
    	</div>

		<!-- 여기까지 -->
	</div>
    <!-- CONTENT-WRAPPER SECTION END-->
    <c:import url="../common/bottom.jsp" />
</body>
</html>
