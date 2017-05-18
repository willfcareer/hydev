package org.hy.dev.spark.common

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author edwin
  * @since 10 May 2017
  */
object SparkHelper {

    def initLocalSpark():SparkContext = {
        val conf = new SparkConf().setAppName("localSpark")
//        conf.set("spark.akka.frameSize", "1024") // workers should be able to send bigger messages
//        conf.set("spark.driver.maxResultSize", "6g")
//        conf.set("spark.scheduler.executorTaskBlacklistTime", "300000")
//        conf.set("hadoop.property.hadoop.security.authentication", "kerberos")

        conf.setIfMissing("master", "local")
        conf.setIfMissing("spark.master", "local")
        conf.setIfMissing("--num-executors", "3")
        conf.setIfMissing("--driver-memory", "1g")
        conf.setIfMissing("--executor-memory", "1g")
        conf.setIfMissing("--executor-cores", "1")

        new SparkContext(conf)
    }
}
