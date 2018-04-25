/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tarun gupta
 */
import java.sql.*;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
public class DBCon {
    public static final String URL  = "jdbc:mysql://localhost/PR3";
    public static final String USER = "root";
    public static final String PASS = "";
    Connection con;
    Statement st;
    ResultSet rs ;
    public DBCon(){
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            con = DriverManager.getConnection(URL,USER,PASS);
            st = con.createStatement();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    public void category(String str) {
        try {
            st.executeUpdate("insert into category (cat_name) values('"+str+"')");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    public void editCategory(String str,int i){
        try {
            st.executeUpdate("update category set cat_name = '"+str+"' where cat_id = "+i+" ");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    
    }
    int getCat_id(String query) {
        try {
            ResultSet rs = st.executeQuery(query);
            if(rs.next())
                return rs.getInt("cat_id");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return 0;
    }
    
    public void addCourse(String cat_name,String cor_name,int fees,String desc){
        try {
            int id;
            
            ResultSet rss = st.executeQuery("select cat_id from category where cat_name = '"+cat_name+"'");
            if(rss.next()){
                id = rss.getInt("cat_id");
                st.executeUpdate("insert into course(cat_id,cor_name,fees,description) values('"+id+"','"+cor_name+"',"+fees+",'"+desc+"')");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    public ResultSet select(String q){
        ResultSet rs=null;
        try {
            rs = st.executeQuery(q);
        }
         catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return rs;
    }
    public void enquiry(String roll,String name,String email,String gender,String college,String phone,
                        String duration,String coursename,String date,String time,String fees,String reference){
        try {
            st.executeUpdate("insert into enquiry values ('"+roll+"','"+name+"','"+email+"','"+gender+"'  ,'"+college+"','"+phone+"','"+duration+"','"+coursename+"','"+date+"','"+time+"','"+fees+"','"+reference+"')");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        JOptionPane.showMessageDialog(null, "Submission Successful");
    }
    public int login(String username,String password){
        try {
             rs = st.executeQuery("select * from login1 where username = '"+username+"' and password = '"+password+"' ");
             if(rs.next()){  
                 return 1;
             }
             else{
             rs = st.executeQuery("select * from login2 where username = '"+username+"' and password = '"+password+"'");
                if(rs.next())
                    return 2;
             }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return 0;
    }
    public ResultSet selectTable(String query){
        ResultSet rss=null;
        try {
            rss = st.executeQuery(query);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, rss);
        }
    return rss;   
}
    public String registration(String roll,String query,String arg){
        try {
            rs = st.executeQuery(query);
            if(rs.next()){
                return rs.getString(arg);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return "invalid";
    }
    public void registeringValues(String roll,String name,String father,String gender,String phone,String duration,String course,
                                    String time,int totalfees,int discount,int paid,int remainfee,String query){
        try {
            Calendar cal = Calendar.getInstance();
            int dat = cal.get(Calendar.DATE);
            int month = cal.get(Calendar.MONTH) + 1;
            int year = cal.get(Calendar.YEAR);
            String date = "";
            date = String.valueOf(year) +"-"+ String.valueOf(month) +"-" + String.valueOf(dat);
            DBCon db = new DBCon();
            st.executeUpdate(query);
            st.executeUpdate("insert into transaction(roll,amountpaid,paymentleft,date) values('"+roll+"',"+paid+","+remainfee+",'"+date+"')");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    public void Updation(String roll,int amountpaid,int amountleft){
        try {
            Calendar cal = Calendar.getInstance();
            int dat = cal.get(Calendar.DATE);
            int month = cal.get(Calendar.MONTH) + 1;
            int year = cal.get(Calendar.YEAR);
            String date = "";
            date = String.valueOf(year) +"-"+ String.valueOf(month) +"-" + String.valueOf(dat);
            st.executeUpdate("insert into transaction(roll,amountpaid,paymentleft,date) values('"+roll+"',"+amountpaid+","+amountleft+",'"+date+"')");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
    }
    public void changePass(String user,String oldPass,String pass){
        try {
            rs = st.executeQuery("select * from login1 where username='"+user+"' and password='"+oldPass+"'");
            if(rs.next()){
                st.executeUpdate("update login1 set password='"+pass+"' where username='"+user+"'");
            }
            else{
                rs = st.executeQuery("select * from login2 where username='"+user+"' and password='"+oldPass+"'");
                if(rs.next()){
                    st.executeUpdate("update login1 set password='"+pass+"' where username='"+user+"'");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Change password error");
        }
                
                
    }
    
}
