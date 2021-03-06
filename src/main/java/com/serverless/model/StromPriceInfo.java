package com.serverless.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@DynamoDBTable(tableName = "STROMPRICEINFO")
public class StromPriceInfo {

    private Integer id;
    private Date date;
    private double price;
    private Integer startTime;
    private Integer endTime;

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    public Integer getId() {
        return id;
    }

    @DynamoDBAttribute
    public Date getDate() {
        return date;
    }

    @DynamoDBAttribute
    public double getPrice() {
        return price;
    }

    @DynamoDBAttribute
    public Integer getStartTime() {
        return startTime;
    }

    @DynamoDBAttribute
    public Integer getEndTime() {
        return endTime;
    }

    public StromPriceInfo(Date date, double price, Date startTime, Date endTime) {
        this.date = date;
        this.price = price;
        final ZonedDateTime startDateTime = startTime
                .toInstant()
                .atZone(ZoneId.systemDefault());
        //this.startTime = startDateTime.getHour() + ":"+startDateTime.getMinute();
        this.startTime = startDateTime.getHour();
        final ZonedDateTime endDateTime = endTime
                .toInstant()
                .atZone(ZoneId.systemDefault());
        //this.endTime = endDateTime.getHour()+":"+endDateTime.getMinute();
        this.endTime = endDateTime.getHour();
    }
}
