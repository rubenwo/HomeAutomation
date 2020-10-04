FROM golang:latest AS builder

WORKDIR /go/src/home-automation
COPY . .

RUN go get -d -v ./...
RUN CGO_ENABLED=0 GOOS=linux go install ./service.event-bus

FROM alpine:latest
WORKDIR /root/
COPY --from=builder /go/bin/service.event-bus .

CMD ["./service.event-bus"]