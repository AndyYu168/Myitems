// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.passive.debuff;

import api.praya.myitems.builder.passive.PassiveEffectEnum;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.abs.PassiveEffect;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.PassiveEffectManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

public class DebuffConfuse extends PassiveEffect {
    private static final PassiveEffectEnum debuff;

    static {
        debuff = PassiveEffectEnum.CONFUSE;
    }

    public DebuffConfuse() {
        super(DebuffConfuse.debuff, 1);
    }

    public DebuffConfuse(final int grade) {
        super(DebuffConfuse.debuff, grade);
    }

    @Override
    public final void cast(final Player player) {
        final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        if (!passiveEffectManager.isPassiveEffectCooldown(DebuffConfuse.debuff, player)) {
            final PotionEffectType potionType = this.getPotion();
            final int duration = this.getDuration();
            final long cooldown = this.getCooldown();
            final boolean isEnableParticle = mainConfig.isMiscEnableParticlePotion();
            passiveEffectManager.setPassiveEffectCooldown(DebuffConfuse.debuff, player, cooldown);
        }
    }

    private final int getDuration() {
        final MainConfig mainConfig = MainConfig.getInstance();
        return (int) (mainConfig.getPassivePeriodEffect() * this.grade / 2.5 + 40.0);
    }

    private final long getCooldown() {
        final MainConfig mainConfig = MainConfig.getInstance();
        return (long) (MathUtil.convertTickToMilis(mainConfig.getPassivePeriodEffect()) * DebuffConfuse.debuff.getMaxGrade() / 2.5);
    }
}
