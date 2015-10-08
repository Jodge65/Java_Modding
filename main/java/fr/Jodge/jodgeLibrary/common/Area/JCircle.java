package fr.Jodge.jodgeLibrary.common.Area;

import java.util.ArrayList;
import java.util.List;

import fr.Jodge.jodgeLibrary.common.function.JLog;

public class JCircle extends JSchema
{
	public List<List<Boolean>> circle;
	public int rayon;
	// private static List<JCircle> allCircle = new ArrayList<JCircle>();
	public static final int LIMITE_RAYON_CIRCLE_GENERATE = 10;
	public static final List<JCircle> PRE_GENERATE_CIRCLE = iniPreGenerateCircle();

	public static List<JCircle> iniPreGenerateCircle()
	{
		List<JCircle> myCircle = new ArrayList<JCircle>();
		for (int i = 1; i <= LIMITE_RAYON_CIRCLE_GENERATE; i++)
		{
			PRE_GENERATE_CIRCLE.add(new JCircle(i));
		}
		JLog.write("### : " + PRE_GENERATE_CIRCLE + " circle has been generate");
		return myCircle;
	}

	public JCircle(int rayon)
	{
		this(rayon, rayon * 2 + 1);
	}

	public JCircle(int rayon, int gridSize)
	{
		this(rayon, gridSize, false);
	}

	public JCircle(int rayon, int gridSize, boolean forceGenerate)
	{
		circle = createCircle(rayon, gridSize, forceGenerate);
		this.rayon = rayon;
	}

	public List<List<Boolean>> getCircle()
	{
		return circle;
	}

	public int getRayon()
	{
		return rayon;
	}

	public static List<List<Boolean>> createCircle(int rayon)
	{
		return createCircle(rayon, rayon * 2 + 1);
	}

	public static List<List<Boolean>> createCircle(int rayon, int gridSize)
	{
		return createCircle(rayon, gridSize, false);
	}

	public static List<List<Boolean>> createCircle(int rayon, int gridSize, boolean forceGenerate)
	{

		List<List<Boolean>> areaBool = new ArrayList<List<Boolean>>(); // Tab of boolean
		int actualGridSize = rayon * 2 + 1;
		for (int i = 0; i < actualGridSize; i++)
		{
			areaBool.add(new ArrayList<Boolean>());
		}

		int circleToGenerateBasedOnRayon = rayon;

		if (forceGenerate)
		{
			circleToGenerateBasedOnRayon = -1;
		}

		switch (circleToGenerateBasedOnRayon)
		{
		/*
		 * case 1:
		 * areaBool.get( 0).addAll(line(1, 1, 1));
		 * areaBool.get( 1).addAll(line(1, 1, 1));
		 * areaBool.get( 2).addAll(line(1, 1, 1));
		 * break;
		 * 
		 * case 2:
		 * areaBool.get( 0).addAll(line(0, 1, 1, 1, 0));
		 * areaBool.get( 1).addAll(line(1, 1, 1, 1, 1));
		 * areaBool.get( 2).addAll(line(1, 1, 1, 1, 1));
		 * areaBool.get( 3).addAll(line(1, 1, 1, 1, 1));
		 * areaBool.get( 4).addAll(line(0, 1, 1, 1, 0));
		 * break;
		 * 
		 * case 3:
		 * areaBool.get( 0).addAll(line(0, 0, 1, 1, 1, 0, 0));
		 * areaBool.get( 1).addAll(line(0, 1, 1, 1, 1, 1, 0));
		 * areaBool.get( 2).addAll(line(1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get( 3).addAll(line(1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get( 4).addAll(line(1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get( 5).addAll(line(0, 1, 1, 1, 1, 1, 0));
		 * areaBool.get( 6).addAll(line(0, 0, 1, 1, 1, 0, 0));
		 * break;
		 * 
		 * case 4:
		 * areaBool.get( 0).addAll(line(0, 0, 0, 1, 1, 1, 0, 0, 0));
		 * areaBool.get( 1).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get( 2).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get( 3).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get( 4).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get( 5).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get( 6).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get( 7).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get( 8).addAll(line(0, 0, 0, 1, 1, 1, 0, 0, 0));
		 * break;
		 * 
		 * case 5:
		 * areaBool.get( 0).addAll(line(0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0));
		 * areaBool.get( 1).addAll(line(0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0));
		 * areaBool.get( 2).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get( 3).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get( 4).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get( 5).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get( 6).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get( 7).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get( 8).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get( 9).addAll(line(0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0));
		 * areaBool.get(10).addAll(line(0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0));
		 * break;
		 * 
		 * case 6:
		 * areaBool.get( 0).addAll(line(0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0));
		 * areaBool.get( 1).addAll(line(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0));
		 * areaBool.get( 2).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get( 3).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get( 4).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get( 5).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get( 6).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get( 7).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get( 8).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get( 9).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get(10).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get(11).addAll(line(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0));
		 * areaBool.get(12).addAll(line(0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0));
		 * break;
		 * 
		 * case 7:
		 * areaBool.get( 0).addAll(line(0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0));
		 * areaBool.get( 1).addAll(line(0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0));
		 * areaBool.get( 2).addAll(line(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0));
		 * areaBool.get( 3).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get( 4).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get( 5).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get( 6).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get( 7).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get( 8).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get( 9).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get(10).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get(11).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get(12).addAll(line(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0));
		 * areaBool.get(13).addAll(line(0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0));
		 * areaBool.get(14).addAll(line(0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0));
		 * break;
		 * 
		 * case 8:
		 * areaBool.get( 0).addAll(line(0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0));
		 * areaBool.get( 1).addAll(line(0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0));
		 * areaBool.get( 2).addAll(line(0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0));
		 * areaBool.get( 3).addAll(line(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0));
		 * areaBool.get( 4).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get( 5).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get( 6).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get( 7).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get( 8).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get( 9).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get(10).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get(11).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get(12).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get(13).addAll(line(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0));
		 * areaBool.get(14).addAll(line(0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0));
		 * areaBool.get(15).addAll(line(0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0));
		 * areaBool.get(16).addAll(line(0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0));
		 * break;
		 * 
		 * case 9:
		 * areaBool.get( 0).addAll(line(0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0));
		 * areaBool.get( 1).addAll(line(0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0));
		 * areaBool.get( 2).addAll(line(0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0));
		 * areaBool.get( 3).addAll(line(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0));
		 * areaBool.get( 4).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get( 5).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get( 6).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get( 7).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get( 8).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get( 9).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get(10).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get(11).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get(12).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get(13).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get(14).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get(15).addAll(line(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0));
		 * areaBool.get(16).addAll(line(0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0));
		 * areaBool.get(17).addAll(line(0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0));
		 * areaBool.get(18).addAll(line(0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0));
		 * break;
		 * 
		 * case 10:
		 * areaBool.get( 0).addAll(line(0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0));
		 * areaBool.get( 1).addAll(line(0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0));
		 * areaBool.get( 2).addAll(line(0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0));
		 * areaBool.get( 3).addAll(line(0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0));
		 * areaBool.get( 4).addAll(line(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0));
		 * areaBool.get( 5).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get( 6).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get( 7).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get( 8).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get( 9).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get(10).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get(11).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get(12).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get(13).addAll(line(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1));
		 * areaBool.get(14).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get(15).addAll(line(0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0));
		 * areaBool.get(16).addAll(line(0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0));
		 * areaBool.get(17).addAll(line(0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0));
		 * areaBool.get(18).addAll(line(0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0));
		 * areaBool.get(19).addAll(line(0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0));
		 * areaBool.get(20).addAll(line(0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0));
		 * break;
		 */

			default:
				if (rayon != 0)
				{
					if (rayon < LIMITE_RAYON_CIRCLE_GENERATE)
					{
						areaBool = PRE_GENERATE_CIRCLE.get(rayon).circle;
					}
					areaBool = generateCircle(rayon);

				}
				else
				{
					areaBool.get(0).add(false);
				}

				break;
		} // end of switch

		return adaptOnGrid(areaBool, gridSize, actualGridSize);
	}

	private static List<List<Boolean>> generateCircle(int rayon)
	{
		List<List<Boolean>> areaBool = new ArrayList<List<Boolean>>(); // Tab of boolean

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

		return areaBool;
	}

}
