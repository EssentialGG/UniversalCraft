package club.sk1er.mods.core.universal;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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

//#if MC>=11502 && MC<11602
//$$ import net.minecraft.client.renderer.Quaternion;
//$$ import net.minecraft.client.renderer.Vector3f;
//$$ import com.mojang.blaze3d.systems.RenderSystem;
//$$ import com.mojang.blaze3d.platform.GlStateManager;
//$$ import net.minecraft.client.renderer.BufferBuilder;
//$$ import net.minecraft.client.renderer.IRenderTypeBuffer;
//$$ import net.minecraft.client.renderer.texture.NativeImage;
//$$ import java.io.ByteArrayInputStream;
//$$ import java.io.ByteArrayOutputStream;
//#endif

//#if MC>=11202 && MC<11502
//$$ import net.minecraft.client.renderer.GlStateManager;
//$$ import net.minecraft.client.renderer.BufferBuilder;
//$$ import net.minecraft.client.renderer.OpenGlHelper;
//#endif

//#if MC<=10809
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.WorldRenderer;
//#endif

public class UniversalGraphicsHandler {
    public static int ZERO_TEXT_ALPHA = 10;

    //#if FORGE && MC>=11602
    //$$ public static Style EMPTY_WITH_FONT_ID = Style.EMPTY.setFontId(new ResourceLocation("minecraft", "alt"));
    //#endif

    private static final Pattern formattingCodePattern = Pattern.compile("(?i)" + String.valueOf('\u00a7') + "[0-9A-FK-OR]");

    //#if MC<=10809
    private WorldRenderer instance;
    //#else
    //$$ private BufferBuilder instance;
    //#endif

    //#if MC<=10809
    public UniversalGraphicsHandler(WorldRenderer instance) {
        this.instance = instance;
    }
    //#else
    //$$ public UniversalGraphicsHandler(BufferBuilder instance) {
    //$$     this.instance = instance;
    //$$ }
    //#endif

    //#if MC>=11602
    //$$ private static MatrixStack stack = new MatrixStack();
    //$$ public static MatrixStack getStack() {
    //$$     return stack;
    //$$ }
    //$$
    //$$ public static void setStack(MatrixStack stack) {
    //$$     UniversalGraphicsHandler.stack = stack;
    //$$ }
    //#endif

    public static void pushMatrix() {
        //#if MC<11502
        GlStateManager.pushMatrix();
        //#else
        //#if MC<11602
        //$$ RenderSystem.pushMatrix();
        //#else
        //$$ stack.push();
        //#endif
        //#endif
    }

    public static void popMatrix() {
        //#if MC<11502
        GlStateManager.popMatrix();
        //#else
        //#if MC<11602
        //$$ RenderSystem.popMatrix();
        //#else
        //$$ stack.pop();
        //#endif
        //#endif
    }

    public static UniversalGraphicsHandler getFromTessellator() {
        //#if MC<=10809
        return new UniversalGraphicsHandler(getTessellator().getWorldRenderer());
        //#else
        //$$ return new UniversalGraphicsHandler(getTessellator().getBuffer());
        //#endif
    }

    public static void translate(float x, float y, float z) {
        //#if MC>=11502 && MC<11602
        //$$ RenderSystem.translatef(x, y, z);
        //#else
        translate((double) x, (double) y, (double) z); //Don't remove double casts or this breaks
        //#endif
    }

    public static void translate(double x, double y, double z) {
        //#if MC<11502
        GlStateManager.translate(x, y, z);
        //#else
        //#if MC<11602
        //$$ RenderSystem.translated(x, y, z);
        //#else
        //$$ stack.translate(x, y, z);
        //#endif
        //#endif
    }

    public static void rotate(float angle, float x, float y, float z) {
        //#if MC<11502
        GlStateManager.rotate(angle, x, y, z);
        //#else
        //#if FABRIC
        //$$ stack.multiply(new Quaternion(new Vector3f(x, y, z), angle, true));
        //#else
        //#if MC<11602
        //$$ RenderSystem.rotatef(angle, x, y, z);
        //#else
        //$$ stack.rotate(new Quaternion(new Vector3f(x, y, z), angle, true));
        //#endif
        //#endif
        //#endif
    }

    public static void scale(float x, float y, float z) {
        //#if MC>=11502 && MC<11602
        //$$ RenderSystem.scalef(x, y, z);
        //#else
        scale((double) x, (double) y, (double) z);
        //#endif
    }

    public static void scale(double x, double y, double z) {
        //#if MC<11502
        GlStateManager.scale(x, y, z);
        //#else
        //#if MC<11602
        //$$ RenderSystem.scaled(x, y, z);
        //#else
        //$$ stack.scale((float) x, (float) y, (float) z);
        //#endif
        //#endif
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
        //#else
        //#if MC>10809
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
        //#endif

    }

    public static void enableLighting() {
        //#if MC<=11202
        GlStateManager.enableLighting();
        //#else
        //#if MC<11602
        //$$ RenderSystem.enableLighting();
        //#endif
        //#endif
    }

    public static void disableLighting() {
        //#if MC<=11202
        GlStateManager.disableLighting();
        //#else
        //#if MC<11602
        //$$ RenderSystem.disableLighting();
        //#endif
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
        //#if MC<11502
        GlStateManager.disableTexture2D();
        //#else
        //$$ GlStateManager.disableTexture();
        //#endif

    }
    public static void disableAlpha() {
        //#if MC<11502
        GlStateManager.disableAlpha();
        //#endif
    }

    public static void shadeModel(int mode) {
        //#if MC<11502
        GlStateManager.shadeModel(mode);
        //#else
        //#if MC<11502
        //$$ RenderSystem.shadeModel(mode);
        //#endif
        //#endif
    }

    public static void tryBlendFuncSeparate(int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
        //#if MC<11502
        GlStateManager.tryBlendFuncSeparate(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
        //#else
        //$$ GlStateManager.blendFuncSeparate(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
        //#endif
    }

    public static void enableTexture2D() {
        //#if MC<11502
        GlStateManager.enableTexture2D();
        //#else
        //$$ GlStateManager.enableTexture();
        //#endif
    }

    public static void disableBlend() {
        GlStateManager.disableBlend();
    }

    public static void deleteTexture(int glTextureId) {
        GlStateManager.deleteTexture(glTextureId);
    }

    public static void enableAlpha() {
        //#if MC<11502
        //$$ GlStateManager.enableAlpha();
        //#else
        //#if FORGE
        //$$ GlStateManager.enableAlphaTest();
        //#endif
        //#endif
    }

    public static void bindTexture(int glTextureId) {
        GlStateManager.bindTexture(glTextureId);
    }

    public static int getStringWidth(String in) {
        //#if FABRIC
        //$$ return UniversalMinecraft.getFontRenderer().getWidth(in);
        //#else
        return UniversalMinecraft.getFontRenderer().getStringWidth(in);
        //#endif
    }

    public static void drawString(String text, float x, float y, int color, boolean shadow) {
        if ((color >> 24 & 255) <= 10) return;
        //#if MC<11502
        UniversalMinecraft.getFontRenderer().drawString(text, x, y, color, shadow);
        //#else
        //#if FABRIC
        //$$ VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
        //$$ UniversalMinecraft.getFontRenderer().draw(text, x, y, color, shadow, stack.peek().getModel(), immediate, false, 0, 15728880);
        //#else
        //#if MC<11602
        //$$ if (shadow) {
        //$$     UniversalMinecraft.getFontRenderer().drawStringWithShadow(text, x, y, color);
        //$$ } else {
        //$$     UniversalMinecraft.getFontRenderer().drawString(text, x, y, color);
        //$$ }
        //#else
        //$$ IRenderTypeBuffer.Impl irendertypebuffer$impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
        //$$ UniversalMinecraft.getFontRenderer().renderString(text, x, y, color, shadow, stack.getLast().getMatrix(), irendertypebuffer$impl, false, 0, 15728880);
        //$$ irendertypebuffer$impl.finish();
        //#endif
        //#endif
        //#endif
    }

    public static void drawString(String text, float x, float y, int color, int shadowColor) {
        if ((color >> 24 & 255) <= 10) return;
        //#if MC<11502
        UniversalMinecraft.getFontRenderer().drawString(text, x + 1f, y + 1f, shadowColor, false);
        UniversalMinecraft.getFontRenderer().drawString(text, x, y, color, false);
        //#else
        //#if FABRIC
        //$$ VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
        //$$ UniversalMinecraft.getFontRenderer().draw(text, x + 1f, y + 1f, shadowColor, false, stack.peek().getModel(), immediate, false, 0, 15728880);
        //$$ UniversalMinecraft.getFontRenderer().draw(text, x, y, color, false, stack.peek().getModel(), immediate, false, 0, 15728880);
        //#else
        //#if MC<11602
        //$$ UniversalMinecraft.getFontRenderer().drawString(text, x + 1f, y + 1f, shadowColor);
        //$$ UniversalMinecraft.getFontRenderer().drawString(text, x, y, color);
        //#else
        //$$ IRenderTypeBuffer.Impl irendertypebuffer$impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
        //$$ UniversalMinecraft.getFontRenderer().renderString(text, x + 1f, y + 1f, shadowColor, false, stack.getLast().getMatrix(), irendertypebuffer$impl, false, 0, 15728880);
        //$$ UniversalMinecraft.getFontRenderer().renderString(text, x, y, color, false, stack.getLast().getMatrix(), irendertypebuffer$impl, false, 0, 15728880);
        //$$ irendertypebuffer$impl.finish();
        //#endif
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

        //#if MC<11602
        return UniversalMinecraft.getFontRenderer().listFormattedStringToWidth(str, wrapWidth);
        //#else
        // TODO: Validate this code
        //$$ List<String> strings = new ArrayList<>();
        //$$
        //#if FABRIC
        //$$ TextHandler handler = UniversalMinecraft.getFontRenderer().getTextHandler();
        //$$ List<StringVisitable> visitables = handler.wrapLines(str, wrapWidth, Style.EMPTY);
        //$$ for (StringVisitable visitable : visitables)
        //$$     strings.add(visitable.getString());
        //#else
        //$$ CharacterManager charManager = UniversalMinecraft.getFontRenderer().func_238420_b_();
        //$$ ITextProperties properties = charManager.func_238358_a_(new StringTextComponent(str).mergeStyle(EMPTY_WITH_FONT_ID), wrapWidth, Style.EMPTY);
        // From net.minecraft.util.text.ITextProperties line 88
        //$$ properties.func_230438_a_(string -> {
        //$$     strings.add(string);
        //$$     return Optional.empty();
        //$$ });
        //#endif
        //$$ return strings;
        //#endif
    }

    public static float getCharWidth(char character) {
        //#if FABRIC
        //$$ return UniversalMinecraft.getFontRenderer().getWidth(String.valueOf(character));
        //#else
        //#if MC<11602
        return UniversalMinecraft.getFontRenderer().getCharWidth(character); // float because its a float in 1.15+
        //#else
        //$$ return getStringWidth(String.valueOf(character));
        //#endif
        //#endif
    }

    public static void glClear(int mode) {
        GL11.glClear(mode);
    }

    public static void glClearStencil(int mode) {
        GL11.glClearStencil(mode);
    }

    // TODO: What is the Fabric equivalent of all this?
    //#if FORGE
    public static DynamicTexture getTexture(InputStream stream) {
         try {
             //#if MC<11502
             return new DynamicTexture(ImageIO.read(stream));
             //#else
             //$$ return new DynamicTexture(NativeImage.read(stream));
             //#endif
         } catch (IOException e) {
             e.printStackTrace();
         }
         throw new IllegalStateException("Failed to read image");
     }

    public static DynamicTexture getTexture(BufferedImage img) {
         //#if MC<11502
         return new DynamicTexture(img);
         //#else
         //$$ try {
         //$$     ByteArrayOutputStream baos = new ByteArrayOutputStream();
         //$$     ImageIO.write(img, "png", baos );
         //$$     return new DynamicTexture(NativeImage.read(new ByteArrayInputStream(baos.toByteArray())));
         //$$ } catch (IOException e) {
         //$$     e.printStackTrace();
         //$$ }
         //$$ throw new IllegalStateException("Failed to create texture");
         //#endif
    }

    public static DynamicTexture getEmptyTexture() {
         //#if MC<11502
         return new DynamicTexture(0, 0);
         //#else
         //$$ return new DynamicTexture(0, 0, false);
         //#endif
    }
    //#endif

    public static void glUseProgram(int program) {
        //#if MC<11502
        OpenGlHelper.glUseProgram(program);
        //#else
        //$$ GlStateManager.useProgram(program);
        //#endif
    }

    public static boolean areShadersSupported() {
        //#if MC<11502
        return OpenGlHelper.areShadersSupported();
        //#else
        //$$ return true;
        //#endif
    }

    public static int glCreateProgram() {
        //#if MC<11502
        return OpenGlHelper.glCreateProgram();
        //#else
        //$$ return GlStateManager.createProgram();
        //#endif
    }

    public static int glCreateShader(int type) {
        //#if MC<11502
        return OpenGlHelper.glCreateShader(type);
        //#else
        //$$ return GlStateManager.createShader(type);
        //#endif
    }

    public static void glCompileShader(int shaderIn) {
        //#if MC<11502
        OpenGlHelper.glCompileShader(shaderIn);
        //#else
        //$$ GlStateManager.compileShader(shaderIn);
        //#endif
    }

    public static int glGetShaderi(int shaderIn, int pname) {
        //#if MC<11502
        return OpenGlHelper.glGetShaderi(shaderIn, pname);
        //#else
        //$$ return GlStateManager.getShader(shaderIn,pname);
        //#endif
    }

    public static String glGetShaderInfoLog(int shader, int maxLen) {
        //#if MC<11502
        return OpenGlHelper.glGetShaderInfoLog(shader, maxLen);
        //#else
        //$$ return GlStateManager.getShaderInfoLog( shader,maxLen);
        //#endif
    }

    public static void glAttachShader(int program, int shaderIn) {
        //#if MC<11502
        OpenGlHelper.glAttachShader(program, shaderIn);
        //#else
        //$$ GlStateManager.attachShader(program,shaderIn);
        //#endif
    }

    public static void glLinkProgram(int program) {
        //#if MC<11502
        OpenGlHelper.glLinkProgram(program);
        //#else
        //$$ GlStateManager.linkProgram(program);
        //#endif
    }

    public static int glGetProgrami(int program, int pname) {
        //#if MC<11502
        return OpenGlHelper.glGetProgrami(program, pname);
        //#else
        //$$ return GlStateManager.getProgram(program,pname);
        //#endif
    }

    public static String glGetProgramInfoLog(int program, int maxLen) {
        //#if MC<11502
        return OpenGlHelper.glGetProgramInfoLog(program, maxLen);
        //#else
        //$$ return GlStateManager.getProgramInfoLog(program, maxLen);
        //#endif
    }

    public void begin(int glMode, VertexFormat format) {
        instance.begin(glMode, format);
    }

    public UniversalGraphicsHandler pos(double x, double y, double z) {
        //#if FABRIC
        //$$ instance.vertex(stack.peek().getModel(), (float) x, (float) y, (float) z);
        //#else
        //#if MC<=11502
        instance.pos(x, y, z);
        //#else
        //$$ instance.pos(stack.getLast().getMatrix(), (float) x, (float) y, (float) z);
        //#endif
        //#endif
        return this;
    }

    public UniversalGraphicsHandler color(float red, float green, float blue, float alpha) {
        instance.color(red, green, blue, alpha);
        return this;
    }

    public void endVertex() {
        //#if FABRIC
        //$$ instance.end();
        //#else
        instance.endVertex();
        //#endif
    }

    public UniversalGraphicsHandler tex(double u, double v) {
        //#if FABRIC
        //$$ instance.texture((float) u, (float) v);
        //#else
        //#if MC<11502
        instance.tex(u, v);
        //#else
        //$$ instance.tex((float)u,(float)v);
        //#endif
        //#endif
        return this;
    }
}
