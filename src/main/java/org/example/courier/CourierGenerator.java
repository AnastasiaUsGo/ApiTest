package org.example.courier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {

    public Courier generic(){
        return new Courier("alan123", "1234alan", "Alan");
    }

    public Courier random(){
        return new Courier(RandomStringUtils.randomAlphanumeric(10),"1234alan", "Alan");
    }

    public Courier loginCredentials(){
        return new Courier("alan123","1234alan");
    }
}

