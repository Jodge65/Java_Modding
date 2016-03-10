package fr.Jodge.jodgeLibrary.common.skills;

public class BaseSkills
{
	
	
	/**
	 * give the amount of damage deal by the skills (often 0 if defensive skills)
	 * @return (float)
	 */
	public float damageDeal()
	{
		return 0.0F;
	}
	
	/**
	 * give the amount of damage reduce by the skills (often 0 if offensive skills)
	 * @return
	 */
	public float damageReduce()
	{
		return 0.0F;
	}
}
