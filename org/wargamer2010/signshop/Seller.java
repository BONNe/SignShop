package org.wargamer2010.signshop;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.wargamer2010.signshop.util.itemUtil;
import com.kellerkindt.scs.*;
import java.util.LinkedHashMap;
import org.wargamer2010.signshop.blocks.SignShopBooks;
import org.wargamer2010.signshop.blocks.SignShopItemMeta;
import org.wargamer2010.signshop.util.signshopUtil;

public class Seller {
    private List<Block> containables = new LinkedList<Block>();
    private List<Block> activatables = new LinkedList<Block>();
    private ItemStack[] isItems;
    private Map<String, String> miscProps = new HashMap<String, String>();
    private Map<String, String> volatileProperties = new LinkedHashMap<String, String>();

    private String owner;
    private String world;

    public Seller(String sPlayer, String sWorld, List<Block> pContainables, List<Block> pActivatables, ItemStack[] isChestItems, Map<String, String> pMiscProps, Boolean save) {
        owner = sPlayer;
        world = sWorld;

        isItems = itemUtil.getBackupItemStack(isChestItems);
        containables = pContainables;
        activatables = pActivatables;
        if(pMiscProps != null)
            miscProps.putAll(pMiscProps);
        if(save)
            storeMeta();
    }

    public ItemStack[] getItems() {
        return itemUtil.getBackupItemStack(isItems);
    }

    public void setItems(ItemStack[] items) {
        isItems = items;
    }

    public List<Block> getContainables() {
        return containables;
    }

    public void setContainables(List<Block> blocklist) {
        containables = blocklist;
    }

    public List<Block> getActivatables() {
        return activatables;
    }

    public void setActivatables(List<Block> blocklist) {
        activatables = blocklist;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String newowner) {
        owner = newowner;
    }

    public String getWorld() {
        return world;
    }

    public Map<String, String> getMisc() {
        return miscProps;
    }

    public void cleanUp() {
        if(miscProps.containsKey("showcaselocation")) {
            if(Bukkit.getServer().getPluginManager().getPlugin("ShowCaseStandalone") == null)
                return;
            Location loc = signshopUtil.convertStringToLocation(miscProps.get("showcaselocation"), Bukkit.getWorld(world));
            ShowCaseStandalone scs = (ShowCaseStandalone) Bukkit.getServer().getPluginManager().getPlugin("ShowCaseStandalone");
            com.kellerkindt.scs.shops.Shop shop = null;
            try {
                shop = scs.getShopHandler().getShopForBlock(Bukkit.getWorld(world).getBlockAt(loc));
            } catch(Exception ex) {
                return;
            }
            if(shop != null)
                scs.getShopHandler().removeShop(shop);
        }
    }

    private void storeMeta() {
        for(ItemStack stack : isItems) {
            if(itemUtil.isWriteableBook(stack)) {
                SignShopBooks.addBook(stack);
            }
            SignShopItemMeta.storeMeta(stack);
        }
    }

    public String getVolatile(String key) {
        if(volatileProperties.containsKey(key))
            return volatileProperties.get(key);
        return null;
    }

    public void setVolatile(String key, String value) {
        volatileProperties.put(key, value);
    }
}
