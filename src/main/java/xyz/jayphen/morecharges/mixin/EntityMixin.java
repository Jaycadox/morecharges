package xyz.jayphen.morecharges.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.jayphen.morecharges.charges.earth.EarthChargeEntity;
import xyz.jayphen.morecharges.charges.ender.EnderChargeEntity;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(method = "tick", at = @At("RETURN"))
    public void tick(CallbackInfo cir) {
        var _this = (Entity)(Object)this;
        if (_this instanceof PlayerEntity || _this instanceof EnderChargeEntity) {
            return;
        }

        for (var e : _this.getWorld().getEntitiesByClass(EnderChargeEntity.class, Box.of(_this.getPos(), 16.0, 16.0, 16.0), earthChargeEntity -> true)) {
            e.applyForce(_this);
        }
    }

}
