package net.bote.training.frontend.listener;

import net.bote.training.Training;
import net.bote.training.backend.enums.SpectatorState;
import net.bote.training.backend.game.TrainingPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Elias Arndt | bote100
 * Created on 02.02.2020
 */

public class QuitListener implements Listener {

    @EventHandler
    public void on(PlayerQuitEvent e) {
        TrainingPlayer trainingPlayer = Training.getInstance().getController().getCWTPlayer(e.getPlayer());
        switch (Training.getInstance().getController().getCurrentState()) {
            case LOBBY:
            case RESTART:
                e.setQuitMessage(Training.getInstance().getMessageService().getMessage("lobby.quit", trainingPlayer.getPlayer().getName()));
                break;
            case INGAME:
                e.setQuitMessage(null);
                if (!trainingPlayer.getSpectatorState().equals(SpectatorState.NORMAL_SPECTATOR)) {
                    Training.getInstance().getMessageService().sendToAll("lobby.quit", trainingPlayer.getPlayer().getName());
                    if (trainingPlayer.getTeam().getMembers().size() <= 1) {
                        Training.getInstance().getController().getCurrentMap().closeRound(Training.getInstance().getController().getOppenent(trainingPlayer.getTeam()), true);
                    }
                }

                break;
        }
        Training.getInstance().getController().forgetPlayer(trainingPlayer);
    }

}
