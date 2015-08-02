package fr.Jodge.jodgeLibrary.common.function;

import java.util.List;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;

public class JDamageHelper
{
	private static boolean forcedKnockBack = false;
	private static int powerForcedKnockBack = 0;
	
	private static boolean bonusPhisicHit = false;
	private static float powerBonusPhisicHit = 0;

	private static boolean bonusMagicHit = false;
	private static float powerBonusMagicHit = 0;

	private static boolean bonusFixPhisicHit = false;
	private static float powerFixBonusPhisicHit = 0;

	private static boolean bonusFixMagicHit = false;
	private static float powerFixBonusMagicHit = 0;
	
	private static void initVar()
	{
		forcedKnockBack = false;
		powerForcedKnockBack = 0;
		
		bonusPhisicHit = false;
		powerBonusPhisicHit = 0;

		bonusMagicHit = false;
		powerBonusMagicHit = 0;

		bonusFixPhisicHit = false;
		powerFixBonusPhisicHit = 0;

		bonusFixMagicHit = false;
		powerFixBonusMagicHit = 0;
	}
	
	public static void addKnockBack(int power)
	{
		forcedKnockBack = true;
		powerForcedKnockBack = power;
	}
	public static void addFixPhisicDamage(float power)
	{
		bonusFixPhisicHit = true;
		powerFixBonusPhisicHit = power;
	}
	public static void addFixMagicDamage(float power)
	{
		bonusFixMagicHit = true;
		powerFixBonusPhisicHit = power;
	}
	public static void addPercentPhisicDamage(float percent)
	{
		bonusPhisicHit = true;
		powerBonusPhisicHit = percent;
	}
	public static void addPercentMagicDamage(float percent)
	{
		bonusMagicHit = true;
		powerBonusMagicHit = percent;
	}
	
	public static void dealDamage(EntityPlayer playerIn, Entity targetEntity, float damageMultiplier, Boolean knockBack, Boolean canBeCritical, float criticalBonus)
	{
		if(!(targetEntity instanceof EntityLivingBase))
		{ // if is not something living
			return;
		}
		
		if (playerIn instanceof EntityPlayer)
		{ // if user is a player
			if (targetEntity.canAttackWithItem())
			{ // if user can attack whit this item
				if (!targetEntity.hitByEntity(playerIn))
				{ // if entity can be hit by player
					float entityDamage = (float) playerIn.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
					float enchantDamage = EnchantmentHelper.func_152377_a(playerIn.getHeldItem(), ((EntityLivingBase) targetEntity).getCreatureAttribute());
					if(bonusPhisicHit)
					{
						entityDamage *= powerBonusPhisicHit;
					}
					if(bonusMagicHit)
					{
						enchantDamage *= powerBonusMagicHit;
					}
					if(bonusFixPhisicHit)
					{
						entityDamage += powerBonusPhisicHit;
					}
					if(bonusFixMagicHit)
					{
						enchantDamage += powerFixBonusPhisicHit;
					}
					ItemStack itemstack = playerIn.getCurrentEquippedItem();
					int knockBackPower = EnchantmentHelper.getKnockbackModifier(playerIn);
					if (playerIn.isSprinting())
					{
						knockBackPower++;
					}
					
					if ((entityDamage > 0.0F) || (enchantDamage > 0.0F))
					{ // if the player deal some damage
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
							if (!knockBack)
							{
								knockBackPower = 0;
							}
							if(forcedKnockBack)
							{
								knockBackPower += powerForcedKnockBack;
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
								itemstack.hitEntity((EntityLivingBase) targetEntity, playerIn);
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
		initVar();
	}

	public static void dealDamage(EntityPlayer playerIn, Entity targetEntity)
	{
		dealDamage(playerIn, targetEntity, 1.0F);
	}

	public static void dealDamage(EntityPlayer playerIn, Entity targetEntity, float damageMultiplier)
	{
		dealDamage(playerIn, targetEntity, damageMultiplier, true);
	}

	public static void dealDamage(EntityPlayer playerIn, Entity targetEntity, float damageMultiplier, Boolean knockBack)
	{
		dealDamage(playerIn, targetEntity, damageMultiplier, knockBack, true);
	}

	public static void dealDamage(EntityPlayer playerIn, Entity targetEntity, float damageMultiplier, Boolean knockBack, Boolean canBeCritical)
	{
		dealDamage(playerIn, targetEntity, damageMultiplier, knockBack, canBeCritical, 1.5F);
	}

	
	public static boolean criticalHit(EntityPlayer playerIn, ItemStack itemstack)
	{
		return false;
	}

	
	public static void dealMultipleDamage(EntityPlayer player, List entityList, float damageMultiplier, Boolean knockBack, Boolean canBeCritical, float criticalBonus)
	{
		boolean canBeAttack = false;
		EntityLivingBase entity;
		for (int i = 0; i < entityList.size(); i++)
		{
			if ((entityList.get(i) instanceof EntityLivingBase))
			{
				entity = (EntityLivingBase) entityList.get(i);
				if(entityList.get(i) instanceof EntityPlayer)
				{
					if(player.canAttackPlayer((EntityPlayer) entity))
					{
						canBeAttack = true;
					}
				}
				else
				{
					canBeAttack = true;
				}
				
				if(canBeAttack)
				{
					JDamageHelper.dealDamage(player, entity, damageMultiplier, knockBack, canBeCritical, criticalBonus);
				}
			}
		}
	}
	public static void dealMultipleDamage(EntityPlayer playerIn, List entityList, float damageMultiplier, Boolean knockBack, Boolean canBeCritical)
	{
		dealMultipleDamage(playerIn, entityList, damageMultiplier, knockBack, canBeCritical, 1.5F);
	}
	public static void dealMultipleDamage(EntityPlayer playerIn, List entityList, float damageMultiplier, Boolean knockBack)
	{
		dealMultipleDamage(playerIn, entityList, damageMultiplier, knockBack, true);
	}
	public static void dealMultipleDamage(EntityPlayer playerIn, List entityList, float damageMultiplier)
	{
		dealMultipleDamage(playerIn, entityList, damageMultiplier, true);
	}
	public static void dealMultipleDamage(EntityPlayer playerIn, List entityList)
	{
		dealMultipleDamage(playerIn, entityList, 1.0F);
	}
	
	public static void poisonMultipleEntity(EntityPlayer player, List entityList, int duration, int power)
	{
		potionMultiplerEntity(player, Potion.poison, entityList, duration, power);
	}
	public static void poisonMultipleEntity(EntityPlayer player, List entityList, int duration)
	{
		poisonMultipleEntity(player, entityList, duration, 0);
	}
	
	public static void witherMultipleEntity(EntityPlayer player, List entityList, int duration, int power)
	{
		potionMultiplerEntity(player, Potion.wither, entityList, duration, power);
	}
	public static void witherMultipleEntity(EntityPlayer player, List entityList, int duration)
	{
		witherMultipleEntity(player, entityList, duration, 0);
	}
	
	public static void potionMultiplerEntity(EntityPlayer player, Potion potion, List entityList, int duration, int power)
	{
		boolean canBePotion = false;
		EntityLivingBase entity;
		for (int i = 0; i < entityList.size(); i++)
		{
			if ((entityList.get(i) instanceof EntityLivingBase))
			{
				entity = (EntityLivingBase) entityList.get(i);
				if(entityList.get(i) instanceof EntityPlayer)
				{
					if(player.canAttackPlayer((EntityPlayer) entity))
					{
						canBePotion = true;
					}
				}
				else
				{
					if(!(entity.isEntityUndead() && potion.equals(potion.poison)))
					{
						canBePotion = true;
					}
				}
				
				if(canBePotion)
				{
					entity.addPotionEffect(new PotionEffect(potion.id, duration, power));
				}
			}
		}
	}
	public static void potionMultiplerEntity(EntityPlayer player, Potion potion, List entityList, int duration)
	{
		potionMultiplerEntity(player, potion, entityList, duration, 0);
	}
	
	public static void fireMultiplerEntity(EntityPlayer player, List entityList, int duration)
	{
		boolean canBeFire = false;
		EntityLivingBase entity;
		
		for (int i = 0; i < entityList.size(); i++)
		{
			if ((entityList.get(i) instanceof EntityLivingBase))
			{
				entity = (EntityLivingBase) entityList.get(i);
				if(entityList.get(i) instanceof EntityPlayer)
				{
					if(player.canAttackPlayer((EntityPlayer) entity))
					{
						canBeFire = true;
					}
				}
				else
				{
					canBeFire = true;
				}
				
				if(canBeFire)
				{
					entity.setFire(duration);
				}
			}
		}
	}
}
