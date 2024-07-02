package bapt.bechacraft.entity;

import bapt.bechacraft.item.ModItems;
import bapt.bechacraft.sound.ModSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

public class ShurikenEntity extends PersistentProjectileEntity {

    public ShurikenEntity(EntityType<? extends ShurikenEntity> entityType, World world) {
        super(entityType, world);
    }

    public ShurikenEntity(World world, double x, double y, double z) {
        super(ModEntityTypes.SHURIKEN, x, y, z, world);
    }

    public ShurikenEntity(World world, LivingEntity owner) {
        super(ModEntityTypes.SHURIKEN, owner, world);
    }

    @Override
    protected ItemStack asItemStack() {
        return new ItemStack(ModItems.SHURIKEN);
    }

    @Override
    protected SoundEvent getHitSound() {
        return ModSounds.ENTITY_SHURIKEN_HIT;
    }
}
