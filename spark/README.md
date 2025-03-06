# Introduction
A previously implemented data analytics solution using Python and Jupyter notebook was sufficient for a small marketing team, however with the clients desire for expansion, this project was introduced evaluate environment options for data analytics handling larger datasets with distributed systems. This project leverages Apache Spark (PySpark) for distributed data processing, using both Zeppelin (on Hadoop) and Databricks (on Azure) for environment evaluation. It processes customer transaction data and retention statistics, utilizing structured APIs for efficient querying. Data is stored using Databricks Catalog and HDFS for Zeppelin, with Hive Metastore managing schema and table structures.

# Zeppelin and Hadoop Implementation
![Zeppelin Notebook](notebook/Spark_Notebook_WDI.zpln)

The dataset used in the Zeppelin and Hadoop implementation is the World Development Indicators (WDI) dataset, which contains global economic, social, and environmental data. The analytics work involved transforming existing HiveQL queries into PySpark to efficiently process large-scale data and extract insights. By leveraging PySpark's distributed processing capabilities and in-memory processing, the analysis was significantly optimized for scalability and performance.

Workflow:
1. A user submits a PySpark API operation via Zeppelin.
2. Hive Server checks the Hive Metastore to resolve table schemas and data locations (in HDFS).
3. The operation is translated into a Spark job.
4. YARN schedules and runs the job, reading/writing data to HDFS.


# Databricks and Hadoop Implementation
![Databricks Notebook](notebook/retail_data_wrangling_spark.ipynb)

The dataset consists of customer transactional data, including information on purchases, dates, product categories, and transaction amounts. Initially, the data was processed using Pandas and NumPy for analytics, but as the dataset grew larger, performance issues arose. To address this, the analysis pipeline was transformed to use PySpark, leveraging distributed processing for better performance with large datasets. The PySpark transformation enabled faster computations during transaction aggregation, average spend calculations, and RFM table creation, while maintaining scalability for even larger datasets in the future.

Workflow
1. A user submits a PySpark API operation via Databricks notebook.
2. Databricks retrieves table schemas and data locations from the Databricks Catalog.
3. The operation is translated into a Spark job.
4. Spark's Standalone Cluster Manager assigns and runs the job, reading/writing data to DBFS.

# Future Improvement
1. Real-Time Data Processing: Implement Apache Kafka for streaming data ingestion and Spark Streaming for real-time analytics.
2. Advanced Machine Learning Integration: Use MLlib in Spark to develop predictive models for customer segmentation and personalized marketing.
3. Data Governance & Security: Implement role-based access controls (RBAC) and encryption for data protection.