package me.wuxie.psrune.gui;

import lombok.Getter;
import me.wuxie.plugstats.util.MessageUtil;
import me.wuxie.psrune.PSRune;
import me.wuxie.psrune.util.ItemStackUtil;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class ZhaiChuGui {
    @Getter
    private static Inventory inv;
    @Getter
    private static String title ;
    public static void createInventory(){
        title = MessageUtil.colorReplace(PSRune.getInstance().getConfig().getString("zhaichu.title"));
        ItemStack is1 = ItemStackUtil.createItemStack("STAINED_GLASS_PANE:15"," ",null,null,null);
        inv = Bukkit.createInventory(new Holder(Holder.HType.XQ),54,title);
        for(int a=0;a<9;){
            inv.setItem(a,is1);
            a+=1;
        }
        inv.setItem(4,ItemStackUtil.createItemStack("END_CRYSTAL",MessageUtil.colorReplace(PSRune.getInstance().getConfig().getString("zhaichu.item")), MessageUtil.getMsg(PSRune.getInstance().getConfig().getStringList("zhaichu.lore")),null,null));
        /*for(int a = 45;a<54;){
            inv.setItem(a,is1);
            a+=1;
        }*/
    }
}
