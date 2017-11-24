<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>SayList</title>
		<link href="css/index.css" rel="stylesheet">
		<link href="css/saylist.css" rel="stylesheet">
</head>
<body>
		<div class="box">
		  <div id="logo"><a href="/"></a></div>
		<nav class="topnav" id="topnav">
      <a href="selectAllBlog"><span>Home</span><span class="en">主页</span></a>
      <a href="about.jsp"><span>About</span><span class="en">关于我</span></a>
     <a href="selectAllDiary"><span>Diary</span><span class="en">日记心得</span></a>
      <a href="gustbook.jsp"><span>Gustbook</span><span class="en">留言板</span></a>
      <a href="#" onclick="fun1()"><span>Admin</span><span class="en">管理</span></a>
  </nav>
	<div class="moodlist">
  <h1 class="t_nav"><span>往事随风，只留记忆扰人！</span><a href="/" class="n1">网站首页</a><a href="/" class="n2">个人日记</a></h1>
  <div class="bloglist">
  <c:forEach items="${diarys}" var="diarys">
    <ul class="arrow_box">
     <div class="sy">
      <p><c:out value="${diarys.diary}"></c:out></p>
      </div>
      <span class="dateview"><c:out value="${diarys.time}"></c:out></span>
    </ul>
    </c:forEach>
  </div>
</div>
<footer>
  <p><span>Design By:<a href="www.duanliang920.com" target="_blank">xxx</a></span><span>网站地图</span><span><a href="/">网站统计</a></span></p>
</footer>
<script src="js/nav.js"></script>
</div>
</body>
<script type="text/javascript" src="js/alert.js" ></script>
</html>