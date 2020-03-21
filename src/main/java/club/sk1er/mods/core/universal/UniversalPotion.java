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
        //#if MC<11502
        //$$ return Potion.getIdFromPotion(potion.getPotion());
        //#else
        //$$ return -1;
        //#endif
        //#endif
    }
}
