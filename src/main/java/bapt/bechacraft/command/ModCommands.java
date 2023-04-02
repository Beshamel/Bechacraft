package bapt.bechacraft.command;


import bapt.bechacraft.Bechacraft;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class ModCommands {

    public static final VocationCommand VOCATION = new VocationCommand();
    public static final VocationPickCommand VOCATION_PICK = new VocationPickCommand();
    public static final VocationResetCommand VOCATION_RESET = new VocationResetCommand();

    public static void registerCommands() {

        CommandRegistrationCallback.EVENT.register(VocationCommand::register);
        CommandRegistrationCallback.EVENT.register(VocationPickCommand::register);
        CommandRegistrationCallback.EVENT.register(VocationResetCommand::register);

        Bechacraft.LOGGER.info("Commands have been initialized");
    }
}
