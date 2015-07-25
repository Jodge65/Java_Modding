package fr.Jodge.jodgeLibrary.common.toolSet;

import fr.Jodge.jodgeLibrary.common.CommonProxy;
import fr.Jodge.jodgeLibrary.common.JFunction;
import fr.Jodge.jodgeLibrary.common.Main;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class JHoe extends ItemHoe
{
	Item ingot;
	boolean canBeRepair = false;
	private String oreDictionaryName = "";

	public JHoe(String name, Item.ToolMaterial toolData, String modid)
	{
		super(toolData);

		String m = JFunction.convertNameToUnLocalizedName(name);
		setUnlocalizedName(m);
		GameRegistry.registerItem(this, m);
		Main.proxy.registerItemTexture(this, m, modid);
		if (this.oreDictionaryName.isEmpty())
		{
			this.oreDictionaryName = JFunction.convertNameToOreDictionaryName(name);
		}
		setOreDictionaryName(this.oreDictionaryName);
		OreDictionary.registerOre(this.oreDictionaryName, this);
	}

	public JHoe(String name, Item ingot, Item.ToolMaterial toolData, String modid)
	{
		this(name, toolData, modid);
		this.ingot = ingot;
	}

	public JHoe(String name, Block block, Item.ToolMaterial toolData, String modid)
	{
		this(name, getItemFromBlock(block), toolData, modid);
	}

	public boolean getIsRepairable(ItemStack input, ItemStack repair)
	{
		if (this.canBeRepair)
		{
			return repair.getItem() == this.ingot;
		}
		return false;
	}

	public String getOreDic()
	{
		return getOreDictionaryName();
	}

	public String getOreDictionaryName()
	{
		return this.oreDictionaryName;
	}

	void setOreDictionaryName(String oreDictionaryName)
	{
		this.oreDictionaryName = oreDictionaryName;
	}

	public JHoe activeAutoCraftingRecipe()
	{
		return activeAutoCraftingRecipe("Hoe");
	}

	public JHoe activeAutoCraftingRecipe(String schemaType)
	{
		JFunction.addBasicRecipe(this, this.ingot, schemaType);
		return this;
	}
}
