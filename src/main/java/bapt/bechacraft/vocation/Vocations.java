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
    
    public static final Vocation NONE = createAndRegisterVocation("none", null);
    public static final Vocation FARMER = createAndRegisterVocation("farmer", NONE);
    public static final Vocation MINER = createAndRegisterVocation("miner", NONE);
    public static final Vocation TRAVELER = register(new VocationBuilder("traveler", NONE).addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "traveler_speed_modifier", 0.1, Operation.MULTIPLY_TOTAL).build());
    public static final Vocation BUILDER = createAndRegisterVocation("builder", NONE);
    public static final Vocation FIGHTER = register(new VocationBuilder("fighter", NONE).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "fighter_attack_modifier", 1, Operation.ADDITION).build());
    public static final Vocation MAGICIAN = register(new VocationBuilder("magician", NONE).implemented(false).build());
    public static final Vocation CULTIST = register(new VocationBuilder("cultist", NONE).implemented(false).build());
    public static final Vocation BREEDER = createAndRegisterVocation("breeder", FARMER);
    public static final Vocation FISHER = createAndRegisterVocation("fisher", FARMER);
    public static final Vocation LUMBERJACK = createAndRegisterVocation("lumberjack", FARMER);
    public static final Vocation TILLER = createAndRegisterVocation("tiller", FARMER);
    public static final Vocation GEOLOGIST = createAndRegisterVocation("geologist", MINER);
    public static final Vocation BLACKSMITH = createAndRegisterVocation("blacksmith", MINER);
    public static final Vocation EXPLORATOR = register(new VocationBuilder("explorator", TRAVELER).build());
    public static final Vocation MERCHANT = register(new VocationBuilder("merchant", TRAVELER).build());
    public static final Vocation ADVENTURER = register(new VocationBuilder("adventurer", TRAVELER).build());
    public static final Vocation SAILOR = createAndRegisterVocation("sailor", TRAVELER);
    public static final Vocation ENGINEER = createAndRegisterVocation("engineer", BUILDER);
    public static final Vocation CRAFTMAN = createAndRegisterVocation("craftman", BUILDER);
    public static final Vocation MARKSMAN = createAndRegisterVocation("marksman", FIGHTER);
    public static final Vocation WARRIOR = createAndRegisterVocation("warrior", FIGHTER);
    public static final Vocation HUNTER = createAndRegisterVocation("hunter", FIGHTER);
    public static final Vocation BRUTE = register(new VocationBuilder("brute", WARRIOR).addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, "brute_attack_modifier", 1, Operation.MULTIPLY_TOTAL).inheritsModifiers(false).build());
    public static final Vocation ASSASSIN = createAndRegisterVocation("assassin", FIGHTER);
    public static final Vocation WIZARD = createAndRegisterVocation("wizard", MAGICIAN);
    public static final Vocation CHARMER = createAndRegisterVocation("charmer", MAGICIAN);
    public static final Vocation ALCHEMIST = createAndRegisterVocation("alchemist", MAGICIAN);
    public static final Vocation SHEPHERD = createAndRegisterVocation("shepherd", BREEDER);
    public static final Vocation ANGLER = createAndRegisterVocation("angler", FISHER);
    public static final Vocation BOTANIST = createAndRegisterVocation("botanist", TILLER);
    public static final Vocation GATHERER = createAndRegisterVocation("gatherer", LUMBERJACK);
    public static final Vocation CRYSTALLOGRAPHER = createAndRegisterVocation("crystallographer", GEOLOGIST);
    public static final Vocation WEAPONSMITH = createAndRegisterVocation("weaponmaster", BLACKSMITH);
    public static final Vocation ARMORER = createAndRegisterVocation("armorer", BLACKSMITH);
    public static final Vocation MEDIC = createAndRegisterVocation("medic", WIZARD);
    public static final Vocation ATLANT = createAndRegisterVocation("sailor", SAILOR);
    public static final Vocation FAIRY = createAndRegisterVocation("fairy", CHARMER);
    public static final Vocation TRADER = createAndRegisterVocation("trader", MERCHANT);
    
    private static Vocation register(Vocation vocation) {
        entries.put(vocation.getId(), vocation);
        return vocation;
    }

    public static void registerVocations() {
        Bechacraft.LOGGER.info("Loaded {} vocations", entries.size());
    }
    
    private static Vocation createAndRegisterVocation(String name, Vocation parent) {
        if(parent == null)
            return register(new Vocation(name, parent));
        
        Vocation vocation = new VocationBuilder(name, parent).build();
        return register(vocation);
    }

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
