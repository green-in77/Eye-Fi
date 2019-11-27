<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="../common/member.jsp" />

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<c:import url="../common/top.jsp" />
	
	<!-- include libraries(jQuery, bootstrap) --> 
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.js"></script> 

	<!-- include summernote css/js -->
	<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote.css" rel="stylesheet">
	<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote.js"></script>
	
	<c:set var="arcode" value="${requestScope.arcode}"/>
	<c:set var="btype" value="${requestScope.btype}" />
	
	<script type="text/javascript">
		$(function(){
			
			//말머리 ajax
			/* $.ajax({
				url : "http://api.childcare.go.kr/mediate/rest/cpmsapi030/cpmsapi030/request",
				data : "key"
			}); *///말머리 ajax 끝
			$.ajax({
				url : 'childListAjax.ch',
				data : {"arcode" : '${arcode}'},
				dataType : "json",
				type : "get",
				success : function(res){
					//console.log(res);
					crname = "";
					$('#crname').empty();
					
					var crname = '<option value="">어린이집 선택</option>';
					$.each(res, function(index, list) {
						//console.log(list.crname);	
						crname += '<option value="'+list.crname+'">'+list.crname+'</option>';
					});
					
					$('#crname').append(crname);
				},
				error : function(xhr){
					console.log(xhr.status);
				}
			});//외부데이터 끝
			
			
			$('#summernote').summernote({
				height : 300,
				minHeight : null,
				maxHeight : null,
				focus : true,
				toolbar: [
				    // [groupName, [list of button]]
				    ['style', ['bold', 'italic', 'underline', 'clear']],
				    ['font', ['strikethrough', 'superscript', 'subscript']],
				    ['fontsize', ['fontsize']],
				    ['color', ['color']],
				    ['para', ['ul', 'ol', 'paragraph']],
				    ['height', ['height']]
				  ]
			});

			$('#writeOk').click(function() {
				let board = false;
				let subject = false;
				let centent = false;
					
				//console.log($('#board').val());
				//console.log($('#subject').val());
				//console.log($('#summernote').val());
				
				if($('#board').val() == ""){
					alert("게시판을 선택하세요.");
				}else {
					board = true;
				}
				
				if($('#subject').val() == ""){
					alert("제목을 입력하세요.");
				}else {
					subject = true;
				}
				
				if($('#summernote').val() == ""){
					alert("내용을 입력하세요.");
				}else {
					centent = true;
				}
				
				let issubmit = board && subject && content;
				
				return issubmit;
			})
			
			if(${btype} == 3){
				var file = document.querySelector('#img');
	    		
	    	    file.onchange = function () { 
	    	        var fileList = file.files ;
	    	        
	    	        // 읽기
	    	        var reader = new FileReader();
	    	        reader.readAsDataURL(fileList [0]);
	    	
	    	        //로드 한 후
	    	        reader.onload = function  () {
	    	            document.querySelector('#preview').src = reader.result;
	    	        }; 
	    	    }; 
			};
			
		});
		
	</script>
    <div class="content-wrapper text-center margin-top0" style="background-image: url('${pageContext.request.contextPath}/assets/img/background.jpg');">
		<!-- 여기부터 -->
		<div class="container">
		
			<div class="row">
				<div class="col-md-12">
					<h1 class="page-head-line">글쓰기</h1>
				</div>
			</div>
			
			<form name="bbs" action="boardWriteOk.bdo" method="POST" enctype="multipart/form-data">
				<div class="row">
					<div class="col-md-2" style="text-align:center;">
						<c:if test="${requestScope.bcode != 1 && scssionScope.admin == 1}">
							<input type="checkbox" name="notice" value="true">&nbsp;&nbsp;공지사항
						</c:if>
					</div>
					<div class="col-md-5" style="text-align:center;">
						<!-- 게시판 종류 선택 -->
						<!-- 관리자의 경우 공지사항 게시판까지 선택가능 / 일반회원의 경우 공지사항 보이지 않음 -->
						<select class="btn btn-primary" id="board" name="bcode">
							<c:choose>
								<c:when test="${requestScope.bcode == 1}">
									<option value="1">공지게시판</option>
								</c:when>
								<c:otherwise>
									<option value="">게시판 선택</option>
									<c:forEach var="board" items="${requestScope.boardList}">
										<c:choose>
											<c:when test="${sessionScope.admin == 1}">
												<option value="${board.bcode}" <c:if test="${board.bcode == requestScope.bcode}">selected</c:if>>${board.bname}</option>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${board.bcode == 1}">
													</c:when>
													<c:otherwise>
														<option value="${board.bcode}" <c:if test="${board.bcode == requestScope.bcode}">selected</c:if>>${board.bname}</option>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</c:otherwise>
							</c:choose>
						</select>
					</div>
					
					<div class="col-md-5" style="text-align:center;">
						<!-- 말머리 선택 -->
						<c:if test="${requestScope.bcode != 1}">
							<select class="btn btn-primary" id="crname" name="classify">
							
							</select>
						</c:if>
						
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
											<td>제목</td>
											<td><input type="text" class="form-control" id="subject" name="subject"/></td>
										</tr>
										<tr>
											<td colspan="2"><textarea rows="10" cols="60" name="content" id ="summernote"></textarea></td>
										</tr>
										<c:if test="${requestScope.btype == 3}">
											<tr>
												<td width="20%"><img id="preview" src="${pageContext.request.contextPath}/assets/img/Logo3.png" width="100%" alt="미리보기" class="img" sizingMethod='scale'></td>
												<td><input type="file" name='img' id='img' placeholder="Image" class="form-control"></td>
											</tr>
										</c:if>
									</table>
								</div>
							</div>
						</div>
	
						<div class="row">
							<div class="col-md-12" style="text-align:center;">
								<input type="hidden" name="arcode" value="${arcode}">
								<input type="hidden" name="userid" value="${sessionScope.userid}">
								<input type="submit" class="btn btn-primary" value="글쓰기" id="writeOk">
								<input type="reset" class="btn btn-primary" value="다시쓰기">
							</div>
						</div>
						
					</div>	
				</div>
			</form>
			
    	</div>

		<!-- 여기까지 -->
	</div>
    <!-- CONTENT-WRAPPER SECTION END-->
    <c:import url="../common/bottom.jsp" />
</body>
</html>
