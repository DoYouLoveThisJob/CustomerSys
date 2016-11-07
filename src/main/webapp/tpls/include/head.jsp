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
    <meta name="description" content="Bootstrap Metro Dashboard">
    <meta name="author" content="Dennis Ji">
    <meta name="keyword" content="Metro, Metro UI, Dashboard, Bootstrap, Admin, Template, Theme, Responsive, Fluid, Retina">
    <!-- end: Meta -->
    <!-- start: Mobile Specific -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- end: Mobile Specific -->
    <!-- start: CSS -->
    <link id="bootstrap-style" href="script/plugins/layer/1.9.1/skin/layer.css" rel="stylesheet">
</head>
<body>
<!-- start: Header -->
<div class="navbar">
    <div class="navbar-inner">
        <div class="container-fluid">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".top-nav.nav-collapse,.sidebar-nav.nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </a>
            <a class="brand" href="index.html"><span>LESSO</span></a>

            <!-- start: Header Menu -->
            <%--<div class="nav-no-collapse header-nav">--%>
                <%--<ul class="nav pull-right">--%>
                    <%--<!-- start: User Dropdown -->--%>
                    <%--<li class="dropdown">--%>
                        <%--<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">--%>
                            <%--<i class="halflings-icon white user"></i> Dennis Ji--%>
                            <%--<span class="caret"></span>--%>
                        <%--</a>--%>
                        <%--<ul class="dropdown-menu">--%>
                            <%--<li class="dropdown-menu-title">--%>
                                <%--<span>Account Settings</span>--%>
                            <%--</li>--%>
                            <%--<li><a href="#"><i class="halflings-icon user"></i> Profile</a></li>--%>
                            <%--<li><a href="login.html"><i class="halflings-icon off"></i> Logout</a></li>--%>
                        <%--</ul>--%>
                    <%--</li>--%>
                    <%--<!-- end: User Dropdown -->--%>
                <%--</ul>--%>
            <%--</div>--%>
            <!-- end: Header Menu -->

        </div>
    </div>
</div>
<!-- start: Header -->
</body>
</html>