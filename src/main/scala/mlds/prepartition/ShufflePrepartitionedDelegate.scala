package mlds.prepartition

import mlds.serializer.{SerialBean, SerialSeq1}
import mlds.{CmdDelegate, ConfigException, Options}
import org.apache.spark.HashPartitioner

/**
 * Created by vyacheslav.baranov on 12/09/15.
 */
class ShufflePrepartitionedDelegate(opts: Options) extends CmdDelegate(opts) {

  override def apply(): Unit = {
    val path = opts.path.getOrElse(throw new ConfigException("Missing required argument: path"))
    val data = sc.objectFile[(Int, Iterable[SerialBean])](path)
    require(data.partitions.length == opts.scale * 10) //This controls correct reading of prepartitioned data
    val res = data.groupByKey(opts.scale * 10).mapValues(_.flatMap(_.map(_.value)).sum).collect()
    println(res.size)
    println(res.find(_._1 == 125))
  }
}
