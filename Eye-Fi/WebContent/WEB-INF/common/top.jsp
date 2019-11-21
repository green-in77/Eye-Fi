<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <!--[if IE]>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <![endif]-->
    <title>Eye-Fi</title>
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
			let id_pattern = /^[a-z0-9_]{4,6}$/;
	        //a~z,0~9까지 입력가능 4자~6자 이내로
	        let pw_pattern = /^[a-z0-9_]{4,10}$/;
	      	//a~z,0~9까지 입력가능 4자~10자 이내로
	        let email_pattern = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
	        //0부터9a부터zA부터Z까지 (-, _, . 가 있어도 되고 없어도 되고, 0부터9a부터zA부터Z까지)반복횟수 상관없이가능
	        //@기호포함  (-, _, . 가 있어도 되고 없어도 되고, 0부터9a부터zA부터Z까지)반복횟수 상관없이가능 .기호포함 2자~3자 이내 대소문자 구분안함
	      		      
	        let id_check = false;
	        let pw_check = false;
	        let pwck_check = false;
	        let email_check = false;
	        
	        //id 유효성 및 중복체크
			$('#userid').keyup(function() {
				if(id_pattern.test($(this).val()) != true){
					$('#idcheck').text("아이디가 조건에 일치하지 않습니다.");
					id_check=false;
					
				}else {
					$.ajax({
						url:"idCheck.do",
						type:"post",
						dataType: "html",
						data : {"userid" : $('#userid').val()},
						success: function(result){
							//console.log($('#userid').val());
							//console.log(result.trim());
							if(result.trim() != "false"){
								$('#idcheck').text("사용가능한 ID입니다.");
								id_check=true;
							}else {
								$('#idcheck').text("이미 존재하는 ID입니다.");
								$('#userid').focus();
							}
						},
						error : function(xhr){
							alert(xhr.status);
						}
					}); //ajax 끝
				}//if else 끝
			});//이벤트 끝
			
			//pw 유효성체크
			$('#userpw').keyup(function() {
				if (pw_pattern.test($(this).val()) != true){
					$('#pwcheck').text("비밀번호가 조건에 일치하지 않습니다.");
				}else {
					$('#pwcheck').text("사용가능한 비밀번호 입니다..");
					pw_check = true;
				}
			});//이벤트 끝
			
			//pw재입력 체크
			$('#userpwCk').keyup(function() {
				if($('#userpw').val() != $('#userpwCk').val()){
					$('#pwckcheck').text("비밀번호가 일치하지 않습니다.");
				}else {
					$('#pwckcheck').text("비밀번호가 확인되었습니다.");
					pwck_check = true;
				}
			});//이벤트 끝
			
			//email 유효성체크 
	        $('#email').keyup(function(){
	            if(email_pattern.test($(this).val()) != true){
	                $('#emailcheck').text("이메일 형식에 맞지 않습니다.");
	            }else{
	                $('#emailcheck').text("이메일 형식에 맞습니다.");
	                $('#emailCheck').removeAttr('disabled');
	            }
	        });
			
			//이메일 인증
			$('#emailCheck').click(function() {
				//이메일이 입력되면 새창으로 이동
				if($('#emailCheck').val() != null){
					var url = "emailCheck.do?email="+$('#email').val();
					//console.log(url);
					open(url,"Email Check","statusber=no, scrollbar=no, menuber=no, width=400, height=130");
				}else {
					alert("이메일이 입력되지 않았습니다.");
				}
			});//이벤트 끝
			
			//전송
			$('#submit').click(function() {
				console.log($('#emailCheckReturn').val());
				
				if($('#emailCheckReturn').val() == "true"){
					email_check = true;
				}
				let issubmit = id_check && pw_check && pwck_check && email_check;
				console.log(issubmit);
				
				return issubmit;
			});//이벤트 끝			
		});
	</script>
</head>
<body>
	
    <div class="navbar navbar-inverse set-radius-zero">
        <div class="container">
        
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>

                <a class="navbar-brand" href="index.jsp">
                    <img src="assets/img/Logo3.png" style="width: 45%"/>
                </a>
            </div>
            
			<c:if test="${sessionScope.userid != null}">
	            <div class="left-div">
	                <div class="user-settings-wrapper" style="text-align: right;">
	                	<ul class="nav">
	                		<li class="dropdown">
			                	<img src="assets/img/member.jpg" alt="member" class="img-rounded" data-toggle="dropdown" aria-expanded="false"/>
			                	<div class="dropdown-menu dropdown-settings">
									<div class="media">
										<div class="media-body">
											<h4 class="media-heading">${sessionScope.userid}</h4>
											<c:if test="${sessionScope.admin == 1}">
												<h5>관리자</h5>
											</c:if>	
										</div>
									</div>
									<hr>
									<a href = "memberEdit.do?userid=${sessionScope.userid}" data-toggle="modal" data-target="#edit">
									<button class="btn btn-primary" id="memberEdit" ><i class="fa fa-edit"></i>정보수정</button></a>
								</div>
							</li>
	                    </ul>
	                </div>
	            </div>
            </c:if>
            
        </div>
    </div>
    <!-- LOGO HEADER END-->
    <section class="menu-section">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                
                    <div class="navbar-collapse collapse ">
                        <ul id="menu-top" class="nav navbar-nav navbar-right">
                            <li><a href="index.jsp">Eye-Fi</a></li>
                            <li><a href="">어린이집</a></li>
                            <c:choose>
                            	<c:when test="${sessionScope.userid == null}">
                            		<li><a data-toggle="modal" data-target="#login">로그인</a></li>
                            		<li><a data-toggle="modal" data-target="#join">회원가입</a></li>	
                            	</c:when>
                            	<c:when test="${sessionScope.admin == 1}">
                            		<li><a href="">게시판</a></li>
                            		<li><a href="memberList.do">회원관리</a></li>
                            		<li><a href="logout.do">로그아웃</a></li>
                            	</c:when>
                            	<c:otherwise>
                            		<li><a href="">게시판</a></li>
                            		<li><a href="logout.do">로그아웃</a></li>
                            	</c:otherwise>
                            </c:choose>
                        </ul>
                    </div>
                    
                </div>
            </div>
        </div>
    </section>
    <!-- MENU SECTION END-->
    
    <!-- 로그인  -->
    <div class="modal fade" id="login" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
		<div class="modal-dialog1">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">로그인</h4>
				</div>
				<form role="form" id="loginForm" action="login.do" method="post">
					<div class="modal-body">
						
							<div class="form-group">
								<label class="control-label" for="userid">아이디</label>
								<input type="text" class="form-control" id="loginUserid" name="userid">
							</div>
							
							<div class="form-group">
								<label class="control-label" for="userpw">비밀번호</label>
								<input type="password" class="form-control" id="loginUserpw" name="userpw">
							</div>
						
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
						<button type="submit" class="btn btn-primary">로그인</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<!-- 회원가입 -->
    <div class="modal fade" id="join" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
		<div class="modal-dialog2">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h4 class="modal-title" id="myModalLabel">회원가입</h4>
				</div>
				<form role="form" id="joinForm" name="joinForm" action="join.do" method="post">
					<div class="modal-body">
						
							<div class="form-group">
								<label class="control-label" for="userid">아이디</label>
								<input type="text" class="form-control" id="userid" name="userid"/>
								<small id="idcheck">아이디는 4자~6자 입니다.</small>
							</div>
							
							<div class="form-group">
								<label class="control-label" for="userpw">비밀번호</label>
								<input type="password" class="form-control" id="userpw" name="userpw"/>
								<small id="pwcheck">비밀번호는 4자~10자 입니다.</small>
							</div>
							
							<div class="form-group">
								<label class="control-label" for="userpwCk">비밀번호 확인</label>
								<input type="password" class="form-control" id="userpwCk" />
								<small id="pwckcheck">비밀번호를 다시한번 입력해주세요.</small>
							</div>
							
							<div class="form-group">
								<label class="control-label" for="email">이메일</label>
								<input type="text" class="form-control" id="email" name="email" />
								<small id="emailcheck">이메일을 입력해주세요.</small>
								<div style="text-align: right;">
									<button type="button" class="btn btn-primary" id="emailCheck" disabled='disabled'>인증</button>
								</div>
								<input type="hidden" id="emailCheckReturn" name="emailCheckReturn" value="false">
							</div>
						
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
						<button type="submit" class="btn btn-primary" id="submit">회원가입</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<!-- modal -->
	<div class="modal fade" id="edit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
		<div class="modal-dialog2">
			<div class="modal-content">
			
			</div>
		</div>
	</div>
	