How to launch application:
 - open command line tool,
 - enter directory where the project is present
 - cd to instrument-task directory,
 - execute the following command: mvn spring-boot:run,

How to launch tests:
 - open command line tool,
 - enter directory where the project is present
 - cd to instrument-task directory,
 - execute the following command: mvn test,

Validation:
 - Composition,
 - Check all possible validation failures (does not implement fail fast rule),

Scalability:
 - it should execute huge volume of data because it does not persist file's content in memory. It fetches needed value and calculates it with previous value.

Efficiency:
 - works slow due to sql queries. SQL query is executed every time when calculation within aggregator is processed (in oder to fetch multiplier),
 - it does not read the whole file to memory at once, just a single line.

Workflow:
 - reading line,
 - validating input line,
 - executing calculations in three aggregations (for instrument1, instrument2 and instrument3).

Area for improvements:
 - executing aggregations in parallel,