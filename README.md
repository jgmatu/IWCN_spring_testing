# IWCN_Gestion_de_productos Web Driver

Para que webdriver funcione hay que hacer varias cosas.

En maven descargamos el jar de [selenium-server-standalone](https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-server-standalone/2.53.0)


Una vez descargado lo añadimos como librería externa de nuestro projecto..

En el IDE -> Click derecho en el proyecto -> Properties -> Java Build Path -> Pestaña Libraries -> Add external Jar ->
añadirmos el jar que hemos descargado.

Una vez añadida la librería externa, yo uso firefox, hay que añadir [geckodriver](https://github.com/mozilla/geckodriver/releases)

¡¡¡CUIDADO CON LA ARQUITECTURA DE DESCARGA!!!


Se descarga y se añade al PATH la ruta del fichero descomprimido, cambia según el Sistema Operativo.

En linux:

    $ tar -xvzf  geckodriver-v0.19.1-linux64.tar.gz
    $ mkdir -p ~/bin
    $ mv gecokdriver ~/bin
    $ PATH=$PATH:~/bin

En [Windows](https://medium.com/@01luisrene/como-agregar-variables-de-entorno-s-o-windows-10-e7f38851f11f)

Por último se lanza el MVC con las vistas y se lanza el test, debería abriros un navegador y empezar a navegar
automáticamente con lo que hayais puesto en el test.

      private WebDriver driver;
      @Before
      public void initalize() {
            driver = new FirefoxDriver();
            driver.get("http://localhost:1234/");
      }
      
