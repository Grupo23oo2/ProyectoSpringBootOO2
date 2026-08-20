Pasos a seguir para levantar el proyecto

Tener instalada:

Java 17 o superior. Maven 3 o superior. MySQL como Base de Datos. Crear una base de datos en MySQL con la siguiente instrucción:

CREAR UNA BASE DE DATOS servicios; Abrir el proyecto en tu IDE y verificar que se descarguen las dependencias. En caso de que no se descarguen automáticamente, abrir una terminal en la raíz del proyecto y ejecutar: mvn clean install Configurar las variables de entorno para que el archivo application.properties las reconozca antes de iniciar la aplicación:

Se utiliza por defecto el puerto 8080, en el archivo application.yml, puede seleccionar otro si lo requiere.

DB_URL → Colocar la URL de la base de datos (por ejemplo: jdbc:mysql://localhost:3306/servicios).

DB_USERNAME → Tu usuario de la base de datos.

DB_PASSWORD → Tu contraseña de la base de datos.

Se utilizaron las siguientes variables de entorno para el envío y la corroboración con el email:

EMAIL_USERNAME 

EMAIL_PASSWORD 



Ya se puede ejecutar el proyecto.

Ahora pueden ir al navegador con la siguiente url: localhost:8080/login y crear su primer usuario 

Dentro de la url: http://localhost:8080/swagger-ui/index.html Tambien pueden ejecutar los métodos
