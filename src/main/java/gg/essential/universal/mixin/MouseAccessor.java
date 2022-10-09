//#if MC>=11502
//$$package gg.essential.universal.mixin;
//$$
//$$import net.minecraft.client.MouseHelper;
//$$import org.spongepowered.asm.mixin.Mixin;
//$$import org.spongepowered.asm.mixin.gen.Accessor;
//$$@Mixin(MouseHelper.class)
//$$public interface MouseAccessor {
        //#if MC==11502
        //$$ @Accessor("accumulatedScrollDelta")
        //#else
        //$$ @Accessor("eventDeltaWheel")
        //#endif
//$$    double getEventDeltaWheel();
//$$
        //#if MC==11502
        //$$ @Accessor("xVelocity")
        //#else
        //$$ @Accessor()
        //#endif
//$$    double getCursorDeltaX();
//$$
        //#if MC==11502
        //$$ @Accessor("yVelocity")
        //#else
        //$$ @Accessor()
        //#endif
//$$    double getCursorDeltaY();
//$$}
//#endif