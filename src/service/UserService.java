package service;

import java.util.Scanner;

public class UserService extends Service{

    public boolean login(String username, String password){
        boolean isLogin;
        try{
            ps = con.prepareStatement("SELECT * FROM tbl_user WHERE username=? AND password=?");
            ps.setString(1,username);
            ps.setString(2,password);
            rs = ps.executeQuery();

            if(rs.next()){
                System.out.println(rs.getString(2));
                isLogin = true;
            }else {
                isLogin = false;
            }
        }catch (Exception ex){
            return false;
        }
        return isLogin;
    }

    public boolean userLogin(){
        Scanner input = new Scanner(System.in);
        //login vars
        String username;
        String password;
        int loginCount=0;
        boolean isLogin=false;

        //1. login function
        while (loginCount!=3){
            System.out.print("Enter username : ");
            username = input.nextLine();
            System.out.print("Enter password : ");
            password = input.nextLine();
            isLogin = login(username.trim(), password.trim());

            if (isLogin){
                System.out.println("-----------------------------------------");
                System.out.println("Successfully Login" );
                System.out.println("-----------------------------------------");
                isLogin = true;
                break;
            }else{
                loginCount++;
                isLogin=false;
                System.out.println("-----------------------------------------");
                System.out.println("Incorrect Login for "+loginCount+ " times" );
                System.out.println("-----------------------------------------");
                if(loginCount==3){
                    System.out.println("Cannot Login Again!!!");
                    System.out.println("-----------------------------------------");
                }
            }
        }
        return isLogin;
    }

    @Override
    public void add() {

    }

    @Override
    public void edit() {

    }

    @Override
    public void search() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void show() {

    }

}
