package me.wuxie.psrune.listener;

import me.wuxie.plugstats.util.MessageUtil;
import me.wuxie.psrune.PSRune;
import me.wuxie.psrune.gui.Holder;
import me.wuxie.psrune.gui.InvRuneData;
import me.wuxie.psrune.rune.Rune;
import me.wuxie.psrune.util.StringUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class RuneListener implements Listener {
    private PSRune plugin;
    public RuneListener(PSRune plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getInventory()==null)return;
        if(e.getSlot()==-1)return;
        if(e.getInventory().getHolder() instanceof Holder){
            e.setCancelled(true);
            if(e.getClickedInventory()!=null&& !(e.getClickedInventory().getHolder() instanceof Holder)){
                e.setCancelled(false);
            }
            Player player = (Player) e.getWhoClicked();
            Inventory inv = e.getClickedInventory();
            Holder holder = (Holder) inv.getHolder();
            if(holder.getHType().equals(Holder.HType.XQ)){
                int slot = e.getSlot();
                if(slot==10||slot == 13){
                    e.setCancelled(false);
                }else if(slot == 16){
                    ItemStack zb = inv.getItem(10);
                    ItemStack cl = inv.getItem(13);
                    if(zb == null ||  zb.getType().equals(Material.AIR)){
                        MessageUtil.send(player,plugin.getConfig().getString("message.emptyItem"));
                        return;
                    }
                    if(cl == null ||cl.getType().equals(Material.AIR)){
                        MessageUtil.send(player,plugin.getConfig().getString("message.emptyRune"));
                        return;
                    }
                    boolean isCL = false;
                    Rune rune = null;
                    for(Rune r:PSRune.getInstance().getRuneManager().getRuneMap().values()){
                        if(r.getItemStack().isSimilar(cl)){
                            isCL = true;
                            rune = r;
                            break;
                        }
                    }
                    if(!isCL){
                        MessageUtil.send(player,plugin.getConfig().getString("message.noRune"));
                        return;
                    }
                    if(zb.getAmount()>1){
                        MessageUtil.send(player,plugin.getConfig().getString("message.moreItem"));
                        return;
                    }
                    if(zb.hasItemMeta()&&zb.getItemMeta().hasLore()){
                        String emptyslot = PSRune.getInstance().getEmptySlot();
                        List<String> lore = zb.getItemMeta().getLore();
                        int line = 0;
                        boolean can = false;
                        for(String l:lore){
                            if(l.contains(emptyslot)){
                                can = true;
                                break;
                            }else {
                                line+=1;
                            }
                        }
                        if(can){
                            if(cl.getAmount()>1){
                                cl.setAmount(cl.getAmount()-1);
                            }else inv.setItem(13,null);
                            lore.set(line, StringUtil.replaceFirst(lore.get(line),emptyslot,rune.getSign()));
                            ItemMeta meta =  zb.getItemMeta();
                            meta.setLore(lore);
                            zb.setItemMeta(meta);
                            inv.setItem(10,zb);
                            MessageUtil.send(player,plugin.getConfig().getString("message.successXQ"),cl.getItemMeta().getDisplayName());
                        }else {
                            MessageUtil.send(player,plugin.getConfig().getString("message.noItem"));
                        }
                    }else {
                        MessageUtil.send(player,plugin.getConfig().getString("message.noItem"));
                    }
                }
            }else {
                //摘除界面
                ItemStack is = player.getInventory().getItemInMainHand();
                ItemMeta meta = is.getItemMeta();
                List<String> lore = meta.getLore();
                int slot = e.getSlot();
                ItemStack current = e.getCurrentItem();
                if(slot>8&&current!=null&&!current.getType().equals(Material.AIR)){
                    Map<Integer,InvRuneData> invdata = holder.getInvRuneDataMap();
                    int clickLine = getClickLine(slot);

                    InvRuneData data = invdata.get(clickLine);
                    int loreLine = data.getLoreline();
                    String signLore = lore.get(loreLine);

                    int invlineIndex = getLineIndex(slot);
                    int fistinvlineIndex = slot-invlineIndex;
                    StringBuilder sb = new StringBuilder("hhh");
                    Rune rune = null;
                    Map<String,Rune> runeMap = plugin.getRuneManager().getRuneMap();
                    for (;fistinvlineIndex<=slot;){
                        ItemStack runei = inv.getItem(fistinvlineIndex);
                        if(runei!=null&&!runei.getType().equals(Material.AIR)){
                            for(Rune r:runeMap.values()){
                                if(runei.isSimilar(r.getItemStack())){
                                    sb.append(r.getSign());
                                    if(fistinvlineIndex == slot){
                                        rune = r;
                                    }
                                }
                            }
                        }
                        fistinvlineIndex+=1;
                    }
                    int lorelineIndex = StringUtil.countMatches(sb.toString(),rune.getSign())-1;
                    if(lorelineIndex==0){
                        signLore = StringUtil.replaceFirst(signLore,rune.getSign(),plugin.getEmptySlot());
                    }else signLore = StringUtil.replaceIndex(lorelineIndex,signLore,rune.getSign(),plugin.getEmptySlot());
                    player.getInventory().addItem(current);
                    inv.setItem(slot,null);
                    lore.set(loreLine,signLore);
                    meta.setLore(lore);
                    is.setItemMeta(meta);
                    MessageUtil.send(player,plugin.getConfig().getString("message.successZC"),current.getItemMeta().hasDisplayName()?current.getItemMeta().getDisplayName():current.getType().toString());
                }
            }
        }
    }
    @EventHandler
    public void onClose(InventoryCloseEvent e){
        if(e.getInventory().getHolder() instanceof Holder){
            Inventory inv = e.getInventory();
            Holder holder = (Holder) inv.getHolder();
            Player player = (Player) e.getPlayer();
            if(holder.getHType().equals(Holder.HType.XQ)){
                ItemStack zb = inv.getItem(10);
                ItemStack cl = inv.getItem(13);
                if(zb != null&&!zb.getType().equals(Material.AIR)){
                    player.getInventory().addItem(zb);
                }
                if(cl != null &&!cl.getType().equals(Material.AIR)){
                    player.getInventory().addItem(cl);
                }
            }
        }
    }
    private int getClickLine(int slot){
        return slot/9;
    }
    private int getLineIndex(int slot){
        int fistLineSlot = getClickLine(slot)*9;
        return slot-fistLineSlot;
    }

    String[] replacecolors = {"%零%","%一%","%二%","%三%","%四%","%五%","%六%","%七%","%八%","%九%"};
    String[] colors = {"§0","§1","§2","§3","§4","§5","§6","§7","§8","§9"};
}
