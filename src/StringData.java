import java.util.HashMap;

public class StringData implements DataProvider<Integer, String> {
    private HashMap<Integer,String> _hashMap;
    public StringData(){
        _hashMap=new HashMap<>();
        _hashMap.put(1,"This is the first element");
        _hashMap.put(2,"This is the second element");
        _hashMap.put(3,"This is the third element");
        _hashMap.put(4,"This is the fourth element");
        _hashMap.put(5,"This is the fifth element");
        _hashMap.put(6,"This is the sixth element");

    }

    @Override
    public String get(Integer key) {
        return _hashMap.get(key);
    }
}
