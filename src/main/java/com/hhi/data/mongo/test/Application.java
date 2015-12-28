package com.hhi.data.mongo.test;


import java.net.UnknownHostException;


public class Application
{
	public static void main(String[] args) throws UnknownHostException {
//		String address = "10.14.44.143";
		String address = "10.100.16.52";

		MongoDbSamplingClient client = new MongoDbSamplingClient(address, 27017);
//		client.doDump(DUMP_TYPE.FILE);
		client.getPath(DUMP_TYPE.FILE, "vdmPaths");
//		client.testDump(1448982060000L, 1448982120000L);

	}
}

