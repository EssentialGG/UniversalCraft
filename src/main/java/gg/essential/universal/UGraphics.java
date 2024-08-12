package gg.essential.universal;

import gg.essential.universal.utils.ReleasedDynamicTexture;
import gg.essential.universal.vertex.UVertexConsumer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

//#if STANDALONE
//$$ import gg.essential.universal.standalone.nanovg.NvgContext;
//$$ import gg.essential.universal.standalone.nanovg.NvgFont;
//$$ import gg.essential.universal.standalone.nanovg.NvgFontFace;
//$$ import gg.essential.universal.standalone.render.BufferBuilder;
//$$ import gg.essential.universal.standalone.render.DefaultShader;
//$$ import gg.essential.universal.standalone.render.DefaultVertexFormats;
//$$ import gg.essential.universal.standalone.render.Gl2Renderer;
//$$ import gg.essential.universal.standalone.render.VertexFormat;
//$$ import org.lwjgl.opengl.GL11;
//$$ import org.lwjgl.opengl.GL20;
//$$ import java.net.URL;
//$$ import static kotlin.io.TextStreamsKt.readBytes;
//$$ import static org.lwjgl.opengl.GL14.*;
//#else
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_BINDING_2D;
import static org.lwjgl.opengl.GL13.GL_ACTIVE_TEXTURE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;

//#if MC>=12100
//$$ import net.minecraft.client.render.BufferRenderer;
//$$ import net.minecraft.client.render.BuiltBuffer;
//$$ import net.minecraft.client.util.BufferAllocator;
//#endif

//#if MC>=12005
//$$ import org.joml.Vector3f;
//#endif

//#if MC>=11904
//$$ import net.minecraft.client.font.TextRenderer;
//#endif

//#if MC>=11900
//$$ import net.minecraft.text.Text;
//#endif

//#if MC>=11700
//$$ import net.minecraft.client.render.GameRenderer;
//$$ import net.minecraft.client.render.Shader;
//$$ import java.util.IdentityHashMap;
//$$ import java.util.Map;
//$$ import java.util.function.Supplier;
//#endif

//#if MC>=11602
//$$ import net.minecraft.util.ResourceLocation;
//$$ import net.minecraft.util.text.Style;
//$$ import net.minecraft.util.text.CharacterManager;
//$$ import net.minecraft.util.text.StringTextComponent;
//$$ import net.minecraft.util.text.ITextProperties;
//$$ import net.minecraft.client.renderer.RenderType;
//$$ import net.minecraft.client.renderer.IRenderTypeBuffer;
//$$ import java.util.ArrayList;
//$$ import java.util.Optional;
//#else
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
//#endif

//#if MC>=11502
//$$ import com.mojang.blaze3d.platform.GlStateManager;
//$$ import net.minecraft.client.renderer.texture.NativeImage;
//$$ import java.io.ByteArrayInputStream;
//$$ import java.io.ByteArrayOutputStream;
//#endif

//#if MC>=11400
//$$ import net.minecraft.client.renderer.texture.Texture;
//#else
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.ITextureObject;
//#endif

//#endif

@SuppressWarnings("deprecation") // lots of MC methods are deprecated on some versions but only replaced on the next one
public class UGraphics {
    private static final Pattern formattingCodePattern = Pattern.compile("(?i)\u00a7[0-9A-FK-OR]");

    private static UMatrixStack UNIT_STACK = UMatrixStack.UNIT;

    //#if STANDALONE
    //$$ public static void init() { /* triggers static initializer */ }
    //$$ private static final Gl2Renderer RENDERER = new Gl2Renderer();
    //$$ private static final NvgFont MC_FONT;
    //$$ static {
    //$$     URL fontUrl = UGraphics.class.getResource("/fonts/Minecraft-Regular.otf");
    //$$     assert fontUrl != null;
    //$$     MC_FONT = new NvgFont(new NvgFontFace(new NvgContext(), readBytes(fontUrl)), 10, 7f, 1f);
    //$$
    //$$     // TODO should probably put this in a unit test, but lwjgl makes those a bit tricky to set up
    //$$     assert UGraphics.getStringWidth("a") == 6;
    //$$     assert UGraphics.getStringWidth("aa") == 12;
    //$$     assert UGraphics.getStringWidth(" ") == 4;
    //$$     assert UGraphics.getStringWidth("  ") == 8;
    //$$     assert UGraphics.getStringWidth("a ") == 10;
    //$$     assert UGraphics.getStringWidth("a  ") == 14;
    //$$ }
    //$$
    //$$ private BufferBuilder instance;
    //$$ private DrawMode drawMode;
    //$$ private CommonVertexFormats vertexFormat;
    //$$ private DefaultShader shader;
    //#else

    //#if MC>=12100
    //$$ public static Style EMPTY_WITH_FONT_ID = Style.EMPTY.withFont(Identifier.of("minecraft", "alt"));
    //#elseif MC>=11602
    //$$ public static Style EMPTY_WITH_FONT_ID = Style.EMPTY.setFontId(new ResourceLocation("minecraft", "alt"));
    //#endif
    public static int ZERO_TEXT_ALPHA = 10;
    private WorldRenderer instance;
    private VertexFormat vertexFormat;

    //#if MC>=11904
    //$$ private static final TextRenderer.TextLayerType TEXT_LAYER_TYPE = TextRenderer.TextLayerType.NORMAL;
    //#elseif MC>=11602
    //$$ private static final boolean TEXT_LAYER_TYPE = false;
    //#endif

    //#if MC>=12100
    //$$ /**
    //$$  * A buffer to use for storing sorted quad vertex indices. The buffer will automatically grow as needed,
    //$$  * the specified size is not a hard cap.
    //$$  */
    //$$ private static final BufferAllocator SORTED_QUADS_ALLOCATOR = new BufferAllocator(65536);
    //#endif

    public UGraphics(WorldRenderer instance) {
        this.instance = instance;
    }
    //#endif

    public UVertexConsumer asUVertexConsumer() {
        //#if STANDALONE
        //$$ return instance;
        //#else
        return UVertexConsumer.of(instance);
        //#endif
    }

    public static UGraphics getFromTessellator() {
        //#if STANDALONE
        //$$ return new UGraphics();
        //#elseif MC>=12100
        //$$ return new UGraphics(null);
        //#else
        return new UGraphics(getTessellator().getWorldRenderer());
        //#endif
    }

    //#if MC<11700
    @Deprecated // See UGraphics.GL
    public static void pushMatrix() {
        GL.pushMatrix();
    }

    @Deprecated // See UGraphics.GL
    public static void popMatrix() {
        GL.popMatrix();
    }

    @Deprecated // See UGraphics.GL
    public static void translate(float x, float y, float z) {
        GL.translate(x, y, z);
    }

    @Deprecated // See UGraphics.GL
    public static void translate(double x, double y, double z) {
        GL.translate(x, y, z);
    }

    @Deprecated // See UGraphics.GL
    public static void rotate(float angle, float x, float y, float z) {
        GL.rotate(angle, x, y, z);
    }

    @Deprecated // See UGraphics.GL
    public static void scale(float x, float y, float z) {
        GL.scale(x, y, z);
    }

    @Deprecated // See UGraphics.GL
    public static void scale(double x, double y, double z) {
        GL.scale(x, y, z);
    }
    //#endif

    //#if !STANDALONE
    public static Tessellator getTessellator() {
        return Tessellator.getInstance();
    }
    //#endif

    //#if MC>=12100
    //$$ // No possible alternative on 1.21. A compile time error here is better than a run time one.
    //#else
    @Deprecated // Use the non-static methods for 1.17+ compatibility or call UGraphics.getTessellator().draw() directly
    public static void draw() {
        getTessellator().draw();
    }
    //#endif

    public static boolean isCoreProfile() {
        //#if MC>=11700
        //$$ return true;
        //#else
        return false;
        //#endif
    }

    @Deprecated // only works on Forge 1.12.2 and below (relies on a Forge patch)
    public static void enableStencil() {
        //#if MC<11400
        Framebuffer framebuffer = Minecraft.getMinecraft().getFramebuffer();
        if (!framebuffer.isStencilEnabled()) {
            framebuffer.enableStencil();
        }
        //#endif
    }

    public static void cullFace(int mode) {
        //#if MC>=11502
        //$$ GL11.glCullFace(mode);
        //#elseif MC>10809
        //$$ GlStateManager.CullFace[] values = GlStateManager.CullFace.values();
        //$$ for (GlStateManager.CullFace value : values) {
        //$$     if (value.mode == mode) {
        //$$         GlStateManager.cullFace(value);
        //$$         return;
        //$$     }
        //$$ }
        //$$ throw new IllegalArgumentException(String.format("Mode %d is not valid!", mode));
        //#else
        GlStateManager.cullFace(mode);
        //#endif

    }

    public static void enableLighting() {
        //#if MC<11700
        GlStateManager.enableLighting();
        //#endif
    }

    public static void disableLighting() {
        //#if MC<11700
        GlStateManager.disableLighting();
        //#endif
    }

    public static void disableLight(int mode) {
        //#if MC<=11202
        GlStateManager.disableLight(mode);
        //#endif
    }

    public static void enableLight(int mode) {
        //#if MC<=11202
        GlStateManager.enableLight(mode);
        //#endif
    }

    public static void enableBlend() {
        //#if STANDALONE
        //$$ glEnable(GL_BLEND);
        //#else
        GlStateManager.enableBlend();
        //#endif
    }

    /**
     * @deprecated see {@link #enableTexture2D()}
     */
    @Deprecated
    public static void disableTexture2D() {
        //#if MC>=11904
        //$$ // no-op
        //#else
        GlStateManager.disableTexture2D();
        //#endif

    }

    public static void disableAlpha() {
        //#if MC<11700
        GlStateManager.disableAlpha();
        //#endif
    }

    public static void alphaFunc(int func, float ref) {
        //#if MC<11700
        GlStateManager.alphaFunc(func, ref);
        //#endif
    }

    public static void shadeModel(int mode) {
        //#if MC<11700
        GlStateManager.shadeModel(mode);
        //#endif
    }

    public static void blendEquation(int equation) {
        //#if MC>=10900 && !STANDALONE
        //$$ GlStateManager.glBlendEquation(equation);
        //#else
        org.lwjgl.opengl.GL14.glBlendEquation(equation);
        //#endif
    }

    public static void tryBlendFuncSeparate(int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
        //#if STANDALONE
        //$$ glBlendFuncSeparate(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
        //#else
        GlStateManager.tryBlendFuncSeparate(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
        //#endif
    }

    /**
     * @deprecated Relies on the global {@link #setActiveTexture(int) activeTexture} state.<br>
     *     Instead of manually managing TEXTURE_2D state, prefer using
     *     {@link #beginWithDefaultShader(DrawMode, CommonVertexFormats)} or any of the other non-deprecated begin methods as
     *     these will set (and restore) the appropriate state for the given {@link CommonVertexFormats} right before/after
     *     rendering.<br>
     *     Also incompatible with OpenGL Core / MC 1.17.
     */
    @Deprecated
    public static void enableTexture2D() {
        //#if MC>=11904
        //$$ // no-op
        //#else
        GlStateManager.enableTexture2D();
        //#endif
    }

    public static void disableBlend() {
        //#if STANDALONE
        //$$ glDisable(GL_BLEND);
        //#else
        GlStateManager.disableBlend();
        //#endif
    }

    public static void deleteTexture(int glTextureId) {
        //#if STANDALONE
        //$$ glDeleteTextures(GL_BLEND);
        //#else
        GlStateManager.deleteTexture(glTextureId);
        //#endif
    }

    public static void enableAlpha() {
        //#if MC<11700
        GlStateManager.enableAlpha();
        //#endif
    }

    public static void configureTexture(int glTextureId, Runnable block) {
        int prevTextureBinding = GL11.glGetInteger(GL_TEXTURE_BINDING_2D);
        bindTexture(glTextureId);

        block.run();

        bindTexture(prevTextureBinding);
    }

    public static void configureTextureUnit(int index, Runnable block) {
        int prevActiveTexture = getActiveTexture();
        setActiveTexture(GL_TEXTURE0 + index);

        block.run();

        setActiveTexture(prevActiveTexture);
    }

    /**
     * @deprecated Changes global state and may as such easily lead to bug if the caller forgets to store the previous
     * state and restore it afterwards.<br>
     * Prefer {@link #configureTextureUnit(int, Runnable)} instead (which only changes the global state for the duration
     * of the given block).<br>
     * If you must change it for longer, then use {@link #getActiveTexture()} before {@link #setActiveTexture(int)} and
     * make sure to restore it afterwards.
     */
    @Deprecated
    public static void activeTexture(int glId) {
        setActiveTexture(glId);
    }

    public static int getActiveTexture() {
        return GL11.glGetInteger(GL_ACTIVE_TEXTURE);
    }

    public static void setActiveTexture(int glId) {
        //#if STANDALONE
        //$$ glActiveTexture(glId);
        //#elseif MC>=11700
        //$$ GlStateManager._activeTexture(glId);
        //#elseif MC>=11400
        //$$ GlStateManager.activeTexture(glId);
        //#else
        GlStateManager.setActiveTexture(glId);
        //#endif
    }

    /**
     * @deprecated Relies on the global {@link #setActiveTexture(int) activeTexture} state.<br>
     * Prefer {@link #bindTexture(int, int)} instead.
     */
    @Deprecated
    public static void bindTexture(int glTextureId) {
        //#if STANDALONE
        //$$ glBindTexture(GL_TEXTURE_2D, glTextureId);
        //#elseif MC>=11700
        //$$ RenderSystem.setShaderTexture(GlStateManager._getActiveTexture() - GL_TEXTURE0, glTextureId);
        //#else
        GlStateManager.bindTexture(glTextureId);
        //#endif
    }

    //#if !STANDALONE
    /**
     * @deprecated Relies on the global {@link #setActiveTexture(int) activeTexture} state.<br>
     * Prefer {@link #bindTexture(int, ResourceLocation)} instead.
     */
    @Deprecated
    public static void bindTexture(ResourceLocation resourceLocation) {
        bindTexture(getOrLoadTextureId(resourceLocation));
    }
    //#endif

    public static void bindTexture(int index, int glTextureId) {
        //#if STANDALONE
        //$$ configureTextureUnit(index, () -> glBindTexture(GL_TEXTURE_2D, glTextureId));
        //#elseif MC>=11700
        //$$ RenderSystem.setShaderTexture(index, glTextureId);
        //#else
        configureTextureUnit(index, () -> GlStateManager.bindTexture(glTextureId));
        //#endif
    }

    //#if !STANDALONE
    public static void bindTexture(int index, ResourceLocation resourceLocation) {
        bindTexture(index, getOrLoadTextureId(resourceLocation));
    }

    private static int getOrLoadTextureId(ResourceLocation resourceLocation) {
        TextureManager textureManager = UMinecraft.getMinecraft().getTextureManager();
        //#if MC>=11400
        //$$ Texture texture = textureManager.getTexture(resourceLocation);
        //#else
        ITextureObject texture = textureManager.getTexture(resourceLocation);
        //#endif
        if (texture == null) {
            texture = new SimpleTexture(resourceLocation);
            textureManager.loadTexture(resourceLocation, texture);
        }
        return texture.getGlTextureId();
    }
    //#endif

    public static int getStringWidth(String in) {
        //#if STANDALONE
        //$$ return (int) MC_FONT.getStringWidth(in, 10f);
        //#else
        return UMinecraft.getFontRenderer().getStringWidth(in);
        //#endif
    }

    public static int getFontHeight() {
        //#if STANDALONE
        //$$ return 9;
        //#else
        return UMinecraft.getFontRenderer().FONT_HEIGHT;
        //#endif
    }

    @Deprecated // Pass UMatrixStack as first arg, required for 1.17+
    public static void drawString(String text, float x, float y, int color, boolean shadow) {
        drawString(UNIT_STACK, text, x, y, color, shadow);
    }

    public static void drawString(UMatrixStack stack, String text, float x, float y, int color, boolean shadow) {
        if ((color >> 24 & 255) <= 10) return;
        //#if STANDALONE
        //$$ MC_FONT.drawString(stack, text, new Color(color), x, y, 10f, 1f, shadow, null);
        //#else
        //#if MC>=11602
        //#if MC>=12100
        //$$ VertexConsumerProvider.Immediate irendertypebuffer$impl = UMinecraft.getMinecraft().getBufferBuilders().getEntityVertexConsumers();
        //#else
        //$$ IRenderTypeBuffer.Impl irendertypebuffer$impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
        //#endif
        //$$ UMinecraft.getFontRenderer().renderString(text, x, y, color, shadow, stack.peek().getModel(), irendertypebuffer$impl, TEXT_LAYER_TYPE, 0, 15728880);
        //$$ irendertypebuffer$impl.finish();
        //#else
        if (stack != UNIT_STACK) GL.pushMatrix();
        if (stack != UNIT_STACK) stack.applyToGlobalState();
        //#if MC>=11502
        //$$ if (shadow) {
        //$$     UMinecraft.getFontRenderer().drawStringWithShadow(text, x, y, color);
        //$$ } else {
        //$$     UMinecraft.getFontRenderer().drawString(text, x, y, color);
        //$$ }
        //#else
        UMinecraft.getFontRenderer().drawString(text, x, y, color, shadow);
        //#endif
        if (stack != UNIT_STACK) GL.popMatrix();
        //#endif
        //#endif
    }

    @Deprecated // Pass UMatrixStack as first arg, required for 1.17+
    public static void drawString(String text, float x, float y, int color, int shadowColor) {
        drawString(UNIT_STACK, text, x, y, color, shadowColor);
    }

    public static void drawString(UMatrixStack stack, String text, float x, float y, int color, int shadowColor) {
        if ((color >> 24 & 255) <= 10) return;
        String shadowText = ChatColor.Companion.stripColorCodes(text);
        //#if STANDALONE
        //$$ MC_FONT.drawString(stack, shadowText, new Color(shadowColor), x + 1f, y + 1f, 10f, 1f, false, null);
        //$$ MC_FONT.drawString(stack, text, new Color(color), x, y, 10f, 1f, false, null);
        //#else
        //#if MC>=11602
        //#if MC>=12100
        //$$ VertexConsumerProvider.Immediate irendertypebuffer$impl = UMinecraft.getMinecraft().getBufferBuilders().getEntityVertexConsumers();
        //#else
        //$$ IRenderTypeBuffer.Impl irendertypebuffer$impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
        //#endif
        //$$ UMinecraft.getFontRenderer().renderString(shadowText, x + 1f, y + 1f, shadowColor, false, stack.peek().getModel(), irendertypebuffer$impl, TEXT_LAYER_TYPE, 0, 15728880);
        //$$ UMinecraft.getFontRenderer().renderString(text, x, y, color, false, stack.peek().getModel(), irendertypebuffer$impl, TEXT_LAYER_TYPE, 0, 15728880);
        //$$ irendertypebuffer$impl.finish();
        //#else
        if (stack != UNIT_STACK) GL.pushMatrix();
        if (stack != UNIT_STACK) stack.applyToGlobalState();
        //#if MC>=11502
        //$$ UMinecraft.getFontRenderer().drawString(shadowText, x + 1f, y + 1f, shadowColor);
        //$$ UMinecraft.getFontRenderer().drawString(text, x, y, color);
        //#else
        UMinecraft.getFontRenderer().drawString(shadowText, x + 1f, y + 1f, shadowColor, false);
        UMinecraft.getFontRenderer().drawString(text, x, y, color, false);
        //#endif
        if (stack != UNIT_STACK) GL.popMatrix();
        //#endif
        //#endif
    }

    public static List<String> listFormattedStringToWidth(String str, int wrapWidth) {
        return listFormattedStringToWidth(str, wrapWidth, true);
    }

    public static List<String> listFormattedStringToWidth(String str, int wrapWidth, boolean safe) {
        if (safe) {
            String tmp = formattingCodePattern.matcher(str).replaceAll("");
            int max = 0;
            for (String s : tmp.split(" "))
                max = Math.max(max, getStringWidth(s));
            wrapWidth = Math.max(max, wrapWidth);
        }

        //#if STANDALONE
        //$$ throw new UnsupportedOperationException("not implemented");
        //#else
        //#if MC>=11602
        //$$ // TODO: Validate this code
        //$$ List<String> strings = new ArrayList<>();
        //$$
        //$$ CharacterManager charManager = UMinecraft.getFontRenderer().getCharacterManager();
        //#if MC>=11900
        //$$ StringVisitable properties = charManager.trimToWidth(Text.literal(str).fillStyle(EMPTY_WITH_FONT_ID), wrapWidth, Style.EMPTY);
        //#else
        //$$ ITextProperties properties = charManager.func_238358_a_(new StringTextComponent(str).mergeStyle(EMPTY_WITH_FONT_ID), wrapWidth, Style.EMPTY);
        //#endif
        //$$ // From net.minecraft.util.text.ITextProperties line 88
        //$$ properties.getComponent(string -> {
        //$$     strings.add(string);
        //$$     return Optional.empty();
        //$$ });
        //$$ return strings;
        //#else
        return UMinecraft.getFontRenderer().listFormattedStringToWidth(str, wrapWidth);
        //#endif
        //#endif
    }

    public static float getCharWidth(char character) {
        //#if STANDALONE
        //$$ return MC_FONT.getStringWidth(String.valueOf(character), 10f);
        //#elseif MC>=11602
        //$$ return getStringWidth(String.valueOf(character));
        //#else
        return UMinecraft.getFontRenderer().getCharWidth(character); // float because its a float in 1.15+
        //#endif
    }

    public static void glClear(int mode) {
        GL11.glClear(mode);
    }

    public static void glClearStencil(int mode) {
        GL11.glClearStencil(mode);
    }

    public static ReleasedDynamicTexture getTexture(InputStream stream) {
        try {
            //#if MC>=11502 && !STANDALONE
            //$$ return new ReleasedDynamicTexture(NativeImage.read(stream));
            //#else
            return new ReleasedDynamicTexture(ImageIO.read(stream));
            //#endif
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Failed to read image");
    }

    public static ReleasedDynamicTexture getTexture(BufferedImage img) {
        //#if MC>=11502 && !STANDALONE
        //$$ try {
        //$$     ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //$$     ImageIO.write(img, "png", baos );
        //$$     return new ReleasedDynamicTexture(NativeImage.read(new ByteArrayInputStream(baos.toByteArray())));
        //$$ } catch (IOException e) {
        //$$     e.printStackTrace();
        //$$ }
        //$$ throw new IllegalStateException("Failed to create texture");
        //#else
        return new ReleasedDynamicTexture(img);
        //#endif
    }

    public static ReleasedDynamicTexture getEmptyTexture() {
        return new ReleasedDynamicTexture(0, 0);
    }

    public static void glUseProgram(int program) {
        //#if STANDALONE
        //$$ GL20.glUseProgram(program);
        //#elseif MC>=11502
        //$$ GlStateManager.useProgram(program);
        //#else
        OpenGlHelper.glUseProgram(program);
        //#endif
    }

    public static boolean isOpenGl21Supported() {
        //#if MC>=11502
        //$$ return true;
        //#else
        return OpenGlHelper.openGL21;
        //#endif
    }

    public static boolean areShadersSupported() {
        //#if MC>=11502
        //$$ return true;
        //#else
        return OpenGlHelper.areShadersSupported();
        //#endif
    }

    public static int glCreateProgram() {
        //#if STANDALONE
        //$$ return GL20.glCreateProgram();
        //#elseif MC>=11502
        //$$ return GlStateManager.createProgram();
        //#else
        return OpenGlHelper.glCreateProgram();
        //#endif
    }

    public static int glCreateShader(int type) {
        //#if STANDALONE
        //$$ return GL20.glCreateShader(type);
        //#elseif MC>=11502
        //$$ return GlStateManager.createShader(type);
        //#else
        return OpenGlHelper.glCreateShader(type);
        //#endif
    }

    public static void glCompileShader(int shaderIn) {
        //#if STANDALONE
        //$$ GL20.glCompileShader(shaderIn);
        //#elseif MC>=11502
        //$$ GlStateManager.compileShader(shaderIn);
        //#else
        OpenGlHelper.glCompileShader(shaderIn);
        //#endif
    }

    public static int glGetShaderi(int shaderIn, int pname) {
        //#if STANDALONE
        //$$ return GL20.glGetShaderi(shaderIn, pname);
        //#elseif MC>=11502
        //$$ return GlStateManager.getShader(shaderIn,pname);
        //#else
        return OpenGlHelper.glGetShaderi(shaderIn, pname);
        //#endif
    }

    public static String glGetShaderInfoLog(int shader, int maxLen) {
        //#if STANDALONE
        //$$ return GL20.glGetShaderInfoLog(shader, maxLen);
        //#elseif MC>=11502
        //$$ return GlStateManager.getShaderInfoLog( shader,maxLen);
        //#else
        return OpenGlHelper.glGetShaderInfoLog(shader, maxLen);
        //#endif
    }

    public static void glAttachShader(int program, int shaderIn) {
        //#if STANDALONE
        //$$ GL20.glAttachShader(program, shaderIn);
        //#elseif MC>=11502
        //$$ GlStateManager.attachShader(program,shaderIn);
        //#else
        OpenGlHelper.glAttachShader(program, shaderIn);
        //#endif
    }

    public static void glLinkProgram(int program) {
        //#if STANDALONE
        //$$ GL20.glLinkProgram(program);
        //#elseif MC>=11502
        //$$ GlStateManager.linkProgram(program);
        //#else
        OpenGlHelper.glLinkProgram(program);
        //#endif
    }

    public static int glGetProgrami(int program, int pname) {
        //#if STANDALONE
        //$$ return GL20.glGetProgrami(program, pname);
        //#elseif MC>=11502
        //$$ return GlStateManager.getProgram(program,pname);
        //#else
        return OpenGlHelper.glGetProgrami(program, pname);
        //#endif
    }

    public static String glGetProgramInfoLog(int program, int maxLen) {
        //#if STANDALONE
        //$$ return GL20.glGetProgramInfoLog(program, maxLen);
        //#elseif MC>=11502
        //$$ return GlStateManager.getProgramInfoLog(program, maxLen);
        //#else
        return OpenGlHelper.glGetProgramInfoLog(program, maxLen);
        //#endif
    }

    public static void color4f(float red, float green, float blue, float alpha) {
        //#if STANDALONE
        //$$ throw new UnsupportedOperationException();
        //#else
        GlStateManager.color(red, green, blue, alpha);
        //#endif
    }

    public static void directColor3f(float red, float green, float blue) {
        //#if MC>=11700
        //$$ color4f(red, green, blue, 1f);
        //#else
        GlStateManager.color(red, green, blue);
        //#endif
    }

    public static void enableDepth() {
        //#if STANDALONE
        //$$ glEnable(GL_DEPTH_TEST);
        //#else
        GlStateManager.enableDepth();
        //#endif
    }

    public static void depthFunc(int mode) {
        //#if STANDALONE
        //$$ glDepthFunc(mode);
        //#else
        GlStateManager.depthFunc(mode);
        //#endif
    }

    public static void depthMask(boolean flag) {
        //#if STANDALONE
        //$$ glDepthMask(flag);
        //#else
        GlStateManager.depthMask(flag);
        //#endif
    }

    public static void disableDepth() {
        //#if STANDALONE
        //$$ glDisable(GL_DEPTH_TEST);
        //#else
        GlStateManager.disableDepth();
        //#endif
    }

    //#if MC>=11700 && !STANDALONE
    //$$ public static void setShader(Supplier<Shader> shader) {
    //$$     RenderSystem.setShader(shader);
    //$$ }
    //#endif

    public enum DrawMode {
        LINES(GL11.GL_LINES),
        LINE_STRIP(GL11.GL_LINE_STRIP),
        TRIANGLES(GL11.GL_TRIANGLES),
        TRIANGLE_STRIP(GL11.GL_TRIANGLE_STRIP),
        TRIANGLE_FAN(GL11.GL_TRIANGLE_FAN),
        QUADS(GL11.GL_QUADS),
        ;

        private final int glMode;
        //#if !STANDALONE
        //#if MC>=11700
        //$$ private final VertexFormat.DrawMode mcMode;
        //#else
        private final int mcMode;
        //#endif
        //#endif

        DrawMode(int glMode) {
            this.glMode = glMode;
            //#if !STANDALONE
            //#if MC>=11700
            //$$ this.mcMode = glToMcDrawMode(glMode);
            //#else
            this.mcMode = glMode;
            //#endif
            //#endif
        }

        //#if MC>=11700 && !STANDALONE
        //$$ private static VertexFormat.DrawMode glToMcDrawMode(int glMode) {
        //$$     switch (glMode) {
        //$$         case GL11.GL_LINES: return VertexFormat.DrawMode.LINES;
        //$$         case GL11.GL_LINE_STRIP: return VertexFormat.DrawMode.LINE_STRIP;
        //$$         case GL11.GL_TRIANGLES: return VertexFormat.DrawMode.TRIANGLES;
        //$$         case GL11.GL_TRIANGLE_STRIP: return VertexFormat.DrawMode.TRIANGLE_STRIP;
        //$$         case GL11.GL_TRIANGLE_FAN: return VertexFormat.DrawMode.TRIANGLE_FAN;
        //$$         case GL11.GL_QUADS: return VertexFormat.DrawMode.QUADS;
        //$$         default: throw new IllegalArgumentException("Unsupported draw mode " + glMode);
        //$$     }
        //$$ }
        //$$
        //$$ private static DrawMode fromMc(VertexFormat.DrawMode mcMode) {
        //$$     switch (mcMode) {
        //$$         case LINES: return DrawMode.LINES;
        //$$         case LINE_STRIP: return DrawMode.LINE_STRIP;
        //$$         case TRIANGLES: return DrawMode.TRIANGLES;
        //$$         case TRIANGLE_STRIP: return DrawMode.TRIANGLE_STRIP;
        //$$         case TRIANGLE_FAN: return DrawMode.TRIANGLE_FAN;
        //$$         case QUADS: return DrawMode.QUADS;
        //$$         default: throw new IllegalArgumentException("Unsupported draw mode " + mcMode);
        //$$     }
        //$$ }
        //#endif

        public static DrawMode fromGl(int glMode) {
            switch (glMode) {
                case GL11.GL_LINES: return LINES;
                case GL11.GL_LINE_STRIP: return LINE_STRIP;
                case GL11.GL_TRIANGLES: return TRIANGLES;
                case GL11.GL_TRIANGLE_STRIP: return TRIANGLE_STRIP;
                case GL11.GL_TRIANGLE_FAN: return TRIANGLE_FAN;
                case GL11.GL_QUADS: return QUADS;
                default: throw new IllegalArgumentException("Unsupported draw mode " + glMode);
            }
        }

        //#if MC>=11600 && !STANDALONE
        //$$ public static DrawMode fromRenderLayer(RenderType renderLayer) {
            //#if MC>=11700
            //$$ return fromMc(renderLayer.getDrawMode());
            //#else
            //$$ return fromGl(renderLayer.getDrawMode());
            //#endif
        //$$ }
        //#endif
    }

    public enum CommonVertexFormats {
        POSITION(DefaultVertexFormats.POSITION),
        POSITION_COLOR(DefaultVertexFormats.POSITION_COLOR),
        POSITION_TEXTURE(DefaultVertexFormats.POSITION_TEX),
        POSITION_TEXTURE_COLOR(DefaultVertexFormats.POSITION_TEX_COLOR),
        POSITION_COLOR_TEXTURE_LIGHT(DefaultVertexFormats.BLOCK),
        /**
         * @deprecated Minecraft removed the built-in shader for this vertex format in 1.20.5, so it is no
         * longer universal across all versions.
         */
        @Deprecated
        POSITION_TEXTURE_LIGHT_COLOR(DefaultVertexFormats.POSITION_TEX_LMAP_COLOR),
        POSITION_TEXTURE_COLOR_LIGHT(DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP),
        /**
         * @deprecated Minecraft removed the built-in shader for this vertex format in 1.20.5, so it is no
         * longer universal across all versions.
         */
        @Deprecated
        POSITION_TEXTURE_COLOR_NORMAL(DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL),
        ;

        public final VertexFormat mc;

        CommonVertexFormats(VertexFormat mc) {
            this.mc = mc;
        }
    }

    public UGraphics beginWithActiveShader(DrawMode mode, CommonVertexFormats format) {
        //#if STANDALONE
        //$$ vertexFormat = format;
        //$$ drawMode = mode;
        //$$ instance = new BufferBuilder(format.mc.getParts());
        //$$ shader = null;
        //$$ return this;
        //#else
        return beginWithActiveShader(mode, format.mc);
        //#endif
    }

    //#if !STANDALONE
    public UGraphics beginWithActiveShader(DrawMode mode, VertexFormat format) {
        vertexFormat = format;
        //#if MC>=12100
        //$$ instance = getTessellator().begin(mode.mcMode, format);
        //#else
        instance.begin(mode.mcMode, format);
        //#endif
        return this;
    }
    //#endif

    //#if MC>=11700 && !STANDALONE
    //$$ // Note: Needs to be an Identity hash map because VertexFormat's equals method is broken (compares via its
    //$$ //       component Map but order very much matters for VertexFormat) as of 1.17
    //$$ private static final Map<VertexFormat, Supplier<Shader>> DEFAULT_SHADERS = new IdentityHashMap<>();
    //$$ static {
    //$$     DEFAULT_SHADERS.put(VertexFormats.LINES, GameRenderer::getRenderTypeLinesShader);
    //$$     DEFAULT_SHADERS.put(VertexFormats.POSITION_TEXTURE_COLOR_LIGHT, GameRenderer::getParticleShader);
    //$$     DEFAULT_SHADERS.put(VertexFormats.POSITION, GameRenderer::getPositionShader);
    //$$     DEFAULT_SHADERS.put(VertexFormats.POSITION_COLOR, GameRenderer::getPositionColorShader);
    //$$     DEFAULT_SHADERS.put(VertexFormats.POSITION_COLOR_LIGHT, GameRenderer::getPositionColorLightmapShader);
    //$$     DEFAULT_SHADERS.put(VertexFormats.POSITION_TEXTURE, GameRenderer::getPositionTexShader);
    //#if MC>=12100
    //$$     // Shader for this format is no longer provided.
    //#else
    //$$     DEFAULT_SHADERS.put(VertexFormats.POSITION_COLOR_TEXTURE, GameRenderer::getPositionColorTexShader);
    //#endif
    //$$     DEFAULT_SHADERS.put(VertexFormats.POSITION_TEXTURE_COLOR, GameRenderer::getPositionTexColorShader);
    //$$     DEFAULT_SHADERS.put(VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, GameRenderer::getPositionColorTexLightmapShader);
    //#if MC>=12005
    //$$     // Shaders for these formats are no longer provided.
    //#else
    //$$     DEFAULT_SHADERS.put(VertexFormats.POSITION_TEXTURE_LIGHT_COLOR, GameRenderer::getPositionTexLightmapColorShader);
    //$$     DEFAULT_SHADERS.put(VertexFormats.POSITION_TEXTURE_COLOR_NORMAL, GameRenderer::getPositionTexColorNormalShader);
    //#endif
    //$$ }
    //#endif

    public UGraphics beginWithDefaultShader(DrawMode mode, CommonVertexFormats format) {
        //#if STANDALONE
        //$$ beginWithActiveShader(mode, format);
        //$$ shader = DefaultShader.Companion.get(format.mc.getParts());
        //$$ return this;
        //#else
        return beginWithDefaultShader(mode, format.mc);
        //#endif
    }

    //#if !STANDALONE
    public UGraphics beginWithDefaultShader(DrawMode mode, VertexFormat format) {
        //#if MC>=11700
        //$$ Supplier<Shader> supplier = DEFAULT_SHADERS.get(format);
        //$$ if (supplier == null) {
        //$$     throw new IllegalArgumentException("No default shader for " + format + ". Bind your own and use beginWithActiveShader instead.");
        //$$ }
        //$$ setShader(supplier);
        //#endif
        return beginWithActiveShader(mode, format);
    }

    //#if MC>=11600
    //$$ private RenderType renderLayer;
    //$$ public UGraphics beginRenderLayer(RenderType renderLayer) {
    //$$     this.renderLayer = renderLayer;
    //$$     beginWithActiveShader(DrawMode.fromRenderLayer(renderLayer), renderLayer.getVertexFormat());
    //$$     return this;
    //$$ }
    //#endif

    @Deprecated // use `beginWithDefaultShader` or `beginWithActiveShader` or `beginRenderLayer` instead
    public UGraphics begin(int glMode, CommonVertexFormats format) {
        return begin(glMode, format.mc);
    }

    @Deprecated // use `beginWithDefaultShader` or `beginWithActiveShader` or `beginRenderLayer` instead
    public UGraphics begin(int glMode, VertexFormat format) {
        //#if MC>=11700
        //$$ beginWithDefaultShader(DrawMode.fromGl(glMode), format);
        //#else
        instance.begin(glMode, format);
        //#endif
        return this;
    }
    //#endif

    public void drawDirect() {
        //#if STANDALONE
        //$$ doDraw();
        //#else
        //#if MC>=12100
        //$$ BuiltBuffer builtBuffer = instance.end();
        //#endif
        //#if MC>=11600
        //$$ if (renderLayer != null) {
            //#if MC>=12100
            //$$ renderLayer.draw(builtBuffer);
            //#elseif MC>=12000
            //$$ renderLayer.draw(instance, RenderSystem.getVertexSorting());
            //#else
            //$$ renderLayer.finish(instance, 0, 0, 0);
            //#endif
        //$$     return;
        //$$ }
        //#endif
        doDraw(
            //#if MC>=12100
            //$$ builtBuffer
            //#endif
        );
        //#endif
    }

    public void drawSorted(int cameraX, int cameraY, int cameraZ) {
        //#if STANDALONE
        //$$ // TODO sorting, if we ever need it
        //$$ doDraw();
        //#else
        //#if MC>=12100
        //$$ BuiltBuffer builtBuffer = instance.end();
        //$$ builtBuffer.sortQuads(SORTED_QUADS_ALLOCATOR, RenderSystem.getVertexSorting());
        //#endif
        //#if MC>=11600
        //$$ if (renderLayer != null) {
            //#if MC>=12100
            //$$ renderLayer.draw(builtBuffer);
            //#elseif MC>=12000
            //$$ renderLayer.draw(instance, RenderSystem.getVertexSorting());
            //#else
            //$$ renderLayer.finish(instance, cameraX, cameraY, cameraZ);
            //#endif
        //$$     return;
        //$$ }
        //#endif
        //#if MC>=12100
        //$$ // Sorting handled above.
        //#elseif MC>=12000
        //$$ instance.setSorter(RenderSystem.getVertexSorting());
        //#elseif MC>=11700
        //$$ instance.setCameraPosition(cameraX, cameraY, cameraZ);
        //#else
        instance.sortVertexData(cameraX, cameraY, cameraZ);
        //#endif
        doDraw(
            //#if MC>=12100
            //$$ builtBuffer
            //#endif
        );
        //#endif
    }

    //#if MC<11700
    private static boolean[] getDesiredTextureUnitState(VertexFormat vertexFormat) {
        // Vanilla only ever has two UV elements, so we can assume the remainder to be disabled by default and don't
        // need to check them unless we want them enabled.
        boolean[] wantEnabled = new boolean[2];
        for (VertexFormatElement element : vertexFormat.getElements()) {
            if (element.getUsage() == VertexFormatElement.EnumUsage.UV) {
                int index = element.getIndex();
                if (wantEnabled.length <= index) {
                    wantEnabled = Arrays.copyOf(wantEnabled, index + 1);
                }
                wantEnabled[index] = true;
            }
        }
        return wantEnabled;
    }
    //#endif

    //#if STANDALONE
    //$$ private void doDraw() {
    //$$     CommonVertexFormats vertexFormat = this.vertexFormat;
    //$$     if (vertexFormat == null) {
    //$$         throw new IllegalStateException("Must call `begin` before `draw`.");
    //$$     }
    //$$     RENDERER.draw(instance, drawMode, shader);
    //$$ }
    //#else
    private void doDraw(
        //#if MC>=12100
        //$$ BuiltBuffer builtBuffer
        //#endif
    ) {
        VertexFormat vertexFormat = this.vertexFormat;
        if (vertexFormat == null) {
            //#if MC>=12100
            //$$ BufferRenderer.drawWithGlobalProgram(builtBuffer);
            //#else
            getTessellator().draw();
            //#endif
            return;
        }

        //#if MC<11700
        boolean[] wantEnabledStates = getDesiredTextureUnitState(vertexFormat);
        boolean[] wasEnabledStates = new boolean[wantEnabledStates.length];
        for (int i = 0; i < wasEnabledStates.length; i++) {
            final int index = i;
            configureTextureUnit(index, () -> {
                boolean isEnabled = wasEnabledStates[index] = GL11.glIsEnabled(GL11.GL_TEXTURE_2D);
                boolean wantEnabled = wantEnabledStates[index];
                if (isEnabled != wantEnabled) {
                    if (wantEnabled) {
                        GlStateManager.enableTexture2D();
                    } else {
                        GlStateManager.disableTexture2D();
                    }
                }
            });
        }
        //#endif

        //#if MC>=12100
        //$$ BufferRenderer.drawWithGlobalProgram(builtBuffer);
        //#else
        getTessellator().draw();
        //#endif

        //#if MC<11700
        for (int i = 0; i < wasEnabledStates.length; i++) {
            if (wasEnabledStates[i] == wantEnabledStates[i]) {
                continue;
            }
            if (wasEnabledStates[i]) {
                configureTextureUnit(i, GlStateManager::enableTexture2D);
            } else {
                configureTextureUnit(i, GlStateManager::disableTexture2D);
            }
        }
        //#endif
    }
    //#endif

    @Deprecated // Pass UMatrixStack as first arg, required for 1.17+
    public UGraphics pos(double x, double y, double z) {
        return pos(UNIT_STACK, x, y, z);
    }

    public UGraphics pos(UMatrixStack stack, double x, double y, double z) {
        //#if STANDALONE
        //$$ asUVertexConsumer().pos(stack, x, y, z);
        //#else
        if (stack == UNIT_STACK) {
            //#if MC>=12100
            //$$ instance.vertex((float) x, (float) y, (float) z);
            //#else
            instance.pos(x, y, z);
            //#endif
        } else {
            //#if MC>=11602
            //$$ instance.pos(stack.peek().getModel(), (float) x, (float) y, (float) z);
            //#else
            Vector4f vec = new Vector4f((float) x, (float) y, (float) z, 1f);
            //#if MC>=11400
            //$$ vec.transform(stack.peek().getModel());
            //#else
            Matrix4f.transform(stack.peek().getModel(), vec, vec);
            //#endif
            instance.pos(vec.getX(), vec.getY(), vec.getZ());
            //#endif
        }
        //#endif
        return this;
    }

    @Deprecated // Pass UMatrixStack as first arg, required for 1.17+
    public UGraphics norm(float x, float y, float z) {
        return norm(UNIT_STACK, x, y, z);
    }

    public UGraphics norm(UMatrixStack stack, float x, float y, float z) {
        //#if STANDALONE
        //$$ asUVertexConsumer().norm(stack, x, y, z);
        //#else
        if (stack == UNIT_STACK) {
            instance.normal(x, y, z);
        } else {
            //#if MC>=12005
            //$$ Vector3f normal = stack.peek().getNormal().transform(x, y, z, new Vector3f());
            //$$ instance.normal(normal.x(), normal.y(), normal.z());
            //#elseif MC>=11602
            //$$ instance.normal(stack.peek().getNormal(), x, y, z);
            //#else
            Vector3f vec = new Vector3f(x, y, z);
            //#if MC>=11400
            //$$ vec.transform(stack.peek().getNormal());
            //#else
            Matrix3f.transform(stack.peek().getNormal(), vec, vec);
            //#endif
            instance.normal(vec.getX(), vec.getY(), vec.getZ());
            //#endif
        }
        //#endif
        return this;
    }

    public UGraphics color(int red, int green, int blue, int alpha) {
        return color(red / 255f, green / 255f, blue / 255f, alpha / 255f);
    }

    public UGraphics color(float red, float green, float blue, float alpha) {
        //#if STANDALONE
        //$$ asUVertexConsumer().color(red, green, blue, alpha);
        //#else
        instance.color(red, green, blue, alpha);
        //#endif
        return this;
    }

    public UGraphics color(Color color) {
        return color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public UGraphics endVertex() {
        //#if STANDALONE
        //$$ instance.endVertex();
        //#elseif MC<12100
        instance.endVertex();
        //#endif
        return this;
    }

    public UGraphics tex(double u, double v) {
        //#if MC>=11502 && !STANDALONE
        //$$ instance.tex((float)u,(float)v);
        //#else
        instance.tex(u, v);
        //#endif
        return this;
    }

    public UGraphics overlay(int u, int v) {
        //#if MC>=11502 && !STANDALONE
        //$$ instance.overlay(u, v);
        //#else
        instance.tex(u, v);
        //#endif
        return this;
    }

    public UGraphics light(int u, int v) {
        //#if STANDALONE
        //$$ asUVertexConsumer().light(u, v);
        //#else
        instance.lightmap(u, v);
        //#endif
        return this;
    }

    /**
     * Using UMatrixStack should be preferred for all versions as direct GL transforms will break in 1.17.
     *
     * These methods are no different than transformation methods in the UGraphics class except they are not deprecated
     * and as such can be used in version-specific code.
     */
    //#if MC<11700
    public static class GL {
        public static void pushMatrix() {
            GlStateManager.pushMatrix();
        }

        public static void popMatrix() {
            GlStateManager.popMatrix();
        }

        public static void translate(float x, float y, float z) {
            GlStateManager.translate(x, y, z);
        }

        public static void translate(double x, double y, double z) {
            GlStateManager.translate(x, y, z);
        }

        public static void rotate(float angle, float x, float y, float z) {
            GlStateManager.rotate(angle, x, y, z);
        }

        public static void scale(float x, float y, float z) {
            GlStateManager.scale(x, y, z);
        }

        public static void scale(double x, double y, double z) {
            GlStateManager.scale(x, y, z);
        }
    }
    //#endif
}
