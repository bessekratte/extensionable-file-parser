# Fixed buffer file parser

With this parser u can declare your own buffer size for processing data!

## How to run
* with default buffer size
```bash
mvn spring-boot:run -Dspring-boot.run.arguments=path/to/file,
```
* with custom buffer size, here 10000
```bash
mvn spring-boot:run -Dspring-boot.run.arguments=path/to/file,10000
```
