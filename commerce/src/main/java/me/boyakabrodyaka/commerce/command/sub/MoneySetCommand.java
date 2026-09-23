package me.boyakabrodyaka.commerce.command.sub;

import lombok.RequiredArgsConstructor;
import me.boyakabrodyaka.commerce.account.Account;
import me.boyakabrodyaka.commerce.account.AccountManager;
import me.boyakabrodyaka.core.util.Color;
import me.boyakabrodyaka.core.util.FormatNumber;
import me.boyakabrodyaka.core.util.Number;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
public class MoneySetCommand {

    private static final String PERMISSION = "tavern.money.set";
    private static final String USAGE = "&cИспользование: /money set <игрок> <сумма>";
    private static final String AMOUNT_ERROR = "&cСумма должна быть числом";

    private final AccountManager accountManager;

    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission(PERMISSION)) return;
        if (args.length != 3) {
            sender.sendMessage(Color.color(USAGE));
            return;
        }

        String targetName = args[1];
        String amountArg = args[2];

        if (!Number.isNumber(amountArg)) {
            sender.sendMessage(Color.color(AMOUNT_ERROR));
            return;
        }

        double amount = Number.parse(amountArg);
        Player target = Bukkit.getPlayerExact(targetName);

        String key = resolveKey(target, targetName);

        Account account = this.accountManager.get(key);
        account.setMoney(amount);

        String formatted = FormatNumber.format(amount);

        sender.sendMessage(Color.color("&aУстановлено &e$" + formatted + " &aигроку &e" + targetName));

        if (target != null) target.sendMessage(Color.color("&aВаш баланс: &e$" + formatted));
    }

    private String resolveKey(Player target, String targetName) {
        if (target == null) return targetName;
        return target.getWorld().getName();
    }
}