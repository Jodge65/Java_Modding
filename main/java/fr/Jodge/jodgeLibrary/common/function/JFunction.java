package fr.Jodge.jodgeLibrary.common.function;

import java.util.Locale;

import fr.Jodge.jodgeLibrary.common.extendWeapons.JWeapons;


import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.enchantment.EnchantmentHelper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.nbt.NBTTagCompound;

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
		JLog.write("[WARNING] The Block \"" + name + "\" doens't have an oreDictionnary name. \"" + oreDictionary + "\" was generate.");
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
		JLog.write("[WARNING] The crafting schema \"" + format + "\" doens't exist");
		return Boolean.valueOf(false);
	}


}
