package model.user;

import dao.Identifiable;

import java.io.Serializable;
import java.util.StringJoiner;

public abstract class BaseEntity implements Identifiable<Long>, Serializable {
    private static final long serialVersionUID = 1L;

    private static long nextId = 0;

    private Long id;

    public BaseEntity() {
        id = nextId;
    }

    public static long getNextId() {
        return nextId;
    }

    public static void setNextId(long nextId) {
        BaseEntity.nextId = nextId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) return false;

        BaseEntity that = (BaseEntity) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("id = '" + id + "' ")
                .toString();
    }
}
