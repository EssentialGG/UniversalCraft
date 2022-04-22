package gg.essential.universal;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import gg.essential.universal.utils.ReleasedDynamicTexture;
import gg.essential.universal.vertex.UVertexConsumer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_BINDING_2D;
import static org.lwjgl.opengl.GL13.GL_ACTIVE_TEXTURE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;

//#if MC>=11700
//$$ import net.minecraft.client.render.GameRenderer;
//$$ import net.minecraft.client.render.Shader;
//$$ import java.util.HashMap;
//$$ import java.util.Map;
//$$ import java.util.function.Supplier;
//#endif

//#if MC>=11602
//$$ import net.minecraft.util.ResourceLocation;
//$$ import net.minecraft.util.text.Style;
//$$ import net.minecraft.util.math.vector.Quaternion;
//$$ import net.minecraft.util.math.vector.Vector3f;
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
//$$ import net.minecraft.client.renderer.BufferBuilder;
//$$ import net.minecraft.client.renderer.texture.NativeImage;
//$$ import java.io.ByteArrayInputStream;
//$$ import java.io.ByteArrayOutputStream;
//#else
import static org.lwjgl.opengl.GL14.glBlendEquation;
//#endif

//#if MC>=11400
//$$ import net.minecraft.client.renderer.texture.Texture;
//#else
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.ITextureObject;
//#endif

public class UGraphics {
    private static final Pattern formattingCodePattern = Pattern.compile("(?i)\u00a7[0-9A-FK-OR]");

    //#if MC>=11602
    //$$ public static Style EMPTY_WITH_FONT_ID = Style.EMPTY.setFontId(new ResourceLocation("minecraft", "alt"));
    //#endif
    private static UMatrixStack UNIT_STACK = new UMatrixStack();
    public static int ZERO_TEXT_ALPHA = 10;
    private WorldRenderer instance;
    private VertexFormat vertexFormat;

    public UGraphics(WorldRenderer instance) {
        this.instance = instance;
    }

    public UVertexConsumer asUVertexConsumer() {
        return UVertexConsumer.of(instance);
    }

    public static UGraphics getFromTessellator() {
        return new UGraphics(getTessellator().getWorldRenderer());
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

    public static Tessellator getTessellator() {
        return Tessellator.getInstance();
    }

    @Deprecated // Use the non-static methods for 1.17+ compatibility or call UGraphics.getTessellator().draw() directly
    public static void draw() {
        getTessellator().draw();
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
        GlStateManager.enableBlend();
    }

    /**
     * @deprecated see {@link #enableTexture2D()}
     */
    @Deprecated
    public static void disableTexture2D() {
        //#if MC>=11502
        //$$ RenderSystem.disableTexture();
        //#else
        GlStateManager.disableTexture2D();
        //#endif

    }

    public static void disableAlpha() {
        //#if MC<11700
        //#if MC>=11502
        //$$ RenderSystem.disableAlphaTest();
        //#else
        GlStateManager.disableAlpha();
        //#endif
        //#endif
    }

    public static void shadeModel(int mode) {
        //#if MC<11700
        GlStateManager.shadeModel(mode);
        //#endif
    }

    public static void blendEquation(int equation) {
        //#if MC>=11500
        //$$ RenderSystem.blendEquation(equation);
        //#else
        glBlendEquation(equation);
        //#endif
    }

    public static void tryBlendFuncSeparate(int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
        //#if MC>=11502
        //$$ RenderSystem.blendFuncSeparate(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
        //#else
        GlStateManager.tryBlendFuncSeparate(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
        //#endif
    }

    /**
     * @deprecated Relies on the global {@link #setActiveTexture(int) activeTexture} state.<br>
     *     Instead of manually managing TEXTURE_2D state, prefer using
     *     {@link #beginWithDefaultShader(DrawMode, VertexFormat)} or any of the other non-deprecated begin methods as
     *     these will set (and restore) the appropriate state for the given {@link VertexFormat} right before/after
     *     rendering.<br>
     *     Also incompatible with OpenGL Core / MC 1.17.
     */
    @Deprecated
    public static void enableTexture2D() {
        //#if MC>=11502
        //$$ RenderSystem.enableTexture();
        //#else
        GlStateManager.enableTexture2D();
        //#endif
    }

    public static void disableBlend() {
        GlStateManager.disableBlend();
    }

    public static void deleteTexture(int glTextureId) {
        GlStateManager.deleteTexture(glTextureId);
    }

    public static void enableAlpha() {
        //#if MC<11700
        //#if MC>=11502
        //$$ RenderSystem.enableAlphaTest();
        //#else
        GlStateManager.enableAlpha();
        //#endif
        //#endif
    }

    public static void configureTexture(int glTextureId, Runnable block) {
        int prevTextureBinding = GL11.glGetInteger(GL_TEXTURE_BINDING_2D);
        GlStateManager.bindTexture(glTextureId);

        block.run();

        GlStateManager.bindTexture(prevTextureBinding);
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
        //#if MC>=11700
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
        //#if MC>=11700
        //$$ RenderSystem.setShaderTexture(GlStateManager._getActiveTexture() - GL_TEXTURE0, glTextureId);
        //#else
        GlStateManager.bindTexture(glTextureId);
        //#endif
    }

    /**
     * @deprecated Relies on the global {@link #setActiveTexture(int) activeTexture} state.<br>
     * Prefer {@link #bindTexture(int, ResourceLocation)} instead.
     */
    @Deprecated
    public static void bindTexture(ResourceLocation resourceLocation) {
        bindTexture(getOrLoadTextureId(resourceLocation));
    }

    public static void bindTexture(int index, int glTextureId) {
        //#if MC>=11700
        //$$ RenderSystem.setShaderTexture(index, glTextureId);
        //#else
        configureTextureUnit(index, () -> GlStateManager.bindTexture(glTextureId));
        //#endif
    }

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

    public static int getStringWidth(String in) {
        return UMinecraft.getFontRenderer().getStringWidth(in);
    }

    public static int getFontHeight() {
        return UMinecraft.getFontRenderer().FONT_HEIGHT;
    }

    @Deprecated // Pass UMatrixStack as first arg, required for 1.17+
    public static void drawString(String text, float x, float y, int color, boolean shadow) {
        drawString(UNIT_STACK, text, x, y, color, shadow);
    }

    public static void drawString(UMatrixStack stack, String text, float x, float y, int color, boolean shadow) {
        if ((color >> 24 & 255) <= 10) return;
        //#if MC>=11602
        //$$ IRenderTypeBuffer.Impl irendertypebuffer$impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
        //$$ UMinecraft.getFontRenderer().renderString(text, x, y, color, shadow, stack.peek().getModel(), irendertypebuffer$impl, false, 0, 15728880);
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
    }

    @Deprecated // Pass UMatrixStack as first arg, required for 1.17+
    public static void drawString(String text, float x, float y, int color, int shadowColor) {
        drawString(UNIT_STACK, text, x, y, color, shadowColor);
    }

    public static void drawString(UMatrixStack stack, String text, float x, float y, int color, int shadowColor) {
        if ((color >> 24 & 255) <= 10) return;
        //#if MC>=11602
        //$$ IRenderTypeBuffer.Impl irendertypebuffer$impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
        //$$ UMinecraft.getFontRenderer().renderString(text, x + 1f, y + 1f, shadowColor, false, stack.peek().getModel(), irendertypebuffer$impl, false, 0, 15728880);
        //$$ UMinecraft.getFontRenderer().renderString(text, x, y, color, false, stack.peek().getModel(), irendertypebuffer$impl, false, 0, 15728880);
        //$$ irendertypebuffer$impl.finish();
        //#else
        if (stack != UNIT_STACK) GL.pushMatrix();
        if (stack != UNIT_STACK) stack.applyToGlobalState();
        //#if MC>=11502
        //$$ UMinecraft.getFontRenderer().drawString(text, x + 1f, y + 1f, shadowColor);
        //$$ UMinecraft.getFontRenderer().drawString(text, x, y, color);
        //#else
        UMinecraft.getFontRenderer().drawString(text, x + 1f, y + 1f, shadowColor, false);
        UMinecraft.getFontRenderer().drawString(text, x, y, color, false);
        //#endif
        if (stack != UNIT_STACK) GL.popMatrix();
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

        //#if MC>=11602
        //$$ // TODO: Validate this code
        //$$ List<String> strings = new ArrayList<>();
        //$$
        //$$ CharacterManager charManager = UMinecraft.getFontRenderer().getCharacterManager();
        //$$ ITextProperties properties = charManager.func_238358_a_(new StringTextComponent(str).mergeStyle(EMPTY_WITH_FONT_ID), wrapWidth, Style.EMPTY);
        //$$ // From net.minecraft.util.text.ITextProperties line 88
        //$$ properties.getComponent(string -> {
        //$$     strings.add(string);
        //$$     return Optional.empty();
        //$$ });
        //$$ return strings;
        //#else
        return UMinecraft.getFontRenderer().listFormattedStringToWidth(str, wrapWidth);
        //#endif
    }

    public static float getCharWidth(char character) {
        //#if MC>=11602
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
            //#if MC>=11502
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
        //#if MC>=11502
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
        //#if MC>=11502
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
        //#if MC>=11502
        //$$ return GlStateManager.createProgram();
        //#else
        return OpenGlHelper.glCreateProgram();
        //#endif
    }

    public static int glCreateShader(int type) {
        //#if MC>=11502
        //$$ return GlStateManager.createShader(type);
        //#else
        return OpenGlHelper.glCreateShader(type);
        //#endif
    }

    public static void glCompileShader(int shaderIn) {
        //#if MC>=11502
        //$$ GlStateManager.compileShader(shaderIn);
        //#else
        OpenGlHelper.glCompileShader(shaderIn);
        //#endif
    }

    public static int glGetShaderi(int shaderIn, int pname) {
        //#if MC>=11502
        //$$ return GlStateManager.getShader(shaderIn,pname);
        //#else
        return OpenGlHelper.glGetShaderi(shaderIn, pname);
        //#endif
    }

    public static String glGetShaderInfoLog(int shader, int maxLen) {
        //#if MC>=11502
        //$$ return GlStateManager.getShaderInfoLog( shader,maxLen);
        //#else
        return OpenGlHelper.glGetShaderInfoLog(shader, maxLen);
        //#endif
    }

    public static void glAttachShader(int program, int shaderIn) {
        //#if MC>=11502
        //$$ GlStateManager.attachShader(program,shaderIn);
        //#else
        OpenGlHelper.glAttachShader(program, shaderIn);
        //#endif
    }

    public static void glLinkProgram(int program) {
        //#if MC>=11502
        //$$ GlStateManager.linkProgram(program);
        //#else
        OpenGlHelper.glLinkProgram(program);
        //#endif
    }

    public static int glGetProgrami(int program, int pname) {
        //#if MC>=11502
        //$$ return GlStateManager.getProgram(program,pname);
        //#else
        return OpenGlHelper.glGetProgrami(program, pname);
        //#endif
    }

    public static String glGetProgramInfoLog(int program, int maxLen) {
        //#if MC>=11502
        //$$ return GlStateManager.getProgramInfoLog(program, maxLen);
        //#else
        return OpenGlHelper.glGetProgramInfoLog(program, maxLen);
        //#endif
    }

    public static void color4f(float red, float green, float blue, float alpha) {
        //#if MC<11502
        GlStateManager.color(red, green, blue, alpha);
        //#else
        //$$ RenderSystem.color4f(red, green, blue, alpha);
        //#endif
    }

    public static void directColor3f(float red, float green, float blue) {
        //#if MC>=11700
        //$$ color4f(red, green, blue, 1f);
        //#else
        //#if MC<11502
        GlStateManager.color(red, green, blue);
        //#else
        //$$ RenderSystem.color3f(red, green, blue);
        //#endif
        //#endif
    }

    public static void enableDepth() {
        //#if MC<11502
        GlStateManager.enableDepth();
        //#else
        //$$ RenderSystem.enableDepthTest();
        //#endif
    }

    public static void depthFunc(int mode) {
        GlStateManager.depthFunc(mode);
    }

    public static void depthMask(boolean flag) {
        GlStateManager.depthMask(flag);
    }

    public static void disableDepth() {
        //#if MC<11502
        GlStateManager.disableDepth();
        //#else
        //$$ RenderSystem.disableDepthTest();
        //#endif
    }

    //#if MC>=11700
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
        //#if MC>=11700
        //$$ private final VertexFormat.DrawMode mcMode;
        //#else
        private final int mcMode;
        //#endif

        DrawMode(int glMode) {
            this.glMode = glMode;
            //#if MC>=11700
            //$$ this.mcMode = glToMcDrawMode(glMode);
            //#else
            this.mcMode = glMode;
            //#endif
        }

        //#if MC>=11700
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

        //#if MC>=11600
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
        POSITION_TEXTURE_LIGHT_COLOR(DefaultVertexFormats.POSITION_TEX_LMAP_COLOR),
        POSITION_TEXTURE_COLOR_LIGHT(DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP),
        POSITION_TEXTURE_COLOR_NORMAL(DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL),
        ;

        private final VertexFormat mc;

        CommonVertexFormats(VertexFormat mc) {
            this.mc = mc;
        }
    }

    public UGraphics beginWithActiveShader(DrawMode mode, CommonVertexFormats format) {
        return beginWithActiveShader(mode, format.mc);
    }

    public UGraphics beginWithActiveShader(DrawMode mode, VertexFormat format) {
        vertexFormat = format;
        instance.begin(mode.mcMode, format);
        return this;
    }

    //#if MC>=11700
    //$$ private static final Map<VertexFormat, Supplier<Shader>> DEFAULT_SHADERS = new HashMap<>();
    //$$ static {
    //$$     DEFAULT_SHADERS.put(VertexFormats.LINES, GameRenderer::getRenderTypeLinesShader);
    //$$     DEFAULT_SHADERS.put(VertexFormats.POSITION_TEXTURE_COLOR_LIGHT, GameRenderer::getParticleShader);
    //$$     DEFAULT_SHADERS.put(VertexFormats.POSITION, GameRenderer::getPositionShader);
    //$$     DEFAULT_SHADERS.put(VertexFormats.POSITION_COLOR, GameRenderer::getPositionColorShader);
    //$$     DEFAULT_SHADERS.put(VertexFormats.POSITION_COLOR_LIGHT, GameRenderer::getPositionColorLightmapShader);
    //$$     DEFAULT_SHADERS.put(VertexFormats.POSITION_TEXTURE, GameRenderer::getPositionTexShader);
    //$$     DEFAULT_SHADERS.put(VertexFormats.POSITION_COLOR_TEXTURE, GameRenderer::getPositionColorTexShader);
    //$$     DEFAULT_SHADERS.put(VertexFormats.POSITION_TEXTURE_COLOR, GameRenderer::getPositionTexColorShader);
    //$$     DEFAULT_SHADERS.put(VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, GameRenderer::getPositionColorTexLightmapShader);
    //$$     DEFAULT_SHADERS.put(VertexFormats.POSITION_TEXTURE_LIGHT_COLOR, GameRenderer::getPositionTexLightmapColorShader);
    //$$     DEFAULT_SHADERS.put(VertexFormats.POSITION_TEXTURE_COLOR_NORMAL, GameRenderer::getPositionTexColorNormalShader);
    //$$ }
    //#endif

    public UGraphics beginWithDefaultShader(DrawMode mode, CommonVertexFormats format) {
        return beginWithDefaultShader(mode, format.mc);
    }

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

    public void drawDirect() {
        //#if MC>=11600
        //$$ if (renderLayer != null) {
        //$$     renderLayer.finish(instance, 0, 0, 0);
        //$$     return;
        //$$ }
        //#endif
        doDraw();
    }

    public void drawSorted(int cameraX, int cameraY, int cameraZ) {
        //#if MC>=11600
        //$$ if (renderLayer != null) {
        //$$     renderLayer.finish(instance, cameraX, cameraY, cameraZ);
        //$$     return;
        //$$ }
        //#endif
        //#if MC>=11700
        //$$ instance.setCameraPosition(cameraX, cameraY, cameraZ);
        //#else
        instance.sortVertexData(cameraX, cameraY, cameraZ);
        //#endif
        doDraw();
    }

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

    private void doDraw() {
        VertexFormat vertexFormat = this.vertexFormat;
        if (vertexFormat == null) {
            getTessellator().draw();
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

        getTessellator().draw();

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

    @Deprecated // Pass UMatrixStack as first arg, required for 1.17+
    public UGraphics pos(double x, double y, double z) {
        return pos(UNIT_STACK, x, y, z);
    }

    public UGraphics pos(UMatrixStack stack, double x, double y, double z) {
        //#if MC>=11602
        //$$ instance.pos(stack.peek().getModel(), (float) x, (float) y, (float) z);
        //#else
        if (stack == UNIT_STACK) {
            instance.pos(x, y, z);
        } else {
            Vector4f vec = new Vector4f((float) x, (float) y, (float) z, 1f);
            //#if MC>=11400
            //$$ vec.transform(stack.peek().getModel());
            //#else
            Matrix4f.transform(stack.peek().getModel(), vec, vec);
            //#endif
            instance.pos(vec.getX(), vec.getY(), vec.getZ());
        }
        //#endif
        return this;
    }

    @Deprecated // Pass UMatrixStack as first arg, required for 1.17+
    public UGraphics norm(float x, float y, float z) {
        return norm(UNIT_STACK, x, y, z);
    }

    public UGraphics norm(UMatrixStack stack, float x, float y, float z) {
        //#if MC>=11602
        //$$ instance.normal(stack.peek().getNormal(), x, y, z);
        //#else
        if (stack == UNIT_STACK) {
            instance.normal(x, y, z);
        } else {
            Vector3f vec = new Vector3f(x, y, z);
            //#if MC>=11400
            //$$ vec.transform(stack.peek().getNormal());
            //#else
            Matrix3f.transform(stack.peek().getNormal(), vec, vec);
            //#endif
            instance.normal(vec.getX(), vec.getY(), vec.getZ());
        }
        //#endif
        return this;
    }

    public UGraphics color(int red, int green, int blue, int alpha) {
        return color(red / 255f, green / 255f, blue / 255f, alpha / 255f);
    }

    public UGraphics color(float red, float green, float blue, float alpha) {
        instance.color(red, green, blue, alpha);
        return this;
    }

    public UGraphics color(Color color) {
        return color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public UGraphics endVertex() {
        instance.endVertex();
        return this;
    }

    public UGraphics tex(double u, double v) {
        //#if MC>=11502
        //$$ instance.tex((float)u,(float)v);
        //#else
        instance.tex(u, v);
        //#endif
        return this;
    }

    public UGraphics overlay(int u, int v) {
        //#if MC>=11502
        //$$ instance.overlay(u, v);
        //#else
        instance.tex(u, v);
        //#endif
        return this;
    }

    public UGraphics light(int u, int v) {
        instance.lightmap(u, v);
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
            //#if MC>=11502
            //$$ RenderSystem.translatef(x, y, z);
            //#else
            translate(x, y, (double) z);
            //#endif
        }

        public static void translate(double x, double y, double z) {
            //#if MC>=11502
            //$$ RenderSystem.translated(x, y, z);
            //#else
            GlStateManager.translate(x, y, z);
            //#endif
        }

        public static void rotate(float angle, float x, float y, float z) {
            //#if MC>=11502
            //$$ RenderSystem.rotatef(angle, x, y, z);
            //#else
            GlStateManager.rotate(angle, x, y, z);
            //#endif
        }

        public static void scale(float x, float y, float z) {
            //#if MC>=11502
            //$$ RenderSystem.scalef(x, y, z);
            //#else
            scale(x, y, (double) z);
            //#endif
        }

        public static void scale(double x, double y, double z) {
            //#if MC>=11502
            //$$ RenderSystem.scaled(x, y, z);
            //#else
            GlStateManager.scale(x, y, z);
            //#endif
        }
    }
    //#endif
}
