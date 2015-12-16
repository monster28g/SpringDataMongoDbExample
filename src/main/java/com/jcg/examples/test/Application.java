package com.jcg.examples.test;


import java.net.UnknownHostException;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;


public class Application
{
	public static void main(String[] args) throws UnknownHostException {
//		String address = "10.14.44.143";
		String address = "10.100.16.51";

		MongoDbSamplingClient client = new MongoDbSamplingClient(address, 27017);
//		client.doDump(DUMP_TYPE.FILE);
		client.getPath(DUMP_TYPE.FILE);
//		client.testDump(1448982060000L, 1448982120000L);

	}
}

