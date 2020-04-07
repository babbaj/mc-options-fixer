package com.babbaj.options;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.function.Predicate;

import static org.objectweb.asm.Opcodes.*;

@Resource // mixin is fascist
public class Transformer implements IClassTransformer {

    {
        System.out.println("Created Transformer");
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (transformedName.equals("net.minecraft.client.settings.GameSettings")) {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(bytes);
            classReader.accept(classNode, 0);

            transformGameSettings(classNode);

            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);

            classNode.accept(classWriter);

            return classWriter.toByteArray();
        } else {
            return bytes;
        }
    }

    private static void transformGameSettings(ClassNode node) {
        node.methods.stream()
            .filter(mn -> (mn.name.equals("a") || mn.name.equals("loadOptions")) && mn.desc.equals("()V"))
            .forEach(Transformer::transformLoadOptions);
    }

    private static void transformLoadOptions(MethodNode mn) {
        final JumpInsnNode loop = findLoop(mn)
            .orElseThrow(() -> new IllegalStateException("Failed to find parsing loop"));

        final int s2Index = 5;

        AbstractInsnNode iter = loop;
        while (iter != loop.label) {
            if (iter.getOpcode() == ALOAD && ((VarInsnNode) iter).var == s2Index) {
                final MethodInsnNode parse = new MethodInsnNode(INVOKESTATIC, "com/babbaj/options/EpicOptionParser", "fix", "(Ljava/lang/String;)Ljava/lang/String;", false);
                mn.instructions.insert(iter, parse);
            }

            iter = iter.getNext();
        }

    }

    @SuppressWarnings("unchecked")
    private static Optional<JumpInsnNode> findLoop(MethodNode node) {
        AbstractInsnNode iter = node.instructions.getFirst();

        while (iter.getNext() != null) {
            if (iter.getOpcode() == GETFIELD && (isField((FieldInsnNode) iter, "net/minecraft/client/settings/GameSettings", "keyBindings")
                || isField((FieldInsnNode) iter, "bid", "as"))
            ) {
                return (Optional<JumpInsnNode>) findNode(iter, insn -> insn.getOpcode() == IF_ICMPGE);
            }

            iter = iter.getNext();
        }
        return Optional.empty();
    }

    private static Optional<? extends AbstractInsnNode> findNode(AbstractInsnNode start, Predicate<AbstractInsnNode> pred) {
        AbstractInsnNode iter = start;
        while (iter != null) {
            if (pred.test(iter)) return Optional.of(iter);
            iter = iter.getNext();
        }

        return Optional.empty();
    }

    private static boolean isField(FieldInsnNode node, String owner, String name) {
        return node.owner.equals(owner) && node.name.equals(name);
    }
}
