<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
<head>
    <base href="<%=basePath%>">
    <!-- start: Meta -->
    <meta charset="utf-8">
    <title>lesso</title>
    <meta name="description" content="Bootstrap Metro Dashboard">
    <meta name="author" content="Dennis Ji">
    <meta name="keyword" content="Metro, Metro UI, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
    <!-- end: Meta -->
    <!-- start: Mobile Specific -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- end: Mobile Specific -->
    <!-- start: CSS -->
    <link id="bootstrap-style" href="script/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="script/plugins/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
    <link href="script/plugins/dataTable/dataTables.bootstrap.css"  rel="stylesheet" >
    <link href="script/plugins/dataTable/jquery.dataTables.css"  rel="stylesheet" >
    <link id="base-style" href="script/plugins/bootstrap/css/style.css" rel="stylesheet">
    <link id="base-style" href="script/plugins/datepicker/datepicker3.css" rel="stylesheet">
    <link id="base-style-responsive" href="script/plugins/bootstrap/css/style-responsive.css" rel="stylesheet">
    <%--<link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800&subset=latin,cyrillic-ext,latin-ext' rel='stylesheet' type='text/css'>--%>
    <!-- end: CSS -->


    <!-- The HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <link id="ie-style" href="script/plugins/bootstrap/css/ie.css" rel="stylesheet">
    <![endif]-->
    <!--[if IE 9]>
    <link id="ie9style" href="script/plugins/bootstrap/css/ie9.css" rel="stylesheet">
    <![endif]-->
    <!-- start: Favicon -->
    <%--<link rel="shortcut icon" href="script/plugins/bootstrap/img/favicon.ico">--%>
    <!-- end: Favicon -->
</head>
<body>
<!-- start: Header -->
<jsp:include page="../include/head.jsp"></jsp:include>
<!-- start: Header -->

<div class="container-fluid-full">
    <div class="row-fluid">

        <!-- start: Main Menu -->
        <jsp:include page="../include/menu.jsp"></jsp:include>
        <!-- end: Main Menu -->
        <noscript>
            <div class="alert alert-block span10">
                <h4 class="alert-heading">Warning!</h4>
                <p>You need to have <a href="http://en.wikipedia.org/wiki/JavaScript" target="_blank">JavaScript</a> enabled to use this site.</p>
            </div>
        </noscript>

        <!-- start: Content -->
        <div id="content" class="span10">
                <ul class="breadcrumb">
                    <li>
                        <i class="icon-home"></i>
                        <a href="index.html">首页</a>
                        <i class="icon-angle-right"></i>
                    </li>
                    <li><a href="#">客户日志详情</a></li>
                </ul>
                <div class="span12">
                    <span class="form-control">用户id:</span><input type="text" class="form-control " id="userCode" placeholder=""/>
                </div>
                <div class="span12">
                    <span class="form-control">账户类型:</span><input type="text" class="form-control " id="appType" placeholder=""/>
                    <%--<input type="checkbox" class="form-control " id="isConfigedOfJob"/><span class="form-control">使用配置文件job</span>--%>
                </div>
                <div class="span12">
                    <span class="form-control">登录端设备:</span><input type="text" class="form-control " id="device" placeholder=""/>
                </div>
                <div class="span12">
                    <span class="form-control">ip地址:</span><input type="text" class="form-control " id="ipAddress" placeholder=""/>
                </div>
                <div class="span12">
                    <span class="form-control">操作系统:</span><input type="text" class="form-control " id="optSystem" placeholder=""/>
                </div>
                <div class="span12">
                    <span class="form-control">操作浏览器:</span><input type="text" class="form-control " id="browser" placeholder=""/>
                </div>

                <div class="span12">
                    <span class="form-control">操作模块:</span><input type="text" class="form-control " id="behavior" placeholder=""/>
                </div>
                <div class="span12">
                    <span class="form-control">操作内容:</span><input type="text" class="form-control " id="behaviorContent" placeholder=""/>
                </div>
            </div><!--/row-->
        </div><!--/.fluid-container-->
        <!-- end: Content -->
    </div><!--/#content.span10-->
</div><!--/fluid-row-->

<div class="modal hide fade" id="myModal">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">×</button>
        <h3>Settings</h3>
    </div>
    <div class="modal-body">
        <p>Here settings can be configured...</p>
    </div>
    <div class="modal-footer">
        <a href="#" class="btn" data-dismiss="modal">Close</a>
        <a href="#" class="btn btn-primary">Save changes</a>
    </div>
</div>
<div class="common-modal modal fade" id="common-Modal1" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-content">
        <ul class="list-inline item-details">
            <li><a href="http://themifycloud.com">Admin templates</a></li>
            <li><a href="http://themescloud.org">Bootstrap themes</a></li>
        </ul>
    </div>
</div>
<div class="clearfix"></div>
<jsp:include page="../include/foot.jsp"></jsp:include>
<%--<script src="script/plugins/select2/js/select2.full.js"></script>--%>
<script src="script/modules/index/custLogsDeatil.js"></script>
<%--<footer>--%>

    <%--<p>--%>
        <%--<span style="text-align:left;float:left">&copy; 2013 <a href="http://themifycloud.com/downloads/janux-free-responsive-admin-dashboard-template/" alt="Bootstrap_Metro_Dashboard">JANUX Responsive Dashboard</a></span>--%>

    <%--</p>--%>

<%--</footer>--%>

<%--<!-- start: JavaScript-->--%>

<%--<script src="script/plugins/bootstrap/js/jquery-1.9.1.min.js"></script>--%>
<%--<script src="script/plugins/bootstrap/js/jquery-migrate-1.0.0.min.js"></script>--%>

<%--<script src="script/plugins/bootstrap/js/jquery-ui-1.10.0.custom.min.js"></script>--%>

<%--<script src="script/plugins/bootstrap/js/jquery.ui.touch-punch.js"></script>--%>

<%--<script src="script/plugins/bootstrap/js/modernizr.js"></script>--%>

<%--<script src="script/plugins/bootstrap/js/bootstrap.min.js"></script>--%>

<%--<script src="script/plugins/bootstrap/js/jquery.cookie.js"></script>--%>

<%--<script src='script/plugins/bootstrap/js/fullcalendar.min.js'></script>--%>

<%--<script src="script/plugins/bootstrap/js/excanvas.js"></script>--%>

<%--<script src="script/plugins/bootstrap/js/jquery.flot.js"></script>--%>

<%--<script src="script/plugins/bootstrap/js/jquery.flot.pie.js"></script>--%>

<%--<script src="script/plugins/bootstrap/js/jquery.flot.stack.js"></script>--%>

<%--<script src="script/plugins/bootstrap/js/jquery.flot.resize.min.js"></script>--%>

<%--<script src="script/plugins/bootstrap/js/jquery.chosen.min.js"></script>--%>

<%--<script src="script/plugins/bootstrap/js/jquery.uniform.min.js"></script>--%>

<%--<script src="script/plugins/bootstrap/js/jquery.cleditor.min.js"></script>--%>

<%--<script src="script/plugins/bootstrap/js/jquery.noty.js"></script>--%>

<%--<script src="script/plugins/bootstrap/js/jquery.elfinder.min.js"></script>--%>

<%--<script src="script/plugins/bootstrap/js/jquery.raty.min.js"></script>--%>

<%--<script src="script/plugins/bootstrap/js/jquery.iphone.toggle.js"></script>--%>

<%--<script src="script/plugins/bootstrap/js/jquery.uploadify-3.1.min.js"></script>--%>

<%--<script src="script/plugins/bootstrap/js/jquery.gritter.min.js"></script>--%>

<%--<script src="script/plugins/bootstrap/js/jquery.imagesloaded.js"></script>--%>

<%--<script src="script/plugins/bootstrap/js/jquery.masonry.min.js"></script>--%>

<%--<script src="script/plugins/bootstrap/js/jquery.knob.modified.js"></script>--%>

<%--<script src="script/plugins/bootstrap/js/jquery.sparkline.min.js"></script>--%>

<%--<script src="script/plugins/bootstrap/js/counter.js"></script>--%>

<%--<script src="script/plugins/bootstrap/js/retina.js"></script>--%>

<%--&lt;%&ndash;<script src='script/plugins/bootstrap/js/jquery.dataTables.min.js'></script>&ndash;%&gt;--%>

<%--<script src="script/plugins/dataTable/jquery.dataTables.min.js"></script>--%>

<%--<script src="script/plugins/dataTable/dataTables.bootstrap.js"></script>--%>


<%--<script src="script/plugins/dataTable/input2.js"></script>--%>
<%--<script src="script/plugins/datepicker/bootstrap-datepicker.js"></script>--%>
<%--<script src="script/plugins/datepicker/locales/bootstrap-datepicker.zh-CN.js"></script>--%>
<%--<script src="script/modules/index/custom.js"></script>--%>
<%--<!-- end: JavaScript-->--%>
</body>
</body>
</html>