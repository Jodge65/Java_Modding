package fr.Jodge.jodgeLibrary.common.item;

import fr.Jodge.jodgeLibrary.common.JCommonCreate;
import fr.Jodge.jodgeLibrary.common.Main;
import fr.Jodge.jodgeLibrary.common.function.JFunction;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class JItem extends Item implements JCommonCreate
{

	
	private String oreDictionaryName = "";

	public JItem(String name, String modid)
	{
		this(name, modid, "");
	}

	public JItem(String name, String modid, String oreDictionnaryName)
	{
		this(name, modid, modid, name, oreDictionnaryName);
	}
	
	public JItem(String name, String modid, String textureModid, String textureName)
	{
		this(name, modid, textureModid, textureName, "");
	}
	
	public JItem(String name, String modid, String textureModid, String textureName, String oreDictionnaryName)
	{
		super();
		JFunction.commonInit(name, modid, this, textureModid, textureName, oreDictionnaryName);
	}

	public String getOreDic()
	{
		return getOreDictionaryName();
	}

	public String getOreDictionaryName()
	{
		return this.oreDictionaryName;
	}

	public void setOreDictionaryName(String oreDictionaryName)
	{
		this.oreDictionaryName = oreDictionaryName;
	}


}
