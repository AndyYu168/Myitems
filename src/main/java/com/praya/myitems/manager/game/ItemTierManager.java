package com.praya.myitems.manager.game;

import api.praya.myitems.builder.item.ItemTier;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.config.game.ItemTierConfig;
import java.util.Collection;

public class ItemTierManager extends HandlerManager {
   private final ItemTierConfig itemTierConfig;

   protected ItemTierManager(MyItems plugin) {
      super(plugin);
      this.itemTierConfig = new ItemTierConfig(plugin);
   }

   public final ItemTierConfig getItemTierConfig() {
      return this.itemTierConfig;
   }

   public final Collection<String> getItemTierIDs() {
      return this.getItemTierConfig().getItemTierIDs();
   }

   public final Collection<ItemTier> getItemTiers() {
      return this.getItemTierConfig().getItemTiers();
   }

   public final ItemTier getItemTier(String id) {
      return this.getItemTierConfig().getItemTier(id);
   }

   public final boolean isItemTierExists(String id) {
      return this.getItemTier(id) != null;
   }
}
