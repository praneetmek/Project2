import java.util.HashMap;


/**
 * An implementation of <tt>Cache</tt> that uses a least-recently-used (LRU)
 * eviction policy.
 */

public class LRUCache<T, U> implements Cache<T, U> {
    class Node{
        U _content;
        Node _nextInList;
        T _key;
        private Node(T key, U content, Node nextInList){
            _content=content;
            _nextInList=nextInList;
            _key=key;
        }

    }
    private DataProvider _provider;
    private int _capacity,_numElements,_numMisses;
    private HashMap<T, Node> _map =new HashMap<T, Node>();
    private Node _firstNode, _lastNode;

    /**
     * @param provider the data provider to consult for a cache miss
     * @param capacity the exact number of (key,value) pairs to store in the cache
     */
    public LRUCache (DataProvider<T, U> provider, int capacity) {
        _provider=provider;
        _capacity=capacity;
        _numElements=0;
        _numMisses=0;
    }
    private U getFromDataProvider(T key,DataProvider dataProvider){
        return (U) dataProvider.get(key);
    }

    /**
     * Returns the value associated with the specified key.
     * @param key the key
     * @return the value associated with the key
     */
    public U get(T key){
        if(_map.get(key)==null){
            U addedObject=getFromDataProvider(key,_provider);
            Node addedNode=new Node(key,addedObject,null);
            if(_numElements==0){
                _firstNode=addedNode;
                _lastNode=addedNode;
                _map.put(key,addedNode);
            }
            else if(_numElements<_capacity){
                _lastNode._nextInList=addedNode;
                _lastNode=addedNode;
                _map.put(key,addedNode);
            }
           else {
               Node newFirstNode=_firstNode._nextInList;
                _map.remove(_firstNode._key);
                _firstNode=newFirstNode;
                _lastNode._nextInList=addedNode;
                _lastNode=addedNode;
                _map.put(key,addedNode);
                _numElements--;
            }
            _numElements++;
           _numMisses++;
            return addedObject;
        }
        else{
            return _map.get(key)._content;
        }
    }

    /**
     * Returns the number of cache misses since the object's instantiation.
     * @return the number of cache misses since the object's instantiation.
     */
    public int getNumMisses (){
        return _numMisses;
    }
}
