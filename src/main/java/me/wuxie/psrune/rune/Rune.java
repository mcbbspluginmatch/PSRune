package me.wuxie.psrune.rune;

import lombok.Getter;
import me.wuxie.plugstats.attribute.AttributeData;
import org.bukkit.inventory.ItemStack;

public class Rune {
    @Getter
    private String sign;
    @Getter
    private String showSign;
    @Getter
    private ItemStack itemStack;
    @Getter
    private AttributeData attributeData;
    public Rune(String showSign,String sign,ItemStack itemStack,AttributeData attributeData){
        this.sign = sign;
        this.showSign = showSign;
        this.itemStack = itemStack;
        this.attributeData = attributeData;
    }
}
