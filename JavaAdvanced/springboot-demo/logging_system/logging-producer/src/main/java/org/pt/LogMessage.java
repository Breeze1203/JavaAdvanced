package org.pt;

/**
 * @ClassName LogMessage
 * @Author pt
 * @Description
 * @Date 2025/8/31 20:52
 **/
public class LogMessage {
    private String id;
    private long timestamp;
    private String level;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "LogMessage{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", level='" + level + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
