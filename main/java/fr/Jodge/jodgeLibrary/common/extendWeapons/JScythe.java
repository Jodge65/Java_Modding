package fr.Jodge.jodgeLibrary.common.extendWeapons;

import fr.Jodge.jodgeLibrary.common.Main;
import fr.Jodge.jodgeLibrary.common.function.JAreaHelper;
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
	private static final long start = System.currentTimeMillis() / 1000;
	private static int seconds = 0;
	public static int fireColumn;

	private static int rayonArea;

	public JScythe(String name, Item ingot, Item.ToolMaterial toolData, String modid)
	{
		super(name, ingot, toolData, modid);
		setBonusDamage(5.0F);
		comboTimer = 5 * 20;
		fireColumn = 50;
		rayonArea = 5;

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

    public void initialiseTag(ItemStack stack)
	{
    	super.initialiseTag(stack);
		stack.getTagCompound().setInteger(JNbtVar.posCenterAreaX.toString(), 0); // Right Combo
		stack.getTagCompound().setInteger(JNbtVar.posCenterAreaY.toString(), 0); // Right Previous Combo
		stack.getTagCompound().setInteger(JNbtVar.posCenterAreaZ.toString(), 0); // Left Combo
	}
	
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if(stack.getTagCompound() == null)
        {
        	initialiseTag(stack);
        }	


		if (seconds != (int) (System.currentTimeMillis() / 1000 - start))
		{
			if(!worldIn.isRemote)
			{
				if (	JNbtVar.readNbtVarDouble(stack, JNbtVar.posCenterAreaX) != 0 || 
						JNbtVar.readNbtVarDouble(stack, JNbtVar.posCenterAreaY) != 0 || 
						JNbtVar.readNbtVarDouble(stack, JNbtVar.posCenterAreaZ) != 0)
				{
					AxisAlignedBB area = JAreaHelper.circleArea(JNbtVar.readNbtVarDouble(stack, JNbtVar.posCenterAreaX), 
																JNbtVar.readNbtVarDouble(stack, JNbtVar.posCenterAreaY), 
																JNbtVar.readNbtVarDouble(stack, JNbtVar.posCenterAreaZ), 
																JNbtVar.readNbtVarDouble(stack, JNbtVar.posCenterAreaX), 
																JNbtVar.readNbtVarDouble(stack, JNbtVar.posCenterAreaY) + fireColumn, 
																JNbtVar.readNbtVarDouble(stack, JNbtVar.posCenterAreaZ),
																(double) rayonArea);
					JLog.write("### : " + area);
					EntityPlayer player = Minecraft.getMinecraft().thePlayer;
					World world = player.worldObj;
					
					ArrayList entityInArea = Lists.newArrayList();
					
					int xMin = MathHelper.floor_double(area.minX);
					int xMax = MathHelper.floor_double(area.maxX);
					int zMin = MathHelper.floor_double(area.minZ);
					int zMax = MathHelper.floor_double(area.maxZ);
					int yMin = MathHelper.floor_double(area.minY);

					List<List<Boolean>> areaBool = new ArrayList<List<Boolean>>(); 
									
					for (int x = 0; x < xMax - xMin; ++x)
					{
						areaBool.add(new ArrayList<Boolean>());

						for (int z = 0; z <= zMax - zMin; ++z)
						{
							JLog.write(" #" + x + z + "# ");

							areaBool.get(x).add(true); 
						}
					}
					List entityList;

					for (int y = 0; y < fireColumn; y++)
					{
						area = JAreaHelper.circleArea(JNbtVar.readNbtVarDouble(stack, JNbtVar.posCenterAreaX), JNbtVar.readNbtVarDouble(stack, JNbtVar.posCenterAreaY) + y, JNbtVar.readNbtVarDouble(stack, JNbtVar.posCenterAreaZ), (double) rayonArea);
						for (int x = 0; x < xMax - xMin; ++x)
						{
							for (int z = 0; z < zMax - zMin; ++z)
							{
								if (areaBool.get(x).get(z))
								{
									ArrayList whereIsEnitytInList = Lists.newArrayList();
									Block block = world.getBlockState(new BlockPos(xMin + x, yMin + y, zMin + z)).getBlock();
									if (block.isAir(world, new BlockPos(xMin + x, yMin + y, zMin + z)) ||
										block instanceof BlockBush)
									{
										List currentEntityList = JAreaHelper.getEntityInArea(player, new AxisAlignedBB(xMin + x, y, zMin + z, xMin + x + 1, yMin + y + 1, zMin + z + 1));
										int numberOfEntity = currentEntityList.size();
										int numberOfEntityInArea = entityInArea.size();
										for(int i = 0; i < numberOfEntity; i++)
										{
											if(entityInArea.get(i) instanceof EntityLivingBase && entityInArea.get(i) != null)
											{

												Entity entity = (Entity) entityInArea.get(i);
												boolean canBeAdd = true;

												for(int i1 = 0; i1 < numberOfEntityInArea; i1++)
												{
													if(entity.equals(currentEntityList.get(i1)))
													{
														canBeAdd = false;
														break;
													}
												}
												
												if(canBeAdd)
												{
													entityInArea.add(entity);
												}
											}
										}
									} // if block is air
									else
									{
										areaBool.get(x).set(z, false);
									}
								} // if[x][z] = true
							} // for Y
						} // for X
						entityList = JAreaHelper.getEntityInArea(player, area);
						JDamageHelper.fireMultiplerEntity(player, entityList, 1);

					}
				} // if x, y, z

			}
			seconds = (int) (System.currentTimeMillis() / 1000 - start);
		}

		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
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

	private void rightHit1(ItemStack stack, World worldIn, EntityPlayer player, int timeLeft)
	{

	}

	private void rightHit2(ItemStack stack, World worldIn, EntityPlayer player, int timeLeft)
	{
		JNbtVar.writeNbtVar(stack, JNbtVar.posCenterAreaX, player.posX);
		JNbtVar.writeNbtVar(stack, JNbtVar.posCenterAreaY, player.posY);
		JNbtVar.writeNbtVar(stack, JNbtVar.posCenterAreaZ, player.posZ);
	}

	private void rightHit3(ItemStack stack, World worldIn, EntityPlayer player, int timeLeft)
	{
		List entity = JAreaHelper.getEntityInArea(player, 2);

		JDamageHelper.dealMultipleDamage(player, entity, 1.0F);
		JDamageHelper.witherMultipleEntity(player, entity, 7 * 20, (int) (attackDamage / 4));

	}
}
