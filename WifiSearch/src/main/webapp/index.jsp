<%@page import="sqliteDB.*"%>
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
	 <%
		String myX = "37.539061";
		String myY = "127.069166"; 
	 	SqliteCRUD sql = new SqliteCRUD();
	 	WifiDataDB wd = new WifiDataDB();
	 	ArrayList<WifiDataSetting> wifiList = new ArrayList<>();
	 	String home = request.getParameter("home");
	 	String X;
	 	String Y;
	 	if(home != null){
	 		X = myX;
	 		Y = myY;
	 		home = "0";
	 	}else{
			X = request.getParameter("X");
			Y = request.getParameter("Y");
			 

			if (X != null && Y != null && X != "" && Y != "" && home == null
			&& (Double.parseDouble(X)) < 37.693707 && (Double.parseDouble(X)) > 37.184
			&& (Double.parseDouble(Y)) < 127.18 && (Double.parseDouble(Y)) > 126.795){
				System.out.println("시작");
				sql.dbInsert(X, Y);	
				wifiList = wd.search(X, Y);
				
				if(wifiList == null){
					System.out.println("00");
				}else{
					
					
				}
				System.out.println("10");
			}else {
				X = "0.0";
				Y = "0.0";
				System.out.println("안함");
			}
	 	}
	%>

 
<span style="
	 font-size:3em;
	 font-weight:bold;"
	 >
	 		서울 와이파이 정보 구하기
	 </span>
<span style="
	 font-size:1em;
	 font-weight:bold;"
	 >
	 		LAT - 최대: 37.694 , 최소: 37.18
	 		LAT - 최대: 127.18 , 최소 126.795
	 </span>
<br> 
 	<span style="
	 font-size:1em;
	 "
	 >
 		<a href="<%= request.getContextPath() %>/index.jsp">홈</a>|
 		<a href="<%= request.getContextPath() %>/history.jsp">위치 히스토리목록</a>|
 		<a href="<%= request.getContextPath() %>/DataUpdate.jsp">Open API 와이파이 정보 가져오기</a>
 
 	</span>	


 
	 <form name="from1" action = "">
	  LAT <input type="number" step="0.0000001" name="X" value="<%=X%>">
	  , LNT <input type="number" step="0.0000001" name="Y" value="<%=Y%>">
	  <button type="submit" name="home" onclick="value = 1" >내위치 가져오기</button>
	  <button type="submit" onclick="">근처 Wifi 정보 보기</button>
	</form>
	

 
	<table style="border:1 ; width:1500px; height:30px;"  >
		<tr bgcolor="#7FFF00" >
	
			<th scope="col" width = "100px"  >거리(KM)</th>
			<th scope="col" width = "100px">관리번호</th>
			<th scope="col" width = "50px">자치구</th>
			<th scope="col" width = "150px">와이파이명</th>
			<th scope="col" width = "100px">도로명주소</th>
			<th scope="col" width = "150px">상세주소</th>
			<th scope="col" width = "50px">층</th>
			<th scope="col" width = "70px">설치유형</th>
			<th scope="col" width = "70px">설치기관</th>
			<th scope="col" width = "100px">서비스구분</th>
			<th scope="col" width = "50px">망종류</th>
			<th scope="col" width = "40px">설치년도</th>
			<th scope="col" width = "50px">실내외구분</th>
			<th scope="col" width = "70px">접속환경</th>
			<th scope="col" width = "70px">X좌표</th>
			<th scope="col" width = "70px">Y좌표</th>
			<th scope="col" >작업일자</th>
		</tr>
		
		<%if(wifiList.size() == 0){ %>
		<tr>
		<th scope="col" colspan = '17'>위치 정보를 입력한 후에 조회해 주세요.</th>
		</tr>
		<%}else{
			for(int i = 0 ; i < wifiList.size(); i++){%>
			<tr>	
				<th scope = "col"> <%= wifiList.get(i).getkm() %></th>
				<th scope = "col"> <%= wifiList.get(i).getX_SWIFI_MGR_NO()      %></th>
				<th scope = "col"> <%= wifiList.get(i).getX_SWIFI_WRDOFC()      %></th>
				<th scope = "col"> <%= wifiList.get(i).getX_SWIFI_MAIN_NM()     %></th>
				<th scope = "col"> <%= wifiList.get(i).getX_SWIFI_ADRES1()      %></th>
				<th scope = "col"> <%= wifiList.get(i).getX_SWIFI_ADRES2()      %></th>
				<th scope = "col"> <%= wifiList.get(i).getX_SWIFI_INSTL_FLOOR() %></th>
				<th scope = "col"> <%= wifiList.get(i).getX_SWIFI_INSTL_TY()    %></th>
				<th scope = "col"> <%= wifiList.get(i).getX_SWIFI_INSTL_MBY()   %></th>
				<th scope = "col"> <%= wifiList.get(i).getX_SWIFI_SVC_SE()      %></th>
				<th scope = "col"> <%= wifiList.get(i).getX_SWIFI_CMCWR()       %></th>
				<th scope = "col"> <%= wifiList.get(i).getX_SWIFI_CNSTC_YEAR()  %></th>
				<th scope = "col"> <%= wifiList.get(i).getX_SWIFI_INOUT_DOOR()  %></th>
				<th scope = "col"> <%= wifiList.get(i).getX_SWIFI_REMARS3()     %></th>
				<th scope = "col"> <%= wifiList.get(i).getLAT()                 %></th>
				<th scope = "col"> <%= wifiList.get(i).getLNT()                 %></th>
				<th scope = "col"> <%= wifiList.get(i).getWORK_DTTM()           %></th>
			</tr>
			<%}
		  }%>
	</table>

</body>
</html>