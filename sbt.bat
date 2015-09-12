@ECHO OFF
java -Xmx1024M -XX:MaxPermSize=384M -jar ./contrib/sbt/sbt-launch.jar %*
