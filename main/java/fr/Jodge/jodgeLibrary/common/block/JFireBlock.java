package fr.Jodge.jodgeLibrary.common.block;

import java.util.Map;
import java.util.Random;

import com.google.common.collect.Maps;

import fr.Jodge.jodgeLibrary.common.Main;
import fr.Jodge.jodgeLibrary.common.function.JFunction;
import fr.Jodge.jodgeLibrary.common.function.JLog;

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
		super(Material.fire, "Fire", "minecraft", "fire");
		CanDownOnWater(false);
	}

	public int tickRate(World worldIn)
	{
		return 30;
	}

	// -- FALLING --- //
	public boolean canFallIntoBlock(World worldIn, BlockPos pos)
	{
		boolean canFall = false;
		Block block = worldIn.getBlockState(pos).getBlock();
		Material material = block.getMaterial();
		
		if (worldIn.isAirBlock(pos))
		{
			if(material == Material.fire || material == Material.air)
			{
				canFall = true;
			}
			else
			{
				if( !(material == Material.water || material == Material.lava))
				{
					canFall = true;
				}
			}
		}
		else
		{
			if(block instanceof JFireBlock  || block instanceof BlockBush)
			{
				canFall = true;
			}
		}
		
		return canFall;
	}

	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (!worldIn.isRemote)
		{
			this.checkFallable(worldIn, pos);
			if(!canFallIntoBlock(worldIn, pos.down()))
			{
				worldIn.setBlockToAir(pos);
			}
		}
	}


	public void onEndFalling(World worldIn, BlockPos pos)
	{
		worldIn.setBlockState(pos, Blocks.fire.getDefaultState());
		if(worldIn.getBlockState(pos.down()).getBlock() == Blocks.grass)
		{
			worldIn.setBlockState(pos.down(), Blocks.dirt.getDefaultState());
		}
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
