package com.feed_the_beast.mods.ftbteams.impl;

import com.feed_the_beast.mods.ftbteams.api.TeamProperty;
import com.feed_the_beast.mods.ftbteams.event.TeamConfigEvent;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.MinecraftForge;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author LatvianModder
 */
public class TeamPropertyArgument implements ArgumentType<TeamProperty>
{
	private static final SimpleCommandExceptionType PROPERTY_NOT_FOUND = new SimpleCommandExceptionType(new TranslationTextComponent("ftbteams.property_not_found"));

	@Override
	public TeamProperty parse(StringReader reader) throws CommandSyntaxException
	{
		ResourceLocation id = ResourceLocation.read(reader);
		Map<ResourceLocation, TeamProperty> map = new LinkedHashMap<>();
		MinecraftForge.EVENT_BUS.post(new TeamConfigEvent(property -> map.put(property.id, property)));
		TeamProperty property = map.get(id);

		if (property != null)
		{
			return property;
		}

		throw PROPERTY_NOT_FOUND.create();
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder)
	{
		try
		{
			Map<String, TeamProperty> map = new LinkedHashMap<>();
			MinecraftForge.EVENT_BUS.post(new TeamConfigEvent(property -> map.put(property.id.toString(), property)));
			return ISuggestionProvider.suggest(map.keySet(), builder);
		}
		catch (Exception ex)
		{
			return Suggestions.empty();
		}
	}
}