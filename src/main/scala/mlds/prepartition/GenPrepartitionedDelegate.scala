package mlds.prepartition

import mlds.serializer.SerialSeq1
import mlds.{ConfigException, CmdDelegate, Options}
import org.apache.spark.HashPartitioner

/**
 * Created by vyacheslav.baranov on 12/09/15.
 */
class GenPrepartitionedDelegate(opts: Options) extends CmdDelegate(opts) {

  override def apply(): Unit = {
    val path = opts.path.getOrElse(throw new ConfigException("Missing required argument: path"))
    val data = sc.parallelize(0 until opts.scale, opts.scale)
      .flatMap(SerialSeq1.createPartition)

    data.map(s => s.id -> s.items)
      .partitionBy(new HashPartitioner(opts.scale * 10)) //Partitioned data has twice this amount of partitions
      .saveAsObjectFile(path)
  }
}
