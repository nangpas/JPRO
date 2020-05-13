<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE HTML>
<html lang="ko">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>coin</title>
	
	<script type="text/javascript" src="<c:url value="/resources/js/common/jquery-3.4.1.min.js"/>"></script>
    <script type="text/javascript" src=" <c:url value="/resources/js/common/jquery.form.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jpro_default.0.1.js"/>"></script>
    <script src="//developers.kakao.com/sdk/js/kakao.min.js"></script>
</head>
<style>
.wrap-loading{ /*화면 전체를 어둡게 합니다.*/
    position: fixed;
    left:0;
    right:0;
    top:0;
    bottom:0;
    background: rgba(0,0,0,0.2); /*not in ie */
    filter: progid:DXImageTransform.Microsoft.Gradient(startColorstr='#20000000', endColorstr='#20000000');    /* ie */
}

.wrap-loading div{ /*로딩 이미지*/
    position: fixed;
    top:50%;
    left:50%;
    margin-left: -21px;
    margin-top: -21px;
}

.display-none{ /*감추기*/
    display:none;
}

table {
  width: 100%;
  border: 1px solid #444444;
}
th, td {
  border: 1px solid #444444;
}

</style>
<body>
<div>
	<a id="kakao-login-btn"></a>
	<button id="btn_srch" onclick="setAuth()">인증2</button>
	<button id="btn_start" onclick="start()">크롤링 시작</button>
	<button id="btn_clear" onclick="clear1()">크롤링 중지</button>
	<button id="btn_clear" onclick="start2()">20분마다 비트코인</button>
	<a href="http://developers.kakao.com/logout">로그아웃입니다!</a>
	
	<div>
		<p>
			1. 로그인 한다.</br>
			2. 인증버튼을 눌려서 인증한다.</br>
			3. 검색버튼을 눌려서 시작한다.</br>
		</p>
	</div>
	
	<div>
		초기 반복주기 : 60000(1분)</br>
		초기 퍼센트 : 3%</br>
		반복주기 : <input type="text" id="intervalTimeId" onblur="won_isNum(this)"/>
		퍼센트 : <input type="text" id="percentId"  onblur="won_isNum(this)"/>
		<button id="btn_clear" onclick="setParm()">퍼센트 반복주기</button>	
	</div>
	
	<div style="width: 50%;">
	    <table>
		    <colgroup>
				<col width="30%">
				<col width="30%">
				<col width="30%">
	   		</colgroup>
	        <thead>
	            <tr>
	                <th>코인명</th>
	                <th>현재 가격</th>
	                <th>1분대비</th>
	            </tr>
	        </thead>
	        <tbody id="coinList">
	        	
			</tbody>
		</table>
	</div>
	
	<div class="wrap-loading display-none">
    	<div><img src="<c:out value='${pageContext.request.contextPath}'/>/resources/image/Progress_Loading.gif" /></div>
	</div>  

</div>
<script>

//////////////////////////////////////////////////////////////////////
	Kakao.init('d63045e5730305c9359f2458d40ff9b6');

	//카카오 로그인 버튼을 생성합니다.
	Kakao.Auth.createLoginButton({
		container: '#kakao-login-btn',
		success: function(authObj) {
			alert(JSON.stringify(authObj));
		},
		fail: function(err) {
			alert(JSON.stringify(err));
		}
	});
//////////////////////////////////////////////////////////////////////

var sendMsg = "";
var preCoinList;
var timer;
//반복주기
var intervalTime = 60000;
//반복주기 알림 대비 퍼센트
var percent = 3;

//비트코인
var bitPrice;
var bitTimer;


$(document).ready(function(){
	$('#Progress_Loading').hide(); //첫 시작시 로딩바를 숨겨준다.
});

function setParm(){
	if($("#intervalTimeId").val() == "" || $("#intervalTimeId").val() == null){
		alert('시간 입력하세요');
		return false;
	}

	if($("#percentId").val() == "" || $("#percentId").val() == null){
		alert('시간 입력하세요');
		return false;
	}
	
	intervalTime = $("#intervalTimeId").val();
	percent = $("#percentId").val();
}

function start(){
	if(timer == null || timer ==""){
		timer = setInterval(function(){
		    srchKeyward3();
		}, intervalTime);
	}else{
		alert('중지하고 다시 시작해 주세요!');
		return false;
	}
	
}

function start2(){
	bitCoin();
	bitTimer = setInterval(function(){
	    bitCoin();
	}, 10000);
}

function clear1(){
	if(timer == null || timer ==""){
		console.log('정지정지');
		alert('클롤링 시작하세요 ^^');
		return false;
	}
	console.log('정지정지');
	clearInterval(timer);
	clearInterval(timer);
	clearInterval(timer);
	clearInterval(timer);
	
}

function srchKeyward3(){
	var ajax_set = {
			url:"<c:url value='/jpro/testCrawling3.do'/>",
			param: "KEYWARD=" + $("#keyward").val(),
			return_fn:function(jdata){
				setCoinList(jdata);
			},
			loading: "true"
	};
	
	won_getAjax(ajax_set);
}

function setCoinList(jdata){
	var str = "";
	$("#coinList").empty();

	for(var i=0; i<jdata.length; i++){
		str += "<tr>";
		str += "    <td>" + jdata[i].coinNameKr + " </td>";
		str += "    <td>" + jdata[i].coinPrice + "</td>";
		str += "    <td>" + getPrcDiff(jdata[i]) + "</td>";
		str += "</tr>";
	}

	if(sendMsg != ""){
		sendMessage(sendMsg);
	}

	preCoinList = jdata;
	sendMsg = "";
	$("#coinList").append(str);
}


function bitCoin(){
	var ajax_set = {
			url:"<c:url value='/jpro/testCrawling3.do'/>",
			param: "",
			return_fn:function(jdata){
				sendBitCoin(jdata);
			},
			loading: "true"
	};
	
	won_getAjax(ajax_set);
}

function sendBitCoin(jdata){
	var temp;
	var tt;
	var result;
	var cnt;
	var sendMsg;
	
	if(bitPrice == "" || bitPrice == null){
		for(var i=0; i<jdata.length; i++){
			if(jdata[i].coinNameKr == "비트코인"){
				bitPrice = Number(jdata[i].coinPrice);
				cnt = i;
			}
		}
		
		sendMsg = "(" + "비트코인" + ":" + jdata[cnt].coinPrice + "/" + result + "%/" + jdata[cnt].coinRate + "/" + ")\n";
		sendMessage(sendMsg);
		
	}else{
		for(var i=0; i<jdata.length; i++){
			if(jdata[i].coinNameKr == "비트코인"){
				temp = Number(jdata[i].coinPrice) - bitPrice;
				cnt = i;
			}	
		}

		if(temp <= 0){
			tt = "-";
		}

		reuslt = Math.abs(temp)/bitPrice * 100;
		result = Number(result);
		result = result.toFixed(3);

		if(tt != "-"){
			sendMsg = "(" + "비트코인" + ":" + jdata[cnt].coinPrice + "/" + result + "%/" + jdata[cnt].coinRate + "/" + ")\n";
			sendMessage(sendMsg);
		}

		bitPrice = Number(jdata[cnt].coinPrice);
	}
	
	
	
}


function getPrcDiff(tempMap){
	if(preCoinList == "" || preCoinList == null){
		return "";
	}
	
	var name = tempMap.coinNameKr;
	var price = Number(tempMap.coinPrice);

	var prcDiff;
	var temp;
	var tt = "";
	
	for(var i=0; i < preCoinList.length; i++){
		if(preCoinList[i].coinNameKr == name){
			temp = Number(preCoinList[i].coinPrice) - price;
		}
	}

	if(temp < 0){
		tt = "-";
	}

	prcDiff = Math.abs(temp)/price * 100;

	prcDiff = prcDiff.toFixed(3);

	if(tt != "-" && prcDiff > percent){
		sendMsg += "(" + name + ":" + tempMap.coinPrice + "/" + prcDiff + "%/" + tempMap.coinRate + "/" + tempMap.coinTranPrice + "/" + tempMap.coinTotal + ")\n";
	}
	prcDiff = tt + prcDiff + "%";
	
	return prcDiff;
}


function sendMessage(msg)
{
	Kakao.API.request({
	  url: '/v2/api/talk/memo/default/send',
	  data: {
	    template_object: {
	      object_type: 'text',
	      text: msg,
	      link: {
	          web_url: 'http://dev.kakao.com',
	          mobile_web_url: 'http://dev.kakao.com',
	        },
	    },
	  },
	  success: function(response) {
	    console.log(response);
	  },
	  fail: function(error) {
	    console.log(error);
	  },
	});

	/*
	Kakao.API.request({
		  url: '/v1/api/talk/friends/message/default/send',
		  data: {
			receiver_uuids: ["FyMVJRQmHywAMQk9Dj0MNAAzHysaIhUiGlE", "FyYTIRQnFSEZNQM7DDoMOQs4FCARKR4pEVk"],
		    template_object: {
		      object_type: 'text',
		      text: msg,
		      link: {
		          web_url: 'http://dev.kakao.com',
		          mobile_web_url: 'http://dev.kakao.com',
		        },
		    },
		  },
		  success: function(response) {
		    console.log(response);
		  },
		  fail: function(error) {
		    console.log(error);
		  },
		});
	*/
}

function setAuth(){
	Kakao.Auth.login({
	    scope: 'talk_message,friends',
	    success: function(response) {
	        console.log(response);
	    },
	    fail: function(error) {
	        console.log(error);
	    }
	});
}
</script>
</body>
</html>
