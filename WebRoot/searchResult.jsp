<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	List<String> list = (List<String>) request
	.getAttribute("searchResult");
	session.setAttribute("list", list) ;
	int pagenum = (Integer)request.getAttribute("pagesize");
	int docNum = pagenum*10 ;
	int docsize = list.size() / 3;
	
	String time = list.get(list.size()-1) ;
	String resultNum = list.get(list.size()-2) ;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Search Result</title>
<link type="text/css" rel="Stylesheet" href="css/stytle.css" />

<link rel="icon" href="img/web_logo_small.gif" type="img/web_logo" />
<link rel="shortcut icon" href="img/web_logo_small.gif"
	type="img/web_logo" />
<link rel="bookmark" href="img/web_logo_small.gif" type="img/web_logo" />

</head>

<body>

	<div id="head" class="clhead">

		<div id="resultsearch" align="absmiddle">
			<form name="search" method="post" action="blogSearch"
				onsubmit="return headcheck()">

				<input name="search_content" type="text"
					style="width:530px;height:30px" id="inputsave" /> <input
					type="submit" name="submit" value="Search" class="kpbb"
					style="width:70px;height:30px" align="absmiddle"></input>

			</form>
		</div>
		<div class="resultlogo">
			<a href="./" target="blank"><img src="img/search_logo.gif"
				width="80" height="28" /> </a>
		</div>
	</div>

	<div id="main" class="main">

		<div class="rsabout">
			About
			<%=resultNum%>
			results (<%=time%>
			seconds)
		</div>




		<%
			for (; docNum < docsize && docNum<10*(pagenum+1); ++docNum) {
		%>


		<div id="doctitle" class="pt">
			<a class="tclick" href="<%=list.get(3 * docNum)%>" target="_blank">
				<%=list.get(3 * docNum + 1)%> </a>
		</div>
		<div id="doccontent" class="ft">
			<%=list.get(3 * docNum + 2)%>
		</div>

		<%
			}
		%>




		<%
			if ((docsize / 10 + 1) > 1) {
		%>
		<%
			for (int pagesize = 0; pagesize < docsize / 10 + 1; ++pagesize) {
		%>
		<a href="turnPage?pagesize=<%=pagesize%>" class="click"><%=pagesize + 1%>&nbsp&nbsp</a>
		<%
			}
		%>
		<%
			}
		%>


	</div>


	<div id="resultfoot" class="resultfoot">
		<div id="foot">
			<span class="muted">© CQU Source . All Rights Reserved.</span>
		</div>
	</div>


</body>


<script LANGUAGE="javascript">
function headcheck()
{
	if(document.search.search_content.value.length==0){
	 	document.search.search_content.focus(); //文本为空时不处理
	  	return false;
	}
}
</script>

</html>
