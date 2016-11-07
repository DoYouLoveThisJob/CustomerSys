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
	var op=getUrlParam("op");
	if(taskId==undefined || taskId =="" || taskId.trim()==""){
        setNewPage();
	}else{
		setUpdatePage(taskId);
	}

	if(op!=undefined && op !=null && op=="show"){
		setShowPage();
	}

	$("#saveBtn").click(function(){
		saveWithCheckDouble(taskId);
	});
	$("#showDoc").click(function(){
		alertDoc();
	});

});

function alertDoc(){
	//layer.alert('新增调度说明：<br/>'+
	//	'1、通过applicationContext-quartz.xml配置文件读取，已经配置好的trigger或者jobDeatil。通过配置文件读取的bean不允许修改除了触发器作业规则的其余设置。<br/>'+
	//	'2、通过配置文件读取的trigger或者jobDeatil，其余设置可以不填写，系统将自动从配置文件中读取相关配置，如果手动填写，需要与配置文件的配置一致，否则将会报错。<br/>'+
	//	'3、触发器的名字不允许重复。<br/>'+
	//	'4、自定义jobDeatil：<br/>'+
	//	'&nbsp;&nbsp;4.1、自定义作业名称具有唯一性，不允许重复。<br/>'+
	//	'&nbsp;&nbsp;4.2、作业执行类（beanClass）和作业执行类ID（beanId）必须至少填写一个，注意，当调用作业类中存在spring依赖注入关系的话，必须填写调用类ID。<br/>'+
	//	'&nbsp;&nbsp;4.3、方法名必须填写，并且该方法必须属于作业执行类。<br/>'+
	//	'&nbsp;&nbsp;4.4、作业状态：默认为运行，当选择运行时，会把该调度保存到数据库中，并将该作业调度放入调度器中调度。<br/>'+
	//	'&nbsp;&nbsp;4.5、是否需要并发：表示该调度是否需要并发执行，或者顺序执行。<br/>'+
	//	'&nbsp;&nbsp;4.6、作业规则：必须选择一项并填写完整相关数据，如果选择按时间间隔，将生成简单触发器（SimpleTrigger），按固定时间间隔执行。而选择其余两项的话，会生成表达式触发器（CronTrigger），按cron表达式执行。<br/>'+
	//	'5、作业内容：对该作业做必要的说明。允许为空。');
	layer.open({
		type: 1,
		area: ['600px', '600px'],
		title: '新增调度说明',
		shade: 0.6, //遮罩透明度
		moveType: 1, //拖拽风格，0是默认，1是传统拖动
		shift: 1, //0-6的动画形式，-1不开启
		content: '<div>1、通过applicationContext-quartz.xml配置文件读取，已经配置好的trigger或者jobDeatil。通过配置文件读取的bean不允许修改除了触发器作业规则的其余设置。<br/>'+
	'2、通过配置文件读取的trigger或者jobDeatil，其余设置可以不填写，系统将自动从配置文件中读取相关配置，如果手动填写，需要与配置文件的配置一致，否则将会报错。<br/>'+
	'3、触发器的名字不允许重复。<br/>'+
	'4、自定义jobDeatil：<br/>'+
	'&nbsp;&nbsp;4.1、自定义作业名称具有唯一性，不允许重复。<br/>'+
	'&nbsp;&nbsp;4.2、作业执行类（beanClass）和作业执行类ID（beanId）必须至少填写一个，注意，当调用作业类中存在spring依赖注入关系的话，必须填写调用类ID。<br/>'+
	'&nbsp;&nbsp;4.3、方法名必须填写，并且该方法必须属于作业执行类。<br/>'+
	'&nbsp;&nbsp;4.4、作业状态：默认为运行，当选择运行时，会把该调度保存到数据库中，并将该作业调度放入调度器中调度。<br/>'+
	'&nbsp;&nbsp;4.5、是否需要并发：表示该调度是否需要并发执行，或者顺序执行。<br/>'+
	'&nbsp;&nbsp;4.6、作业规则：必须选择一项并填写完整相关数据，如果选择按时间间隔，将生成简单触发器（SimpleTrigger），按固定时间间隔执行。而选择其余两项的话，会生成表达式触发器（CronTrigger），按cron表达式执行。<br/>'+
	'5、作业内容：对该作业做必要的说明。允许为空。</div>'
	});
}
function setNewPage(){
	$("input:text").val("");
	$("input:radio").removeAttr("checked");
	$("input:optional").removeAttr("selected");
	$("input").removeAttr("disabled");
	$("input").removeAttr("readOnly");
	$("#saveBtn").show();
}
function setShowPage(){
	$("input").attr("disabled","disabled");
	$("input").attr("readOnly","readOnly");
	$("select").attr("disabled","disabled");
	$("textarea").attr("disabled","disabled");
	$("#saveBtn").hide();
}
function setUpdatePage(taskId){
	$("#saveBtn").show();
	$.ajax({
		url: "task/getTaskById",
		type:"post",
		data: {
			taskId:taskId
		},
		dataType:"json",
		success: function(data){
			if(data!=undefined && data!=null){
				$("#taskId").val(data.taskId);
				$("#jobId").val(data.jobId);
				$("#jobName").val(data.jobName);
				$("#jobName").attr("readOnly","readOnly");
				$("#triggerName").attr("readOnly","readOnly");
				$("#jobGroup").val(data.jobGroup);
				$("#springId").val(data.springId);
				$("#methodName").val(data.methodName);
				$("#triggerName").val(data.triggerName);
				$("#triggerGroup").val(data.triggerGroup);
				$("#jobStatus").find("option[value="+data.jobStatus+"]").attr("selected","selected");
				$("#beanClass").val(data.beanClass);
				$("#isConcurrent").find("option[value="+data.isConcurrent+"]").attr("selected","selected");
				$("#isInterrupt").find("option[value="+data.isInterrupt+"]").attr("selected","selected");
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
	$('input[value=cronExpression]').attr("checked","checked");
	$("#cronExpression").val(cronExpression);
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
	//var taskId=$("#taskId").val();
	var jobName=$("#jobId").val();
	var jobName=$("#jobName").val();
	//var jobGroup=$("#jobGroup").val();
	var jobGroup="DEFAULT";
	var springId=$("#springId").val();
	var methodName=$("#methodName").val();
	//var triggerName="";
	var triggerGroup="DEFAULT";
	var triggerName=$("#triggerName").val();
	if(triggerName==undefined||triggerName==null|| triggerName=="" || triggerName.trim()==""){
		triggerName=jobName;
		triggerGroup=jobGroup;
	}
	//var triggerGroup=$("#triggerGroup").val();
	//var triggerGroup="DEFAULT";
	var jobStatus=$("#jobStatus").val();
	var beanClass=$("#beanClass").val();
	var isConcurrent=$("#isConcurrent").val();
	//var isInterrupt=$("#isInterrupt").val();

	var isInterrupt="0";
	var description=$("#description").val();
	$.ajax({
		url: "task/addTask",
		type:"post",
		data: {
			taskId:taskId,
			jobId:jobId,
			jobName:jobName,
			jobGroup:jobGroup,
			springId:springId,
			methodName:methodName,
			triggerName:triggerName,
			triggerGroup:triggerGroup,
			jobStatus:jobStatus,
			beanClass:beanClass,
			isConcurrent:isConcurrent,
			isInterrupt:isInterrupt,
			description:description,
			cronExpression:cronExpression,
			triggerType:triggerType,
			repeatInterval:repeatInterval,
			unit:unit
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
function saveWithCheckDouble(taskId){
	var jobName=$("#jobName").val();
	var triggerName=$("#triggerName").val();
	if((jobName==undefined||jobName==null||jobName ==""||jobName.trim()=="")&&(triggerName==undefined||triggerName==null||triggerName ==""||triggerName.trim()=="")){
		layer.msg("任务名称和触发器不允许同时为空");
		return;
	}
	if(jobName==undefined||jobName==null||jobName ==""||jobName.trim()==""){
		jobName=triggerName;
	}
	if(triggerName==undefined||triggerName==null||triggerName ==""||triggerName.trim()==""){
		triggerName=jobName;
	}
	$.ajax({
		url: "task/checkTrigger",
		type:"post",
		data: {
			taskId:taskId,
			jobName:jobName,
			triggerName:triggerName
		},
		dataType:"json",
		success: function(data){
			if(data.isSucc){
				saveOrUpdateTask(taskId)
			}else{
                layer.msg(data.msg);
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