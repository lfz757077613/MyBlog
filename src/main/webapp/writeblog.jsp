<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Write Blog</title>
    <link rel="stylesheet" href="EditorMd/examples/css/style.css"/>
    <link rel="stylesheet" href="EditorMd/css/editormd.css"/>
    <link rel="shortcut icon" href="https://pandao.github.io/editor.md/favicon.ico" type="image/x-icon"/>

    <link href="css/index.css" rel="stylesheet">
    <link href="css/about.css" rel="stylesheet"/>
</head>
<body>
<header>
    <div id="logo"><a href="/"></a></div>
    <nav class="topnav" id="topnav">
        <a href="selectAllBlog"><span>Home</span><span class="en">主页</span></a>
        <a href="adminblog"><span>Admin Blog</span><span class="en">管理博客</span></a>
        <a href="admindiary"><span>Admin Diary</span><span class="en">管理日记</span></a>
        <a href="writeblog.jsp"><span>Write Blog</span><span class="en">写博客</span></a>
        <a href="writediary.jsp"><span>Write Diary</span><span class="en">写日记</span></a>
    </nav>
</header>
<div class="box">
    <form action="writeBlog" method="post">
        <div class="admin">
            <h1 class="t_nav"><span>分享使人感到愉悦。</span></h1><br/>

            <div id="layout">
                <header>

                    <h1>写博客</h1>
                </header>
                <div class="blogtitle">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    标题 ：<input type="text" name="blogtitle" style="width: 400px;height:25px;"/>
                </div>
                <br/>
                <br/>
                <br/>
                <input type="submit" value="upload" style="background-color:#0055AA;color: white;font-size: 15px;
            	height:30px;width:80px;display:inline-block;float: left;margin-left: 55px;"/>
                <br/>
                <br/>
                <br/>

                <div id="test-editormd">
                <textarea style="display:none;" name="article"></textarea>
                </div>
            </div>
            <script src="EditorMd/examples/js/jquery.min.js"></script>
            <script src="EditorMd/editormd.min.js"></script>
            <script type="text/javascript">
                var testEditor;

                $(function () {
                    testEditor = editormd("test-editormd", {
                        width: "90%",
                        height: 800,
                        syncScrolling: "single",
                        path: "EditorMd/lib/"
                    });

                    /*
                     // or
                     testEditor = editormd({
                     id      : "test-editormd",
                     width   : "90%",
                     height  : 640,
                     path    : "../lib/"
                     });
                     */
                });
            </script>
        </div>
    </form>
</div>
</body>
</html>

</html>