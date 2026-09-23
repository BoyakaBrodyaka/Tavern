package me.boyakabrodyaka.commerce.command.sub;

import me.boyakabrodyaka.core.util.Color;
import org.bukkit.command.CommandSender;

public class MoneyHelpCommand {
    public void execute(CommandSender sender) {
        sender.sendMessage(Color.color("&6&lДеньги"));
        sender.sendMessage(Color.color("&e/money set <игрок> <сумма> &7- установить баланс"));
        sender.sendMessage(Color.color("&e/money help &7- помощь"));
    }
}
