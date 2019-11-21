<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="../common/admin.jsp" />

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<c:import url="../common/top.jsp"></c:import>
	<script type="text/javascript">
		$(function(){
			var cp = 1;
			vat data = {"cp" : 1};
			
		});
	</script>	
	
    <div class="content-wrapper text-center margin-top0" style="background-image: url('${pageContext.request.contextPath}/assets/img/background.jpg');">
		<!-- 여기부터 -->

		<!-- 여기까지 -->
    
    </div>
    <!-- CONTENT-WRAPPER SECTION END-->
    
    <c:import url="../common/bottom.jsp"></c:import>
</body>
</html>
