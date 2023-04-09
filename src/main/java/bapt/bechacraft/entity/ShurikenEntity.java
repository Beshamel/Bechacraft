package bapt.bechacraft.entity;

import bapt.bechacraft.item.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ShurikenEntity extends PersistentProjectileEntity {

    public ShurikenEntity(EntityType<? extends ShurikenEntity> entityType, World world) {
        super((EntityType<? extends ShurikenEntity>)entityType, world);
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
}
