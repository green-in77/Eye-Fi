<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 	<!-- BOOTSTRAP CORE STYLE  -->
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
    <!-- FONT AWESOME ICONS  -->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
    <!-- CUSTOM STYLE  -->
    <link href="assets/css/style.css" rel="stylesheet" />
     <!-- HTML5 Shiv and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <!-- CORE JQUERY SCRIPTS -->
    <script src="assets/js/jquery-1.11.1.js"></script>
    <!-- BOOTSTRAP SCRIPTS  -->
    <script src="assets/js/bootstrap.js"></script>
    
    <script type="text/javascript">
		$(function(){
	        let pw_pattern = /^[a-z0-9_]{4,10}$/;
	      	//a~z,0~9까지 입력가능 4자~10자 이내로

	        let pw_check = false;
	        let pwck_check = false;
			
			//pw 유효성체크
			$('#userpwEdit').keyup(function() {
				//console.log($(this).val());
				if (pw_pattern.test($(this).val()) != true){
					$('#pwcheckEdit').text("비밀번호가 조건에 일치하지 않습니다.");
				}else {
					$('#pwcheckEdit').text("사용가능한 비밀번호 입니다..");
					pw_check = true;
				}
			});//이벤트 끝
			
			//pw재입력 체크
			$('#userpwCkEdit').keyup(function() {
				if($('#userpwEdit').val() != $('#userpwCkEdit').val()){
					$('#pwckcheckEdit').text("비밀번호가 일치하지 않습니다.");
				}else {
					$('#pwckcheckEdit').text("비밀번호가 확인되었습니다.");
					pwck_check = true;
				}
			});//이벤트 끝
			
			
			//전송
			$('#submitEdit').click(function() {

				let issubmit = pw_check && pwck_check;
				//console.log(issubmit);
				
				if(issubmit) {
					var url = $('#editForm').attr('action');
					var data = {
							"userid" : $('#useridEdit').val(),
							"userpw" : $('#userpwEdit').val()
						};
					
					$.ajax({
						url : url,
						data : data,
						type : "POST",
						dataType: "text",
						success: function(data){
							console.log(data.trim());
							if(data.trim() == "true"){
								alert("수정이 완료되었습니다.");
							}else {
								alert("수정이 실패했습니다.");
							}
							window.location.href = "${pageContext.request.contextPath}/index.jsp";
						},
						error : function(xhr){
							
						}
					});
					
				}else {
					return false;
					alert("입력이 완료되지 않았습니다.");
				}
				return false;
			});//이벤트 끝
		});
	</script>

	<!-- 정보수정 -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">정보수정</h4>
				</div>
				<form role="form" id="editForm" name="editForm" action="memberEditOk.do" method="post">
					<div class="modal-body">
							<c:set var="member" value="${requestScope.member}"></c:set>
							<div class="form-group">
								<label class="control-label" for="userid">아이디</label>
								<input type="text" class="form-control" id="useridEdit" name="userid" value='${member.userid}' readonly/>
							</div>
							
							<div class="form-group">
								<label class="control-label" for="userpw">비밀번호</label>
								<input type="password" class="form-control" id="userpwEdit" name="userpw"/>
								<small id="pwcheckEdit">비밀번호는 4자~10자 입니다.</small>
							</div>
							
							<div class="form-group">
								<label class="control-label" for="userpwCk">비밀번호 확인</label>
								<input type="password" class="form-control" id="userpwCkEdit" />
								<small id="pwckcheckEdit">비밀번호를 다시한번 입력해주세요.</small>
							</div>
							
							<div class="form-group">
								<label class="control-label" for="email">이메일</label>
								<input type="text" class="form-control" id="emailEdit" name="email" value='${member.email}' readonly />
							</div>
						
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
						<button type="button" class="btn btn-primary" id="submitEdit">정보수정</button>
					</div>
				</form>
