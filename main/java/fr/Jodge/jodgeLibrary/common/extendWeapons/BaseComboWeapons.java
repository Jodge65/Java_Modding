package fr.Jodge.jodgeLibrary.common.extendWeapons;

import java.util.HashMap;
import java.util.Map;

import fr.Jodge.jodgeLibrary.common.JCommonCreate;
import fr.Jodge.jodgeLibrary.common.skills.BaseSkills;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BaseComboWeapons extends Item implements JCommonCreate
{
	/** Ore dictionary name of the weapon */
	protected String oreDictionaryName = "";

	
    public BaseComboWeapons()
    {
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

   
    /**
     * Return all time false. The item can't be repair on Anvil
     *  
     * @param toRepair The ItemStack to be repaired
     * @param repair The ItemStack that should repair this Item (leather for leather armor, etc.)
     */
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return false;
    }  
    
    
	public String getOreDic()
	{
		return getOreDictionaryName();
	}
	
	@Override
	public void setOreDictionaryName(String oreDictionnaryName)
	{
		this.oreDictionaryName = oreDictionnaryName;
	}
	
	@Override
	public String getOreDictionaryName()
	{
		return this.oreDictionaryName;
	}
}
