package net.anmlmc.StaffList;

import net.anmlmc.StaffList.Commands.StaffCommand;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * Created by Anml on 1/16/2017.
 */
public class Main extends Plugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        getProxy().getPluginManager().registerCommand(this, new StaffCommand(this));
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public Main getInstance() {
        return instance;
    }
}
