package tn_usousse_eniso_stage.model;

public class Node {

    private String value;
    private Node next ;

    private boolean last;

    public Node() {
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public Node(String value){
        this.value =value;
        this.next =null;
    }

    public String getValue() {
        return this.value;
    }

    public Node getNext() {
        return this.next;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}


