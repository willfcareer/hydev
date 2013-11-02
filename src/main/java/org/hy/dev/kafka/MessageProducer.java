package org.hy.dev.kafka;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MessageProducer {

	private static final Log log = LogFactory.getLog(MessageProducer.class);

	public static void main(String[] args) throws InterruptedException {
		// long events = Long.parseLong(args[0]);
		long events = 1000000;
		Random rnd = new Random();

		Properties props = new Properties();
		props.put("metadata.broker.list", "hyrhel:9092,broker5:9092");
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("partitioner.class", "org.hy.dev.kafka.MessagePartitioner");
		props.put("request.required.acks", "1");

		ProducerConfig config = new ProducerConfig(props);
		
		Producer<String, String> producer = new Producer<String, String>(config);
		
		long start = System.currentTimeMillis();
		for (long nEvents = 0; nEvents < events; nEvents++) {
			long runtime = new Date().getTime();
			String ip = "192.168.2." + rnd.nextInt(2);
			String msg = nEvents + "," + runtime + ",www.example.com," + ip;
			KeyedMessage<String, String> data = new KeyedMessage<String, String>("chatmsg", ip, msg);
			try{
				producer.send(data);				
				log.info("send data successfully:" + nEvents);
			}catch(Exception e){
				e.printStackTrace();
			}
			Thread.sleep(1000);
		}
		producer.close();
		long end = System.currentTimeMillis();
		System.out.println("Time:" + (end - start) + " ms");
	}
}
