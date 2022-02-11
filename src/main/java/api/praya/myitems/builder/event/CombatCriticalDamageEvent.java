// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.event;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CombatCriticalDamageEvent extends Event implements Cancellable {
    private static final HandlerList handlers;

    static {
        handlers = new HandlerList();
    }

    private final LivingEntity attacker;
    private final LivingEntity victims;
    private boolean cancel;
    private final double baseDamage;
    private double scaleDamage;
    private double bonusDamage;

    public CombatCriticalDamageEvent(final LivingEntity attacker, final LivingEntity victims, final double baseDamage, final double scaleDamage, final double bonusDamage) {
        this.cancel = false;
        this.attacker = attacker;
        this.victims = victims;
        this.baseDamage = baseDamage;
        this.scaleDamage = scaleDamage;
        this.bonusDamage = bonusDamage;
    }

    public static final HandlerList getHandlerList() {
        return CombatCriticalDamageEvent.handlers;
    }

    public final LivingEntity getAttacker() {
        return this.attacker;
    }

    public final LivingEntity getVictims() {
        return this.victims;
    }

    public final double getBaseDamage() {
        return this.baseDamage;
    }

    public final double getScaleDamage() {
        return this.scaleDamage;
    }

    public final void setScaleDamage(final double scaleDamage) {
        this.scaleDamage = scaleDamage;
    }

    public final double getBonusDamage() {
        return this.bonusDamage;
    }

    public final void setBonusDamage(final double bonusDamage) {
        this.bonusDamage = bonusDamage;
    }

    public final double getCalculationDamage() {
        return this.baseDamage * this.scaleDamage + this.bonusDamage;
    }

    public HandlerList getHandlers() {
        return CombatCriticalDamageEvent.handlers;
    }

    public boolean isCancelled() {
        return this.cancel;
    }

    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
}
