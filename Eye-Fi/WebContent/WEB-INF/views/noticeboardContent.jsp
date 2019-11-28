<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="../common/member.jsp" />

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<c:import url="../common/top.jsp" />
	<script type="text/javascript">
		$(function(){
			replyList();
			
			$('#replyWrite').click(function() {
				//console.log('${sessionScope.userid}');
				//console.log(${board.seq});
				//console.log($('#rcontent').val());
				
				if($('#rcontent').val() != ""){
					$.ajax({
						url : "replyWriteOk.bdo",
						data :{
							"reuserid" : '${sessionScope.userid}',
							"seq" : ${board.seq},
							"rcontent" : $('#rcontent').val()
						},
						dataType : "text",
						type : "post",
						success : function(result){
							//console.log(">"+result.trim()+"<");
							if(result.trim() > 0){
								$('#rcontent').val("");
								replyList();
							}else {
								alert("댓글등록에 실패했습니다. 다시 확인 바랍니다.");
							}
							
						},
						error : function(xhr){
							console.log(xhr.status);
						}
					});//댓글등록 ajax 끝
				}else {
					alert("댓글 내용을 입력바랍니다.");
				}
			});//리플등록 클릭끝
			
			
		});//onload 끝
		
		function replyList(){
			$.ajax({
				url : "replyList.bdo",
				data :{"seq" : ${board.seq}},
				dataType : "json",
				type : "post",
				success : function(list){
					//console.log(list);
					$('#replyList').empty()
					var replyList = "";
					
					$.each(list,function(index,reply){
						replyList += '<tr>'
										+'<td>'+reply.reuserid+'</td>'
										+'<td colspan="2">'+reply.rcontent+'</td>'
										+'<td>'+reply.retime+'</td>'
										+'<td>';
						if('${sessionScope.userid}' == reply.reuserid || '${sessionScope.admin}' == 1){
							replyList += '<input type="button" rseq="'+reply.rseq+'"class="replyDelete btn btn-primary" value="삭제">';
						}
						
						replyList +=	'</td>'
									+'</tr>';
					});//replylist each 끝
					
					$('#replyList').append(replyList);
						
					replyDelete();
				},
				error : function(xhr){
					console.log(xhr.status);
				}
			});//댓글리스트 ajax 끝
		}
		
		function replyDelete(){
			$('.replyDelete').click(function() {
				//console.log($(this).attr('rseq'));
				
				$.ajax({
					url : "replyDelete.bdo",
					data :{"rseq" : $(this).attr('rseq')},
					dataType : "text",
					type : "post",
					success : function(result){
						//console.log(">"+result.trim()+"<");
						if(result.trim() > 0){
							replyList();
						}else {
							alert("댓글삭제에 실패했습니다. 다시 확인 바랍니다.");
						}
						
					},
					error : function(xhr){
						console.log(xhr.status);
					}
				});//댓글삭제 ajax 끝
			});
		}
		
	</script>
    <div class="content-wrapper text-center margin-top0" style="background-image: url('${pageContext.request.contextPath}/assets/img/background.jpg');">
		<!-- 여기부터 -->
		<c:set var = "board" value="${requestScope.board}" />
		<c:set var = "btype" value="${requestScope.btype}" />
		
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
									<tbody>
										<tr>
											<c:choose>
												<c:when test="${board.bcode == 1}">
													<td colspan="5">${board.subject}</td>	
												</c:when>
												<c:otherwise>
													<td colspan="2">${board.classify}</td>												
													<td colspan="3">${board.subject}</td>
												</c:otherwise>										
											</c:choose>
										</tr>
										<tr>
											<td width="10%">${board.userid}</td>
											<td width="10%"></td>
											<td width="60%"></td>
											<td width="15%">${board.logtime}</td>
											<td width="5%">${board.hit}</td>
										</tr>
										<c:if test="${btype == 3}">
											<tr>
												<td colspan="5" height="500px">
													<img src="board_img/${board.save_file}" style="max-width: 30rem;" class="img-fluid rounded-circle shadow">
												</td>
											</tr>
										</c:if>
										<tr height="300px">
											<td colspan="5">${board.content}</td>
										</tr>
										
										<tr>
											<th colspan="5">댓글</th>
										</tr>
										
										<!-- 댓글 쓰기 -->
										<tr>
											<td colspan="3"><textarea id="rcontent" name="rcontent" rows="2" cols="100"></textarea></td>
											<td colspan="2"><input type="button" id="replyWrite" class="btn btn-primary" value="댓글등록"></td>
										</tr>
									</tbody>
									<!--  댓글 보여주기 -->
									<tbody id="replyList">
										
									</tbody>
									
								</table>
							</div>
						</div>
					</div>
					<div class="row">
						
						<div class="col-md-3" style="text-align : center;">
							<c:if test="${board.userid == sessionScope.userid || sessionScope.admin == 1}">
								<form action="boardEdit.bdo" method="post">
									<input type="hidden" name="arcode" value="${requestScope.arcode}">
									<input type="hidden" name="btype" value="${btype}">
									<input type="hidden" name="seq" value="${board.seq}">
									<input type="hidden" name="bcode" value="${board.bcode}">
									<input type="hidden" name="cp" value="${requestScope.cp}">
									<input type="submit" class="btn btn-primary" value="수정하기">
								</form>
							</c:if>	
						</div>
							
						<div class="col-md-3" style="text-align : center;">
							<c:if test="${board.userid == sessionScope.userid || sessionScope.admin == 1}">
								<form action="boardDelete.bdo" method="post" >
									<input type="hidden" name="btype" value="${btype}">
									<input type="hidden" name="arcode" value="${requestScope.arcode}">
									<input type="hidden" name="seq" value="${board.seq}">
									<input type="hidden" name="bcode" value="${board.bcode}">
									<input type="hidden" name="cp" value="${requestScope.cp}">
									<input type="submit" class="btn btn-primary" value="삭제하기">
								</form>
							</c:if>	
						</div>
												
						<div class="col-md-3" style="text-align : center;">
							<c:if test="${btype == 2}">
								<form action="boardRewrite.bdo" method="post" >
									<input type="hidden" name="arcode" value="${requestScope.arcode}">
									<input type="hidden" name="btype" value="${btype}">
									<input type="hidden" name="classify" value="${board.classify}">
									<input type="hidden" name="content" value="${board.content}">
									<input type="hidden" name="subject" value="${board.subject}">
									<input type="hidden" name="seq" value="${board.seq}">
									<input type="hidden" name="bcode" value="${board.bcode}">
									<input type="hidden" name="cp" value="${requestScope.cp}">
									<input type="submit" class="btn btn-primary" value="답글쓰기">
								</form>	
							</c:if>
						</div>
						
						<div class="col-md-3" style="text-align:center;">
							<form action="boardList.bdo" method="post">
								<input type="hidden" name="arcode" value="${arcode}">
								<input type="hidden" name="btype" value="${btype}">
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
