package xyz.jayphen.morecharges.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.CreeperEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.jayphen.morecharges.MoreCharges;

@Mixin(CreeperEntity.class)
public class CreeperDropEarthChargeMixin {
    @Inject(method = "dropEquipment", at = @At("RETURN"))
    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops, CallbackInfo cir) {
        var _this = (CreeperEntity)((Object)this);
        var deltaFuse = _this.fuseTime - _this.currentFuseTime;
        if (deltaFuse <= lootingMultiplier + (lootingMultiplier + 1) && allowDrops) {
            _this.dropItem(MoreCharges.EARTH_CHARGE);
        }
    }
}

