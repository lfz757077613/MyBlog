/* 博客发布 */
// Markdown 编辑器
var testEditor;

$(function () {
    testEditor = editormd("test-editormd", {
        width: "100%",
        height: 640,
        syncScrolling: "single",
        path: "/static/vendor/editor/lib/",
        saveHTMLToTextarea: true
    });

    $("#id_btn_blog_submit").bind("click", function () {
        /* markdown 格式文本 */
        $("#id_input_md").val(testEditor.getMarkdown());
        /* html 格式内容 */
        $("#id_input_html").val($(".markdown-body").prop('outerHTML'));
        var form = document.forms[0];
        form.action = "/api/addBlog";
        form.method = "post";
        form.submit();
    });
});