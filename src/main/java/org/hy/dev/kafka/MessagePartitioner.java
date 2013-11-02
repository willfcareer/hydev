package org.hy.dev.kafka;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

public class MessagePartitioner implements Partitioner<String> {

	public MessagePartitioner(VerifiableProperties props) {

	}

	@Override
	public int partition(String key, int num_partitions) {
		int partition = 0;
		int offset = key.lastIndexOf('.');
		if (offset > 0) {
			partition = Integer.parseInt(key.substring(offset + 1)) % num_partitions;
		}
		return partition;
	}

}
