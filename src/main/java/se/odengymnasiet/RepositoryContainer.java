package se.odengymnasiet;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class RepositoryContainer {

    private final Application application;

    private final Map<Class<? extends Repository>, Repository> container;

    public RepositoryContainer(Application app) {
        this.application = app;

        this.container = new LinkedHashMap<>();
    }

    public boolean install(Repository<?> repository) {
        RepositoryHandler handler = repository.getClass()
                .getDeclaredAnnotation(RepositoryHandler.class);
        if (handler == null) {
            return false;
        }

        Class<? extends Repository> clazz = handler.value();
        if (this.container.containsKey(clazz)) {
            return false;
        }

        this.container.put(clazz, repository);
        this.application.getLogger().info(
                "Repository {} has been installed to {}.",
                clazz.getSimpleName(),
                repository.getClass().getSimpleName());
        return true;
    }

    public Set<Class<? extends Repository>> keys() {
        return this.container.keySet();
    }

    public <T extends Repository> T of(Class<T> repository) {
        return (T) this.container.get(repository);
    }

    public Collection<Repository> values() {
        return this.container.values();
    }
}
