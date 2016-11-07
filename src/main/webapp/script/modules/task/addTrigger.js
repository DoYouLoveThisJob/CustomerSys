/* -------------------- Check Browser --------------------- */
function browser() {
	
	var isOpera = !!(window.opera && window.opera.version);  // Opera 8.0+
	var isFirefox = testCSS('MozBoxSizing');                 // FF 0.8+
	var isSafari = Object.prototype.toString.call(window.HTMLElement).indexOf('Constructor') > 0;
	    // At least Safari 3+: "[object HTMLElementConstructor]"
	var isChrome = !isSafari && testCSS('WebkitTransform');  // Chrome 1+
	//var isIE = /*@cc_on!@*/false || testCSS('msTransform');  // At least IE6

	function testCSS(prop) {
	    return prop in document.documentElement.style;
	}
	
	if (isOpera) {
		
		return false;
		
	}else if (isSafari || isChrome) {
		
		return true;
		
	} else {
		
		return false;
		
	}
	
}

/* ---------- IE8 list style hack (:nth-child(odd)) ---------- */

jQuery(document).ready(function($){
	
	if($('.messagesList').width()) {
		
		if(jQuery.browser.version.substring(0, 2) == "8.") {

			$('ul.messagesList li:nth-child(2n+1)').addClass('odd');
			
		}
		
	}

});	

$(document).ready(function(){
	var taskId=getUrlParam("taskId");
	var jobId=getUrlParam("jobId");
	var op=getUrlParam("op");
	if(taskId!=undefined && taskId !="" && taskId.trim()!=""){
		getTaskDeatil(taskId)
	}else if(jobId!=undefined && jobId !="" && jobId.trim()!=""){
		getJobDeatil(jobId)
	}
	if(op!=undefined && op !=null && op=="show"){
		setShowPage();
	}

	$("#saveBtn").click(function(){
		saveOrUpdateTask(taskId);
	});


});

function setShowPage(){
	$("input").attr("disabled","disabled");
	$("input").attr("readOnly","readOnly");
	$("select").attr("disabled","disabled");
	$("textarea").attr("disabled","disabled");
	$("#saveBtn").hide();
}

function getTaskDeatil(taskId){
	$.ajax({
		url: "task/getTaskById",
		type:"post",
		data: {
			taskId:taskId
		},
		dataType:"json",
		success: function(data){
			if(data!=undefined && data!=null){
				getJobDeatil(data.jobId);
				$("#jobStatus").find("option[value="+data.jobStatus+"]").attr("selected","selected");
				$("#description").val(data.description);
				if(data.triggerType=="1"){
					$('input[value=interval]').attr("checked","checked");
					var unit=data.unit;
					if(unit=="minute"){
						$("#intervalValue").val(parseFloat(data.repeatInterval)/1000/60);
						$("#intervalUnit").find("option[value=minute]").attr("selected","selected");
					}else if(unit=="hour"){
						$("#intervalValue").val(parseFloat(data.repeatInterval)/1000/60/60);
						$("#intervalUnit").find("option[value=hour]").attr("selected","selected");
					}else{
						$("#intervalValue").val(parseFloat(data.repeatInterval)/1000);
						$("#intervalUnit").find("option[value=second]").attr("selected","selected");
					}
				}else if(data.triggerType=="2"){
					setCronExpression(data.cronExpression);
				}

			}
		}
	});
}
function setCronExpression(cronExpression){
	var timeResult = /(\d+) (\d+) (\d+) \* \* \?/ig.exec(cronExpression);
	if(timeResult!=null && timeResult.length>0){
		$('input[value=time]').attr("checked","checked");
		$("#second").val(timeResult[1]);
		$("#minute").val(timeResult[2]);
		$("#hour").val(timeResult[3]);
		return;
	}
	//$('input[value=cronExpression]').attr("checked","checked");
	//$("#cronExpression").val(cronExpression);
}
function getJobDeatil(jobId){
	$.ajax({
		url: "task/getJobDeatilById",
		type:"post",
		data: {
			jobId:jobId
		},
		dataType:"json",
		success: function(data){
			if(data!=undefined && data!=null){
				$("#jobName").html(data.jobName);
				$("#jobId").val(data.jobId);
				$("#jobCname").html(data.jobCname);
				$("#jobDescription").html(data.description);
			}
		}
	});
}
function saveOrUpdateTask(taskId){
	var cronExpression="";
	var triggerType="";
	var repeatInterval=-1;
	var  unit="second";
	/*保存时间间隔*/
	var mode=$('input:radio:checked').val();
	if(mode=="interval"){
		var intervalUnit=$("#intervalUnit").val();
		var intervalValue=$("#intervalValue").val();
		triggerType="1"
		if(intervalUnit=="second"){
			if(!(isPositiveInteger(intervalValue) && 0<parseInt(intervalValue) )){
				layer.msg("输入的秒数有误，必须大于零的整数");
				return;
			}
			cronExpression="*/"+intervalValue+" * * * * ?";
			repeatInterval=parseInt(intervalValue)*1000;
			unit="second";
		}else if(intervalUnit=="minute"){
			if(!(isPositiveInteger(intervalValue) && 0<parseInt(intervalValue) )){
				layer.msg("输入的分钟有误，必须大于零的整数");
				return;
			}
			cronExpression="0 */"+intervalValue+" * * * ?";
			repeatInterval=parseInt(intervalValue)*60*1000;
			unit="minute";
		}else if(intervalUnit=="hour"){
			if(!(isPositiveInteger(intervalValue) && 0<parseInt(intervalValue))){
				layer.msg("输入的小时有误，必须大于零的整数");
				return;
			}
			cronExpression="0 0 */"+intervalValue+" * * ?";
			repeatInterval=parseInt(intervalValue)*60*60*1000;
			unit="hour";
		}else{
			layer.msg("时间单位必须选择");
			return;
		}
	}else if((mode=="time")){
		triggerType="2";
		var hour=$("#hour").val();
		var minute=$("#minute").val();
		var second=$("#second").val();
		if(!(isPositiveInteger(hour) && 0<=parseInt(hour) && parseInt(hour)<=23)){
			layer.msg("输入的小时数有误，必须为不小于0并且不大于23的整数");
			return;
		}
		if(!( isPositiveInteger(minute) && 0<=parseInt(minute)&&parseInt(minute)<=59 )){
			layer.msg("输入的分钟数有误，必须为不小于0并且不大于59的整数");
			return;
		}
		if(!( isPositiveInteger(second) && 0<=parseInt(second) && parseInt(second)<=59 )){
			layer.msg("输入的秒数有误，必须为不小于0并且不大于59的整数");
			return;
		}
		cronExpression=second+" "+minute+" "+hour+" * * ?";
	}else if((mode=="cronExpression")){
		triggerType="2";
		cronExpression=$("#cronExpression").val();
		if(cronExpression==undefined||cronExpression==null|| cronExpression=="" || cronExpression.trim()==""){
			layer.msg("执行规则不能为空");
			return;
		}
	}
	else {
		layer.msg("必须选择一个任务调度规则");
		return;
	}

	var jobId=$("#jobId").val();
	var description=$("#description").val();
	var jobStatus=$("#jobStatus").val();
	if(jobId==undefined || jobId==null || jobId=="" || jobId.trim()==""){
		layer.msg("作业细节未知,无法继续操作");
		return;
	}
	$.ajax({
		url: "task/addOrUpdateTask",
		type:"post",
		data: {
			taskId:taskId,
			jobId:jobId,
			description:description,
			cronExpression:cronExpression,
			triggerType:triggerType,
			repeatInterval:repeatInterval,
			unit:unit,
			jobStatus:jobStatus
		},
		dataType:"json",
		beforeSend:function() {
			forbiddenPage();
		},
		complete:function(data) {
			releasePage();
		},
		success: function(data){
			layer.msg(data.msg);
			if(data.isSucc){
				window.location.href="/task/taskManagePage.action";
			}
		}
	});
}

function isPositiveInteger(value){
	var reg=/^[0-9]+[0-9]*]*$/;
	return reg.test(value);
}
//$(window).bind("resize", widthFunctions);
function widthFunctions(e) {
    var winHeight = $(window).height();
    var winWidth = $(window).width();
	var contentHeight = $("#content").height();
	if (winHeight) {
		$("#content").css("min-height",winHeight);
	}
	if (contentHeight) {
		$("#sidebar-left2").css("height",contentHeight);
	}
	if (winWidth < 980 && winWidth > 750) {
		if($("#sidebar-left").hasClass("span2")) {
			$("#sidebar-left").removeClass("span2");
			$("#sidebar-left").addClass("span1");

		}

		if($("#content").hasClass("span10")) {

			$("#content").removeClass("span10");
			$("#content").addClass("span11");

		}


		$("a").each(function(){

			if($(this).hasClass("quick-button-small span1")) {

				$(this).removeClass("quick-button-small span1");
				$(this).addClass("quick-button span2 changed");

			}

		});

		$(".circleStatsItem, .circleStatsItemBox").each(function() {

			var getOnTablet = $(this).parent().attr('onTablet');
			var getOnDesktop = $(this).parent().attr('onDesktop');

			if (getOnTablet) {

				$(this).parent().removeClass(getOnDesktop);
				$(this).parent().addClass(getOnTablet);

			}

		});

		$(".box").each(function(){

			var getOnTablet = $(this).attr('onTablet');
			var getOnDesktop = $(this).attr('onDesktop');

			if (getOnTablet) {

				$(this).removeClass(getOnDesktop);
				$(this).addClass(getOnTablet);

			}

		});

		$(".widget").each(function(){

			var getOnTablet = $(this).attr('onTablet');
			var getOnDesktop = $(this).attr('onDesktop');

			if (getOnTablet) {

				$(this).removeClass(getOnDesktop);
				$(this).addClass(getOnTablet);

			}

		});

		$(".statbox").each(function(){

			var getOnTablet = $(this).attr('onTablet');
			var getOnDesktop = $(this).attr('onDesktop');

			if (getOnTablet) {

				$(this).removeClass(getOnDesktop);
				$(this).addClass(getOnTablet);

			}

		});

	} else {

		if($("#sidebar-left").hasClass("span1")) {

			$("#sidebar-left").removeClass("span1");
			$("#sidebar-left").addClass("span2");

		}

		if($("#content").hasClass("span11")) {

			$("#content").removeClass("span11");
			$("#content").addClass("span11");

		}

		$("a").each(function(){

			if($(this).hasClass("quick-button span2 changed")) {

				$(this).removeClass("quick-button span2 changed");
				$(this).addClass("quick-button-small span1");

			}

		});

		$(".circleStatsItem, .circleStatsItemBox").each(function() {

			var getOnTablet = $(this).parent().attr('onTablet');
			var getOnDesktop = $(this).parent().attr('onDesktop');

			if (getOnTablet) {

				$(this).parent().removeClass(getOnTablet);
				$(this).parent().addClass(getOnDesktop);

			}

		});

		$(".box").each(function(){

			var getOnTablet = $(this).attr('onTablet');
			var getOnDesktop = $(this).attr('onDesktop');

			if (getOnTablet) {

				$(this).removeClass(getOnTablet);
				$(this).addClass(getOnDesktop);

			}

		});

		$(".widget").each(function(){

			var getOnTablet = $(this).attr('onTablet');
			var getOnDesktop = $(this).attr('onDesktop');

			if (getOnTablet) {

				$(this).removeClass(getOnTablet);
				$(this).addClass(getOnDesktop);

			}

		});

		$(".statbox").each(function(){

			var getOnTablet = $(this).attr('onTablet');
			var getOnDesktop = $(this).attr('onDesktop');

			if (getOnTablet) {

				$(this).removeClass(getOnTablet);
				$(this).addClass(getOnDesktop);

			}

		});

	}

	if($('.timeline')) {

		$('.timeslot').each(function(){

			var timeslotHeight = $(this).find('.task').outerHeight();

			$(this).css('height',timeslotHeight);

		});

	}

}