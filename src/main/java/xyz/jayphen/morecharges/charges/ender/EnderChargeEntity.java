package xyz.jayphen.morecharges.charges.ender;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractWindChargeEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import xyz.jayphen.morecharges.MoreCharges;

public class EnderChargeEntity extends AbstractWindChargeEntity {
    public EnderChargeEntity(EntityType<EnderChargeEntity> earthChargeEntityEntityType, World world) {
        super(MoreCharges.ENDER_CHARGE_ENTITY, world);
    }

    public EnderChargeEntity(PlayerEntity player, World world, double x, double y, double z) {
        super(MoreCharges.ENDER_CHARGE_ENTITY, world, player, x, y, z);
    }

    public EnderChargeEntity(World world, double x, double y, double z, double directionX, double directionY, double directionZ) {
        super(MoreCharges.ENDER_CHARGE_ENTITY, x, y, z, directionX, directionY, directionZ, world);
    }

    @Override
    protected void createExplosion() {
        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_TELEPORT, SoundCategory.PLAYERS);
    }

    @Override
    public boolean canHit(Entity other) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        for(int i = 0; i < 32; ++i) {
            this.getWorld().addParticle(ParticleTypes.PORTAL, this.getX(), this.getY() + this.random.nextDouble() * 2.0, this.getZ(), this.random.nextGaussian(), 0.0, this.random.nextGaussian());
        }
    }

    public void applyForce(Entity entity) {
        var pPos = entity.getEyePos();
        var ePos = this.getPos();

        var diff = new Vec3d(ePos.toVector3f()).subtract(pPos); // Get vector between charge and entity
        var age = this.age / 14;  // Get factor to reduce strength given age

        // Get scalar of similarity between entity velocity vector and our velocity vector
        var cross = Math.min(this.getVelocity().normalize().crossProduct(entity.getVelocity().normalize()).length() + 0.3, 1.0) / 2.0;
        var dist = Math.max(0.0, Math.abs(diff.length()) * cross - age); // Get strength of magnet given distance and previous factors

        if (entity instanceof PlayerEntity pl) {
            var kbRes = 0.0;
            for (var item : pl.getArmorItems()) {
                if (item != null && item.getItem() instanceof ArmorItem armor) {
                    kbRes += armor.getMaterial().value().getKnockbackResistance();
                }
            }
            dist = Math.max(dist - (kbRes) * 6.0, 0.0);
        }

        var expDiff = diff.normalize().multiply(dist / 3.5); // Multiply normalized difference vector with strength

        // Set velocities
        entity.addVelocity(expDiff);
    }
}
