package sqliteDB;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;

public class DataUpdate {
	public int upDate() {
		// 버퍼 이용: BufferedReader --> 버퍼를 이용해서 읽고 쓰는 함수.
				// 버퍼? : 입출려 속도 향상을 위해서 데이터를 일시적으로 메모리 영역의 한 곳에 모아두는 것.
				// 버퍼의 장점은 입출력 관련 처리 작업을 매우 빠르게 할수 있다.
		int cts = 1;
		int ctslow = 1;
		int ctshight = 200;
		WifiDataDB wfDb = new WifiDataDB();
		wfDb.dbDell();
		loop1:
		while(true) {
			BufferedReader br = null;
			try {
				// 공공 API 기관 인증키 및 전체주소
				// 번수에 여러 값을 넣어 주소 체계를 만들어야 한다.--> StringBuilder를 사용.
				// String: 불변(immutable)성을 가지므로 문자열을 더할 때 매번 새로운 객체를 생성해서 참조하는 방식 --> 값 변경.X
				// StringBuilder : 문자열을 더해나갈때 새로운 객체를 매번 생성하는 것이 아닌 기존 데이터 값에 추가해가는 방식, 상대적으로 속도가 빠르다.
				//				 : mutable 속성이고, append().insert().delete()등을 사용해 값을 변경.
				// 공공 API방식 --> StringBuilder 사용.
				String urlStr = "http://openapi.seoul.go.kr:8088/67506f626230383136324a7259775a/json/TbPublicWifiInfo/"+ctslow+"/" + ctshight + "/";
				// url 클래스 객체 생성 --> 2가지방법 : 절대경로, 상대경로
				URL url = new URL(urlStr);
				
				// open Connection() 메서드를 이용한 연결
				// URL 주소의 원격 객체에 접속한 뒤 -> 통신할 수 있는 URLConnection 객체 리턴.
				HttpURLConnection urlconn = (HttpURLConnection)url.openConnection();
				urlconn.setRequestMethod("GET");
				urlconn.setRequestProperty("Content-type", "application/json");
				System.out.println("코드"+urlconn.getResponseCode());// 응답코드가 200이 출력돼야함.
				
				if(urlconn.getResponseCode() >= 200 && urlconn.getResponseCode() <= 300) {
					br = new BufferedReader(new InputStreamReader(urlconn.getInputStream(), "UTF-8"));
					//System.out.println("rkskekfk");
				} else {
					br = new BufferedReader(new InputStreamReader(urlconn.getErrorStream(), "UTF-8"));
				}
				
						
				StringBuilder sb = new StringBuilder();
				String line = "";
				String result = "";
				while ((line = br.readLine()) != null) {
					result = result.concat(line);
				}
				//System.out.println(result);
				
				JSONParser parser = new JSONParser();
				JSONObject obj = (JSONObject) parser.parse(result);
				
				JSONObject parse_TbPublicWifiInfo = (JSONObject) obj.get("TbPublicWifiInfo");
				
				JSONArray row = (JSONArray)parse_TbPublicWifiInfo.get("row");
				//System.out.println(parse_TbPublicWifiInfo.toString());
				
				for (int i=0;i< row.size();i++) {
		            JSONObject weather = (JSONObject) row.get(i);
		            WifiDataSetting wd = new WifiDataSetting();
		            wd.setX_SWIFI_MGR_NO((String) weather.get("X_SWIFI_MGR_NO"));        		// 관리번호
		            wd.setX_SWIFI_WRDOFC  ((String) weather.get("X_SWIFI_WRDOFC"));        		// 자치구
		            wd.setX_SWIFI_MAIN_NM ((String) weather.get("X_SWIFI_MAIN_NM"));      		// 와이파이명
		            wd.setX_SWIFI_ADRES1  ((String) weather.get("X_SWIFI_ADRES1"));        		// 도로명 주소
		            wd.setX_SWIFI_ADRES2  ((String) weather.get("X_SWIFI_ADRES2"));				// 상세주소
		            wd.setX_SWIFI_INSTL_FLOOR ( (String) weather.get("X_SWIFI_INSTL_FLOOR"));	// 상세주소 
		            wd.setX_SWIFI_INSTL_TY  ( (String) weather.get("X_SWIFI_INSTL_TY"));		// 설치유형
		            wd.setX_SWIFI_INSTL_MBY  ( (String) weather.get("X_SWIFI_INSTL_MBY"));  	// 설치기관 
		            wd.setX_SWIFI_SVC_SE  ( (String) weather.get("X_SWIFI_SVC_SE"));        	// 서비스구분
		            wd.setX_SWIFI_CMCWR ( (String) weather.get("X_SWIFI_CMCWR"));        		// 망 종류
		            wd.setX_SWIFI_CNSTC_YEAR ( (String) weather.get("X_SWIFI_CNSTC_YEAR"));     // 설치년도
		            wd.setX_SWIFI_INOUT_DOOR ( (String) weather.get("X_SWIFI_INOUT_DOOR"));     // 실내구분
		            wd.setX_SWIFI_REMARS3 ( (String) weather.get("X_SWIFI_REMARS3"));           // 접속 환경
		            wd.setLAT ( (String) weather.get("LAT"));            						// X 좌표
		            wd.setLNT ( (String) weather.get("LNT"));            						// Y 좌표
		            wd.setWORK_DTTM ( (String) weather.get("WORK_DTTM"));            			// 작성일  
		            wfDb.dbInsert(wd);          
		            cts++;
		        }
				br.close();
				urlconn.disconnect();

			}catch(Exception e) {
			System.out.print(e.getMessage());
			break loop1;
			}
			System.out.println(cts);
			ctslow = ctshight+1;
			ctshight = ctslow + 200;
					
		}
		return cts;

	}
}
