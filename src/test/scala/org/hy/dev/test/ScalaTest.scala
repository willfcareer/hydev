package org.hy.dev.test

import org.apache.spark.{SparkConf, SparkContext}
import org.hy.dev.spark.common.SparkHelper
import org.junit.Test

/**
  * @author edwin
  * @since 10 May 2017
  */
object ScalaTest {

    def main(args: Array[String]) {

        val sc = SparkHelper.initLocalSpark()

        val words = Array("one", "two", "two", "three", "three", "three")
        val wordPairsRDD = sc.parallelize(words).map(word => (word, 1))
        val wordCountsWithReduce = wordPairsRDD.reduceByKey(_ + _)
        val wordCountsWithGroup = wordPairsRDD.groupByKey().map(t => (t._1, t._2.sum))

        wordCountsWithGroup.foreach(println)
    }
}
