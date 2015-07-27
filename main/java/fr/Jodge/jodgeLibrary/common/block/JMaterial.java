package fr.Jodge.jodgeLibrary.common.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class JMaterial extends Material
{
	private boolean isTranslucent;

	public JMaterial(MapColor color)
	{
		super(color);
	}

	public static final Material ore = new JMaterial(MapColor.stoneColor).setRequiresTool();
	public static final Material cloud = ((JMaterial) new JMaterial(MapColor.quartzColor).setNoPushMobility()).setJTranslucent();

	public Material setJTranslucent()
	{
		this.isTranslucent = true;
		return this;
	}

	public boolean isOpaque()
	{
		return super.isOpaque();
	}
}
