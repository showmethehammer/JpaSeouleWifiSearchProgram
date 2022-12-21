<%@page import="sqliteDB.DataUpdate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<span style="
	 font-size:3em;
	 font-weight:bold;"
	 >
	 <% DataUpdate ud = new DataUpdate();
		int cts = ud.upDate();
	%>
	<%=cts %> 개의 정보를 저장해였습니다.
	 </span>
<br> 

	
	
 	<span style="font-size:1em;">
 		<a href="<%= request.getContextPath() %>/index.jsp">홈으로 가기</a>|
 	</span>	
</body>
</html>