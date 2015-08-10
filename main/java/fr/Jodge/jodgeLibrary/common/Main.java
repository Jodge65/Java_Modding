package fr.Jodge.jodgeLibrary.common;

import fr.Jodge.jodgeLibrary.common.Area.JSchema;
import fr.Jodge.jodgeLibrary.common.block.JBlock;
import fr.Jodge.jodgeLibrary.common.block.JFireBlock;
import fr.Jodge.jodgeLibrary.common.block.JMaterial;
import fr.Jodge.jodgeLibrary.proxy.CommonProxy;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = "jodgelibrary", name = "Jodge Library", version = "A0.1.0", acceptedMinecraftVersions = "[1.8]")
public class Main
{
	public static final String MODID = "jodgelibrary";
	public static final String MODNAME = "Jodge Library";
	public static final String MODVER = "A0.1.0";
	
	public static final boolean DEBUG = true;
	
	@Mod.Instance("jodgelibrary")
	public static Main instance;
	
	@SidedProxy(clientSide = "fr.Jodge.jodgeLibrary.proxy.ClientProxy", serverSide = "fr.Jodge.jodgeLibrary.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		proxy.registerRender();
		if (event.getSide().isClient()) // Client
		{
			MinecraftForge.EVENT_BUS.register(new JScreen());
		}
		else // Serveur
		{
			
		}
		

	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{

	}
}
