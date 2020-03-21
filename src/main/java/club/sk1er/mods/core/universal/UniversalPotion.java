package club.sk1er.mods.core.universal;

import net.minecraft.potion.PotionEffect;
//#if MC>10809
//$$ import net.minecraft.potion.Potion;
//#endif

public class UniversalPotion {

    public static int getPotionID(PotionEffect potion) {
        //#if MC<=10809
        return potion.getPotionID();
        //#else
        //$$ return Potion.getIdFromPotion(potion.getPotion());
        //#endif
    }
}
