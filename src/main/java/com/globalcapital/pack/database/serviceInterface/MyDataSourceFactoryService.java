package com.globalcapital.pack.database.serviceInterface;

import javax.sql.DataSource;

public interface MyDataSourceFactoryService {
	
	public DataSource getOracleDataSource();
	
	public int getRunningQuery();

}
