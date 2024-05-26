# Proyecto 2: Sistema de Recomendaciones FindMyGame
## Autores: José Antonio Mérida, Adrián López
Este proyecto implementa un sistema de recomendaciones de videojuegos utilizando una base de datos basada en grafos. La base de datos fue creada utilizando el servicio de [AuraDB](https://neo4j.com/cloud/platform/aura-graph-database/) y datos recopilados sobre los juegos de la plataforma [Steam](https://store.steampowered.com/) obtenidos de [Steam Games Dataset](https://www.kaggle.com/datasets/fronkongames/steam-games-dataset).
## Instalación
Este proyecto utiliza [Maven](https://maven.apache.org/) y fue escrito en [Java](https://www.java.com/en/), por lo cual es necesario instalar estas dependencias para poder correr el programa. Para proteger los datos de los usuarios y el acceso a la base de datos, la información de conexión se coordina por medio de variables de entorno, por lo cuál es necesario configurarlas para conectarse a la base de datos de AuraDB con los datos cargados.
### Utilizar Base de Datos (Privada)
Al tener acceso a las credenciales de la base de datos con la información cargada, únicamente se deben editar las variables dentro de la clase Model.
- String uri = el uri de la base de datos de Neo4j
- String user = el usuario de la base de datos de Neo4j
- String password = la contraseña de la base de datos de Neo4j

Al reemplazar estas variables se logra conectar exitosamente a la base de datos.
### Crear Base de Datos
En caso de no tener acceso a la base de datos con la información cargada, se sugiere utilizar AuraDB o correr una base de datos local por medio de Neo4j. Para cargar los datos se puede correr el script "QueryExecuter.java" uziliando el archivo "cypher_queries.txt". Luego se puede crear un archivo .env con los parámetros necesarios para realizar la conexión a la base de datos de Neo4j.
### Requerimientos
Los requerimientos recomendados para correr Java son:
- Sistema Operativo Linux / MacOS (10.14 en adelante) / Windows (8 en adelante)
- 128MB de RAM
- Suficiente almacenamiento para instalar Java y las dependencias (2GB)
- Procesador Intel Pentium 266MHz en adelante

El programa correrá de manera más fluida en hardware más moderno, al no ser muy pesado se puede correr en virtualmente cualquier máquina de los años recientes conforme cumplan los requerimientos de sistema operativo.
### Video Demostrativo
[Instalación FindMyGame](https://youtu.be/Bh2SjFwDA9w)
## Diseño de la Base de Datos
El programa utiliza una base de datos basada en grafos empleando Neo4j. Tiene como nodos "Game", "User", "Tag" y las relaciones "LIKES" "LIKES_TAG".
### Game
Los juegos se relacionan por medio de "HAS_TAG" hacia los diferentes Tags

![image](https://github.com/TonitoMC/Proyecto2_AED/assets/138615863/f98f96b7-9370-4287-a0c5-a31c38f80386)

Y por medio de "HAS_PRICE" hacia el nodo "Free"

![image](https://github.com/TonitoMC/Proyecto2_AED/assets/138615863/60a3820d-a999-4889-9a4b-f102a51ae2ee)
### User
Las contraseñas de los usuarios se encuentran encriptadas, únicamente realizando una verificación por medio de BCrypt

![image](https://github.com/TonitoMC/Proyecto2_AED/assets/138615863/3ca2eef4-95b4-4da4-9f38-21872a2dbc43)

Se relaciona con los juegos por medio de la relación "LIKES"

![image](https://github.com/TonitoMC/Proyecto2_AED/assets/138615863/92e29987-0f12-47ec-8be6-fbb86659191b)

Y se relaciona con los Tags por medio de "LIKES_TAG", una relación ponderada

![image](https://github.com/TonitoMC/Proyecto2_AED/assets/138615863/9cb6341f-3738-4eee-8c2f-49099dd5b911)
### Tag
Los tags únicamente tienen relaciones dirigidos hacia ellos.
### Privacidad de los Usuarios
La información de los usuarios se ve protegida al utilizar una base de datos privada, en este caso para la calificación del proyecto se proveerá al profesor y los auxiliares con las credenciales para acceder a la base de datos. Estas credenciales pueden almacenarse utilizando variables de entornoe para evitar que cualquier persona tenga acceso a la base de datos. También se encripta la información sensible (como contraseñas) por medio de BCrypt para que no se almacenen contraseñas en texto en la base de datos. Se almacena la contraseña de manera encriptada en la base de datos y únicamente se realiza una verificación dentro del programa.

## Algoritmo de Recomendación
El algoritmo de recomendación toma en cuenta los juegos que el usuario indica que le gustan, calcula una afinidad hacia ciertos "Tags" basada en su frecuencia relativa y crea una relación ponderada. Para realizar las recomendaciones visita cada uno de los tags a los cuales el usuario es afín, y se le suma la ponderación de la relación al "Puntaje de Recomendación" del juego. Luego de recorrer todos los tags y los juegos correspondientes se le recomiendan los 5 juegos con mayor "Puntaje de Recomendación" al usuario

## Pruebas con Usuarios
Luego de desarrollar el programa se buscó realizar pruebas con usuarios por medio de una conexión de TeamViewer, pidiéndole a cada uno un puntaje de satisfacción y recomendaciones. A continuación se encuentran vínculos a los videos de estas pruebas con usuarios:
- [Jorge](https://youtu.be/LJHlnRyUwt0) - Satisfacción 9/10
- [Sue](https://youtu.be/vTTxLcTmW_Y) - Satisfacción 9/10
- [Yahel](https://youtu.be/nsHdOJpDXdA) - Satisfacción 8/10

Llegamos a la conclusión que el sistema puede realizar recomendaciones adecuadas para los usuarios, sin embargo entre las recomendaciones dadas encontramos:
- Ampliar la base de datos
- Incluir juegos fuera de Steam
- Mejorar la interfaz gráfica
