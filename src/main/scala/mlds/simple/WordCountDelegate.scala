package mlds.simple

import mlds.{CmdDelegate, Options}

/**
 * Created by vyacheslav.baranov on 12/09/15.
 */
class WordCountDelegate(opts: Options) extends CmdDelegate(opts) {

  val txt = """The quick brown fox
              |jumps over the lazy dog""".stripMargin.toLowerCase

  override def apply(): Unit = {

    val rdd = sc.parallelize(txt.split("""\n"""), 2)

    val res = {
      rdd
        .flatMap(_.split("""\s"""))
        .filter(_.length > 0)
        .map(_ -> 1)
        .reduceByKey(_ + _)
        .collect()
    }

    res.foreach { case (word, cnt) => println(s"$word -> $cnt")}
  }
}
