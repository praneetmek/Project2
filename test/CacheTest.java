import static org.junit.Assert.*;

import org.junit.Test;

import java.util.HashMap;

/**
 * Code to test an <tt>LRUCache</tt> implementation.
 */
public class CacheTest {
	DataProvider<Integer,String> provider = new StringData();
	Cache<Integer,String> cacheWithThree = new LRUCache<Integer,String>(provider, 3);
	Cache<Integer,String> cacheWithFour = new LRUCache<Integer,String>(provider, 4);

	 class StringData implements DataProvider<Integer, String> {
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

	@Test
	public void missOnEmpty () {
		 // Need to instantiate an actual DataProvider
		cacheWithThree.get(1);


		assertEquals(1, cacheWithThree.getNumMisses());
	}
	@Test
	public void missOnSemiEmpty () {
		cacheWithThree.get(1);
		cacheWithThree.get(2);
		cacheWithThree.get(3);

		assertEquals(3, cacheWithThree.getNumMisses());
	}

	@Test
	public void missOnFull () {
		cacheWithThree.get(1);
		cacheWithThree.get(2);
		cacheWithThree.get(3);
		cacheWithThree.get(4);

		assertEquals(4, cacheWithThree.getNumMisses());
	}
	@Test
	public void hitOnOneElement () {
		cacheWithThree.get(1);
		cacheWithThree.get(1);
		assertEquals(1, cacheWithThree.getNumMisses());

	}
	@Test
	public void hitOnMultipleFirst () {
		cacheWithThree.get(1);
		cacheWithThree.get(2);
		cacheWithThree.get(1);
		assertEquals(2, cacheWithThree.getNumMisses());

	}
	@Test
	public void hitOnFullFirst () {
		cacheWithThree.get(1);
		cacheWithThree.get(2);
		cacheWithThree.get(3);
		cacheWithThree.get(1);
		cacheWithThree.get(2);
		assertEquals(3, cacheWithThree.getNumMisses());

	}
	@Test
	public void hitOnMultipleLast () {
		cacheWithThree.get(1);
		cacheWithThree.get(2);
		cacheWithThree.get(2);
		assertEquals(2, cacheWithThree.getNumMisses());

	}
	@Test
	public void hitOnFullLast () {
		cacheWithThree.get(1);
		cacheWithThree.get(2);
		cacheWithThree.get(3);
		cacheWithThree.get(3);
		assertEquals(3, cacheWithThree.getNumMisses());

	}
	@Test
	public void hitOnMultipleMiddle () {
		cacheWithFour.get(1);
		cacheWithFour.get(2);
		cacheWithFour.get(3);
		cacheWithFour.get(2);
		assertEquals(3, cacheWithFour.getNumMisses());

	}
	@Test
	public void hitOnFullMiddle () {
		cacheWithFour.get(1);
		cacheWithFour.get(2);
		cacheWithFour.get(3);
		cacheWithFour.get(4);
		cacheWithFour.get(2);
		assertEquals(4, cacheWithFour.getNumMisses());

	}
	@Test
	public void missOnFullEvicted () {
		cacheWithFour.get(1);
		cacheWithFour.get(2);
		cacheWithFour.get(3);
		cacheWithFour.get(4);
		cacheWithFour.get(5);
		cacheWithFour.get(1);
		assertEquals(6, cacheWithFour.getNumMisses());

	}

}
