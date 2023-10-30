FROM alpine:latest
WORKDIR /db
COPY . .
RUN apk update && apk add sqlite
CMD [ "sh", "-c", "cat db_dev.sql | sqlite3 database.db; sqlite3 database.db" ]
EXPOSE 3003