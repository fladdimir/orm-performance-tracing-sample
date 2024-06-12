# tracing common ORM performance issues

## quick start

<https://opentelemetry.io/docs/instrumentation/java/>

```sh
# download java-agent for auto-instrumentation:
curl -L -O https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar
```

```sh
# start database and tracing-backend
docker compose up

# activate the tracing-agent
source otel_activate.sh

# run some tests
./gradlew test --rerun-tasks
```

visit http://localhost:16686/search?service=demo&tags=%7B%22appPerformanceTrace%22%3A%22true%22%7D
