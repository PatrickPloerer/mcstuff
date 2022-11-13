package de.padel.itemsystem.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Mysql {
  private String HOST = "localhost";
  
  private String DATABASE = "coins";
  
  private String USER = "tug";
  
  private String PASSWORD = "2407kara";
  
  private Connection con;
  
  public Mysql(String host, String database, String user, String password) {
    this.HOST = host;
    this.DATABASE = database;
    this.USER = user;
    this.PASSWORD = password;
    connect();
  }
  
  public void connect() {
    try {
      this.con = DriverManager.getConnection("jdbc:mysql://" + this.HOST + ":3306/" + this.DATABASE + "?autoReconnect=true", 
          this.USER, this.PASSWORD);
      System.out.println("[MySQL] Verbindung hergestellt!");
    } catch (SQLException e) {
      System.out.println("[MySQL] Es konnte keine Verbindung aufgebaut werden! Fehler: " + e.getMessage());
    } 
  }
  
  public void close() {
    try {
      if (this.con != null) {
        this.con.close();
        System.out.println("[MySQL] Verbindung getrennt!");
      } 
    } catch (SQLException e) {
      System.out.println("[MySQL] Konnte nicht richtig beenden! Fehler: " + e.getMessage());
    } 
  }
  
  public void update(String qry) {
    try {
      Statement st = this.con.createStatement();
      st.executeUpdate(qry);
      st.close();
    } catch (SQLException e) {
      connect();
      System.err.println(e);
    } 
  }
  
  public boolean isConnected() {
    if (this.con != null)
      return true; 
    return false;
  }
  
  public ResultSet query(String qry) {
    ResultSet rs = null;
    try {
      Statement st = this.con.createStatement();
      rs = st.executeQuery(qry);
    } catch (SQLException e) {
      connect();
      System.err.println(e);
    } 
    return rs;
  }
}
