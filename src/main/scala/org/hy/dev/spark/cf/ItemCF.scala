package org.hy.dev.spark.cf

/**
  * Created by edwin on 4/17/17.
  */
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

object ItemCF {
  def main(args: Array[String]) {

    //0 构建Spark对象
    val conf = new SparkConf().setAppName("ItemCF")
    conf.setIfMissing("master", "local")
    conf.setIfMissing("spark.master", "local")
    conf.setIfMissing("--num-executors", "3")
    conf.setIfMissing("--driver-memory", "1g")
    conf.setIfMissing("--executor-memory", "1g")
    conf.setIfMissing("--executor-cores", "1")

    val sc = new SparkContext(conf)
    Logger.getRootLogger.setLevel(Level.WARN)

    //1 读取样本数据
    val data_path = "/Users/edwin/Downloads/dev/src/sample_itemcf2.txt"
    val data = sc.textFile(data_path)
    val userdata = data.map(_.split(",")).map(f => (ItemPref(f(0), f(1), f(2).toDouble))).cache()

    //2 建立模型
    val mysimil = new ItemSimilarity()
    val simil_rdd1 = mysimil.Similarity(userdata, "cosine")
    val recommd = new RecommendedItem
    val recommd_rdd1 = recommd.Recommend(simil_rdd1, userdata, 30)

    //3 打印结果
    println(s"物品相似度矩阵: ${simil_rdd1.count()}")
    simil_rdd1.collect().foreach { ItemSimi =>
      println(ItemSimi.itemid1 + ", " + ItemSimi.itemid2 + ", " + ItemSimi.similar)
    }
    println(s"用戶推荐列表: ${recommd_rdd1.count()}")
    recommd_rdd1.collect().foreach { UserRecomm =>
      println(UserRecomm.userid + ", " + UserRecomm.itemid + ", " + UserRecomm.pref)
    }

  }
}
