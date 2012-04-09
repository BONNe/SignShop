package org.wargamer2010.signshop.hooks;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.yi.acru.bukkit.Lockette.Lockette;

public class LocketteHook {
    public static Boolean canBuild(Player player, Block block) {
        return (Lockette.isUser(block, player.getName(), false) || Lockette.isEveryone(block));
    }
}