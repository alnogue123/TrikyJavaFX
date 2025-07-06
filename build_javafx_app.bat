@echo off
setlocal

REM --- Configura aquí las variables de tu proyecto ---
set JAVA_HOME=C:\Program Files\Java\jdk-17.0.8
set JAVAFX_JMODS=C:\tools\javafx-jmods-24.0.1
set PROJECT_DIR=%~dp0
set TARGET_DIR=%PROJECT_DIR%target
set RUNTIME_IMAGE=%PROJECT_DIR%triky-runtime
set DIST_DIR=%PROJECT_DIR%dist

set MAIN_JAR=triky-jar-with-dependencies.jar
set MAIN_CLASS=com.example.demo.View.MainView
set APP_NAME=TrikyApp

echo.
echo ===============================
echo Generando runtime image con jlink
echo ===============================
echo.

jlink ^
  --module-path "%JAVA_HOME%\jmods;%JAVAFX_JMODS%" ^
  --add-modules java.base,javafx.controls,javafx.fxml,javafx.graphics ^
  --output "%RUNTIME_IMAGE%"

if ERRORLEVEL 1 (
  echo ERROR: jlink fallo
  pause
  exit /b 1
)

echo.
echo ===============================
echo Generando instalador MSI con jpackage
echo ===============================
echo.

jpackage ^
  --type msi ^
  --name %APP_NAME% ^
  --input "%TARGET_DIR%" ^
  --main-jar %MAIN_JAR% ^
  --main-class %MAIN_CLASS% ^
  --runtime-image "%RUNTIME_IMAGE%" ^
  --java-options "-Dprism.order=sw" ^
  --dest "%DIST_DIR%" ^
  --win-menu ^
  --win-shortcut ^
  --win-dir-chooser ^
  --win-per-user-install

if ERRORLEVEL 1 (
  echo ERROR: jpackage fallo
  pause
  exit /b 1
)

echo.
echo Instalador generado exitosamente en %DIST_DIR%
pause
