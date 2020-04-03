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
	<form id="excelUploadForm" name="excelUploadForm" enctype="multipart/form-data" method="post" action="">
	    <div class="contents">
	        <div>첨부파일은 한개만 등록 가능합니다.</div>
	 
	        <dl class="vm_name">
	                <dt class="down w90">첨부 파일</dt>
	                <dd><input id="excelFile" type="file" name="excelFile" /></dd>
	        </dl>        
	    </div>
	            
	    <div class="bottom">
	        <button type="button" id="addExcelImpoartBtn" class="btn" onclick="check()"><span>추가</span></button> 
	    </div>
	</form>
</div>

<script>

	function checkFileType(filePath) {
	    var fileFormat = filePath.split(".");
	    if (fileFormat.indexOf("xlsx") > -1) {
	        return true;
	    } else {
	        return false;
	    }
	
	}
	
	function check() {
	    var file = $("#excelFile").val();
	    if (file == "" || file == null) {
	        alert("파일을 선택해주세요.");
	        return false;
	    } else if (!checkFileType(file)) {
	        alert("엑셀 파일만 업로드 가능합니다.");
	        return false;
	    }
	
	    if (confirm("업로드 하시겠습니까?")) {
	    	$("#excelUploadForm").ajaxSubmit({
	            type:"POST",
	            url:"<c:url value='/jpro/excelTest.do'/>",
	            contentType : "application/x-www-form-urlencoded;charset=UTF-8",
	            beforeSubmit: function (data,form,option) {
	                return true;
	            },
	            error: function(e){
	           		alert('에러발생');
	            },
	            success:function(jdata){
	            	alert('성공');
	            }
	     	});
	
	    }
	}
</script>
</body>
</html>
