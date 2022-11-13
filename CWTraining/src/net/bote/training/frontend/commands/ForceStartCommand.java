package net.bote.training.frontend.commands;

import net.bote.training.Training;
import net.bote.training.backend.enums.GameState;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Elias Arndt | bote100
 * Created on 26.01.2020
 */

public class ForceStartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        Player p = ((Player) commandSender);

        if(!p.hasPermission("training.start")) {
            p.sendMessage(Training.getInstance().getMessageService().getMessage("general.noRights"));
            return true;
        }

        if(!Training.getInstance().getController().getCurrentState().equals(GameState.LOBBY)) {
            p.sendMessage(Training.getInstance().getMessageService().getMessage("command.start.error"));
            return true;
        }

        if(Bukkit.getOnlinePlayers().size() < Training.getInstance().getMessageService().getYamlConfiguration().getInt("general.minPlayers")) {
            p.sendMessage(Training.getInstance().getMessageService().getMessage("command.start.error"));
            return true;
        }

        if(Training.getInstance().getController().getLobbyCountdown() <= Training.getInstance().getMessageService().getYamlConfiguration().getInt("lobby.earlyvalue")) {
            p.sendMessage(Training.getInstance().getMessageService().getMessage("command.start.error"));
            return true;
        }

        Training.getInstance().getController().setLobbyCountdown(Training.getInstance().getMessageService().getYamlConfiguration().getInt("lobby.earlyvalue"));
        p.sendMessage(Training.getInstance().getMessageService().getMessage("command.start.success"));
        return true;
    }
}
