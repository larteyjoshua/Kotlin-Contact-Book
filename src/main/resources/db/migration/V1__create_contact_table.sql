create TABLE Contacts (
                       id serial primary key,
                       name varchar,
                       email varchar(100) unique,
                       telephone varchar(100),
                       gpslocation varchar,
                       town varchar

)