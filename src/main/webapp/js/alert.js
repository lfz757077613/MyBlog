function fun1(){
    	var result = confirm("需要验证您的管理员身份");
    if(result){//true
    	var password=prompt("请输入你的管理员密码：");
    	if (password=="000000"){
        window.location.href="selectAllBlog2"
      }
    } else { //false
        window.history.back(-1)
    }
}