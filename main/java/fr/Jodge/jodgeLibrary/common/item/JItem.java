package fr.Jodge.jodgeLibrary.common.item;

import fr.Jodge.jodgeLibrary.common.Main;
import fr.Jodge.jodgeLibrary.common.function.JFunction;

import net.minecraft.item.Item;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class JItem extends Item
{
	private String oreDictionaryName = "";

	protected Item createItem(String modid, String name)
	{
		return createItem(modid, name, "");
	}

	protected Item createItem(String modid, String name, String oreDictionnaryName)
	{
		String m = JFunction.convertNameToUnLocalizedName(name);

		setUnlocalizedName(m);

		GameRegistry.registerItem(this, m);
		Main.proxy.registerItemTexture(this, m, modid);
		if (oreDictionnaryName.isEmpty())
		{
			oreDictionnaryName = JFunction.convertNameToOreDictionaryName(name);
		}
		setOreDictionnaryName(oreDictionnaryName);
		OreDictionary.registerOre(oreDictionnaryName, this);
		return this;
	}

	public Item createIngot(String name, String modid)
	{
		return createItem(name, modid);
	}

	public String getOreDic()
	{
		return getOreDictionaryName();
	}

	public String getOreDictionaryName()
	{
		return this.oreDictionaryName;
	}

	void setOreDictionnaryName(String oreDictionnaryName)
	{
		this.oreDictionaryName = oreDictionnaryName;
	}
}
