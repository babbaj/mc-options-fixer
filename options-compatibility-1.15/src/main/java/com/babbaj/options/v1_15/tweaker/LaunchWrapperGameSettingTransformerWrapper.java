package com.babbaj.options.v1_15.tweaker;

import com.babbaj.options.v1_15.KeyOptionFixerTransformer;
import com.babbaj.options.v1_15.util.MethodTarget;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LaunchWrapperGameSettingTransformerWrapper implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes) {
        if (anyMatch(KeyOptionFixerTransformer.targets(), target -> target.className.replace('/', '.').equals(transformedName))) {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(bytes);
            classReader.accept(classNode, 0);

            List<MethodNode> mnList = KeyOptionFixerTransformer.targets().stream()
                .flatMap(t -> findMethod(classNode, t))
                .collect(Collectors.toList());
            if (mnList.isEmpty()) {
                throw new IllegalStateException("Failed to find method");
            }

            mnList.forEach(KeyOptionFixerTransformer::transformMethod);

            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            classNode.accept(classWriter);

            return classWriter.toByteArray();
        } else {
            return bytes;
        }
    }

    private static Stream<MethodNode> findMethod(ClassNode cla$$, MethodTarget target) {
        return cla$$.methods.stream()
            .filter(mn -> mn.name.equals(target.name) && mn.desc.equals(target.descriptor));
    }

    private static <T> boolean anyMatch(List<T> list, Predicate<T> predicate) {
        for (T x : list) {
            if (predicate.test(x)) return true;
        }
        return false;
    }
}
