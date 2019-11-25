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
	
	<script type="text/javascript">
		$(function(){
			
			//말머리 ajax
			/* $.ajax({
				url : "http://api.childcare.go.kr/mediate/rest/cpmsapi030/cpmsapi030/request",
				data : "key"
			}); *///말머리 ajax 끝
			
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
				let subject = false;
				let centent = false;
					
				//console.log($('#subject').val());
				//console.log($('#summernote').val());

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
				
				let issubmit = subject && content;
				
				return issubmit;
			})
			
		});

	</script>
    <div class="content-wrapper text-center margin-top0" style="background-image: url('${pageContext.request.contextPath}/assets/img/background.jpg');">
		<!-- 여기부터 -->
		<div class="container">
		
			<div class="row">
				<div class="col-md-12">
					<h1 class="page-head-line">답글쓰기</h1>
				</div>
			</div>
			
			<form name="bbs" action="reboardWriteOk.bdo" method="POST">
				<div class="row">
					<div class="col-md-12">
						<!--    Hover Rows  -->
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="table-responsive">
									<table class="table">
										<tr>
											<td>제목</td>
											<td><input type="text" class="form-control" id="subject" name="subject"/ value="[RE]${requestScope.subject}"></td>
										</tr>
										<tr>
											<td colspan="2"><textarea rows="10" cols="60" name="content" id ="summernote">원본글<br>${requestScope.content}-----------<br></textarea></td>
										</tr>
									</table>
								</div>
							</div>
						</div>
	
						<div class="row">
							<div class="col-md-12" style="text-align:center;">
								<input type="hidden" name="seq" value="${requestScope.seq}">
								<input type="hidden" name="userid" value="${sessionScope.userid}">
								<input type="hidden" name="bcode" value="${requestScope.bcode}">
								<input type="submit" class="btn btn-primary" value="답글쓰기" id="writeOk">
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
