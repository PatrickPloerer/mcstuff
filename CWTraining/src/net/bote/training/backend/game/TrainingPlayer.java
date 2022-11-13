package net.bote.training.backend.game;

import net.bote.training.Training;
import net.bote.training.backend.enums.SpectatorState;
import net.bote.training.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import java.util.Objects;

import javax.annotation.Nonnull;

public class TrainingPlayer {

    private TrainingTeam team;
    private final Player player;
	private SpectatorState spectatorState = SpectatorState.NORMAL_SPECTATOR;
	
	public TrainingPlayer(Player p) {
		player = p;
	}

    public void setSpectatorState(SpectatorState state) {
        System.out.println("state = " + state + " > " + this.toString());
        this.spectatorState = state;
    }

    public boolean addToTeam(@Nonnull TrainingTeam trainingTeam) {
        System.out.println("[Training] Adding " + player.getName() + " to team " + trainingTeam.getName());
        if(Objects.nonNull(this.team)) {
            if(this.team.getName().equals(trainingTeam.getName())) {
                this.player.sendMessage(Training.getInstance().getMessageService().getMessage("lobby.teams.alreadyIn"));
                return false;
            } else removeFromTeam();
        }

        if(trainingTeam.getMaxSize()<=trainingTeam.getMembers().size()) {
            this.player.sendMessage(Training.getInstance().getMessageService().getMessage("lobby.teams.full"));
            return false;
        }

        this.player.sendMessage(Training.getInstance().getMessageService().getMessage("lobby.teams.entered",
                trainingTeam.getColorCode(), trainingTeam.getName()));
        this.player.playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 2, 3);

        this.team = trainingTeam;
        trainingTeam.getMembers().add(this);
        this.player.closeInventory();
        return true;
    }

    public void setRespawnInventory() {
        player.getInventory().clear();
        player.getInventory().setItem(0, new ItemBuilder(Material.WOOD_PICKAXE)
                .addEnchantment(Enchantment.DIG_SPEED, 1)
                .addEnchantment(Enchantment.DURABILITY, 3)
                .build());
        player.getInventory().setItem(1, new ItemBuilder(Material.COOKED_BEEF, 3).build());
        player.getInventory().setItem(8, new ItemBuilder(Material.SANDSTONE, 32).build());
    }

    public void removeFromTeam() {
        if(Objects.isNull(this.team)) return;
        this.team.getMembers().remove(this);
        this.team = null;
    }

	public Player getPlayer() {
		return player;
	}

	public SpectatorState getSpectatorState() {
		return spectatorState;
	}

	public TrainingTeam getTeam() {
		return team;
	}

}
