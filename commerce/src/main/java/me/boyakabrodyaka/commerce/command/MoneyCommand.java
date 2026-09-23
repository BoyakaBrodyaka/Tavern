package me.boyakabrodyaka.commerce.command;

import me.boyakabrodyaka.commerce.account.AccountManager;
import me.boyakabrodyaka.commerce.command.sub.MoneyHelpCommand;
import me.boyakabrodyaka.commerce.command.sub.MoneySetCommand;
import me.boyakabrodyaka.core.util.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MoneyCommand implements CommandExecutor {

    private static final String UNKNOWN_SUBCOMMAND = "&cНеизвестная команда: ";

    private final MoneySetCommand setCommand;
    private final MoneyHelpCommand helpCommand;

    public MoneyCommand(AccountManager accountManager) {
        this.setCommand = new MoneySetCommand(accountManager);
        this.helpCommand = new MoneyHelpCommand();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            this.helpCommand.execute(sender);
            return true;
        }

        String sub = args[0].toLowerCase();

        switch (sub) {
            case "set":
                this.setCommand.execute(sender, args);
                return true;
            case "help":
                this.helpCommand.execute(sender);
                return true;
            default:
                sender.sendMessage(Color.color(UNKNOWN_SUBCOMMAND + sub));
                return true;
        }
    }
}