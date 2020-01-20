<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE HTML>
<html lang="ko">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>로그인</title>
    
    <script type="text/javascript" src="<c:url value="/resources/js/common/jquery-3.4.1.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/common/jquery.form.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jpro_default.0.1.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/smartEditor/js/HuskyEZCreator.js"/>" charset="utf-8"></script>
</head>

<body>
    <div id="wrap">
    	<textarea name="smartEditor" id="smartEditor" rows="10" cols="100" style="width:766px; height:412px; display:none;"></textarea>
    </div>
<script>
	var oEditors = [];

	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "smartEditor",
		sSkinURI: '<c:url value="/resources/smartEditor/SmartEditor2Skin.html"/>',	
		htParams : {
			bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseVerticalResizer : true,		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
			bUseModeChanger : true,			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
			//aAdditionalFontList : aAdditionalFontSet,		// 추가 글꼴 목록
			fOnBeforeUnload : function(){
				//alert("완료!");
			}
		}, //boolean
		fOnAppLoad : function(){
			//예제 코드
			//oEditors.getById["ir1"].exec("PASTE_HTML", ["로딩이 완료된 후에 본문에 삽입되는 text입니다."]);
		},
		fCreator: "createSEditor2"
	});
</script>
<script>
	
</script>
</body>

</html>