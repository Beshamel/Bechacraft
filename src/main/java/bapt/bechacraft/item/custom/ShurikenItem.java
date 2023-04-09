package bapt.bechacraft.item.custom;

import bapt.bechacraft.entity.ShurikenEntity;
import bapt.bechacraft.vocation.Vocation;
import bapt.bechacraft.vocation.Vocations;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ShurikenItem extends Item {

    public ShurikenItem(Settings settings) {
        super(settings);
    }

    public PersistentProjectileEntity createShuriken(World world, ItemStack stack, LivingEntity shooter) {
        ShurikenEntity shurikenEntity = new ShurikenEntity(world, shooter);
        return shurikenEntity;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        //world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.SHURIKEN_SHIT, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        boolean amplify = Vocation.get(user).inherits(Vocations.NINJA);
        float speed = amplify ? 5f : 3f;
        int damage = amplify ? 2 : 1;
        int cooldown = amplify ? 4 : 40;
        user.getItemCooldownManager().set(this, cooldown);
        if (!world.isClient) {
            ShurikenEntity shurikenEntity = new ShurikenEntity(world, user);
            shurikenEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, speed, 1.0f);
            shurikenEntity.setDamage(damage);
            world.spawnEntity(shurikenEntity);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }
        return TypedActionResult.success(itemStack, world.isClient());
    }

}
