package mlds

import mlds.prepartition.{ReadPrepartitionedDelegate, ShufflePrepartitionedDelegate, GenPrepartitionedDelegate}
import mlds.serializer.{CustomSerializerDelegate, RegisteredSerializerDelegate, DefaultSerializerDelegate}
import mlds.simple.{ShuffleDelegate, WordCountDelegate}
import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by vyacheslav.baranov on 09/09/15.
 */
abstract class CmdDelegate(opts: Options) {

  lazy val sc: SparkContext = new SparkContext(sparkConf)

  def sparkConf: SparkConf = {
    new SparkConf()
      .setAppName(getClass.getSimpleName)
  }

  def apply(): Unit
}

object CmdDelegate {

  def apply(opts: Options): CmdDelegate = opts.cmd match {
    case Some(WordCountCmd) => new WordCountDelegate(opts)
    case Some(ShuffleCmd) => new ShuffleDelegate(opts)
    case Some(DefaultSerializerCmd) => new DefaultSerializerDelegate(opts)
    case Some(RegisteredSerializerCmd) => new RegisteredSerializerDelegate(opts)
    case Some(CustomSerializerCmd) => new CustomSerializerDelegate(opts)
    case Some(GenPrepartitionedCmd) => new GenPrepartitionedDelegate(opts)
    case Some(ShufflePrepartitionedCmd) => new ShufflePrepartitionedDelegate(opts)
    case Some(ReadPrepartitionedCmd) => new ReadPrepartitionedDelegate(opts)
    case Some(cmd) => throw new ConfigException(s"Delegate for class ${cmd.getClass} not found")
    case None => throw new ConfigException("Command not defined")
  }
}
