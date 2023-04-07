package bapt.bechacraft.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import bapt.bechacraft.command.argument.VocationArgumentType;
import bapt.bechacraft.vocation.Vocation;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.CommandManager.RegistrationEnvironment;
import net.minecraft.text.Text;

public class VocationPickCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, RegistrationEnvironment environment) {
        /*for(Vocation vocation : Vocations.all()) {
            dispatcher.register(CommandManager.literal("vocation").then(CommandManager.literal("pick").then(CommandManager.literal(vocation.getName()).executes(context -> execute(context, vocation)))));
        }*/
        dispatcher.register(CommandManager.literal("vocation").then(CommandManager.literal("pick").then(CommandManager.argument("vocation", VocationArgumentType.vocation()).executes(context -> execute(context)))));
    }

    private static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        
        Entity entity = context.getSource().getEntity();

        if(entity == null || !(entity instanceof PlayerEntity))
            return 0;
        
        PlayerEntity player = (PlayerEntity) entity;

        Vocation newVocation = context.getArgument("vocation", Vocation.class);
        Vocation vocation = Vocation.get(player);

        if(newVocation != vocation && newVocation.unlocked(player)) {
            Vocation.set(player, newVocation);
            return 1;
        } else
            player.sendMessage(Text.translatable("msg.bechacraft.change_vocation_fail"));
            
        return 0;
    }
    
}