package fr.Jodge.jodgeLibrary.common;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import fr.Jodge.jodgeLibrary.common.extendWeapons.JWeapons;
import fr.Jodge.jodgeLibrary.common.function.JFunction;
import fr.Jodge.jodgeLibrary.common.function.JNbtVar;
import fr.Jodge.jodgeLibrary.common.function.JRenderHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class JScreen extends Gui
{

	/** Width of the texture use */
	protected static int WIDTH = 16;
	/** Height of the texture use */
	protected static int HEIGHT = 16;
	/** Number of slot available on screen */
	protected static int NBSLOT = 9;
	
	
	protected static float BLUE_BAR = 1.0F;
	protected static float RED_BAR = 1.0F;
	protected static float YELLOW_BAR = 1.0F;

	/** reference to the player */
	protected static EntityPlayer player;
	/** reference to the minecraft class */
	protected static Minecraft MC = Minecraft.getMinecraft();;
	/** reference to the texture*/
	ResourceLocation TIMERTEXTURE = new ResourceLocation(Main.MODID + ":textures/gui/timerCombo.png");

	/**
	 * render Timer
	 */
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void onRenderTimer(RenderGameOverlayEvent.Post event)
	{
		if (event.isCancelable() || event.type != ElementType.HOTBAR)
		{ 
			return;
		}

		// VARIABLE
		if (player == null || player != MC.thePlayer)
		{ // if the player can change
			player = MC.thePlayer; 
		}

		int limMax = player.openContainer.getInventory().size(); // maximum size of inventory		
		ScaledResolution sr = new ScaledResolution(MC, MC.displayWidth, MC.displayHeight); // actual resolution
		int x = 0;
		int y = sr.getScaledHeight() - HEIGHT - 3;
		int xString = sr.getScaledWidth() / 2;
		int yString = y - 27;
		int corrString = 2;
		
		// ################################################
		GlStateManager.pushMatrix();
		// GlStateManager.disableLighting();
		// GlStateManager.disableDepth();
		// GlStateManager.disableTexture2D();
		// GlStateManager.disableAlpha();
		// GlStateManager.disableBlend();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.25F); // Cyan, Magenta, Jaune,
		// ################################################
		for (int i = limMax - NBSLOT; i < limMax; ++i) // just for check the 9 Hotbar slot
		{
			Slot slot = (Slot) player.openContainer.inventorySlots.get(i); // actual slot
			ItemStack itemStack = slot.getStack(); // actual itemStack
			
			if (itemStack != null)
			{
				Item item = itemStack.getItem();

				if (item instanceof JWeapons)
				{
					x = getXforSlot(i, limMax);
					if (x != 0)
					{
						int comboTimer = ((JWeapons) item).comboTimer;
						int actualTimer = ((JWeapons) item).timer - JNbtVar.readNbtVarInt(itemStack, JNbtVar.StartCombo);
											
						double width = (((comboTimer - Math.min(comboTimer, actualTimer)) * WIDTH) / comboTimer);
						
						if (actualTimer != 0)
						{
							MC.renderEngine.bindTexture(TIMERTEXTURE);
							JRenderHelper.drawTexturedModalRect(x, y, 1.0F, 0, 0, (int) width, HEIGHT);
						}
					}

				}// end of item instanceof JWeapons

			}// end of itemStack != null

		}// end of for
		
		if(!player.capabilities.isCreativeMode)
		{
			ItemStack itemStack = player.getCurrentEquippedItem();
			if (itemStack != null)
			{
				Item item = itemStack.getItem();
				if (item instanceof JWeapons)
				{
					int rightCombo = JNbtVar.readNbtVarInt(itemStack, JNbtVar.RightCombo);
					int leftCombo = JNbtVar.readNbtVarInt(itemStack, JNbtVar.LeftCombo);
					
					FontRenderer fr = MC.fontRendererObj;
					if(((JWeapons)item).multiCombo)
					{
						fr.drawStringWithShadow("" + rightCombo, xString + corrString, yString, 0xFFFFFF);
						fr.drawStringWithShadow("" + leftCombo, xString - corrString - fr.getStringWidth("" + leftCombo), yString, 0xFFFFFF);
					}
					else
					{
						fr.drawStringWithShadow("" + (leftCombo+rightCombo), xString - fr.getStringWidth("" + (leftCombo+rightCombo)) / 2, yString, 0xFFFFFF);
					}
				}// end of item instanceof JWeapons
			}// end of itemStack != null
		}

		// ################################################
		// GlStateManager.enableBlend();
		// GlStateManager.enableAlpha();
		// GlStateManager.enableTexture2D();
		// GlStateManager.enableDepth();
		// GlStateManager.enableLighting();
		GlStateManager.popMatrix();
		// ################################################
		
	} // RenderGameOverlayEvent(post)

	
	/**
	 * 
	 * @param slot (int) slot to check
	 * @param nbSlot (int) number of slot (hotbarSlot = nbSlot - slot)
	 * @return (int) number of pixel to adapt. 0 if slot not available.
	 */
	int getXforSlot(int slot, int nbSlot)
	{
		ScaledResolution sr = new ScaledResolution(MC, MC.displayWidth, MC.displayHeight);

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


