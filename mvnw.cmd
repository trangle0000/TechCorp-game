@REM ----------------------------------------------------------------------------
@REM Maven Start Up Batch script
@REM ----------------------------------------------------------------------------
@echo off

set MAVEN_PROJECTBASEDIR=%~dp0

IF NOT EXIST "%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar" (
    IF NOT EXIST "%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\MavenWrapperDownloader.java" (
        SET DOWNLOAD_URL=https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar
        powershell -Command "(New-Object System.Net.WebClient).DownloadFile('%DOWNLOAD_URL%', '%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar')"
    )
)

set WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar"
set WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

java -jar %WRAPPER_JAR% %WRAPPER_LAUNCHER% %*
