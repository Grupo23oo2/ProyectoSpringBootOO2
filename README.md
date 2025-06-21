Grupo 23 Proyecto Spring Boot Nivel 1

Pasos a seguir para levantar el proyecto

Tener instalada:

Java 17 o superior. Maven 3 o superior. MySQL como Base de Datos. Crear una base de datos en MySQL con la siguiente instrucción:

CREAR UNA BASE DE DATOS servicios; Abrir el proyecto en tu IDE y verificar que se descarguen las dependencias. En caso de que no se descarguen automáticamente, abrir una terminal en la raíz del proyecto y ejecutar: mvn clean install Configurar las variables de entorno para que el archivo application.properties las reconozca antes de iniciar la aplicación:

Se utiliza por defecto el puerto 8080, en el archivo application.yml, puede seleccionar otro si lo requiere.

DB_URL → Colocar la URL de la base de datos (por ejemplo: jdbc:mysql://localhost:3306/servicios).

DB_USERNAME → Tu usuario de la base de datos.

DB_PASSWORD → Tu contraseña de la base de datos.

Es necesario tener las siguientes variables de entorno:

EMAIL_USERNAME = magosh90@gmail.com

EMAIL_PASSWORD = rzby vjkg svls xnen

Se debe crear en la base de datos el administrador :

1_

INSERT INTO persona (idPersona, apellido, dni, nombre) VALUES (1, 'admin', '12345678', 'admin');

2_

INSERT INTO empleado (fechaInicio, idPersona) VALUES ('21-06-2025', '1');

3_

INSERT INTO usuario (idUsuario, contraseniaUsuario, estado, nombreUsuario, email) VALUES (1, 'admin', true, 'admin', 'magosh90@gmail.com');

4_

INSERT INTO rolusuario (idRolUsuario, fechaCreacion, role, id_Usuario) VALUES (1, '2025-06-21', 'ADMIN', 1);

Ya se puede ejecutar el proyecto.

Ahora pueden ir al navegador con la siguiente url: localhost:8080/login

Con las credenciales, se puede iniciar el Login con Usuario : admin y Contraseña : admin
