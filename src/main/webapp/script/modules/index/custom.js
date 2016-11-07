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
						var showBtns = $("a[name=showBtn]");
						$.each(showBtns, function(i, n) {
							$(n).unbind('click');
							$(n).click(function() {
								(function(logId) {
									showLog(logId);
								})($(n).attr('logId'));
							});
						});

					},
					"ajax" : {
						url : 'logs/queryBehaviorLogsList.action',
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
						"data" : "userCode"
					}, {
						"data" : "objectid"
					}, {
						"data" : "createTime"
					}, {
						"data" : "appTypeDesc"
					}, {
						"data" : "ipAddress"
					}, {
						"data" : "optSystemDesc"
					}, {
						"data" : "browserDesc"
					}, {
						"data" : "behaviorDesc"
					}],

					'searching' : false,
					"createdRow" : function(row, data, index) {
						var objectId=data.objectid;
						if(objectId==null){
							objectId=data.id;
						}
						$('td', row).eq(1).html("<a  logId='"+data.id+"' name='showBtn' style='color:lightblue;'>"+objectId+"</a>");

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

function  showLog(logId){
	window.location.href="/logs/custLogsDeatilPage.action?logId="+logId+"&op=show";
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