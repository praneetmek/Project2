import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Code to test an <tt>LRUCache</tt> implementation.
 */
public class CacheTest {
	@Test
	public void leastRecentlyUsedIsCorrect () {
		DataProvider<Integer,String> provider = new StringData(); // Need to instantiate an actual DataProvider
		Cache<Integer,String> cache = new LRUCache<Integer,String>(provider, 3);
		System.out.println(cache.get(1));
		System.out.println(cache.get(2));
		System.out.println(cache.get(3));
		System.out.println(cache.get(4));
		System.out.println(cache.get(1));
		System.out.println(cache.get(1));
		assertEquals(5,cache.getNumMisses());
	}
	@Test
	public void leastRecentlyUsedIsCorrect2 () {
		DataProvider<Integer,String> provider = new StringData(); // Need to instantiate an actual DataProvider
		Cache<Integer,String> cache = new LRUCache<Integer,String>(provider, 3);
		cache.get(1);
		cache.get(1);
		cache.get(1);
		cache.get(1);
		cache.get(2);
		cache.get(3);
		cache.get(4);
		cache.get(1);

		assertEquals(5,cache.getNumMisses());
	}
}
