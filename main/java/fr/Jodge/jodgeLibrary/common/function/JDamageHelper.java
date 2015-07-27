package fr.Jodge.jodgeLibrary.common.function;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;

public class JDamageHelper
{
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


}
