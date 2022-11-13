package de.padel.buildffa.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import de.padel.buildffa.utils.Utils;

public class BasicListener implements Listener {

	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		e.setExpToDrop(0);
		if (Utils.build.contains(e.getPlayer().getUniqueId())) {
			e.setCancelled(false);
		} else {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if (Utils.build.contains(e.getPlayer().getUniqueId())) {
			e.setCancelled(false);
		} else {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPickUp(PlayerPickupItemEvent e) {
		if (Utils.build.contains(e.getPlayer().getUniqueId())) {
			e.setCancelled(false);
		} else {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onOpen(InventoryOpenEvent e) {
		if (e.getInventory().getTitle() == "Chest")
			e.setCancelled(true);
		if (e.getInventory().getType() == InventoryType.ENDER_CHEST)
			e.setCancelled(true);
		if (e.getInventory().getType() == InventoryType.ANVIL)
			e.setCancelled(true);
		if (e.getInventory().getType() == InventoryType.BEACON)
			e.setCancelled(true);
	}

	@EventHandler
	public void onXP(PlayerLevelChangeEvent e) {
		e.getPlayer().setLevel(0);
	}
}
