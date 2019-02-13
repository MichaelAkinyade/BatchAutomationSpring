package com.globalcapital.pack.database.serviceInterface;

import java.util.List;

public interface SoccerService {
	
	List<String> getAllTeamPlayers(long teamId);
	void addBarcelonaPlayer(String name, String position, int number);
	void getSpecificPlayerByID(int id);
	 List<Object[]>getMultipleTbales();
	
}
