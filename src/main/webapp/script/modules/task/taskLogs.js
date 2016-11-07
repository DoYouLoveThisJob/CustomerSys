/* -------------------- Check Browser --------------------- */
var orderList;
var  taskId;
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
	taskId=getUrlParam("taskId");
	doSearch();
	querySumInfoOfLogs(taskId);
	$('#queryBtn').click(function(){
		doSearch();
		querySumInfoOfLogs(taskId);
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
				d.taskId=taskId;
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

					},
					"ajax" : {
						url : 'task/queryLogs.action',
						type:'post',
						data : function(d) {
							d.columns = null;
							d.search.regex = null;
							d.searchText = $("#searchText").val();
							d.startTime=$("#startTime").val();
							d.endTime=$("#endTime").val();
							d.taskId=taskId;
						}
					},
					/*
					 * <th>日期</th> <th>运输单数量</th> <th>已竞价运输单</th>
					 * <th>未竞价运输单</th> <th>竞价司机数量</th>
					 */
					"columns" : [ {
						"data" : "taskId"
					}, {
						"data" : "jobCName"
					}, {
						"data" : "createTime"
					}, {
						"data" : "statusDesc"
					}, {
						"data" : "processResult"
					}],

					'searching' : false,
					"createdRow" : function(row, data, index) {

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


function  querySumInfoOfLogs(taskId){
	$.ajax({
		url: "task/querySumInfoOfLogs",
		type:"post",
		data: {
			taskId:taskId,
			searchText : $("#searchText").val(),
			startTime:$("#startTime").val(),
			endTime:$("#endTime").val()
		},beforeSend:function() {
			forbiddenPage();
		},
		complete:function(data) {
			releasePage();
		},
		dataType:"json",
		success: function(data){
			if(data!=null && data.isSucc){
				var obj=data.obj;
				$("#allCount").text(obj.allCount);
				$("#successCount").text(obj.successCount);
				$("#falseCount").text(obj.falseCount);
			}
		}
	});
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