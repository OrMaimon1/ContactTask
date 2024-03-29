package com.example.contacttask.database;

public class GenderizeResponse {
        private String name;
        private String gender;
        private double probability;
        private int count;

    public GenderizeResponse(String name, String gender, double probability, int count) {
        this.name = name;
        this.gender = gender;
        this.probability = probability;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
