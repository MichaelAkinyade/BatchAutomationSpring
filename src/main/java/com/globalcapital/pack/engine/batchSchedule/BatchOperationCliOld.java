package com.globalcapital.pack.engine.batchSchedule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.globalcapital.pack.database.serviceInterface.GenericService;

@Component
public class BatchOperationCliOld {

	@Autowired
	GenericService genericService;

	@Autowired
	Environment env;

	public BatchOperationCliOld() {
		
	}
	

	public boolean checkDBBackgroundActivity() {
		int thresold = 20;
		int dbRuningProcessCount = 0;
		try {
			if (!(genericService.findAllRuningQueries().isEmpty())) {

				dbRuningProcessCount = genericService.findAllRuningQueries().size();
			}

		} catch (Exception e) {
			System.out.println("print stack started here");
			e.printStackTrace();
		}

        return dbRuningProcessCount < thresold;

    }

	public void checkBatchCliVersion(String path) throws IOException {

		BufferedReader r = null;

		if (checkDBBackgroundActivity() == true) {

			// run Cli command
			Runtime rt = Runtime.getRuntime();
			//System.out.println("Starting Batch Cli Proces");
			Process pr = rt.exec(path + " -version");
			r = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String line;
			while (true) {
				line = r.readLine();
				if (line == null) {
					break;
				}
				System.out.println(line);
			}
		}

		r.close();

	}

	public int startBatchCli(String command)
			throws IOException {
		int counter = 0;
		BufferedReader r = null;
		

		while (counter <= 0) {
			if (checkDBBackgroundActivity() == true) {

				// run Cli command
				Runtime rt = Runtime.getRuntime();
				
				System.out.println("Starting Batch Cli Proces");
				Process pr = rt.exec(command);
				r = new BufferedReader(new InputStreamReader(pr.getInputStream()));
				String line;
				while (true) {
					line = r.readLine();
					if (line == null) {
						break;
					}
					System.out.println(line);
				}
				counter++;
				r.close();
				break;
			}
			try {
				Thread.sleep(300000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;

	}

}
