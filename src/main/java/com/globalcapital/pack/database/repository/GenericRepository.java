package com.globalcapital.pack.database.repository;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.globalcapital.pack.database.entity.Player;

@Repository("genericRepository")
public interface GenericRepository extends CrudRepository<Player, Long> {

	// Customer queries with multiple tables
	@Query(value = "SELECT p.name, p.num, t.id from player p, Team t", nativeQuery = true)
	List<Object[]> methodThatQueriesMultipleTables();

	@Query(value = "SELECT * from POLICY_TYPE", nativeQuery = true)
	List<Object[]> methodThatQueriesMultipleTablesForPolicyType();

	String mainQuery = "with q1 as (\r\n"
			+ "    select sql_id, child_number, sql_text, last_Active_Time, fetches, executions, disk_reads, direct_writes, buffer_Gets, \r\n"
			+ "    application_Wait_time, user_io_Wait_Time, cpu_time, elapsed_time,\r\n"
			+ "    rows_processed, physical_read_Requests, physical_read_bytes, \r\n"
			+ "    ROUND((buffer_gets - disk_reads) / buffer_gets, 2) hit_ratio,  ROUND(disk_reads / executions, 2) reads_per_run,\r\n"
			+ "    ROUND((elapsed_time/1000000)/executions, 2) avg_time_s,   \r\n" + "    sql_fulltext\r\n"
			+ "    from v$sql where last_active_Time is not null \r\n" + "    and parsing_schema_name = ?1"
			+ "    and abs(sysdate - last_active_time) * 10000 <= 20\r\n"
			+ "    and buffer_gets > 0 and executions > 0\r\n" + ")\r\n" + "select * from q1 order by avg_time_s desc";

	String query = "   select sql_id, child_number, sql_text, last_Active_Time, fetches, executions, disk_reads, direct_writes, buffer_Gets, \r\n"
			+ "    application_Wait_time, user_io_Wait_Time, cpu_time, elapsed_time,\r\n"
			+ "    rows_processed, physical_read_Requests, physical_read_bytes, \r\n"
			+ "    ROUND((buffer_gets - disk_reads) / buffer_gets, 2) hit_ratio,  ROUND(disk_reads / executions, 2) reads_per_run,\r\n"
			+ "    ROUND((elapsed_time/1000000)/executions, 2) avg_time_s,   \r\n" + "    sql_fulltext\r\n"
			+ "    from v$sql where last_active_Time is not null \r\n" + "    and parsing_schema_name = ''\r\n"
			+ "    and abs(sysdate - last_active_time) * 10000 <= 20\r\n"
			+ "    and buffer_gets > 0 and executions > 0";

	
	@Query(value = mainQuery, nativeQuery = true)
	List<Object[]> findRunningQueries(String schemaName);

}
