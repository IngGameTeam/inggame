# Container image that runs your code
FROM ubuntu

# Copies your code file from your action repository to the filesystem path `/` of the container
COPY run.sh /run.sh

# Code file to execute when the docker container starts up (`entrypoint.sh`)
ENTRYPOINT ["/run.sh"]