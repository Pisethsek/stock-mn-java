package service;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Scanner;


public abstract class Service {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    //open con
    public Service(){
        Properties properties = new Properties();
        Path myPath = Paths.get("src/application.properties");
        try {
            BufferedReader bf = Files.newBufferedReader(myPath, StandardCharsets.UTF_8);
            properties.load(bf);

            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(properties.getProperty("db.url"),
                    properties.getProperty("db.user"), properties.getProperty("db.passwd"));
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    //close con
    public void closed(){
        try{
            con.close();
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    //validate inputInteger
    public static int validateInput(String text){
        Scanner input = new Scanner(System.in);
        int integer = 0;
        String integerString;
        boolean isValid = false;

        do {
            try {
                System.out.print(text);
                integerString = input.nextLine();
                integer = Integer.parseInt(integerString);
                isValid = true;
                return integer;
            } catch (Exception ex) {
                System.out.println("Please Input Number!!!");
                System.out.println("_____________________");
                isValid = false;
            }
        } while (!isValid);
        return integer;
    }

    public void showByAsc(){

    }

    public void showByDesc(){

    }

    public void searchByName(){

    }

    public void searchByCategory(){}


    abstract public void add();
    abstract public void show();
    abstract public void edit();
    abstract public void search();
    abstract public void delete();
}
