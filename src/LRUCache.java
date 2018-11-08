import java.util.HashMap;


/**
 * An implementation of <tt>Cache</tt> that uses a least-recently-used (LRU)
 * eviction policy.
 */

public class LRUCache<T, U> implements Cache<T, U> {
    class Node{
        U _content;

        Node _nextInList,_previousInList;
        T _key;
        private Node(T key, U content, Node nextInList, Node previousInList){
            _content=content;
            _nextInList=nextInList;
            _previousInList=previousInList;
            _key=key;
        }

    }
    private DataProvider<T,U> _provider;
    private int _capacity,_numElements,_numMisses;
    private HashMap<T, Node> _map =new HashMap<>();
    private Node _firstNode, _lastNode;

    /**
     * @param provider the data provider to consult for a cache miss
     * @param capacity the exact number of (key,value) pairs to store in the cache
     */
    public LRUCache (DataProvider<T, U> provider, int capacity) {
        if(capacity<1){
            throw new IllegalArgumentException("You cant have a capacity of less than 1!");
        }
        _provider=provider;
        _capacity=capacity;
        _numElements=0;
        _numMisses=0;
    }

    /**
     * Returns the value associated with the specified key.
     * @param key the key
     * @return the value associated with the key
     */
    private String getListAsString(){
        String s="";
        Node currentNode=_firstNode;
        while(currentNode!=null){
            s+=currentNode._key+ " ";
            currentNode=currentNode._nextInList;
        }
        return s;
    }
    public U get(T key){

        if(_map.get(key)==null){
            U addedObject=_provider.get(key);
            Node addedNode=new Node(key,addedObject,null,null);
            if(_numElements==0){
                _firstNode=addedNode;
                _lastNode=addedNode;
                _map.put(key,addedNode);
            }
            else if(_numElements<_capacity){
               // System.out.println(getListAsString());
                addedNode._previousInList=_lastNode;
                _lastNode._nextInList=addedNode;
                _lastNode=addedNode;
                _map.put(key,addedNode);
            }
           else {
               // System.out.println(getListAsString());
                _map.remove(_firstNode._key);
                _map.put(key,addedNode);
                addedNode._previousInList=_lastNode;
                _firstNode=_firstNode._nextInList;
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
            //System.out.println(getListAsString());
            U addedObject=_map.get(key)._content;
            Node newNode=new Node(key,addedObject,null,null);
            if(_numElements==1){

            }
            else if(_map.get(key).equals(_firstNode)){
                _map.remove(_firstNode);
                _map.put(key,newNode);
                _firstNode._nextInList._previousInList=null;
                _firstNode=_firstNode._nextInList;
                newNode._previousInList=_lastNode;
                _lastNode._nextInList=newNode;
                _lastNode=newNode;

            }
            else if(_map.get(key).equals(_lastNode)){

            }
            else{
                //System.out.println("The node we are checking is "+_map.get(key)._previousInList);
                _map.get(key)._previousInList._nextInList=_map.get(key)._nextInList;
                _map.get(key)._nextInList._previousInList=_map.get(key)._previousInList;
                _map.remove(key);
                _map.put(key,newNode);
                _lastNode._nextInList=newNode;
                newNode._previousInList=_lastNode;
                _lastNode=newNode;

            }
            return _lastNode._content;
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
