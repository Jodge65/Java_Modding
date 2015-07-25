package fr.Jodge.jodgeLibrary.common.toolSet;

import fr.Jodge.jodgeLibrary.common.CommonProxy;
import fr.Jodge.jodgeLibrary.common.JFunction;
import fr.Jodge.jodgeLibrary.common.Main;
import fr.Jodge.jodgeLibrary.common.JFunction.nbtVar;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCrops;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class JScythe extends JWeapons
{
	public JScythe(String name, Item ingot, Item.ToolMaterial toolData, String modid)
	{
		super(name, ingot, toolData, modid);
		setBonusDamage(5.0F);
		this.comboTimer = 5*20;

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

	public void onPlayerStoppedUsing(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, int timeLeft)
	{
		if (!worldIn.isRemote)
		{
			int x = itemStackIn.getMaxItemUseDuration() - timeLeft;
			if (x > 40)
			{
				x = 40;
			}
			double bonus = 1.0D + x / 20;
			float damageMultiplier = 0.75F;

			nbtVar.incNbtVarInt(itemStackIn, nbtVar.RightCombo);
			AxisAlignedBB hitArea;
			if (playerIn.isSneaking())
			{
				hitArea = new AxisAlignedBB(playerIn.posX, playerIn.posY, playerIn.posZ, playerIn.posX + 1.0D, playerIn.posY + 1.0D, playerIn.posZ + 1.0D).expand(bonus, 0.0D, bonus);
				JFunction.destroyBlock(playerIn, (int) bonus, worldIn, this, 2, 3);
			}
			else
			{
				hitArea = new AxisAlignedBB(playerIn.posX, playerIn.posY + 1.0D, playerIn.posZ, playerIn.posX + 1.0D, playerIn.posY + 2.0D, playerIn.posZ + 1.0D).expand(bonus, 0.0D, bonus);
				damageMultiplier += 0.5F;
			}
			damageMultiplier *= (0.25F + (float) (bonus / 4.0D)) * 1.5F * (0.5F / nbtVar.readNbtVarInt(itemStackIn, nbtVar.RightCombo) + 0.5F);

			List entityList = playerIn.worldObj.getEntitiesWithinAABBExcludingEntity(playerIn, hitArea);
			for (int i = 0; i < entityList.size(); i++)
			{
				if ((entityList.get(i) instanceof EntityLivingBase))
				{
					JFunction.dealDamage(playerIn, (EntityLivingBase) entityList.get(i), damageMultiplier, Boolean.valueOf(false));
				}
			}
		}
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
}
