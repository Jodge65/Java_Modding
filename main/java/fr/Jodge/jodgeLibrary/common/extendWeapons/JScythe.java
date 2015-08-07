package fr.Jodge.jodgeLibrary.common.extendWeapons;

import fr.Jodge.jodgeLibrary.common.Main;
import fr.Jodge.jodgeLibrary.common.Area.JAreaHelper;
import fr.Jodge.jodgeLibrary.common.Area.JCircle;
import fr.Jodge.jodgeLibrary.common.Area.JDome;
import fr.Jodge.jodgeLibrary.common.function.JBlockHelper;
import fr.Jodge.jodgeLibrary.common.function.JDamageHelper;
import fr.Jodge.jodgeLibrary.common.function.JFunction;
import fr.Jodge.jodgeLibrary.common.function.JLog;
import fr.Jodge.jodgeLibrary.common.function.JNbtVar;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockStone;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class JScythe extends JWeapons
{
	// private static final long start = System.currentTimeMillis() / 1000;
	// private static int seconds = 0;

	public int fireColumn;
	private int rayonArea;

	public JScythe(String name, Item ingot, Item.ToolMaterial toolData, String modid)
	{
		super(name, ingot, toolData, modid);
		setBonusDamage(5.0F);
		comboTimer = 5 * 20;
		fireColumn = 1;
		rayonArea = 4;

		Main.proxy.registerItemTexture(this, 1, getUnlocalizedNameAlone() + "Using", modid);
		Main.proxy.registerItemTexture(this, 1, getUnlocalizedNameAlone() + "UsingSneaking", modid);

		Main.proxy.multiTexture(this, new String[] { getModid() + ":" + getUnlocalizedNameAlone(), getModid() + ":" + getUnlocalizedNameAlone() + "Using", getModid() + ":" + getUnlocalizedNameAlone() + "UsingSneaking" });
	}

	public JScythe(String name, Item.ToolMaterial toolData, String modid)
	{
		this(name, new Item(), toolData, modid);
	}

	public JScythe(String name, Block block, Item.ToolMaterial toolData, String modid)
	{
		this(name, getItemFromBlock(block), toolData, modid);
	}

	public JScythe activeAutoCraftingRecipe()
	{
		return activeAutoCraftingRecipe("Scythe");
	}

	public JScythe activeAutoCraftingRecipe(String schemaType)
	{
		JFunction.addBasicRecipe(this, getIngot(), schemaType);
		return this;
	}

	public JScythe setFireColumn(int height)
	{
		fireColumn = height;
		return this;
	}

	public JScythe setRayonArea(int value)
	{
		rayonArea = value;
		return this;
	}

	public JScythe setComboTimer(int value)
	{
		comboTimer = value;
		return this;
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining)
	{
		if (useRemaining == 0)
		{
			return null;
		}
		if (player.isSneaking())
		{
			return new ModelResourceLocation(getModid() + ":" + getUnlocalizedNameAlone() + "UsingSneaking", "inventory");
		}
		return new ModelResourceLocation(getModid() + ":" + getUnlocalizedNameAlone() + "Using", "inventory");
	}

	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.BOW;
	}

	public boolean destructibleBlock(Block block, BlockPos pos, World worldIn, EntityPlayer player)
	{
		boolean isDestructible = false;
		if (!block.isAir(worldIn, pos))
		{
			if ((Block.isEqualTo(block, Blocks.fire)) || (Block.isEqualTo(block, Blocks.web)) || (Block.isEqualTo(block, Blocks.reeds)) || (Block.isEqualTo(block, Blocks.cactus)))
			{
				isDestructible = true;
				player.getCurrentEquippedItem().damageItem(2, player);
			}
			else if (((block instanceof BlockBush)) && (!Block.isEqualTo(block, Blocks.pumpkin_stem)) && (!Block.isEqualTo(block, Blocks.melon_stem)))
			{
				if ((block instanceof BlockCrops))
				{
					if (!((BlockCrops) block).canGrow(worldIn, pos, worldIn.getBlockState(pos), true))
					{
						isDestructible = true;
					}
				}
				else
				{
					isDestructible = true;
				}
			}
		}
		return isDestructible;
	}

	public ItemStack onItemUseFinish(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn)
	{
		onPlayerStoppedUsing(itemStackIn, worldIn, playerIn, 0);
		return itemStackIn;
	}

	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		stack.damageItem(1, attacker);
		return true;
	}

	// --- LEFT CLIC ---
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		int combo = JNbtVar.readNbtVarInt(stack, JNbtVar.LeftCombo);
		if (!player.worldObj.isRemote)
		{
			switch (combo)
			{
				case 0:
					leftHit1(stack, player, entity);
					JNbtVar.incNbtVarInt(stack, JNbtVar.LeftCombo);
					break;
				case 1:
					leftHit2(stack, player, entity);
					JNbtVar.incNbtVarInt(stack, JNbtVar.LeftCombo);
					break;
				case 2:
					leftHit3(stack, player, entity);
					JNbtVar.writeNbtVar(stack, JNbtVar.LeftCombo, 0);
					JNbtVar.writeNbtVar(stack, JNbtVar.StartCombo, timer);
					break;

				default:
					JNbtVar.writeNbtVar(stack, JNbtVar.LeftCombo, 0);
					JNbtVar.writeNbtVar(stack, JNbtVar.StartCombo, timer);
					break;
			}
		}
		return true;
	}

	private void leftHit1(ItemStack stack, EntityPlayer player, Entity entity)
	{
		JDamageHelper.dealDamage(player, entity);
	}

	private void leftHit2(ItemStack stack, EntityPlayer player, Entity entity)
	{
		JDamageHelper.dealMultipleDamage(player, JAreaHelper.getEntitySquareArea(player, 3), 0.75F);
	}

	private void leftHit3(ItemStack stack, EntityPlayer player, Entity entity)
	{
		if (player.isSneaking())
		{
			JDamageHelper.dealDamage(player, entity, 1.5F, false);
		}
		else
		{
			JDamageHelper.addKnockBack(3);
			JDamageHelper.dealMultipleDamage(player, JAreaHelper.getEntitySquareArea(player, 3), 0.5F);
		}
	}

	// --- RIGHT CLIC ---
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int timeLeft)
	{
		if (!world.isRemote)
		{
			if ((stack.getMaxItemUseDuration() - timeLeft) > clicSpeed)
			{
				rightHitCharge(stack, world, player, timeLeft);
			}
			else
			{
				int combo = JNbtVar.readNbtVarInt(stack, JNbtVar.LeftCombo);
				switch (combo)
				{
					case 0:
						rightHit1(stack, world, player, timeLeft);
						break;
					case 1:
						rightHit2(stack, world, player, timeLeft);
						break;
					case 2:
						rightHit3(stack, world, player, timeLeft);
						break;

					default:
						break;
				}
			}
			JNbtVar.writeNbtVar(stack, JNbtVar.RightCombo, 0);
			JNbtVar.writeNbtVar(stack, JNbtVar.LeftCombo, 0);
			JNbtVar.writeNbtVar(stack, JNbtVar.StartCombo, timer);
		}
	}

	private void rightHitCharge(ItemStack stack, World worldIn, EntityPlayer player, int timeLeft)
	{
		int x = stack.getMaxItemUseDuration() - timeLeft;
		if (x > 40)
		{
			x = 40;
		}
		double bonus = 1.0D + (x / 20) * 2;
		float damageMultiplier = 1.0F;

		if (player.isSneaking())
			JBlockHelper.destroyBlock(player, (int) bonus, worldIn, this, 2, 3);
		else
			damageMultiplier += 0.25F;

		damageMultiplier *= bonus / 4.0F;
		JDamageHelper.dealMultipleDamage(player, JAreaHelper.getEntityInArea(player, bonus), damageMultiplier, false);
	}

	private void rightHit1(ItemStack stack, World world, EntityPlayer player, int timeLeft)
	{
		int xMin = Math.round(player.getPosition().getX() - rayonArea);
		int xMax = Math.round(player.getPosition().getX() + rayonArea);
		int zMin = Math.round(player.getPosition().getZ() - rayonArea);
		int zMax = Math.round(player.getPosition().getZ() + rayonArea);
		int yMin = Math.round(player.getPosition().getY());
		int yMax = Math.round(player.getPosition().getY() + rayonArea);

		List<List<List<Boolean>>> areaBool = new ArrayList<List<List<Boolean>>>(); // Tab of boolean
		List<List<List<Boolean>>> areaExtrudBool = new ArrayList<List<List<Boolean>>>(); // Tab of boolean
		areaBool.addAll(JDome.dome(rayonArea));
		areaExtrudBool = JDome.extrudeDome(areaBool, rayonArea);
		//areaBool.addAll(JDome.dome(rayonArea - 1, rayonArea*2+1));

		int rayonOfDome = rayonArea*2 + 1;
		
		for (int y = 0; y <= rayonArea; y++)
		{ // pour chaque étage
			//JLog.write("#LINE# y :" + y + ", VALUE : " + areaExtrudBool.get(y));

			for (int x = 0; x <= xMax - xMin; x++)
			{ // je lis X
				//JLog.write("#LINE# x :" + x + ", VALUE : " + areaExtrudBool.get(y).get(x));

				for (int z = 0; z <= zMax - zMin; z++)
				{ // puis Z
					//JLog.write("#VALUE# :" + areaBool.get(y).get(x));

					//if (areaExtrudBool.get(y).get(x).get(z))
					BlockPos posOfBlock = new BlockPos(xMin + x, yMin + y, zMin + z);

					if (areaExtrudBool.get(y).get(x).get(z))
					{
						Block block = world.getBlockState(posOfBlock).getBlock();
						world.setBlockState(posOfBlock, Blocks.stone.getDefaultState());
					} // if[x][z] = true
					else
					{
						world.setBlockState(posOfBlock, Blocks.glass.getDefaultState());
					}

				} // end of for z
			} // end of for x
		} // end of for y

	}

	private void rightHit2(ItemStack stack, World world, EntityPlayer player, int timeLeft)
	{
		int xMin = Math.round(player.getPosition().getX() - rayonArea);
		int xMax = Math.round(player.getPosition().getX() + rayonArea);
		int zMin = Math.round(player.getPosition().getZ() - rayonArea);
		int zMax = Math.round(player.getPosition().getZ() + rayonArea);

		int yMin = Math.round(player.getPosition().getY());

		List<List<Boolean>> areaBool = new ArrayList<List<Boolean>>(); // Tab of boolean
		areaBool = JCircle.circle(rayonArea);

		for (int i = 0; i < rayonArea * 2 + 1; i++)
		{
			JLog.write("### : " + areaBool.get(i));
		}

		for (int y = 0; y < fireColumn; y++)
		{
			for (int x = 0; x <= xMax - xMin; x++)
			{
				for (int z = 0; z <= zMax - zMin; z++)
				{
					if (areaBool.get(x).get(z))
					{

						BlockPos posOfBlock = new BlockPos(xMin + x, yMin + y, zMin + z);
						Block block = world.getBlockState(posOfBlock).getBlock();
						if (block.isAir(world, posOfBlock) || block instanceof BlockBush)
						{
							world.setBlockState(posOfBlock, Blocks.stone.getDefaultState());
						} // if block is air
						else
						{
							areaBool.get(x).set(z, false);
						}
					} // if[x][z] = true

					// if(y == 0)
					// JLog.write("APRES : X = " + xMin + x + ", z = " + zMin + z + " Value = " + areaBool.get(x).get(z));

				} // for Z
			} // for X
		} // for Y

	}

	private void rightHit3(ItemStack stack, World worldIn, EntityPlayer player, int timeLeft)
	{
		List entity = JAreaHelper.getEntityInArea(player, 2);

		JDamageHelper.dealMultipleDamage(player, entity, 1.0F);
		JDamageHelper.witherMultipleEntity(player, entity, 7 * 20, (int) (attackDamage / 4));

	}
}
