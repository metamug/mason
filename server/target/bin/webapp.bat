@REM ----------------------------------------------------------------------------
@REM  Copyright 2001-2006 The Apache Software Foundation.
@REM
@REM  Licensed under the Apache License, Version 2.0 (the "License");
@REM  you may not use this file except in compliance with the License.
@REM  You may obtain a copy of the License at
@REM
@REM       http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM  Unless required by applicable law or agreed to in writing, software
@REM  distributed under the License is distributed on an "AS IS" BASIS,
@REM  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM  See the License for the specific language governing permissions and
@REM  limitations under the License.
@REM ----------------------------------------------------------------------------
@REM
@REM   Copyright (c) 2001-2006 The Apache Software Foundation.  All rights
@REM   reserved.

@echo off

set ERROR_CODE=0

:init
@REM Decide how to startup depending on the version of windows

@REM -- Win98ME
if NOT "%OS%"=="Windows_NT" goto Win9xArg

@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" @setlocal

@REM -- 4NT shell
if "%eval[2+2]" == "4" goto 4NTArgs

@REM -- Regular WinNT shell
set CMD_LINE_ARGS=%*
goto WinNTGetScriptDir

@REM The 4NT Shell from jp software
:4NTArgs
set CMD_LINE_ARGS=%$
goto WinNTGetScriptDir

:Win9xArg
@REM Slurp the command line arguments.  This loop allows for an unlimited number
@REM of arguments (up to the command line limit, anyway).
set CMD_LINE_ARGS=
:Win9xApp
if %1a==a goto Win9xGetScriptDir
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto Win9xApp

:Win9xGetScriptDir
set SAVEDIR=%CD%
%0\
cd %0\..\.. 
set BASEDIR=%CD%
cd %SAVEDIR%
set SAVE_DIR=
goto repoSetup

:WinNTGetScriptDir
set BASEDIR=%~dp0\..

:repoSetup
set REPO=


if "%JAVACMD%"=="" set JAVACMD=java

if "%REPO%"=="" set REPO=%BASEDIR%\repo

set CLASSPATH="%BASEDIR%"\etc;"%REPO%"\org\apache\tomcat\embed\tomcat-embed-core\9.0.31\tomcat-embed-core-9.0.31.jar;"%REPO%"\org\apache\tomcat\tomcat-annotations-api\9.0.31\tomcat-annotations-api-9.0.31.jar;"%REPO%"\org\apache\tomcat\embed\tomcat-embed-jasper\9.0.31\tomcat-embed-jasper-9.0.31.jar;"%REPO%"\org\apache\tomcat\embed\tomcat-embed-el\9.0.31\tomcat-embed-el-9.0.31.jar;"%REPO%"\org\eclipse\jdt\ecj\3.18.0\ecj-3.18.0.jar;"%REPO%"\org\apache\tomcat\tomcat-jasper\9.0.31\tomcat-jasper-9.0.31.jar;"%REPO%"\org\apache\tomcat\tomcat-servlet-api\9.0.31\tomcat-servlet-api-9.0.31.jar;"%REPO%"\org\apache\tomcat\tomcat-juli\9.0.31\tomcat-juli-9.0.31.jar;"%REPO%"\org\apache\tomcat\tomcat-el-api\9.0.31\tomcat-el-api-9.0.31.jar;"%REPO%"\org\apache\tomcat\tomcat-api\9.0.31\tomcat-api-9.0.31.jar;"%REPO%"\org\apache\tomcat\tomcat-util-scan\9.0.31\tomcat-util-scan-9.0.31.jar;"%REPO%"\org\apache\tomcat\tomcat-util\9.0.31\tomcat-util-9.0.31.jar;"%REPO%"\org\apache\tomcat\tomcat-jasper-el\9.0.31\tomcat-jasper-el-9.0.31.jar;"%REPO%"\org\apache\tomcat\tomcat-jsp-api\9.0.31\tomcat-jsp-api-9.0.31.jar;"%REPO%"\javax\servlet\jstl\1.2\jstl-1.2.jar;"%REPO%"\javax\servlet\javax.servlet-api\3.1.0\javax.servlet-api-3.1.0.jar;"%REPO%"\com\metamug\mason\4.2.9\mason-4.2.9.jar;"%REPO%"\com\squareup\okhttp3\okhttp\3.10.0\okhttp-3.10.0.jar;"%REPO%"\com\squareup\okio\okio\1.14.0\okio-1.14.0.jar;"%REPO%"\org\apache\commons\commons-text\1.6\commons-text-1.6.jar;"%REPO%"\org\apache\commons\commons-lang3\3.8.1\commons-lang3-3.8.1.jar;"%REPO%"\javax\javaee-web-api\8.0.1\javaee-web-api-8.0.1.jar;"%REPO%"\com\jayway\jsonpath\json-path\2.4.0\json-path-2.4.0.jar;"%REPO%"\net\minidev\json-smart\2.3\json-smart-2.3.jar;"%REPO%"\net\minidev\accessors-smart\1.2\accessors-smart-1.2.jar;"%REPO%"\org\ow2\asm\asm\5.0.4\asm-5.0.4.jar;"%REPO%"\com\github\wnameless\json\json-flattener\0.7.1\json-flattener-0.7.1.jar;"%REPO%"\com\github\wnameless\json\json-base\1.0.0\json-base-1.0.0.jar;"%REPO%"\org\eclipse\persistence\org.eclipse.persistence.moxy\2.6.4\org.eclipse.persistence.moxy-2.6.4.jar;"%REPO%"\org\eclipse\persistence\org.eclipse.persistence.core\2.6.4\org.eclipse.persistence.core-2.6.4.jar;"%REPO%"\org\eclipse\persistence\org.eclipse.persistence.asm\2.6.4\org.eclipse.persistence.asm-2.6.4.jar;"%REPO%"\javax\validation\validation-api\1.1.0.Final\validation-api-1.1.0.Final.jar;"%REPO%"\org\glassfish\javax.json\1.0.4\javax.json-1.0.4.jar;"%REPO%"\org\codehaus\groovy\groovy-all\2.4.15\groovy-all-2.4.15.jar;"%REPO%"\org\apache\ivy\ivy\2.4.0\ivy-2.4.0.jar;"%REPO%"\org\json\json\20180813\json-20180813.jar;"%REPO%"\com\github\wnameless\json-flattener\0.6.0\json-flattener-0.6.0.jar;"%REPO%"\com\eclipsesource\minimal-json\minimal-json\0.9.5\minimal-json-0.9.5.jar;"%REPO%"\mysql\mysql-connector-java\8.0.17\mysql-connector-java-8.0.17.jar;"%REPO%"\com\zaxxer\HikariCP\3.4.0\HikariCP-3.4.0.jar;"%REPO%"\org\slf4j\slf4j-api\1.7.25\slf4j-api-1.7.25.jar;"%REPO%"\com\metamug\mtg-api\1.5\mtg-api-1.5.jar;"%REPO%"\com\metamug\mason-server\1.0-SNAPSHOT\mason-server-1.0-SNAPSHOT.jar

set ENDORSED_DIR=
if NOT "%ENDORSED_DIR%" == "" set CLASSPATH="%BASEDIR%"\%ENDORSED_DIR%\*;%CLASSPATH%

if NOT "%CLASSPATH_PREFIX%" == "" set CLASSPATH=%CLASSPATH_PREFIX%;%CLASSPATH%

@REM Reaching here means variables are defined and arguments have been captured
:endInit

%JAVACMD% %JAVA_OPTS%  -classpath %CLASSPATH% -Dapp.name="webapp" -Dapp.repo="%REPO%" -Dapp.home="%BASEDIR%" -Dbasedir="%BASEDIR%" main.java.launch.Main %CMD_LINE_ARGS%
if %ERRORLEVEL% NEQ 0 goto error
goto end

:error
if "%OS%"=="Windows_NT" @endlocal
set ERROR_CODE=%ERRORLEVEL%

:end
@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" goto endNT

@REM For old DOS remove the set variables from ENV - we assume they were not set
@REM before we started - at least we don't leave any baggage around
set CMD_LINE_ARGS=
goto postExec

:endNT
@REM If error code is set to 1 then the endlocal was done already in :error.
if %ERROR_CODE% EQU 0 @endlocal


:postExec

if "%FORCE_EXIT_ON_ERROR%" == "on" (
  if %ERROR_CODE% NEQ 0 exit %ERROR_CODE%
)

exit /B %ERROR_CODE%
