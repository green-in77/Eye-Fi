<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<c:import url="../common/top.jsp" />
	
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=5d00399e8d45fb8607fd0eec1c06e2ce&libraries=services"></script>
	<c:set var="stcode" value="${requestScope.stcode}" />
	<c:set var="arcode" value="${requestScope.arcode}" />
	
	<script type="text/javascript">
	$(function(){
		var la = "";
		var lo = "";
		
		$.ajax({
			url : 'childListAjax.ch',
			data : {"arcode" : ${arcode},
					"stcode":${stcode}},
			dataType : "json",
			type : "get",
			success : function(list){
				//console.log(list.item);
				
				$('#crname').text(list.item.crname);
				$('#crtypename').text(list.item.crtypename);
				$('#crtelno').text(list.item.crtelno);
				$('#crfaxno').text(list.item.crfaxno);
				$('#craddr').text(list.item.craddr);
				$('#chcrtescnt').text(list.item.chcrtescnt);
				$('#cntpat').text(list.item.crchcnt+"/"+list.item.crcapat);
				$('#crhome').html("<a href='"+list.item.crhome+"' target=_blank>"+list.item.crhome+"</a>");
				$('#crcargbname').text(list.item.crcargbname);
				$('#crabldt').text(list.item.crabldt);
				$('#datastdrdt').text("기준일자 : "+list.item.datastdrdt);
				
				la = list.item.la;
				lo = list.item.lo;
				
				//주소로 좌표를 받기위해...
				var coords;
				
				var container = document.getElementById('map');
				
				var options = {
					center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
					level: 2
				};
				
				var map = new kakao.maps.Map(container, options);
				
				// 주소-좌표 변환 객체를 생성합니다
				var geocoder = new kakao.maps.services.Geocoder();
					
				// 주소로 좌표를 검색합니다
				geocoder.addressSearch(list.item.craddr, function(result, status) {

					// 정상적으로 검색이 완료됐으면 
					if (status === kakao.maps.services.Status.OK) {
						
						if( la == "" || lo == ""){
							coords = new kakao.maps.LatLng(result[0].y, result[0].x);
						}else {
							coords = new kakao.maps.LatLng(la, lo);
						}
						// 결과값으로 받은 위치를 마커로 표시합니다
						var marker = new kakao.maps.Marker({
							map: map,
							position: coords
						});
	
						// 인포윈도우로 장소에 대한 설명을 표시합니다
						var infowindow = new kakao.maps.InfoWindow({
							content: '<div style="width:150px;text-align:center;padding:6px 0;">'+list.item.crname+'</div>'
						});
						infowindow.open(map, marker);
	
						// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
						map.setCenter(coords);
					} 
				});    
						
			},
			error : function(xhr){
				console.log(xhr.status);
			}
		});//외부데이터 끝
		
		

		
	});
	</script>
    <div class="content-wrapper text-center margin-top0" style="background-image: url('${pageContext.request.contextPath}/assets/img/background.jpg');">
		<!-- 여기부터 -->
		
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h1 class="page-head-line">어린이집 상세정보</h1>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<!--    Hover Rows  -->
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table">
									<tr>
										<td>어린이집</td>	
										<td id="crname"></td>
									</tr>
									<tr>
										<td>어린이집 유형</td>
										<td id="crtypename"></td>
									</tr>
									<tr>
										<td>전화번호</td>
										<td id="crtelno"></td>
									</tr>
									<tr>
										<td>팩스번호</td>
										<td id="crfaxno"></td>
									</tr>
									<tr>
										<td>주소</td>
										<td id="craddr"></td>
									</tr>
									<tr>
										<td>교직원 수</td>
										<td id="chcrtescnt"></td>
									</tr>
									<tr>
										<td>현원 / 정원</td>
										<td id="cntpat"></td>
									</tr>
									<tr>
										<td>홈페이지</td>
										<td id="crhome"></td>
									</tr>
									<tr>
										<td>통학차량</td>
										<td id="crcargbname"></td>
									</tr>
									<tr>
										<td>폐지일자</td>
										<td id="crabldt"></td>
									</tr>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6" id="map" style="width:40%;height:400px;">
				</div>
			</div>
			<div class="row">
				<div class="col-md-12" style="text-align:right;" id="datastdrdt">
				</div>
			</div>		
			
			<div class="row">
				<div class="col-md-12" style="text-align:center;">
					<form action="childList.ch" method="post">
						<input type="submit" class="btn btn-primary" value="목록가기">
					</form>
				</div>
			</div>
			
    	</div>

		<!-- 여기까지 -->
	</div>
    <!-- CONTENT-WRAPPER SECTION END-->
    <c:import url="../common/bottom.jsp" />
</body>
</html>
