@echo off
setlocal enabledelayedexpansion

set "SCRIPT_DIR=%~dp0"
set "PROJECT_DIR=%SCRIPT_DIR:~0,-1%"
cd /d "%PROJECT_DIR%"
if not exist "%PROJECT_DIR%\.m2\repository" mkdir "%PROJECT_DIR%\.m2\repository"

if not defined JAVA_HOME set "JAVA_HOME=C:\laragon\bin\java\jdk17"
if not defined MAVEN_HOME set "MAVEN_HOME=C:\laragon\bin\maven\maven3"
if not defined SPRING_PROFILES_ACTIVE set "SPRING_PROFILES_ACTIVE=local"

set "JAVA_CMD=%JAVA_HOME%\bin\java.exe"
if not exist "%JAVA_CMD%" (
    for %%i in (java.exe) do set "JAVA_CMD=%%~$PATH:i"
)

if not exist "%JAVA_CMD%" (
    echo No se encontro Java. Configura JAVA_HOME o instala Java 17+.
    pause
    exit /b 1
)

echo JAVA_HOME=%JAVA_HOME%
echo MAVEN_HOME=%MAVEN_HOME%
echo SPRING_PROFILES_ACTIVE=%SPRING_PROFILES_ACTIVE%
echo.
echo Verificando Java...
"%JAVA_CMD%" -version

set "MAVEN_CMD="
set "CLASSWORLDS_JAR="

if exist "%MAVEN_HOME%\bin\mvn.cmd" (
    set "MAVEN_CMD=%MAVEN_HOME%\bin\mvn.cmd"
) else if exist "%MAVEN_HOME%\mvn.cmd" (
    rem Estructura no estandar (Laragon): ejecutar el launcher de Maven directo.
    for %%f in ("%MAVEN_HOME%\boot\plexus-classworlds-*.jar") do set "CLASSWORLDS_JAR=%%~ff"
) else (
    for %%i in (mvn.cmd mvn) do (
        if not defined MAVEN_CMD set "MAVEN_CMD=%%~$PATH:i"
    )
)

if not defined MAVEN_CMD if not defined CLASSWORLDS_JAR (
    echo No se encontro Maven. Configura MAVEN_HOME o instala Maven 3.x.
    pause
    exit /b 1
)

echo.
echo Compilando proyecto...
call :run_maven -Dmaven.repo.local="%PROJECT_DIR%\.m2\repository" clean compile -DskipTests

if !errorlevel! neq 0 (
    echo Error en la compilacion. Code: !errorlevel!
    pause
    exit /b 1
)

echo.
echo Iniciando Spring Boot...
call :run_maven -Dmaven.repo.local="%PROJECT_DIR%\.m2\repository" -Dmaven.test.skip=true -Dspring-boot.run.profiles=%SPRING_PROFILES_ACTIVE% spring-boot:run
exit /b %errorlevel%

:run_maven
if defined MAVEN_CMD (
    call "%MAVEN_CMD%" %*
) else (
    "%JAVA_CMD%" ^
      -classpath "%CLASSWORLDS_JAR%" ^
      "-Dclassworlds.conf=%MAVEN_HOME%\m2.conf" ^
      "-Dmaven.home=%MAVEN_HOME%" ^
      "-Dlibrary.jansi.path=%MAVEN_HOME%\lib\jansi-native" ^
      "-Dmaven.multiModuleProjectDirectory=%PROJECT_DIR%" ^
      org.codehaus.plexus.classworlds.launcher.Launcher %*
)
exit /b %errorlevel%
