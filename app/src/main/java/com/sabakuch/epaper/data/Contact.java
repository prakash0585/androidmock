package com.sabakuch.epaper.data;

public class Contact {

    //private variables
    int id;
    String name;
    String contact;
    String email;

    // Empty constructor
    public Contact(){

    }
    // constructor
    public Contact(int id, String name, String _phone_number, String email){
        this.id = id;
        this.name = name;
        this.contact = _phone_number;
        this.email = email;
    }

    // constructor
    public Contact(String name, String _phone_number,String email){
        this.name = name;
        this.contact = _phone_number;
        this.email = email;
    }
    // getting ID
    public int getID(){
        return this.id;
    }

    // setting id
    public void setID(int id){
        this.id = id;
    }

    // getting name
    public String getName(){
        return this.name;
    }

    // setting name
    public void setName(String name){
        this.name = name;
    }

    // getting phone number
    public String getPhoneNumber(){
        return this.contact;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number){
        this.contact = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}