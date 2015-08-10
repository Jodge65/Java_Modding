package fr.Jodge.jodgeLibrary.common.block;

import fr.Jodge.jodgeLibrary.common.Main;
import fr.Jodge.jodgeLibrary.common.function.JFunction;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class JFallingBlock extends BlockFalling
{
	private String oreDictionnaryName;

	public JFallingBlock(Material material, String name, String modid)
	{
		this(material, name, modid, "");
	}

	public JFallingBlock(Material material, String name, String modid, String oreDictionnaryName)
	{
		super(material);
		String m = JFunction.convertNameToUnLocalizedName(name);
		setUnlocalizedName(m);

		GameRegistry.registerBlock(this, m);
		Main.proxy.registerBlockTexture(this, m, modid);
		if (oreDictionnaryName.isEmpty())
		{
			oreDictionnaryName = JFunction.convertNameToOreDictionaryName(name);
		}
		setOreDictionnaryName(oreDictionnaryName);
		OreDictionary.registerOre(oreDictionnaryName, this);
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
