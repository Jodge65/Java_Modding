package fr.Jodge.jodgeLibrary.common;

import net.minecraft.block.Block;
import net.minecraft.block.Block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class JBlock extends Block
{
	private String oreDictionnaryName;
	public static final Block.SoundType soundTypeCloud = new Block.SoundType("stone", 0.1F, 0.3F);

	public JBlock(Material material, String name, String modid)
	{
		this(material, name, modid, "");
	}

	public JBlock(Material material, String name, String modid, String oreDictionnaryName)
	{
		super(material);
		String m = JFunction.convertNameToUnLocalizedName(name);
		setUnlocalizedName(m);
		if (material == JMaterial.ore)
		{
			isOre();
		}
		else if (material == JMaterial.cloud)
		{
			isCloud();
		}
		GameRegistry.registerBlock(this, m);
		Main.proxy.registerBlockTexture(this, m, modid);
		if (oreDictionnaryName.isEmpty())
		{
			oreDictionnaryName = JFunction.convertNameToOreDictionaryName(name);
		}
		setOreDictionnaryName(oreDictionnaryName);
		OreDictionary.registerOre(oreDictionnaryName, this);
	}

	public Block isOre()
	{
		setHardness(0.3F);
		setResistance(0.5F);
		setStepSound(soundTypePiston);
		return this;
	}

	public Block isCloud()
	{
		setHardness(0.0F);
		setResistance(0.0F);
		setStepSound(soundTypeCloud);
		return this;
	}

	public String getOreDic()
	{
		return getOreDictionnaryName();
	}

	public String getOreDictionnaryName()
	{
		return this.oreDictionnaryName;
	}

	void setOreDictionnaryName(String oreDictionnaryName)
	{
		this.oreDictionnaryName = oreDictionnaryName;
	}
}
