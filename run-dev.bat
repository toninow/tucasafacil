@echo off
setlocal

set "ROOT_DIR=%~dp0"
set "BACKEND_DIR=%ROOT_DIR%backend"
set "FRONTEND_DIR=%ROOT_DIR%frontend"
set "SPRING_PROFILES_ACTIVE=local"

echo Iniciando TuCasaFacil en modo desarrollo...
echo.
echo Perfil backend: %SPRING_PROFILES_ACTIVE% (H2 embebida, no requiere PostgreSQL)
echo.

start "TuCasaFacil Backend" cmd /k "cd /d ""%BACKEND_DIR%"" && set SPRING_PROFILES_ACTIVE=%SPRING_PROFILES_ACTIVE% && call run-backend.bat"
start "TuCasaFacil Frontend" cmd /k "cd /d ""%FRONTEND_DIR%"" && (if not exist node_modules (npm install)) && npm start"

echo Se abrieron dos consolas: Backend y Frontend.
echo Frontend: http://localhost:4200
echo Backend API: http://localhost:8080/api
