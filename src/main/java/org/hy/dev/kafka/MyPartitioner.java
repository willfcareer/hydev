package org.hy.dev.kafka;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

/**
 * @author edwin
 * @since 15 Jun 2014
 */
public class MyPartitioner implements Partitioner {
    public MyPartitioner(VerifiableProperties props) {
    }

    @Override
    public int partition(Object o, int i) {
        System.out.println(o+" - "+i);
        return i;
    }
}
