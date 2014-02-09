package org.hy.dev.zk;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZkExample implements Watcher{

	private static final Logger logger = LoggerFactory.getLogger(ZkExample.class);
	
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		String connectString = "localhost:2181";
		String root = "/test";
		ZooKeeper zk = new ZooKeeper(connectString, 3000, new ZkExample());
		Stat st = zk.exists(root, true);
		logger.info("st:{}",st);
		if(st == null){
			zk.create(root, "edwin".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		
	}

	@Override
	public void process(WatchedEvent event) {
		logger.info("event:{}",event);
	}

}
