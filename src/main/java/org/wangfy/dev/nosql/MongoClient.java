package org.wangfy.dev.nosql;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;

public class MongoClient {
	static Mongo m;
	
	static {
		List<ServerAddress> addresslist = new ArrayList<ServerAddress>();
		try {
			addresslist.add(new ServerAddress("192.168.10.100:27017"));
//			addresslist.add(new ServerAddress("127.0.0.1:11240"));
//			addresslist.add(new ServerAddress("127.0.0.1:12240"));
		} catch (UnknownHostException e) {
			System.err.println("address check error.");
			System.exit(-1);
		}
		
		MongoOptions options = new MongoOptions();
		options.autoConnectRetry = true;
		options.connectionsPerHost = 20;
		options.connectTimeout = 6000;
		options.maxAutoConnectRetryTime = 12000;
		options.maxWaitTime = 12000;
		options.socketKeepAlive = true;
		options.socketTimeout = 2000;
		
		try {
			m = new Mongo(addresslist, options);
		} catch (MongoException e) {
			System.err.println("mongo create error.");
			System.exit(-1);
		}
	}

	public static void main(String[] args) {
		DB db = m.getDB("test");
		DBCollection col = db.getCollection("foo");
		DBCursor cursor = col.find();
		while(cursor.hasNext()) {
		 System.out.println(cursor.next());
		}
	}

}
