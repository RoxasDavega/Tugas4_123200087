package tugas4;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class View extends JFrame implements ActionListener{
    JLabel lTitle, lLogin, lRegister, lUsername, lPassword, lUsernameR, lPasswordR;
    JButton tombolLogin, tombolRegister;
    final JTextField fUsername, fPassword, fUsernameR, fPasswordR;
    Connector connector = new Connector();
    public View(){
        
        lTitle = new JLabel("Tugas 4");
        lUsername = new JLabel("Username");
        lPassword = new JLabel("Password");
        lUsernameR = new JLabel("Username");
        lPasswordR = new JLabel("Password");
        
        tombolLogin = new JButton("Login");
        tombolRegister = new JButton("Register");
        
        fUsername = new JTextField();
        fPassword = new JTextField();
        fUsernameR = new JTextField();
        fPasswordR = new JTextField();
        
        setLayout(null);
        add(lTitle);
        add(lUsername);
        add(lPassword);
        add(lUsernameR);
        add(lPasswordR);
        add(tombolLogin);
        add(tombolRegister);
        add(fUsername);
        add(fPassword);
        add(fUsernameR);
        add(fPasswordR);
        
        lTitle.setBounds(300, 20, 140, 40);
        lUsername.setBounds(100, 80, 80, 40);
        lPassword.setBounds(100, 180, 80, 40);
        lUsernameR.setBounds(355, 80, 80, 40);
        lPasswordR.setBounds(355, 180, 80, 40);
        
        fUsername.setBounds(100, 120, 140, 40);
        fPassword.setBounds(100, 220, 140, 40);
        fUsernameR.setBounds(355, 120, 140, 40);
        fPasswordR.setBounds(355, 220, 140, 40);
        
        tombolLogin.setBounds(125, 275, 80, 25);
        tombolRegister.setBounds(365, 275, 120, 25);
        
        tombolLogin.addActionListener(this);        
        tombolRegister.addActionListener(this);
        
        setSize(650,500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == tombolLogin){ 
            try{
                if(getUsername().isBlank() || getUsername().isEmpty()){
                    throw new IllegalArgumentException("Username is empty");
                }
                if(getPassword().isBlank()|| getPassword().isEmpty()){
                     throw new IllegalArgumentException("Password is empty");
                }
                //mengambil password dari username
                String query = "SELECT password FROM users WHERE username = '"+ getUsername() +"'"; 
                connector.statement = connector.koneksi.createStatement();
                ResultSet result = connector.statement.executeQuery(query);
                if(result.next()){
                    String pass = result.getString("password");
                    if(!pass.equals(getPassword())){ //pengecekan input password dengan password di database
                        JOptionPane.showMessageDialog(new JFrame(), "Password Salah");
                    }else{
                        JOptionPane.showMessageDialog(new JFrame(), "Berhasil Login");
                    }
                }else{ //jika username tidak ditemukan
                    JOptionPane.showMessageDialog(new JFrame(), "Username tidak ditemukan");
                }
                connector.statement.close(); 
            }catch(Exception error){
                JOptionPane.showMessageDialog(new JFrame(), error.getMessage());
            }
            
        }
        if(e.getSource() == tombolRegister){
            try{
                if(getUsernameR() == null || getUsernameR().isEmpty()){
                    throw new IllegalArgumentException("Username is empty");
                }
                if(getPasswordR() == null || getPasswordR().isEmpty()){
                     throw new IllegalArgumentException("Password is empty");
                }
                String query = "SELECT username FROM users WHERE username = '"+getUsernameR()+"'"; //mengambil username dari db
                connector.statement = connector.koneksi.createStatement();
                ResultSet result = connector.statement.executeQuery(query);

                if(result.next() == false){    //jika username tidak ditemukan, maka akan diinsert ke db
                    String queryInsert = "INSERT INTO users(username, password) VALUES ('"+getUsernameR()+"','"+getPasswordR()+"')";
                    connector.statement = connector.koneksi.createStatement();
                    connector.statement.executeUpdate(queryInsert);
                    JOptionPane.showMessageDialog(new JFrame(), "Berhasil Mendaftarkan User");
                }else{//jika username sudah ada di db
                    JOptionPane.showMessageDialog(new JFrame(), "Username sudah digunakan");
                }
                connector.statement.close();
            }catch(Exception error){
                JOptionPane.showMessageDialog(new JFrame(), error.getMessage());
            }
        }
    }  
    
    public String getUsername(){
        return fUsername.getText();
    }
    public String getPassword(){
        return fPassword.getText();
    }
    public String getUsernameR(){
        return fUsernameR.getText();
    }
    public String getPasswordR(){
        return fPasswordR.getText();
    }
}
