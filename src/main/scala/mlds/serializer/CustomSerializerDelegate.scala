package mlds.serializer

import mlds.{CmdDelegate, Options}

/**
 * Created by vyacheslav.baranov on 09/09/15.
 */
class CustomSerializerDelegate(opts: Options) extends CmdDelegate(opts) {

  override def apply(): Unit = {
    val rdd = sc.parallelize(0 until opts.scale, opts.scale)
      .flatMap(SerialSeq2.createPartition)
    val res = rdd.groupBy(_.id).mapValues(_.flatMap(_.items).map(_.value).sum).collect()
    println(res.size)
  }

}
