#### Resolution ITS is an Enterprise IT Issue Management System :computer::clipboard:

Effeciently track IT help tickets, resolve end-user issues in record time and improve employee productivity

More funtionality coming soon💻!

Resolution ITS is a program that allows authenticated end-users to submit IT help ticket requests. 
The IT help desk can then triage the ticket and assign a technician to the ticket for resolution. 
The Helpdesk can track the status of a ticket and can track how long an employee has been assigned 
to a specific ticket. Once the technician resolves the ticket, the ticket is completed and stored in
the database for later use for later use. 

This program demonstrates the use of the JDBC and JPA to access, retrieve and maniplate and store values in a
postgresql database. 

To get started, 

Clone the repository:
git clone https://github.com/jordanmlee/Resolution-ITS.git

Ensure PostgreSQL is installed:
(see https://www.postgresql.org/download/)

Ensure Apache Maven is installed:
(see https://maven.apache.org/install.html)

Setup PostgreSQL database
Import database schema and seed data from main folder
psql Resolution-ITS < ResolutionITS_DB.pqsql

Run application
mvn springboot-run 

navigate to localhost:8080

Or run this jar file