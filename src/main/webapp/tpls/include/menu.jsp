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

</head>
<body>
<!-- start: Main Menu -->
<div id="sidebar-left" class="span2">
    <div class="nav-collapse sidebar-nav">
        <ul class="nav nav-tabs nav-stacked main-menu">
            <%--<li><a href="login.html"><i class="icon-lock"></i><span class="hidden-tablet"> Login Page</span></a></li>--%>
            <li><a href="logs/custLogsPage.action"><i class="icon-calendar"></i><span class="hidden-tablet"> 客户日志</span></a></li>
                <li><a href="task/taskManagePage.action"><i class="icon-calendar"></i><span class="hidden-tablet">定时任务</span></a></li>
        </ul>
    </div>
</div>
<!-- end: Main Menu -->
</body>
</html>