package me.wuxie.psrune.gui;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.Map;

public class Holder implements InventoryHolder {
    @Getter
    private HType hType;
    @Getter
    @Setter
    Map<Integer,InvRuneData> invRuneDataMap = null;
    public Holder(HType type){
        hType = type;
    }
    @Override
    public Inventory getInventory() {
        return null;
    }
    public enum HType{
        XQ,
        ZC;
    }
}
