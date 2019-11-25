<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="../common/member.jsp" />

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<c:import url="../common/top.jsp" />
	
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.20/css/jquery.dataTables.css">
	<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.20/js/jquery.dataTables.js"></script>
	
	<script type="text/javascript">
		$(function(){			
			var table = $('#childTable').DataTable({
				ajax : {
					url : 'childListAjax.ch?arcode=11260',
					dataSrc : ''
				},
				columns : [
					{"data" : 'crname',
						 "render": function(data, type, row, meta){
							 //console.log(data);
						     if(type === 'display'){
						        data = '<a href="childContent.ch?stcode='+row.stcode+'">' + data + '</a>';
						     }
						     return data;

						    }
					},
					{"data" : "craddr"},
					{"data" : 'crstatusname'},
					{"data" : 'crtelno'}
				]
			});
	
		});//onload 끝	
	</script>
    <div class="content-wrapper text-center margin-top0" style="background-image: url('${pageContext.request.contextPath}/assets/img/background.jpg');">
		<!-- 여기부터 -->
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h1 class="page-head-line">어린이집</h1>
				</div>
			</div>
			
			<div class="row">
				
				<div class="col-md-12">
					<!--    Hover Rows  -->
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-hover" id="childTable">
									<thead>
										<tr id="crname"></tr>
										<tr>
											<th>어린이집명</th>
											<th>주소</th>
											<th>운영여부</th>
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
