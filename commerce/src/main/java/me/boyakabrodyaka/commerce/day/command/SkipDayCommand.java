package me.boyakabrodyaka.commerce.day.command;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.commerce.day.finisher.DayFinisher;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class SkipDayCommand implements CommandExecutor {

    private final DayFinisher dayFinisher;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        if (!player.isOp()) return true;

        this.dayFinisher.complete(player);

        return true;
    }
}