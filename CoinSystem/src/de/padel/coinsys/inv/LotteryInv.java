package de.padel.coinsys.inv;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import de.padel.coinsys.main.Main;

public class LotteryInv {
/*    */   public static void loadInv(Player p) {
/* 15 */     if (Main.isMySQLEnabled()) {
/* 16 */       Inventory i = p.getPlayer().getServer().createInventory(null, 27, "§6Tägliche Belohnung§7(§6en§7)");
/* 17 */      
/* 36 */       p.openInventory(i);
/*    */     } 
/*    */   }
/*    */   
/*    */ }

