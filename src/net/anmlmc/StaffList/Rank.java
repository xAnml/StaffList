package net.anmlmc.StaffList;

import net.md_5.bungee.api.ChatColor;

/**
 * Created by Anml on 1/16/2017.
 */

public enum Rank {

    HELPER("Helper", ChatColor.DARK_GREEN, "stafflist.helper", 1),
    MOD("Mod", ChatColor.BLUE, "stafflist.mod", 2),
    ADMIN("Admin", ChatColor.RED, "stafflist.admin", 3),
    DEV("Dev", ChatColor.GOLD, "stafflist.dev", 4),
    OWNER("Owner", ChatColor.DARK_RED, "stafflist.owner", 5);


    String name;
    ChatColor color;
    String permission;
    int id;

    Rank(String name, ChatColor color, String permission, int id) {
        this.name = name;
        this.color = color;
        this.permission = permission;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public String getPermission() {
        return permission;
    }

    public int getID() {
        return id;
    }

}