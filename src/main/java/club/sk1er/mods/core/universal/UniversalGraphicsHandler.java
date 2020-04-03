package club.sk1er.mods.core.universal;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.VertexFormat;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

//#if MC>=11500
//$$ import com.mojang.blaze3d.matrix.MatrixStack;
//$$ import net.minecraft.client.renderer.Vector3f;
//$$ import java.io.ByteArrayOutputStream;
//$$ import javax.imageio.ImageIO;
//$$ import java.io.IOException;
//$$ import java.nio.ByteBuffer;
//$$ import net.minecraft.client.renderer.texture.NativeImage;
//$$ import net.minecraft.client.renderer.IRenderTypeBuffer;
//$$ import java.io.ByteArrayInputStream;
//#else
import net.minecraft.client.renderer.OpenGlHelper;

//#endif

public class UniversalGraphicsHandler {
    //#if MC<11500
    public static int ZERO_TEXT_ALPHA = 10;
    //#else
    //$$ public static int ZERO_TEXT_ALPHA = 1;
    //#endif

    //#if MC<=10809
    private WorldRenderer instance;

    //#else
    //$$ private BufferBuilder instance;
    //$$
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
    //#if MC>=11502
    //$$private static MatrixStack stack = new MatrixStack();
    //$$ public static MatrixStack getStack() {
    //$$      return stack;
    //$$  }
    //
    //$$  public static void setStack(MatrixStack stack) {
    //$$      UniversalGraphicsHandler.stack = stack;
    //$$  }
    //#endif
    public static void pushMatrix() {
        GlStateManager.pushMatrix();
    }

    public static void popMatrix() {
        GlStateManager.popMatrix();
    }

    public static UniversalGraphicsHandler getFromTessellator() {
        //#if MC<=10809
        return new UniversalGraphicsHandler(getTessellator().getWorldRenderer());
        //#else
        //$$ return new UniversalGraphicsHandler(getTessellator().getBuffer());
        //#endif
    }

    public static void translate(float x, float y, float z) {
        translate((double) x, (double) y, (double) z); //Don't remove double casts or this breaks
    }

    public static void translate(double x, double y, double z) {
        //#if MC<11502
        GlStateManager.translate(x, y, z);
        //#else
        //$$ stack.translate(x, y, z);
        //#endif
    }

    public static void rotate(float angle, float x, float y, float z) {
        //#if MC<11502
        GlStateManager.rotate(angle, x, y, z);
        //#else
        //$$if (x != 0) stack.rotate(Vector3f.XP.rotationDegrees(angle));
        //$$ if (y != 0) stack.rotate(Vector3f.YP.rotationDegrees(angle));
        //$$  if (z != 0) stack.rotate(Vector3f.ZP.rotationDegrees(angle));
        //$$
        //#endif
    }

    public static void scale(float x, float y, float z) {
        scale((double) x, (double) y, (double) z);
    }

    public static void scale(double x, double y, double z) {
        //#if MC<11502
        GlStateManager.scale(x, y, z);
        //#else
        //$$ stack.scale((float) x, (float) y, (float) z);
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
        //$$  GL11.glCullFace(mode);
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

    public static void tryBlendFuncSeparate(int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
        //#if MC<11502
        GlStateManager.tryBlendFuncSeparate(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
        //#else
        //$$  GlStateManager.blendFuncSeparate(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
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
        GlStateManager.enableAlpha();
        //#else
        //$$ GlStateManager.enableAlphaTest();
        //#endif
    }

    public static void bindTexture(int glTextureId) {
        GlStateManager.bindTexture(glTextureId);
    }

    public static int getStringWidth(String in) {
        return UniversalMinecraft.getFontRenderer().getStringWidth(in);
    }

    public static void drawString(String text, float x, float y, int color, boolean shadow) {
        if((color >> 24) <=10) return;
        //#if MC<11500
        UniversalMinecraft.getFontRenderer().drawString(text, x, y, color, shadow);
        //#else
        //$$      IRenderTypeBuffer.Impl irendertypebuffer$impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
        //$$      UniversalMinecraft.getFontRenderer().renderString(text, x, y, color, shadow, stack.getLast().getMatrix(), irendertypebuffer$impl, false, 0, 15728880);
        //$$      irendertypebuffer$impl.finish();
        //#endif
    }

    public static List<String> listFormattedStringToWidth(String str, int wrapWidth) {
        return UniversalMinecraft.getFontRenderer().listFormattedStringToWidth(str, wrapWidth);
    }

    public static float getCharWidth(char character) {
        return UniversalMinecraft.getFontRenderer().getCharWidth(character); //float because its a float in 1.15+
    }

    public static void glClear(int mode) {
        GL11.glClear(mode);
    }

    public static void glClearStencil(int mode) {
        GL11.glClearStencil(mode);
    }

    public static DynamicTexture getTexture(InputStream stream) {
        try {
        //#if MC<11500
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
        //#if MC<11500
        return new DynamicTexture(img);
        //#else
        //$$ try {
        //$$     ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //$$     ImageIO.write(img, "png", baos );
        //$$ return new DynamicTexture(NativeImage.read(new ByteArrayInputStream(baos.toByteArray())));
        //$$ } catch (IOException e) {
        //$$     e.printStackTrace();
        //$$ }
        //$$ throw new IllegalStateException("Failed to create texture");

        //#endif
    }

    public static void glUseProgram(int program) {
        //#if MC<11500
        OpenGlHelper.glUseProgram(program);
        //#else
        //$$ GlStateManager.useProgram(program);
        //#endif
    }

    public static boolean areShadersSupported() {
        //#if MC<11500
        return OpenGlHelper.areShadersSupported();
        //#else
        //$$ return true;
        //#endif
    }

    public static int glCreateProgram() {
        //#if MC<11500
        return OpenGlHelper.glCreateProgram();
        //#else
        //$$ return GlStateManager.createProgram();
        //#endif
    }

    public static int glCreateShader(int type) {
        //#if MC<11500
        return OpenGlHelper.glCreateShader(type);
        //#else
        //$$ return GlStateManager.createShader(type);
        //#endif
    }

    public static void glCompileShader(int shaderIn) {
        //#if MC<11500
        OpenGlHelper.glCompileShader(shaderIn);
        //#else
        //$$  GlStateManager.compileShader(shaderIn);
        //#endif
    }

    public static int glGetShaderi(int shaderIn, int pname) {
        //#if MC<11500
        return OpenGlHelper.glGetShaderi(shaderIn, pname);
        //#else
        //$$ return GlStateManager.getShader(shaderIn,pname);
        //#endif
    }

    public static String glGetShaderInfoLog(int shader, int maxLen) {
        //#if MC<11500
        return OpenGlHelper.glGetShaderInfoLog(shader, maxLen);
        //#else
        //$$ return GlStateManager.getShaderInfoLog( shader,maxLen);
        //#endif
    }

    public static void glAttachShader(int program, int shaderIn) {
        //#if MC<11500
        OpenGlHelper.glAttachShader(program, shaderIn);
        //#else
        //$$  GlStateManager.attachShader(program,shaderIn);
        //#endif
    }

    public static void glLinkProgram(int program) {
        //#if MC<11500
        OpenGlHelper.glLinkProgram(program);
        //#else
        //$$  GlStateManager.linkProgram(program);
        //#endif
    }

    public static int glGetProgrami(int program, int pname) {
        //#if MC<11500
        return OpenGlHelper.glGetProgrami(program, pname);
        //#else
        //$$ return GlStateManager.getProgram(program,pname);
        //#endif
    }

    public static String glGetProgramInfoLog(int program, int maxLen) {
        //#if MC<11500
        return OpenGlHelper.glGetProgramInfoLog(program, maxLen);
        //#else
        //$$ return GlStateManager.getProgramInfoLog(program, maxLen);
        //#endif
    }


    public void begin(int glMode, VertexFormat format) {
        instance.begin(glMode, format);
    }

    public UniversalGraphicsHandler pos(double x, double y, double z) {
        //#if MC<11500
        instance.pos(x, y, z);
        //#else
        //$$     instance.pos(stack.getLast().getMatrix(), (float) x, (float) y, (float) z);
        //#endif
        return this;
    }

    public UniversalGraphicsHandler color(float red, float green, float blue, float alpha) {
        instance.color(red, green, blue, alpha);
        return this;
    }

    public void endVertex() {
        instance.endVertex();
    }

    public UniversalGraphicsHandler tex(double u, double v) {
        //#if MC<11502
        instance.tex(u, v);
        //#else
        //$$ instance.tex((float)u,(float)v);
        //#endif
        return this;
    }
}
