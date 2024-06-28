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
lscpu_out=$(lscpu)
hostname=$(hostname -f)

# Retrieve hardware spec variables
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out"  | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out"  | egrep "GHz" | awk '{print $3, $4, $5, $6, $7}' | xargs)
cpu_mhz=$(echo "$lscpu_out"  | egrep "GHz" | awk '{printf "%.2f", int($NF * 1000)}' | xargs)
l2_cache=$(echo "$lscpu_out"  | egrep "L2 cache:" | awk '{print $3}' | xargs)
total_mem=$(cat /proc/meminfo | egrep "^MemTotal: " | awk '{print $2}')

# Current time in `2019-11-26 14:40:19` UTC format
timestamp=$(vmstat -t | tail -1 | awk '{print $(NF-1) " " $NF}' | xargs)

insert_stmt="INSERT INTO host_info(hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache, "timestamp", total_mem) VALUES('$hostname', '$cpu_number', '$cpu_architecture', '$cpu_model', '$cpu_mhz', '$l2_cache', '$timestamp', '$total_mem')";

# set up env var for psql cmd
export PGPASSWORD=$psql_password
# Insert data into database
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?