// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.passive.buff;

import api.praya.myitems.builder.passive.PassiveEffectEnum;
import com.praya.myitems.builder.abs.PassiveEffect;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class BuffRegeneration extends PassiveEffect {
    private static final PassiveEffectEnum buff;

    static {
        buff = PassiveEffectEnum.REGENERATION;
    }

    public BuffRegeneration() {
        super(BuffRegeneration.buff, 1);
    }

    public BuffRegeneration(final int grade) {
        super(BuffRegeneration.buff, grade);
    }

    @Override
    public final void cast(final Player player) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final PotionEffectType potionType = this.getPotion();
        final boolean isEnableParticle = mainConfig.isMiscEnableParticlePotion();
    }
}
