@echo off

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT

REM compile the code into the bin folder (Point to your new package folders)
javac -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\messi\ui\Messi.java ..\src\main\java\messi\task\*.java ..\src\main\java\messi\exception\*.java
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)

REM run the program using the full package name
java -classpath ..\bin messi.ui.Messi < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT