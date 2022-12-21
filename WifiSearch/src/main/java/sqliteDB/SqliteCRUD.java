package sqliteDB;

import java.sql.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SqliteCRUD {
	static String road = "D:\\\\gitData\\\\Zerobase_Mission1\\\\";  // 값은 반드시 프로젝트 주소에 맞게 변경 후 사용하세요.
	public ArrayList<DbList> select() {
		/**
		 * 1. IP
		 * 2. port
		 * 3. id
		 * 4. password
		 * 5. 인스턴스
		 */
		ArrayList<DbList> list = new ArrayList<>();
		//File file = new File("");
		
		//String url = "jdbc:sqlite:" + file.getAbsolutePath() + "\\wifi.db";
//		String url = "jdbc:sqlite:\\SQLite\\wifi.db";
//		String url = "jdbc:sqlite:" + a + "\\wifi.db";
		String url = "jdbc:sqlite:"+ road +  "WifiSearch\\src\\main\\webapp\\SQLite\\wifi.db";
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
		
		ResultSet rs = null;
		try {
			System.out.println("1");
			connection = DriverManager.getConnection(url);	
			
//			statement = connection.createStatement(); 				 // 잘 안씀
			String sql = "select * " + " from wifi";
//						+ " where 변수 = ?"							// 1변수 타입 정해줄때 씀.
//						+ " where 변수 = ?"							// 2변수 타입 정해줄때 씀.
			preparedStatement = connection.prepareStatement(sql);    // 이거 씀. 
//			preparedStatement.setString((1, 변수)						// 1변수 타입 정해줄때 씀 변수 지정.
//			preparedStatement.setString((2, 변수)						// 2변수 타입 정해줄때 씀 변수 지정.
			rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				DbList dblist = new DbList();
				dblist.setDate(rs.getString("Date"));
				dblist.setX(rs.getString("X"));
				dblist.setY(rs.getString("Y"));
				list.add(dblist);
			}
					
							
//			PreparedStatement preparedStatement = null;
//			CallableStatement callableStatement = null;
			
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
		System.out.println("2");
		return list;
	}


    public void dbInsert(String x , String y) {

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
        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        //////////////////////////////////////////////////////////////////////////////////////

		try {
			connection = DriverManager.getConnection(url);	
		
            String sql = "insert into wifi (Date, X, Y) "
                    + " values (?, ?, ?);";
            preparedStatement = connection.prepareStatement(sql);    // 이거 씀. 

            preparedStatement.setString(1, date);                        // 1변수(1번째 ?) 타입 정해줄때 씀 변수 지정.
            preparedStatement.setString(2, x);                        // 2변수 타입 정해줄때 씀 변수 지정.
            preparedStatement.setString(3, y);


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

    public void dbUpdate(String x , String y , String x_update, String y_update) {

		 
		File file = new File("");
		String url = "jdbc:sqlite:"+ road +  "WifiSearch\\src\\main\\webapp\\SQLite\\wifi.db";
//		String url = "jdbc:sqlite:D:\\gitData\\Zerobase_Mission1\\WifiSearch\\src\\main\\webapp\\SQLite\\wifi.db";
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
       LocalDateTime now = LocalDateTime.now();
       String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
       //////////////////////////////////////////////////////////////////////////////////////

		
		try {
			connection = DriverManager.getConnection(url);	
		
            String sql = "update wifi "
            		+ "set X = ? , Y = ? , date = ? "   // 바뀔값 
                    + " where X = ? and Y = ? ;";		// 변경할 값 선택
            
            preparedStatement = connection.prepareStatement(sql);    // 이거 씀. 

            preparedStatement.setString(1,x_update);	
            preparedStatement.setString(2,y_update);
            preparedStatement.setString(3, date);
            preparedStatement.setString(4, x);
            preparedStatement.setString(5, y);
            


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
    public void dbDell(String date) {

//		File file = new File("");
		String url = "jdbc:sqlite:"+ road +  "WifiSearch\\src\\main\\webapp\\SQLite\\wifi.db";
//		String url = "jdbc:sqlite:D:\\gitData\\Zerobase_Mission1\\WifiSearch\\src\\main\\webapp\\SQLite\\wifi.db";
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
		
		try {
			connection = DriverManager.getConnection(url);	
	
            String sql = "delete from wifi  "
                    + " where date = ? ;";
            preparedStatement = connection.prepareStatement(sql);    // 이거 씀. 

            preparedStatement.setString(1, date);                        // 1변수(1번째 ?) 타입 정해줄때 씀 변수 지정.
            
           
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
