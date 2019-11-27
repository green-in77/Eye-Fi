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
			var choiceboard;
			var cname;
			var ccode;
			var btype;
			var bname;
			
			$.ajax({
				url : "sigun.ch",
				dataType: "JSON",
				type : "post",
				success : function(list){
					//console.log(list);
					var sigunlist = "<option>지역선택</option>";
					
					$.each(list,function(index,sigun){
						sigunlist += '<option value="'+sigun.arcode+'" cname="'+sigun.sigunname+'">'+sigun.sigunname+'</option>'
					}); //each 끝
					
					$('#sigunname').append(sigunlist);
					
					$('#sigunname').change(function() {
						ccode = $(this).val();
						console.log(ccode);
						cname = $('option:selected').attr('cname');
						console.log(cname);
						
					});//클릭이벤트 끝
				},
				error : function(xhr){
					console.log(xhr.status);
				}
			}); //sigun ajax 끝
			
			$('#type').change(function() {
				btype = $(this).val();
				console.log(btype);
				btype_name = $('.btype:selected').attr('bname');
				console.log(btype_name);
			})
			
			$('#choice').click(function() {
				$('#choiceboard').empty();
				choiceboard = "";
				
				choiceboard = "<td colspan='2' id='bname'>"+cname+" "+btype_name+"</td>"
							+ "<td><a href='#' url='boardCreateOk.bdo' id='submit' class='btn btn-primary'>게시판추가</td>";
				$('#choiceboard').append(choiceboard);
				
				$(document).on("click","#submit",function(e) {
					e.preventDefault();
					bname = $('#bname').text();
					var url = "";
					url = $(this).attr('url') + "?&bname="+bname+"&btype="+btype+"&ccode="+ccode+"&cname="+cname;
					console.log(url);
					window.location = url;
				});
			})
			
		});//onload 끝
	</script>
    <div class="content-wrapper text-center margin-top0" style="background-image: url('${pageContext.request.contextPath}/assets/img/background.jpg');">
    
		<!-- 여기부터 -->
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h1 class="page-head-line">게시판추가</h1>
				</div>
			</div>
		
		
			<div class="row">
				<div class="col-md-2">
					<!-- 게시판 리스트 -->
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="panel-heading">
	                           	현재<br>게시판 리스트
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
				
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-hover">
									<thead>
										<tr>
											<th>카테고리 선택</th>
											<th>게시판종류 선택</th>
										</tr>
									</thead>
									<tbody id="tbody">
										<tr>
											<td><select id="sigunname"></select></td>
											<td>
												<select id="type">
													<option>게시판 종류</option>
													<c:forEach var="type" items="${requestScope.typeList}">
														<c:choose>
															<c:when test="${type.btype == 1}">
															</c:when>
															<c:otherwise>
																<option class="btype" value="${type.btype}" bname="${type.btype_name}">${type.btype_name}</option>	
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</select>
											</td>
											<td>
												<input type="button" id="choice" class="btn btn-primary" value="선택완료">
											</td>
										</tr>
										<tr id="choiceboard">
											
										</tr>
									</tbody>
								</table>
							</div>
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
