/*     */ package de.padel.itemsystem.listener;
/*     */ 
/*     */ import de.padel.itemsystem.main.ItemAPI;
/*     */ import de.padel.itemsystem.main.Main;
/*     */ import de.padel.itemsystem.mobs.RideAbleChicken;
/*     */ import de.padel.itemsystem.mobs.RideAbleCow;
/*     */ import de.padel.itemsystem.mobs.RideAbleGuardian;
/*     */ import de.padel.itemsystem.mobs.RideAblePig;
/*     */ import de.padel.itemsystem.utils.AnvilGUI;
/*     */ import de.padel.itemsystem.utils.Pet;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.md_5.bungee.api.ChatColor;
/*     */ import net.minecraft.server.v1_8_R3.Entity;
/*     */ import net.minecraft.server.v1_8_R3.EntityAgeable;
/*     */ import net.minecraft.server.v1_8_R3.EntityLiving;
/*     */ import net.minecraft.server.v1_8_R3.EnumParticle;
/*     */ import net.minecraft.server.v1_8_R3.Packet;
/*     */ import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.Effect;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
/*     */ import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.inventory.ClickType;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.player.PlayerInteractAtEntityEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.event.player.PlayerMoveEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class InvListener implements Listener {
/*     */   @EventHandler
/*     */   public void onInvClick(InventoryClickEvent e) {
/*  48 */     if (e != null && 
/*  49 */       e.getCurrentItem() != null) {
/*  50 */       if (!e.getCurrentItem().hasItemMeta())
/*     */         return; 
/*  51 */       if (!e.getCurrentItem().getItemMeta().hasDisplayName())
/*     */         return; 
/*  52 */       if ((e.getWhoClicked().getItemInHand() != null && e.getWhoClicked().getItemOnCursor() != null && !e.getClick().equals(ClickType.WINDOW_BORDER_LEFT)) || (!e.getClick().equals(ClickType.WINDOW_BORDER_RIGHT) && e.getCurrentItem().getType() != null && e.getCurrentItem().hasItemMeta())) {
/*  53 */         final Player p = (Player)e.getWhoClicked();
/*  54 */         if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cDeine Schuhe")) {
/*  55 */           Inventory inv = Bukkit.createInventory(null, 27, "§cDeine Schuhe");
/*  56 */           String items = ItemAPI.getItems(p.getUniqueId().toString());
/*  57 */           String[] str = items.split(",");
/*  59 */           for (int f = 0; f < str.length; f++) {
/*  61 */             if (str[f].equalsIgnoreCase("1")) {
/*  62 */               inv.addItem(new ItemStack[] { createItem3(Material.LEATHER_BOOTS, Color.RED, 1, "§cFeuer Schuhe ", Arrays.asList(new String[] { "§cAchtung HeiÃŸ!" })) });
/*  63 */             } else if (str[f].equalsIgnoreCase("2")) {
/*  64 */               inv.addItem(new ItemStack[] { createItem3(Material.LEATHER_BOOTS, Color.PURPLE, 1, "§5Liebes Schuhe ", Arrays.asList(new String[] { "§5Ein hingucker!" })) });
/*  65 */             } else if (str[f].equalsIgnoreCase("3")) {
/*  66 */               inv.addItem(new ItemStack[] { createItem3(Material.LEATHER_BOOTS, Color.GRAY, 1, "§8Rauch Schuhe ", Arrays.asList(new String[] { "§8Smoke Weed Everyday!" })) });
/*  67 */             } else if (str[f].equalsIgnoreCase("4")) {
/*  68 */               inv.addItem(new ItemStack[] { createItem3(Material.LEATHER_BOOTS, Color.AQUA, 1, "§bRegen Schuhe ", Arrays.asList(new String[] { "§bPlitsch nass!" })) });
/*  69 */             } else if (str[f].equalsIgnoreCase("5")) {
/*  70 */               inv.addItem(new ItemStack[] { createItem3(Material.LEATHER_BOOTS, Color.LIME, 1, "§aMusik Schuhe ", Arrays.asList(new String[] { "§aWie Musik in den Ohren!" })) });
/*  71 */             } else if (str[f].equalsIgnoreCase("6")) {
/*  72 */               inv.addItem(new ItemStack[] { createItem3(Material.LEATHER_BOOTS, Color.ORANGE, 1, "§6Lava Schuhe ", Arrays.asList(new String[] { "§6TSCHHH!" })) });
/*  73 */             } else if (str[f].equalsIgnoreCase("7")) {
/*  74 */               inv.addItem(new ItemStack[] { createItem3(Material.LEATHER_BOOTS, Color.SILVER, 1, "§7Geister Schuhe ", Arrays.asList(new String[] { "§7Wo bist du hin?" })) });
/*     */             } 
/*     */           } 
/*  78 */           inv.setItem(26, createItem(Material.BARRIER, 1, "§cAlles Ausziehen", Arrays.asList(new String[] { "" })));
/*  79 */           inv.setItem(18, createItem2(Material.STAINED_GLASS_PANE, (short)14, 1, "§cZurÃ¼ck ", Arrays.asList(new String[] { "" })));
/*  80 */           p.openInventory(inv);
/*     */         } 
/*  82 */         if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aDeine Pets")) {
/*  83 */           Inventory inv = Bukkit.createInventory(null, 27, "§aDeine Pets");
/*  84 */           String items = ItemAPI.getItems(p.getUniqueId().toString());
/*  85 */           String[] str = items.split(",");
/*  87 */           for (int f = 0; f < str.length; f++) {
/*  89 */             if (str[f].equalsIgnoreCase("8")) {
/*  90 */               inv.addItem(new ItemStack[] { createItem2(Material.MONSTER_EGG, (short)90, 1, "§dBaby-Schwein ", Arrays.asList(new String[] { "" })) });
/*  91 */             } else if (str[f].equalsIgnoreCase("9")) {
/*  92 */               inv.addItem(new ItemStack[] { createItem2(Material.MONSTER_EGG, (short)90, 1, "§dSchwein ", Arrays.asList(new String[] { "" })) });
/*  93 */             } else if (str[f].equalsIgnoreCase("10")) {
/*  94 */               inv.addItem(new ItemStack[] { createItem2(Material.MONSTER_EGG, (short)92, 1, "§7Baby-Kuh ", Arrays.asList(new String[] { "" })) });
/*  95 */             } else if (str[f].equalsIgnoreCase("11")) {
/*  96 */               inv.addItem(new ItemStack[] { createItem2(Material.MONSTER_EGG, (short)92, 1, "§7Kuh ", Arrays.asList(new String[] { "" })) });
/*  97 */             } else if (str[f].equalsIgnoreCase("12")) {
/*  98 */               inv.addItem(new ItemStack[] { createItem2(Material.MONSTER_EGG, (short)93, 1, "§fHÃ¼hnchen ", Arrays.asList(new String[] { "" })) });
/*  99 */             } else if (str[f].equalsIgnoreCase("13")) {
/* 100 */               inv.addItem(new ItemStack[] { createItem2(Material.MONSTER_EGG, (short)93, 1, "§fHuhn ", Arrays.asList(new String[] { "" })) });
/* 101 */             } else if (str[f].equalsIgnoreCase("14")) {
/* 102 */               inv.addItem(new ItemStack[] { createItem2(Material.MONSTER_EGG, (short)68, 1, "§bWÃ¤chter ", Arrays.asList(new String[] { "" })) });
/*     */             } 
/*     */           } 
/* 105 */           inv.setItem(26, createItem(Material.BARRIER, 1, "§cPet entfernen!", Arrays.asList(new String[] { "" })));
/* 106 */           inv.setItem(18, createItem2(Material.STAINED_GLASS_PANE, (short)14, 1, "§cZurÃ¼ck ", Arrays.asList(new String[] { "" })));
/* 107 */           p.openInventory(inv);
/*     */         } 
/* 109 */         if (e.getClickedInventory().getName().equalsIgnoreCase("§cDeine Schuhe")) {
/* 110 */           if (e.getCurrentItem().getItemMeta().getDisplayName() == "§cFeuer Schuhe ") {
/* 111 */             p.getInventory().setBoots(createItem3(Material.LEATHER_BOOTS, Color.RED, 1, "§cFeuer Schuhe ", Arrays.asList(new String[] { "§cAchtung HeiÃŸ!" })));
/* 112 */             p.closeInventory();
/*     */           } 
/* 114 */           if (e.getCurrentItem().getItemMeta().getDisplayName() == "§5Liebes Schuhe ") {
/* 115 */             p.getInventory().setBoots(createItem3(Material.LEATHER_BOOTS, Color.PURPLE, 1, "§5Liebes Schuhe ", Arrays.asList(new String[] { "§5Ein hingucker!" })));
/* 116 */             p.closeInventory();
/*     */           } 
/* 118 */           if (e.getCurrentItem().getItemMeta().getDisplayName() == "§8Rauch Schuhe ") {
/* 119 */             p.getInventory().setBoots(createItem3(Material.LEATHER_BOOTS, Color.GRAY, 1, "§8Rauch Schuhe ", Arrays.asList(new String[] { "§8Smoke Weed Everyday!" })));
/* 120 */             p.closeInventory();
/*     */           } 
/* 122 */           if (e.getCurrentItem().getItemMeta().getDisplayName() == "§bRegen Schuhe ") {
/* 123 */             p.getInventory().setBoots(createItem3(Material.LEATHER_BOOTS, Color.AQUA, 1, "§bRegen Schuhe ", Arrays.asList(new String[] { "§bPlitsch nass!" })));
/* 124 */             p.closeInventory();
/*     */           } 
/* 126 */           if (e.getCurrentItem().getItemMeta().getDisplayName() == "§aMusik Schuhe ") {
/* 127 */             p.getInventory().setBoots(createItem3(Material.LEATHER_BOOTS, Color.LIME, 1, "§aMusik Schuhe ", Arrays.asList(new String[] { "§aWie Musik in den Ohren!" })));
/* 128 */             p.closeInventory();
/*     */           } 
/* 130 */           if (e.getCurrentItem().getItemMeta().getDisplayName() == "§6Lava Schuhe ") {
/* 131 */             p.getInventory().setBoots(createItem3(Material.LEATHER_BOOTS, Color.ORANGE, 1, "§6Lava Schuhe ", Arrays.asList(new String[] { "§6TSCHHH!" })));
/* 132 */             p.closeInventory();
/*     */           } 
/* 134 */           if (e.getCurrentItem().getItemMeta().getDisplayName() == "§7Geister Schuhe ") {
/* 135 */             p.getInventory().setBoots(createItem3(Material.LEATHER_BOOTS, Color.SILVER, 1, "§7Geister Schuhe ", Arrays.asList(new String[] { "§7Wo bist du hin?" })));
/* 136 */             p.closeInventory();
/*     */           } 
/* 138 */           if (e.getCurrentItem().getItemMeta().getDisplayName() == "§cZurÃ¼ck ") {
/* 139 */             Inventory i = p.getPlayer().getServer().createInventory(null, 27, "§fItems");
/* 140 */             for (int f = 0; f <= 26; f++) {
/* 141 */               i.setItem(f, createItem2(Material.STAINED_GLASS_PANE, (short)15, 1, "§c-", Arrays.asList(new String[] { "" })));
/*     */             } 
/* 143 */             i.setItem(12, createItem3(Material.LEATHER_BOOTS, Color.RED, 1, "§cDeine Schuhe", Arrays.asList(new String[] { "" })));
/* 144 */             i.setItem(14, createItem2(Material.MONSTER_EGG, (short)90, 1, "§aDeine Pets", Arrays.asList(new String[] { "" })));
/* 145 */             if (ItemAPI.getHide(p.getUniqueId().toString())) {
/* 146 */               i.setItem(18, createItem(Material.GLOWSTONE_DUST, 1, "§aPets Aktivieren", Arrays.asList(new String[] { "" })));
/*     */             } else {
/* 148 */               i.setItem(18, createItem(Material.REDSTONE, 1, "§cPets Deaktivieren", Arrays.asList(new String[] { "" })));
/*     */             } 
/* 150 */             p.openInventory(i);
/*     */           } 
/* 152 */           if (e.getCurrentItem().getItemMeta().getDisplayName() == "§cAlles Ausziehen")
/* 153 */             p.getInventory().setArmorContents(null); 
/*     */         } 
/* 156 */         if (e.getClickedInventory().getName().equalsIgnoreCase("§aDeine Pets")) {
/* 157 */           if (e.getCurrentItem().getItemMeta().getDisplayName() == "§dBaby-Schwein ") {
/* 158 */             if (Main.pigs.containsKey(p.getUniqueId())) {
/* 159 */               RideAblePig toRemove = (RideAblePig)Main.pigs.get(p.getUniqueId());
/* 160 */               toRemove.die();
/* 161 */               Main.pigs.remove(p.getUniqueId());
/* 162 */             } else if (Main.cows.containsKey(p.getUniqueId())) {
/* 163 */               RideAbleCow toRemove = (RideAbleCow)Main.cows.get(p.getUniqueId());
/* 164 */               toRemove.die();
/* 165 */               Main.cows.remove(p.getUniqueId());
/* 166 */             } else if (Main.chickens.containsKey(p.getUniqueId())) {
/* 167 */               RideAbleChicken toRemove = (RideAbleChicken)Main.chickens.get(p.getUniqueId());
/* 168 */               toRemove.die();
/* 169 */               Main.chickens.remove(p.getUniqueId());
/* 170 */             } else if (Main.guardians.containsKey(p.getUniqueId())) {
/* 171 */               RideAbleGuardian toRemove = (RideAbleGuardian)Main.guardians.get(p.getUniqueId());
/* 172 */               toRemove.die();
/* 173 */               Main.guardians.remove(p.getUniqueId());
/*     */             } 
/* 176 */             RideAblePig pet = (RideAblePig)Pet.getPet(p, "8");
/* 177 */             pet.setCustomNameVisible(true);
/* 178 */             if (ItemAPI.getName(p.getUniqueId().toString(), "8") == "") {
/* 179 */               pet.setCustomName("§dBaby-Schwein");
/*     */             } else {
/* 182 */               pet.setCustomName(ItemAPI.getName(p.getUniqueId().toString(), "8"));
/*     */             } 
/* 185 */             pet.setAge(0);
/* 186 */             Main.pigs.put(p.getUniqueId(), pet);
/* 187 */             for (Player on : Bukkit.getOnlinePlayers()) {
/* 188 */               if (ItemAPI.getHide(on.getUniqueId().toString()))
/* 189 */                 Main.hideEntity(p, (Entity)pet); 
/*     */             } 
/* 192 */             p.closeInventory();
/*     */           } 
/* 196 */           if (e.getCurrentItem().getItemMeta().getDisplayName() == "§dSchwein ") {
/* 197 */             if (Main.pigs.containsKey(p.getUniqueId())) {
/* 198 */               RideAblePig toRemove = (RideAblePig)Main.pigs.get(p.getUniqueId());
/* 199 */               toRemove.die();
/* 200 */               Main.pigs.remove(p.getUniqueId());
/* 201 */             } else if (Main.cows.containsKey(p.getUniqueId())) {
/* 202 */               RideAbleCow toRemove = (RideAbleCow)Main.cows.get(p.getUniqueId());
/* 203 */               toRemove.die();
/* 204 */               Main.cows.remove(p.getUniqueId());
/* 205 */             } else if (Main.chickens.containsKey(p.getUniqueId())) {
/* 206 */               RideAbleChicken toRemove = (RideAbleChicken)Main.chickens.get(p.getUniqueId());
/* 207 */               toRemove.die();
/* 208 */               Main.chickens.remove(p.getUniqueId());
/* 209 */             } else if (Main.guardians.containsKey(p.getUniqueId())) {
/* 210 */               RideAbleGuardian toRemove = (RideAbleGuardian)Main.guardians.get(p.getUniqueId());
/* 211 */               toRemove.die();
/* 212 */               Main.guardians.remove(p.getUniqueId());
/*     */             } 
/* 214 */             RideAblePig pet = (RideAblePig)Pet.getPet(p, "9");
/* 216 */             pet.setCustomNameVisible(true);
/* 217 */             if (ItemAPI.getName(p.getUniqueId().toString(), "9") == "") {
/* 218 */               pet.setCustomName("§dSchwein");
/*     */             } else {
/* 220 */               pet.setCustomName(ItemAPI.getName(p.getUniqueId().toString(), "9"));
/*     */             } 
/* 222 */             pet.setAge(10);
/* 223 */             Main.pigs.put(p.getUniqueId(), pet);
/* 225 */             for (Player on : Bukkit.getOnlinePlayers()) {
/* 226 */               if (ItemAPI.getHide(on.getUniqueId().toString()))
/* 227 */                 Main.hideEntity(on, (Entity)pet); 
/*     */             } 
/* 230 */             p.closeInventory();
/*     */           } 
/* 234 */           if (e.getCurrentItem().getItemMeta().getDisplayName() == "§7Baby-Kuh ") {
/* 235 */             if (Main.pigs.containsKey(p.getUniqueId())) {
/* 236 */               RideAblePig toRemove = (RideAblePig)Main.pigs.get(p.getUniqueId());
/* 237 */               toRemove.die();
/* 238 */               Main.pigs.remove(p.getUniqueId());
/* 239 */             } else if (Main.cows.containsKey(p.getUniqueId())) {
/* 240 */               RideAbleCow toRemove = (RideAbleCow)Main.cows.get(p.getUniqueId());
/* 241 */               toRemove.die();
/* 242 */               Main.cows.remove(p.getUniqueId());
/* 243 */             } else if (Main.chickens.containsKey(p.getUniqueId())) {
/* 244 */               RideAbleChicken toRemove = (RideAbleChicken)Main.chickens.get(p.getUniqueId());
/* 245 */               toRemove.die();
/* 246 */               Main.chickens.remove(p.getUniqueId());
/* 247 */             } else if (Main.guardians.containsKey(p.getUniqueId())) {
/* 248 */               RideAbleGuardian toRemove = (RideAbleGuardian)Main.guardians.get(p.getUniqueId());
/* 249 */               toRemove.die();
/* 250 */               Main.guardians.remove(p.getUniqueId());
/*     */             } 
/* 252 */             RideAbleCow pet = (RideAbleCow)Pet.getPet(p, "10");
/* 254 */             pet.setCustomNameVisible(true);
/* 255 */             if (ItemAPI.getName(p.getUniqueId().toString(), "10") == "") {
/* 256 */               pet.setCustomName("§7Baby-Kuh");
/*     */             } else {
/* 258 */               pet.setCustomName(ItemAPI.getName(p.getUniqueId().toString(), "10"));
/*     */             } 
/* 260 */             pet.setAge(0);
/* 262 */             Main.cows.put(p.getUniqueId(), pet);
/* 264 */             for (Player on : Bukkit.getOnlinePlayers()) {
/* 265 */               if (ItemAPI.getHide(on.getUniqueId().toString()))
/* 266 */                 Main.hideEntity(on, (Entity)pet); 
/*     */             } 
/* 269 */             p.closeInventory();
/*     */           } 
/* 273 */           if (e.getCurrentItem().getItemMeta().getDisplayName() == "§7Kuh ") {
/* 274 */             if (Main.pigs.containsKey(p.getUniqueId())) {
/* 275 */               RideAblePig toRemove = (RideAblePig)Main.pigs.get(p.getUniqueId());
/* 276 */               toRemove.die();
/* 277 */               Main.pigs.remove(p.getUniqueId());
/* 278 */             } else if (Main.cows.containsKey(p.getUniqueId())) {
/* 279 */               RideAbleCow toRemove = (RideAbleCow)Main.cows.get(p.getUniqueId());
/* 280 */               toRemove.die();
/* 281 */               Main.cows.remove(p.getUniqueId());
/* 282 */             } else if (Main.chickens.containsKey(p.getUniqueId())) {
/* 283 */               RideAbleChicken toRemove = (RideAbleChicken)Main.chickens.get(p.getUniqueId());
/* 284 */               toRemove.die();
/* 285 */               Main.chickens.remove(p.getUniqueId());
/* 286 */             } else if (Main.guardians.containsKey(p.getUniqueId())) {
/* 287 */               RideAbleGuardian toRemove = (RideAbleGuardian)Main.guardians.get(p.getUniqueId());
/* 288 */               toRemove.die();
/* 289 */               Main.guardians.remove(p.getUniqueId());
/*     */             } 
/* 291 */             RideAbleCow pet = (RideAbleCow)Pet.getPet(p, "11");
/* 292 */             pet.setCustomNameVisible(true);
/* 293 */             if (ItemAPI.getName(p.getUniqueId().toString(), "11") == "") {
/* 294 */               pet.setCustomName("§7Kuh");
/*     */             } else {
/* 296 */               pet.setCustomName(ItemAPI.getName(p.getUniqueId().toString(), "11"));
/*     */             } 
/* 298 */             pet.setAge(10);
/* 299 */             Main.cows.put(p.getUniqueId(), pet);
/* 301 */             for (Player on : Bukkit.getOnlinePlayers()) {
/* 302 */               if (ItemAPI.getHide(on.getUniqueId().toString()))
/* 303 */                 Main.hideEntity(on, (Entity)pet); 
/*     */             } 
/* 306 */             p.closeInventory();
/*     */           } 
/* 310 */           if (e.getCurrentItem().getItemMeta().getDisplayName() == "§fHÃ¼hnchen ") {
/* 311 */             if (Main.pigs.containsKey(p.getUniqueId())) {
/* 312 */               RideAblePig toRemove = (RideAblePig)Main.pigs.get(p.getUniqueId());
/* 313 */               toRemove.die();
/* 314 */               Main.pigs.remove(p.getUniqueId());
/* 315 */             } else if (Main.cows.containsKey(p.getUniqueId())) {
/* 316 */               RideAbleCow toRemove = (RideAbleCow)Main.cows.get(p.getUniqueId());
/* 317 */               toRemove.die();
/* 318 */               Main.cows.remove(p.getUniqueId());
/* 319 */             } else if (Main.chickens.containsKey(p.getUniqueId())) {
/* 320 */               RideAbleChicken toRemove = (RideAbleChicken)Main.chickens.get(p.getUniqueId());
/* 321 */               toRemove.die();
/* 322 */               Main.chickens.remove(p.getUniqueId());
/* 323 */             } else if (Main.guardians.containsKey(p.getUniqueId())) {
/* 324 */               RideAbleGuardian toRemove = (RideAbleGuardian)Main.guardians.get(p.getUniqueId());
/* 325 */               toRemove.die();
/* 326 */               Main.guardians.remove(p.getUniqueId());
/*     */             } 
/* 328 */             RideAbleChicken pet = (RideAbleChicken)Pet.getPet(p, "12");
/* 330 */             pet.setCustomNameVisible(true);
/* 331 */             if (ItemAPI.getName(p.getUniqueId().toString(), "12") == "") {
/* 332 */               pet.setCustomName("§fHÃ¼hnchen");
/*     */             } else {
/* 334 */               pet.setCustomName(ItemAPI.getName(p.getUniqueId().toString(), "10"));
/*     */             } 
/* 336 */             pet.createChild((EntityAgeable)pet);
/* 337 */             pet.setAge(0);
/* 338 */             Main.chickens.put(p.getUniqueId(), pet);
/* 340 */             for (Player on : Bukkit.getOnlinePlayers()) {
/* 341 */               if (ItemAPI.getHide(on.getUniqueId().toString()))
/* 342 */                 Main.hideEntity(on, (Entity)pet); 
/*     */             } 
/* 345 */             p.closeInventory();
/*     */           } 
/* 349 */           if (e.getCurrentItem().getItemMeta().getDisplayName() == "§fHuhn ") {
/* 351 */             if (Main.pigs.containsKey(p.getUniqueId())) {
/* 352 */               RideAblePig toRemove = (RideAblePig)Main.pigs.get(p.getUniqueId());
/* 353 */               toRemove.die();
/* 354 */               Main.pigs.remove(p.getUniqueId());
/* 355 */             } else if (Main.cows.containsKey(p.getUniqueId())) {
/* 356 */               RideAbleCow toRemove = (RideAbleCow)Main.cows.get(p.getUniqueId());
/* 357 */               toRemove.die();
/* 358 */               Main.cows.remove(p.getUniqueId());
/* 359 */             } else if (Main.chickens.containsKey(p.getUniqueId())) {
/* 360 */               RideAbleChicken toRemove = (RideAbleChicken)Main.chickens.get(p.getUniqueId());
/* 361 */               toRemove.die();
/* 362 */               Main.chickens.remove(p.getUniqueId());
/* 363 */             } else if (Main.guardians.containsKey(p.getUniqueId())) {
/* 364 */               RideAbleGuardian toRemove = (RideAbleGuardian)Main.guardians.get(p.getUniqueId());
/* 365 */               toRemove.die();
/* 366 */               Main.guardians.remove(p.getUniqueId());
/*     */             } 
/* 368 */             RideAbleChicken pet = (RideAbleChicken)Pet.getPet(p, "13");
/* 369 */             pet.setCustomNameVisible(true);
/* 370 */             if (ItemAPI.getName(p.getUniqueId().toString(), "13") == "") {
/* 371 */               pet.setCustomName("§fHuhn");
/*     */             } else {
/* 373 */               pet.setCustomName(ItemAPI.getName(p.getUniqueId().toString(), "13"));
/*     */             } 
/* 375 */             pet.setAge(10);
/* 376 */             Main.chickens.put(p.getUniqueId(), pet);
/* 378 */             for (Player on : Bukkit.getOnlinePlayers()) {
/* 379 */               if (ItemAPI.getHide(on.getUniqueId().toString()))
/* 380 */                 Main.hideEntity(on, (Entity)pet); 
/*     */             } 
/* 383 */             p.closeInventory();
/*     */           } 
/* 387 */           if (e.getCurrentItem().getItemMeta().getDisplayName() == "§bWÃ¤chter ") {
/* 389 */             if (Main.pigs.containsKey(p.getUniqueId())) {
/* 390 */               RideAblePig toRemove = (RideAblePig)Main.pigs.get(p.getUniqueId());
/* 391 */               toRemove.die();
/* 392 */               Main.pigs.remove(p.getUniqueId());
/* 393 */             } else if (Main.cows.containsKey(p.getUniqueId())) {
/* 394 */               RideAbleCow toRemove = (RideAbleCow)Main.cows.get(p.getUniqueId());
/* 395 */               toRemove.die();
/* 396 */               Main.cows.remove(p.getUniqueId());
/* 397 */             } else if (Main.chickens.containsKey(p.getUniqueId())) {
/* 398 */               RideAbleChicken toRemove = (RideAbleChicken)Main.chickens.get(p.getUniqueId());
/* 399 */               toRemove.die();
/* 400 */               Main.chickens.remove(p.getUniqueId());
/* 401 */             } else if (Main.guardians.containsKey(p.getUniqueId())) {
/* 402 */               RideAbleGuardian toRemove = (RideAbleGuardian)Main.guardians.get(p.getUniqueId());
/* 403 */               toRemove.die();
/* 404 */               Main.guardians.remove(p.getUniqueId());
/*     */             } 
/* 406 */             RideAbleGuardian pet = (RideAbleGuardian)Pet.getPet(p, "14");
/* 408 */             pet.setCustomNameVisible(true);
/* 409 */             if (ItemAPI.getName(p.getUniqueId().toString(), "14") == "") {
/* 410 */               pet.setCustomName("§bWÃ¤chter");
/*     */             } else {
/* 412 */               pet.setCustomName(ItemAPI.getName(p.getUniqueId().toString(), "14"));
/*     */             } 
/* 414 */             pet.setElder(false);
/* 415 */             pet.setGoalTarget(null);
/* 416 */             Main.guardians.put(p.getUniqueId(), pet);
/* 418 */             for (Player on : Bukkit.getOnlinePlayers()) {
/* 419 */               if (ItemAPI.getHide(on.getUniqueId().toString()))
/* 420 */                 Main.hideEntity(on, (Entity)pet); 
/*     */             } 
/* 423 */             p.closeInventory();
/*     */           } 
/* 427 */           if (e.getCurrentItem().getItemMeta().getDisplayName() == "§cZurÃ¼ck ") {
/* 428 */             Inventory i = p.getPlayer().getServer().createInventory(null, 27, "§fItems");
/* 429 */             for (int f = 0; f <= 26; f++) {
/* 430 */               i.setItem(f, createItem2(Material.STAINED_GLASS_PANE, (short)15, 1, "§c-", Arrays.asList(new String[] { "" })));
/*     */             } 
/* 432 */             i.setItem(12, createItem3(Material.LEATHER_BOOTS, Color.RED, 1, "§cDeine Schuhe", Arrays.asList(new String[] { "" })));
/* 433 */             i.setItem(14, createItem2(Material.MONSTER_EGG, (short)90, 1, "§aDeine Pets", Arrays.asList(new String[] { "" })));
/* 434 */             if (ItemAPI.getHide(p.getUniqueId().toString())) {
/* 435 */               i.setItem(18, createItem(Material.GLOWSTONE_DUST, 1, "§aPets Aktivieren", Arrays.asList(new String[] { "" })));
/*     */             } else {
/* 437 */               i.setItem(18, createItem(Material.REDSTONE, 1, "§cPets Deaktivieren", Arrays.asList(new String[] { "" })));
/*     */             } 
/* 439 */             p.openInventory(i);
/*     */           } 
/* 441 */           if (e.getCurrentItem().getItemMeta().getDisplayName() == "§cPet entfernen!") {
/* 442 */             if (Main.pigs.containsKey(p.getUniqueId())) {
/* 443 */               RideAblePig toRemove = (RideAblePig)Main.pigs.get(p.getUniqueId());
/* 444 */               toRemove.die();
/* 445 */               Main.pigs.remove(p.getUniqueId());
/* 446 */             } else if (Main.cows.containsKey(p.getUniqueId())) {
/* 447 */               RideAbleCow toRemove = (RideAbleCow)Main.cows.get(p.getUniqueId());
/* 448 */               toRemove.die();
/* 449 */               Main.cows.remove(p.getUniqueId());
/* 450 */             } else if (Main.chickens.containsKey(p.getUniqueId())) {
/* 451 */               RideAbleChicken toRemove = (RideAbleChicken)Main.chickens.get(p.getUniqueId());
/* 452 */               toRemove.die();
/* 453 */               Main.chickens.remove(p.getUniqueId());
/* 454 */             } else if (Main.guardians.containsKey(p.getUniqueId())) {
/* 455 */               RideAbleGuardian toRemove = (RideAbleGuardian)Main.guardians.get(p.getUniqueId());
/* 456 */               toRemove.die();
/* 457 */               Main.guardians.remove(p.getUniqueId());
/*     */             } 
/* 459 */             p.closeInventory();
/*     */           } 
/*     */         } 
/* 463 */         if (e.getCurrentItem().getItemMeta().getDisplayName() == "§aName Ã¤ndern")
/* 464 */           if (Main.pigs.containsKey(p.getUniqueId())) {
/* 465 */             final RideAblePig en = (RideAblePig)Main.pigs.get(p.getUniqueId());
/* 466 */             AnvilGUI gui = new AnvilGUI(p, new AnvilGUI.AnvilClickEventHandler() {
/*     */                   public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
/* 470 */                     if (event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT) {
/* 471 */                       event.setWillClose(true);
/* 472 */                       event.setWillDestroy(true);
/* 473 */                       String name = event.getName();
/* 474 */                       name = ChatColor.translateAlternateColorCodes('&', name);
/* 476 */                       if (en.getAge() == 10) {
/* 477 */                         ItemAPI.removeName(p.getUniqueId().toString(), "9");
/* 478 */                         ItemAPI.setName(p.getUniqueId().toString(), "9", name);
/* 479 */                         en.setCustomName(name);
/*     */                       } else {
/* 481 */                         ItemAPI.removeName(p.getUniqueId().toString(), "8");
/* 482 */                         ItemAPI.setName(p.getUniqueId().toString(), "8", name);
/* 483 */                         en.setCustomName(name);
/*     */                       } 
/*     */                     } else {
/* 488 */                       event.setWillClose(false);
/* 489 */                       event.setWillDestroy(false);
/*     */                     } 
/*     */                   }
/*     */                 });
/* 494 */             gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, createItem(Material.NAME_TAG, 1, en.getCustomName(), Arrays.asList(new String[] { "" })));
/* 495 */             gui.open();
/* 496 */           } else if (Main.chickens.containsKey(p.getUniqueId())) {
/* 497 */             final RideAbleChicken en = (RideAbleChicken)Main.chickens.get(p.getUniqueId());
/* 498 */             AnvilGUI gui = new AnvilGUI(p, new AnvilGUI.AnvilClickEventHandler() {
/*     */                   public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
/* 502 */                     if (event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT) {
/* 503 */                       event.setWillClose(true);
/* 504 */                       event.setWillDestroy(true);
/* 505 */                       String name = event.getName();
/* 506 */                       name = ChatColor.translateAlternateColorCodes('&', name);
/* 507 */                       RideAbleChicken cen = en;
/* 508 */                       if (cen.getAge() == 10) {
/* 509 */                         ItemAPI.removeName(p.getUniqueId().toString(), "13");
/* 510 */                         ItemAPI.setName(p.getUniqueId().toString(), "13", name);
/* 511 */                         cen.setCustomName(name);
/*     */                       } else {
/* 513 */                         ItemAPI.removeName(p.getUniqueId().toString(), "12");
/* 514 */                         ItemAPI.setName(p.getUniqueId().toString(), "12", name);
/* 515 */                         cen.setCustomName(name);
/*     */                       } 
/*     */                     } else {
/* 521 */                       event.setWillClose(false);
/* 522 */                       event.setWillDestroy(false);
/*     */                     } 
/*     */                   }
/*     */                 });
/* 527 */             gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, createItem(Material.NAME_TAG, 1, en.getCustomName(), Arrays.asList(new String[] { "" })));
/* 528 */             gui.open();
/* 529 */           } else if (Main.cows.containsKey(p.getUniqueId())) {
/* 530 */             final RideAbleCow en = (RideAbleCow)Main.cows.get(p.getUniqueId());
/* 531 */             AnvilGUI gui = new AnvilGUI(p, new AnvilGUI.AnvilClickEventHandler() {
/*     */                   public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
/* 535 */                     if (event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT) {
/* 536 */                       event.setWillClose(true);
/* 537 */                       event.setWillDestroy(true);
/* 538 */                       String name = event.getName();
/* 539 */                       name = ChatColor.translateAlternateColorCodes('&', name);
/* 540 */                       RideAbleCow cen = en;
/* 541 */                       if (cen.getAge() == 10) {
/* 542 */                         ItemAPI.removeName(p.getUniqueId().toString(), "11");
/* 543 */                         ItemAPI.setName(p.getUniqueId().toString(), "11", name);
/* 544 */                         cen.setCustomName(name);
/*     */                       } else {
/* 546 */                         ItemAPI.removeName(p.getUniqueId().toString(), "10");
/* 547 */                         ItemAPI.setName(p.getUniqueId().toString(), "10", name);
/* 548 */                         cen.setCustomName(name);
/*     */                       } 
/*     */                     } else {
/* 554 */                       event.setWillClose(false);
/* 555 */                       event.setWillDestroy(false);
/*     */                     } 
/*     */                   }
/*     */                 });
/* 560 */             gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, createItem(Material.NAME_TAG, 1, en.getCustomName(), Arrays.asList(new String[] { "" })));
/* 561 */             gui.open();
/* 562 */           } else if (Main.guardians.containsKey(p.getUniqueId())) {
/* 563 */             final RideAbleGuardian en = (RideAbleGuardian)Main.guardians.get(p.getUniqueId());
/* 564 */             AnvilGUI gui = new AnvilGUI(p, new AnvilGUI.AnvilClickEventHandler() {
/*     */                   public void onAnvilClick(AnvilGUI.AnvilClickEvent event) {
/* 568 */                     if (event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT) {
/* 569 */                       event.setWillClose(true);
/* 570 */                       event.setWillDestroy(true);
/* 571 */                       String name = event.getName();
/* 572 */                       name = ChatColor.translateAlternateColorCodes('&', name);
/* 573 */                       RideAbleGuardian gen = en;
/* 574 */                       ItemAPI.removeName(p.getUniqueId().toString(), "14");
/* 575 */                       ItemAPI.setName(p.getUniqueId().toString(), "14", name);
/* 576 */                       gen.setCustomName(name);
/*     */                     } else {
/* 580 */                       event.setWillClose(false);
/* 581 */                       event.setWillDestroy(false);
/*     */                     } 
/*     */                   }
/*     */                 });
/* 586 */             gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, createItem(Material.NAME_TAG, 1, en.getCustomName(), Arrays.asList(new String[] { "" })));
/* 587 */             gui.open();
/*     */           }  
/* 590 */         if (e.getCurrentItem().getItemMeta().getDisplayName() == "§5Reite dein Pet") {
/* 592 */           if (Main.pigs.containsKey(p.getUniqueId())) {
/* 593 */             RideAblePig ep = (RideAblePig)Main.pigs.get(p.getUniqueId());
/* 594 */             p.closeInventory();
/* 595 */             ep.getBukkitEntity().setPassenger(p);
/* 596 */             ep.passenger = (Entity)((CraftPlayer)p).getHandle();
/*     */           } 
/* 598 */           if (Main.cows.containsKey(p.getUniqueId())) {
/* 599 */             RideAbleCow ep = (RideAbleCow)Main.cows.get(p.getUniqueId());
/* 600 */             p.closeInventory();
/* 601 */             ep.getBukkitEntity().setPassenger(p);
/* 602 */             ep.passenger = (Entity)((CraftPlayer)p).getHandle();
/*     */           } 
/* 604 */           if (Main.chickens.containsKey(p.getUniqueId())) {
/* 605 */             RideAbleChicken ep = (RideAbleChicken)Main.chickens.get(p.getUniqueId());
/* 606 */             p.closeInventory();
/* 607 */             ep.getBukkitEntity().setPassenger(p);
/* 608 */             ep.passenger = (Entity)((CraftPlayer)p).getHandle();
/*     */           } 
/* 610 */           if (Main.guardians.containsKey(p.getUniqueId())) {
/* 611 */             RideAbleGuardian ep = (RideAbleGuardian)Main.guardians.get(p.getUniqueId());
/* 612 */             p.closeInventory();
/* 613 */             ep.getBukkitEntity().setPassenger(p);
/* 614 */             ep.passenger = (Entity)((CraftPlayer)p).getHandle();
/*     */           } 
/* 617 */           p.closeInventory();
/*     */         } 
/* 619 */         if (e.getCurrentItem().getItemMeta().getDisplayName() == "§cPets Deaktivieren") {
/* 620 */           for (Player on : Bukkit.getOnlinePlayers()) {
/* 621 */             if (Main.pigs.containsKey(on.getUniqueId())) {
/* 622 */               Main.hideEntity(p, (Entity)Main.pigs.get(on.getUniqueId()));
/* 623 */               ItemAPI.setHide(p.getUniqueId().toString(), true);
/*     */             } 
/* 625 */             if (Main.cows.containsKey(on.getUniqueId())) {
/* 626 */               Main.hideEntity(p, (Entity)Main.cows.get(on.getUniqueId()));
/* 627 */               ItemAPI.setHide(p.getUniqueId().toString(), true);
/*     */             } 
/* 629 */             if (Main.chickens.containsKey(on.getUniqueId())) {
/* 630 */               Main.hideEntity(p, (Entity)Main.chickens.get(on.getUniqueId()));
/* 631 */               ItemAPI.setHide(p.getUniqueId().toString(), true);
/*     */             } 
/* 633 */             if (Main.guardians.containsKey(on.getUniqueId())) {
/* 634 */               Main.hideEntity(p, (Entity)Main.guardians.get(on.getUniqueId()));
/* 635 */               ItemAPI.setHide(p.getUniqueId().toString(), true);
/*     */             } 
/*     */           } 
/* 638 */           p.closeInventory();
/*     */         } 
/* 640 */         if (e.getCurrentItem().getItemMeta().getDisplayName() == "§aPets Aktivieren") {
/* 641 */           for (Player on : Bukkit.getOnlinePlayers()) {
/* 642 */             if (Main.pigs.containsKey(on.getUniqueId())) {
/* 643 */               Main.showEntity(p, (EntityLiving)Main.pigs.get(on.getUniqueId()));
/* 644 */               ItemAPI.setHide(p.getUniqueId().toString(), false);
/*     */             } 
/* 646 */             if (Main.cows.containsKey(on.getUniqueId())) {
/* 647 */               Main.showEntity(p, (EntityLiving)Main.cows.get(on.getUniqueId()));
/* 648 */               ItemAPI.setHide(p.getUniqueId().toString(), false);
/*     */             } 
/* 650 */             if (Main.chickens.containsKey(on.getUniqueId())) {
/* 651 */               Main.showEntity(p, (EntityLiving)Main.chickens.get(on.getUniqueId()));
/* 652 */               ItemAPI.setHide(p.getUniqueId().toString(), false);
/*     */             } 
/* 654 */             if (Main.guardians.containsKey(on.getUniqueId())) {
/* 655 */               Main.showEntity(p, (EntityLiving)Main.guardians.get(on.getUniqueId()));
/* 656 */               ItemAPI.setHide(p.getUniqueId().toString(), false);
/*     */             } 
/*     */           } 
/* 659 */           p.closeInventory();
/*     */         } 
/* 662 */         e.setCancelled(true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onClick(PlayerInteractEvent e) {
/* 670 */     Player p = e.getPlayer();
/* 671 */     if (e.getAction() != null && (
/* 672 */       e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && 
/* 673 */       p.getItemInHand().hasItemMeta() && 
/* 674 */       p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§fItems")) {
/* 675 */       Inventory i = p.getPlayer().getServer().createInventory(null, 27, "§fItems");
/* 676 */       for (int f = 0; f <= 26; f++) {
/* 677 */         i.setItem(f, createItem2(Material.STAINED_GLASS_PANE, (short)15, 1, "§c-", Arrays.asList(new String[] { "" })));
/*     */       } 
/* 679 */       i.setItem(12, createItem3(Material.LEATHER_BOOTS, Color.RED, 1, "§cDeine Schuhe", Arrays.asList(new String[] { "" })));
/* 680 */       i.setItem(14, createItem2(Material.MONSTER_EGG, (short)90, 1, "§aDeine Pets", Arrays.asList(new String[] { "" })));
/* 681 */       if (ItemAPI.getHide(p.getUniqueId().toString())) {
/* 682 */         i.setItem(18, createItem(Material.GLOWSTONE_DUST, 1, "§aPets Aktivieren", Arrays.asList(new String[] { "" })));
/*     */       } else {
/* 684 */         i.setItem(18, createItem(Material.REDSTONE, 1, "§cPets Deaktivieren", Arrays.asList(new String[] { "" })));
/*     */       } 
/* 686 */       p.openInventory(i);
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onEntityClick(PlayerInteractAtEntityEvent e) {
/* 695 */     CraftEntity en = (CraftEntity)e.getRightClicked();
/* 696 */     Player p = e.getPlayer();
/* 697 */     if (Main.pigs.containsKey(p.getUniqueId()) || Main.cows.containsKey(p.getUniqueId()) || Main.chickens.containsKey(p.getUniqueId()) || Main.guardians.containsKey(p.getUniqueId())) {
/* 698 */       if (en.getCustomName() == null)
/*     */         return; 
/* 701 */       Inventory inv = Bukkit.createInventory(null, 27, en.getCustomName());
/* 702 */       for (int f = 0; f <= 26; f++) {
/* 703 */         inv.setItem(f, createItem2(Material.STAINED_GLASS_PANE, (short)15, 1, "§c-", Arrays.asList(new String[] { "" })));
/*     */       } 
/* 705 */       inv.setItem(12, createItem(Material.NAME_TAG, 1, "§aName Ã¤ndern", Arrays.asList(new String[] { "" })));
/* 706 */       inv.setItem(14, createItem(Material.SADDLE, 1, "§5Reite dein Pet", Arrays.asList(new String[] { "" })));
/* 707 */       p.openInventory(inv);
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onJoin(PlayerJoinEvent e) {
/* 713 */     final Player p = e.getPlayer();
/* 714 */     final ItemStack i = new ItemStack(Material.CHEST);
/* 715 */     ItemMeta im = i.getItemMeta();
/* 716 */     im.setDisplayName("§fItems");
/* 717 */     i.setItemMeta(im);
/* 718 */     (new BukkitRunnable() {
/*     */         public void run() {
/* 723 */           p.getInventory().setItem(5, i);
/*     */         }
/* 725 */       }).runTaskLater((Plugin)Main.getInstance(), 3L);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onMove(PlayerMoveEvent e) {
/* 730 */     Player p = e.getPlayer();
/* 731 */     if (p.getInventory().getBoots() != null && 
/* 732 */       p.getInventory().getBoots().hasItemMeta()) {
/* 733 */       if (p.getInventory().getBoots().getItemMeta().getDisplayName().equalsIgnoreCase("§cFeuer Schuhe ")) {
/* 734 */         Location loc = p.getLocation();
/* 735 */         PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(EnumParticle.FLAME, true, (float)loc.getX(), (float)(loc.getY() + 0.2D), (float)loc.getZ(), 0.0F, 0.0F, 0.0F, 0.0F, 5, new int[] { 5 });
/* 737 */         for (Player g : Bukkit.getOnlinePlayers())
/* 738 */           (((CraftPlayer)g).getHandle()).playerConnection.sendPacket((Packet<?>)particles); 
/*     */       } 
/* 741 */       if (p.getInventory().getBoots().getItemMeta().getDisplayName().equalsIgnoreCase("§5Liebes Schuhe "))
/* 742 */         p.getWorld().playEffect(p.getLocation().add(0.0D, 0.1D, 0.0D), Effect.HEART, 2); 
/* 744 */       if (p.getInventory().getBoots().getItemMeta().getDisplayName().equalsIgnoreCase("§8Rauch Schuhe ")) {
/* 745 */         Location loc = p.getLocation();
/* 746 */         PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(EnumParticle.SMOKE_LARGE, true, (float)loc.getX(), (float)(loc.getY() + 0.2D), (float)loc.getZ(), 0.0F, 0.0F, 0.0F, 0.0F, 5, new int[] { 5 });
/* 748 */         for (Player g : Bukkit.getOnlinePlayers())
/* 749 */           (((CraftPlayer)g).getHandle()).playerConnection.sendPacket((Packet<?>)particles); 
/*     */       } 
/* 752 */       if (p.getInventory().getBoots().getItemMeta().getDisplayName().equalsIgnoreCase("§bRegen Schuhe "))
/* 753 */         p.getWorld().playEffect(p.getLocation().add(0.0D, 0.1D, 0.0D), Effect.WATERDRIP, 1); 
/* 755 */       if (p.getInventory().getBoots().getItemMeta().getDisplayName().equalsIgnoreCase("§aMusik Schuhe "))
/* 756 */         p.getWorld().playEffect(p.getLocation().add(0.0D, 0.1D, 0.0D), Effect.NOTE, 100); 
/* 758 */       if (p.getInventory().getBoots().getItemMeta().getDisplayName().equalsIgnoreCase("§6Lava Schuhe "))
/* 759 */         p.getWorld().playEffect(p.getLocation().add(0.0D, 0.1D, 0.0D), Effect.LAVA_POP, 3); 
/* 761 */       if (p.getInventory().getBoots().getItemMeta().getDisplayName().equalsIgnoreCase("§7Geister Schuhe ")) {
/* 762 */         Location loc = p.getLocation();
/* 763 */         PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(EnumParticle.SMOKE_NORMAL, true, (float)loc.getX(), (float)(loc.getY() + 0.2D), (float)loc.getZ(), 0.0F, 0.0F, 0.0F, 0.0F, 5, new int[] { 5 });
/* 765 */         for (Player g : Bukkit.getOnlinePlayers())
/* 766 */           (((CraftPlayer)g).getHandle()).playerConnection.sendPacket((Packet<?>)particles); 
/* 769 */         if (p.isSneaking()) {
/* 770 */           for (Player all : Bukkit.getOnlinePlayers()) {
/* 772 */             all.hidePlayer(p);
/* 773 */             p.hidePlayer(p);
/*     */           } 
/*     */         } else {
/* 776 */           for (Player all : Bukkit.getOnlinePlayers()) {
/* 778 */             all.showPlayer(p);
/* 779 */             p.showPlayer(p);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static ItemStack createItem3(Material mat, Color c, int anzahl, String name, List<String> lore) {
/* 787 */     ItemStack i = new ItemStack(mat, anzahl);
/* 788 */     i.setAmount(anzahl);
/* 789 */     LeatherArmorMeta m = (LeatherArmorMeta)i.getItemMeta();
/* 790 */     m.setLore(lore);
/* 791 */     m.setColor(c);
/* 792 */     m.setDisplayName(name);
/* 793 */     i.setItemMeta((ItemMeta)m);
/* 794 */     return i;
/*     */   }
/*     */   
/*     */   private static ItemStack createItem2(Material mat, short h, int anzahl, String name, List<String> lore) {
/* 797 */     ItemStack i = new ItemStack(mat, anzahl, h);
/* 798 */     i.setAmount(anzahl);
/* 799 */     ItemMeta m = i.getItemMeta();
/* 800 */     m.setDisplayName(name);
/* 801 */     m.setLore(lore);
/* 802 */     i.setItemMeta(m);
/* 803 */     return i;
/*     */   }
/*     */   
/*     */   private static ItemStack createItem(Material mat, int anzahl, String name, List<String> lore) {
/* 806 */     ItemStack i = new ItemStack(mat, anzahl);
/* 807 */     i.setAmount(anzahl);
/* 808 */     ItemMeta m = i.getItemMeta();
/* 809 */     m.setDisplayName(name);
/* 810 */     m.setLore(lore);
/* 811 */     i.setItemMeta(m);
/* 812 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\Users\jarja\Desktop\Plugins\Pls\PLobby\plugins\ItemSystem.jar!\de\padel\itemsystem\listerner\InvListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */