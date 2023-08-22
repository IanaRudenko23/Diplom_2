package org.example.client;

public class Client {
    private String name;
    private String password;
    private String email;

    public Client(String email, String password){
        this.email = email;
        this.password = password;
    }
    public Client (String name){
        this.name = name;
    }

    public Client(String email, String password, String name){
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public Client(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
