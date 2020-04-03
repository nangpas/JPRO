<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE HTML>
<html lang="ko">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>Test</title>
	
	<script type="text/javascript" src="<c:url value="/resources/js/common/jquery-3.4.1.min.js"/>"></script>
    <script type="text/javascript" src=" <c:url value="/resources/js/common/jquery.form.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jpro_default.0.1.js"/>"></script>
</head>
<body>
<div>
	<img id="img_form_url" src="">
	<img id="asdf" src="data:image/gif;base64,R0lGODlhAQABAIAAAP///////yH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==">
	<input type="text" id="keyward"/>
	<button id="btn_srch" onclick="srchKeyward()">검색</button>
	<button id="btn_srch" onclick="srchKeyward2()">검색2</button>
	<div id="temp">
	</div>
	
	
</div>

<script>
function srchKeyward(){
	if($("#keyward").val() == '' || $("#keyward").val() == null){
		alert("키워드를 입력해주세요");
		return false;
	}
	var ajax_set = {
			url:"<c:url value='/jpro/testCrawling.do'/>",
			param: "KEYWARD=" + $("#keyward").val(),
			return_fn:function(jdata){
				var str = '';
				for(var i=0; i<jdata.length; i++){
					str += "<img src=\"" + jdata[i] + "\"/>";
				}
				$("#temp").clear();
				$("#temp").append(str);
			}
	};
	
	won_getAjax(ajax_set);
}

function srchKeyward2(){
	var ajax_set = {
			url:"<c:url value='/jpro/testCrawling2.do'/>",
			param: "KEYWARD=" + $("#keyward").val(),
			return_fn:function(jdata){
				console.log(jdata + '');
			}
	};
	
	won_getAjax(ajax_set);
}
	
</script>
</body>
</html>
