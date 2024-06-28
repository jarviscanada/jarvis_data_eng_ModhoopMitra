#!/bin/bash

# Setup and validate arguments
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

# Check number of args
if [ "$#" -ne 5 ]; then
  echo "Illegal number of parameters"
  exit 1
fi

# Save machine statistics in MB and current machine hostname to variables
vmstat_mb=$(vmstat --unit M)
hostname=$(hostname -f)

# Retrieve cpu and memory data
memory_free=$(echo "$vmstat_mb" | tail -1 | awk -v col="4" '{print $col}' | xargs)
cpu_idle=$(echo "$vmstat_mb" | tail -1 | head -2 | awk '{print $15}' | xargs)
cpu_kernel=$(echo "$vmstat_mb" | tail -1 | head -2 | awk '{print $14}' | xargs)
disk_io=$(echo "$vmstat_mb" | tail -1 | awk -v col="10" '{print $col}' | xargs)
disk_available=$(df -BM / | tail -1 | awk '{print substr($4, 1, length($4)-1)}' | xargs)

# Current time in `2019-11-26 14:40:19` UTC format
timestamp=$(vmstat -t | tail -1 | awk '{print $(NF-1) " " $NF}' | xargs)

# Subquery to find matching id in host_info table
host_id="(SELECT id FROM host_info WHERE hostname='$hostname')";

insert_stmt="INSERT INTO host_usage(timestamp, host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available) VALUES('$timestamp', $host_id, '$memory_free', '$cpu_idle', '$cpu_kernel', '$disk_io', '$disk_available')";

# set up env var for psql cmd
export PGPASSWORD=$psql_password
# Insert data into database
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?