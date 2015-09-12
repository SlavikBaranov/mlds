#!/bin/bash
FWDIR=`dirname $0`
EXTRA_ARGS=""
java -Xmx1024M -XX:MaxPermSize=384M $EXTRA_ARGS -jar $FWDIR/contrib/sbt/sbt-launch.jar "$@"
