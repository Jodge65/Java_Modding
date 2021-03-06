package fr.Jodge.jodgeLibrary.common.block;

import fr.Jodge.jodgeLibrary.common.JCommonCreate;
import fr.Jodge.jodgeLibrary.common.Main;
import fr.Jodge.jodgeLibrary.common.function.JFunction;
import fr.Jodge.jodgeLibrary.common.item.JItem;
import fr.Jodge.jodgeLibrary.common.sound.JSound;

import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
/**
 * @author Jodge
 * 
 */
public class JBlock extends Block implements JCommonCreate
{

	public String oreDictionaryName;
	
	public JBlock(Material material, String name, String modid)
	{
		this(material, name, modid, "");
	}

	public JBlock(Material material, String name, String modid, String oreDictionnaryName)
	{
		this(material, name, modid, modid, name, oreDictionnaryName);
	}
	
	public JBlock(Material material, String name, String modid, String textureModid, String textureName)
	{
		this(material, name, modid, textureModid, textureName, "");
	}
	
	public JBlock(Material material, String name, String modid, String textureModid, String textureName, String oreDictionnaryName)
	{
		super(material);
		
		JFunction.commonInit(name, modid, this, textureModid, textureName, oreDictionnaryName);

		if (material == JMaterial.ore)
		{
			isOre();
		}
		else if (material == JMaterial.cloud)
		{
			isCloud();
		}
		
	}

	public JBlock isOre()
	{
		setHardness(0.3F);
		setResistance(0.5F);
		setStepSound(soundTypePiston);
		return this;
	}

	public JBlock isCloud()
	{
		setHardness(0.0F);
		setResistance(0.0F);
		setStepSound(JSound.soundTypeCloud);
		return this;
	}

	public String getOreDic()
	{
		return getOreDictionaryName();
	}

	public void setOreDictionaryName(String oreDictionnaryName)
	{
		this.oreDictionaryName = oreDictionnaryName;
	}

	public String getOreDictionaryName()
	{
		return this.oreDictionaryName;
	}

	public JBlock activeAutoCraftingRecipe(Item ingot)
	{
		return activeAutoCraftingRecipe("Block", ingot);
	}

	public JBlock activeAutoCraftingRecipe(String schemaType, Item ingot)
	{
		JFunction.addBasicRecipe(this,ingot, schemaType);
		return this;
	}
}
