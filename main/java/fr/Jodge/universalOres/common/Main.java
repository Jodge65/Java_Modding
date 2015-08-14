package fr.Jodge.universalOres.common;


import fr.Jodge.jodgeLibrary.common.block.JBlock;
import fr.Jodge.jodgeLibrary.common.block.JMaterial;
import fr.Jodge.jodgeLibrary.common.extendWeapons.JScythe;
import fr.Jodge.jodgeLibrary.common.item.JItem;
import fr.Jodge.jodgeLibrary.common.toolSet.JAxe;
import fr.Jodge.jodgeLibrary.common.toolSet.JHoe;
import fr.Jodge.jodgeLibrary.common.toolSet.JPickaxe;
import fr.Jodge.jodgeLibrary.common.toolSet.JSpade;
import fr.Jodge.jodgeLibrary.common.toolSet.JSword;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "universalores", name = "Universal Ores", version = "0.0.0", acceptedMinecraftVersions = "[1.8]")
public class Main
{
	public static final String MODID = "universalores";
	public static final String MODNAME = "Universal Ores";
	public static final String MODVER = "0.0.0";
	@Mod.Instance("universalores")
	public static Main instance;
	@SidedProxy(clientSide = "fr.Jodge.universalOres.client.ClientProxy", serverSide = "fr.Jodge.universalOres.common.CommonProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		Item.ToolMaterial copperTool = EnumHelper.addToolMaterial("COPPER", 2, 100, 6.0F, 2.0F, 14);

		proxy.registerRender();
		Block copperOre = new JBlock(JMaterial.cloud, "Copper ore", "universalores").setCreativeTab(CreativeTabs.tabBlock);
		Item copperIngot = new JItem("Copper Ingot", "universalores").setCreativeTab(CreativeTabs.tabBlock);
		Item copperSword = new JSword("Copper sword", copperIngot, copperTool, "universalores").activeAutoCraftingRecipe();
		Item copperPickaxe = new JPickaxe("Copper pickaxe", copperIngot, copperTool, "universalores").activeAutoCraftingRecipe();
		Item copperAxe = new JAxe("Copper axe", copperIngot, copperTool, "universalores").activeAutoCraftingRecipe();
		Item copperShovel = new JSpade("Copper shovel", copperIngot, copperTool, "universalores").activeAutoCraftingRecipe();
		Item copperHoe = new JHoe("Copper hoe", copperIngot, copperTool, "universalores").activeAutoCraftingRecipe();
		//Item copperScythe = new JScythe("Copper scythe", copperIngot, copperTool, "universalores").activeAutoCraftingRecipe();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
	}
}
