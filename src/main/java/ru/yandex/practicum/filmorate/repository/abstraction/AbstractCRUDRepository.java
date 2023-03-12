package ru.yandex.practicum.filmorate.repository.abstraction;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.repository.projection.InterfaceCRUD;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public abstract class AbstractCRUDRepository<I, O>
        implements InterfaceCRUD<I, O> {

    private final Map<I, O> objectMap;

    public AbstractCRUDRepository() {
        this.objectMap = new HashMap<>();
    }

    protected Map<I, O> getObjectMap() {
        return objectMap;
    }

    public Collection<O> bringBackAllValues() {
        return new ArrayList<>(objectMap.values());
    }

    public abstract O save(O o);

    public O removeById(I id) {
        return objectMap.remove(id);
    }

    public O findById(I id) {
        return objectMap.get(id);
    }

    public void cleaner() {
        objectMap.clear();
    }

    public abstract O update(O o);

    protected boolean searchCopy(O o) {
        return !objectMap.containsValue(o);
    }
}