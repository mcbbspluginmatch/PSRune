package me.wuxie.psrune.rune;

import lombok.Getter;
import me.wuxie.plugstats.PlugStats;
import me.wuxie.plugstats.attribute.AttributeData;
import me.wuxie.plugstats.util.MessageUtil;
import me.wuxie.psrune.PSRune;
import me.wuxie.psrune.util.ItemStackUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuneManager {
    // String -> key
    @Getter
    private Map<String,Rune> runeMap;
    // String -> sign
    @Getter
    private Map<String, AttributeData> attributeDataMap;

    @Getter
    private YamlConfiguration runeYml;
    private PSRune plugin;
    public RuneManager(PSRune plugin){
        this.plugin = plugin;
        File runeF = new File(plugin.getDataFolder(),"runes.yml");
        if(!runeF.exists()){
            plugin.saveResource("runes.yml",true);
        }
        runeYml = YamlConfiguration.loadConfiguration(runeF);
        loadRune();
    }
    public void loadRune(){
        runeMap = new HashMap<>();
        attributeDataMap = new HashMap<>();
        for(String key:runeYml.getKeys(false)){
            String showSign = runeYml.getString(key+".showSign");
            AttributeData attributeData = PlugStats.getAttributeManager().getLoreAttributeData(runeYml.getStringList(key+".attribute"));
            String id = runeYml.getString(key+".id");
            String name = runeYml.getString(key+".name");
            List<String> lore = runeYml.getStringList(key+".lore");
            List<String> enchantment = runeYml.getStringList(key+".enchantment");
            List<String> flags = runeYml.getStringList(key+".flags");
            ItemStack itemStack = ItemStackUtil.createItemStack(id,MessageUtil.colorReplace(name),lore,enchantment,flags);
            String tag = MessageUtil.getMsg(plugin.getTag(),showSign);
            Rune rune = new Rune(showSign,tag,itemStack,attributeData);
            runeMap.put(key,rune);
            attributeDataMap.put(tag,attributeData);
        }
    }
}
