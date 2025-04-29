package org.example.vertxDemo.Utils;

public class DBQueries {


    //Authentication queries
    public static final String loginQuery="SELECT * FROM users WHERE email = $1 AND password = $2";
    public static final String registrationQuery="INSERT INTO users (email, password, username) VALUES ($1, $2, $3)";


}
