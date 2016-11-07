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
	var jobId=getUrlParam("jobId");
	var op=getUrlParam("op");
	if(jobId==undefined || jobId =="" || jobId.trim()==""){
        setNewPage();
	}else{
		setUpdatePage(jobId);
	}

	if(op!=undefined && op !=null && op=="show"){
		setShowPage();
	}

	$("#saveBtn").click(function(){
		saveWithCheckDouble(jobId);
	});
	$("#showDoc").click(function(){
		alertDoc();
	});

});

function alertDoc(){
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
function setUpdatePage(jobId){
	$("#saveBtn").show();
	$.ajax({
		url: "task/getJobDeatilById",
		type:"post",
		data: {
			jobId:jobId
		},
		dataType:"json",
		success: function(data){
			if(data!=undefined && data!=null){
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
			}
		}
	});
}
function saveOrUpdateTask(jobId){
	var jobName=$("#jobName").val();
	var jobCname=$("#jobCname").val();
	//var jobGroup=$("#jobGroup").val();
	var jobGroup="DEFAULT";
	var springId=$("#springId").val();
	var methodName=$("#methodName").val();
	var jobStatus=$("#jobStatus").val();
	var beanClass=$("#beanClass").val();
	var isConcurrent=$("#isConcurrent").val();
	var isInterrupt="0";
	var description=$("#description").val();

	if(jobName==undefined || jobName==null || jobName=="" || jobName.trim()==""){
		layer.msg("工作名不允许为空");
		return;
	}

	if(jobCname==undefined || jobCname==null || jobCname=="" || jobCname.trim()==""){
		jobCname=jobName;
	}

	$.ajax({
		url: "task/addOrUpdateJobToDB",
		type:"post",
		data: {
			jobCname:jobCname,
			jobId:jobId,
			jobName:jobName,
			jobGroup:jobGroup,
			springId:springId,
			methodName:methodName,
			jobStatus:"1",
			beanClass:beanClass,
			isConcurrent:isConcurrent,
			isInterrupt:isInterrupt,
			description:description
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
				//alert("保存成功");
				window.location.href="task/jobDeatilManagePage.action";
			}
		}
	});
}


function saveWithCheckDouble(jobId){
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
			jobId:jobId,
			jobName:jobName,
			triggerName:triggerName
		},
		dataType:"json",
		success: function(data){
			if(data.isSucc){
				saveOrUpdateTask(jobId)
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