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
    <script type="text/javascript" src=" <c:url value="/resources/js/common/jquery.form.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jpro_default.0.1.js"/>"></script>
</head>

<body>
    <div id="wrap">
        <div class="loginwrap">
            <div class="loginbg">
               <div class="login">
                   <div>
                    <div class="bgone"></div>
                    <div class="bgtwo">
                        <form id="loginForm" method="post" onsubmit="return false;">
                            <h2>Login</h2>
                            <div class="inrbox">
                                <div class="inr">
                                    <div>
                                        <span><img src="<c:url value='home/images/disaster/common/ico_login.png'/>" alt="로그인"></span>
                                        <input type="text" id="inputID" name="inputID" maxlength="20" placeholder="아이디">
                                    </div>
                                    <div>
                                        <span><img src="<c:url value='home/images/disaster/common/ico_pw.png'/>" alt="비밀번호"></span>
                                        <input type="password" id="inputPW" name="inputPW" maxlength="100" placeholder="비밀번호">
                                    </div>
                                </div>
                            </div>
                            <div class="submit">
                                <input type="submit" id="loginSubmit" name="loginSubmit" value="로그인">
                            </div>
                        </form>
                    </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- // wrap -->
<script>
	var loginBtn = document.getElementById('loginSubmit');
	
	loginBtn.onclick = function(){
		goLogin();
	}

	function goLogin(){

		var ajax_set = {
				url:"<c:url value='/login/actionLogin.do'/>",
				param: $('#loginForm').serialize(),
				return_fn:function(jdata){
					loginCheck(jdata);
				}
		};
		
		won_getAjax(ajax_set);
	}

	function loginCheck(jdata){
		if(jdata.errCd == "0"){ // 로그인 성공 시
			location.href = "<c:url value='/jpro/main.do'/>";
		}
		else{
			alert("" +  jdata.errMsg);
		}
	}

</script>
</body>

</html>