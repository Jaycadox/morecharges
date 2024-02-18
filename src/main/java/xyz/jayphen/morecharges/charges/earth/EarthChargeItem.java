package xyz.jayphen.morecharges.charges.earth;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.WindChargeItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EarthChargeItem extends WindChargeItem {
    public EarthChargeItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            Vec3d vec3d = (new Vec3d(user.getPos().getX(), user.getEyeY(), user.getPos().getZ())).add(user.getRotationVecClient().multiply(0.8));
            if (!world.getBlockState(BlockPos.ofFloored(vec3d)).isReplaceable()) {
                vec3d = (new Vec3d(user.getPos().getX(), user.getEyeY(), user.getPos().getZ())).add(user.getRotationVecClient().multiply(0.05));
            }

            EarthChargeEntity earthChargeEntity = new EarthChargeEntity(user, world, vec3d.getX(), vec3d.getY(), vec3d.getZ());
            earthChargeEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            world.spawnEntity(earthChargeEntity);
        }

        world.playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_WIND_CHARGE_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        ItemStack itemStack = user.getStackInHand(hand);
        user.getItemCooldownManager().set(this, 10);
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.isCreative()) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}

