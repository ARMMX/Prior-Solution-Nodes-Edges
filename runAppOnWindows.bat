@echo off
rmdir /s /q com 2>nul
del /q *.class 2>nul
echo Compiling Java source...
javac -cp "modules/*" -d . main.java
if %errorlevel% neq 0 (
    echo Compilation failed!
    exit /b 1
)
java -cp .;modules/* com.example.main
