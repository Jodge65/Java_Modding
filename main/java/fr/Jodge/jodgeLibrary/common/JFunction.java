package fr.Jodge.jodgeLibrary.common;

import fr.Jodge.jodgeLibrary.common.toolSet.JWeapons;
import java.io.PrintStream;
import java.util.Locale;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class JFunction
{
	public static final String getJAuthor()
	{
		return "Jodge65";
	}

	public static enum nbtVar
	{
		RightCombo("rC"), 
		RightPreviousCombo("rPC"), 
		LeftCombo("lC"), 
		LeftPreviousCombo("lPC"), 
		StartCombo("sC");

		private String name = "";

		nbtVar(String name)
		{
			this.name = name;
		}

		public String toString()
		{
			return name;
		}

		public static boolean writeNbtVar(ItemStack stack, nbtVar var, int value)
		{
			boolean isWrite = false;
			if (stack.hasTagCompound())
			{
				NBTTagCompound itemData = stack.getTagCompound();

				String varName = var.toString();

				if (itemData.hasKey(varName))
				{
					stack.getTagCompound().setInteger(varName, value);
					isWrite = true;
				}
			}

			return isWrite;

		}

		public static boolean incNbtVarInt(ItemStack stack, nbtVar var)
		{
			int i = readNbtVarInt(stack, var);

			return writeNbtVar(stack, var, i + 1);
		}

		public static boolean decNbtVarInt(ItemStack stack, nbtVar var)
		{
			int i = readNbtVarInt(stack, var);
			return writeNbtVar(stack, var, i - 1);
		}

		public static int readNbtVarInt(ItemStack stack, nbtVar var)
		{
			if (stack.hasTagCompound())
			{
				NBTTagCompound itemData = stack.getTagCompound();

				String varName = var.toString();

				if (itemData.hasKey(varName))
				{
					return stack.getTagCompound().getInteger(varName);
				}
			}
			
			return 0;
		}

	}

	public static void dealDamage(EntityPlayer playerIn, EntityLivingBase targetEntity, float damageMultiplier, Boolean knockBack, Boolean canBeCritical, float criticalBonus)
	{
		if ((playerIn instanceof EntityPlayer))
		{
			if (targetEntity.canAttackWithItem())
			{
				if (!targetEntity.hitByEntity(playerIn))
				{
					float entityDamage = (float) playerIn.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
					float enchantDamage = EnchantmentHelper.func_152377_a(playerIn.getHeldItem(), targetEntity.getCreatureAttribute());
					ItemStack itemstack = playerIn.getCurrentEquippedItem();
					int knockBackPower = EnchantmentHelper.getKnockbackModifier(playerIn);
					if (playerIn.isSprinting())
					{
						knockBackPower++;
					}
					if ((entityDamage > 0.0F) || (enchantDamage > 0.0F))
					{
						boolean criticalOccure = (criticalHit(playerIn, itemstack)) && (canBeCritical.booleanValue());
						if ((criticalOccure) && (entityDamage > 0.0F))
						{
							entityDamage *= criticalBonus;
						}
						entityDamage += enchantDamage;

						boolean isBurning = false;

						int fireAspectPower = EnchantmentHelper.getFireAspectModifier(playerIn);
						if ((fireAspectPower > 0) && (!targetEntity.isBurning()))
						{
							isBurning = true;
							targetEntity.setFire(1);
						}
						double d0 = targetEntity.motionX;
						double d1 = targetEntity.motionY;
						double d2 = targetEntity.motionZ;

						entityDamage *= damageMultiplier;
						if (targetEntity.attackEntityFrom(DamageSource.causePlayerDamage(playerIn), entityDamage))
						{
							if (!knockBack.booleanValue())
							{
								knockBackPower = 0;
							}
							if (knockBackPower > 0)
							{
								targetEntity.addVelocity(-MathHelper.sin(playerIn.rotationYaw * 3.1415927F / 180.0F) * knockBackPower * 0.5F, 0.1D, MathHelper.cos(playerIn.rotationYaw * 3.1415927F / 180.0F) * knockBackPower * 0.5F);
								playerIn.motionX *= 0.6D;
								playerIn.motionZ *= 0.6D;
								playerIn.setSprinting(false);
							}
							if (((targetEntity instanceof EntityPlayerMP)) && (targetEntity.velocityChanged))
							{
								((EntityPlayerMP) targetEntity).playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(targetEntity));
								targetEntity.velocityChanged = false;
								targetEntity.motionX = d0;
								targetEntity.motionY = d1;
								targetEntity.motionZ = d2;
							}
							if (criticalOccure)
							{
								playerIn.onCriticalHit(targetEntity);
							}
							if (enchantDamage > 0.0F)
							{
								playerIn.onEnchantmentCritical(targetEntity);
							}
							if (entityDamage >= 18.0F)
							{
								playerIn.triggerAchievement(AchievementList.overkill);
							}
							playerIn.setLastAttacker(targetEntity);
							if (itemstack != null)
							{
								itemstack.hitEntity(targetEntity, playerIn);
								if (itemstack.stackSize <= 0)
								{
									playerIn.destroyCurrentEquippedItem();
								}
							}
							playerIn.addStat(StatList.damageDealtStat, Math.round(entityDamage * 10.0F));
							if (fireAspectPower > 0)
							{
								targetEntity.setFire(fireAspectPower * 4);
							}
							playerIn.addExhaustion(0.3F);
						}
						else if (isBurning)
						{
							targetEntity.extinguish();
						}
					}
				}
			}
		}
	}

	public static void dealDamage(EntityPlayer playerIn, EntityLivingBase targetEntity)
	{
		dealDamage(playerIn, targetEntity, 1.0F);
	}

	public static void dealDamage(EntityPlayer playerIn, EntityLivingBase targetEntity, float damageMultiplier)
	{
		dealDamage(playerIn, targetEntity, damageMultiplier, Boolean.valueOf(true));
	}

	public static void dealDamage(EntityPlayer playerIn, EntityLivingBase targetEntity, float damageMultiplier, Boolean knockBack)
	{
		dealDamage(playerIn, targetEntity, damageMultiplier, knockBack, Boolean.valueOf(true));
	}

	public static void dealDamage(EntityPlayer playerIn, EntityLivingBase targetEntity, float damageMultiplier, Boolean knockBack, Boolean canBeCritical)
	{
		dealDamage(playerIn, targetEntity, damageMultiplier, knockBack, canBeCritical, 1.5F);
	}

	public static boolean criticalHit(EntityPlayer playerIn, ItemStack itemstack)
	{
		return false;
	}

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

	public static String convertNameToUnLocalizedName(String unLocalized)
	{
		String temps = "";
		for (int i = 0; i < unLocalized.length(); i++)
		{
			if (unLocalized.charAt(i) != ' ')
			{
				if (i > 0)
				{
					if (unLocalized.charAt(i - 1) == ' ')
					{
						temps = temps + unLocalized.toUpperCase(Locale.ENGLISH).charAt(i);
					}
					else
					{
						temps = temps + unLocalized.toLowerCase(Locale.ENGLISH).charAt(i);
					}
				}
				else
				{
					temps = temps + unLocalized.toLowerCase(Locale.ENGLISH).charAt(i);
				}
			}
		}
		return temps;
	}

	public static String convertNameToOreDictionaryName(String name)
	{
		String lowerUnLocalizedName = name.toLowerCase(Locale.ENGLISH) + " ";
		String oreDictionary = "";
		String partDeb = "";
		String partFin = "";
		String temps = "";
		boolean end = false;
		boolean mainWordFind = false;
		boolean fillPart2 = false;
		String lastStep = "";
		int i = 0;
		while (i < lowerUnLocalizedName.length())
		{
			if (lowerUnLocalizedName.charAt(i) != ' ')
			{
				temps = temps + lowerUnLocalizedName.charAt(i);
			}
			else
			{
				if (temps.equalsIgnoreCase("workbench"))
				{
					oreDictionary = "workbench";
					end = true;
				}
				else if (temps.equalsIgnoreCase("ladder"))
				{
					oreDictionary = "blockLadder";
					end = true;
				}
				else if (temps.equalsIgnoreCase("sapling"))
				{
					oreDictionary = "treeSapling";
					end = true;
				}
				else if ((temps.equalsIgnoreCase("block")) || (temps.equalsIgnoreCase("circuit")) || (temps.equalsIgnoreCase("dust")) || (temps.equalsIgnoreCase("gem")) || (temps.equalsIgnoreCase("ingot")) || (temps.equalsIgnoreCase("nugget")) || (temps.equalsIgnoreCase("ore")) || (temps.equalsIgnoreCase("piece")) || (temps.equalsIgnoreCase("wire")) || (temps.equalsIgnoreCase("sword")) || (temps.equalsIgnoreCase("shield")) || (temps.equalsIgnoreCase("axe")) || (temps.equalsIgnoreCase("pickaxe")) || (temps.equalsIgnoreCase("hoe")) || (temps.equalsIgnoreCase("spade")) || (temps.equalsIgnoreCase("scythe")) || (temps.equalsIgnoreCase("hammer")) || (temps.equalsIgnoreCase("helmet")) || (temps.equalsIgnoreCase("chestplate")) || (temps.equalsIgnoreCase("leggings")) || (temps.equalsIgnoreCase("boots")))
				{
					oreDictionary = temps;
					mainWordFind = true;
				}
				else if ((temps.equalsIgnoreCase("crafting")) || (temps.equalsIgnoreCase("redstone")) || (temps.equalsIgnoreCase("secret")) || (temps.equalsIgnoreCase("traped")))
				{
					lastStep = temps;
				}
				else if ((temps.equalsIgnoreCase("birch")) || (temps.equalsIgnoreCase("spruce")) || (temps.equalsIgnoreCase("jungle")) || (temps.equalsIgnoreCase("willow")) || (temps.equalsIgnoreCase("ebony")) || (temps.equalsIgnoreCase("silkwood")))
				{
					lastStep = "wood";
				}
				else if (temps.equalsIgnoreCase("chest"))
				{
					if (lastStep.equalsIgnoreCase("traped"))
					{
						oreDictionary = "trappedChest";
					}
					else
					{
						oreDictionary = "blockChest";
					}
					end = true;
				}
				else if (temps.equalsIgnoreCase("door"))
				{
					if (lastStep.equalsIgnoreCase("wood"))
					{
						oreDictionary = "doorWood";
						end = true;
					}
					else
					{
						oreDictionary = temps;
						mainWordFind = true;
					}
				}
				else if (temps.equalsIgnoreCase("fence"))
				{
					if (lastStep.equalsIgnoreCase("wood"))
					{
						oreDictionary = "fenceWood";
					}
					lastStep = "fence";
				}
				else if (temps.equalsIgnoreCase("fencegate"))
				{
					if (lastStep.equalsIgnoreCase("wood"))
					{
						oreDictionary = "fenceWood";
						end = true;
					}
					else
					{
						oreDictionary = "fenceGate";
						mainWordFind = true;
					}
				}
				else if (temps.equalsIgnoreCase("leaves"))
				{
					if (lastStep.equalsIgnoreCase("wood"))
					{
						oreDictionary = "treeLeaves";
						end = true;
					}
					else
					{
						oreDictionary = temps;
						mainWordFind = true;
					}
				}
				else if (temps.equalsIgnoreCase("plank"))
				{
					if (lastStep.equalsIgnoreCase("wood"))
					{
						oreDictionary = "plankWood";
						end = true;
					}
					else
					{
						oreDictionary = temps;
						mainWordFind = true;
					}
				}
				else if (temps.equalsIgnoreCase("slab"))
				{
					if (lastStep.equalsIgnoreCase("wood"))
					{
						oreDictionary = "slabWood";
						end = true;
					}
					else
					{
						oreDictionary = temps;
						mainWordFind = true;
					}
				}
				else if (temps.equalsIgnoreCase("stair"))
				{
					if (lastStep.equalsIgnoreCase("wood"))
					{
						oreDictionary = "stairWood";
						end = true;
					}
					else
					{
						oreDictionary = temps;
						mainWordFind = true;
					}
				}
				else if (temps.equalsIgnoreCase("stick"))
				{
					if (lastStep.equalsIgnoreCase("wood"))
					{
						oreDictionary = "stickWood";
						end = true;
					}
					else
					{
						oreDictionary = temps;
						mainWordFind = true;
					}
				}
				else if (temps.equalsIgnoreCase("table"))
				{
					if (lastStep.equalsIgnoreCase("crafting"))
					{
						oreDictionary = "workbench";
						end = true;
					}
					else
					{
						oreDictionary = temps;
						mainWordFind = true;
					}
				}
				else if (temps.equalsIgnoreCase("torch"))
				{
					if (lastStep.equalsIgnoreCase("redstone"))
					{
						oreDictionary = "redstoneTorch";
					}
					else
					{
						oreDictionary = "blockTorch";
					}
					end = true;
				}
				else if (temps.equalsIgnoreCase("trapdoor"))
				{
					if (lastStep.equalsIgnoreCase("secret"))
					{
						oreDictionary = "secretTrapdoor";
					}
					else
					{
						oreDictionary = "trapdoor";
					}
					end = true;
				}
				else if (temps.equalsIgnoreCase("wall"))
				{
					if (lastStep.equalsIgnoreCase("wood"))
					{
						oreDictionary = "wallWood";
						end = true;
					}
					else
					{
						oreDictionary = temps;
						mainWordFind = true;
					}
				}
				else if (temps.equalsIgnoreCase("wood"))
				{
					if (lastStep.equalsIgnoreCase("wood"))
					{
						oreDictionary = "treeWood";
						end = true;
					}
				}
				else
				{
					lastStep = temps;
				}
				if (!mainWordFind)
				{
					partDeb = partDeb + temps.substring(0, 1).toUpperCase();
					if (temps.length() > 1)
					{
						partDeb = partDeb + temps.substring(1).toLowerCase();
					}
				}
				else if (fillPart2)
				{
					partFin = partFin + temps.substring(0, 1).toUpperCase();
					if (temps.length() > 1)
					{
						partFin = partFin + temps.substring(1).toLowerCase();
					}
				}
				else
				{
					fillPart2 = true;
				}
				temps = "";
			}
			if (end)
			{
				break;
			}
			i++;
		}
		if (mainWordFind)
		{
			oreDictionary = oreDictionary + partDeb + partFin;
		}
		if (oreDictionary.isEmpty())
		{
			oreDictionary = convertNameToUnLocalizedName(name);
		}
		Jlog("[WARNING] The Block \"" + name + "\" doens't have an oreDictionnary name. \"" + oreDictionary + "\" was generate.");
		return oreDictionary;
	}

	public static Boolean addBasicRecipe(Item finalItem, Item ingot, String format)
	{
		String ligne1 = "   ";
		String ligne2 = "   ";
		String ligne3 = "   ";
		if (format.equalsIgnoreCase("sword"))
		{
			ligne1 = " I ";
			ligne2 = " I ";
			ligne3 = " S ";
		}
		else if (format.equalsIgnoreCase("pickaxe"))
		{
			ligne1 = "III";
			ligne2 = " S ";
			ligne3 = " S ";
		}
		else if (format.equalsIgnoreCase("axe"))
		{
			ligne1 = "II ";
			ligne2 = "IS ";
			ligne3 = " S ";
		}
		else if (format.equalsIgnoreCase("spade"))
		{
			ligne1 = " I ";
			ligne2 = " S ";
			ligne3 = " S ";
		}
		else if (format.equalsIgnoreCase("hoe"))
		{
			ligne1 = "II ";
			ligne2 = " S ";
			ligne3 = " S ";
		}
		else if (format.equalsIgnoreCase("scythe"))
		{
			ligne1 = "IIS";
			ligne2 = "I S";
			ligne3 = "  S";
		}
		else if (format.equalsIgnoreCase("helmet"))
		{
			ligne1 = "III";
			ligne2 = "I I";
			ligne3 = "   ";
		}
		else if (format.equalsIgnoreCase("chestplate"))
		{
			ligne1 = "I I";
			ligne2 = "III";
			ligne3 = "III";
		}
		else if (format.equalsIgnoreCase("leggings"))
		{
			ligne1 = "III";
			ligne2 = "I I";
			ligne3 = "I I";
		}
		else if (format.equalsIgnoreCase("boots"))
		{
			ligne1 = "   ";
			ligne2 = "I I";
			ligne3 = "I I";
		}
		if ((ligne1 != "   ") || (ligne2 != "   ") || (ligne3 != "   "))
		{
			if (ligne1 == "   ")
			{
				GameRegistry.addRecipe(new ItemStack(finalItem), new Object[] { ligne2, ligne3, Character.valueOf('I'), ingot, Character.valueOf('S'), Item.getByNameOrId("stick") });
			}
			else if (ligne3 == "   ")
			{
				GameRegistry.addRecipe(new ItemStack(finalItem), new Object[] { ligne1, ligne2, Character.valueOf('I'), ingot, Character.valueOf('S'), Item.getByNameOrId("stick") });
			}
			else
			{
				GameRegistry.addRecipe(new ItemStack(finalItem), new Object[] { ligne1, ligne2, ligne3, Character.valueOf('I'), ingot, Character.valueOf('S'), Item.getByNameOrId("stick") });
			}
			return Boolean.valueOf(true);
		}
		Jlog("[WARNING] The crafting schema \"" + format + "\" doens't exist");
		return Boolean.valueOf(false);
	}

	public static void drawTexturedModalRect(int x, int y, float z, int textureX, int textureY, int width, int height)
	{
		drawTexturedModalRect(x, y, z, textureX, textureY, width, height, 0.00390625F, 0.00390625F);
	}

	public static void drawTexturedModalRect(int x, int y, float z, int textureX, int textureY, int width, int height, float f, float f1)
	{
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();

		worldrenderer.startDrawingQuads();
		worldrenderer.addVertexWithUV(x + 0, y + height, z, (textureX + 0) * f, (textureY + height) * f1);
		worldrenderer.addVertexWithUV(x + width, y + height, z, (textureX + width) * f, (textureY + height) * f1);
		worldrenderer.addVertexWithUV(x + width, y + 0, z, (textureX + width) * f, (textureY + 0) * f1);
		worldrenderer.addVertexWithUV(x + 0, y + 0, z, (textureX + 0) * f, (textureY + 0) * f1);
		tessellator.draw();
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

	public static void write(String text)
	{
		System.out.println(text);
	}

	public static void writeLog(String text)
	{
		System.out.println(text);
	}

	public static void Jlog(String text)
	{
		writeLog(text);
	}
}
