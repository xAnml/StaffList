package net.anmlmc.StaffList.Commands;

import net.anmlmc.StaffList.Main;
import net.anmlmc.StaffList.Rank;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Anml on 1/16/2017.
 */
public class StaffCommand extends Command {

    private Main instance;

    public StaffCommand(Main instance) {
        super("staff");
        this.instance = instance;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(new ComponentBuilder("This command can only be executed by a player.").color(ChatColor.RED).create());
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;
        HashMap<ProxiedPlayer, Rank> ranks = new HashMap<>();
        HashMap<String, List<ProxiedPlayer>> servers = new HashMap<>();

        for (ServerInfo server : ProxyServer.getInstance().getServers().values()) {
            servers.put(server.getName(), new ArrayList<ProxiedPlayer>());
        }

        for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
            for (Rank rank : Rank.values()) {
                if (p.hasPermission(rank.getPermission())) {
                    if (ranks.get(p) == null) {
                        ranks.put(p, rank);
                    } else {
                        if (rank.getID() > ranks.get(p).getID()) {
                            ranks.replace(p, ranks.get(p), rank);
                        }
                    }

                    servers.get(p.getServer().getInfo().getName()).add(p);
                }
            }
        }

        int total = 0;
        for (String server : servers.keySet()) {
            total += servers.get(server).size();
        }

        if (total == 0) {
            player.sendMessage(new ComponentBuilder("There are currently no staff members online.").color(ChatColor.RED).create());
            return;
        }

        player.sendMessage(new ComponentBuilder(centerText("-----------[ Staff Online ]----------", 2)).color(ChatColor.GREEN).bold(true).create());

        for (String server : servers.keySet()) {
            if (servers.get(server).size() != 0) {
                player.sendMessage(new ComponentBuilder(server).color(ChatColor.DARK_GREEN).bold(true).append(":").color(ChatColor.WHITE).bold(true).create());

                for (int x = Rank.values().length; x > 0; x--) {
                    Rank rank = getRank(x);

                    if (rank == null) {
                        player.sendMessage(new ComponentBuilder("A rank was not found. Please contact an administrator.").color(ChatColor.RED).create());
                        return;
                    }

                    for (ProxiedPlayer p : servers.get(server)) {
                        if (ranks.get(p).equals(rank)) {
                            player.sendMessage(new ComponentBuilder(" - ").color(ChatColor.WHITE).append(rank.getName()).color(rank.getColor()).bold(true)
                                    .append(" " + p.getName()).color(rank.getColor()).bold(false).create());
                        }
                    }
                }
            }
        }


    }

    private String centerText(String text, int colors) {
        int maxWidth = 80, spaces = (int) Math.round((maxWidth - 1.4 * (colors * 2)) / 2);
        String spaced = "";
        for (int x = 0; x < spaces; x++)
            spaced = spaced + " ";
        return spaced + text;
    }

    private Rank getRank(int id) {
        for (Rank rank : Rank.values()) {
            if (rank.getID() == id) {
                return rank;
            }
        }

        return null;
    }

}
