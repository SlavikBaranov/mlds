#!/usr/bin/env bash

CURRENT_DIR=`pwd`

FWD="`dirname $0`/.."
cd $FWD

MLDS_HOME=`pwd`
cd $CURRENT_DIR

# Use SPARK_SUBMIT if set, otherwise look for spark-submit in PATH
if [ "x$SPARK_SUBMIT" = "x" ]; then
    SPARK_SUBMIT=spark-submit
fi

APP_JAR=$MLDS_HOME/mlds.jar

for jar in ${MLDS_HOME}/lib/*.jar; do
    ADD_JARS=${ADD_JARS}${jar},
done

ADD_JARS=`echo $ADD_JARS | sed 's/,$//'`

SPARK_OPTS="--class mlds.Main --jars $ADD_JARS $SPARK_PROFILE $APP_JAR"

ARGS=$*

if [ -n "$VERBOSE" ]; then
    echo "MLDS_HOME=$MLDS_HOME" >&2
    echo "SPARK_PROFILE=$SPARK_PROFILE" >&2
    echo "SPARK_SUBMIT=$SPARK_SUBMIT" >&2
    echo "ARGS=$ARGS" >&2
fi

exec $SPARK_SUBMIT $SPARK_OPTS $ARGS
