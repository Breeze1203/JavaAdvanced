package org.pt.persistence;

import org.pt.framework.TimerTask;

import java.util.List;

public interface TaskPersistence {
    void save(TimerTask task);
    void update(TimerTask task);
    void delete(long taskId);
    List<TimerTask> loadAll();
}
