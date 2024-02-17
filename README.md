# AXARA BACKEND

## Descripcion General
AXARA es una aplicación backend desarrollada con Java y el framework Spring Boot. 
Proporciona una API RESTful para gestionar recursos y servicios relacionados con la funcionalidad principal de la aplicación.

---
## Configuracion
1. Clona el repositorio en tu máquina local.
2. Configura las propiedades de la base de datos en application.properties o application.yml.
3. Ejecuta mvn clean install para construir el proyecto.
4. Una vez construida la aplicacion, ejecutela.

## Configuración de la Base de Datos
- URL de la Base de Datos: jdbc:postgresql://<host>:<puerto>/<nombre_base_de_datos>
- Nombre de usuario: <nombre_usuario>
- Contraseña: <contraseña_segura>
- 
## Configuración de Hibernate
- Estrategia de creación de esquemas: none o validate
- Mostrar SQL: false
- Dialecto de Hibernate: org.hibernate.dialect.PostgreSQLDialect
- Formato de SQL: false