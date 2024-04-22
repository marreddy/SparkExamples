package com.examples.dataframes;

import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class DataFrameExample {

    public static void main(String[] args) {

        SparkSession spark = SparkSession.builder()
                .appName("DataFrameExample")
                .master("local[2]")
                .getOrCreate();
        
        Dataset<Row> ds = spark.read()
                .option("header", true)
                .option("inferSchema", true)
                .csv("stocks.csv");
        
        ds.printSchema();
        //Column name checks will be done at runtime 
        FilterFunction<Row> quantityGreaterThan10Fun = row -> (Integer)row.getAs("quantity") > 10;
        ds.filter(quantityGreaterThan10Fun).show();
        
    }
}
