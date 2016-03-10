package fr.Jodge.jodgeLibrary.common.function;

import java.util.ArrayList;
import java.util.List;

import fr.Jodge.jodgeLibrary.common.Area.JCircle;

import akka.japi.Pair;

import net.minecraft.item.Item.ToolMaterial;

public class JToolMaterial
{
	private static List<ToolMaterial> listOfToolMaterial = new ArrayList<ToolMaterial>();
	private static List<String> listOfToolName = new ArrayList<String>();
	
	public static void initialize()
	{
		addToolMaterial("null", null);
		addToolMaterial("wood", ToolMaterial.WOOD);
		addToolMaterial("stone", ToolMaterial.STONE);
		addToolMaterial("iron", ToolMaterial.IRON);
		addToolMaterial("gold", ToolMaterial.GOLD);
		addToolMaterial("diamond", ToolMaterial.EMERALD);
	}
	
	/**
	 * 
	 * @param toolMaterial (String) name of the toolMaterial 
	 * @return (ToolMaterial) tool material which is refers by the name
	 */
	public static ToolMaterial getToolMaterialByString(String toolMaterial)
	{

		return getToolMaterialById(getIdOfToolMaterial(toolMaterial));
	}
	
	/**
	 *  Wood = 1; Stone = 2; Iron = 3; Gold = 4; Diamond = 5; </BR>
	 *  it's suppose that if you add a value, nobody know is ID, so use "byName" when you don't need a Vanilla ToolMaterial.
	 * @param id (int) id of the toolMaterial in the list
	 * @return (ToolMaterial) tool material which is refers by the name
	 */
	public static ToolMaterial getToolMaterialById(int id)
	{
		return listOfToolMaterial.get(id);
	}
	
	public static int getIdOfToolMaterial(String name)
	{
		int index = 0;
		for(String n : listOfToolName)
		{ // looking for the name
			if(n.equalsIgnoreCase(name))
			{
				break;
			}
			index++;
		}
		return index;
	}
	/**
	 * 
	 * @param name (String) Name of the tool to add on list. 
	 * @param material (ToolMaterial) Your Personal ToolMaterial to add on list
	 * @return (Boolean) True if the material is add else, return False
	 */
	public static boolean addToolMaterial(String name, ToolMaterial material)
	{
		boolean alreadyExist = false;
		int index = 0;
		for(String n : listOfToolName)
		{ // looking for the name
			if(n.equalsIgnoreCase(name))
			{
				alreadyExist = true;
				break;
			}
			index++;
		}
		
		if(alreadyExist)
		{ // if exist then, check is same
			ToolMaterial temps = listOfToolMaterial.get(index);
			boolean flag = temps.equals(material);
			if(!flag)
				flag = 	(temps.getHarvestLevel() == material.getHarvestLevel()) && 
						(temps.getDamageVsEntity() == material.getDamageVsEntity()) && 
						(temps.getMaxUses() == material.getMaxUses()) &&
						(temps.getEfficiencyOnProperMaterial() == material.getEfficiencyOnProperMaterial()) &&
						(temps.getEnchantability() == material.getEnchantability());
			if(flag)
				JLog.info("This ToolMaterial is already Added.");
			else
				JLog.warning("This name is already used by a different ToolMaterial.");
		}
		else
		{ // if not exist, then add
			listOfToolName.add(name);
			listOfToolMaterial.add(material);
		}

		return !alreadyExist;
	}
}
