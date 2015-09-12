package mlds.prepartition

import mlds.serializer.SerialBean
import mlds.{CmdDelegate, ConfigException, Options}
import org.apache.spark.HashPartitioner

/**
 * Created by vyacheslav.baranov on 12/09/15.
 */
class ReadPrepartitionedDelegate(opts: Options) extends CmdDelegate(opts) {

  override def apply(): Unit = {
    val path = opts.path.getOrElse(throw new ConfigException("Missing required argument: path"))
    val src = sc.objectFile[(Int, Iterable[SerialBean])](path)
    require(src.partitions.length == opts.scale * 10) //This controls correct reading of prepartitioned data
    val partitioner = new HashPartitioner(opts.scale * 10)
    val data = new PrepartitionedRDD(src, partitioner)
    val res = data.groupByKey(partitioner).mapValues(_.flatMap(_.map(_.value)).sum).collect()
    println(res.size)
    println(res.find(_._1 == 125))
  }
}
