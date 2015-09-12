package mlds.simple

import mlds.{CmdDelegate, Options}
import org.apache.spark.Partitioner

/**
 * Created by vyacheslav.baranov on 12/09/15.
 */
class ShuffleDelegate(opts: Options) extends CmdDelegate(opts) {

  override def apply(): Unit = {
    val rdd = sc.parallelize(0 until 1000, 4)

    val partitioner = new Partitioner {
      override def numPartitions: Int = 6
      override def getPartition(key: Any): Int = key.asInstanceOf[Int] % 6
    }

    val res = {
      rdd
        .map(_ -> 1)
        .partitionBy(partitioner)
        .mapPartitionsWithIndex { case (idx, iter) =>
        Iterator(idx -> iter.map(_._2).sum)
      }
        .collect()
    }

    res.sortBy(_._1).foreach { case (idx, cnt) => println(s"$idx -> $cnt")}

  }
}
