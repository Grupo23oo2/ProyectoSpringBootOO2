Grupo 23 Proyecto Spring Boot Nivel 1

Pasos a seguir para levantar el proyecto

1. Tener instalada:
   - Java 17 o superior.
   - Maven 3 o superior.
   - Plugin de Lombok configurado en tu IDE (IntelliJ, Eclipse, etc.).
   - MySQL como Base de Datos.

2. Crear una base de datos en MySQL con la siguiente instrucción:

   ```sql
   CREATE DATABASE servicios;
Abrir el proyecto en tu IDE y verificar que se descarguen las dependencias.
En caso de que no se descarguen automáticamente, abrir una terminal en la raíz del proyecto y ejecutar: mvn clean install
Configurar las variables de entorno para que el archivo application.properties las reconozca antes de iniciar la aplicación:

DB_URL → Colocar la URL de la base de datos (por ejemplo: jdbc:mysql://localhost:3306/servicios).

DB_USERNAME → Tu usuario de la base de datos.

DB_PASSWORD → Tu contraseña de la base de datos.

Ejecutar el proyecto.
Si todo está correcto, debería aparecer un mensaje indicando que la aplicación inició correctamente en el puerto 8082.
