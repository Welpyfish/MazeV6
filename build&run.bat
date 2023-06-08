setlocal
cd /d %~dp0
javac.exe -sourcepath .\src welp\poc\Main.java
java.exe -classpath .\src welp.poc.Main