@echo off
setlocal
set PRG=%~dp0%~n0
set DIR=%~dp0

set CLASSPATH=%DIR%gradle\wrapper\gradle-wrapper.jar

java -jar "%CLASSPATH%" %*

endlocal
