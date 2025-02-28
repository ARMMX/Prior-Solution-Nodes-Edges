#!/bin/bash

rm -rf com/
rm -f *.class


echo "Compiling Java source..."
javac -cp "modules/*" -d . main.java
if [ $? -ne 0 ]; then
    echo "Compilation failed!"
    exit 1
fi



java -cp .:modules/* com.example.main
