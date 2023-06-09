setlocal
cd /d %~dp0
javac -sourcepath .\src .\src\welp\poc\Main.java
pause
java -classpath .\src welp.poc.Main
pause
