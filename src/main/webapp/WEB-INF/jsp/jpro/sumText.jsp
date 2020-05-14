<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE HTML>
<html lang="ko">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>텍스트 합치기!</title>
	<script type="text/javascript" src="<c:url value="/resources/js/common/jquery-3.4.1.min.js"/>"></script>
    <script type="text/javascript" src=" <c:url value="/resources/js/common/jquery.form.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jpro_default.0.1.js"/>"></script>
    <!-- semantic ui -->
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/semantic/semantic.min.css"/>"/>
	<script type="text/javascript" src="<c:url value="/resources/js/semantic/semantic.min.js"/>"></script>
	
	<link type="text/css" rel="stylesheet" href="<c:url value="/resources/css/test.css"/>"/>
</head>
<body>
<div class="wrap">
	<div class="context">
		<h2 class="ui header">텍스트파일 합치기(*.txt만 가능)</h2>
		<div class="ui raised segment">
			<form name="frmS" id="frmS" method="post" enctype="multipart/form-data"	action="<c:url value='/jpro/sumTextFile.do'/>">
				<div class="flie">
					<div id="fileDiv">
					</div>
				</div>
				<button type="button" class="ui button" onclick="addFile();">파일 추가</button>
				<div class="submit">
					<button class="ui primary button">합친 후 다운로드</button>
				</div>
			</form>
			
		</div>
	</div>
	<div class="ui divider"></div>
	<div class="context">
		<h2 class="ui header">엑셀 특정단어 포함 행 뽑기!(*.xlsx만 가능)</h2>
		<div class="ui raised segment">
			<form id="excelUploadForm" name="excelUploadForm" enctype="multipart/form-data" method="post" action="<c:url value='/jpro/extExcelFile.do'/>">
				<p><input type="file" id="excelFile" name="excelFile" onchange="checkFileExcel(this);"/></p>
				<div class="ui input">
				  <input type="text" id="word" name="word" placeholder="포함할 단어">
				</div>
				<div class="ui input">
				  <input type="text" id="column" name="column" placeholder="행이름(ex:A,B,C,D,E,F,G)">
				</div>
				<div class="submit">
					<button class="ui primary button">추출 및 다운로드</button>
				</div>
			</form>
		</div>
	</div>
	<div class="ui divider"></div>
	<div class="context">
		<h2 class="ui header">추가 기능 개발 예정</h2>
		<div class="ui raised segment">
		</div>
	</div>
</div>
<script>
//첨부된 파일 개수
var gfv_count = 0;

function addFile(){
	var filesChk = $("input[name='file_"+ gfv_count +"']").val();
    if(filesChk == ""){
           alert("파일을 선택하세요.");
           return false;
    }
    
    ++gfv_count;
	
	var str = "<p><input type='file' id='file_"+(gfv_count)+"' name='file_"+(gfv_count)+"' onchange='checkFile(this);'><button type='button' class='ui inverted red button' onclick='deleteFile(this)'>삭제</button></p>";
	$("#fileDiv").append(str);
}

function deleteFile(obj){
	obj.parentElement.remove();
}

function checkFile(obj){
	var name = obj.name;
	var fileName = $("#"+name).val();
	var ext = fileName.split('.').pop().toLowerCase();

	if(ext != "txt"){
		alert("txt 파일만 업로드 가능합니다");
		$("#"+name).val("");
		return;
	}
}

function sumText(){
	var ajax_set = 
    { 
        form_name:"#frmS",
        url:"./sumTextFile.do",
        return_fn:function(jdata){
            
        }    
    }  
	won_formAjax(ajax_set);
}

/////////////////////////////////////////////////////////////////////////////////////////////

function checkFileExcel(obj){
	var name = obj.name;
	var fileName = $("#"+name).val();
	var ext = fileName.split('.').pop().toLowerCase();
	
	if(ext != "xlsx"){
		alert("xlsx 파일만 업로드 가능합니다");
		$("#"+name).val("");
		return;
	}
}



</script>
</body>
</html>
