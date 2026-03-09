# TuCasaFacil

Proyecto para analizar viviendas de alquiler en Madrid y alrededores a partir de URLs de anuncios.

## Arquitectura

- **Backend**: Spring Boot con JPA, PostgreSQL, Playwright para scraping.
- **Frontend**: Angular standalone con Material UI.
- **BD**: PostgreSQL con Flyway para migraciones.
- **Docker**: Orquestación completa.

## Requisitos

- Docker y Docker Compose
- Node.js 18+ (para desarrollo frontend)
- Java 17+ (para desarrollo backend)

## Instalación y Ejecución

1. Clona el repositorio:
   ```bash
   git clone https://github.com/toninow/tucasafacil.git
   cd tucasafacil
   ```

2. Construye y ejecuta con Docker:
   ```bash
   docker-compose up --build
   ```

3. Accede a la aplicación:
   - Frontend: http://localhost:4200
   - Backend API: http://localhost:8080/api

## Desarrollo

### Backend
- `cd backend`
- `mvn spring-boot:run`

### Frontend
- `cd frontend`
- `npm install`
- `npm start`

### EjecuciÃ³n local rÃ¡pida (Windows)
- Levanta PostgreSQL en `localhost:5432` con:
  - DB: `tucasafacil`
  - Usuario: `tucasafacil`
  - Password: `password`
- Ejecuta desde la raÃ­z:
  - `run-dev.bat`

## Funcionalidades

- Análisis de URLs de anuncios inmobiliarios
- Scoring configurable de viviendas
- Gestión de preferencias de usuario
- Dashboard con estadísticas
- Lista y detalle de propiedades

## Próximas Fases

- Mejorar extractores reales con Playwright
- Integrar APIs de zona (Google Places)
- Análisis de requisitos con IA
- Autenticación de usuarios
- Comparador de propiedades
