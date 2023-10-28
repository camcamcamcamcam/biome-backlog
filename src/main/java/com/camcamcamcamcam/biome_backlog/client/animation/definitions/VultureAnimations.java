package com.camcamcamcamcam.biome_backlog.client.animation.definitions;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class VultureAnimations {

    public static final AnimationDefinition FLYING = AnimationDefinition.Builder.withLength(0f)
            .addAnimation("vulture",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, -10f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("vulture",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(-52.5f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("neck",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(20f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_wing",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(-37.5f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_wing",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(-37.5f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_leg",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR))).build();
    public static final AnimationDefinition FLYING_AGRESSIVE = AnimationDefinition.Builder.withLength(0f)
            .addAnimation("vulture",
                    new AnimationChannel(AnimationChannel.Targets.POSITION,
                            new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, -10f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("vulture",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(-52.5f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("neck",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(20f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_wing",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(-37.5f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("right_leg",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(85f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_wing",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(-37.5f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR)))
            .addAnimation("left_leg",
                    new AnimationChannel(AnimationChannel.Targets.ROTATION,
                            new Keyframe(0f, KeyframeAnimations.degreeVec(85f, 0f, 0f),
                                    AnimationChannel.Interpolations.LINEAR))).build();
}
