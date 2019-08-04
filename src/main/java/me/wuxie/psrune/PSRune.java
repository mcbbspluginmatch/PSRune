package me.wuxie.psrune;

import lombok.Getter;
import me.wuxie.plugstats.PlugStats;
import me.wuxie.plugstats.attribute.AttributeData;
import me.wuxie.plugstats.attributesign.AbstractSign;
import me.wuxie.plugstats.util.MessageUtil;
import me.wuxie.psrune.cmd.PSRCommand;
import me.wuxie.psrune.gui.XiangQianGui;
import me.wuxie.psrune.gui.ZhaiChuGui;
import me.wuxie.psrune.listener.RuneListener;
import me.wuxie.psrune.rune.RuneManager;
import me.wuxie.psrune.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public final class PSRune extends AbstractSign {
    @Getter
    private RuneManager runeManager;
    @Getter
    private String emptySlot;
    @Getter
    private String tag;
    @Getter
    private RuneListener runeListener;
    @Getter
    private static PSRune instance;
    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        tag = getConfig().getString("tag");
        emptySlot = MessageUtil.getMsg(tag,getConfig().getString("emptySlot"));
        XiangQianGui.createInventory();
        ZhaiChuGui.createInventory();
        PlugStats.getCommandManager().register(new PSRCommand());
        runeListener = new RuneListener(this);
        registerOnlyEvents(runeListener);
    }

    @Override
    public void onDisable() {
        unRegisterEvents(runeListener);
        for(Player player:Bukkit.getOnlinePlayers()){
            InventoryView view = player.getOpenInventory();
            if(view!=null){
                view.close();
            }
        }
    }

    @Override
    public void onReload(){
        reloadConfig();
        tag = getConfig().getString("tag");
        emptySlot = MessageUtil.getMsg(tag,getConfig().getString("emptySlot"));
        XiangQianGui.createInventory();
        ZhaiChuGui.createInventory();
        runeManager = new RuneManager(this);
    }

    @Override
    public AttributeData getAttribute(ItemStack itemStack) {
        AttributeData data = new AttributeData();
        if(itemStack!=null&&itemStack.hasItemMeta()){
            if(itemStack.getItemMeta().hasLore()){
                List<String> lore = itemStack.getItemMeta().getLore();
                Map<String,AttributeData> dataMap = runeManager.getAttributeDataMap();
                for(String l:lore){
                    for (Map.Entry<String,AttributeData> mp:dataMap.entrySet()){
                        if(l.contains(mp.getKey())){
                            int num = StringUtil.countMatches(l,mp.getKey());
                            for(int a=0;a<num;){
                                a+=1; // 为什么自增要放进来 —— 754503921
                                data.addAttributes(mp.getValue());
                            }
                        }
                    }
                }
            }
        }
        return data;
    }

    public void reload(){

    }

    @Override
    public void onAttributeLoaded() {
        runeManager = new RuneManager(this);
    }
}
