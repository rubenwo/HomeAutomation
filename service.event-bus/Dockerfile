FROM golang:latest
RUN mkdir /app
ADD . /app/

RUN go get github.com/gorilla/mux && go get github.com/gorilla/websocket

WORKDIR /app
RUN go build -o server .

EXPOSE 80
CMD ["/app/server"]