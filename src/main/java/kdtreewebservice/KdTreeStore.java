package kdtreewebservice;

import java.util.concurrent.locks.ReentrantLock;

public class KdTreeStore implements IKdTreeOperations {
	private final int _dimension;
	private Node _root;
	private int _depth = 0;
	private ReentrantLock _mutex = new ReentrantLock();

	public KdTreeStore(int dimension) {
		_dimension = dimension;
	}

	@Override
	public void insert(int[] coordinate) {
		if (_root == null)
			_root = insertRecord(_root, coordinate, 0);
		else
			insertRecord(_root, coordinate, _depth);
	}

	@Override
	public Boolean search(int[] coordinate) {
		return searchRecord(_root, coordinate, 0);
	}

	public int getDimension() {
		return _dimension;
	}

	private Node insertRecord(Node root, int[] coordinate, int depth) {
		try {
			_mutex.lock();

			if (root == null) {
				return createNewNode(coordinate);
			}

			int currentDepth = depth % _dimension;
			if (coordinate[currentDepth] < (root.getCoordinate()[currentDepth])) {
				++_depth;
				root.setLeft(insertRecord(root.getLeft(), coordinate, depth + 1));
			} else {
				++_depth;
				root.setRight(insertRecord(root.getLeft(), coordinate, depth + 1));
			}

			return root;

		} finally {
			_mutex.unlock();
		}
	}

	private boolean searchRecord(Node root, int coordinate[], int depth) {
		if (root == null)
			return false;

		if (areCoordinatesSame(root.getCoordinate(), coordinate))
			return true;

		int currentDepth = depth % _dimension;
		if (coordinate[currentDepth] < root.getCoordinate()[currentDepth])
			return searchRecord(root.getLeft(), coordinate, depth + 1);

		return searchRecord(root.getRight(), coordinate, depth + 1);
	}

	private boolean areCoordinatesSame(int coordinate1[], int coordinate2[]) {
		for (int i = 0; i < _dimension; ++i) {
			if (coordinate1[i] != coordinate2[i])
				return false;
		}

		return true;
	}

	// A method to create a node of K D tree
	private Node createNewNode(int[] coordinate) {
		Node temp = new Node(_dimension);
		for (int i = 0; i < _dimension; i++) {
			temp.getCoordinate()[i] = coordinate[i];
		}

		return temp;
	}
}
