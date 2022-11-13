package net.bote.training.frontend.listener;

import net.bote.training.Training;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

/**
 * @author Elias Arndt | bote100
 * Created on 26.01.2020
 */

public final class PingListener implements Listener {

    @EventHandler
    public void on(ServerListPingEvent e) {
        switch (Training.getInstance().getController().getCurrentState()) {
            case LOBBY:
                e.setMaxPlayers(2*Training.getInstance().getMessageService().getYamlConfiguration().getInt("general.playersPerTeam"));
                break;
            default:
        }
    }

}
