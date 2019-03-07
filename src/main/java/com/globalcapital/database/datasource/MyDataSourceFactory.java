package com.globalcapital.database.datasource;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import com.globalcapital.utility.PropertyFileUtils;

import oracle.jdbc.pool.OracleDataSource;

public class MyDataSourceFactory {

	static Properties props = new Properties();
	
	public static  DataSource getOracleDataSource() {
		
		FileInputStream fis = null;
		OracleDataSource oracleDS = null;
		try {
			PropertyFileUtils prop1 = new PropertyFileUtils("application.properties");
			//fis = new FileInputStream("application.properties");
			props = prop1.loadProperties();
			oracleDS = new OracleDataSource();
			oracleDS.setURL(props.getProperty("spring.datasource.url"));
			oracleDS.setUser(props.getProperty("spring.datasource.username"));
			oracleDS.setPassword(props.getProperty("spring.datasource.password"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return oracleDS;
	}

	public static int getRunningQuery() {
		// List<Object> result = new ArrayList<>();
		int result = 0;
		DataSource ds = MyDataSourceFactory.getOracleDataSource();
		String query = "with q1 as (select sql_id, child_number, sql_text, last_Active_Time, fetches, executions, disk_reads, direct_writes, buffer_Gets,"
			+"application_Wait_time, user_io_Wait_Time, cpu_time, elapsed_time,rows_processed, physical_read_Requests, physical_read_bytes,ROUND((buffer_gets - disk_reads) / buffer_gets, 2) hit_ratio,  ROUND(disk_reads / executions, 2) reads_per_run,"
				+ "    ROUND((elapsed_time/1000000)/executions, 2) avg_time_s,   \r\n" + "    sql_fulltext\r\n"
				+ "    from v$sql where last_active_Time is not null and parsing_schema_name ="+"'"+props.getProperty("solife.server.schema")+"'"
				+ "    and abs(sysdate - last_active_time) * 10000 <= 20\r\n"
				+ "    and buffer_gets > 0 and executions > 0\r\n" + ")\r\n"
				+ "select count(*) as rowcount from q1 order by avg_time_s desc";

		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery(query);
			//result = rs.getFetchSize();
			while (rs.next()) {
				
				result = rs.getInt("rowcount");
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}