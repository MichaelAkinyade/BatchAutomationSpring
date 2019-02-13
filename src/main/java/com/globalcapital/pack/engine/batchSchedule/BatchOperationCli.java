package com.globalcapital.pack.engine.batchSchedule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.globalcapital.database.datasource.MyDataSourceFactory;

public class BatchOperationCli {

	public BatchOperationCli() {
	}

	public boolean checkDBBackgroundActivity() {
		MyDataSourceFactory ms = new MyDataSourceFactory();
		int thresold = 20;
		int dbRuningProcessCount = ms.getRunningQuery();

		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%checking background activity, number of running queries are "+dbRuningProcessCount+ "%%%%%%%%%%%%%%%%%");

        return dbRuningProcessCount < thresold;

    }
	

	public int startBatchCli(String command) throws IOException {
		int counter = 0;
		BufferedReader r = null;

		while (counter <= 0) {
			if ((checkDBBackgroundActivity() == true)) {

				// run Cli command
				Runtime rt = Runtime.getRuntime();
				System.out.println("Batch process has started");
				Process pr = rt.exec(command);
				pr.getInputStream();
				//comment out reading log file to console
				  r = new BufferedReader(new InputStreamReader(pr.getInputStream())); String
				  line; while (true) { line = r.readLine(); if (line == null) { break; }
				  System.out.println(line); }
				//checkDBBackgroundActivity()= false;
				counter++;
				r.close();
				break;
			}
			try {
				System.out.println("Another Batch is in Progress, it will retry to run the batch in 300 seconds");
				Thread.sleep(300000);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;

	}

}
