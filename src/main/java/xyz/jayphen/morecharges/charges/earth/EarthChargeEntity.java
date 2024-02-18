package xyz.jayphen.morecharges.charges.earth;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractWindChargeEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import net.minecraft.world.explosion.ExplosionBehavior;
import xyz.jayphen.morecharges.MoreCharges;

public class EarthChargeEntity extends AbstractWindChargeEntity {
    public EarthChargeEntity(EntityType<EarthChargeEntity> earthChargeEntityEntityType, World world) {
        super(MoreCharges.EARTH_CHARGE_ENTITY, world);
    }

    public EarthChargeEntity(PlayerEntity player, World world, double x, double y, double z) {
        super(MoreCharges.EARTH_CHARGE_ENTITY, world, player, x, y, z);
    }

    @Override
    public void tick() {
        super.tick();
        for(int i = 0; i < 32; ++i) {
            var offset = this.random.nextDouble() - (1.0 / 2.0);
            var offset1 = this.random.nextDouble() - (1.0 / 2.0);
            var offset2 = this.random.nextDouble() - (1.0 / 2.0);
            this.getWorld().addParticle(ParticleTypes.ASH, this.getX() + offset, this.getY() + offset1, this.getZ() + offset2, this.random.nextGaussian(), this.random.nextGaussian(), this.random.nextGaussian());
        }
    }

    public EarthChargeEntity(World world, double x, double y, double z, double directionX, double directionY, double directionZ) {
        super(MoreCharges.EARTH_CHARGE_ENTITY, x, y, z, directionX, directionY, directionZ, world);
    }

    @Override
    protected void createExplosion() {
        this.getWorld().createExplosion(null, null, new ExplosionBehavior(), this.getPos(), 4.5F, false, World.ExplosionSourceType.MOB);
    }
}
