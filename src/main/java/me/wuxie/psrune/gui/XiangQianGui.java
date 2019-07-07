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

public class XiangQianGui {
    @Getter
    private static Inventory inv;
    @Getter
    private static String title ;
    public static void createInventory(){
        title = MessageUtil.colorReplace(PSRune.getInstance().getConfig().getString("xiangqian.title"));
        ItemStack is1 = ItemStackUtil.createItemStack("STAINED_GLASS_PANE:15"," ",null,null,null);
        inv = Bukkit.createInventory(new Holder(Holder.HType.XQ),27,title);
        for(int a=0;a<10;){
            inv.setItem(a,is1);
            a+=1;
        }
        inv.setItem(11,is1);
        inv.setItem(12,is1);
        inv.setItem(14,is1);
        inv.setItem(15,is1);
        inv.setItem(16,ItemStackUtil.createItemStack("END_CRYSTAL",MessageUtil.colorReplace(PSRune.getInstance().getConfig().getString("xiangqian.item")), MessageUtil.getMsg(PSRune.getInstance().getConfig().getStringList("xiangqian.lore")),null,null));
        /*inv.setItem(17,is1);inv.setItem(18,is1);
        inv.setItem(26,is1);
        inv.setItem(27,is1);
        inv.setItem(35,is1);inv.setItem(36,is1);*/
        for(int a=17;a<27;){
            inv.setItem(a,is1);
            a+=1;
        }
    }
}
