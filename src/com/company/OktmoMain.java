package com.company;



public class OktmoMain {

    public static void main(String[] args) {
        OktmoData place = new OktmoData();

        new OktmoReader().readPlaces("data-201710.csv", place);
    }
}
