package fr.Jodge.jodgeLibrary.common.toolSet;

import fr.Jodge.jodgeLibrary.common.function.JFunction;
import fr.Jodge.jodgeLibrary.common.Main;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class JAxe extends ItemAxe
{
	Item ingot;
	boolean canBeRepair = false;
	private String oreDictionaryName = "";

	public JAxe(String name, Item.ToolMaterial toolData, String modid)
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
		setOreDictionnaryName(this.oreDictionaryName);
		OreDictionary.registerOre(this.oreDictionaryName, this);
	}

	public JAxe(String name, Item ingot, Item.ToolMaterial toolData, String modid)
	{
		this(name, toolData, modid);
		this.ingot = ingot;
	}

	public JAxe(String name, Block block, Item.ToolMaterial toolData, String modid)
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
		return getOreDictionnaryName();
	}

	public String getOreDictionnaryName()
	{
		return this.oreDictionaryName;
	}

	void setOreDictionnaryName(String oreDictionnaryName)
	{
		this.oreDictionaryName = oreDictionnaryName;
	}

	public JAxe activeAutoCraftingRecipe()
	{
		return activeAutoCraftingRecipe("Axe");
	}

	public JAxe activeAutoCraftingRecipe(String schemaType)
	{
		JFunction.addBasicRecipe(this, this.ingot, schemaType);
		return this;
	}
}
