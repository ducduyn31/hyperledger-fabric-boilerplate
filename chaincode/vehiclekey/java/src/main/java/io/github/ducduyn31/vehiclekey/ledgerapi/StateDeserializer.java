package io.github.ducduyn31.vehiclekey.ledgerapi;

@FunctionalInterface
public interface StateDeserializer {
    State deserialize(byte[] buffer);
}
