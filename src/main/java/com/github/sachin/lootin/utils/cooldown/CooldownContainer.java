package com.github.sachin.lootin.utils.cooldown;

import java.util.HashMap;
import java.util.UUID;

public final class CooldownContainer {

    private final CooldownTimer timer;
    private final HashMap<UUID, Cooldown> cooldowns = new HashMap<>();

    private long cooldown = 200L;

    public CooldownContainer() {
        this(new CooldownTimer());
    }

    public CooldownContainer(CooldownTimer timer) {
        this.timer = timer;
    }

    public void setCooldown(long cooldown) {
        if (cooldown <= 0) {
            throw new IllegalArgumentException("cooldown has to be higher than 0");
        }
        this.cooldown = cooldown;
        if (!cooldowns.isEmpty()) {
            for (Cooldown cooldownObj : cooldowns.values()) {
                cooldownObj.setCooldown(cooldown);
            }
        }
    }

    public long getCooldown() {
        return cooldown;
    }

    public final CooldownTimer getTimer() {
        return timer;
    }

    public Cooldown get(UUID uniqueId) {
        return cooldowns.computeIfAbsent(uniqueId, ignore -> {
            Cooldown cooldownObj = new Cooldown(cooldown);
            timer.add(cooldownObj);
            return cooldownObj;
        });
    }
    
}
