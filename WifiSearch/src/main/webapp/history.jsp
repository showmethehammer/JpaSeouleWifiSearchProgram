<%@page import="sqliteDB.SqliteCRUD"%>
<%@page import="sqliteDB.DbList"%>
<%@page import="java.util.ArrayList"%>
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
	 		위치 히스토리 목록
	 </span>
<br> 
 	<span style="
	 font-size:1em;
	 "
	 >
 		<a href="<%= request.getContextPath() %>/index.jsp">홈</a>|
 		<a href="<%= request.getContextPath() %>/history.jsp">위치 히스토리목록</a>|
 		<a href="<%= request.getContextPath() %>/index.jsp">Open API 와이파이 정보 가져오기</a>
 	</span>	
 	<table style="border:1 ; width:1500px; height:30px;"  >
		<tr bgcolor="#7FFF00">
			<th scope="col" width = 80px;>ID</th>
			<th scope="col" width = 350px;>X좌표</th>
			<th scope="col" width = 350px>Y좌표</th>
			<th scope="col" width = 500px>조회일자</th>
			<th scope="col">비고</th>

		</tr>
		<% SqliteCRUD db = new SqliteCRUD();
			ArrayList<DbList> dblist = new ArrayList<>();
			dblist = db.select();
			String del = request.getParameter("del");
			if(del != null){

				db.dbDell(dblist.get(Integer.parseInt(del)).getDate());
				dblist = db.select();
			}
			for(int i = 0 ; i < dblist.size(); i++){%>
		<tr>
		
				<th scope = "col"> <%= (i+1) %></th>	
				<th scope = "col"> <%= dblist.get(i).getX() %></th>
				<th scope = "col"> <%= dblist.get(i).getY() %></th>
				<th scope = "col"> <%= dblist.get(i).getDate() %></th>
				<th scope = "col"> <form name="from1" action = "">
	 								<button type="submit" name="del" value = <%= i %>>삭제</button>
									</form> </th>
		</tr>
		<%} %>
	</table>

</body>
</html>