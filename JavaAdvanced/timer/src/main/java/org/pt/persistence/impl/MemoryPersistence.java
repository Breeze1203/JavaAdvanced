package org.pt.persistence.impl;

import org.pt.framework.TimerTask;
import org.pt.persistence.TaskPersistence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName MemoryPersistence
 * @Author pt
 * @Description
 * @Date 2026/1/14 19:44
 **/
public class MemoryPersistence implements TaskPersistence {
    private final ConcurrentHashMap<Long, TimerTask> store = new ConcurrentHashMap<>();

    @Override
    public void save(TimerTask task) {
        store.put(task.id, task);
    }

    @Override
    public void update(TimerTask task) {
        store.put(task.id, task);
    }

    @Override
    public void delete(long taskId) {
        store.remove(taskId);
    }

    @Override
    public List<TimerTask> loadAll() {
        return new ArrayList<>(store.values());
    }
}
