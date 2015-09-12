import sbt.Keys._
import sbt._

object MLDSBuild extends Build {

  val scalatestVersion = "2.2.4"
  val sparkVersion = "1.4.1"

  val distDir = file("dist")
  lazy val dist = TaskKey[Unit]("dist")
  val addFiles = SettingKey[Map[File, File]]("add-files", "The additional directories to package in dist.")
  val binFiles = SettingKey[Seq[String]]("bin-files", "Files to make executable in dist.")

  def distTask = dist <<= (Keys.`package` in Compile, dependencyClasspath in Runtime, baseDirectory,
    name, version, addFiles, binFiles) map {
    (jar, libs, baseDirectory, name, version, addFiles, binFiles) => {

      IO.copyFile(jar, distDir / (name + ".jar"), preserveLastModified = true)

      libs.map(_.data).filter(_.isFile).foreach {
        src =>
          val dst = distDir / "lib" / src.getName
          IO.copyFile(src, dst, preserveLastModified = true)
      }
      addFiles.foreach { pair =>
        val src = pair._1
        val dst = distDir / pair._2.toString
        if (src.isDirectory) {
          IO.copyDirectory(src, dst, preserveLastModified = true)
        } else {
          IO.copyFile(src, dst, preserveLastModified = true)
        }
      }
      binFiles.foreach { s =>
        val f = distDir / s
        f.setExecutable(true, false)
      }
    }
  }

  lazy val root = Project(id = "mlds", base = file("."), settings = Seq(
    addFiles := Map(
      file("src/main/bin") -> file("bin")
    ),
    binFiles := Seq(
      "bin/mlds.sh"
    ),
    cleanFiles <+= baseDirectory { base => base / "dist" },
    distTask,
    libraryDependencies ++= Seq(
      "com.github.scopt" %% "scopt" % "3.3.0",
      "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
      "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
      //Test dependencies
      "org.apache.spark" %% "spark-core" % sparkVersion % "test",
      "org.apache.spark" %% "spark-sql" % sparkVersion % "test",
      "org.scalatest" %% "scalatest" % scalatestVersion % "test"
    ),
    mainClass := Some("mlds.Main"),
    publish := {}
  ))

}
