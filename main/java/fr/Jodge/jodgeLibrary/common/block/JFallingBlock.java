package fr.Jodge.jodgeLibrary.common.block;

import fr.Jodge.jodgeLibrary.common.JCommonCreate;
import fr.Jodge.jodgeLibrary.common.Main;
import fr.Jodge.jodgeLibrary.common.entity.JEntityFallingBlock;
import fr.Jodge.jodgeLibrary.common.function.JFunction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
/**
 * @author Jodge
 * 
 */
public class JFallingBlock extends BlockFalling implements JCommonCreate
{
	public String oreDictionaryName;
	public boolean canDownInLiquid = true;
	
	public JFallingBlock(Material material, String name, String modid)
	{
		this(material, name, modid, "");
	}

	public JFallingBlock(Material material, String name, String modid, String oreDictionnaryName)
	{
		this(material, name, modid, modid, name, oreDictionnaryName);
	}
	
	public JFallingBlock(Material material, String name, String modid, String textureModid, String textureName)
	{
		this(material, name, modid, textureModid, textureName, "");
	}
	
	public JFallingBlock(Material material, String name, String modid, String textureModid, String textureName, String oreDictionnaryName)
	{
		super(material);
		JFunction.commonInit(name, modid, this, textureModid, textureName, oreDictionnaryName);
	}
	
	public JFallingBlock CanDownOnWater(boolean canDownOnWater)
	{
		canDownInLiquid = canDownOnWater;
		return this;
	}
	
	public boolean canFallIntoBlock(World worldIn, BlockPos pos)
    {
        if (worldIn.isAirBlock(pos)) return true;
        Block block = worldIn.getBlockState(pos).getBlock();
        Material material = block.getMaterial();
        return block == Blocks.fire || material == Material.air || material == Material.water || material == Material.lava;
    }
	
	public static boolean canFall(World worldIn, BlockPos pos, JFallingBlock originalBlock)
	{
		return originalBlock.canFallIntoBlock(worldIn, pos);
	}
	
	public void checkFallable(World worldIn, BlockPos pos)
	{
		if (canFallIntoBlock(worldIn, pos.down()) && pos.getY() >= 0)
		{
			byte b0 = 32;

			if (!fallInstantly && worldIn.isAreaLoaded(pos.add(-b0, -b0, -b0), pos.add(b0, b0, b0)))
			{
				if (!worldIn.isRemote)
				{
					EntityFallingBlock entityfallingblock = new JEntityFallingBlock(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, worldIn.getBlockState(pos), 1.0F, false, true).CanDownOnWater(canDownInLiquid);
					onStartFalling(entityfallingblock);
					worldIn.spawnEntityInWorld(entityfallingblock);
				}
			}
			else
			{
				worldIn.setBlockToAir(pos);
				BlockPos blockpos1 = pos.down();
				Block blockWhereFall;
				
				while (true)
				{
					blockpos1 = blockpos1.down();
					blockWhereFall = worldIn.getBlockState(blockpos1).getBlock();
					
					if ( !(canFallIntoBlock(worldIn, blockpos1) && blockpos1.getY() > 0))
					{
						break;
					}

				}

				if (blockpos1.getY() > 0)
				{
					worldIn.setBlockState(blockpos1.up(), getDefaultState());
				}
			}
		}
	}

	
	public String getOreDic()
	{
		return getOreDictionaryName();
	}

	public String getOreDictionaryName()
	{
		return oreDictionaryName;
	}

	public void setOreDictionaryName(String oreDictionaryName)
	{
		this.oreDictionaryName = oreDictionaryName;
	}
}
