# Build Your Own Programming Language

Working through the book [Build Your Own Programming Language](https://www.packtpub.com/en-us/product/build-your-own-programming-language-9781800204805) by Cliniton L. Jeffery. 

The official code repository for the book can be found [here](https://github.com/PacktPublishing/Build-Your-Own-Programming-Language).

Although it has been a decade since I last wrote any Java code, I'm trying to make it a bit easier
by using Maven to manage the project. 

## Usage

```bash
mvn clean package
mvn exec:java
```

> [!note]
> `jflex` is used to generate the lexer. The `pom.xml` file has a plugin to run jflex before the
> packaging. Since `byacc-j` is not available in the Maven repository (why would it?), I had to
> install it manually. On Ubuntu that's as easy as `sudo apt install byacc-j`.
