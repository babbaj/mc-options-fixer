package com.babbaj.options.v1_15;

import cpw.mods.modlauncher.api.*;
import org.objectweb.asm.tree.MethodNode;

import javax.annotation.Nonnull;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModLauncherTransformerService implements ITransformationService {
    @Nonnull
    @Override
    public String name() {
        return "SettingFixerTransformerService";
    }

    @Override
    public void initialize(IEnvironment environment) { }

    @Override
    public void beginScanning(IEnvironment environment) { }

    @Override
    public void onLoad(IEnvironment env, Set<String> otherServices) throws IncompatibleEnvironmentException { }

    @Nonnull
    @Override
    public List<ITransformer> transformers() {
        return Collections.singletonList(
            new ITransformer<MethodNode>() {
                @Nonnull
                @Override
                public MethodNode transform(MethodNode input, ITransformerVotingContext context) {
                    KeyOptionFixerTransformer.transformMethod(input);
                    return input;
                }

                @Nonnull
                @Override
                public TransformerVoteResult castVote(ITransformerVotingContext context) {
                    return TransformerVoteResult.YES;
                }

                @Nonnull
                @Override
                public Set<Target> targets() {
                    return KeyOptionFixerTransformer.targets().stream()
                        .map(t -> Target.targetMethod(t.className, t.name, t.descriptor))
                        .collect(Collectors.toSet());
                }
            }
        );
    }

    @Override
    public Map.Entry<Set<String>, Supplier<Function<String, Optional<URL>>>> additionalClassesLocator() {
        return new AbstractMap.SimpleEntry<>(
            Collections.singleton("com.babbaj.options.v1_15."),
            () -> (clazz -> Optional.ofNullable(getClass().getClassLoader().getResource(clazz)))
        );
    }
}
