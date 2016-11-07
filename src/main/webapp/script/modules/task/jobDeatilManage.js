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
								(function(jobId,cmd) {
									changJobStatus(jobId,cmd);
								})($(n).attr('jobId'),$(n).attr('cmd'));
							});
						});
						var runNowButtons = $("a[name=runNowBtn]");
						$.each(runNowButtons, function(i, n) {
							$(n).unbind('click');
							$(n).click(function() {
								(function(jobId) {
									runJobNow(jobId);
								})($(n).attr('jobId'));
							});
						});


						var updateButtons = $("a[name=updateBtn]");
						$.each(updateButtons, function(i, n) {
							$(n).unbind('click');
							$(n).click(function() {
								(function(jobId) {
									updateJob(jobId);
								})($(n).attr('jobId'));
							});
						});
						var showBtns = $("a[name=showBtn]");
						$.each(showBtns, function(i, n) {
							$(n).unbind('click');
							$(n).click(function() {
								(function(jobId) {
									showJob(jobId);
								})($(n).attr('jobId'));
							});
						});


						var addBtns = $("a[name=addBtn]");
						$.each(addBtns, function(i, n) {
							$(n).unbind('click');
							$(n).click(function() {
								(function(jobId) {
									addTrigger(jobId);
								})($(n).attr('jobId'));
							});
						});
					},
					"ajax" : {
						url : 'task/queryJobDeatil.action',
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
						"data" : "jobId"
					}, {
						"data" : "jobName"
					}, {
						"data" : "jobCname"
					}, {
						"data" : "createTime"
					}, {
						"data" : "updateTime"
					}, {
						"data" : "jobStatus"
					}],

					'searching' : false,
					"createdRow" : function(row, data, index) {
						$('td', row).eq(0).html("<a  jobId='"+data.jobId+"' name='showBtn' style='color:lightblue;'>"+data.jobId+"</a>");
						var stopButton="<a  jobId='"+data.jobId+"' name='cmdBtn'' cmd='stop' href='javascript:void(0);'style='color:lightblue;'>删除</a>";
						var resumeButton="<a  jobId='"+data.jobId+"' name='cmdBtn'  cmd='resume'href='javascript:void(0);' style='color:lightblue;'>恢复</a>";
						var startButton="<a  jobId='"+data.jobId+"' name='cmdBtn'  cmd='start'href='javascript:void(0);' style='color:lightblue;'>运行</a>";
						var pauseButton="<a  jobId='"+data.jobId+"' name='cmdBtn'  cmd='pause'href='javascript:void(0);' style='color:lightblue;'>暂停</a>";
						var runNowButton="<a jobId='"+data.jobId+"' name='runNowBtn' cmd='runNow'href='javascript:void(0);' style='color:lightblue;'>立即执行</a>";
						var addButton="<a jobId='"+data.jobId+"' name='addBtn' cmd='runNow'href='javascript:void(0);' style='color:lightblue;'>添加到调度中</a>";
						var updateButton="<a  jobId='"+data.jobId+"' name='updateBtn' style='color:lightblue;'>修改</a>";
						if(data.status=='1'){
							$('td', row).eq(5).html(addButton+"&nbsp;"+stopButton);
						}
						else {
							//$('td', row).eq(5).html(updateButton);
							$('td', row).eq(5).html("无可用操作");
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


function  changJobStatus(jobId,cmd){
	$.ajax({
		url: "task/changeJobDeatil",
		type:"post",
		data: {
			jobId:jobId,
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
				layer.msg("更改成功");
				doSearch();
			}else{
				layer.msg(data.msg);
			}
		}
	});
}

function  updateJob(jobId){
	window.location.href="/task/addJobDeatilPage.action?jobId="+jobId;
}
function  showJob(jobId){
	window.location.href="/task/addJobDeatilPage.action?jobId="+jobId+"&op=show";
}

function  addJob(){
	window.location.href="/task/addJobDeatilPage.action";
}


function  addTrigger(jobId){
	window.location.href="/task/addTriggerPage.action?jobId="+jobId;
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