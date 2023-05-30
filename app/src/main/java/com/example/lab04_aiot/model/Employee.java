package com.example.lab04_aiot.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Employee implements Serializable {
    private int id;

    private String first_name;

    private String last_name;

    private String email;

    private String phone_number;


    private Timestamp hire_date;

    private Job job_id;

    private double salary;

    private Employee manager;

    private Department department_id;

    private int meeting;

    private Timestamp meeting_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Timestamp getHire_date() {
        return hire_date;
    }

    public void setHire_date(Timestamp hire_date) {
        this.hire_date = hire_date;
    }

    public Job getJob() {
        return job_id;
    }

    public void setJob(Job job) {
        this.job_id = job;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public Department getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Department department_id) {
        this.department_id = department_id;
    }

    public int getMeeting() {
        return meeting;
    }

    public void setMeeting(int meeting) {
        this.meeting = meeting;
    }

    public Timestamp getMeeting_date() {
        return meeting_date;
    }

    public void setMeeting_date(Timestamp meeting_date) {
        this.meeting_date = meeting_date;
    }
}
