package tn_usousse_eniso_stage.model;

public class Table {

    private Node[] nodes;

    public Table(int size) {
        this.nodes = new Node[size];
    }

    public Node[] getNodes() {
        return this.nodes;
    }

    /*public void setNodes() {
        this.nodes = nodes;
    }*/
}
