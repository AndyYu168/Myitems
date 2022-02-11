// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.ability.weapon;

import api.praya.myitems.builder.ability.AbilityWeapon;
import api.praya.myitems.builder.ability.AbilityWeaponAttributeBaseDamage;
import api.praya.myitems.builder.ability.AbilityWeaponProperties;
import com.praya.agarthalib.utility.EntityUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.AbilityWeaponManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.builder.message.MessageBuild;
import core.praya.agarthalib.enums.branch.SoundEnum;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.projectiles.ProjectileSource;

import java.util.List;

public class AbilityWeaponCannibalism extends AbilityWeapon implements AbilityWeaponAttributeBaseDamage {
    private static final String ABILITY_ID = "Cannibalism";

    private AbilityWeaponCannibalism(final MyItems plugin, final String id) {
        super(plugin, id);
    }

    public static final AbilityWeaponCannibalism getInstance() {
        return AbilityCannibalismHelper.instance;
    }

    @Override
    public String getKeyLore() {
        final MainConfig mainConfig = MainConfig.getInstance();
        return mainConfig.getAbilityWeaponIdentifierCannibalism();
    }

    @Override
    public List<String> getDescription() {
        return null;
    }

    @Override
    public int getMaxGrade() {
        final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Cannibalism");
        return abilityWeaponProperties.getMaxGrade();
    }

    @Override
    public double getBaseBonusDamage(final int grade) {
        final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Cannibalism");
        final double baseBonusDamage = grade * abilityWeaponProperties.getScaleBaseBonusDamage();
        return baseBonusDamage;
    }

    @Override
    public double getBasePercentDamage(final int grade) {
        final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final AbilityWeaponManager abilityWeaponManager = gameManager.getAbilityWeaponManager();
        final AbilityWeaponProperties abilityWeaponProperties = abilityWeaponManager.getAbilityWeaponProperties("Cannibalism");
        final double basePercentDamage = grade * abilityWeaponProperties.getScaleBasePercentDamage();
        return basePercentDamage;
    }

    @Override
    public void cast(final Entity caster, final Entity target, final int grade, final double damage) {
        final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
        final PluginManager pluginManager = plugin.getPluginManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        LivingEntity attacker;
        if (caster instanceof Projectile) {
            final Projectile projectile = (Projectile) caster;
            final ProjectileSource projectileSource = projectile.getShooter();
            if (projectileSource == null || !(projectileSource instanceof LivingEntity)) {
                return;
            }
            attacker = (LivingEntity) projectileSource;
        } else {
            attacker = (LivingEntity) caster;
        }
        if (target instanceof LivingEntity) {
            final LivingEntity victims = (LivingEntity) target;
            final Location location = attacker.getEyeLocation();
            final MessageBuild messageAttacker = lang.getMessage(attacker, "Ability_Cannibalism_Attacker");
            final MessageBuild messageVictims = lang.getMessage(victims, "Ability_Cannibalism_Victims");
            final int maxGrade = this.getMaxGrade();
            int food = 10 * (grade / maxGrade);
            if (victims instanceof Player) {
                final Player playerVictims = PlayerUtil.parse(victims);
                final int foodVictims = playerVictims.getFoodLevel();
                food = Math.max(foodVictims, food);
                playerVictims.setFoodLevel(foodVictims - food);
                messageVictims.sendMessage(victims, "food", String.valueOf(food));
                Bridge.getBridgeSound().playSound(playerVictims, location, SoundEnum.ENTITY_PLAYER_BURP, 0.7f, 1.0f);
            }
            if (EntityUtil.isPlayer(attacker)) {
                final Player playerAttacker = PlayerUtil.parse(attacker);
                final int foodAttacker = playerAttacker.getFoodLevel();
                food = ((foodAttacker + food > 20) ? (20 - foodAttacker) : food);
                playerAttacker.setFoodLevel(foodAttacker + food);
                messageAttacker.sendMessage(playerAttacker, "food", String.valueOf(food));
                Bridge.getBridgeSound().playSound(playerAttacker, location, SoundEnum.ENTITY_PLAYER_BURP, 0.7f, 1.0f);
            }
        }
    }

    private static class AbilityCannibalismHelper {
        private static final AbilityWeaponCannibalism instance;

        static {
            final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
            instance = new AbilityWeaponCannibalism(plugin, "Cannibalism");
        }
    }
}
