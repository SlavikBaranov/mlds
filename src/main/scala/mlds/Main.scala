package mlds

import java.util.Scanner

import scala.util.{Failure, Success, Try}

/**
 * Created by vyacheslav.baranov on 09/09/15.
 */
object Main extends App {

  val options = Try(Options.parser.parse(args, Options()))

  options match {
    case Success(Some(opts)) if opts.cmd.isDefined =>
      //process
      val delegate = CmdDelegate(opts)
      try {
        val start = System.currentTimeMillis()
        delegate.apply()
        println(s"Finished, took ${(System.currentTimeMillis() - start) / 1000}s")
        println("Hit ENTER to continue")
        System.in.read()
      } catch {
        case ex@ConfigException(msg, cause) =>
          println(msg)
          if (cause != null) {
            ex.printStackTrace()
          }
          println(Options.parser.usage)
      }
    case Success(Some(cfg)) =>
      //process
      println(Options.parser.usage)
    case Success(None) =>
    //Bad config, parser has printed error message
    case Failure(ex) =>
    //--help, parser has printed usage
  }

}
