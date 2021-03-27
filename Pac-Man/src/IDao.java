public interface IDao<T> {
    void save(T t);
    void update(T t, String[] params);
    void remove(T t);
}