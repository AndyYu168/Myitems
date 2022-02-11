// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.command;

import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.agarthalib.utility.SenderUtil;
import com.praya.agarthalib.utility.TextUtil;
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
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandItemName extends HandlerCommand implements CommandExecutor {
    public CommandItemName(final MyItems plugin) {
        super(plugin);
    }

    protected static final boolean setName(final CommandSender sender, final Command command, final String label, final String[] args) {
        final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
        final PluginManager pluginManager = plugin.getPluginManager();
        final CommandManager commandManager = pluginManager.getCommandManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        if (!commandManager.checkPermission(sender, "MyItems_Name")) {
            final String permission = commandManager.getPermission("MyItems_Name");
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
        if (args.length < 1) {
            final MessageBuild message3 = lang.getMessage(player, "MyItems_SetName_Clear_Success");
            message3.sendMessage(sender);
            EquipmentUtil.clearDisplayName(item);
            SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
            player.updateInventory();
            return true;
        }
        final String name = TextUtil.hookPlaceholderAPI(player, TextUtil.concatenate(args));
        final MessageBuild message4 = lang.getMessage(sender, "MyItems_SetName_Success");
        EquipmentUtil.setDisplayName(item, name);
        message4.sendMessage(sender, "name", name);
        SenderUtil.playSound(sender, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
        player.updateInventory();
        return true;
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        return setName(sender, command, label, args);
    }
}
