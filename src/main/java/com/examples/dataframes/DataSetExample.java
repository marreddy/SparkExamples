package com.examples.dataframes;

import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;

public class DataSetExample {

    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder()
                .appName("DataSetExample")
                .master("local[2]")
                .getOrCreate();
        
        

        Dataset<StockDTO> ds = spark.read()
                .option("header", true)
                .schema(Encoders.bean(StockDTO.class).schema())
                .csv("stocks.csv")
                .as(Encoders.bean(StockDTO.class));
        
        ds.printSchema();
        
        //Column name checks will be done at compile time 
        FilterFunction<StockDTO> quantityGreaterThan10Fun = stockDto -> stockDto.getQuantity() > 10;
        ds.filter(quantityGreaterThan10Fun).show();

    }

}
