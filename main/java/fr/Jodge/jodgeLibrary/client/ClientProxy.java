package fr.Jodge.jodgeLibrary.client;

import fr.Jodge.jodgeLibrary.common.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class ClientProxy extends CommonProxy
{
	public void registerRender()
	{
	}

	public void registerItemTexture(Item item, int metadata, String name, String modid)
	{
		ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		mesher.register(item, metadata, new ModelResourceLocation(modid + ":" + name, "inventory"));
	}

	public void registerItemTexture(Item item, String name, String modid)
	{
		registerItemTexture(item, 0, name, modid);
	}

	public void registerBlockTexture(Block block, int metadata, String name, String modid)
	{
		registerItemTexture(Item.getItemFromBlock(block), metadata, name, modid);
	}

	public void registerBlockTexture(Block block, String name, String modid)
	{
		registerBlockTexture(block, 0, name, modid);
	}

	public void multiTexture(Item item, String[] chaine)
	{
		ModelBakery.addVariantName(item, chaine);
	}
}
