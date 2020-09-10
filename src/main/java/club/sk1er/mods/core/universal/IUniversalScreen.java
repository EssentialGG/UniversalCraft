package club.sk1er.mods.core.universal;

public interface IUniversalScreen {
    boolean initScreen(int width, int height);

    boolean onDrawScreen(int mouseX, int mouseY, float partialTicks);

    boolean onKeyPressed(int keyCode, UniversalKeyboard.Modifier modifiers);

    // This function does not get called on versions less than 1.15.2
    boolean onKeyReleased(int keyCode, UniversalKeyboard.Modifier modifiers);

    // This function does not get called on versions less than 1.15.2
    boolean onCharTyped(char ch, UniversalKeyboard.Modifier modifiers);

    boolean onMouseClicked(double mouseX, double mouseY, int mouseButton);

    boolean onMouseReleased(double mouseX, double mouseY, int state);

    boolean onMouseDragged(double x, double y, int clickedButton, long timeSinceLastClick);

    // The return value is ignored on versions less than 1.15.2
    boolean onMouseScrolled(double delta);

    boolean onTick();

    boolean onScreenClose();

    boolean onDrawDefaultBackground(int tint);

    boolean onDrawWorldBackground(int tint);

    boolean onResize(int width, int height);
}
