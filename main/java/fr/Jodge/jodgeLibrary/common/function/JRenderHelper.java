package fr.Jodge.jodgeLibrary.common.function;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;

public class JRenderHelper
{
	public static void drawTexturedModalRect(int x, int y, float z, int textureX, int textureY, int width, int height)
	{
		drawTexturedModalRect(x, y, z, textureX, textureY, width, height, 0.00390625F, 0.00390625F);
	}

	public static void drawTexturedModalRect(int x, int y, float z, int textureX, int textureY, int width, int height, float f, float f1)
	{
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();

		worldrenderer.startDrawingQuads();
		worldrenderer.addVertexWithUV(x + 0, y + height, z, (textureX + 0) * f, (textureY + height) * f1);
		worldrenderer.addVertexWithUV(x + width, y + height, z, (textureX + width) * f, (textureY + height) * f1);
		worldrenderer.addVertexWithUV(x + width, y + 0, z, (textureX + width) * f, (textureY + 0) * f1);
		worldrenderer.addVertexWithUV(x + 0, y + 0, z, (textureX + 0) * f, (textureY + 0) * f1);
		tessellator.draw();
	}

}
