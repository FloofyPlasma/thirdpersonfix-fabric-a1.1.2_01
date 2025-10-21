package com.floofyplasma.thirdpersonfix.mixin;

import com.floofyplasma.thirdpersonfix.ThirdPersonFixMod;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Minecraft.class)
public class MinecraftMixin
{
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventKey()I", ordinal = 4, remap = false))
    public int redir()
    {
        if (Keyboard.getEventKey() == 63)
        {
            if (((Minecraft) (Object) this).options.debugEnabled)
            {
                if (!ThirdPersonFixMod.front)
                {
                    ThirdPersonFixMod.front = true;
                }
                else
                {
                    ((Minecraft) (Object) this).options.debugEnabled = false;
                    ThirdPersonFixMod.front = false;
                }
            }
            else
            {
                ((Minecraft) (Object) this).options.debugEnabled = true;
            }
        }
        return 0;
    }
}
