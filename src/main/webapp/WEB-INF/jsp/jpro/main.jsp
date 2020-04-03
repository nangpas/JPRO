<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE HTML>
<html lang="ko">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>홈</title>
<!-- css -->
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/semantic/semantic.min.css"/>" />
<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/fullpage/fullpage.css"/>" />

<!-- js -->
<script type="text/javascript" src="<c:url value="/resources/js/common/jquery-3.4.1.min.js"/>"></script>
<script type="text/javascript" src=" <c:url value="/resources/js/common/jquery.form.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/semantic/semantic.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/fullpage/fullpage.js"/>"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jpro_default.0.1.js"/>"></script>
</head>
<body>
<div>
	<div id="fullpage">
		<div class="section">page1</div>
		<div class="section">page2</div>
		<div class="section">page3</div>
		<div class="section">page4</div>
	</div>
	<div id="menu">
		<ul>
		</ul>
	</div>
</div>


<script>
$(document).ready(function(){
	$('#fullpage').fullpage({
		licenseKey: 'OPEN-SOURCE-GLPV3-LICENSE',
		autoScrolling: true,
		scrollHorizontally:true
	});
});
</script>
</body>

</html>