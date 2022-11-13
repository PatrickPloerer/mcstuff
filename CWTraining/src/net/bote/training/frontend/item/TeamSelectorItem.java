package net.bote.training.frontend.item;

import com.google.common.collect.Lists;
import net.bote.training.Training;
import net.bote.training.util.ItemBuilder;
import net.bote.training.util.transport.InventoryClickTransporter;
import net.bote.training.util.transport.ItemInteractTransporter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.List;

/**
 * @author Elias Arndt | bote100
 * Created on 24.01.2020
 */

public class TeamSelectorItem extends ItemInteractTransporter {

    private final Player player;
    
    public TeamSelectorItem(Player p) {
    	player = p;
    }

    @Override
    public void exec(PlayerInteractEvent event) {
        Inventory inventory = Bukkit.createInventory(null, 9, Training.getInstance().getMessageService().getMessage("lobby.item.team"));

        List<String> listBlue = Lists.newArrayList();
        Training.getInstance().getTeamBlue().getMembers().forEach(member -> listBlue.add("§7- §9" + member.getPlayer().getName()));

        List<String> listRed = Lists.newArrayList();
        Training.getInstance().getTeamRed().getMembers().forEach(member -> listRed.add("§7- §c" + member.getPlayer().getName()));

        inventory.setItem(3, new ItemBuilder(Material.WOOL, 1, 11)
                .setName(Training.getInstance().getMessageService().getMessage("lobby.teams.blue"))
                .setLore(listBlue)
                .setID(player.getUniqueId().toString())
                .addClick(new InventoryClickTransporter() {
                    @Override
                    public void exec(InventoryClickEvent event) {
                        Training.getInstance().getController().getCWTPlayer(player).addToTeam(Training.getInstance().getTeamBlue());
                        player.closeInventory();
                        player.updateInventory();
                        player.playSound(player.getLocation(), Sound.NOTE_PLING, 2, 3);
                    }
                })
                .build());
        inventory.setItem(5, new ItemBuilder(Material.WOOL, 1, 14)
                .setName(Training.getInstance().getMessageService().getMessage("lobby.teams.red"))
                .setLore(listRed)
                .setID(player.getUniqueId().toString())
                .addClick(new InventoryClickTransporter() {
                    @Override
                    public void exec(InventoryClickEvent event) {
                        Training.getInstance().getController().getCWTPlayer(player).addToTeam(Training.getInstance().getTeamRed());
                        player.closeInventory();
                        player.updateInventory();
                    }
                })
                .build());

        player.openInventory(inventory);
    }
}
