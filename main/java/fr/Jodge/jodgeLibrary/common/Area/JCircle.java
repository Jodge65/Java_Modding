package fr.Jodge.jodgeLibrary.common.Area;

import java.util.ArrayList;
import java.util.List;

import fr.Jodge.jodgeLibrary.common.function.JLog;

public class JCircle extends JSchema
{

	/*public final static List<List<Boolean>> circle_1  = circle(1 );
	public final static List<List<Boolean>> circle_2  = circle(2 );
	public final static List<List<Boolean>> circle_3  = circle(3 );
	public final static List<List<Boolean>> circle_4  = circle(4 );
	public final static List<List<Boolean>> circle_5  = circle(5 );
	public final static List<List<Boolean>> circle_6  = circle(6 );
	public final static List<List<Boolean>> circle_7  = circle(7 );
	public final static List<List<Boolean>> circle_8  = circle(8 );
	public final static List<List<Boolean>> circle_9  = circle(9 );
	public final static List<List<Boolean>> circle_10 = circle(10);*/
	
	public static List<List<Boolean>> circle(int rayon)
	{
		return circle(rayon, rayon * 2 + 1);
	}
	
	public static List<List<Boolean>> circle(int rayon, int gridSize)
	{
		return circle(rayon, gridSize, false);
	}
	public static List<List<Boolean>> circle(int rayon, int gridSize, boolean forceGenerate)
	{

		
		List<List<Boolean>> areaBool = new ArrayList<List<Boolean>>(); // Tab of boolean
		int actualGridSize = rayon * 2 + 1;
		for (int i = 0; i < actualGridSize; i++)
		{
			areaBool.add(new ArrayList<Boolean>());
		}

		int circleToGenerateBasedOnRayon = rayon;
		
		if(forceGenerate)
		{
			circleToGenerateBasedOnRayon = -1; 
		}
		
		switch (circleToGenerateBasedOnRayon)
		{
			/*case 1:
				areaBool.get( 0).addAll(line(1, 1, 1));
				areaBool.get( 1).addAll(line(1, 1, 1));
				areaBool.get( 2).addAll(line(1, 1, 1));
				break;
				
			case 2:
				areaBool.get( 0).addAll(line(0, 1, 1, 1, 0));
				areaBool.get( 1).addAll(line(1, 1, 1, 1, 1));
				areaBool.get( 2).addAll(line(1, 1, 1, 1, 1));
				areaBool.get( 3).addAll(line(1, 1, 1, 1, 1));
				areaBool.get( 4).addAll(line(0, 1, 1, 1, 0));
				break;
				
			case 3:
				areaBool.get( 0).addAll(line(0, 0, 1, 1, 1, 0, 0));
				areaBool.get( 1).addAll(line(0, 1, 1, 1, 1, 1, 0));
				areaBool.get( 2).addAll(line(1, 1, 1, 1, 1, 1, 1));
				areaBool.get( 3).addAll(line(1, 1, 1, 1, 1, 1, 1));
				areaBool.get( 4).addAll(line(1, 1, 1, 1, 1, 1, 1));
				areaBool.get( 5).addAll(line(0, 1, 1, 1, 1, 1, 0));
				areaBool.get( 6).addAll(line(0, 0, 1, 1, 1, 0, 0));
				break;

			case 4:
				areaBool.get( 0).addAll(line(0, 0, 0, 1, 1, 1, 0, 0, 0));
				areaBool.get( 1).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get( 2).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get( 3).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get( 4).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get( 5).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get( 6).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get( 7).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get( 8).addAll(line(0, 0, 0, 1, 1, 1, 0, 0, 0));
				break;

			case 5:
				areaBool.get( 0).addAll(line(0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0));
				areaBool.get( 1).addAll(line(0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0));
				areaBool.get( 2).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get( 3).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get( 4).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get( 5).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get( 6).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get( 7).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get( 8).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get( 9).addAll(line(0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0));
				areaBool.get(10).addAll(line(0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0));
				break;
				
			case 6:
				areaBool.get( 0).addAll(line(0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0));
				areaBool.get( 1).addAll(line(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0));
				areaBool.get( 2).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get( 3).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get( 4).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get( 5).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get( 6).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get( 7).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get( 8).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get( 9).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get(10).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get(11).addAll(line(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0));
				areaBool.get(12).addAll(line(0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0));
				break;
				
			case 7:
				areaBool.get( 0).addAll(line(0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0));
				areaBool.get( 1).addAll(line(0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0));
				areaBool.get( 2).addAll(line(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0));
				areaBool.get( 3).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get( 4).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get( 5).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get( 6).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get( 7).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get( 8).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get( 9).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get(10).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get(11).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get(12).addAll(line(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0));
				areaBool.get(13).addAll(line(0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0));
				areaBool.get(14).addAll(line(0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0));
				break;

			case 8:
				areaBool.get( 0).addAll(line(0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0));
				areaBool.get( 1).addAll(line(0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0));
				areaBool.get( 2).addAll(line(0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0));
				areaBool.get( 3).addAll(line(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0));
				areaBool.get( 4).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get( 5).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get( 6).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get( 7).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get( 8).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get( 9).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get(10).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get(11).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get(12).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get(13).addAll(line(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0));
				areaBool.get(14).addAll(line(0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0));
				areaBool.get(15).addAll(line(0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0));
				areaBool.get(16).addAll(line(0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0));
				break;

			case 9:
				areaBool.get( 0).addAll(line(0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0));
				areaBool.get( 1).addAll(line(0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0));
				areaBool.get( 2).addAll(line(0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0));
				areaBool.get( 3).addAll(line(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0));
				areaBool.get( 4).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get( 5).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get( 6).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get( 7).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get( 8).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get( 9).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get(10).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get(11).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get(12).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get(13).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get(14).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get(15).addAll(line(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0));
				areaBool.get(16).addAll(line(0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0));
				areaBool.get(17).addAll(line(0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0));
				areaBool.get(18).addAll(line(0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0));
				break;

			case 10:
				areaBool.get( 0).addAll(line(0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0));
				areaBool.get( 1).addAll(line(0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0));
				areaBool.get( 2).addAll(line(0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0));
				areaBool.get( 3).addAll(line(0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0));
				areaBool.get( 4).addAll(line(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0));
				areaBool.get( 5).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get( 6).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get( 7).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get( 8).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get( 9).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get(10).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get(11).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get(12).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get(13).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
				areaBool.get(14).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get(15).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
				areaBool.get(16).addAll(line(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0));
				areaBool.get(17).addAll(line(0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0));
				areaBool.get(18).addAll(line(0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0));
				areaBool.get(19).addAll(line(0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0));
				areaBool.get(20).addAll(line(0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0));
				break;*/

			default:
				if(rayon != 0)
				{
					for (int x = 0; x <= rayon * 2; ++x)
					{
						areaBool.add(new ArrayList<Boolean>());
						for (int z = 0; z <= rayon * 2; ++z)
						{
							if ((Math.pow(x - rayon, 2) + Math.pow(z - rayon, 2)) <= Math.pow(rayon + 0.1F, 2))
							{
								areaBool.get(x).add(true);
							}
							else
							{
								areaBool.get(x).add(false);
							}
						}
					}
				}
				else
				{
					areaBool.get( 0).add(false);
				}

				break;
		} // end of switch

		return adaptOnGrid(areaBool, gridSize, actualGridSize);
	}

}
