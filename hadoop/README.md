# Table of contents
* [Introduction](#Introduction)
* [Hadoop Cluster](#hadoop-cluster)
* [Hive Project](#hive-project)
* [Improvements](#improvements)

# Introduction
This project focuses on migrating the data analytics team to Apache Hadoop for 
distributed big data processing. Leveraging Google Cloud Platform (GCP), 
a Hadoop cluster is provisioned using GCP Dataproc, incorporating core 
components like HDFS (storage), MapReduce (batch processing), and YARN 
(resource management). Apache Hive and HiveQL is utilized alongside Zeppelin 
Notebook to interact with data stored in the Hadoop system.


# Hadoop Cluster
### Workflow
1. A user submits a HiveQL query via Zeppelin.

2. Hive Server checks the Hive Metastore to resolve table schemas and data locations (in HDFS).

3. The query is translated into a MapReduce/Tez/Spark job.

4. YARN schedules and runs the job, reading/writing data to HDFS.

### Diagram
![architecture](resources/architecture.png)

### Hardware Specifications
```
Master node: Standard (1 master, N workers)
Machine type: n2-standard-4
Primary disk type: pd-standard
Primary disk size: 100GB

Worker nodes: 2
Machine type: n2-standard-4
Primary disk type: pd-standard
Primary disk size: 100GB
Secondary worker nodes: 0
```

# Hive Project
![Notebook](resources/zeppelin-notebook.png)

# Improvements
1. Implement auto-scaling (e.g., YARN with Kubernetes) to reduce resource costs.

2. Cache frequently accessed metadata (e.g., with Hive LLAP) to improve query efficiency.

3. Adopt Apache Spark for in-memory processing, which can be faster for ETL/ML, whereas MapReduce can be slow for interactive workloads due to heavy read/write operations.