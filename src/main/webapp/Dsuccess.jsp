<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<script>
window.onload = function(){ //设置当页面加载时执行
	var result = confirm("上传成功");
if(result) //判断是否点击确定
window.location ="writediary.jsp" //确定的话游览器自身跳转
}
</script>
</body>

</html>