#!/bin/sh
JAVA_CMD=`which java`
$JAVA_CMD -jar ./target/multiclone.jar $*
