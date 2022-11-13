package de.padel.coinsys.inv;

import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import org.bukkit.Color;
/*    */ import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ import org.bukkit.inventory.meta.LeatherArmorMeta;

import de.padel.coinsys.main.Main;

/*    */
/*    */ public class ShopInv {
	/*    */ public static void loadInv(Player p) {
		/* 20 */ if (Main.isMySQLEnabled()) {
			/* 21 */ Inventory i = p.getPlayer().getServer().createInventory(null, 27, "§cShop");
			/* 22 */ for (int f = 0; f <= 26; f++) {
				/* 23 */ i.setItem(f, createItem2(Material.STAINED_GLASS_PANE, (short) 15, 1, "§c-",
						Arrays.asList(new String[] { "" })));
				/*    */ }
			/* 25 */ i.setItem(12,
					createItem3(Material.LEATHER_BOOTS, Color.RED, 1, "§cSchuhe", Arrays.asList(new String[] { "" })));
					 i.setItem(13, createItem(Material.SANDSTONE, 1, "§eBuildFFA", Arrays.asList("")));
			/* 26 */ i.setItem(14,
					createItem2(Material.MONSTER_EGG, (short) 90, 1, "§aPets", Arrays.asList(new String[] { "" })));
			/* 27 */ i.setItem(15,
					createItem2(Material.STAINED_GLASS_PANE, (short) 15, 1, "§c-", Arrays.asList(new String[] { "" })));
			/* 28 */ p.openInventory(i);
			/*    */ }
		/*    */ }

	/*    */
	/*    */ public static void loadInvBoots(Player p) {
		/* 32 */ if (Main.isMySQLEnabled()) {
			/* 33 */ Inventory i = p.getPlayer().getServer().createInventory(null, 27, "§cSchuhe");
			/* 34 */ for (int f = 0; f <= 26; f++) {
				/* 35 */ i.setItem(f, createItem2(Material.STAINED_GLASS_PANE, (short) 15, 1, "§c-",
						Arrays.asList(new String[] { "" })));
				/*    */ }
			/* 37 */ i.setItem(10, createItem3(Material.LEATHER_BOOTS, Color.RED, 1, "§cFeuer Schuhe",
					Arrays.asList(new String[] { "§6600 Coins" })));
			/* 38 */ i.setItem(11, createItem3(Material.LEATHER_BOOTS, Color.PURPLE, 1, "§5Liebes Schuhe",
					Arrays.asList(new String[] { "§6600 Coins" })));
			/* 39 */ i.setItem(12, createItem3(Material.LEATHER_BOOTS, Color.GRAY, 1, "§8Rauch Schuhe",
					Arrays.asList(new String[] { "§6600 Coins" })));
			/* 40 */ i.setItem(13, createItem3(Material.LEATHER_BOOTS, Color.AQUA, 1, "§bRegen Schuhe",
					Arrays.asList(new String[] { "§6600 Coins" })));
			/* 41 */ i.setItem(14, createItem3(Material.LEATHER_BOOTS, Color.LIME, 1, "§aMusik Schuhe",
					Arrays.asList(new String[] { "§6600 Coins" })));
			/* 42 */ i.setItem(15, createItem3(Material.LEATHER_BOOTS, Color.ORANGE, 1, "§6Lava Schuhe",
					Arrays.asList(new String[] { "§6600 Coins" })));
			/* 43 */ i.setItem(16, createItem3(Material.LEATHER_BOOTS, Color.SILVER, 1, "§7Geister Schuhe",
					Arrays.asList(new String[] { "§6900 Coins" })));
			/* 44 */ i.setItem(18, createItem2(Material.STAINED_GLASS_PANE, (short) 14, 1, "§cZurück",
					Arrays.asList(new String[] { "" })));
			/* 47 */ p.openInventory(i);
			/*    */ }
		/*    */ }

	/*    */
	/*    */ public static void loadInvEGG(Player p) {
		/* 51 */ if (Main.isMySQLEnabled()) {
			/* 52 */ Inventory i = p.getPlayer().getServer().createInventory(null, 27, "§aPets");
			/* 53 */ for (int f = 0; f <= 26; f++) {
				/* 54 */ i.setItem(f, createItem2(Material.STAINED_GLASS_PANE, (short) 15, 1, "§c-",
						Arrays.asList(new String[] { "" })));
				/*    */ }
			/* 56 */ i.setItem(10, createItem2(Material.MONSTER_EGG, (short) 90, 1, "§dBaby-Schwein",
					Arrays.asList(new String[] { "§6800 Coins" })));
			/* 57 */ i.setItem(11, createItem2(Material.MONSTER_EGG, (short) 90, 1, "§dSchwein",
					Arrays.asList(new String[] { "§6600 Coins" })));
			/* 58 */ i.setItem(12, createItem2(Material.MONSTER_EGG, (short) 92, 1, "§7Baby-Kuh",
					Arrays.asList(new String[] { "§6800 Coins" })));
			/* 59 */ i.setItem(13, createItem2(Material.MONSTER_EGG, (short) 92, 1, "§7Kuh",
					Arrays.asList(new String[] { "§6600 Coins" })));
			/* 60 */ i.setItem(14, createItem2(Material.MONSTER_EGG, (short) 93, 1, "§fHühnchen",
					Arrays.asList(new String[] { "§6800 Coins" })));
			/* 61 */ i.setItem(15, createItem2(Material.MONSTER_EGG, (short) 93, 1, "§fHuhn",
					Arrays.asList(new String[] { "§6800 Coins" })));
			/* 62 */ i.setItem(16, createItem2(Material.MONSTER_EGG, (short) 68, 1, "§bWächter",
					Arrays.asList(new String[] { "§61200 Coins" })));
			/* 63 */ i.setItem(18, createItem2(Material.STAINED_GLASS_PANE, (short) 14, 1, "§cZurück",
					Arrays.asList(new String[] { "" })));
			/* 65 */ p.openInventory(i);
			/*    */ }
		/*    */ }

	// Sticks: Stock, Knochen, Feder, Blazerod, hoe, poppy oder red tulip, Schere
	// Blöcke: Smooth sandstone, Bricks, Glowstone, red sandstone, quarz block, endstone block, eisen block, dia block, emerald block, redstone block
	// rüstungs farben: Lime, white, black, light blue
	// kill sounds: Villager hit, broken anvil, broken item, donner

	// Selten, Episch, Legendär

	public static void loadInvBFFA(Player p) {
		/* 51 */ if (Main.isMySQLEnabled()) {
			/* 52 */ Inventory i = p.getPlayer().getServer().createInventory(null, 27, "§eBuildFFA");
			/* 53 */ for (int f = 0; f <= 26; f++) {
				/* 54 */ i.setItem(f, createItem2(Material.STAINED_GLASS_PANE, (short) 15, 1, "§c-",
						Arrays.asList(new String[] { "" })));
				/*    */ }
			/* 56 */ i.setItem(11, createItem(Material.GOLD_SWORD, 1, "§6Schwerter", Arrays.asList("")));
			i.setItem(12, createItem(Material.STICK, 1, "§cSticks", Arrays.asList("")));
			i.setItem(13, createItem(Material.NOTE_BLOCK, 1, "§fKillsound", Arrays.asList("")));
			i.setItem(14, createItem(Material.LEATHER_CHESTPLATE, 1, "§aRüstungsfarbe", Arrays.asList("")));
			i.setItem(15, createItem2(Material.SANDSTONE, (short) 2, 64, "§eBlöcke", Arrays.asList("")));
			i.setItem(18, createItem2(Material.STAINED_GLASS_PANE, (short) 14, 1, "§cZurück",
					Arrays.asList(new String[] { "" })));
			/* 65 */ p.openInventory(i);
			/*    */ }
		/*    */ }

	public static void loadInvBFFASword(Player p) {
		/* 51 */ if (Main.isMySQLEnabled()) {
			/* 52 */ Inventory i = p.getPlayer().getServer().createInventory(null, 27, "§6Schwerter");
			/* 53 */ for (int f = 0; f <= 26; f++) {
				/* 54 */ i.setItem(f, createItem2(Material.STAINED_GLASS_PANE, (short) 15, 1, "§c-",
						Arrays.asList(new String[] { "" })));
				/*    */ }
			/* 56 */ i.setItem(12, createItem(Material.GOLD_SWORD, 1, "§6Goldschwert", Arrays.asList("§7Normal")));
			/* 57 */ i.setItem(14,
					createItem(Material.WOOD_SWORD, 1, "§cHolzschwert", Arrays.asList("§63000 Coins", "§6Legendär")));
			/* 63 */ i.setItem(18,
					createItem2(Material.STAINED_GLASS_PANE, (short) 14, 1, "§cZurück", Arrays.asList("")));
			/* 65 */ p.openInventory(i);
			/*    */ }
		/*    */ }

	/*    */ public static void loadInvBFFASound(Player p) {
		/* 51 */ if (Main.isMySQLEnabled()) {
			/* 52 */ Inventory i = p.getPlayer().getServer().createInventory(null, 27, "§fKillsound");
			/* 53 */ for (int f = 0; f <= 26; f++) {
				/* 54 */ i.setItem(f, createItem2(Material.STAINED_GLASS_PANE, (short) 15, 1, "§c-",
						Arrays.asList(new String[] { "" })));
				/*    */ }
			/* 56 */ i.setItem(10,
					createItem(Material.NOTE_BLOCK, 1, "§cVillager Tod", Arrays.asList("§61000 Coins", "§1Selten")));
			/* 58 */ i.setItem(12, createItem(Material.NOTE_BLOCK, 1, "§7Sound, wenn ein Anvil kaputt geht",
					Arrays.asList("§61000 Coins", "§1Selten")));
			/* 60 */ i.setItem(14, createItem(Material.NOTE_BLOCK, 1, "§aSound wenn das Item kaputt geht",
					Arrays.asList("§61000 Coins", "§5Episch")));
			/* 62 */ i.setItem(16,
					createItem(Material.NOTE_BLOCK, 1, "§1Donner", Arrays.asList("§61000 Coins", "§6Legendär")));
			/* 63 */ i.setItem(18, createItem2(Material.STAINED_GLASS_PANE, (short) 14, 1, "§cZurück",
					Arrays.asList(new String[] { "" })));
			/* 65 */ p.openInventory(i);
			/*    */ }
		/*    */ }

	/*    */ public static void loadInvBFFARuestungColor(Player p) {
		/* 51 */ if (Main.isMySQLEnabled()) {
			/* 52 */ Inventory i = p.getPlayer().getServer().createInventory(null, 27, "§aRüstungsfarbe");
			/* 53 */ for (int f = 0; f <= 26; f++) {
				/* 54 */ i.setItem(f, createItem2(Material.STAINED_GLASS_PANE, (short) 15, 1, "§c-",
						Arrays.asList(new String[] { "" })));
				/*    */ }
			/* 56 */ i.setItem(10, createItem3(Material.LEATHER_CHESTPLATE, Color.AQUA, 1, "§bBlau",
					Arrays.asList("§6400Coins", "§1Selten")));
			/* 58 */ i.setItem(12, createItem3(Material.LEATHER_CHESTPLATE, Color.BLACK, 1, "§0Schwarz",
					Arrays.asList("§6800 Coins", "§5Episch")));
			/* 60 */ i.setItem(14, createItem3(Material.LEATHER_CHESTPLATE, Color.WHITE, 1, "§fWeiß",
					Arrays.asList("§6800 Coins", "§5Episch")));
			/* 62 */ i.setItem(16, createItem3(Material.LEATHER_CHESTPLATE, Color.LIME, 1, "§aGrün",
					Arrays.asList("§61200 Coins", "§6Legendär")));
			i.setItem(18, createItem2(Material.STAINED_GLASS_PANE, (short) 14, 1, "§cZurück",
					Arrays.asList(new String[] { "" })));
			/* 65 */ p.openInventory(i);
			/*    */ }
		/*    */ }

	/*    */ public static void loadInvBFFASticks(Player p) {
		/* 51 */ if (Main.isMySQLEnabled()) {
			/* 52 */ Inventory i = p.getPlayer().getServer().createInventory(null, 27, "§cSticks");
			/* 53 */ for (int f = 0; f <= 26; f++) {
				/* 54 */ i.setItem(f, createItem2(Material.STAINED_GLASS_PANE, (short) 15, 1, "§c-",
						Arrays.asList(new String[] { "" })));
				/*    */ }
			i.setItem(10, createItem(Material.STICK, 1, "§7Stick", Arrays.asList("§7Normal")));
			
			i.setItem(11, createItem(Material.BONE, 1, "§fKnochen", Arrays.asList("§61000 Coins", "§1Selten")));
			i.setItem(12, createItem(Material.FEATHER, 1, "§fFeder", Arrays.asList("§61000 Coins", "§1Selten")));
			
			i.setItem(13, createItem(Material.BLAZE_ROD, 1, "§eBlaze Rod", Arrays.asList("§61500 Coins", "§5Episch")));
			i.setItem(14, createItem(Material.WOOD_HOE, 1, "§cHolzhoe", Arrays.asList("§61500 Coins", "§5Episch")));
			
			i.setItem(15, createItem(Material.RED_ROSE, 1, "§crote Rose", Arrays.asList("§62000 Coins", "§6Legendär")));
			i.setItem(16, createItem(Material.SHEARS, 1, "§7Schere", Arrays.asList("§62000 Coins", "§6Legendär")));
			
			/* 63 */ i.setItem(18, createItem2(Material.STAINED_GLASS_PANE, (short) 14, 1, "§cZurück",
					Arrays.asList(new String[] { "" })));
			/* 65 */ p.openInventory(i);
			/*    */ }
		/*    */ }

	/*    */ public static void loadInvBFFABlocks(Player p) {
		/* 51 */ if (Main.isMySQLEnabled()) {
			/* 52 */ Inventory i = p.getPlayer().getServer().createInventory(null, 27, "§eBlöcke");
			/* 53 */ for (int f = 0; f <= 26; f++) {
				/* 54 */ i.setItem(f, createItem2(Material.STAINED_GLASS_PANE, (short) 15, 1, "§c-",
						Arrays.asList(new String[] { "" })));
				/*    */ }
			i.setItem(9, createItem(Material.RED_SANDSTONE, 1, "§croter Sandstein", Arrays.asList("§6800 Coins", "§1Selten")));
			i.setItem(10, createItem(Material.BRICK, 1, "§cBrick", Arrays.asList("§61000 Coins", "§1Selten")));
			i.setItem(11, createItem(Material.ENDER_STONE, 1, "§eEndstone", Arrays.asList("§61000 Coins", "§1Selten")));
			
			i.setItem(12, createItem(Material.QUARTZ_BLOCK, 1, "§fQuarz Block", Arrays.asList("§61200 Coins", "§5Episch")));
			i.setItem(13, createItem(Material.GLOWSTONE, 1, "§eGlowstone", Arrays.asList("§61200 Coins", "§5Episch")));
			i.setItem(14, createItem(Material.IRON_BLOCK, 1, "§7Eisen Block", Arrays.asList("§61500 Coins", "§5Episch")));
			
			i.setItem(15, createItem(Material.EMERALD_BLOCK, 1, "§2Emerald Block", Arrays.asList("§61800 Coins", "§6Legendär")));
			i.setItem(16, createItem(Material.REDSTONE_BLOCK, 1, "§cRedstone Block", Arrays.asList("§61800 Coins", "§6Legendär")));
			i.setItem(17, createItem(Material.DIAMOND_BLOCK, 1, "§bDiamant Block", Arrays.asList("§62000 Coins", "§6Legendär")));
			/* 63 */ i.setItem(18, createItem2(Material.STAINED_GLASS_PANE, (short) 14, 1, "§cZurück",
					Arrays.asList(new String[] { "" })));
			/* 65 */ p.openInventory(i);
			/*    */ }
		/*    */ }

	/*    */
	/*    */ private static ItemStack createItem2(Material mat, short h, int anzahl, String name, List<String> lore) {
		/* 69 */ ItemStack i = new ItemStack(mat, anzahl, h);
		/* 70 */ i.setAmount(anzahl);
		/* 71 */ ItemMeta m = i.getItemMeta();
		/* 72 */ m.setDisplayName(name);
		/* 73 */ m.setLore(lore);
		/* 74 */ i.setItemMeta(m);
		/* 75 */ return i;
		/*    */ }

	private static ItemStack createItem(Material mat, int anzahl, String name, List<String> lore) {
		/* 69 */ ItemStack i = new ItemStack(mat, anzahl);
		/* 70 */ i.setAmount(anzahl);
		/* 71 */ ItemMeta m = i.getItemMeta();
		/* 72 */ m.setDisplayName(name);
		/* 73 */ m.setLore(lore);
		/* 74 */ i.setItemMeta(m);
		/* 75 */ return i;
		/*    */ }

	/*    */
	/*    */ private static ItemStack createItem3(Material mat, Color c, int anzahl, String name, List<String> lore) {
		/* 78 */ ItemStack i = new ItemStack(mat, anzahl);
		/* 79 */ i.setAmount(anzahl);
		/* 80 */ LeatherArmorMeta m = (LeatherArmorMeta) i.getItemMeta();
		/* 81 */ m.setLore(lore);
		/* 82 */ m.setColor(c);
		/* 83 */ m.setDisplayName(name);
		/* 84 */ m.spigot().setUnbreakable(true);
		/* 85 */ i.setItemMeta((ItemMeta) m);
		/* 86 */ return i;
		/*    */ }

	public static ItemStack createEnchantedItem(Material mat, int anzahl, String name, Enchantment en, int power) {
		ItemStack i = new ItemStack(mat, anzahl);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(name);
		m.addEnchant(en, power, true);
		m.spigot().setUnbreakable(true);
		i.setItemMeta(m);
		return i;
	}

	public static ItemStack createEnchantedItem2(Material mat, int anzahl, String name, Enchantment en, int power,
			Enchantment en2, int power2) {
		ItemStack i = new ItemStack(mat, anzahl);
		ItemMeta m = i.getItemMeta();
		m.setDisplayName(name);
		m.addEnchant(en, power, true);
		m.addEnchant(en2, power2, true);
		m.spigot().setUnbreakable(true);
		i.setItemMeta(m);
		return i;
	}
	/*    */ }
