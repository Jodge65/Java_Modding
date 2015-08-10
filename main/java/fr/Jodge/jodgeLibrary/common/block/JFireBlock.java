package fr.Jodge.jodgeLibrary.common.block;

import java.util.Map;
import java.util.Random;

import com.google.common.collect.Maps;

import fr.Jodge.jodgeLibrary.common.Main;
import fr.Jodge.jodgeLibrary.common.function.JFunction;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class JFireBlock extends JFallingBlock
{

	private String oreDictionnaryName;

	private final Map encouragements = Maps.newIdentityHashMap();
	private final Map flammabilities = Maps.newIdentityHashMap();

	public JFireBlock()
	{
		super(Material.fire, "Fall Fire", Main.MODID, "firefall");

	}

	public int tickRate(World worldIn)
	{
		return 30;
	}

	// -- FALLING --- //
	public static boolean canFallInto(World worldIn, BlockPos pos)
	{
		boolean canFall = false;
		if (worldIn.isAirBlock(pos))
		{
			Block block = worldIn.getBlockState(pos).getBlock();
			Material material = block.getMaterial();
			
			if(material == Material.fire || material == Material.air)
			{
				canFall = true;
			}
			else
			{
				if( (block instanceof BlockBush || block instanceof JFireBlock) && !(block instanceof BlockStaticLiquid) )
				{
					canFall = true;
				}
			}
		}
		
		return canFall;
	}

	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (!worldIn.isRemote)
		{
			this.checkFallable(worldIn, pos);
		}
	}

	private void checkFallable(World worldIn, BlockPos pos)
	{
		if (canFallInto(worldIn, pos.down()) && pos.getY() >= 0)
		{
			byte b0 = 32;

			if (!fallInstantly && worldIn.isAreaLoaded(pos.add(-b0, -b0, -b0), pos.add(b0, b0, b0)))
			{
				if (!worldIn.isRemote)
				{
					EntityFallingBlock entityfallingblock = new EntityFallingBlock(worldIn, (double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, worldIn.getBlockState(pos));
					this.onStartFalling(entityfallingblock);
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
					
					if ( !(canFallInto(worldIn, blockpos1) && blockpos1.getY() > 0))
					{
						break;
					}
					
					try
					{
						wait(15);
					}
					catch (InterruptedException e)
					{
						;
					}
				}

				if (blockpos1.getY() > 0)
				{
					worldIn.setBlockState(blockpos1.up(), this.getDefaultState());
				}
			}
		}
	}

	public void onEndFalling(World worldIn, BlockPos pos)
	{
		worldIn.setBlockState(pos, Blocks.fire.getDefaultState());
	}

	// -- FIRE --- //
	public boolean isCollidable()
	{
		return false;
	}

	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state)
	{
		return null;
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	public boolean isFullCube()
	{
		return false;
	}

	public int quantityDropped(Random random)
	{
		return 0;
	}

	public MapColor getMapColor(IBlockState state)
	{
		return MapColor.tntColor;
	}
}
