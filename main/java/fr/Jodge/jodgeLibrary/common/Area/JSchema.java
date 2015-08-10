package fr.Jodge.jodgeLibrary.common.Area;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.Jodge.jodgeLibrary.common.Main;
import fr.Jodge.jodgeLibrary.common.function.JLog;

public class JSchema
{
	
	public static List<Boolean> line(int... value)
	{
		List<Boolean> line = new ArrayList<Boolean>();

		for (int i : value)
		{
			if (i == 0)
			{
				line.add(false);
			}
			else
			{
				line.add(true);
			}
		}

		return line;
	}

	public static List<List<Boolean>> emptyTab(int size)
	{
		List<List<Boolean>> areaBool = new ArrayList<List<Boolean>>(); // Tab of boolean
		for(int x = 0; x < size; x++)
		{
			areaBool.add(new ArrayList<Boolean>());
			for(int z = 0; z < size; z++)
			{
				areaBool.get(x).add(false);
			}
		}
		return areaBool;
	}
	
	public static List<List<Boolean>> adaptOnGrid(List<List<Boolean>> grid, int newSize, int oldSize)
	{
		List<List<Boolean>> areaBool = new ArrayList<List<Boolean>>(); // Tab of boolean
		
		if (oldSize > newSize)
		{
			areaBool = grid;
			JLog.write("[WARNING] The tab doens't need to be adapte. Actual size : " + oldSize + ", New Size : " + newSize + ".");
		}
		else if (oldSize == newSize)
		{
			areaBool = grid;
		}
		else 
		{
			int numberOfAdd = newSize - oldSize;
			if (numberOfAdd % 2 != 0)
			{
				numberOfAdd++;
				JLog.write("[WARNING] You try to adapt a " + oldSize + "x" + oldSize + "grid to a " + newSize + "x" + newSize + " the form will be no longue center !");
			}
			numberOfAdd /= 2;

			List<Boolean> emptyLine = new ArrayList<Boolean>();
			for (int i = 0; i < newSize; i++)
			{
				emptyLine.add(false);
			}

			for (int x = 0; x < newSize; x++)
			{
				areaBool.add(new ArrayList<Boolean>());

				for (int z = 0; z < newSize; z++)
				{

					if (x < numberOfAdd || x >= newSize - numberOfAdd)
					{
						areaBool.get(x).add(false);
					}
					else
					{
						if (z < numberOfAdd || z >= newSize - numberOfAdd)
						{
							areaBool.get(x).add(false);
						}
						else
						{
							areaBool.get(x).add(grid.get(x - numberOfAdd).get(z - numberOfAdd));
						}
					}
				} // end of for z
			} // end of for x

		}

		return areaBool;
	}

	


}
