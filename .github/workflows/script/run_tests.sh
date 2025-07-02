#!/bin/bash

set +e
./gradlew assembleEspresso assembleEspressoAndroidTest --no-daemon
adb install app/build/outputs/apk/espresso/app-espresso.apk
adb install app/build/outputs/apk/androidTest/espresso/app-espresso-androidTest.apk
adb shell am instrument -w -m -e debug false \\n -e class 'com.mitteloupe.whoami.suite.SmokeTests' \\ncom.mitteloupe.whoami.test/com.mitteloupe.whoami.di.HiltTestRunner
GRADLE_EXIT_CODE=$?
set -e

if [ $GRADLE_EXIT_CODE -ne 0 ]; then
  echo Pulling screenshots from the emulator...
  adb pull /storage/emulated/0/Pictures/ Screenshots
  rm -rf Screenshots/.thumbnails
  echo Done.
fi

exit $GRADLE_EXIT_CODE
