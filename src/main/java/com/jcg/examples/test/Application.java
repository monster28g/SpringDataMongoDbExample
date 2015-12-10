package com.jcg.examples.test;


import java.net.UnknownHostException;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;


public class Application
{
	public static void main(String[] args) throws UnknownHostException {
		String address = "10.14.44.143";
//        String address = "10.100.16.51";

		MongoDbSamplingClient client = new MongoDbSamplingClient(address, 27017);
//		client.doDump(DUMP_TYPE.FILE);
		client.doDump(DUMP_TYPE.FILE);

	}
//		public static void main(String[] args)
//		{
//				ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new ClassPathResource("spring-config.xml").getPath());
//		}
}
