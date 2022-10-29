## Interactive calculator
Example of interaction with the calculator:
```
>>> 42
42
>>> 10 * (5 + 7)
120
>>> let x = 2 * 7
>>> x + 1
15
>>> let x = x / 5
>>> x
2
```
# Running
This project is ran by gradle, which requires JDK 6 or later. To run project on linux-like systems type 
`./gradlew -q run --console=plain` in terminal (it can take a couple of seconds to start, because this command will 
also compile the project).

If something goes wrong or if you are using Windows, then there is a Dockerfile, which can be started to give you
access to the application (installed docker is required for this method). On linux/macOS (and hopefully on Windows) you 
should run the following from the source dir:
```bash 
docker build -t interactive-calculator .
docker run -i interactive-calculator
```