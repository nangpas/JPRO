<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import = "com.nyc.main.service.UserVO" %> 
<%
    UserVO temp = (UserVO)session.getAttribute("UserVO");
	String userId = temp.getUSR_ID();
%>

<!DOCTYPE HTML>
<html lang="ko">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>권한관리</title>
    
    <script type="text/javascript" src="<c:url value="/resources/js/common/jquery-3.4.1.min.js"/>"></script>
    <script type="text/javascript" src=" <c:url value="/resources/js/common/jquery.form.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jpro_default.0.1.js"/>"></script>
</head>

<body>
    <div id="wrap">
        <div>
			<table>
				<thead>
			        <tr>
			            <th>권한명</th>
			            <th>할인율(%)</th>
			            <th>권한설명</th>
			            <th>삭제</th>
			        </tr>
			    </thead>
			    <tbody id="role_table">
			    </tbody>
		    </table>
		</div>
		<div>
			<button id="btn_insert" onclick="insertRole()">추가</button>
			<button id="btn_select" onclick="selectRoleList()">검색</button>
		</div>
		<div>
			<input type="text" id="role_nm" maxlength="100"></input>
			<input type="text" id="role_dc" maxlength="300"></input>
			<input type="text" id="discount" maxlength="2" onblur="won_isNum(this)"></input>
		</div>
    </div>
    <!-- // wrap -->
<script>
	function selectRoleList(){
		var ajax_set = {
				url:"<c:url value='/admin/selectRoleList.do'/>",
				param: '',
				return_fn:function(jdata){
					setRoleList(jdata);
				}
		};
		
		won_getAjax(ajax_set);
	}
	
	function setRoleList(jdata){
		$("#role_table").empty();		
		str = "";
		
		if(jdata.length != 0){
			for(var i = 0; i < jdata.length; i++){
				str += "<tr id=\"" + jdata[i].roleId + "\">";
				str += "<td>" +jdata[i].roleNm + "</td>";
				str += "<td>" +jdata[i].discount + "</td>";
				str += "<td>" +jdata[i].roleDc + "</td>";
				str += "<td><button onclick=\"deleteRole(" + jdata[i].roleId + ")\">삭제</button></td>"
				str += "</tr>";
			}
		}else{
			str += "<tr><td colspan=\"4\">조회된 값이 없습니다.</td></tr>"
		}

		$("#role_table").append(str);
	}


	function insertRole(){
		if($("#role_nm").val() == "" || $("#role_nm").val() == null){
			alert('권한명을 입력해주세요.');
			$("#role_nm").focus();
			return false;
		}

		if($("#discount").val() == "" || $("#discount").val() == null){
			alert('할인율을 입력해주세요.');
			$("#discount").focus();
			return false;
		}

		var ajax_set = {
				url:"<c:url value='/admin/insertRole.do'/>",
				param: "ROLE_NM=" + $("#role_nm").val() + "&ROLE_DC=" + $("#role_dc").val() + "&DISCOUNT=" + $("#discount").val() + "&USR_ID=" + "<%=userId%>",
				return_fn:function(jdata){
					if(jdata == '0'){
						alert('실패');
					}else if(jdata == '1'){
						alert('성공');
					}else{
						alert('실패');
					}
				}
		};
		
		won_getAjax(ajax_set);
	}

	function deleteRole(id){
		var ajax_set = {
				url:"<c:url value='/admin/deleteRole.do'/>",
				param: "ROLE_ID=" + id + "&USR_ID=" + "<%=userId%>",
				return_fn:function(jdata){
					if(jdata == '0'){
						alert('실패');
					}else if(jdata == '1'){
						alert('성공');
					}else{
						alert('실패');
					}
				}
		};
		
		won_getAjax(ajax_set);
	}
</script>
</body>

</html>