package CIBGrp5;

public interface DistributedDAO {

    public boolean create(String name, Object key, Object value, Boolean replicate);
    public Object read(String name, Object key);
    public boolean update(String name, Object key, Object value, Boolean replicate);
    public boolean delete(String name, Object key);
    
}
