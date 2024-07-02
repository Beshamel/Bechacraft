package bapt.bechacraft;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bapt.bechacraft.block.ModBlocks;
import bapt.bechacraft.block.entity.ModBlockEntities;
import bapt.bechacraft.command.ModCommands;
import bapt.bechacraft.effect.ModStatusEffects;
import bapt.bechacraft.entity.ModEntityRenderers;
import bapt.bechacraft.entity.ModEntityTypes;
import bapt.bechacraft.item.ModItemGroup;
import bapt.bechacraft.item.ModItems;
import bapt.bechacraft.networking.ModMessages;
import bapt.bechacraft.recipe.ModRecipes;
import bapt.bechacraft.screen.ModScreenHandlers;
import bapt.bechacraft.sound.ModSounds;
import bapt.bechacraft.vocation.Vocations;

public class Bechacraft implements ModInitializer {

	public static final String MOD_ID = "bechacraft";
	public static final String MOD_NAME = "Béchacraft";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItemGroup.registerItemGroups();
		ModItems.registerModItems();
		Vocations.registerVocations();
		ModBlocks.registerModBlocks();
		ModBlockEntities.registerBlockEntities();
		ModStatusEffects.registerEffects();
		ModScreenHandlers.registerScreenHandlers();
		ModRecipes.registerRecipes();
		ModCommands.registerCommands();
		ModMessages.registerC2SPackets();
		ModSounds.registerSoundEvents();
		ModEntityTypes.registerEntityTypes();
		ModEntityRenderers.registerRenderers();

		LOGGER.info(MOD_NAME + " has initialized succesfully !");
	}
}
