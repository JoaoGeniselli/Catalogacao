package br.com.jgeniselli.catalogacaolem.common.models;

import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by jgeniselli on 19/11/2017.
 */

public abstract class BaseModelRepository<T extends RealmObject> {

    private Class<T> clazz;

    public BaseModelRepository(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T create(T object, Realm realm) {
        long nextId = nextId(realm);

        setIdToEntity(nextId, object);

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(object);
        realm.commitTransaction();

        return object;
    }

    public List<T> create(List<T> objects, Realm realm) {
        if (CollectionUtils.isEmpty(objects)) {
            return objects;
        }

        T first = objects.get(0);

        Number currentId = realm.where(first.getClass()).max(getPrimaryKeyName());
        long nextId = currentId == null ? 1 : currentId.intValue() + 1;

        for (T obj : objects) {
            setIdToEntity(nextId, obj);
            nextId++;
        }

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(objects);
        realm.commitTransaction();

        return objects;
    }

    public long nextId(Realm realm) {
        Number currentId = realm.where(clazz).max(getPrimaryKeyName());
        return currentId == null ? 1 : currentId.intValue() + 1;
    }

    protected abstract void setIdToEntity(long id, T object);

    protected abstract String getPrimaryKeyName();

    public T read(Long id, Realm realm) {
        T object = realm.where(clazz).equalTo(getPrimaryKeyName(), id).findFirst();
        return object;
    }

    public void update(T source, T destiny, Realm realm) {
        realm.beginTransaction();
        cloneToDestiny(source, destiny);
        update(destiny, realm);
        realm.commitTransaction();
    }

    public void update(T object, Realm realm) {
        realm.copyToRealmOrUpdate(object);
    }

    public abstract void cloneToDestiny(T source, T destiny);

    public void delete(T model, Realm realm) {
        model.deleteFromRealm();
    }

}
