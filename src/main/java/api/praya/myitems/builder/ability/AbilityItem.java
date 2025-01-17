package api.praya.myitems.builder.ability;

public class AbilityItem {
   private final String ability;
   private final int grade;
   private final double chance;

   protected AbilityItem(String ability, int grade, double chance) {
      this.ability = ability;
      this.grade = grade;
      this.chance = chance;
   }

   public final String getAbility() {
      return this.ability;
   }

   public final int getGrade() {
      return this.grade;
   }

   public final double getChance() {
      return this.chance;
   }
}
