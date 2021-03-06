package fr.Jodge.jodgeLibrary.common.toolSet;

import fr.Jodge.jodgeLibrary.common.function.JFunction;
import fr.Jodge.jodgeLibrary.common.JCommonCreate;
import fr.Jodge.jodgeLibrary.common.Main;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class JSpade extends ItemSpade implements JCommonCreate
{
	Item ingot;
	boolean canBeRepair = false;
	private String oreDictionaryName = "";

	public JSpade(String name, Item.ToolMaterial toolData, String modid)
	{
		super(toolData);
		JFunction.commonInit(name, modid, this, oreDictionaryName);
	}

	public JSpade(String name, Item ingot, Item.ToolMaterial toolData, String modid)
	{
		this(name, toolData, modid);
		this.ingot = ingot;
	}

	public JSpade(String name, Block block, Item.ToolMaterial toolData, String modid)
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

	public void setOreDictionaryName(String oreDictionaryName)
	{
		this.oreDictionaryName = oreDictionaryName;
	}

	public JSpade activeAutoCraftingRecipe()
	{
		return activeAutoCraftingRecipe("Spade");
	}

	public JSpade activeAutoCraftingRecipe(String schemaType)
	{
		JFunction.addBasicRecipe(this, this.ingot, schemaType);
		return this;
	}
}
