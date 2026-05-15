@echo off
echo === Compiling TechCorp Duel... ===
if not exist out mkdir out

javac -d out -sourcepath src ^
    src\Main.java ^
    src\domain\*.java ^
    src\engine\*.java ^
    src\ui\*.java

if %errorlevel% neq 0 (
    echo.
    echo *** Compile FAILED. Check error messages above. ***
    pause
    exit /b 1
)

echo === Running TechCorp Duel... ===
echo.
java -cp out Main
pause
