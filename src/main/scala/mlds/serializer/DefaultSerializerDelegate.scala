package mlds.serializer

import mlds.{Options, CmdDelegate}

/**
 * Created by vyacheslav.baranov on 09/09/15.
 */
class DefaultSerializerDelegate(opts: Options) extends CmdDelegate(opts) {

  override def apply(): Unit = {
    val rdd = sc.parallelize(0 until opts.scale, opts.scale)
      .flatMap(SerialSeq1.createPartition)
    val res = rdd.groupBy(_.id).mapValues(_.flatMap(_.items).map(_.value).sum).collect()
    println(res.size)
  }

}
