package fr.Jodge.jodgeLibrary.common.Area;

import java.util.ArrayList;
import java.util.List;

import fr.Jodge.jodgeLibrary.common.function.JLog;

public class JDome extends JSchema
{
	public List<List<List<Boolean>>> dome;
	public int rayon;

	public JDome(int rayon)
	{
		this(rayon, rayon * 2 + 1);
	}
	public JDome(int rayon, int gridSize)
	{
		this(rayon, gridSize, false);
	}	
	public JDome(int rayon, int gridSize, boolean forceGenerate)
	{
		dome = createDome(rayon, gridSize, forceGenerate);
		this.rayon = rayon;
	}

	public JDome setExtrudeDome()
	{
		return setExtrudeDome(1);
	}
	
	public JDome setExtrudeDome(int wallLenght)
	{
		dome = extrudeDome(dome, rayon, wallLenght);;
		return this;
	}
	
	public List<List<List<Boolean>>> getDome()
	{
		return dome;
	}
	public int getRayon()
	{
		return rayon;
	}
	
	public static List<List<List<Boolean>>> extrudeDome(List<List<List<Boolean>>> domeReceive, int rayon)
	{
		return extrudeDome(domeReceive, rayon, 1);
	}
	
	public static List<List<List<Boolean>>> extrudeDome(List<List<List<Boolean>>> domeReceive, int rayon, int wallLenght)
	{
		List<List<List<Boolean>>> intDome = createDome( (rayon - wallLenght) , rayon*2+1);
	
		int limY = domeReceive.size() - 1;
		
		int limX = domeReceive.get(0).get(0).size();
		int limZ = limX;

		boolean dV;
		boolean eDV;
		
		List<List<List<Boolean>>> finalDome = new ArrayList<List<List<Boolean>>>();


		for(int y = 0; y < limY; y++)
		{
			finalDome.add(new ArrayList<List<Boolean>>());
			for(int x = 0; x < limX; x++)
			{
				finalDome.get(y).add(new ArrayList<Boolean>());
				for(int z = 0; z < limZ; z++)
				{
					dV = domeReceive.get(y).get(x).get(z);
					eDV = intDome.get(y).get(x).get(z);
					finalDome.get(y).get(x).add(!(dV == eDV) );
					
					JLog.write("#BOOL# x=" + x + ", z=" + z + ", y=" + y + "dV : " + dV + ", eDV : " + eDV + ", !(dV == eDV) : " + !(dV == eDV) + ", finaly : " + domeReceive.get(y).get(x).get(z));

				}
			}
		}
		
		return finalDome;
	}
	
	public static List<List<List<Boolean>>> createDome(int rayon)
	{
		return createDome(rayon, ((rayon*2)+1));
	}
	public static List<List<List<Boolean>>> createDome(int rayon, int grid)
	{
		return createDome(rayon, grid, false);
	}
	public static List<List<List<Boolean>>> createDome(int rayon, int grid , boolean forceGenerate)
	{

		List<List<List<Boolean>>> areaBool = new ArrayList<List<List<Boolean>>>(); // Tab of boolean
		if(rayon > JCircle.LIMITE_RAYON_CIRCLE_GENERATE && !forceGenerate)
		{
			forceGenerate = true;
		}

		int heigth = (grid - 1) / 2 + 1;
		int domeSize = rayon * 2 + 1;
		for (int y = 0; y <= heigth; y++)
		{
			areaBool.add(new ArrayList<List<Boolean>>());
		}
		
		int actualRayon;
		int oldRayon = -1;
		List<List<Boolean>> domeFace = new ArrayList<List<Boolean>>();
		domeFace.addAll(JCircle.createCircle(rayon, grid));
		
		List<List<Boolean>> domeStep = new ArrayList<List<Boolean>>();

		int numberOfAdd = heigth - (rayon + 1);
		
		for(int y = heigth; y > 0; y--)
		{
			actualRayon = 0;

			if( y != heigth)
			{
				for(int x = 0; x < grid; x++)
				{
					if(domeFace.get(y-1).get(x))
					{
						actualRayon++;
					}
				}
				actualRayon--;
				actualRayon /= 2;
				if(oldRayon != actualRayon)
				{
					domeStep = JCircle.createCircle(actualRayon, grid, forceGenerate);
				}
			}
			else
			{
				domeStep = domeFace;
			}

			areaBool.get(heigth - y).addAll(domeStep);

			oldRayon = actualRayon;

		} // for y = rayon
		
		return areaBool;
	} 
	
}
