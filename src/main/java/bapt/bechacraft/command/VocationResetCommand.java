package bapt.bechacraft.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import bapt.bechacraft.Bechacraft;
import bapt.bechacraft.vocation.Vocation;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.CommandManager.RegistrationEnvironment;
import net.minecraft.server.network.ServerPlayerEntity;

public class VocationResetCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("vocation").then(CommandManager.literal("reset").executes(context -> execute(context))));
    }

    private static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        
        Entity entity = context.getSource().getEntity();

        if(entity == null || !(entity instanceof ServerPlayerEntity))
            return 0;
        
        ServerPlayerEntity player = (ServerPlayerEntity) entity;

        Vocation.reset(player);

        Bechacraft.LOGGER.info("Vocation reset euccessfuly !");

        return 1;
    }
    
}