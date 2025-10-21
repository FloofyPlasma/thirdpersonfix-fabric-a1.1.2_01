package com.floofyplasma.thirdpersonfix.mixin;

import com.floofyplasma.thirdpersonfix.ThirdPersonFixMod;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin
{
    @ModifyVariable(method = "transformCamera", at = @At("STORE"), ordinal = 2)
    private float rayCastValueFix(float value)
    {
        return value + (ThirdPersonFixMod.front ? 180.F : 0.F);
    }

    @Unique
    public Vec3d flipYAroundArbitraryY(Vec3d vec, float arbitraryY)
    {
        double reflectedY = arbitraryY + (arbitraryY - vec.y);
        return Vec3d.of(vec.x, reflectedY, vec.z);
    }

    @Inject(method = "transformCamera", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V", ordinal = 1), remap = false)
    private void glRotateCameraFix(float par1, CallbackInfo ci)
    {
        if (ThirdPersonFixMod.front)
            GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
    }
    // NOTE: This doesn't seem to be required? Not quite sure what it was originally doing regardless.
/*
    @Redirect(method = "transformCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/level/Level;method_160(Lnet/minecraft/util/maths/Vec3d;Lnet/minecraft/util/maths/Vec3d;)Lnet/minecraft/util/hit/HitResult;"))
    private net.minecraft.world.HitResult weirdYUpsideDownRayCastFix(World instance, Vec3d arg2, Vec3d Vec3d)
    {
        return instance.method_160(arg2, ThirdPersonFixMod.front ? flipYAroundArbitraryY(Vec3d, (float) ((Minecraft) FabricLoader.getInstance().getGameInstance()).viewEntity.y) : Vec3d);
    }

 */
}