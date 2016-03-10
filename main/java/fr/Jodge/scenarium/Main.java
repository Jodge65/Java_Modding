package fr.Jodge.scenarium;

import fr.Jodge.jodgeLibrary.common.block.JBlock;
import fr.Jodge.jodgeLibrary.common.block.JMaterial;
import fr.Jodge.jodgeLibrary.common.item.JItem;
import fr.Jodge.jodgeLibrary.common.toolSet.*;

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

@Mod(modid = "scenarium", name = "Scénarium", version = "0.0.0", acceptedMinecraftVersions = "[1.8]")
public class Main
{
	public static final String MODID = "scenarium";
	public static final String MODNAME = "Scénarium";
	public static final String MODVER = "0.0.0";
	@Mod.Instance("scenarium")
	public static Main instance;
	@SidedProxy(clientSide = "fr.Jodge.scenarium.ClientProxy", serverSide = "fr.Jodge.scenarium.CommonProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		Item.ToolMaterial scenariumTool = EnumHelper.addToolMaterial("SCENARIUM", 666, -1, 66.6F, 666.0F, 666);

		proxy.registerRender();
		Item script = new JItem("Script", MODID).setCreativeTab(CreativeTabs.tabBlock);
		Item scenariumIngot = new JItem("Scenarium Ingot", "universalores").activeAutoCraftingRecipe(script).setCreativeTab(CreativeTabs.tabBlock);
		Item scenariumSword = new JSword("Scenarium sword", scenariumIngot, scenariumTool, MODID).activeAutoCraftingRecipe();
		Item scenariumPickaxe = new JPickaxe("Scenarium pickaxe", scenariumIngot, scenariumTool, MODID).activeAutoCraftingRecipe();
		Item scenariumAxe = new JAxe("Scenarium axe", scenariumIngot, scenariumTool, MODID).activeAutoCraftingRecipe();
		Item scenariumShovel = new JSpade("Scenarium shovel", scenariumIngot, scenariumTool, MODID).activeAutoCraftingRecipe();
		Item scenariumHoe = new JHoe("Scenarium hoe", scenariumIngot, scenariumTool, MODID).activeAutoCraftingRecipe();
		Block scenariumBlock = new JBlock(JMaterial.ore, "Scenarium block", MODID).activeAutoCraftingRecipe(scenariumIngot).setCreativeTab(CreativeTabs.tabBlock);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
	}
}
