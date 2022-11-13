/*    */ package de.padel.itemsystem.listener;
/*    */ 
/*    */ import de.padel.itemsystem.main.ItemAPI;
/*    */ import de.padel.itemsystem.main.Main;
/*    */ import net.minecraft.server.v1_8_R3.Entity;
/*    */ import net.minecraft.server.v1_8_R3.EntityChicken;
/*    */ import net.minecraft.server.v1_8_R3.EntityCow;
/*    */ import net.minecraft.server.v1_8_R3.EntityGuardian;
/*    */ import net.minecraft.server.v1_8_R3.EntityLiving;
/*    */ import net.minecraft.server.v1_8_R3.EntityPig;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerJoinEvent;
/*    */ import org.bukkit.event.player.PlayerKickEvent;
/*    */ import org.bukkit.event.player.PlayerQuitEvent;
/*    */ 
/*    */ public class QuitListener implements Listener {
/*    */   @EventHandler
/*    */   public void onQuit(PlayerQuitEvent e) {
/* 22 */     Player p = e.getPlayer();
/* 23 */     if (Main.pigs.containsKey(p.getUniqueId())) {
/* 24 */       EntityPig toRemove = (EntityPig)Main.pigs.get(p.getUniqueId());
/* 25 */       toRemove.die();
/* 26 */       Main.pigs.remove(p.getUniqueId());
/* 27 */     } else if (Main.cows.containsKey(p.getUniqueId())) {
/* 28 */       EntityCow toRemove = (EntityCow)Main.cows.get(p.getUniqueId());
/* 29 */       toRemove.die();
/* 30 */       Main.cows.remove(p.getUniqueId());
/* 31 */     } else if (Main.chickens.containsKey(p.getUniqueId())) {
/* 32 */       EntityChicken toRemove = (EntityChicken)Main.chickens.get(p.getUniqueId());
/* 33 */       toRemove.die();
/* 34 */       Main.chickens.remove(p.getUniqueId());
/* 35 */     } else if (Main.guardians.containsKey(p.getUniqueId())) {
/* 36 */       EntityGuardian toRemove = (EntityGuardian)Main.guardians.get(p.getUniqueId());
/* 37 */       toRemove.die();
/* 38 */       Main.guardians.remove(p.getUniqueId());
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onLeave(PlayerKickEvent e) {
/* 43 */     Player p = e.getPlayer();
/* 44 */     if (Main.pigs.containsKey(p.getUniqueId())) {
/* 45 */       EntityPig toRemove = (EntityPig)Main.pigs.get(p.getUniqueId());
/* 46 */       toRemove.die();
/* 47 */       Main.pigs.remove(p.getUniqueId());
/* 48 */     } else if (Main.cows.containsKey(p.getUniqueId())) {
/* 49 */       EntityCow toRemove = (EntityCow)Main.cows.get(p.getUniqueId());
/* 50 */       toRemove.die();
/* 51 */       Main.cows.remove(p.getUniqueId());
/* 52 */     } else if (Main.chickens.containsKey(p.getUniqueId())) {
/* 53 */       EntityChicken toRemove = (EntityChicken)Main.chickens.get(p.getUniqueId());
/* 54 */       toRemove.die();
/* 55 */       Main.chickens.remove(p.getUniqueId());
/* 56 */     } else if (Main.guardians.containsKey(p.getUniqueId())) {
/* 57 */       EntityGuardian toRemove = (EntityGuardian)Main.guardians.get(p.getUniqueId());
/* 58 */       toRemove.die();
/* 59 */       Main.guardians.remove(p.getUniqueId());
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onJoin(PlayerJoinEvent e) {
/* 64 */     Player p = e.getPlayer();
/* 65 */     for (Player on : Bukkit.getOnlinePlayers()) {
/* 66 */       if (Main.pigs.containsKey(on.getUniqueId())) {
/* 67 */         if (ItemAPI.getHide(p.getUniqueId().toString())) {
/* 68 */           Main.hideEntity(p, (Entity)Main.pigs.get(on.getUniqueId()));
/* 69 */           ItemAPI.setHide(p.getUniqueId().toString(), true);
/*    */           continue;
/*    */         } 
/* 71 */         Main.showEntity(p, (EntityLiving)Main.pigs.get(on.getUniqueId()));
/* 72 */         ItemAPI.setHide(p.getUniqueId().toString(), false);
/*    */         continue;
/*    */       } 
/* 75 */       if (Main.cows.containsKey(on.getUniqueId())) {
/* 76 */         if (ItemAPI.getHide(p.getUniqueId().toString())) {
/* 77 */           Main.hideEntity(p, (Entity)Main.cows.get(on.getUniqueId()));
/* 78 */           ItemAPI.setHide(p.getUniqueId().toString(), true);
/*    */           continue;
/*    */         } 
/* 80 */         Main.showEntity(p, (EntityLiving)Main.cows.get(on.getUniqueId()));
/* 81 */         ItemAPI.setHide(p.getUniqueId().toString(), false);
/*    */         continue;
/*    */       } 
/* 84 */       if (Main.chickens.containsKey(on.getUniqueId())) {
/* 85 */         if (ItemAPI.getHide(p.getUniqueId().toString())) {
/* 86 */           Main.hideEntity(p, (Entity)Main.chickens.get(on.getUniqueId()));
/* 87 */           ItemAPI.setHide(p.getUniqueId().toString(), true);
/*    */           continue;
/*    */         } 
/* 89 */         Main.showEntity(p, (EntityLiving)Main.chickens.get(on.getUniqueId()));
/* 90 */         ItemAPI.setHide(p.getUniqueId().toString(), false);
/*    */         continue;
/*    */       } 
/* 93 */       if (Main.guardians.containsKey(on.getUniqueId())) {
/* 94 */         if (ItemAPI.getHide(p.getUniqueId().toString())) {
/* 95 */           Main.hideEntity(p, (Entity)Main.guardians.get(on.getUniqueId()));
/* 96 */           ItemAPI.setHide(p.getUniqueId().toString(), true);
/*    */           continue;
/*    */         } 
/* 98 */         Main.showEntity(p, (EntityLiving)Main.guardians.get(on.getUniqueId()));
/* 99 */         ItemAPI.setHide(p.getUniqueId().toString(), false);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\jarja\Desktop\Plugins\Pls\PLobby\plugins\ItemSystem.jar!\de\padel\itemsystem\listerner\QuitListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */