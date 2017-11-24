<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>GustBook</title>
    <link href="css/index.css" rel="stylesheet">
    <script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
</head>
<body>
<div class="box">
    <div id="logo"><a href="/"></a></div>
    <nav class="topnav" id="topnav">
        <a href="selectAllBlog"><span>Home</span><span class="en">主页</span></a>
        <a href="about.jsp"><span>About</span><span class="en">关于我</span></a>
        <a href="selectAllDiary"><span>Diary</span><span class="en">日记</span></a>
        <a href="gustbook.jsp"><span>Gustbook</span><span class="en">留言板</span></a>
        <a href="#" onclick="fun1()"><span>Admin</span><span class="en">管理</span></a>
    </nav>
    <br>
    <br> <br>
    <!-- UY BEGIN -->
    <div id="uyan_frame"></div>
    <script type="text/javascript" src="http://v2.uyan.cc/code/uyan.js?uid=2142168"></script>
    <!-- UY END -->
</div>
</body>
<script type="text/javascript" src="js/alert.js"></script>
</html>
