/*
 * 이재원
 * 스크립트 공통함수*/

//Ajax
function won_getAjax(ajax_set) {
	if(ajax_set.loading == "true"){
		$.ajax({
	        type: "POST",
	        url: ajax_set.url,
	        data: ajax_set.param,
	        processData: false,
	        dataType: 'json',
	        error: function(){
	            alert('조회중 오류가 발생하였습니다.');
	        },
	        success: ajax_set.return_fn,
	        beforeSend:function(){
	            $('.wrap-loading').removeClass('display-none');
	        },
	        complete:function(){
	            $('.wrap-loading').addClass('display-none');
	        }
	    });
	}else{
		$.ajax({
	        type: "POST",
	        url: ajax_set.url,
	        data: ajax_set.param,
	        processData: false,
	        dataType: 'json',
	        error: function(){
	            alert('조회중 오류가 발생하였습니다.');
	        },
	        success: ajax_set.return_fn
	    });
	}
}

function won_formAjax(ajax_set){
	if(ajax_set.loading == "true"){
		$(ajax_set.form_name).ajaxSubmit({ 
	        type:"POST",
	        dataType:'json',
	        url:ajax_set.url, 
	        contentType : "application/x-www-form-urlencoded;charset=UTF-8",
	        async:false,
	        beforeSubmit : wini_beforeSubmit,
	        complete: function() {
	            winigrid_submit_ingView(false);
	        },
	        error: function(){            
	            alert('처리중 오류가 발생하였습니다.');
	            winigrid_submit_ingView(false);
	        },
	        success:ajax_set.return_fn
	    });
	}else{
		$(ajax_set.form_name).ajaxSubmit({ 
	        type:"POST",
	        dataType:'json',
	        url:ajax_set.url, 
	        contentType : "application/x-www-form-urlencoded;charset=UTF-8",
	        async:false,
	        error: function(){            
	            alert('처리중 오류가 발생하였습니다.');
	            winigrid_submit_ingView(false);
	        },
	        success:ajax_set.return_fn
	    });
	}
}


//onblur(시간 체크)
function won_isTime(input){
	var pattern = /^(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])$/; 
	
	if(input.value != ''){
		if(!pattern.test(input.value.trim())) {
			alert("잘못된 시간 형식 입니다."); 
			input.value = ""; 
			input.focus();
	    	return false;
		}
	}
	
	return true;
}

//onblur(날짜 체크)
function won_isCal(input){
	var pattern = /^(19|20)\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$/; 
	
	if(input.value != ''){
		if(!pattern.test(input.value.trim())) {
			alert("잘못된 날짜 형식 입니다.")
			input.value = "";
			input.focus();
	    	return false;
		}
	}
	
	return true;
}

//onblur(숫자 체크)
function won_isNum(input){
	var pattern = /^[0-9]+$/;
	
	if(input.value != ''){
		if(!pattern.test(input.value.trim())) {
			alert("잘못된 숫자 형식 입니다.")
			input.value = "";
			input.focus();
	    	return false;
		}
	}
	return true;
}

//onblur(시간 체크)
function won_isHour(input){
	var pattern = /^([0-9]|[01][0-9]|2[0-3])$/;
	
	if(input.value != ''){
		if(!pattern.test(input.value.trim())) {
			alert("00~23시간 형태의 시간을 입력해주세요.")
			input.value = "00";
			input.focus();
	    	return false;
		}else{
			input.value = won_Pad(input.value,2);
		}
	}
	
	return true;
}


//자리수를 맞추고 나머지는 0으로 채우는 함수
function won_Pad(n, width) {
	n = n + '';
	return n.length >= width ? n : new Array(width - n.length + 1).join('0') + n;
}
