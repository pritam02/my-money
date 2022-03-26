# My Money
My Money is a platform that lets investors track their consolidated portfolio value across equity, debt, and gold.

Portfolio rebalancing is an activity done to reduce the gains from one asset class and investing them in another, to ensure that the desired weight for each asset class doesn't deviate because of market gains/losses.
 

# Pre-requisites
* Java 1.8/1.11/1.15
* Maven
* Git

[git installation guide](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)

[Java installation guide for Linux](https://www.digitalocean.com/community/tutorials/how-to-install-java-with-apt-on-ubuntu-18-04)

[Java installation guide MacOS](https://mkyong.com/java/how-to-install-java-on-mac-osx/)

[Java installation guide Windows](https://phoenixnap.com/kb/install-java-windows)

[Maven installation guide](https://www.baeldung.com/install-maven-on-windows-linux-mac)



# Installation Steps
* browse to the directory where the project should be installed

* clone repository from [github](https://github.com/pritam02/my-money)

```sh
$ git clone https://github.com/pritam02/my-money
```
* go to the project directory

```sh
$ cd my-money
```


Scripts are provided inside the parent folder to execute the code. 

Use `run.sh` if you are Linux/Unix/macOS Operating systems and `run.bat` if you are on Windows.

* For Linux/Unix/macOS
```sh
$ ./run.sh
```

* For Windows
```sh
$ run.bat
```

Internally both the scripts run the following commands 

 * `mvn clean install -DskipTests assembly:single -q` - This will create a jar file `geektrust.jar` in the `target` folder.
 * `java -jar target/geektrust.jar sample_input/input1.txt` - This will execute the jar file passing in the sample input file as the command line argument
 
 * You can also create your own custom text file for providing inputs. 
 * You need to change the path of the input file in `run.sh` script if you want to use your own custom input files.


 # Execute the unit tests

 `mvn clean test` will execute the unit test cases.

```sh
$ mvn clean test
```

* To check the Unit Test Case coverage, we can open the index.html inside `jacoco-reports` directory in any browser of our choice