-- Modifying Data

-- Question 1

INSERT INTO cd.facilities (
    facid, name, membercost, guestcost,
    initialoutlay, monthlymaintenance
)
VALUES
    (9, 'Spa', 20, 30, 100000, 800);

-- Question 2

INSERT INTO cd.facilities (
    facid, name, membercost, guestcost,
    initialoutlay, monthlymaintenance
)
SELECT
    (
        SELECT
            max(facid)
        FROM
            cd.facilities
    )+ 1,
    'Spa',
    20,
    30,
    100000,
    800;

-- Question 3

UPDATE
    cd.facilities
SET
    initialoutlay = 8000
WHERE
    facid = 1;

-- Question 4

UPDATE
    cd.facilities
SET
    membercost = (
        SELECT
            membercost * 1.1
        FROM
            cd.facilities
        WHERE
            facid = 0
    ),
    guestcost = (
        SELECT
            guestcost * 1.1
        FROM
            cd.facilities
        WHERE
            facid = 0
    )
WHERE
    facid = 1;

-- Question 5

DELETE FROM
    cd.bookings;

-- Question 6

DELETE FROM
    cd.members
WHERE
    memid = 37;

-- Basics
-- Question 7

SELECT
    facid,
    name,
    membercost,
    monthlymaintenance
FROM
    cd.facilities
WHERE
    membercost != 0
  AND membercost < monthlymaintenance * 0.02;

-- Question 8

SELECT
    *
FROM
    cd.facilities
WHERE
    name LIKE 'Tennis%'
   OR name like '%Tennis';

-- Question 9

SELECT
    *
FROM
    cd.facilities
WHERE
    facid in (1, 5);

-- Question 10

SELECT
    memid,
    surname,
    firstname,
    joindate
FROM
    cd.members
WHERE
    joindate > '2012-09-01 00:00:00';

-- Question 11

SELECT
    surname
FROM
    cd.members
UNION
SELECT
    name
FROM
    cd.facilities;

-- Join
-- Question 12

SELECT
    starttime
FROM
    cd.bookings
WHERE
    memid = (
        SELECT
            memid
        FROM
            cd.members
        WHERE
            firstname = 'David'
          AND surname = 'Farrell'
    );

-- Question 13

SELECT
    bks.starttime,
    facs.name
FROM
    cd.bookings bks
        inner join cd.facilities facs ON bks.facid = facs.facid
WHERE
    DATE(bks.starttime) = '2012-09-21'
  AND facs.name LIKE 'Tennis Court%'
ORDER BY
    bks.starttime;

-- Question 14

SELECT
    mem.firstname as memfname,
    mem.surname as memsname,
    rec.firstname as recfname,
    rec.surname as recsname
FROM
    cd.members mem
        LEFT OUTER JOIN cd.members rec ON rec.memid = mem.recommendedby
ORDER BY
    memsname,
    memfname;

-- Question 15

SELECT
    DISTINCT rec.firstname as firstname,
             rec.surname as surname
FROM
    cd.members mem
        LEFT OUTER JOIN cd.members rec ON rec.memid = mem.recommendedby
ORDER BY
    surname,
    firstname;

-- Question 16

SELECT
    DISTINCT CONCAT(mem.firstname, ' ', mem.surname) as member,
             (
                 SELECT
                     CONCAT(
                             mem2.firstname, ' ', mem2.surname
                     ) as recommender
                 FROM
                     cd.members as mem2
                 WHERE
                     mem.recommendedby = mem2.memid
             )
FROM
    cd.members as mem
ORDER BY
    member;

-- Aggregation
-- Question 17

SELECT
    recommendedby,
    COUNT(*)
FROM
    cd.members
WHERE
    recommendedby IS NOT NULL
GROUP BY
    recommendedby
ORDER BY
    recommendedby;

-- Question 18

SELECT
    facid,
    SUM(slots) as "Total Slots"
FROM
    cd.bookings
GROUP BY
    facid
ORDER BY
    facid;

-- Question 19

SELECT
    facid,
    SUM(slots) as "Total Slots"
FROM
    cd.bookings
WHERE
    starttime >= '2012-09-01'
  AND starttime < '2012-10-01'
GROUP BY
    facid
ORDER BY
    "Total Slots";

-- Question 20

SELECT
    facid,
    EXTRACT(
            month
            FROM
            starttime
    ) as month,
    SUM(slots) as "Total Slots"
FROM
    cd.bookings
WHERE
    EXTRACT(
            year
            FROM
            starttime
    ) = 2012
GROUP BY
    facid,
    month
ORDER BY
    facid,
    month;

-- Question 21

SELECT
    COUNT(distinct memid)
FROM
    cd.bookings;

-- Question 22

SELECT
    mem.surname,
    mem.firstname,
    mem.memid,
    MIN(bk.starttime)
FROM
    cd.members mem
        inner join cd.bookings bk ON mem.memid = bk.memid
WHERE
    starttime > '2012-09-01'
GROUP BY
    mem.surname,
    mem.firstname,
    mem.memid
ORDER BY
    mem.memid;

-- Question 23

SELECT
            count(*) over(),
            firstname,
            surname
FROM
    cd.members
ORDER BY
    joindate;

-- Question 24

SELECT
            ROW_NUMBER() OVER (
        ORDER BY
            joindate
        ),
            firstname,
            surname
FROM
    cd.members;

-- Question 25

SELECT
    facid,
    total
FROM
    (
        SELECT
            facid,
            sum(slots) total,
            count(*) OVER (
                ORDER BY
                    sum(slots) DESC
                ) rank
        FROM
            cd.bookings
        GROUP BY
            facid
    ) as ranked
WHERE
    rank = 1;

-- Question 26

SELECT
    CONCAT(surname, ', ', firstname) as name
FROM
    cd.members;

-- Question 27

SELECT
    memid,
    telephone
FROM
    cd.members
WHERE
    telephone LIKE '(%)%';

-- Question 28

SELECT
    SUBSTR(surname, 1, 1) as letter,
    COUNT(*)
FROM
    cd.members
GROUP BY
    letter
ORDER BY
    letter;
