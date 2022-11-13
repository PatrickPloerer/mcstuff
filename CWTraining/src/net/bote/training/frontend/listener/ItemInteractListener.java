package net.bote.training.frontend.listener;

import com.google.common.collect.Maps;

import net.bote.training.util.CustomSophia;
import net.bote.training.util.transport.InventoryClickTransporter;
import net.bote.training.util.transport.ItemInteractTransporter;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;

/**
 * @author Elias Arndt | bote100
 * Created on 05.10.2019
 */

public final class ItemInteractListener implements Listener {

    private static HashMap<String, ItemInteractTransporter> interactItemAction = Maps.newHashMap();

    private static HashMap<String, InventoryClickTransporter> clickItemAction = Maps.newHashMap();

    @EventHandler
    public void on(PlayerInteractEvent e) {
        try {
            if (e.getPlayer().getItemInHand() != null)
                if (e.getPlayer().getItemInHand().getItemMeta() != null)
                    if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName() != null)
                        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                        	if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals("Sophi‰‰‰‰‰‰‰")) {
                        		CustomSophia.SpawnSophi‰(e.getPlayer().getLocation(), e.getPlayer());
                        	}
                            if (interactItemAction.containsKey(e.getPlayer().getItemInHand().getItemMeta().getDisplayName() + "-" + e.getPlayer().getUniqueId().toString())) {
                                e.setCancelled(true);
                                interactItemAction.get(e.getPlayer().getItemInHand().getItemMeta().getDisplayName() + "-" + e.getPlayer().getUniqueId().toString()).exec(e);
                            }
                        }
        } catch (NullPointerException ignored) { }
    }

    @EventHandler
    public void on(InventoryClickEvent e) {
        try {
            if (e.getCurrentItem().getItemMeta() != null)
                if (e.getCurrentItem().getItemMeta().getDisplayName() != null)
                    if (clickItemAction.containsKey(e.getCurrentItem().getItemMeta().getDisplayName() + "-" + e.getWhoClicked().getUniqueId().toString())) {
                        e.setCancelled(true);
                        clickItemAction.get(e.getCurrentItem().getItemMeta().getDisplayName() + "-" + e.getWhoClicked().getUniqueId().toString()).exec(e);
                    }
        } catch (NullPointerException ignored) {
        }
    }

	public static HashMap<String, ItemInteractTransporter> getInteractItemAction() {
		return interactItemAction;
	}

	public static HashMap<String, InventoryClickTransporter> getClickItemAction() {
		return clickItemAction;
	}
	// TODO sheep name Sophi‰‰‰‰‰‰‰
}
