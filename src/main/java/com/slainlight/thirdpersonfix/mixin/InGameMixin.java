package com.slainlight.thirdpersonfix.mixin;

import com.slainlight.thirdpersonfix.ThirdPersonFixMod;
import net.minecraft.client.gui.InGame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGame.class)
public class InGameMixin {
    @Redirect(method = "renderHud", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/InGame;blit(IIIIII)V", ordinal = 2))
    void redir(InGame instance, int a, int b, int c, int d, int e, int f) {
        if (!ThirdPersonFixMod.front) {
            instance.blit(a,b,c,d,e,f);
        }
    }
}
