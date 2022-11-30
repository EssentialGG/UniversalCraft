package gg.essential.universal

//#if MC>=11903
//$$ import net.minecraft.registry.entry.RegistryEntry
//#endif

//#if MC>10809
//$$ import net.minecraft.init.SoundEvents
//$$ import net.minecraft.util.SoundCategory
//$$ import net.minecraft.util.SoundEvent
//#else
import net.minecraft.util.ResourceLocation
//#endif

object USound {
    //#if MC>10809
    //$$ fun playSoundStatic(event: SoundEvent, volume: Float, pitch: Float) {
    //#else
    fun playSoundStatic(event: ResourceLocation, volume: Float, pitch: Float) {
        //#endif

        // sound handler can be null whenever switching devices or openal fails to
        // initialize the sound handler correctly, which is very common, so protect against that
        val soundHandler = UMinecraft.getMinecraft().soundHandler ?: return

        PositionedSoundRecordFactory.makeRecord(event, volume, pitch)?.let { soundHandler.playSound(it) }
    }

    //#if MC>=11903
    //$$ fun playSoundStatic(registryEntry: RegistryEntry<SoundEvent>, volume: Float, pitch: Float) {
    //$$     playSoundStatic(registryEntry.value(), volume, pitch)
    //$$ }
    //#endif

    @JvmOverloads
    fun playButtonPress(volume: Float = 0.25f) {
        //#if MC>10809
        //$$ playSoundStatic(SoundEvents.UI_BUTTON_CLICK, volume, 1.0f)
        //#else
        playSoundStatic(ResourceLocation("gui.button.press"), volume, 1.0F);
        //#endif
    }

    fun playExpSound() {
        //#if MC>10809
        //$$ playSoundStatic(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.25F, 1.0f)
        //#else
        playSoundStatic(ResourceLocation("random.orb"), 0.25F, 1.0F);
        //#endif
    }

    fun playLevelupSound() {
        //#if MC>10809
        //$$ playSoundStatic(SoundEvents.ENTITY_PLAYER_LEVELUP, 0.25F, 1.0f)
        //#else
        playSoundStatic(ResourceLocation("random.levelup"), 0.25F, 1.0F);
        //#endif
    }

    fun playPlingSound() {
        //#if MC>10809
        //$$ playSoundStatic(SoundEvents.BLOCK_NOTE_PLING, 0.25F, 1.0f)
        //#else
        playSoundStatic(ResourceLocation("note.pling"), 0.25F, 1.0F);
        //#endif
    }


}