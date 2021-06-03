package gg.essential.universal;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Pattern;

import gg.essential.universal.utils.ReleasedDynamicTexture;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.VertexFormat;
import org.lwjgl.opengl.GL11;

//#if MC>=11602
//$$ import net.minecraft.util.ResourceLocation;
//$$ import net.minecraft.util.text.Style;
//$$ import net.minecraft.util.math.vector.Quaternion;
//$$ import net.minecraft.util.math.vector.Vector3f;
//$$ import net.minecraft.util.text.CharacterManager;
//$$ import net.minecraft.util.text.StringTextComponent;
//$$ import net.minecraft.util.text.ITextProperties;
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
//#endif

//#if MC>=11400
//#else
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.WorldRenderer;
//#endif

public class UGraphics {
    private static final Pattern formattingCodePattern = Pattern.compile("(?i)\u00a7[0-9A-FK-OR]");

    //#if MC>=11602
    //$$ public static Style EMPTY_WITH_FONT_ID = Style.EMPTY.setFontId(new ResourceLocation("minecraft", "alt"));
    //#endif
    private static UMatrixStack UNIT_STACK = new UMatrixStack();
    public static int ZERO_TEXT_ALPHA = 10;
    private WorldRenderer instance;

    public UGraphics(WorldRenderer instance) {
        this.instance = instance;
    }

    @Deprecated // See UGraphics.GL
    public static void pushMatrix() {
        GL.pushMatrix();
    }

    @Deprecated // See UGraphics.GL
    public static void popMatrix() {
        GL.popMatrix();
    }

    public static UGraphics getFromTessellator() {
        return new UGraphics(getTessellator().getWorldRenderer());
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

    public static Tessellator getTessellator() {
        return Tessellator.getInstance();
    }

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
        GlStateManager.enableLighting();
    }

    public static void disableLighting() {
        GlStateManager.disableLighting();
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

    public static void disableTexture2D() {
        //#if MC>=11502
        //$$ RenderSystem.disableTexture();
        //#else
        GlStateManager.disableTexture2D();
        //#endif

    }

    public static void disableAlpha() {
        //#if MC>=11502
        //$$ RenderSystem.disableAlphaTest();
        //#else
        GlStateManager.disableAlpha();
        //#endif
    }

    public static void shadeModel(int mode) {
        GlStateManager.shadeModel(mode);
    }

    public static void tryBlendFuncSeparate(int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
        //#if MC>=11502
        //$$ RenderSystem.blendFuncSeparate(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
        //#else
        GlStateManager.tryBlendFuncSeparate(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
        //#endif
    }

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
        //#if MC>=11502
        //$$ RenderSystem.enableAlphaTest();
        //#else
        GlStateManager.enableAlpha();
        //#endif
    }

    public static void bindTexture(int glTextureId) {
        GlStateManager.bindTexture(glTextureId);
    }

    public static int getStringWidth(String in) {
        return UMinecraft.getFontRenderer().getStringWidth(in);
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
        // TODO: Validate this code
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
        //#if MC>=11502
        //$$ return new ReleasedDynamicTexture(0, 0, false);
        //#else
        return new ReleasedDynamicTexture(0, 0);
        //#endif
    }

    public static void glUseProgram(int program) {
        //#if MC>=11502
        //$$ GlStateManager.useProgram(program);
        //#else
        OpenGlHelper.glUseProgram(program);
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
        //#if MC<11502
        GlStateManager.color(red, green, blue);
        //#else
        //$$ RenderSystem.color3f(red, green, blue);
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

    public UGraphics begin(int glMode, VertexFormat format) {
        instance.begin(glMode, format);
        return this;
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

    /**
     * Using UMatrixStack should be preferred for all versions as direct GL transforms will break in 1.17.
     *
     * These methods are no different than transformation methods in the UGraphics class except they are not deprecated
     * and as such can be used in version-specific code.
     */
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
}
