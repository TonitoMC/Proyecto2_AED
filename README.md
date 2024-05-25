# Proyecto 2: Sistema de Recomendaciones FindMyGame
## Autores: José Antonio Mérida, Adrián López
Este proyecto implementa un sistema de recomendaciones de videojuegos utilizando una base de datos basada en grafos. La base de datos fue creada utilizando el servicio de [AuraDB](https://neo4j.com/cloud/platform/aura-graph-database/) y datos recopilados sobre los juegos de la plataforma [Steam](https://store.steampowered.com/) obtenidos de [Steam Games Dataset](https://www.kaggle.com/datasets/fronkongames/steam-games-dataset).
## Instalación
Este proyecto utiliza [Maven](https://maven.apache.org/) y fue escrito en [Java](https://www.java.com/en/), por lo cual es necesario instalar estas dependencias para poder correr el programa. Para proteger los datos de los usuarios y el acceso a la base de datos, la información de conexión se coordina por medio de variables de entorno, por lo cuál es necesario configurarlas para conectarse a la base de datos de AuraDB con los datos cargados.
### Utilizar Base de Datos (Privada)
Al tener el archivo .env con las variables de entorno, simplemente se coloca dentro del directorio del proyecto para que el programa pueda realizar la conexión.
### Crear Base de Datos
En caso de no tener acceso a la base de datos con la información cargada, se sugiere utilizar AuraDB o correr una base de datos local por medio de Neo4j. Para cargar los datos se puede correr el script "QueryExecuter.java" uziliando el archivo "cypher_queries.txt". Luego se puede crear un archivo .env con los parámetros necesarios para realizar la conexión a la base de datos de Neo4j.
### Requerimientos
Los requerimientos recomendados para correr Java son:
- Sistema Operativo Linux / MacOS (10.14 en adelante) / Windows (8 en adelante)
- 128MB de RAM
- Suficiente almacenamiento para instalar Java y las dependencias (2GB)
- Procesador Intel Pentium 266MHz en adelante

El programa correrá de manera más fluida en hardware más moderno, al no ser muy pesado se puede correr en virtualmente cualquier máquina de los años recientes conforme cumplan los requerimientos de sistema operativo.
## Diseño de la Base de Datos
El programa utiliza una base de datos basada en grafos empleando Neo4j. Tiene como nodos "Game", "User", "Tag" y las relaciones "LIKES" "LIKES_TAG".
### Game

### User

### Tag

## Algoritmo de Recomendación
El algoritmo de recomendación toma en cuenta los juegos que el usuario indica que le gustan, calcula una afinidad hacia ciertos "Tags" basada en su frecuencia relativa y crea una relación ponderada. Para realizar las recomendaciones visita cada uno de los tags a los cuales el usuario es afín, y se le suma la ponderación de la relación al "Puntaje de Recomendación" del juego. Luego de recorrer todos los tags y los juegos correspondientes se le recomiendan los 5 juegos con mayor "Puntaje de Recomendación" al usuario

## Pruebas con Usuarios
Luego de desarrollar el programa se buscó realizar pruebas con usuarios, pidiéndole a cada uno un puntaje de satisfacción. A continuación se encuentran vínculos a los videos de estas pruebas con usuarios:
(Usuario 1)[Link]
(Usuario 2)[Linlk]
(Usuario 3)[Link]
