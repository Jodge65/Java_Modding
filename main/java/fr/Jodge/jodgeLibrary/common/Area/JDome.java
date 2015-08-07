package fr.Jodge.jodgeLibrary.common.Area;

import java.util.ArrayList;
import java.util.List;

import fr.Jodge.jodgeLibrary.common.function.JLog;

public class JDome extends JCircle
{
	public static List<List<List<Boolean>>> extrudeDome(List<List<List<Boolean>>> domeReceive, int rayon)
	{
		return extrudeDome(domeReceive, rayon, 1);
	}
	
	public static List<List<List<Boolean>>> extrudeDome(List<List<List<Boolean>>> domeReceive, int rayon, int wallLenght)
	{
		List<List<List<Boolean>>> intDome = dome( (rayon - wallLenght) , rayon*2+1);
		

		
		int limY = domeReceive.size() - 1;
		
		int limX = domeReceive.get(0).get(0).size();
		int limZ = limX;
		
		JLog.write("### limY : " + limY);
		JLog.write("### limX : " + limX);
		JLog.write("### limZ : " + limZ);
		
		boolean dV;
		boolean eDV;
		
		
		for (int y = 0; y < limY; y++)
		{
			for (int x = 0; x < limX; x++)
			{ // je lis X
				JLog.write("#LINE# x :" + x + "/" + limX + ", y : " + y + "/" + limY);
				JLog.write("#VALUE# " + domeReceive.get(y).get(x));
			}
			JLog.write("### --- ###");
		}
		JLog.write("### ---------------------- ###");
		for (int y = 0; y < limY; y++)
		{
			for (int x = 0; x < limX; x++)
			{ // je lis X
				JLog.write("#LINE# x :" + x + "/" + limX + ", y : " + y + "/" + limY);
				JLog.write("#VALUE# " + intDome.get(y).get(x));
			}
			JLog.write("### --- ###");
		}
		JLog.write("### ---------------------- ###");
		int intLimY = intDome.size();
		int intLimX = intDome.get(0).size();
		int intLimZ = intDome.get(0).get(0).size();
		JLog.write("### intLimY :" + intLimY);
		JLog.write("### intLimX :" + intLimX);
		JLog.write("### intLimZ :" + intLimZ);
		JLog.write("### ---------------------- ###");

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
					
					//JLog.write("#BOOL# x=" + x + ", z=" + z + ", y=" + y + "dV : " + dV + ", eDV : " + eDV + ", !(dV == eDV) : " + !(dV == eDV) + ", finaly : " + domeReceive.get(y).get(x).get(z));

				}
			}
		}
		
		for (int y = 0; y < limY; y++)
		{
			for (int x = 0; x < limX; x++)
			{ // je lis X
				//JLog.write("#LINE# x :" + x + "/" + limX + ", y : " + y + "/" + limY);
				JLog.write("#VALUE# " + domeReceive.get(y).get(x));
			}
			JLog.write("### --- ###");

		}
		
		return finalDome;
	}
	
	public static List<List<List<Boolean>>> dome(int rayon)
	{
		return dome(rayon, ((rayon*2)+1));
	}
	public static List<List<List<Boolean>>> dome(int rayon, int grid)
	{
		return dome(rayon, grid, false);
	}
	public static List<List<List<Boolean>>> dome(int rayon, int grid , boolean forceGenerate)
	{

		List<List<List<Boolean>>> areaBool = new ArrayList<List<List<Boolean>>>(); // Tab of boolean
		if(rayon > JSchema.LIMITE_RAYON_CIRCLE && !forceGenerate)
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
		domeFace.addAll(circle(rayon, grid));
		
		List<List<Boolean>> domeStep = new ArrayList<List<Boolean>>();

		int numberOfAdd = heigth - (rayon + 1);
		
		for(int y = heigth; y > 0; y--)
		{
			actualRayon = 0;
			/*if(y < numberOfAdd)
			{
				domeStep = JSchema.emptyTab(grid);
			}*/
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
					domeStep = JCircle.circle(actualRayon, grid, forceGenerate);
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
