-- Show table schema 
\d+ retail;

-- Show first 10 rows
SELECT * 
FROM retail 
limit 10;

-- Check # of records
SELECT count(*) 
FROM retail;

-- number of clients (e.g. unique client ID)
SELECT COUNT(DISTINCT(customer_id)) 
FROM retail;

-- invoice date range (e.g. max/min dates)
SELECT MIN(invoice_date), MAX(invoice_date) 
FROM retail;

-- number of SKU/merchants (e.g. unique stock code)
SELECT COUNT(DISTINCT(stock_code)) 
FROM retail;

-- Calculate average invoice amount excluding invoices with a 
-- negative amount (e.g. canceled orders have negative amount)
SELECT AVG(invoice_total)
FROM (SELECT invoice_no, SUM(quantity*unit_price) as invoice_total
      FROM retail
      GROUP BY invoice_no
      HAVING SUM(quantity) > 0
) as invoices;

-- Calculate total revenue (e.g. sum of unit_price * quantity)
SELECT SUM(unit_price*quantity) 
FROM retail;

-- Calculate total revenue by YYYYMM 
SELECT yyyymm, revenue
FROM (SELECT 
        (EXTRACT(YEAR FROM invoice_date) * 100) + EXTRACT(MONTH FROM invoice_date) as yyyymm,
        SUM(unit_price*quantity) as revenue
      FROM retail
      GROUP BY yyyymm
      ORDER BY yyyymm
) as monthly;