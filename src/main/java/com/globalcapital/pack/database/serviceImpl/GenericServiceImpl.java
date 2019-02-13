package com.globalcapital.pack.database.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.globalcapital.pack.database.repository.GenericRepository;
import com.globalcapital.pack.database.serviceInterface.GenericService;

@Service
public class GenericServiceImpl implements GenericService {

	@Autowired
	private GenericRepository genericRepository;
	
	@Autowired
	private Environment env;
	
	public GenericServiceImpl() {
		
	}



	@Override
	public List<String> getAllPolicy(Long policyCodeId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	@Override
	public List<Object[]> findAllRuningQueries() {
		
		//Multiple tables record test
		List<Object[]> multipleTbales = new ArrayList<>();
		
		return multipleTbales = genericRepository.findRunningQueries(env.getProperty("solife.server.schema"));
	}



	}


