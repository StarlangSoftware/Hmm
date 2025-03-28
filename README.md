Hidden Markov Models
============

Video Lectures
============

[<img src=video1.jpg width="50%">](https://youtu.be/zHj5mK3jcyk)[<img src=video2.jpg width="50%">](https://youtu.be/LM0ld3UKCEs)

Class Diagram
============

<img src="classDiagram.png">

For Developers
============

You can also see [Python](https://github.com/starlangsoftware/Hmm-Py), [Cython](https://github.com/starlangsoftware/Hmm-Cy), [C++](https://github.com/starlangsoftware/Hmm-CPP), [C](https://github.com/starlangsoftware/Hmm-C), [Swift](https://github.com/starlangsoftware/Hmm-Swift), [Js](https://github.com/starlangsoftware/Hmm-Js), [Php](https://github.com/starlangsoftware/Hmm-Php), or [C#](https://github.com/starlangsoftware/Hmm-CS) repository.

## Requirements

* [Java Development Kit 8 or higher](#java), Open JDK or Oracle JDK
* [Maven](#maven)
* [Git](#git)

### Java 

To check if you have a compatible version of Java installed, use the following command:

    java -version
    
If you don't have a compatible version, you can download either [Oracle JDK](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) or [OpenJDK](https://openjdk.java.net/install/)    

### Maven
To check if you have Maven installed, use the following command:

    mvn --version
    
To install Maven, you can follow the instructions [here](https://maven.apache.org/install.html).      

### Git

Install the [latest version of Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git).

## Download Code

In order to work on code, create a fork from GitHub page. 
Use Git for cloning the code to your local or below line for Ubuntu:

	git clone <your-fork-git-link>

A directory called Hmm will be created. Or you can use below link for exploring the code:

	git clone https://github.com/olcaytaner/Hmm.git

## Open project with IntelliJ IDEA

Steps for opening the cloned project:

* Start IDE
* Select **File | Open** from main menu
* Choose `Hmm/pom.xml` file
* Select open as project option
* Couple of seconds, dependencies with Maven will be downloaded. 


## Compile

**From IDE**

After being done with the downloading and Maven indexing, select **Build Project** option from **Build** menu. After compilation process, user can run Hmm.

**From Console**

Go to `Hmm` directory and compile with 

     mvn compile 

## Generating jar files

**From IDE**

Use `package` of 'Lifecycle' from maven window on the right and from `Hmm` root module.

**From Console**

Use below line to generate jar file:

     mvn install

## Maven Usage

        <dependency>
            <groupId>io.github.starlangsoftware</groupId>
            <artifactId>Hmm</artifactId>
            <version>1.0.3</version>
        </dependency>

Detailed Description
============

+ [Hmm](#hmm)

## Hmm

Hmm modelini üretmek için

	Hmm(Set<State> states, ArrayList<State>[] observations, ArrayList<Symbol>[] emittedSymbols)


Viterbi algoritması ile en olası State listesini elde etmek için

	ArrayList<State> viterbi(ArrayList<Symbol> s)
