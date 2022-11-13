package de.padel.coinsys.utils;

import java.sql.Connection;
/*    */ import java.sql.DriverManager;
import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;

/*    */ 
/*    */ public class Mysql {
/* 12 */   private String HOST = "localhost";
/*    */   
/* 13 */   private String DATABASE = "coins";
/*    */   
/* 14 */   private String USER = "tug";
/*    */   
/* 15 */   private String PASSWORD = "2407kara";
/*    */   
/*    */   private Connection con;
/*    */   
/*    */   public Mysql(String host, String database, String user, String password) {
/* 20 */     this.HOST = host;
/* 21 */     this.DATABASE = database;
/* 22 */     this.USER = user;
/* 23 */     this.PASSWORD = password;
/* 25 */     connect();
/*    */   }
/*    */   
/*    */   public void connect() {
/*    */     try {
/* 32 */       this.con = DriverManager.getConnection("jdbc:mysql://" + this.HOST + ":3306/" + this.DATABASE + "?autoReconnect=true&useSSL=false", 
/* 33 */           this.USER, this.PASSWORD);
/* 34 */       System.out.println("[MySQL] Verbindung hergestellt!");
/* 36 */     } catch (SQLException e) {
/* 38 */       System.out.println("[MySQL] Es konnte keine Verbindung aufgebaut werden! Fehler: " + e.getMessage());
/*    */     } 
/*    */   }
/*    */   
/*    */   public void close() {
/*    */     try {
/* 46 */       if (this.con != null) {
/* 48 */         this.con.close();
/* 49 */         System.out.println("[MySQL] Verbindung getrennt!");
/*    */       } 
/* 52 */     } catch (SQLException e) {
/* 54 */       System.out.println("[MySQL] Konnte nicht richtig beenden! Fehler: " + e.getMessage());
/*    */     } 
/*    */   }
/*    */   
/*    */   public void update(String qry) {
/*    */     try {
/* 62 */       PreparedStatement st = con.prepareStatement(qry);
/* 63 */       st.executeUpdate(qry);
				st.close();
/* 66 */     } catch (SQLException e) {
/* 69 */       System.err.println(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isConnected() {
/* 74 */     if (this.con != null)
/* 75 */       return true; 
/* 77 */     return false;
/*    */   }
/*    */   
/*    */   public ResultSet query(String qry) {
/* 83 */     ResultSet rs = null;
/*    */     try {
/* 86 */       PreparedStatement st = this.con.prepareStatement(qry);
/* 87 */       rs = st.executeQuery();
/* 89 */     } catch (SQLException e) {
/* 92 */       System.err.println(e);
/*    */     } 
/* 94 */     return rs;
/*    */   }
/*    */ }
