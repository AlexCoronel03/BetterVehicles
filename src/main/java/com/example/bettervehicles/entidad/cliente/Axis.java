//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.bettervehicles.entidad.cliente;

import org.joml.Quaternionf;
import org.joml.Vector3f;

@FunctionalInterface
public interface Axis {
    Axis XN = ($$0) -> {
        return (new Quaternionf()).rotationX(-$$0);
    };
    Axis XP = ($$0) -> {
        return (new Quaternionf()).rotationX($$0);
    };
    Axis YN = ($$0) -> {
        return (new Quaternionf()).rotationY(-$$0);
    };
    Axis YP = ($$0) -> {
        return (new Quaternionf()).rotationY($$0);
    };
    Axis ZN = ($$0) -> {
        return (new Quaternionf()).rotationZ(-$$0);
    };
    Axis ZP = ($$0) -> {
        return (new Quaternionf()).rotationZ($$0);
    };

    static Axis of(Vector3f $$0) {
        return ($$1) -> {
            return (new Quaternionf()).rotationAxis($$1, $$0);
        };
    }

    Quaternionf rotation(float var1);

    default Quaternionf rotationDegrees(float $$0) {
        return this.rotation($$0 * 0.017453292F);
    }
}
