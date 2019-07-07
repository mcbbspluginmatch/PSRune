package me.wuxie.psrune.cmd;

import me.wuxie.plugstats.util.MessageUtil;
import me.wuxie.plugstats.util.cmdutil.PSCommand;
import me.wuxie.psrune.PSRune;
import me.wuxie.psrune.gui.Holder;
import me.wuxie.psrune.gui.InvRuneData;
import me.wuxie.psrune.gui.XiangQianGui;
import me.wuxie.psrune.gui.ZhaiChuGui;
import me.wuxie.psrune.rune.Rune;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class PSRCommand extends PSCommand {
    public PSRCommand() {
        super("psrune", "PSRune主命令！", "§c[§bPSRune§c] §cError! Please reload this server", Arrays.asList("psr","rune","runes"));
    }
    /**
     * 主命令
     *
     * @param sender  CommandSender
     * @param label   String
     * @param args    String[]
     * @return boolean
     */
    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(args.length>0){
            if(args[0].equalsIgnoreCase("xq")&&sender instanceof Player&&sender.hasPermission("PSRune.xq")){
                Player player = (Player) sender;
                openXiangQian(player);
                return true;
            }else if(args[0].equalsIgnoreCase("zc")&&sender instanceof Player&&sender.hasPermission("PSRune.zc")){
                Player player = (Player) sender;
                if (openZhaichu(player)) return true;
                return true;
            }else {
                    if(args[0].equalsIgnoreCase("reload")){
                        if(sender.hasPermission("PSRune.admin")){
                            PSRune.getInstance().onReload();
                            sender.sendMessage("§c[§bPSRune§c] §aThis plugin has been reloaded");
                            return true;
                        }else {
                            sender.sendMessage("§c[§bPSRune§c] §cSorry! You do not have permission PSRune.admin!");
                            return true;
                        }
                    }
                    if(args[0].equalsIgnoreCase("open")&&args.length>2){
                        if(sender.hasPermission("PSRune.admin")){
                            Player player = Bukkit.getPlayer(args[2]);
                            if(player==null){
                                sender.sendMessage("§c[§bPSRune§c] §7玩家不在线！");
                                return true;
                            }
                            if(args[1].equalsIgnoreCase("zc")){
                                return openZhaichu(player);
                            }else if(args[1].equalsIgnoreCase("xq")){
                                openXiangQian(player);
                                return true;
                            }
                        }else {
                            sender.sendMessage("§c[§bPSRune§c] §cSorry! You do not have permission PSRune.admin!");
                            return true;
                        }
                    }
                    if(args.length>2&&args[0].equalsIgnoreCase("give")){
                        if(sender.hasPermission("PSRune.admin")){
                            //psr give 符文1 10
                            //psr give 符文1 player 10
                            String itemkey = null;
                            String num = null;
                            Player player = null;
                            if(args.length==3&& sender instanceof Player){
                                player = (Player) sender;
                                itemkey = args[1];
                                num = args[2];
                            }else if(args.length >=4){
                                player = Bukkit.getPlayer(args[2]);
                                itemkey = args[1];
                                num = args[3];
                            }
                            if(itemkey!=null){
                                int amount;
                                try {
                                    amount = Integer.parseInt(num);
                                }catch (Exception e){
                                    sender.sendMessage("§7/" + label + " give 材料 数量 §c玩家身份执行，获取材料！");
                                    sender.sendMessage("§7/" + label + " give 材料 玩家 数量 §c给玩家发送材料！");
                                    return true;
                                }
                                if(player!=null){
                                    Rune r = PSRune.getInstance().getRuneManager().getRuneMap().get(itemkey);
                                    if(r!=null){
                                        ItemStack is = r.getItemStack().clone();
                                        is.setAmount(amount);
                                        player.getInventory().addItem(is);
                                        if(player.equals(sender)){
                                            MessageUtil.send(sender,PSRune.getInstance().getConfig().getString("message.giveMe"),is.getItemMeta().hasDisplayName()?is.getItemMeta().getDisplayName():is.getType().toString(),amount);
                                        }else {
                                            MessageUtil.send(sender,PSRune.getInstance().getConfig().getString("message.givePlayer"),sender instanceof Player?sender.getName():"Server",is.getItemMeta().hasDisplayName()?is.getItemMeta().getDisplayName():is.getType().toString(),amount);
                                        }
                                        return true;
                                    }else {
                                        sender.sendMessage("§c[§bPSRune§c] §7不存在的材料！");
                                        return true;
                                    }
                                }else {
                                    sender.sendMessage("§c[§bPSRune§c] §7玩家不在线！");
                                    return true;
                                }
                            }else {
                                sender.sendMessage("§7/" + label + " give 材料 数量 §c玩家身份执行，获取材料！");
                                sender.sendMessage("§7/" + label + " give 材料 玩家 数量 §c给玩家发送材料！");
                                return true;
                            }
                        }else {
                            sender.sendMessage("§c[§bPSRune§c] §cSorry! You do not have permission PSRune.admin!");
                            return true;
                        }
                    }
            }
        }else {
            sender.sendMessage("§7§m--=-=--=-=--=-=---=---=----=--§c[§dPSRune§c]§7§m--=----=---=---=--= -=-=--=-=--");
            if(sender instanceof Player){
                sender.sendMessage("§7/"+label+" xq §c打开镶嵌界面！");
                sender.sendMessage("§7/"+label+" zc §c打开摘除界面！");
            }
            if(sender.hasPermission("PSRune.admin")){
                sender.sendMessage("§7/" + label + " give 材料 数量 §c玩家身份执行，获取材料！");
                sender.sendMessage("§7/" + label + " give 材料 玩家 数量 §c给玩家发送材料！");
                sender.sendMessage("§7/" + label + " open xq 玩家 §c为玩家打开镶嵌界面！");
                sender.sendMessage("§7/" + label + " open zc 玩家 §c为玩家打开摘除界面！");
                sender.sendMessage("§7/"+label+" reload §c重载插件！");
            }
            sender.sendMessage("§7§m--=-=--=-=--=-=---=---=----=--========--=----=---=---=--=-=-=--=-=--");
        }
        return true;
    }

    public void openXiangQian(Player player) {
        Inventory inv = Bukkit.createInventory(new Holder(Holder.HType.XQ),27, XiangQianGui.getTitle());
        inv.setContents(XiangQianGui.getInv().getContents());
        player.openInventory(inv);
    }

    public boolean openZhaichu(Player player) {
        ItemStack mainhand = player.getInventory().getItemInMainHand();
        if(mainhand!=null&&!mainhand.getType().equals(Material.AIR)){
            if(mainhand.getAmount()>1){
                MessageUtil.send(player, PSRune.getInstance().getConfig().getString("message.moreHand"));
                return true;
            }
            Inventory inv = Bukkit.createInventory(new Holder(Holder.HType.ZC),54, ZhaiChuGui.getTitle());
            inv.setContents(ZhaiChuGui.getInv().getContents());
            if(mainhand.hasItemMeta()&&mainhand.getItemMeta().hasLore()){
                List<String> lore = mainhand.getItemMeta().getLore();
                int invline = 1;
                int loreline = 0;
                Map<Integer,InvRuneData> invRuneDataMap = new HashMap<>();
                for(String l:lore){
                    if(invline>=5)break;
                    if(isRuneLine(l)){
                        InvRuneData data = getRunes(loreline,l);
                        invRuneDataMap.put(invline,data);
                        String[] strs = data.getData().split(",");
                        int num = 0;
                        for(String a:strs){
                            inv.setItem((invline*9)+num,data.getRunedata().get(Integer.parseInt(a)).getItemStack());
                            num+=1;
                            if(num>=9)break;
                        }
                        invline+=1;
                    }
                    loreline+=1;
                }
                ((Holder)inv.getHolder()).setInvRuneDataMap(invRuneDataMap);
            }
            player.openInventory(inv);
        }else MessageUtil.send(player,PSRune.getInstance().getConfig().getString("message.noHand"));
        return false;
    }

    /**
     * 主命令tab补全
     *
     * @param sender  CommandSender
     * @param alias   String
     * @param args    String[]
     * @return List
     */
    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
        return null;
    }

    /**
     *  返回符文对应列id
     * @param str String
     * @return String
     */
    private static String getRuneRandIds(String str){
        Iterator<String> strs= new ArrayList<>(Arrays.asList(str.split("<\\|psRune"))).iterator();
        StringBuffer sb = new StringBuffer();
        while (strs.hasNext()){
            String s = strs.next();
            if(s.contains("|>")){
                sb.append(s.split("\\|>")[0]);
                if(strs.hasNext()){
                    sb.append(",");
                }
            }
        }
        return sb.toString();
    }
    /**
     * 判断是否是符文行
     * @param str lore
     * @return boolean
     */
    private boolean isRuneLine(String str){
        for(String sign:PSRune.getInstance().getRuneManager().getAttributeDataMap().keySet()){
            if(str.contains(sign)){
                return true;
            }
        }
        return false;
    }

    private InvRuneData getRunes(int loreline,String str){
        int x = 0;
        Map<Integer,Rune> runeMap = new HashMap<>();
        for(Rune r:PSRune.getInstance().getRuneManager().getRuneMap().values()){
            if(str.contains(r.getSign())){
                str = str.replace(r.getSign(),"<|psRune"+x+"|>");
                runeMap.put(x,r);
                x+=1;
            }
        }
        //返回str数字顺序
        return new InvRuneData(loreline,runeMap,getRuneRandIds(str));
    }
}
