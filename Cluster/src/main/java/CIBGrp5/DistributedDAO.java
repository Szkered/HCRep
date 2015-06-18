package CIBGrp5;

public interface DistributedDAO {

    public Boolean create(String name, Object key, Object value, Boolean replicate);
    public Object read(String name, Object key);
    public Boolean update(String name, Object key, Object value, Boolean replicate);
    public Boolean delete(String name, Object key);
    
}
