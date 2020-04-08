package com.babbaj.options.v1_12;

import com.google.common.collect.ImmutableMap;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Map;
import java.util.stream.Collectors;

public class KeyOptionFixer {

    private static ImmutableMap<String, Integer> CONVERSION_MAP = ImmutableMap.<String, Integer>builder()
        .put("key.keyboard.unknown", 0x00)

        .put("key.mouse.left", -100)
        .put("key.mouse.right", -99)
        .put("key.mouse.middle", -98)
        .put("key.mouse.4", -97)
        .put("key.mouse.5", -96)
        .put("key.mouse.6", -95)
        .put("key.mouse.7", -94)
        .put("key.mouse.8", -93)

        .put("key.keyboard.0", 0x0B)
        .put("key.keyboard.1", 0x02)
        .put("key.keyboard.2", 0x03)
        .put("key.keyboard.3", 0x04)
        .put("key.keyboard.4", 0x05)
        .put("key.keyboard.5", 0x06)
        .put("key.keyboard.6", 0x07)
        .put("key.keyboard.7", 0x08)
        .put("key.keyboard.8", 0x09)
        .put("key.keyboard.9", 0x0A)
        .put("key.keyboard.a", 0x1E)
        .put("key.keyboard.b", 0x30)
        .put("key.keyboard.c", 0x2E)
        .put("key.keyboard.d", 0x20)
        .put("key.keyboard.e", 0x12)
        .put("key.keyboard.f", 0x21)
        .put("key.keyboard.g", 0x22)
        .put("key.keyboard.h", 0x23)
        .put("key.keyboard.i", 0x17)
        .put("key.keyboard.j", 0x24)
        .put("key.keyboard.k", 0x25)
        .put("key.keyboard.l", 0x26)
        .put("key.keyboard.m", 0x32)
        .put("key.keyboard.n", 0x31)
        .put("key.keyboard.o", 0x18)
        .put("key.keyboard.p", 0x19)
        .put("key.keyboard.q", 0x10)
        .put("key.keyboard.r", 0x13)
        .put("key.keyboard.s", 0x1F)
        .put("key.keyboard.t", 0x14)
        .put("key.keyboard.u", 0x16)
        .put("key.keyboard.v", 0x2F)
        .put("key.keyboard.w", 0x11)
        .put("key.keyboard.x", 0x2D)
        .put("key.keyboard.y", 0x15)
        .put("key.keyboard.z", 0x2C)
        .put("key.keyboard.f1", 0x3B)
        .put("key.keyboard.f2", 0x3C)
        .put("key.keyboard.f3", 0x3D)
        .put("key.keyboard.f4", 0x3E)
        .put("key.keyboard.f5", 0x3F)
        .put("key.keyboard.f6", 0x40)
        .put("key.keyboard.f7", 0x41)
        .put("key.keyboard.f8", 0x42)
        .put("key.keyboard.f9", 0x43)
        .put("key.keyboard.f10", 0x44)
        .put("key.keyboard.f11", 0x57)
        .put("key.keyboard.f12", 0x58)
        .put("key.keyboard.f13", 0x64)
        .put("key.keyboard.f14", 0x65)
        .put("key.keyboard.f15", 0x66)
        .put("key.keyboard.f16", 0x67)
        .put("key.keyboard.f17", 0x68)
        .put("key.keyboard.f18", 0x69)
        .put("key.keyboard.f19", 0x71)
        // unknown
        .put("key.keyboard.f20", 0x00)
        .put("key.keyboard.f21", 0x00)
        .put("key.keyboard.f22", 0x00)
        .put("key.keyboard.f23", 0x00)
        .put("key.keyboard.f24", 0x00)
        .put("key.keyboard.f25", 0x00)
        .put("key.keyboard.num.lock", 0x45)
        .put("key.keyboard.keypad.0", 0x52)
        .put("key.keyboard.keypad.1", 0x4F)
        .put("key.keyboard.keypad.2", 0x50)
        .put("key.keyboard.keypad.3", 0x51)
        .put("key.keyboard.keypad.4", 0x4B)
        .put("key.keyboard.keypad.5", 0x4C)
        .put("key.keyboard.keypad.6", 0x4D)
        .put("key.keyboard.keypad.7", 0x47)
        .put("key.keyboard.keypad.8", 0x48)
        .put("key.keyboard.keypad.9", 0x49)
        .put("key.keyboard.keypad.add", 0x4E)
        .put("key.keyboard.keypad.decimal", 0x53)
        .put("key.keyboard.keypad.enter", 0x9C)
        .put("key.keyboard.keypad.equal", 0x8D)
        .put("key.keyboard.keypad.multiply", 0x37)
        .put("key.keyboard.keypad.divide", 0xB5)
        .put("key.keyboard.keypad.subtract", 0x4A)
        .put("key.keyboard.down", 0xD0)
        .put("key.keyboard.left", 0xCB)
        .put("key.keyboard.right", 0xCD)
        .put("key.keyboard.up", 0xC8)
        .put("key.keyboard.apostrophe", 0x28)
        .put("key.keyboard.backslash", 0x2B)
        .put("key.keyboard.comma", 0x33)
        .put("key.keyboard.equal", 0x0D)
        .put("key.keyboard.grave.accent", 0x29)
        .put("key.keyboard.left.bracket", 0x1A)
        .put("key.keyboard.minus", 0x0C)
        .put("key.keyboard.period", 0x34)
        .put("key.keyboard.right.bracket", 0x1B)
        .put("key.keyboard.semicolon", 0x27)
        .put("key.keyboard.slash", 0x35)
        .put("key.keyboard.space", 0x39)
        .put("key.keyboard.tab", 0x0F)
        .put("key.keyboard.left.alt", 0x38)
        .put("key.keyboard.left.control", 0x1D)
        .put("key.keyboard.left.shift", 0x2A)
        .put("key.keyboard.left.win", 0xDB)
        .put("key.keyboard.right.alt", 0xB8)
        .put("key.keyboard.right.control", 0x9D)
        .put("key.keyboard.right.shift", 0x36)
        .put("key.keyboard.right.win", 0xDC)
        .put("key.keyboard.enter", 0x1C)
        .put("key.keyboard.escape", 0x01)
        .put("key.keyboard.backspace", 0x0E)
        .put("key.keyboard.delete", 0xD3)
        .put("key.keyboard.end", 0xCF)
        .put("key.keyboard.home", 0xC7)
        .put("key.keyboard.insert", 0xD2)
        .put("key.keyboard.page.down", 0xD1)
        .put("key.keyboard.page.up", 0xC9)
        .put("key.keyboard.caps.lock", 0x3A)
        .put("key.keyboard.pause", 0xC5)
        .put("key.keyboard.scroll.lock", 0x46)
        .put("key.keyboard.menu", 0xDD)
        .put("key.keyboard.print.screen", 0xB7)
        // unknown
        .put("key.keyboard.world.1", 0x00)
        .put("key.keyboard.world.2", 0x00)
        .build();

    public static void fixSettingTagCompound(NBTTagCompound nbt) {
        int i = 0;
        try {
            i = Integer.parseInt(nbt.getString("version"));
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }

        // https://minecraft.gamepedia.com/Data_version
        if (i > 1343) { // newer than 1.12.2
            final Map<String, String> fixedData = nbt.getKeySet()
                .stream()
                .collect(Collectors.toMap(k -> k, k -> {
                    final String value = nbt.getString(k);
                    final Integer mapped = CONVERSION_MAP.get(value);

                    return mapped != null ? mapped.toString() : value;
                }));

            fixedData.forEach(nbt::setString);
        }
    }


}
