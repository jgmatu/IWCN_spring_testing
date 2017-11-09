# IWCN_Gestion_de_productos Docker con mysql


Aquí vamos a explicar como hemos realizado la práctica de Docker con
el contenedor para una base de datos en un SGBD MYSQL.

# Docker MYSQL

Lo primero fue instalar docker.

$ sudo apt insall docker docker-compose

Ver si hemos instalado correctamente.

$ docker --version
Docker version 1.12.6, build 78d1802

Obtener la base de datos de docker desde la url del ejercicio.

Una vez encontrado clonamos el proyecto github de docker
git clone https://github.com/docker-library/mysql.git

Rooteamos un terminal.

$ sudo -i

Vamos al directorio del respositorio tendrá esta estructura
de directorio.

5.5  5.7  generate-stackbrew-library.sh  README.md
5.6  8.0  LICENSE                        update.sh

Realizamos la imagen docker de la versión mysql que deseemos, nos bajará
todas las dependencias necesarias y el SGDB mysql dentro del contenedor.

Todo lo que instale lo hará dentro de la imagen la máquina host no instalará
nada.

~# docker build -t mysql --label mysql 5.7/
Successfully built aac684dd4cc7


Comprobamos la nueva imagen docker de la base de datos mysql.

~# docker images
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
mysql               latest              aac684dd4cc7        14 seconds ago      408.2 MB

Ahora debemos crear un contenedor a partir de la imagen. Es importante al hacer el run que crea el contenedor
el puerto del docker y el puerto host para conectar de forma correcta la base de datos desde el host con el servidor
web, si no no funcionará cuando queramos conectar el servidor web al mysql en el docker -p 3306:3306. Puerto del mysql y puerto del docker en el host.

docker run --name mysql -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=example -e MYSQL_USER=web -e MYSQL_PASSWORD=123456 -p 3306:3306 -d mysql:latest
e45f70aca32f5b122f3d8dcd705b502e192888f9a52b850ce8b279fa87b9ff7d

Este el ID del contenedor que acabamos de crear.

Mostramos los contenedores docker que tenemos corriendo en este momento.

~# docker ps

CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                    NAMES
e45f70aca32f        mysql:latest        "docker-entrypoint.sh"   16 seconds ago      Up 15 seconds       0.0.0.0:3306->3306/tcp   mysql

Podemos parar la instancia actual del contenedor por su nombre...

~# docker stop mysql
mysql

Comprobamos que ya no está corriendo.
~# docker ps

CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES

Vemos los contenedores parados y arrancados.

~# docker ps -a
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS                      PORTS               NAMES
e45f70aca32f        mysql:latest        "docker-entrypoint.sh"   2 minutes ago       Exited (0) 48 seconds ago                       mysql


Creamos una nueva instancia del contenedor por su nombre.

~# docker start mysql
mysql

~# docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                    NAMES
e45f70aca32f        mysql:latest        "docker-entrypoint.sh"   3 minutes ago       Up 8 seconds        0.0.0.0:3306->3306/tcp   mysql

Ahora vamos a crear un cliente mysql dentro del docker.

Ejecutamos un comando dentro del docker con el parámetro exec. Debe haber una instancia del contenedor para lanzar docker exec.

~# docker exec -it mysql mysql -uroot -p
Enter password:
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 3
Server version: 5.7.20 MySQL Community Server (GPL)

Copyright (c) 2000, 2017, Oracle and/or its affiliates. All rights reserved.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql>

De esta manera podemos depurar la base de datos desde el contenedor docker.

Creamos la base de datos y la tabla productos dentro de la consola.

CREATE DATABASE example;

USE example;

CREATE TABLE product (
    code INT(11) NOT NULL,
    name VARCHAR(30) NOT NULL,
    description VARCHAR(120),
    price FLOAT NOT NULL,
    PRIMARY KEY (code)
);

# Spring Boot MYSQL

Ahora vamos al servidor WEB cambiamos la configuración del servidor para que conecte con
la base de datos de docker.

spring:
    jpa:
        database: MYSQL
        hibernate:
            ddl-auto: validate

    datasource:
        url: jdbc:mysql://localhost/example
        username: web
        password: 123456
        driver-class-name: com.mysql.jdbc.Driver

Si el esquema DDL de la tabla está es válido y la base de datos se llama example. La conexión
y la entidad de tabla product será correcta y arrancará de forma satisfactoria el servidor web.

Ahora podremos añadir, listar, actualizar o borrar productos. La interfaz CrudRepository hará las
sentencias SQL por nosotros.

Si hacemos una select a la base de datos mysql del docker después de haber añadido un par de productos desde
el servidor web veremos algo de este estilo en la tabla de la base de datos.

mysql> select * from product;
Empty set (0.00 sec)

mysql> select * from product;
+------+------------+-------------+-------+
| code | name       | description | price |
+------+------------+-------------+-------+
|    1 | Cocacola   | Refresco    |     1 |
|    2 | Aquarious  | Refresco    |     1 |
|    3 | Cacahuetes | Aperitivo   |   1.3 |
+------+------------+-------------+-------+
3 rows in set (0.00 sec)
