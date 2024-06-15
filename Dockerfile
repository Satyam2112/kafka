FROM ubuntu:latest
LABEL authors="satya"

ENTRYPOINT ["top", "-b"]