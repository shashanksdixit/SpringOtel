# SpringOtel
Spring and OpenTelemetry example

# Spring boot with Prometheus
### Adding custom metrics

## Run


> docker compose up

### Check otel-collector logs for custom metrics
> docker logs -f otel-collector &> log.log & tail -f log.log | grep service1.requests.total

### Check Prometheus
> localhost:9090

## Check metrics
> curl -i -H "Accept: application/json" -X POST http://localhost:4318/v1/metrics
