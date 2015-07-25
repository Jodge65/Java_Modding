package fr.Jodge.jodgeLibrary.common;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import fr.Jodge.jodgeLibrary.common.JFunction.nbtVar;
import fr.Jodge.jodgeLibrary.common.toolSet.JWeapons;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class JScreen extends Gui
{
	private static EntityPlayer player;
	private static Item item;

	private static int WIDTH = 16;
	private static int HEIGHT = 16;
	private static int NBSLOT = 9;

	private static Minecraft MC = Minecraft.getMinecraft();;

	ResourceLocation TIMERTEXTURE = new ResourceLocation(Main.MODID + ":textures/gui/timerCombo.png");

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderHotbar(RenderGameOverlayEvent.Post event)
	{
		if (event.isCancelable() || event.type != ElementType.HOTBAR)
		{
			return;
		}

		// VARIABLE
		if (player == null || player != MC.thePlayer)
		{
			player = MC.thePlayer; // To get Player
		}

		int limMax = player.openContainer.getInventory().size();

		for (int i = limMax - NBSLOT; i < limMax; ++i)
		{
			Slot slot = (Slot) player.openContainer.inventorySlots.get(i);
			ItemStack itemStack = slot.getStack();

			if (itemStack != null)
			{
				item = itemStack.getItem();

				ScaledResolution sr = new ScaledResolution(MC, MC.displayWidth, MC.displayHeight);
				;

				int y = sr.getScaledHeight() - HEIGHT - 3;
				int x = 0;

				// FUNCTION

				if (item instanceof JWeapons)
				{
					// ################################################
					GlStateManager.pushMatrix();
					// GlStateManager.disableLighting();
					// GlStateManager.disableDepth();
					// GlStateManager.disableTexture2D();
					// GlStateManager.disableAlpha();
					// GlStateManager.disableBlend();
					GL11.glColor4f(0.8F, 0.8F, 0.8F, 1.0F); // Cyan, Magenta, Jaune,
					// ################################################

					x = getXforSlot(i, limMax);
					if (x != 0)
					{
						int comboTimer = ((JWeapons) item).comboTimer;
						int actualTimer = ((JWeapons) item).timer - nbtVar.readNbtVarInt(itemStack, nbtVar.StartCombo);
						double width = (((comboTimer - Math.min(comboTimer, actualTimer)) * WIDTH) / comboTimer);

						if (actualTimer != 0)
						{
							Minecraft.getMinecraft().renderEngine.bindTexture(TIMERTEXTURE);
							JFunction.drawTexturedModalRect(x, y, 1.0F, 0, 0, (int) width, HEIGHT);
						}
					}

					// ################################################
					// GlStateManager.enableBlend();
					// GlStateManager.enableAlpha();
					// GlStateManager.enableTexture2D();
					// GlStateManager.enableDepth();
					// GlStateManager.enableLighting();
					GlStateManager.popMatrix();
					// ################################################

				} // end of item instanceof JWeapons

			}// end of itemStack != null

		}// end of for

	} // RenderGameOverlayEvent(post)

	int getXforSlot(int slot, int nbSlot)
	{
		ScaledResolution sr = new ScaledResolution(MC, MC.displayWidth, MC.displayHeight);
		;
		int x = (sr.getScaledWidth() / 2) - WIDTH / 2;
		int pos = nbSlot - slot;

		if (pos > 0 && pos <= NBSLOT)
		{
			return x - (WIDTH + 4) * (pos - 5);
		}
		else
		{
			return 0;
		}
	}

}


