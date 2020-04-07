package com.babbaj.options.v1_15;

import cpw.mods.modlauncher.api.*;
import org.objectweb.asm.tree.MethodNode;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ModLauncherTransformerService implements ITransformationService {
    @Nonnull
    @Override
    public String name() {
        return "SettingFixerTransformerService";
    }

    @Override
    public void initialize(IEnvironment environment) {
        System.out.println("Initializing SettingFixerTransformerService");
    }

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
}
