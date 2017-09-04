## Instructions
Project is configured to run with docked

### Prerequisites
Install docker
Install docker-compose

### What is inside
We have 2 docker containers
- container with mysql
- container with news application

On application start docker with application waiting while mysql starts

For testing of SMTP server GreenMail is used
For started project gmail SFTP is configured

### How to start
- Go to Code folder
- run command `docker-compose build`
- run command `docker-compose up`

When container is started go to http://localhost:8080

### Assumptions
- User do not have local database
- User will not run tests localy. My goal was implement profile to allow run project in container and locally, but this feature is not implemented

### Issues
- h2 in memory DB could not start()

### Feedback
- It requires more time
- Interesting task
- How to validate that archive is accepted?
