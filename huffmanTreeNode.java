public class huffmanTreeNode {
    int ch;
    int frequency;
    huffmanTreeNode left;
    huffmanTreeNode right;

    huffmanTreeNode(Integer integer, int frequency,  huffmanTreeNode left,  huffmanTreeNode right) {
        this.ch = integer;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }
}
