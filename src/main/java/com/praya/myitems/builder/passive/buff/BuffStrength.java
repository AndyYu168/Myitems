// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.passive.buff;

import api.praya.myitems.builder.passive.PassiveEffectEnum;
import com.praya.myitems.builder.abs.PassiveEffect;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class BuffStrength extends PassiveEffect {
    private static final PassiveEffectEnum buff;

    static {
        buff = PassiveEffectEnum.STRENGTH;
    }

    public BuffStrength() {
        super(BuffStrength.buff, 1);
    }

    public BuffStrength(final int grade) {
        super(BuffStrength.buff, grade);
    }

    @Override
    public final void cast(final Player player) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final PotionEffectType potionType = this.getPotion();
        final boolean isEnableParticle = mainConfig.isMiscEnableParticlePotion();
    }
}
