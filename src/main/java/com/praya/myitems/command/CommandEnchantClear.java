// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.command;

import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.agarthalib.utility.SenderUtil;
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
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandEnchantClear extends HandlerCommand implements CommandExecutor {
    public CommandEnchantClear(final MyItems plugin) {
        super(plugin);
    }

    protected static final boolean clearEnchant(final CommandSender sender, final Command command, final String label, final String[] args) {
        final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
        final PluginManager pluginManager = plugin.getPluginManager();
        final CommandManager commandManager = pluginManager.getCommandManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        if (!commandManager.checkPermission(sender, "Enchant_Clear")) {
            final String permission = commandManager.getPermission("Enchant_Clear");
            final MessageBuild message = lang.getMessage(sender, "Permission_Lack");
            message.sendMessage(sender, "permission", permission);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return true;
        }
        if (!SenderUtil.isPlayer(sender)) {
            final MessageBuild message2 = lang.getMessage(sender, "Console_Command_Forbiden");
            message2.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return false;
        }
        final Player player = PlayerUtil.parse(sender);
        final ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
        if (!EquipmentUtil.isSolid(item)) {
            final MessageBuild message3 = lang.getMessage(sender, "Item_MainHand_Empty");
            message3.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return false;
        }
        if (!EquipmentUtil.hasLore(item)) {
            final MessageBuild message3 = lang.getMessage(sender, "Item_Lore_Empty");
            message3.sendMessage(sender);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_BLAZE_DEATH);
            return false;
        }
        final MessageBuild message3 = lang.getMessage(sender, "MyItems_ClearEnchant_Success");
        EquipmentUtil.clearEnchantment(item);
        message3.sendMessage(sender);
        SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
        player.updateInventory();
        return true;
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        return clearEnchant(sender, command, label, args);
    }
}
