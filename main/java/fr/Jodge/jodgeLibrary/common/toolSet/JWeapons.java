package fr.Jodge.jodgeLibrary.common.toolSet;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import fr.Jodge.jodgeLibrary.common.CommonProxy;
import fr.Jodge.jodgeLibrary.common.JFunction;
import fr.Jodge.jodgeLibrary.common.JFunction.nbtVar;
import fr.Jodge.jodgeLibrary.common.Main;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class JWeapons extends ItemSword
{
	protected Item ingot;
	protected boolean canBeRepair = false;
	protected String oreDictionaryName = "";
	protected float attackDamage;
	protected final Item.ToolMaterial material;
	protected String modid;
	protected boolean isGoodbombo;
	
	public int timer = 0;
	
	public int comboTimer;

	public JWeapons(String name, Item.ToolMaterial toolData, String modid)
	{
		super(toolData);

		this.material = toolData;
		this.maxStackSize = 1;
		this.comboTimer = 2 * 20;
		this.isGoodbombo = true;
		this.modid = modid;
		setMaxDamage(this.material.getMaxUses());
		setCreativeTab(CreativeTabs.tabCombat);
		this.attackDamage = this.material.getDamageVsEntity();
		String m = JFunction.convertNameToUnLocalizedName(name);
		setUnlocalizedName(m);
		GameRegistry.registerItem(this, m);
		Main.proxy.registerItemTexture(this, getUnlocalizedNameAlone(), modid);
		if (this.oreDictionaryName.isEmpty())
		{
			this.oreDictionaryName = JFunction.convertNameToOreDictionaryName(name);
		}
		setOreDictionaryName(this.oreDictionaryName);
		OreDictionary.registerOre(this.oreDictionaryName, this);
	}

	public JWeapons(String name, Item ingot, Item.ToolMaterial toolData, String modid)
	{
		this(name, toolData, modid);
		this.ingot = ingot;
	}

	public JWeapons(String name, Block block, Item.ToolMaterial toolData, String modid)
	{
		this(name, getItemFromBlock(block), toolData, modid);
	}

	public boolean getIsRepairable(ItemStack input, ItemStack repair)
	{
		if (this.canBeRepair)
		{
			if ((nbtVar.readNbtVarInt(input, nbtVar.RightCombo) == 0) && (nbtVar.readNbtVarInt(input, nbtVar.LeftCombo) == 0))
			{
				return repair.getItem() == this.ingot;
			}
			return false;
		}
		return false;
	}

	public Multimap getItemAttributeModifiers()
	{
		Multimap multimap = HashMultimap.create();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Weapon modifier", this.attackDamage, 0));
		return multimap;
	}

	public boolean canHarvestBlock(Block blockIn)
	{
		return false;
	}

	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
	{
		JFunction.dealDamage(player, (EntityLivingBase) entity, 1.0F, Boolean.valueOf(false));
		return true;
	}

	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if(stack.getTagCompound() == null)
        {
        	stack.setTagCompound(new NBTTagCompound());
       
        	stack.getTagCompound().setInteger(nbtVar.RightCombo.toString(), 0); // Right Combo
        	stack.getTagCompound().setInteger(nbtVar.RightPreviousCombo.toString(),0); // Right Previous Combo
        	stack.getTagCompound().setInteger(nbtVar.LeftCombo.toString(), 0); // Left Combo
        	stack.getTagCompound().setInteger(nbtVar.LeftPreviousCombo.toString(), 0); // Left Previous Combo
        	stack.getTagCompound().setInteger(nbtVar.StartCombo.toString(),timer); // actualTimer
        }		
		

		if ((nbtVar.readNbtVarInt(stack, nbtVar.RightCombo) != 0) || (nbtVar.readNbtVarInt(stack, nbtVar.LeftCombo) != 0))
		{
			
			timer++;
			// Protection again the limit of value
			if (timer < 0)
			{
				timer = 0;
			}
			
			// Protection against restart & limit of value
			if(nbtVar.readNbtVarInt(stack, nbtVar.StartCombo) > timer)
			{
				nbtVar.writeNbtVar(stack, nbtVar.StartCombo, timer);
			}
			
			int seconds = timer  - nbtVar.readNbtVarInt(stack, nbtVar.StartCombo);

			if(seconds >= this.comboTimer)
			{
				JFunction.write("### ### : " + seconds + " >= " + this.comboTimer + " ?");

				nbtVar.writeNbtVar(stack, nbtVar.RightCombo, 0);
				nbtVar.writeNbtVar(stack, nbtVar.LeftCombo, 0);
				nbtVar.writeNbtVar(stack, nbtVar.StartCombo, timer);
			}
			else if (	nbtVar.readNbtVarInt(stack, nbtVar.RightCombo) != nbtVar.readNbtVarInt(stack, nbtVar.RightPreviousCombo) || 
						nbtVar.readNbtVarInt(stack, nbtVar.LeftCombo) != nbtVar.readNbtVarInt(stack, nbtVar.LeftPreviousCombo))
			{
				nbtVar.writeNbtVar(stack, nbtVar.StartCombo, timer);
			}
			if (nbtVar.readNbtVarInt(stack, nbtVar.RightCombo) != nbtVar.readNbtVarInt(stack, nbtVar.RightPreviousCombo))
			{
				nbtVar.writeNbtVar(stack, nbtVar.RightPreviousCombo, nbtVar.readNbtVarInt(stack, nbtVar.RightCombo));
			}
			if (nbtVar.readNbtVarInt(stack, nbtVar.LeftCombo) != nbtVar.readNbtVarInt(stack, nbtVar.LeftPreviousCombo))
			{
				nbtVar.writeNbtVar(stack, nbtVar.LeftPreviousCombo, nbtVar.readNbtVarInt(stack, nbtVar.LeftCombo));
			}
		}
	}

	public Item getIngot()
	{
		return this.ingot;
	}

	public String getOreDic()
	{
		return getOreDictionaryName();
	}

	public String getOreDictionaryName()
	{
		return this.oreDictionaryName;
	}

	public float getHitDamage()
	{
		return this.attackDamage;
	}

	public String getModid()
	{
		return this.modid;
	}

	public String getUnlocalizedNameAlone()
	{
		return getUnlocalizedName().substring(5);
	}

	void setOreDictionaryName(String oreDictionaryName)
	{
		this.oreDictionaryName = oreDictionaryName;
	}

	public void setBonusDamage(float bonusDamage)
	{
		this.attackDamage = (bonusDamage + this.material.getDamageVsEntity());
	}

	public boolean destructibleBlock(Block block, BlockPos pos, World worldIn, EntityPlayer player)
	{
		return false;
	}
}
