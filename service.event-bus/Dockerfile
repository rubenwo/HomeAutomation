FROM golang:latest

WORKDIR /go/src/imc-web-interface
COPY . .

RUN go get -d -v ./...
RUN CGO_ENABLED=0 GOOS=linux go install ./service.event-bus

FROM alpine:latest
WORKDIR /root/
COPY --from=0 /go/bin/service.event-bus .
COPY --from=0 /go/src/imc-web-interface/service.event-bus/accounts.json .

CMD ["./service.event-bus"]