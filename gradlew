#!/usr/bin/env sh
#############################################################################
# Gradle start up script for UN*X
#############################################################################

set -e

# Determine the directory containing this script
PRG="$0"
while [ -h "$PRG" ] ; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`"/$link"
  fi
done

PRG_DIR=`dirname "$PRG"`

# Default to the directory containing the script
ROOT_DIR=`cd "$PRG_DIR" >/dev/null; pwd`

CLASSPATH="$ROOT_DIR/gradle/wrapper/gradle-wrapper.jar"

# Execute the wrapper
exec java -jar "$CLASSPATH" "$@"
