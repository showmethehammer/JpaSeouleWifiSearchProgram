package sqliteDB;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class WifiDataDB {
	static String road = "D:\\\\gitData\\\\Zerobase_Mission1\\\\";  // 값은 반드시 프로젝트 주소에 맞게 변경 후 사용하세요.
	public ArrayList<WifiDataSetting> search(String lat, String lnt) {
		/**
		 * 1. IP
		 * 2. port
		 * 3. id
		 * 4. password
		 * 5. 인스턴스
		 */
		String url = "jdbc:sqlite:"+ road +  "WifiSearch\\src\\main\\webapp\\SQLite\\wifi.db";
		/**
		 * 1. 드라이버로드	Class.forName("org.mariadb.jdbc.Driver");
		 * 2. 커넥션 객체 생성	
		 * 3. 스테이트먼트 객체 생성
		 * 4. 쿼리 실행
		 * 5. 결과 수행
		 * 6. 객체연결 해제
		 */
		try {
			Class.forName("org.sqlite.JDBC"); // 드라이버 로드
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection connection = null;
		
//		Statement statement  = null; 				 // 잘 안씀.
		PreparedStatement preparedStatement = null;  // 이거 씀. 
		
		ResultSet rs = null;
		ArrayList<Node> datalist = new ArrayList<>();
		try {
			connection = DriverManager.getConnection(url);	
			
//			statement = connection.createStatement(); 				 // 잘 안씀
			String sql = "select * " + " from Data";
//						+ " where 변수 = ?"							// 1변수 타입 정해줄때 씀.
//						+ " where 변수 = ?"							// 2변수 타입 정해줄때 씀.
			preparedStatement = connection.prepareStatement(sql);    // 이거 씀. 
			rs = preparedStatement.executeQuery();
			int cts = 0;
			double max = 999;
			double callog = 0;
			Pythagoras pt = new Pythagoras();
			CalWifiPoint calWifi = new CalWifiPoint();
			
			while (rs.next()) {
				String number = (rs.getString("X_SWIFI_MGR_NO"));
				String LAT = (rs.getString("LAT"));
				String LNT = (rs.getString("LNT"));
				callog = pt.cal(lat, lnt, LAT, LNT);

				
					calWifi.dataLog(callog, number);
					max = callog;
					cts++;
		
				
			}
			datalist = calWifi.outData();
			cts = 0;
			
		}catch (SQLException e) {
			
		}finally {
			try {
				if(rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				try {
					if(preparedStatement != null && !preparedStatement.isClosed()) {
						preparedStatement.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					if(connection != null && !connection.isClosed()) {
						connection.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		ArrayList<WifiDataSetting> list = new ArrayList<>();
		for(int i = 0 ; i < datalist.size() ; i ++) {
			list.add(abcd(datalist.get(i)));	
		}
		
		return list;
	}
	
	
////////////////////////////////////////////////////////////////////////////////////
	public WifiDataSetting abcd(Node node) {	
		String url = "jdbc:sqlite:"+ road +  "WifiSearch\\src\\main\\webapp\\SQLite\\wifi.db";
		WifiDataSetting wd = new WifiDataSetting();
//X_SWIFI_MGR_NO
		/**
		 * 1. 드라이버로드	Class.forName("org.mariadb.jdbc.Driver");
		 * 2. 커넥션 객체 생성	
		 * 3. 스테이트먼트 객체 생성
		 * 4. 쿼리 실행
		 * 5. 결과 수행
		 * 6. 객체연결 해제
		 */
		try {
			Class.forName("org.sqlite.JDBC"); // 드라이버 로드
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection connection = null;
		
		//		Statement statement  = null; 				 // 잘 안씀.
		PreparedStatement preparedStatement = null;  // 이거 씀. 
		
		ResultSet rs = null;
		try {
			connection = DriverManager.getConnection(url);	
			
			//		statement = connection.createStatement(); 				 // 잘 안씀
			String sql = "select * " + " from Data"
					+ " where X_SWIFI_MGR_NO = ?";							// 1변수 타입 정해줄때 씀.

			preparedStatement = connection.prepareStatement(sql);    // 이거 씀. 
            preparedStatement.setString(1,  node.X_SWIFI_MGR_NO);        // 관리번호 
			rs = preparedStatement.executeQuery();
			
			
			


			while (rs.next()) {
				wd.setkm(node.distance);												// 키로수
				wd.setX_SWIFI_MGR_NO	(rs.getString("X_SWIFI_MGR_NO"));        		// 관리번호
				wd.setX_SWIFI_WRDOFC  	(rs.getString("X_SWIFI_WRDOFC"));        		// 자치구
				wd.setX_SWIFI_MAIN_NM 	(rs.getString("X_SWIFI_MAIN_NM"));      		// 와이파이명
				wd.setX_SWIFI_ADRES1  	(rs.getString("X_SWIFI_ADRES1"));        		// 도로명 주소
				wd.setX_SWIFI_ADRES2  	(rs.getString("X_SWIFI_ADRES2"));				// 상세주소
				wd.setX_SWIFI_INSTL_FLOOR (rs.getString("X_SWIFI_INSTL_FLOOR"));		// 상세주소 
				wd.setX_SWIFI_INSTL_TY    ( rs.getString("X_SWIFI_INSTL_TY"));			// 설치유형
				wd.setX_SWIFI_INSTL_MBY   ( rs.getString("X_SWIFI_INSTL_MBY"));  		// 설치기관 
				wd.setX_SWIFI_SVC_SE      ( rs.getString("X_SWIFI_SVC_SE"));        	// 서비스구분
				wd.setX_SWIFI_CMCWR       ( rs.getString("X_SWIFI_CMCWR"));        		// 망 종류
				wd.setX_SWIFI_CNSTC_YEAR  ( rs.getString("X_SWIFI_CNSTC_YEAR"));    	// 설치년도
				wd.setX_SWIFI_INOUT_DOOR  ( rs.getString("X_SWIFI_INOUT_DOOR"));  	    // 실내구분
				wd.setX_SWIFI_REMARS3     ( rs.getString("X_SWIFI_REMARS3"));           // 접속 환경
				wd.setLAT                 ( rs.getString("LAT"));            			// X 좌표
				wd.setLNT                 ( rs.getString("LNT"));            			// Y 좌표
				wd.setWORK_DTTM           ( rs.getString("WORK_DTTM"));            		// 작성일  
			}
						
			//		PreparedStatement preparedStatement = null;
			//		CallableStatement callableStatement = null;
		
		}catch (SQLException e) {
		
		}finally {
			try {
				if(rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				if(preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
					if(connection != null && !connection.isClosed()) {
						connection.close();
					}	
				} catch (SQLException e) {
					// 	TODO Auto-generated catch block
					e.printStackTrace();
			}
		
		}
		return wd;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	public void dbInsert(WifiDataSetting wd) {

		/**
		 * 1. IP
		 * 2. port
		 * 3. id
		 * 4. password
		 * 5. 인스턴스
		 */
		//File file = new File("");
    	String url = "jdbc:sqlite:"+ road +  "WifiSearch\\src\\main\\webapp\\SQLite\\wifi.db";
//    	String url = "jdbc:sqlite:D:\\gitData\\Zerobase_Mission1\\WifiSearch\\src\\main\\webapp\\SQLite\\wifi.db";
//		String dbUserId = "root";
//		String dbPassword = "zerobase";
		/**
		 * 1. 드라이버로드	Class.forName("org.mariadb.jdbc.Driver");
		 * 2. 커넥션 객체 생성	
		 * 3. 스테이트먼트 객체 생성
		 * 4. 쿼리 실행
		 * 5. 결과 수행
		 * 6. 객체연결 해제
		 */
		try {
			Class.forName("org.sqlite.JDBC"); // 드라이버 로드
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection connection = null;
		
//		Statement statement  = null; 				 // 잘 안씀.
		PreparedStatement preparedStatement = null;  // 이거 씀. 
        // 임시 값 //
        //////////////////////////////////////////////////////////////////////////////////////

		try {
			connection = DriverManager.getConnection(url);	
		
            String sql = "insert into Data ( X_SWIFI_MGR_NO , X_SWIFI_WRDOFC ,  X_SWIFI_MAIN_NM , X_SWIFI_ADRES1 , X_SWIFI_ADRES2 , "
            		+ "X_SWIFI_INSTL_FLOOR , X_SWIFI_INSTL_TY , X_SWIFI_INSTL_MBY , X_SWIFI_SVC_SE , X_SWIFI_CMCWR ,  X_SWIFI_CNSTC_YEAR , "
            		+ "X_SWIFI_INOUT_DOOR , X_SWIFI_REMARS3 , LAT , LNT , WORK_DTTM ) "
                    + " values (?, ?, ? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?);";
            preparedStatement = connection.prepareStatement(sql);    // 이거 씀. 

            preparedStatement.setString(1,  wd.getX_SWIFI_MGR_NO()      );        // 관리번호 
        	preparedStatement.setString(2,  wd.getX_SWIFI_WRDOFC()      );        // 자치구   
        	preparedStatement.setString(3,  wd.getX_SWIFI_MAIN_NM()     );        // 와이파이명
        	preparedStatement.setString(4,  wd.getX_SWIFI_ADRES1()      );        // 도로명 주
        	preparedStatement.setString(5,  wd.getX_SWIFI_ADRES2()      );        // 상세주소 
        	preparedStatement.setString(6,  wd.getX_SWIFI_INSTL_FLOOR() );        // 상세주소 
        	preparedStatement.setString(7,  wd.getX_SWIFI_INSTL_TY()    );        // 설치유형 
        	preparedStatement.setString(8,  wd.getX_SWIFI_INSTL_MBY()   );        // 설치기관 
        	preparedStatement.setString(9,  wd.getX_SWIFI_SVC_SE()      );        // 서비스구분
        	preparedStatement.setString(10, wd.getX_SWIFI_CMCWR()       );        // 망 종류  
        	preparedStatement.setString(11, wd.getX_SWIFI_CNSTC_YEAR()  );        // 설치년도 
        	preparedStatement.setString(12, wd.getX_SWIFI_INOUT_DOOR()  );        // 실내구분 
        	preparedStatement.setString(13, wd.getX_SWIFI_REMARS3()     );        // 접속 환경
        	preparedStatement.setString(14, wd.getLAT()                 );        // X 좌표   
        	preparedStatement.setString(15, wd.getLNT()                 );        // Y 좌표   
        	preparedStatement.setString(16, wd.getWORK_DTTM()           );        // 작성일   


            int affected = preparedStatement.executeUpdate();
            if (affected > 0) {
                System.out.println("저장성공");
            } else {
                System.out.println("실패");
            }


        } catch (SQLException e) {

        } finally {
                try {
                    if (preparedStatement != null && !preparedStatement.isClosed()) {
                        preparedStatement.close();
                    }
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    if (connection != null && !connection.isClosed()) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }

    }

//    
    public void dbDell() {

		String url = "jdbc:sqlite:"+ road +  "WifiSearch\\src\\main\\webapp\\SQLite\\wifi.db";

		/**
		 * 1. 드라이버로드	Class.forName("org.mariadb.jdbc.Driver");
		 * 2. 커넥션 객체 생성	
		 * 3. 스테이트먼트 객체 생성
		 * 4. 쿼리 실행
		 * 5. 결과 수행
		 * 6. 객체연결 해제
		 */
		try {
			Class.forName("org.sqlite.JDBC"); // 드라이버 로드
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection connection = null;
		
//		Statement statement  = null; 				 // 잘 안씀.
		PreparedStatement preparedStatement = null;  // 이거 씀. 
		
		try {
			connection = DriverManager.getConnection(url);	
	
            String sql = "delete from Data  ";
            preparedStatement = connection.prepareStatement(sql);    // 이거 씀. 
           
            int affected = preparedStatement.executeUpdate();
            if (affected > 0) {
                System.out.println("delete 성공");
            } else {
                System.out.println("실패");
            }
        } catch (SQLException e) {

        } finally {
                try {
                    if (preparedStatement != null && !preparedStatement.isClosed()) {
                        preparedStatement.close();
                    }
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    if (connection != null && !connection.isClosed()) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }
}
