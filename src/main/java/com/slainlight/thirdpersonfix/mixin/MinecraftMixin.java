package com.slainlight.thirdpersonfix.mixin;

import com.slainlight.thirdpersonfix.ThirdPersonFixMod;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Minecraft.class)
public class MinecraftMixin
{
    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventKey()I", ordinal = 6))
    public int redir()
    {
        if (Keyboard.getEventKey() == 63)
        {
            if (((Minecraft) (Object) this).options.thirdPerson)
            {
                if (!ThirdPersonFixMod.front)
                {
                    ThirdPersonFixMod.front = true;
                }
                else
                {
                    ((Minecraft) (Object) this).options.thirdPerson = false;
                    ThirdPersonFixMod.front = false;
                }
            }
            else
            {
                ((Minecraft) (Object) this).options.thirdPerson = true;
            }
        }
        return 0;
    }
}
