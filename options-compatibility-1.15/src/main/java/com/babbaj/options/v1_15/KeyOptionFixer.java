package com.babbaj.options.v1_15;

import com.mojang.datafixers.Dynamic;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.StringNBT;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Stream;

public class KeyOptionFixer {

    // TODO: dont strip key combo when forge is present
    public static Stream<Map.Entry<Dynamic<INBT>, Dynamic<INBT>>> fixValue(Stream<Map.Entry<Dynamic<INBT>, Dynamic<INBT>>> value) {
        return value.map(entry -> {
            if (entry.getKey().asString("").startsWith("key_")) {
                Dynamic<INBT> dyn = entry.getValue();
                Dynamic<INBT> newDyn = entry.getValue().asString()
                    .filter(str -> str.contains(":"))
                    .map(str -> str.split(":")[0])
                    .map(str -> new Dynamic<>(dyn.getOps(), StringNBT.valueOf(str)))
                    .orElse(dyn);

                System.out.println("XD");
                return new AbstractMap.SimpleImmutableEntry<>(entry.getKey(), newDyn);
            } else {
                return entry;
            }
        });
    }


}
