package com.babbaj.options.v1_12;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.function.Predicate;

import static org.objectweb.asm.Opcodes.*;

@Resource // mixin is fascist
public class GameSettingTransformer implements IClassTransformer {

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
            .forEach(GameSettingTransformer::transformLoadOptions);
    }

    private static void transformLoadOptions(MethodNode mn) {
        final AbstractInsnNode start = mn.instructions.getFirst();
        final MethodInsnNode callDataFix = (MethodInsnNode) findNode(start, insn -> insn.getOpcode() == INVOKESPECIAL && isDataFix((MethodInsnNode) insn))
            .orElseThrow(IllegalStateException::new);
        final String compoundType = Type.getReturnType(callDataFix.desc).getDescriptor(); // rofl

        InsnList list = new InsnList();
        list.add(new InsnNode(DUP));
        list.add(new MethodInsnNode(INVOKESTATIC, "com/babbaj/options/v1_12/KeyOptionFixer", "fixSettingTagCompound", String.format("(%s)V", compoundType)));

        mn.instructions.insert(callDataFix, list);
    }


    private static Optional<? extends AbstractInsnNode> findNode(AbstractInsnNode start, Predicate<AbstractInsnNode> pred) {
        AbstractInsnNode iter = start;
        while (iter != null) {
            if (pred.test(iter)) return Optional.of(iter);
            iter = iter.getNext();
        }

        return Optional.empty();
    }

    private static boolean isDataFix(MethodInsnNode insn) {
        return isFunction(insn, "net/minecraft/client/settings/GameSettings", "dataFix", "(Lnet/minecraft/nbt/NBTTagCompound;)Lnet/minecraft/nbt/NBTTagCompound")
            || isFunction(insn, "bid", "a", "(Lfy;)Lfy;");
    }
    private static boolean isFunction(MethodInsnNode insn, String owner, String name, String desc) {
        return insn.owner.equals(owner) && insn.name.equals(name) && insn.desc.equals(desc);
    }

}
