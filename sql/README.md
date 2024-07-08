# Introduction

# SQL Queries

##### Table Setup (DDL)


### Modifying Data 

###### Question 1: The club is adding a new facility - a spa. We need to add it into the facilities table. Use the following values:

facid: 9, Name: 'Spa', membercost: 20, guestcost: 30, initialoutlay: 100000, monthlymaintenance: 800.

```sql
INSERT INTO cd.facilities
    (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) 
    VALUES(9, 'Spa', 20, 30, 100000, 800);
```

###### Question 2: Let's try adding the spa to the facilities table again. This time, though, we want to automatically generate the value for the next facid, rather than specifying it as a constant. Use the following values for everything else:

Name: 'Spa', membercost: 20, guestcost: 30, initialoutlay: 100000, monthlymaintenance: 800.

```sql
INSERT INTO cd.facilities
    (facid, name, membercost, guestcost, initialoutlay, monthlymaintenance)
    select (select max(facid) from cd.facilities)+1, 'Spa', 20, 30, 100000, 800; 
```

###### Question 3:  We made a mistake when entering the data for the second tennis court. The initial outlay was 10000 rather than 8000: you need to alter the data to fix the error.

```sql
UPDATE cd.facilities
    SET initialoutlay = 8000
    WHERE facid = 1;
```

###### Question 4: We want to alter the price of the second tennis court so that it costs 10% more than the first one. Try to do this without using constant values for the prices, so that we can reuse the statement if we want to.

```sql
UPDATE cd.facilities
    SET 
        membercost = (SELECT membercost * 1.1 FROM cd.facilities WHERE facid = 0),
        guestcost = (SELECT guestcost * 1.1 FROM cd.facilities WHERE facid = 0)
    WHERE facid = 1;
```

###### Question 5: As part of a clearout of our database, we want to delete all bookings from the cd.bookings table. How can we accomplish this?

```sql
DELETE FROM cd.bookings;
```

###### Question 6: We want to remove member 37, who has never made a booking, from our database. How can we achieve that?

```sql
DELETE FROM cd.members
    WHERE memid = 37;
```

### Basics

###### Question 7: How can you produce a list of facilities that charge a fee to members, and that fee is less than 1/50th of the monthly maintenance cost? Return the facid, facility name, member cost, and monthly maintenance of the facilities in question.

```sql
SELECT facid, name, membercost, monthlymaintenance
FROM cd.facilities
WHERE membercost != 0 AND membercost < monthlymaintenance*0.02;
```

###### Question 8: How can you produce a list of all facilities with the word 'Tennis' in their name?

```sql
SELECT * FROM cd.facilities
WHERE name LIKE 'Tennis%' OR name like '%Tennis';
```

Note: Solution can be shortened using `name LIKE '%Tennis%'`

###### Question 9: How can you retrieve the details of facilities with ID 1 and 5? Try to do it without using the OR operator.

```sql
SELECT * FROM cd.facilities
WHERE facid in (1,5);
```

###### Question 10: How can you produce a list of members who joined after the start of September 2012? Return the memid, surname, firstname, and joindate of the members in question.

```sql
SELECT memid, surname, firstname, joindate FROM cd.members
WHERE joindate > '2012-09-01 00:00:00';
```

###### Question 11: You, for some reason, want a combined list of all surnames and all facility names. Yes, this is a contrived example :-). Produce that list!

```sql
SELECT surname FROM cd.members
UNION
SELECT name FROM cd.facilities;
```

### Join
###### Question 12: How can you produce a list of the start times for bookings by members named 'David Farrell'?

Not using join
```sql
SELECT starttime FROM cd.bookings
WHERE memid = 
    (SELECT memid FROM cd.members
    WHERE firstname = 'David' AND surname = 'Farrell');
```

Using join
```sql
SELECT starttime 
FROM cd.bookings bks inner join cd.members mems 
    ON mems.memid = bks.memid 
WHERE mems.firstname = 'David' AND mems.surname = 'Farrell';
```

###### Question 13: How can you produce a list of the start times for bookings for tennis courts, for the date '2012-09-21'? Return a list of start time and facility name pairings, ordered by the time.

```sql
SELECT bks.starttime, facs.name
FROM cd.bookings bks inner join cd.facilities facs
    ON bks.facid = facs.facid
WHERE 
    DATE(bks.starttime) = '2012-09-21' AND
    facs.name LIKE 'Tennis Court%'
ORDER BY bks.starttime;
```

alternate solution
```sql
select bks.starttime as start, facs.name as name
	from 
		cd.facilities facs
		inner join cd.bookings bks
			on facs.facid = bks.facid
	where 
		facs.name in ('Tennis Court 2','Tennis Court 1') and
		bks.starttime >= '2012-09-21' and
		bks.starttime < '2012-09-22'
order by bks.starttime;
```


###### Question 14: How can you output a list of all members, including the individual who recommended them (if any)? Ensure that results are ordered by (surname, firstname).

```sql
SELECT mem.firstname as memfname, mem.surname as memsname, rec.firstname as recfname, rec.surname as recsname
FROM cd.members mem
    LEFT OUTER JOIN cd.members rec
    ON rec.memid =  mem.recommendedby
ORDER BY memsname, memfname;
```

###### Question 15: How can you output a list of all members who have recommended another member? Ensure that there are no duplicates in the list, and that results are ordered by (surname, firstname).

```sql
SELECT DISTINCT rec.firstname as firstname, rec.surname as surname
FROM cd.members mem LEFT OUTER JOIN cd.members rec
    ON rec.memid = mem.recommendedby
ORDER BY surname, firstname;
```

###### Question 16: How can you output a list of all members, including the individual who recommended them (if any), without using any joins? Ensure that there are no duplicates in the list, and that each firstname + surname pairing is formatted as a column and ordered.

```sql
SELECT DISTINCT CONCAT(mem.firstname, ' ', mem.surname) as member, 
                (SELECT CONCAT(mem2.firstname, ' ', mem2.surname) as recommender
                FROM cd.members as mem2
                WHERE mem.recommendedby = mem2.memid)     
FROM cd.members as mem
ORDER BY member;
```

### Aggregation

###### Question 17: Produce a count of the number of recommendations each member has made. Order by member ID.

```sql
SELECT recommendedby, COUNT(*)
FROM cd.members
WHERE recommendedby IS NOT NULL
GROUP BY recommendedby
ORDER BY recommendedby;
```

###### Question 18: Produce a list of the total number of slots booked per facility. For now, just produce an output table consisting of facility id and slots, sorted by facility id.

```sql
SELECT facid, SUM(slots) as "Total Slots"
FROM cd.bookings
GROUP BY facid
ORDER BY facid;
```

###### Question 19: Produce a list of the total number of slots booked per facility in the month of September 2012. Produce an output table consisting of facility id and slots, sorted by the number of slots.

```sql
SELECT facid, SUM(slots) as "Total Slots"
FROM cd.bookings
WHERE starttime >= '2012-09-01' AND starttime < '2012-10-01'
GROUP BY facid
ORDER BY "Total Slots";
```

###### Question 20: Produce a list of the total number of slots booked per facility per month in the year of 2012. Produce an output table consisting of facility id and slots, sorted by the id and month.

```sql
SELECT facid, EXTRACT(month FROM starttime) as month, SUM(slots) as "Total Slots"
FROM cd.bookings
WHERE EXTRACT(year FROM starttime) = 2012
GROUP BY facid, month
ORDER BY facid, month;
```

###### Question 21: Find the total number of members (including guests) who have made at least one booking.

```sql
SELECT COUNT(*) from 
	(SELECT DISTINCT memid from cd.bookings) as mem;
```

Alternate solution
```sql
SELECT COUNT(distinct memid)
FROM cd.bookings;
```

###### Question 22: Produce a list of each member name, id, and their first booking after September 1st 2012. Order by member ID.

```sql
SELECT mem.surname, mem.firstname, mem.memid, MIN(bk.starttime)
FROM cd.members mem inner join cd.bookings bk ON mem.memid = bk.memid
WHERE starttime > '2012-09-01'
GROUP BY mem.surname, mem.firstname, mem.memid
ORDER BY mem.memid;
```

###### Question 23: Produce a list of member names, with each row containing the total member count. Order by join date, and include guest members.

Using subquery to bypass grouping by firstname and surname which results in incorrect output
```sql
SELECT 
    (SELECT COUNT(*) FROM cd.members),
    firstname,
    surname
FROM cd.members
ORDER BY joindate;
```

Using window function which operates on the result set of your (sub-)query, after the WHERE clause and all standard aggregation.
```sql
SELECT count(*) over(), firstname, surname
FROM cd.members
ORDER BY joindate;
```

###### Question 24: Produce a monotonically increasing numbered list of members (including guests), ordered by their date of joining. Remember that member IDs are not guaranteed to be sequential.

```sql
SELECT ROW_NUMBER() OVER (ORDER BY joindate), firstname, surname
FROM cd.members;
```

Note: You can also use `COUNT(*) OVER(ORDER BY joindate)`

Q: Is order by joindate necessary?



###### Question 25: Output the facility id that has the highest number of slots booked. Ensure that in the event of a tie, all tieing results get output.

```sql
SELECT facid, total
FROM
	(SELECT facid, 
	 		sum(slots) total, 
	 		count(*) OVER (ORDER BY sum(slots) DESC) rank
	FROM cd.bookings
	GROUP BY facid) as ranked
WHERE rank = 1;
```

### String

###### Question 26: Output the names of all members, formatted as 'Surname, Firstname'

```sql
SELECT CONCAT(surname, ', ', firstname) as name
FROM cd.members;
```

###### Question 27: You've noticed that the club's member table has telephone numbers with very inconsistent formatting. You'd like to find all the telephone numbers that contain parentheses, returning the member ID and telephone number sorted by member ID.

```sql
SELECT memid, telephone
FROM cd.members
WHERE telephone LIKE '(%)%';
```

Note: SIMILAR TO or POSIX very slow and inefficient on large datasets since they rely on regex

###### Question 28: You'd like to produce a count of how many members you have whose surname starts with each letter of the alphabet. Sort by the letter, and don't worry about printing out a letter if the count is 0.

```sql
SELECT SUBSTR(surname, 1, 1) as letter, COUNT(*)
FROM cd.members
GROUP BY letter
ORDER BY letter;
```

Note: Starting pos is 1 not 0 in SQL