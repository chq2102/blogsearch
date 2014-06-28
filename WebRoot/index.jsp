<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>blog search</title>
<link type="text/css" rel="Stylesheet" href="css/stytle.css" />

<link rel="icon" href="img/web_logo_small.gif" type="img/web_logo" />
<link rel="shortcut icon" href="img/web_logo_small.gif" type="img/web_logo" />
<link rel="bookmark" href="img/web_logo_small.gif" type="img/web_logo" />

</head>

<body>
	<div class="index">
		<div id="logo" style="height:231px;margin-top:20px">
			<img alt="Google" height="85" id="hplogo" src="img/search_logo.gif"
				style="padding-top:112px" width="269">
		</div>

		<div id="search">
			<form name="search" method="post" action="blogSearch"
				onsubmit="return check()">

				<input name="search_content" type="text"
					style="width:530px;height:30px" /> <input type="submit"
					name="submit" value="Search" class="kpbb"
					style="width:70px;height:30px" align="absmiddle"></input>

			</form>
		</div>
	</div>
</body>

<script LANGUAGE="javascript">
function check()
{
	if(document.search.search_content.value.length==0){
	  	//alert("输入框不能为空!");
	 	document.search.search_content.focus(); //文本为空时不处理
	  	return false;
	}
}
</script>

</html>
