package net.bote.training.frontend.listener;

import net.bote.training.Training;
import net.bote.training.backend.enums.GameState;
import net.bote.training.backend.enums.SpawnerType;
import net.bote.training.backend.enums.SpectatorState;
import net.bote.training.frontend.commands.ConfigureMapCommand;
import net.bote.training.util.ItemBuilder;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import java.util.Arrays;

/**
 * @author Elias Arndt | bote100
 * Created on 29.01.2020
 */

public final class PickUpListener implements Listener {

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent e) {
        if (ConfigureMapCommand.enabled) return;
        if(!Training.getInstance().getController().getCWTPlayer(e.getPlayer()).getSpectatorState().equals(SpectatorState.NONE)
                || Training.getInstance().getController().getCurrentState().equals(GameState.LOBBY)) {
            e.setCancelled(true);
            return;
        }

        try {

            Arrays.stream(SpawnerType.values()).forEach(spawner -> {
                if(e.getItem().getItemStack().getType().equals(spawner.getMaterial())) {
                    e.setCancelled(true);
                    e.getItem().remove();
                    e.getPlayer().getInventory().addItem(new ItemBuilder(spawner.getMaterial(), e.getItem().getItemStack().getAmount()).setName(spawner.getName()).build());
                    e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ITEM_PICKUP, 2, 3);
                }
            });
        } catch (NullPointerException ignored) {}
    }

}
