package bapt.bechacraft.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.EntityType.Builder;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEntityTypes {

    public static EntityType<ShurikenEntity> SHURIKEN;

    public static void registerEntityTypes() {
        Builder<ShurikenEntity> builder = Builder.create(ShurikenEntity::new, SpawnGroup.MISC);
        builder = builder.setDimensions(.3f, .3f).maxTrackingRange(4).trackingTickInterval(20);
        SHURIKEN = register("shuriken", builder);
    }

    private static <T extends Entity> EntityType<T> register(String id, Builder<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, id, type.build(id));
    }
}
