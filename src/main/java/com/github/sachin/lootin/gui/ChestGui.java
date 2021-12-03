package com.github.sachin.lootin.gui;

import java.util.Arrays;
import java.util.List;

import com.github.sachin.lootin.utils.ChestUtils;
import com.github.sachin.lootin.utils.ContainerType;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ChestGui extends GuiHolder{

    private Chest chest;

    public ChestGui(Player player,Chest chest) {
        super(player, ContainerType.CHEST);
        this.chest = chest;
    }

    @Override
    public void open() {
        List<ItemStack> contents = ChestUtils.getContainerItems(null, chest, ContainerType.CHEST, player);
        if(contents != null){
            inventory.setContents(contents.toArray(new ItemStack[0]));
            player.openInventory(inventory);
            chest.open();
            if(plugin.isRunningProtocolLib){
                player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN,SoundCategory.PLAYERS,0.5F,1);
            }
            plugin.currentChestviewers.add(chest.getLocation());
        }
    }

    @Override
    public void close() {
        List<ItemStack> contents = Arrays.asList(inventory.getContents());
        if(contents != null){
            ChestUtils.setContainerItems(null, chest, type, contents, player.getUniqueId().toString());
        }
        chest.close();
        if(plugin.isRunningProtocolLib){
            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CHEST_CLOSE,SoundCategory.PLAYERS,0.5F,1);
        }
        plugin.currentChestviewers.remove(chest.getLocation());
    }


    
}
