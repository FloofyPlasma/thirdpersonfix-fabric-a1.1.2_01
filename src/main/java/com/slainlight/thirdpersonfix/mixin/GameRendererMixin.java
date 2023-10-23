package com.slainlight.thirdpersonfix.mixin;

import com.slainlight.thirdpersonfix.ThirdPersonFixMod;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.level.Level;
import net.minecraft.sortme.GameRenderer;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.maths.Vec3f;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin
{
    @ModifyVariable(method = "method_1851", at = @At("STORE"), ordinal = 2)
    private float rayCastValueFix(float value)
    {
        return value + (ThirdPersonFixMod.front ? 180.F : 0.F);
    }

    @Unique
    public Vec3f flipYAroundArbitraryY(Vec3f vec, float arbitraryY)
    {
        double reflectedY = arbitraryY + (arbitraryY - vec.y);
        return Vec3f.from(vec.x, reflectedY, vec.z);
    }

    @Inject(method = "method_1851", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V", ordinal = 6))
    private void glRotateCameraFix(float par1, CallbackInfo ci)
    {
        if (ThirdPersonFixMod.front)
            GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
    }

    @Redirect(method = "method_1851", at = @At(value = "INVOKE", target = "Lnet/minecraft/level/Level;method_160(Lnet/minecraft/util/maths/Vec3f;Lnet/minecraft/util/maths/Vec3f;)Lnet/minecraft/util/hit/HitResult;"))
    private HitResult weirdYUpsideDownRayCastFix(Level instance, Vec3f arg2, Vec3f vec3f)
    {
        return instance.method_160(arg2, ThirdPersonFixMod.front ? flipYAroundArbitraryY(vec3f, (float) ((Minecraft) FabricLoader.getInstance().getGameInstance()).viewEntity.y) : vec3f);
    }
}