package club.sk1er.mods.core.universal;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Pattern;

//#if MC>=11602
//$$ import java.util.ArrayList;
//#endif

//#if FABRIC
//$$ import com.mojang.blaze3d.platform.GlStateManager;
//$$ import net.minecraft.client.font.TextHandler;
//$$ import net.minecraft.client.render.BufferBuilder;
//$$ import net.minecraft.client.render.Tessellator;
//$$ import net.minecraft.client.render.VertexConsumerProvider;
//$$ import net.minecraft.client.render.VertexFormat;
//$$ import net.minecraft.client.util.math.MatrixStack;
//$$ import net.minecraft.client.util.math.Vector3f;
//$$ import net.minecraft.text.StringVisitable;
//$$ import net.minecraft.text.Style;
//$$ import net.minecraft.util.math.Quaternion;
//$$ import org.lwjgl.opengl.GL11;
//$$ import net.minecraft.client.texture.NativeImage;
//$$ import net.minecraft.client.texture.NativeImageBackedTexture;
//$$ import java.io.ByteArrayInputStream;
//$$ import java.io.ByteArrayOutputStream;
//#else
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.VertexFormat;
import org.lwjgl.opengl.GL11;
//#endif

//#if FORGE && MC>=11602
//$$ import net.minecraft.util.math.vector.Quaternion;
//$$ import net.minecraft.util.ResourceLocation;
//$$ import net.minecraft.util.text.Style;
//$$ import net.minecraft.util.math.vector.Vector3f;
//$$ import net.minecraft.util.text.CharacterManager;
//$$ import net.minecraft.util.text.StringTextComponent;
//$$ import net.minecraft.util.text.ITextProperties;
//$$ import com.mojang.blaze3d.platform.GlStateManager;
//$$ import com.mojang.blaze3d.matrix.MatrixStack;
//$$ import net.minecraft.client.renderer.texture.NativeImage;
//$$ import net.minecraft.client.renderer.IRenderTypeBuffer;
//$$ import java.io.ByteArrayInputStream;
//$$ import java.io.ByteArrayOutputStream;
//$$ import net.minecraft.client.renderer.BufferBuilder;
//$$ import java.util.ArrayList;
//$$ import java.util.List;
//$$ import java.util.Optional;
//#endif

//#if MC>=11602
//$$ import com.mojang.blaze3d.systems.RenderSystem;
//#elseif MC==11502
//$$ import net.minecraft.client.renderer.Quaternion;
//$$ import net.minecraft.client.renderer.Vector3f;
//$$ import com.mojang.blaze3d.systems.RenderSystem;
//$$ import com.mojang.blaze3d.platform.GlStateManager;
//$$ import net.minecraft.client.renderer.BufferBuilder;
//$$ import net.minecraft.client.renderer.IRenderTypeBuffer;
//$$ import net.minecraft.client.renderer.texture.NativeImage;
//$$ import java.io.ByteArrayInputStream;
//$$ import java.io.ByteArrayOutputStream;
//#elseif MC==11202
//$$ import net.minecraft.client.renderer.GlStateManager;
//$$ import net.minecraft.client.renderer.BufferBuilder;
//$$ import net.minecraft.client.renderer.OpenGlHelper;
//#elseif MC==10809
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.WorldRenderer;
//#endif

public class UGraphics {
    private static final Pattern formattingCodePattern = Pattern.compile("(?i)\u00a7[0-9A-FK-OR]");

    //#if FORGE && MC>=11602
    //$$ public static Style EMPTY_WITH_FONT_ID = Style.EMPTY.setFontId(new ResourceLocation("minecraft", "alt"));
    //#endif
    public static int ZERO_TEXT_ALPHA = 10;
    //#if MC>10809
    //$$ private BufferBuilder instance;
    //#else
    private WorldRenderer instance;
    //#endif

    //#if MC>10809
    //$$ public UGraphics(BufferBuilder instance) {
    //$$     this.instance = instance;
    //$$ }
    //#else
    public UGraphics(WorldRenderer instance) {
        this.instance = instance;
    }
    //#endif

    //#if MC>=11602
    //$$ private static MatrixStack stack = new MatrixStack();
    //$$ public static MatrixStack getStack() {
    //$$     return stack;
    //$$ }
    //$$
    //$$ public static void setStack(MatrixStack stack) {
    //$$     UGraphics.stack = stack;
    //$$ }
    //#endif

    public static void pushMatrix() {
        //#if MC>=11602
        //$$ stack.push();
        //#elseif MC>=11502
        //$$ RenderSystem.pushMatrix();
        //#else
        GlStateManager.pushMatrix();
        //#endif
    }

    public static void popMatrix() {
        //#if MC>=11602
        //$$ stack.pop();
        //#elseif MC>=11502
        //$$ RenderSystem.popMatrix();
        //#else
        GlStateManager.popMatrix();
        //#endif
    }

    public static UGraphics getFromTessellator() {
        //#if MC>10809
        //$$ return new UGraphics(getTessellator().getBuffer());
        //#else
        return new UGraphics(getTessellator().getWorldRenderer());
        //#endif
    }

    public static void translate(float x, float y, float z) {
        //#if MC==11502
        //$$ RenderSystem.translatef(x, y, z);
        //#else
        translate(x, y, (double) z); //Don't remove double casts or this breaks
        //#endif
    }

    public static void translate(double x, double y, double z) {
        //#if MC>=11602
        //$$ stack.translate(x, y, z);
        //#elseif MC>=11502
        //$$ RenderSystem.translated(x, y, z);
        //#else
        GlStateManager.translate(x, y, z);
        //#endif
    }

    public static void rotate(float angle, float x, float y, float z) {
        //#if FABRIC
        //$$ stack.multiply(new Quaternion(new Vector3f(x, y, z), angle, true));
        //#elseif MC>=11602
        //$$ stack.rotate(new Quaternion(new Vector3f(x, y, z), angle, true));
        //#elseif MC>=11502
        //$$ RenderSystem.rotatef(angle, x, y, z);
        //#else
        GlStateManager.rotate(angle, x, y, z);
        //#endif
    }

    public static void scale(float x, float y, float z) {
        //#if MC==11502
        //$$ RenderSystem.scalef(x, y, z);
        //#else
        scale(x, y, (double) z);
        //#endif
    }

    public static void scale(double x, double y, double z) {
        //#if MC>=11602
        //$$ stack.scale((float) x, (float) y, (float) z);
        //#elseif MC>=11502
        //$$ RenderSystem.scaled(x, y, z);
        //#else
        GlStateManager.scale(x, y, z);
        //#endif
    }

    public static Tessellator getTessellator() {
        return Tessellator.getInstance();
    }

    public static void draw() {
        //#if FABRIC
        //$$ getTessellator().getBuffer().end();
        //$$ BufferRenderer.draw(getTessellator().getBuffer());
        //#else
        getTessellator().draw();
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
        //#if MC==11502
        //$$ RenderSystem.enableLighting();
        //#elseif MC<=11202
        GlStateManager.enableLighting();
        //#endif
    }

    public static void disableLighting() {
        //#if MC==11502
        //$$ RenderSystem.disableLighting();
        //#elseif MC<=11202
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

    public static void disableTexture2D() {
        //#if MC>=11502
        //$$ GlStateManager.disableTexture();
        //#else
        GlStateManager.disableTexture2D();
        //#endif

    }

    public static void disableAlpha() {
        //#if MC<11502
        GlStateManager.disableAlpha();
        //#endif
    }

    public static void shadeModel(int mode) {
        //#if MC==11502
        //$$ RenderSystem.shadeModel(mode);
        //#elseif MC<11502
        GlStateManager.shadeModel(mode);
        //#endif
    }

    public static void tryBlendFuncSeparate(int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
        //#if MC>=11502
        //$$ GlStateManager.blendFuncSeparate(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
        //#else
        GlStateManager.tryBlendFuncSeparate(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
        //#endif
    }

    public static void enableTexture2D() {
        //#if MC>=11502
        //$$ GlStateManager.enableTexture();
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
        //#if FORGE && MC>=11502
        //$$ GlStateManager.enableAlphaTest();
        //#elseif FORGE
        GlStateManager.enableAlpha();
        //#endif
    }

    public static void bindTexture(int glTextureId) {
        GlStateManager.bindTexture(glTextureId);
    }

    public static int getStringWidth(String in) {
        //#if FABRIC
        //$$ return UMinecraft.getFontRenderer().getWidth(in);
        //#else
        return UMinecraft.getFontRenderer().getStringWidth(in);
        //#endif
    }

    public static void drawString(String text, float x, float y, int color, boolean shadow) {
        if ((color >> 24 & 255) <= 10) return;
        //#if FABRIC
        //$$ VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
        //$$ UMinecraft.getFontRenderer().draw(text, x, y, color, shadow, stack.peek().getModel(), immediate, false, 0, 15728880);
        //#elseif MC>=11602
        //$$ IRenderTypeBuffer.Impl irendertypebuffer$impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
        //$$ UMinecraft.getFontRenderer().renderString(text, x, y, color, shadow, stack.getLast().getMatrix(), irendertypebuffer$impl, false, 0, 15728880);
        //$$ irendertypebuffer$impl.finish();
        //#elseif MC>=11502
        //$$ if (shadow) {
        //$$     UMinecraft.getFontRenderer().drawStringWithShadow(text, x, y, color);
        //$$ } else {
        //$$     UMinecraft.getFontRenderer().drawString(text, x, y, color);
        //$$ }
        //#else
        UMinecraft.getFontRenderer().drawString(text, x, y, color, shadow);
        //#endif
    }

    public static void drawString(String text, float x, float y, int color, int shadowColor) {
        if ((color >> 24 & 255) <= 10) return;
        //#if FABRIC
        //$$ VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
        //$$ UMinecraft.getFontRenderer().draw(text, x + 1f, y + 1f, shadowColor, false, stack.peek().getModel(), immediate, false, 0, 15728880);
        //$$ UMinecraft.getFontRenderer().draw(text, x, y, color, false, stack.peek().getModel(), immediate, false, 0, 15728880);
        //#elseif MC>=11602
        //$$ IRenderTypeBuffer.Impl irendertypebuffer$impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
        //$$ UMinecraft.getFontRenderer().renderString(text, x + 1f, y + 1f, shadowColor, false, stack.getLast().getMatrix(), irendertypebuffer$impl, false, 0, 15728880);
        //$$ UMinecraft.getFontRenderer().renderString(text, x, y, color, false, stack.getLast().getMatrix(), irendertypebuffer$impl, false, 0, 15728880);
        //$$ irendertypebuffer$impl.finish();
        //#elseif MC>=11502
        //$$ UMinecraft.getFontRenderer().drawString(text, x + 1f, y + 1f, shadowColor);
        //$$ UMinecraft.getFontRenderer().drawString(text, x, y, color);
        //#else
        UMinecraft.getFontRenderer().drawString(text, x + 1f, y + 1f, shadowColor, false);
        UMinecraft.getFontRenderer().drawString(text, x, y, color, false);
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
        //#if FABRIC
        //$$ TextHandler handler = UMinecraft.getFontRenderer().getTextHandler();
        //$$ List<StringVisitable> visitables = handler.wrapLines(str, wrapWidth, Style.EMPTY);
        //$$ for (StringVisitable visitable : visitables)
        //$$     strings.add(visitable.getString());
        //#else
        //$$ CharacterManager charManager = UMinecraft.getFontRenderer().func_238420_b_();
        //$$ ITextProperties properties = charManager.func_238358_a_(new StringTextComponent(str).mergeStyle(EMPTY_WITH_FONT_ID), wrapWidth, Style.EMPTY);
        //$$ // From net.minecraft.util.text.ITextProperties line 88
        //$$ properties.func_230438_a_(string -> {
        //$$     strings.add(string);
        //$$     return Optional.empty();
        //$$ });
        //#endif
        //$$ return strings;
        //#else
        return UMinecraft.getFontRenderer().listFormattedStringToWidth(str, wrapWidth);
        //#endif
    }

    public static float getCharWidth(char character) {
        //#if FABRIC
        //$$ return UMinecraft.getFontRenderer().getWidth(String.valueOf(character));
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

    //#if FORGE
    public static DynamicTexture getTexture(InputStream stream) {
        try {
            //#if MC>=11502
            //$$ return new DynamicTexture(NativeImage.read(stream));
            //#else
            return new DynamicTexture(ImageIO.read(stream));
            //#endif
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Failed to read image");
    }

    public static DynamicTexture getTexture(BufferedImage img) {
        //#if MC>=11502
        //$$ try {
        //$$     ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //$$     ImageIO.write(img, "png", baos );
        //$$     return new DynamicTexture(NativeImage.read(new ByteArrayInputStream(baos.toByteArray())));
        //$$ } catch (IOException e) {
        //$$     e.printStackTrace();
        //$$ }
        //$$ throw new IllegalStateException("Failed to create texture");
        //#else
        return new DynamicTexture(img);
        //#endif
    }

    public static DynamicTexture getEmptyTexture() {
        //#if MC>=11502
        //$$ return new DynamicTexture(0, 0, false);
        //#else
        return new DynamicTexture(0, 0);
        //#endif
    }
    //#endif
    //#if FABRIC
    //$$ public static NativeImageBackedTexture getTexture(InputStream stream) {
    //$$     try {
    //$$         return new NativeImageBackedTexture(NativeImage.read(stream));
    //$$     } catch (IOException e) {
    //$$            e.printStackTrace();
    //$$     }
    //$$     throw new IllegalStateException("Failed to read image");
    //$$ }
    //$$
    //$$ public static NativeImageBackedTexture getTexture(BufferedImage img) {
    //$$     try {
    //$$         ByteArrayOutputStream baos = new ByteArrayOutputStream();
    //$$         ImageIO.write(img, "png", baos );
    //$$         return new NativeImageBackedTexture(NativeImage.read(new ByteArrayInputStream(baos.toByteArray())));
    //$$     } catch (IOException e) {
    //$$          e.printStackTrace();
    //$$     }
    //$$     throw new IllegalStateException("Failed to create texture");
    //$$ }
    //$$
    //$$  public static NativeImageBackedTexture getEmptyTexture() {
    //$$      return new NativeImageBackedTexture(0, 0, false);
    //$$  }
    //#endif

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

    public UGraphics pos(double x, double y, double z) {
        //#if FABRIC
        //$$ instance.vertex(stack.peek().getModel(), (float) x, (float) y, (float) z);
        //#else
        //#if MC>=11602
        //$$ instance.pos(stack.getLast().getMatrix(), (float) x, (float) y, (float) z);
        //#else
        instance.pos(x, y, z);
        //#endif
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
        //#if MC<11502
        instance.endVertex();
        //#endif
        return this;
    }

    public UGraphics tex(double u, double v) {
        //#if FABRIC
        //$$ instance.texture((float) u, (float) v);
        //#elseif MC>=11502
        //$$ instance.tex((float)u,(float)v);
        //#else
        instance.tex(u, v);
        //#endif
        return this;
    }

    // A collection of methods for always calling the OpenGL transformations rather than
    // delegating to the MatrixStack. In versions less than 1.15.2, these methods are no
    // different than transformation methods in the UGraphics class.
    //
    // The other transformation methods should be preferred.
    public static class GL {
        public static void pushMatrix() {
            //#if MC>=11502
            //$$ RenderSystem.pushMatrix();
            //#else
            GlStateManager.pushMatrix();
            //#endif
        }

        public static void popMatrix() {
            //#if MC>=11502
            //$$ RenderSystem.popMatrix();
            //#else
            GlStateManager.popMatrix();
            //#endif
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
