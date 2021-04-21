package kdtreewebservice;

public class KdStoreConfigManager {
	private static KdTreeStore _kdTreeStore;

	public static void Configure(int dimension) {
		set_kdTreeStore(new KdTreeStore(dimension));
	}

	public static KdTreeStore get_kdTreeStore() {
		return _kdTreeStore;
	}

	private static void set_kdTreeStore(KdTreeStore _kdTreeStore) {
		KdStoreConfigManager._kdTreeStore = _kdTreeStore;
	}
}
