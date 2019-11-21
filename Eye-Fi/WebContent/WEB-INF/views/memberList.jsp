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
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h1 class="page-head-line">회원 관리</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-md-8" style="text-align:right;">
					
				</div>
				<div class="col-md-3" style="text-align:right;">
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
											<th>아이디</th>
											<th>비밀번호</th>
											<th>이메일</th>
											<th>관리자</th>
											<th>활동여부</th>
											<th colspan="2">회원관리</th>
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
				</div>	
			</div>
    	</div>
   	
		<!-- 여기까지 -->
	</div>
    <!-- CONTENT-WRAPPER SECTION END-->
    <c:import url="../common/bottom.jsp" />
</body>
<script type="text/javascript">
	$(function(){
		var cp = 1;
		var cp = {"cp" : 1};
		var memberlist = "";
		var url = "memberListOk.do";
		var codelist = "";
		
		list(url,cp);
		totalcount("memberTotalCount.do");
			
		//회원리스트 관련
		function list(url,cp){
			$.ajax({ //리스트 비동기 조회
				url : url,
				data : cp,
				dataType: "JSON",
				type: "POST",
				success: function(list){
					//console.log(list);
					$('#tbody').empty();
					memberlist = "";
					if(list != ""){
						$.each(list, function(index, member){
							//console.log((member.admin==1)?"관리자":"일반회원");
							var admin = (member.admin==1)?"관리자":"일반회원";
							memberlist += '<tr>'
											+'<td>'+member.userid+'</td>'
											+'<td>'+member.userpw+'</td>'
											+'<td>'+member.email+'</td>'
											+'<td>'+admin+'</td>'
											+'<td>'+member.status+'</td>'
											+'<td colspan="2"><a class="edit btn btn-primary"><i class="fa fa-edit "></i>관리</a></td>'
										  +'</tr>'
						});//each 끝
							
						//활성화 코드
						$.ajax({
							url:"code.do",
							dataType: "JSON",
							success: function(codeList){
								codelist = "";
								//console.log(codeList);
								$.each(codeList, function(index,code){
									//console.log(code);	
									codelist += '<option value="'+code.code+'">'+code.status+'</option>'
								});
								//console.log(codelist);
							}
						});
						
					}else {
						memberlist = "데이터가 없습니다.";
					}
					//document.getElementById('tbody').innerHTML = memberlist;
					$('#tbody').append(memberlist);
						
					clickEvent();	
				},
				error : function(xhr){
					console.log(xhr.status);
				}
			});//ajax
		};//list function 끝
	
//-----------------------------------------------------------------------------------------------------------------------
		//ajax 이후 클릭이벤트 모음
		function clickEvent(){
			//수정버튼 클릭
			$('.edit').click(function(){
	
				var tr = $(this).closest('tr'); // 나와 조상요소 중 첫번째 tr //.parent() 바로 상위요소 찾기
				var userid = {"userid" : tr.children().html()}; //나와 조상요소 중 첫번째 tr의 자식의 값
				//console.log(userid);
				tr.empty(); //선택한 줄 비우기
						
				$.ajax({
					url:"memberSelectId.do",
					data:userid,
					type: "POST",
					dataType: "JSON",
					success: function(memberList){
						//console.log(memberList);
						var td = "";
						
						$.each(memberList, function(index, member){
							var admin = (member.admin==1)?"관리자":"일반회원";
							if(member != null) {
								td += '<td><input type="text" class="form-control form-align" value="'+member.userid+'" readonly></td>'
										+'<td><input type="text" class="form-control form-align" value="'+member.userpw+'"></td>'
										+'<td><input type="text" class="form-control form-align" value="'+member.email+'"></td>'
										+'<td>'
											+'<select class="btn btn-primary">'
												+'<option value="0">일반회원</option>'
												+'<option value="1">관리자</option>'
										+'</select></td>'
										+'<td>'
											+'<select class="btn btn-primary">';
												//console.log(codelist);
												td += codelist + '</select></td>'
										+'<td><a class="editok btn btn-primary">완료</a></td>'
										+'<td><a class="cancel btn btn-primary">취소</a></td>';
											//console.log(td);
							}else {
								alert("잘못선택하셨습니다.");
							}	
						});//each 끝
								
						$(tr).append(td);
								
						//완료 버튼 클릭시
						$('.editok').click(function() {
							//console.log(cp);
							var tr = $(this).closest('tr'); // 나와 조상요소 중 첫번째 tr
							var editdata = {
								"userid" : tr.find("td:eq(0)").children().val(),
								"userpw" : tr.find("td:eq(1)").children().val(),
								"email" : tr.find("td:eq(2)").children().val(),
								"admin" : tr.find("td:eq(3)").children().val(),
								"code" : tr.find("td:eq(4)").children().val()
							};
							//console.log(editdata);
							$.ajax({
								url:"adminMemberEditOk.do",
								data : editdata,
								success: function(){
									//console.log(cp);
									list(url,cp);
								}
							});
						});//수정완료 클릭이벤트 끝
						
						//취소버튼 클릭시
						$('.cancel').click(function() {
							list(url,cp);
						});
					},
					error : function(xhr){
						console.log(xhr.status);
					}
				});//ajax 끝
			});//edit 클릭이벤트 끝
		}//함수끝	
//////---------------------------------------------------------------------------------------------------------------------			
			//페이징처리
			function totalcount(totalcount,total){
				$.ajax({
					url:totalcount,
					data : total,
					dataType: "text",
					type:"POST",
					success: function(count){
						//console.log(count.trim());
						$('#page').empty();
						count = count.trim();
						
						if(count%2 == 0 ){
							count = count/4;
						}else {
							count = count/4 +1;
						}
						//console.log(count);
						count = Math.ceil(count);
						//console.log(count);
						var page = '<span style="padding:4px;" class="page btn btn-primary" id="first">처음</span>';
						for(var i = 1; i <= count ; i++){
							page += '<span style="padding:4px;" class="page btn btn-primary" id="paging'+i+'">'+i+'</span>'
						}
						page += '<span style="padding:4px;" class="page btn btn-primary" id="next">마지막</span>'
						
						$('#page').append(page);
						
						$('.page').click(function(){
							//console.log(url);
							var cur = $(this).text(); //클릭한 페이지
							
							//console.log(cur);
							
							if(cur == "처음"){
								cp = {"cp":1};
							}else if(cur == "마지막"){
								cp = {"cp":count};
							}else {
								cp = {"cp":(cur)};
							}
							//console.log(cp);
							
							list(url,cp);
						});
					},
					error : function(xhr){
						console.log(xhr.status);
					}
				});//ajax 끝
			}//totalcount 끝
	});//onload 끝
	</script>
</html>
