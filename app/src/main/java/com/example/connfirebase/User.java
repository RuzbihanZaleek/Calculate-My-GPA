package com.example.connfirebase;

public class User {
    private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getReg() {
        return Reg;
    }

    public void setReg(String reg) {
        Reg = reg;
    }

    public String getIndex() {
        return Index;
    }

    public void setIndex(String index) {
        Index = index;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    private String Reg;
    private String Index;
    private String Phone;
    private String Email;

    public User() {
        //Empty constructor required by firebase
        // serialize the data retrieved and convert
        // it to an object of this class
    }

}
