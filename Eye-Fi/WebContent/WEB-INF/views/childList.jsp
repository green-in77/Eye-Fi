<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<c:import url="../common/top.jsp" />
	
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.css">
	<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.js"></script>
	
	<script type="text/javascript">
		$(function(){
			var arcode = 11110;
			
			$.ajax({
				url : "sigun.ch",
				dataType: "JSON",
				type : "post",
				success : function(list){
					
					var table = $('#childTable').DataTable({
						ajax : {
							url : 'childListAjax.ch',
							data : function(d){
								d.arcode = arcode;
							},
							dataSrc : ''
						},
						columns : [
							{"data" : 'crname',
								 "render": function(data, type, row, meta){
									 //console.log(data);
								     if(type === 'display'){
								        data = '<a href="childContent.ch?arcode='+arcode+'&stcode='+row.stcode+'">' + data + '</a>';
								     }
								     return data;

								    }
							},
							{"data" : "craddr"},
							{"data" : 'crstatusname'},
							{"data" : 'crtelno'}
						]
					});
					
					//console.log(list);
					var sigunlist = "";
					
					$.each(list,function(index,sigun){
						sigunlist += '<option value="'+sigun.arcode+'">'+sigun.sigunname+'</option>'

					}); //each 끝
					
					$('#sigunname').append(sigunlist);
					
					$('#sigunname').change(function() {
						arcode = $(this).val();
						//console.log(arcode);
						table.ajax.reload();
					});//클릭이벤트 끝
					
				},
				error : function(xhr){
					console.log(xhr.status);
				}
			}); //sigun ajax 끝
			
		});//onload 끝	
	</script>
    <div class="content-wrapper text-center margin-top0" style="background-image: url('${pageContext.request.contextPath}/assets/img/background.jpg');">
		<!-- 여기부터 -->
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h1 class="page-head-line">어린이집 리스트</h1>
				</div>
			</div>
			
			<div class="row">
				<div class="col-md-2">
					<!-- 게시판 리스트 -->
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="panel-heading">
                            	지역선택
                        	</div>
							<div class="panel-body">
	                            <select id = "sigunname">
                                </select>
                                
                                <hr>
                                <c:if test="${sessionScope.userid != null}">
	                                <div class="panel-heading">
										<h4 class="panel-title">
			                              	<!-- 카테고리  -->
			                                 <a href="childChart.ch" class="collapsed">통계차트</a>
			                            </h4>
			                        </div>
                                </c:if>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-10">
					<!--    datatble -->
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-hover" id="childTable">
									<thead>
										<tr id="crname"></tr>
										<tr>
											<th>어린이집명</th>
											<th>주소</th>
											<th>운영</th>
											<th>전화번호</th>
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

    	</div>

		<!-- 여기까지 -->
	</div>
    <!-- CONTENT-WRAPPER SECTION END-->
    <c:import url="../common/bottom.jsp" />
</body>
</html>
