package bapt.bechacraft.vocation;

import java.util.Collection;
import java.util.HashMap;

import com.google.common.collect.Maps;

import bapt.bechacraft.Bechacraft;
import bapt.bechacraft.vocation.Vocation.VocationBuilder;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.util.Identifier;

public class Vocations {

    private static HashMap<Identifier, Vocation> entries = Maps.newHashMap();
    
    public static final Vocation NONE = register(new Vocation("none", null, 0, 0, true, Maps.newHashMap()));

    public static final Vocation CULTIST = register(new VocationBuilder("cultist", NONE).build());

    public static final Vocation FARMER = register(new VocationBuilder("farmer", NONE).shift(-50, -50).build());
    public static final Vocation BREEDER = register(new VocationBuilder("breeder", FARMER).shift(-50, -60).build());
    public static final Vocation FISHER = register(new VocationBuilder("fisher", FARMER).shift(-10, -60).build());
    public static final Vocation LUMBERJACK = register(new VocationBuilder("lumberjack", FARMER).shift(-80, -30).build());
    public static final Vocation TILLER = register(new VocationBuilder("tiller", FARMER).shift(-80, 0).build());
    public static final Vocation MILLER = register(new VocationBuilder("miller", FARMER).shift(30, -60).build());
    public static final Vocation SHEPHERD = register(new VocationBuilder("shepherd", BREEDER).shift(-50, -20).build());
    public static final Vocation RANCHER = register(new VocationBuilder("rancher", BREEDER).shift(0, -40).build());
    public static final Vocation ANGLER = register(new VocationBuilder("angler", FISHER).shift(0, -40).build());
    public static final Vocation BOTANIST = register(new VocationBuilder("botanist", TILLER).shift(-50, 0).build());
    public static final Vocation GATHERER = register(new VocationBuilder("gatherer", LUMBERJACK).shift(-50, 0).build());
    public static final Vocation BAKER = register(new VocationBuilder("baker", MILLER).shift(0, -40).build());

    public static final Vocation MINER = register(new VocationBuilder("miner", NONE).shift(0, 70).build());
    public static final Vocation GEOLOGIST = register(new VocationBuilder("geologist", MINER).shift(-40, 40).build());
    public static final Vocation BLACKSMITH = register(new VocationBuilder("blacksmith", MINER).shift(20, 40).build());
    public static final Vocation CRYSTALLOGRAPHER = register(new VocationBuilder("crystallographer", GEOLOGIST).shift(0, 40).build());
    public static final Vocation WEAPONSMITH = register(new VocationBuilder("weaponsmith", BLACKSMITH).shift(20, 40).build());
    public static final Vocation ARMORER = register(new VocationBuilder("armorer", BLACKSMITH).shift(-20, 40).build());

    public static final Vocation FIGHTER = register(new VocationBuilder("fighter", NONE).shift(-70, 20).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "fighter_attack_modifier", 1, Operation.ADDITION).build());
    public static final Vocation MARKSMAN = register(new VocationBuilder("marksman", FIGHTER).shift(-80, -15).build());
    public static final Vocation WARRIOR = register(new VocationBuilder("warrior", FIGHTER).shift(-80, 30).build());
    public static final Vocation ASSASSIN = register(new VocationBuilder("assassin", FIGHTER).shift(-50, 70).build());
    public static final Vocation BRUTE = register(new VocationBuilder("brute", WARRIOR).shift(-50, 0).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "brute_attack_modifier", 1, Operation.MULTIPLY_TOTAL).inheritsModifiers(false).build());
    public static final Vocation HUNTER = register(new VocationBuilder("hunter", MARKSMAN).shift(-50, -15).build());
    public static final Vocation SNIPER = register(new VocationBuilder("sniper", MARKSMAN).shift(-50, 15).build());
    public static final Vocation LURKER = register(new VocationBuilder("lurker", ASSASSIN).shift(-50, 0).build());
    public static final Vocation NINJA = register(new VocationBuilder("ninja", ASSASSIN).shift(-20, 40).build());
    public static final Vocation BOUNTY_HUNTER = register(new VocationBuilder("bounty_hunter", ASSASSIN).shift(20, 40).build());

    public static final Vocation TRAVELER = register(new VocationBuilder("traveler", NONE).shift(50, -50).addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "traveler_speed_modifier", 0.1, Operation.MULTIPLY_TOTAL).build());
    public static final Vocation EXPLORATOR = register(new VocationBuilder("explorator", TRAVELER).shift(0, -60).build());
    public static final Vocation MERCHANT = register(new VocationBuilder("merchant", TRAVELER).shift(40, -60).build());
    public static final Vocation SAILOR = register(new VocationBuilder("sailor", TRAVELER).shift(80, -60).build());
    public static final Vocation ATLANT = register(new VocationBuilder("atlant", SAILOR).shift(0, -40).build());
    public static final Vocation TRADER = register(new VocationBuilder("trader", MERCHANT).shift(0, -40).build());
    public static final Vocation ADVENTURER = register(new VocationBuilder("adventurer", EXPLORATOR).shift(0, -40).build());

    public static final Vocation BUILDER = register(new VocationBuilder("builder", NONE)/*.shift(70, 20)*/.build());
    public static final Vocation ENGINEER = register(new VocationBuilder("engineer", BUILDER).build());
    public static final Vocation CRAFTMAN = register(new VocationBuilder("craftman", BUILDER).build());
    public static final Vocation INVENTOR = register(new VocationBuilder("inventor", ENGINEER).build());
    public static final Vocation ARTISAN = register(new VocationBuilder("artisan", CRAFTMAN).build());
    public static final Vocation MANUFACTURER = register(new VocationBuilder("manufacturer", CRAFTMAN).build());

    public static final Vocation MAGICIAN = register(new VocationBuilder("magician", NONE).visible(false).build());
    public static final Vocation WIZARD = register(new VocationBuilder("wizard", MAGICIAN).build());
    public static final Vocation CHARMER = register(new VocationBuilder("charmer", MAGICIAN).build());
    public static final Vocation ALCHEMIST = register(new VocationBuilder("alchemist", MAGICIAN).build());
    public static final Vocation MEDIC = register(new VocationBuilder("medic", WIZARD).build());
    public static final Vocation FAIRY = register(new VocationBuilder("fairy", CHARMER).build());
    
    private static Vocation register(Vocation vocation) {
        entries.put(vocation.getId(), vocation);
        return vocation;
    }

    public static void registerVocations() {
        Bechacraft.LOGGER.info("Loaded {} vocations", entries.size());
    }
    
    /*private static Vocation createAndRegisterVocation(String name, Vocation parent) {
        if(parent == null)
            return register(new Vocation(name, parent));
        
        Vocation vocation = new VocationBuilder(name, parent).build();
        return register(vocation);
    }*/

    public static Collection<Vocation> all() {
        return entries.values();
    }

    public static Vocation fromName(String name) {
        if(name == null) {
            Bechacraft.LOGGER.warn("null Vocation name querry in Vocations.fromName");
            return NONE;
        }
        Identifier id = new Identifier(Bechacraft.MOD_ID, name);
        if(entries.containsKey(id))
            return entries.get(id);
        else
            return NONE;
    }
}
