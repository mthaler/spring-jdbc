CREATE TABLE SINGER (
       ID SERIAL PRIMARY KEY,
       FIRST_NAME VARCHAR(60) NOT NULL,
       LAST_NAME VARCHAR(40) NOT NULL,
       BIRTH_DATE DATE,
       UNIQUE (FIRST_NAME, LAST_NAME)
);

CREATE TABLE ALBUM (
       ID SERIAL PRIMARY KEY,
       SINGER_ID INT NOT NULL REFERENCES SINGER (ID) ON DELETE CASCADE,
       TITLE VARCHAR(100) NOT NULL,
       RELEASE_DATE DATE,
       UNIQUE (SINGER_ID, TITLE)
);