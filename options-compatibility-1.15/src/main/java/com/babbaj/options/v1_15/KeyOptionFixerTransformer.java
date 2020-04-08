package com.babbaj.options.v1_15;

import com.babbaj.options.v1_15.util.MethodTarget;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.*;
import java.util.function.Predicate;

import static org.objectweb.asm.Opcodes.*;

public enum KeyOptionFixerTransformer {
    ;

    public static List<MethodTarget> targets() {
        final String desc = "(Lcom/mojang/datafixers/Dynamic;Ljava/util/Map;)Lcom/mojang/datafixers/Dynamic;";
        return Arrays.asList(
            // not sure if this needs to be '/' separated for modlauncher
            new MethodTarget("net/minecraft/util/datafix/fixes/LWJGL3KeyOptions", "lambda$null$2", desc),
            new MethodTarget("net/minecraft/util/datafix/fixes/LWJGL3KeyOptions", "func_209663_a", desc),
            new MethodTarget("afn", "a", desc)
        );
    }

    public static void transformMethod(MethodNode methodNode) {
        final AbstractInsnNode start = methodNode.instructions.getFirst();
        final MethodInsnNode stream = (MethodInsnNode) findNode(start,
            insn -> insn.getOpcode() == INVOKEINTERFACE && isFunction((MethodInsnNode) insn, "java/util/Set", "stream", "()Ljava/util/stream/Stream;")
            )
            .orElseThrow(IllegalStateException::new);
        final MethodInsnNode map = new MethodInsnNode(INVOKESTATIC, "com/babbaj/options/v1_15/KeyOptionFixer", "fixValue", "(Ljava/util/stream/Stream;)Ljava/util/stream/Stream;", false);

        methodNode.instructions.insert(stream, map);
    }

    private static Optional<? extends AbstractInsnNode> findNode(AbstractInsnNode start, Predicate<AbstractInsnNode> pred) {
        AbstractInsnNode iter = start;
        while (iter != null) {
            if (pred.test(iter)) return Optional.of(iter);
            iter = iter.getNext();
        }

        return Optional.empty();
    }

    private static boolean isFunction(MethodInsnNode mn, String owner, String name, String desc) {
        return mn.owner.equals(owner) && mn.name.equals(name) && mn.desc.equals(desc);
    }
}
