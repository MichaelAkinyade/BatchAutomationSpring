/*
 * package com.globalcapital.pack.database.repository;
 * 
 * import com.globalcapital.pack.database.entity.Team; import
 * org.springframework.data.jpa.repository.Query; import
 * org.springframework.data.repository.CrudRepository; import
 * org.springframework.stereotype.Repository;
 * 
 * @Repository public interface TeamRepository extends CrudRepository<Team,
 * Long> {
 * 
 * Team findByPlayers(long playerId);
 * 
 * @Query(value="select * from Team t where t.id= ?1", nativeQuery = true) Team
 * findOne (long playerId); }
 */