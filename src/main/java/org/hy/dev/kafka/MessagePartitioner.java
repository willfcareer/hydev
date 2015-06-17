package org.hy.dev.kafka;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

public class MessagePartitioner implements Partitioner {

	public MessagePartitioner(VerifiableProperties props) {

	}

	@Override
	public int partition(Object key, int num_partitions) {
		int partition = 0;
		int offset = key.toString().lastIndexOf('.');
		if (offset > 0) {
			partition = Integer.parseInt(key.toString().substring(offset + 1)) % num_partitions;
		}
		return partition;
	}

}
