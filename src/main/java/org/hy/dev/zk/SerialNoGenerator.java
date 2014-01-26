/*
 * XiaoMi PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.hy.dev.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Edwin
 * @since 1.0
 */
public class SerialNoGenerator {

    public static void main(String[] args) throws Exception {
        String host = "localhost:2181";
        ZooKeeper zk = new ZooKeeper(host, 3000, new MyWatcher());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String znode = format.format(new Date());
        String root = "/serialno";
        try{
            Stat s = zk.exists(root, false);
            if(s == null)
                zk.create(root, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
           
            for (int i = 0; i < 3; i++) {
                String path = zk.create(root + "/" + znode, new byte[0], Ids.OPEN_ACL_UNSAFE,
                        CreateMode.PERSISTENT_SEQUENTIAL);
                System.out.println(path);
//                zk.delete(path, -1);
            }
//            zk.creat.create("/serialno/"+znode, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL, null, null);
       
            List<String> children = zk.getChildren(root, false);
           
            for(String child : children){
                System.out.println(child);
            }
        }catch(KeeperException e){
            e.printStackTrace();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    static class MyWatcher implements Watcher{

        @Override
        public void process(WatchedEvent event) {
            System.out.println(event);
        }
        
    }
}
