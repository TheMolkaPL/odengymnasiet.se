package se.odengymnasiet;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Managing {@link Manifest}s.
 */
public class ManifestManager {

    private final Application application;

    private final Map<Class<? extends Manifest>, Manifest<?>> byClass;
    private final Map<String, Manifest<?>> byName;

    public ManifestManager(Application app) {
        this.application = app;

        this.byClass = new LinkedHashMap<>();
        this.byName = new LinkedHashMap<>();
    }

    public boolean addManifest(Manifest<?> manifest) {
        boolean fail = this.byName.containsKey(manifest.getName()) ||
                this.byClass.containsValue(manifest);
        if (!fail) {
            this.byClass.put(manifest.getClass(), manifest);
            this.byName.put(manifest.getName(), manifest);
        }

        return fail;
    }

    public <E extends Manifest> E byClass(Class<E> clazz) {
        return (E) this.byClass.get(clazz);
    }

    public Manifest<?> byName(String name) {
        return this.byName.get(name);
    }

    public Set<Class<? extends Manifest>> getManifestClasses() {
        return this.byClass.keySet();
    }

    public Set<String> getManifestNames() {
        return this.byName.keySet();
    }

    public Set<Manifest> getManifests() {
        return new LinkedHashSet<>(this.byClass.values());
    }

    public void registerManifest(Manifest<?> manifest) {
        this.addManifest(manifest.init(this.application));
    }

    public void registerManifest(Class<? extends Manifest> manifest) {
        try {
            Constructor<? extends Manifest> constructor =
                    manifest.getDeclaredConstructor();
            if (constructor != null) {
                constructor.setAccessible(true);
                this.registerManifest(constructor.newInstance());
            }
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    public void removeManifest(Manifest<?> manifest) {
        this.byClass.remove(manifest.getClass());
        this.byName.remove(manifest.getName());
    }
}
