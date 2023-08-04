#!/bin/bash

set +e
./gradlew connectedCheck --no-daemon
GRADLE_EXIT_CODE=$?
set -e

if [ $GRADLE_EXIT_CODE -ne 0 ]; then
  echo Pulling screenshots from the emulator...
  adb pull /storage/emulated/0/Pictures/ Screenshots
  echo Done.
fi

exit $GRADLE_EXIT_CODE
