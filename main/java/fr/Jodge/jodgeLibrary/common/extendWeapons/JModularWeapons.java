package fr.Jodge.jodgeLibrary.common.extendWeapons;

import fr.Jodge.jodgeLibrary.common.function.JNbtVar;
import fr.Jodge.jodgeLibrary.common.function.JToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class JModularWeapons extends JWeapons
{
	
	public JModularWeapons(String name, String modid)
	{
		super(name, null, modid);
		//createTool();
	}
	
	public void setToolMaterial(ItemStack stack, JNbtVar slot, String material)
	{
    	stack.getTagCompound().setInteger(slot.toString(), JToolMaterial.getIdOfToolMaterial(material)); // Head	
	}
	
	public void createTool(ItemStack stack)
	{
		createTool(stack, null, null, null, null);
	}
	public void createTool(ItemStack stack, String headMaterial, String crossMaterial, String stickMaterial, String mainMaterial)
	{
		if(stack.getTagCompound() == null) stack.setTagCompound(new NBTTagCompound());
		if(headMaterial == null) 
			headMaterial = "null";
		setToolMaterial(stack, JNbtVar.ToolMaterial_head, headMaterial);
		if(crossMaterial != null) 
			crossMaterial = "null";
		setToolMaterial(stack, JNbtVar.ToolMaterial_cross, crossMaterial);
		if(stickMaterial != null) 
			stickMaterial = "null";
		setToolMaterial(stack, JNbtVar.ToolMaterial_stick, stickMaterial);
		if(mainMaterial != null) 
			mainMaterial = "null";
		setToolMaterial(stack, JNbtVar.ToolMaterial_main, mainMaterial);
	}
	
	public void setDamage()
	{
		setDamage(null);
	}
	public void setDamage(ItemStack stack)
	{
		float nbValue = 0;
		float damage = 0;
		ToolMaterial temps;
		JNbtVar t;
		for(int i = 0; i < 4; i++)
		{
			switch(i)
			{
				case 0:
					t = JNbtVar.ToolMaterial_head;
					break;
				case 1:
					t = JNbtVar.ToolMaterial_stick;
					break;
				case 2:
					t = JNbtVar.ToolMaterial_cross;
					break;
				default:
					t = JNbtVar.ToolMaterial_main;
					break;
			}
			temps = JToolMaterial.getToolMaterialById(JNbtVar.readNbtVarInt(stack, t));

			if(temps != null)
			{
				damage += temps.getDamageVsEntity();
				nbValue++;
			}
		}

		
		this.attackDamage = damage / nbValue;
	}
	
	public boolean getIsRepairable(ItemStack input, ItemStack repair)
	{
		return false;
	}
}
