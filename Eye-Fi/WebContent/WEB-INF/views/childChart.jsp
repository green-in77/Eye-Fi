<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="../common/member.jsp" />

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<c:import url="../common/top.jsp" />
	
	<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
		
	<script type="text/javascript">
		$(function(){			
			$.ajax({
				url : "sigun.ch",
				dataType: "JSON",
				type : "post",
				success : function(list){
					
					var sigunlist = '<option value="">지역 선택</option>';
					
					$.each(list,function(index,sigun){
						sigunlist += '<option value="'+sigun.arcode+'">'+sigun.sigunname+'</option>'

					}); //each 끝
					
					$('#sigunname').append(sigunlist);
					
					$('#sigunname').change(function() {
						arcode = $(this).val();
						console.log(arcode);
						
						$.ajax({
							url : "childListAjax.ch",
							data : {"arcode" : arcode},
							dataType: "JSON",
							type : "post",
							success : function(list){
								//console.log(list);
								var childint = 0;//총 어린이집 수
								var crstatusname = 0; //운영중인 어린이집 수
								var chcrtescnt = 0; //보육교사 수
								var crchcnt = 0; //현원
								var cctvinstlcnt = 0; //CCTV 총 설치수
								
								$.each(list,function(index,child){
									//console.log(child);
									childint++;
									if(child.crstatusname == "정상" || child.crstatusname == "재개"){
										crstatusname++;
										chcrtescnt += parseInt(child.chcrtescnt);
										crchcnt += parseInt(child.crchcnt);
										cctvinstlcnt += parseInt(child.cctvinstlcnt);
									}
									
								});
								
								//console.log("childint : " + childint);
								//console.log("crstatusname : " + crstatusname);
								//console.log("chcrtescnt : " + chcrtescnt);
								//console.log("crchcnt : " + crchcnt);
								//console.log("cctvinstlcnt : " + cctvinstlcnt);
								
								window.chartColors = {
									    red: 'rgb(255, 99, 132)',
									    orange: 'rgb(255, 159, 64)',
									    yellow: 'rgb(255, 205, 86)',
									    green: 'rgb(75, 192, 192)',
									    blue: 'rgb(54, 162, 235)',
									    purple: 'rgb(153, 102, 255)',
									    grey: 'rgb(201, 203, 207)'
								};
								
								var color = Chart.helpers.color;
								
								var barChartData = {
										labels: ['총 어린이집', '운영중인 어린이집', '교사', '현원', 'CCTV'],
										datasets: [{
											label: '지역별',
											backgroundColor: color(window.chartColors.red).alpha(0.5).rgbString(),
											borderColor: window.chartColors.red,
											borderWidth: 1,
											data: [
												childint,
												crstatusname,
												chcrtescnt,
												crchcnt,
												cctvinstlcnt
											]
										}]

								};
								
								var ctx = document.getElementById('canvas').getContext('2d');
								
								window.myBar = new Chart(ctx, {
									type: 'bar',
									data: barChartData,
									options: {
										responsive: true,
										legend: {
											position: 'top',
										}
									}
								});
								
							},
							error : function(xhr){
								console.log(xhr.status);
							}
						});//차트를 위한 ajax끝
						
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
					<h1 class="page-head-line">지역별 통계</h1>
				</div>
			</div>
			
			<div class="row">
				<div class="col-md-3">
					<!-- 게시판 리스트 -->
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="panel-body">
	                            <select id = "sigunname">
	                            	
                                </select>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-9" style="width:70%; height:500px;">
					<canvas id="canvas"></canvas>
				</div>
			</div>	

		</div>

		<!-- 여기까지 -->
	</div>
    <!-- CONTENT-WRAPPER SECTION END-->
    <c:import url="../common/bottom.jsp" />
</body>
</html>
