package com.globalcapital.pack.database.serviceInterface;

import java.util.List;

public interface GenericService {
	
	List<String> getAllPolicy(Long policyCodeId);

	List<Object[]> findAllRuningQueries();

}