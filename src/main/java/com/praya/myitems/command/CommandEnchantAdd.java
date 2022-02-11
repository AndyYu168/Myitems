// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.command;

import com.praya.agarthalib.utility.*;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerCommand;
import com.praya.myitems.manager.plugin.CommandManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.builder.message.MessageBuild;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.enums.main.Slot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class CommandEnchantAdd extends HandlerCommand implements CommandExecutor {
    public CommandEnchantAdd(final MyItems plugin) {
        super(plugin);
    }

    protected static final boolean addEnchant(final CommandSender sender, final Command command, final String label, final String[] args) {
        final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
        final PluginManager pluginManager = plugin.getPluginManager();
        final CommandManager commandManager = pluginManager.getCommandManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        if (!commandManager.checkPermission(sender, "Enchant_Add")) {
            final String permission = commandManager.getPermission("Enchant_Add");
            final MessageBuild message = lang.getMessage(sender, "Permission_Lack");
            message.sendMessage(sender, "permission", permission);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        if (!SenderUtil.isPlayer(sender)) {
            final MessageBuild message2 = lang.getMessage(sender, "Console_Command_Forbiden");
            message2.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        final Player player = PlayerUtil.parse(sender);
        final ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
        if (!EquipmentUtil.isSolid(item)) {
            final MessageBuild message3 = lang.getMessage(sender, "Item_MainHand_Empty");
            message3.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        if (args.length < 2) {
            final String tooltip = TextUtil.getJsonTooltip(lang.getText(sender, "Tooltip_Enchant_Add"));
            final MessageBuild message4 = lang.getMessage(sender, "Argument_AddEnchant");
            message4.sendMessage(sender, "tooltip_enchant_add", tooltip);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        final String enchantmentName = args[0];
        final String textGrade = args[1];
        final Enchantment enchantment = EnchantmentUtil.getEnchantment(enchantmentName);
        if (!MathUtil.isNumber(textGrade)) {
            final MessageBuild message5 = lang.getMessage(sender, "Argument_Invalid_Value");
            message5.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        if (enchantment == null) {
            final MessageBuild message5 = lang.getMessage(sender, "Item_Enchantment_Not_Exist");
            message5.sendMessage(sender, "enchantment", enchantmentName);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        final MessageBuild message5 = lang.getMessage(sender, "MyItems_AddEnchant_Success");
        final int grade = Math.max(1, MathUtil.parseInteger(textGrade));
        final HashMap<String, String> mapPlaceholder = new HashMap<String, String>();
        mapPlaceholder.put("enchantment", enchantment.getName());
        mapPlaceholder.put("grade", String.valueOf(grade));
        EquipmentUtil.addEnchantment(item, enchantment, grade);
        message5.sendMessage(sender, mapPlaceholder);
        SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
        player.updateInventory();
        return true;
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        return addEnchant(sender, command, label, args);
    }
}
