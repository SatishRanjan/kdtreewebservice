package finalproject5278;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

public class KDStoreTest {
	@Test
	public void testTwoDimenasionalInsert() {
		KdTreeStore kdStore = new KdTreeStore(2);
		int[] coordinate1 = { 2, 10 };
		kdStore.insert(coordinate1);
		int[] coordinate2 = { 5, 20 };
		kdStore.insert(coordinate2);
		boolean result = kdStore.search(coordinate1);
		assertEquals(true, result);
	}
	
	@Test
	public void testThreeDimenasionalInsert() {
		KdTreeStore kdStore = new KdTreeStore(3);
		int[] coordinate1 = { 2, 10, 5 };
		kdStore.insert(coordinate1);
		int[] coordinate2 = { 5, 20, 2 };
		kdStore.insert(coordinate2);
		boolean result = kdStore.search(coordinate1);
		assertEquals(true, result);
	}
}
