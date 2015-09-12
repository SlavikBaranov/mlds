package mlds

import scopt.OptionParser

/**
 * Created by vyacheslav.baranov on 09/09/15.
 */
case class ConfigException(msg: String = null, cause: Throwable = null) extends RuntimeException(msg, cause)

case class Options
(
  cmd: Option[Cmd] = None,
  scale: Int = 4,
  path: Option[String] = None
) {

  def withCommand(cmd: Cmd) = copy(cmd = Option(cmd))

  def withScale(scale: Int) = copy(scale = scale)

  def withPath(path: String) = copy(path = Some(path))
}

trait Cmd

//Simple demos
case object WordCountCmd extends Cmd

case object ShuffleCmd extends Cmd

//Serialization demos
case object DefaultSerializerCmd extends Cmd

case object RegisteredSerializerCmd extends Cmd

case object CustomSerializerCmd extends Cmd

//Prepartitioning demos
case object GenPrepartitionedCmd extends Cmd

case object ShufflePrepartitionedCmd extends Cmd

case object ReadPrepartitionedCmd extends Cmd

object Options {
  val parser = new OptionParser[Options]("mlds.sh") {
    head("mlds")
    help("help")
      .text("prints this usage text")
    opt[Int]('s', "scale")
      .action { (v, opts) => opts.withScale(v) }
      .text("Scale (number of partitions in test. Each partition is approximately 100Mb)")
    opt[String]('p', "path")
      .action { (v, opts) => opts.withPath(v) }
      .text("Scale (number of partitions in test. Each partition is approximately 100Mb)")
    cmd("word-count")
      .action { (_, opts) => opts.withCommand(WordCountCmd) }
      .text("WordCount demo")
    cmd("shuffle")
      .action { (_, opts) => opts.withCommand(ShuffleCmd) }
      .text("Shuffle demo")
    cmd("default-serializer")
      .action { (_, opts) => opts.withCommand(DefaultSerializerCmd) }
      .text("Test shuffle size with default serialization")
    cmd("registered-serializer")
      .action { (_, opts) => opts.withCommand(RegisteredSerializerCmd) }
      .text("Test shuffle size with custom serialization")
    cmd("custom-serializer")
      .action { (_, opts) => opts.withCommand(CustomSerializerCmd) }
      .text("Test shuffle size with custom serialization")
    cmd("gen-prepartitioned")
      .action { (_, opts) => opts.withCommand(GenPrepartitionedCmd) }
      .text("Generate prepartitioned dataset")
    cmd("shuffle-prepartitioned")
      .action { (_, opts) => opts.withCommand(ShufflePrepartitionedCmd) }
      .text("Process prepartitioned dataset with a shuffle")
    cmd("read-prepartitioned")
      .action { (_, opts) => opts.withCommand(ReadPrepartitionedCmd) }
      .text("Process prepartitioned dataset without shuffle")
  }
}
