package com.feed_the_beast.mods.ftbteams.api.property;

import com.feed_the_beast.mods.ftbteams.api.TeamProperty;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import java.util.Optional;

/**
 * @author LatvianModder
 */
public class DoubleProperty extends TeamProperty<Double>
{
	public final double minValue;
	public final double maxValue;

	public DoubleProperty(ResourceLocation id, double def, double min, double max)
	{
		super(id, def);
		minValue = min;
		maxValue = max;
	}

	public DoubleProperty(ResourceLocation id, double def)
	{
		this(id, def, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
	}

	@Override
	public Optional<Double> fromString(String string)
	{
		try
		{
			double num = Double.parseDouble(string);
			return Optional.of(MathHelper.clamp(num, minValue, maxValue));
		}
		catch (Exception ex)
		{
			return Optional.empty();
		}
	}
}