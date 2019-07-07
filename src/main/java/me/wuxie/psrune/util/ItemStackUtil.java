package me.wuxie.psrune.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemStackUtil {
    public static ItemStack createItemStack(String id,String name, List<String> lore ,List<String> enchantment ,List<String> flags){
        Material material;
        short data = 0;
        if(id.contains(":")){
            String materialid = id.split(":")[0];
            if(isNumber(materialid)){
                material = Material.getMaterial(Integer.parseInt(materialid));
            }else material = Material.getMaterial(materialid);
            data = Short.valueOf(id.split(":")[1]);
        }else {
            if(isNumber(id)){
                material = Material.getMaterial(Integer.parseInt(id));
            }else material = Material.getMaterial(id);
        }
        ItemStack itemStack = new ItemStack(material,1,data);
        Map<Enchantment,Integer> enchantmentMap = new HashMap<>();
        if(enchantment!=null&&!enchantment.isEmpty()){
            for(String e:enchantment){
                if(e.contains(":")){
                    enchantmentMap.put(Enchantment.getByName(e.split(":")[0]),Integer.parseInt(e.split(":")[1]));
                }
            }
            itemStack.addUnsafeEnchantments(enchantmentMap);
        }
        ItemMeta meta = itemStack.getItemMeta();
        if(lore!=null&&!lore.isEmpty()){
            meta.setLore(recolorList(lore));
        }
        if(flags!=null&&!flags.isEmpty()){
            for(String f:flags){
                meta.addItemFlags(ItemFlag.valueOf(f));
            }
        }
        if(name!=null){
            meta.setDisplayName(name);
        }
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    private static boolean isNumber(String s){
        try {
            Integer.parseInt(s);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private static List<String> recolorList(List<String> lore){
        List<String> l = new ArrayList<>();
        for(String s:lore){
            l.add(ChatColor.translateAlternateColorCodes('&',s));
        }
        return l;
    }
}
