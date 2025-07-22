FROM alpine:latest

RUN apk add --no-cache libc6-compat

COPY target/rinha-quarkus-1.0.0-SNAPSHOT-runner /app/api
RUN chmod +x /app/api

ENTRYPOINT ["/app/api"]
