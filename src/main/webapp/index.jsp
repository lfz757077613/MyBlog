<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>index</title>
    <link rel="stylesheet" type="text/css" href="css/index.css"/>
    <script type="text/javascript" src="js/jquery-3.2.1.min.js"></script>
</head>
<body>
<header>
    <div id="logo"><a href="/"></a></div>
    <nav class="topnav" id="topnav">
        <a href="selectAllBlog"><span>Home</span><span class="en">主页</span></a>
        <a href="about.jsp"><span>About</span><span class="en">关于我</span></a>
        <a href="selectAllDiary"><span>Diary</span><span class="en">日记心得</span></a>
        <a href="gustbook.jsp"><span>Gustbook</span><span class="en">留言板</span></a>
        <a href="#" onclick="fun1()"><span>Admin</span><span class="en">管理</span></a>
    </nav>
</header>
<div class="box">
    <div class="banner"></div>
    <br>

    <h2 class="title">文章列表</h2>

    <div class="bloglist">
        <div class="wz">
            <c:forEach items="${blogs}" var="blogs">
                <h3><c:out value="${blogs.blogtitle}"/></h3>

                <ul>

                    <a title="阅读全文" href="selectBlogById?blogid=<c:out value="${blogs.blogid}"/>" class="readmore">阅读全文>></a>
                </ul>
            </c:forEach>
            <div class="clear"></div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="js/alert.js"></script>
</html>
