package fr.Jodge.jodgeLibrary.common;


/**
 * 
 * @author Jodge
 *
 */
public interface JCommonCreate
{
	/**
	 * set the oreDictionnaryName of the object
	 * @param oreDictionaryName (String) see Wiki for conventional name of oreDictionnary
	 */
	public void setOreDictionaryName(String oreDictionaryName);
	
	/**
	 * return the oreDictionnaryName (if no name was define, then a conventional will be generate
	 * @return (string)
	 */
	public String getOreDictionaryName();
	
}
