package fr.Jodge.jodgeLibrary.common.function;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import fr.Jodge.jodgeLibrary.common.extendWeapons.JWeapons;

public class JBlockHelper
{
	public static void destroyBlock(EntityPlayer entity, int maxRange, World worldIn, JWeapons itemUse, int minRange, int rangeMultiplier)
	{
		int posX = entity.getPosition().getX();
		int posY = entity.getPosition().getY();
		int posZ = entity.getPosition().getZ();
		if (maxRange < minRange)
		{
			maxRange = minRange;
		}
		maxRange *= rangeMultiplier;
		for (int x = 0; x < maxRange; x++)
		{
			for (int z = 0; z < maxRange - x; z++)
			{
				BlockPos temps = new BlockPos(posX + x, posY, posZ + z);

				Block myBlock = worldIn.getBlockState(temps).getBlock();
				if (itemUse.destructibleBlock(myBlock, temps, worldIn, entity))
				{
					worldIn.destroyBlock(temps, true);
				}
				if (z != 0)
				{
					temps = new BlockPos(posX + x, posY, posZ - z);

					myBlock = worldIn.getBlockState(temps).getBlock();
					if (itemUse.destructibleBlock(myBlock, temps, worldIn, entity))
					{
						worldIn.destroyBlock(temps, true);
					}
				}
				if (x != 0)
				{
					temps = new BlockPos(posX - x, posY, posZ + z);

					myBlock = worldIn.getBlockState(temps).getBlock();
					if (itemUse.destructibleBlock(myBlock, temps, worldIn, entity))
					{
						worldIn.destroyBlock(temps, true);
					}
					if (z != 0)
					{
						temps = new BlockPos(posX - x, posY, posZ - z);

						myBlock = worldIn.getBlockState(temps).getBlock();
						if (itemUse.destructibleBlock(myBlock, temps, worldIn, entity))
						{
							worldIn.destroyBlock(temps, true);
						}
					}
				}
			}
		}
	}

	public static void destroyBlock(EntityPlayer entity, int maxRange, World worldIn, JWeapons itemUse)
	{
		destroyBlock(entity, maxRange, worldIn, itemUse, 1, 1);
	}
	
	public float getHardness(Block block)
	{
		return block.getBlockHardness(null, null);
	}

	public float getHardness(int iDBlock)
	{
		return getHardness(Block.getBlockById(iDBlock));
	}

	public float getHardness(String block)
	{
		return getHardness(getBlockByName(block));
	}

	public Block getBlockByName(String block)
	{
		return Block.getBlockFromName(block);
	}

}
