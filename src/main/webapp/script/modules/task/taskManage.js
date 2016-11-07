/* -------------------- Check Browser --------------------- */
var orderList;
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
	doSearch();
	$('#queryBtn').click(function(){
		doSearch();
	});
	$('#addBtn').click(function(){
		addJob();
	});


	//Date picker
	$('#startTime').datepicker({
		autoclose: true,
		language:'zh-CN'
	});
	//Date picker
	$('#endTime').datepicker({
		autoclose: true,
		language:'zh-CN'
	});
});

/* ---------- dataTable ---------- */
function doSearch() {
	// 判断有没有实例化过datatable
	if (orderList != undefined) {
		// 获取参数
		var oSettings = orderList.fnSettings();
		oSettings.aoServerParams.push({
			"fn" : function(d) {
				d.columns = null;
				d.search.regex = null;
				d.searchText = $("#searchText").val();
				d.startTime=$("#startTime").val();
				d.endTime=$("#endTime").val();
			}
		});
		// 重新绘制datatable
		orderList.fnDraw();

	} else {

		orderList = $('#order_table')
			.dataTable(
				{
					"language" : {
						"url" : "script/plugins/dataTable/language/cn_zh.txt"
					},
					// "scrollX" : true,
					"scrollY": "71%",
					"processing" : true,
					"serverSide" : true,
					"sort" : false,
					"ordering" : false,// 排序
					'bDestroy' : true,
					'sPaginationType':'input',
					"bLengthChange" : false,// 禁止页数下拉
					"pageLength": 20,
					"fnDrawCallback" : function(oSettings) {
						var cmdButtons = $("a[name=cmdBtn]");
						$.each(cmdButtons, function(i, n) {
							$(n).unbind('click');
							$(n).click(function() {
								(function(taskId,cmd) {
									changJobStatus(taskId,cmd);
								})($(n).attr('taskId'),$(n).attr('cmd'));
							});
						});
						var runNowButtons = $("a[name=runNowBtn]");
						$.each(runNowButtons, function(i, n) {
							$(n).unbind('click');
							$(n).click(function() {
								(function(taskId) {
									runJobNow(taskId);
								})($(n).attr('taskId'));
							});
						});


						var updateButtons = $("a[name=updateBtn]");
						$.each(updateButtons, function(i, n) {
							$(n).unbind('click');
							$(n).click(function() {
								(function(taskId) {
									updateJob(taskId);
								})($(n).attr('taskId'));
							});
						});
						var showBtns = $("a[name=showBtn]");
						$.each(showBtns, function(i, n) {
							$(n).unbind('click');
							$(n).click(function() {
								(function(taskId) {
									showJob(taskId);
								})($(n).attr('taskId'));
							});
						});

						var logBtns = $("a[name=logBtn]");
						$.each(logBtns, function(i, n) {
							$(n).unbind('click');
							$(n).click(function() {
								(function(taskId) {
									showlogs(taskId);
								})($(n).attr('taskId'));
							});
						});
					},
					"ajax" : {
						url : 'task/queryTask.action',
						type:'post',
						data : function(d) {
							d.columns = null;
							d.search.regex = null;
							d.searchText = $("#searchText").val();
							d.startTime=$("#startTime").val();
							d.endTime=$("#endTime").val();
						}
					},
					/*
					 * <th>日期</th> <th>运输单数量</th> <th>已竞价运输单</th>
					 * <th>未竞价运输单</th> <th>竞价司机数量</th>
					 */
					"columns" : [ {
						"data" : "taskId"
					}, {
						"data" : "jobCname"
					}, {
						"data" : "jobName"
					}, {
						"data" : "createTime"
					}, {
						"data" : "updateTime"
					}, {
						"data" : "cronExpressionDesc"
					}, {
						"data" : "jobStatusDesc"
					}, {
						"data" : "jobStatus"
					}],

					'searching' : false,
					"createdRow" : function(row, data, index) {
						$('td', row).eq(0).html("<a  taskId='"+data.taskId+"' name='showBtn' style='color:lightblue;'>"+data.taskId+"</a>");
						var stopButton="<a  taskId='"+data.taskId+"' name='cmdBtn'' cmd='stop' href='javascript:void(0);'style='color:lightblue;'>停止</a>";
						var resumeButton="<a  taskId='"+data.taskId+"' name='cmdBtn'  cmd='resume'href='javascript:void(0);' style='color:lightblue;'>恢复</a>";
						var startButton="<a  taskId='"+data.taskId+"' name='cmdBtn'  cmd='start'href='javascript:void(0);' style='color:lightblue;'>运行</a>";
						var pauseButton="<a  taskId='"+data.taskId+"' name='cmdBtn'  cmd='pause'href='javascript:void(0);' style='color:lightblue;'>暂停</a>";
						var runNowButton="<a taskId='"+data.taskId+"' name='runNowBtn' cmd='runNow'href='javascript:void(0);' style='color:lightblue;'>立即执行</a>";
						var updateButton="<a  taskId='"+data.taskId+"' name='updateBtn' style='color:lightblue;'>修改</a>";

						var logButton="<a  taskId='"+data.taskId+"' name='logBtn' style='color:lightblue;'>查看日志</a>";
						if(data.jobStatus==0){
							$('td', row).eq(7).html(startButton+"&nbsp;"+updateButton+"&nbsp;"+logButton);
						}else if(data.jobStatus==1){
							$('td', row).eq(7).html(stopButton+"&nbsp;"+runNowButton+"&nbsp;"+logButton);
						}
						//else if(data.jobStatus==2){
						//	$('td', row).eq(6).html(resumeButton+"&nbsp;"+runNowButton+"&nbsp;"+stopButton+"&nbsp;"+updateButton);
						//}
						else if(data.jobStatus==3){
							$('td', row).eq(7).html(startButton+"&nbsp;"+updateButton+"&nbsp;"+logButton);
						}
						else {
							$('td', row).eq(7).html(logButton);
						}

					}
				});

		$('#order_table tbody').on('click', 'tr', function() {
			$('#order_table tbody tr').removeClass('selected');
			if ($(this).hasClass('selected')) {
				$(this).removeClass('selected');
			} else {
				orderList.$('tr.selected').removeClass('selected');
				$(this).addClass('selected');
			}
		});

	}
}


function  changJobStatus(taskId,cmd){
	$.ajax({
		url: "task/changeJobStatus",
		type:"post",
		data: {
			taskId:taskId,
			cmd:cmd
		},beforeSend:function() {
			forbiddenPage();
		},
		complete:function(data) {
			releasePage();
		},
		dataType:"json",
		success: function(data){
			if(data!=null && data.isSucc){
				layer.msg("调度成功");
				doSearch();
			}else{
				layer.msg(data.msg);
			}
		}
	});
}
function  runJobNow(taskId){
	$.ajax({
		url: "task/runJobNow",
		type:"post",
		data: {
			taskId:taskId
		},
		dataType:"json",
		success: function(data){
			if(data!=null){
				layer.msg(data.msg);
			}else{
				layer.msg("出现异常");
			}
		}
	});

}
function  updateJob(taskId){
	window.location.href="/task/addTriggerPage.action?taskId="+taskId;
}
function  addJob(){
	window.location.href="/task/jobDeatilManagePage.action";
}
function  showJob(taskId){
	window.location.href="/task/addTriggerPage.action?taskId="+taskId+"&op=show";
}
function showlogs(taskId){
	window.location.href="/task/taskLogsrPage.action?taskId="+taskId;
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