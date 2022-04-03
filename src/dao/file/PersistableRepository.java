package dao.file;

import dao.Identifiable;
import dao.Repository;

public interface PersistableRepository<K, V extends Identifiable<K>> extends Repository<K,V>, Persistable {
}
