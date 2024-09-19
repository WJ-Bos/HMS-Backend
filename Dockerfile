FROM ubuntu:latest
LABEL authors="wille"

ENTRYPOINT ["top", "-b"]