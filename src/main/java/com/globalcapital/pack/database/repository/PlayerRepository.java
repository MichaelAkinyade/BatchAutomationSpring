package com.globalcapital.pack.database.repository;

import com.globalcapital.pack.database.entity.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {

	//@Query(value="Select * from  player p where p.id = ?1", nativeQuery =true)
	List<Player> findByTeamId(long teamId);

	// using queries
	@Query(value = "SELECT * from player p where p.id = ?1", nativeQuery = true)
	Player findUserByID(int id);

	// Customer queries with multiple tables
	@Query(value = "SELECT p.name, p.num, t.id from player p, Team t", nativeQuery = true)

	List<Object[]> methodThatQueriesMultipleTables();

}
