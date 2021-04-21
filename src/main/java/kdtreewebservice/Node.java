package kdtreewebservice;

public class Node {
	private int[] _coordinate;
	private Node _left, _right;

	public Node(int dimension) {
		_coordinate = new int[dimension];
	}

	public Node getLeft() {
		return _left;
	}

	public Node getRight() {
		return _right;
	}

	public int[] getCoordinate() {
		return _coordinate;
	}

	public void setLeft(Node left) {
		_left = left;
	}

	public void setRight(Node right) {
		_right = right;
	}
}
