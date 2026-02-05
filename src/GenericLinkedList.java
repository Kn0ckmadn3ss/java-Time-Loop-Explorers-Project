package thetimeloopexplorers1;


public class GenericLinkedList<T> {

    protected Node<T> head;
    protected Node<T> tail;
    protected int size;

    public GenericLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void insertLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public void insertFirst(T data) {
        Node<T> newNode = new Node<>(data);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
        size++;
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        Node<T> removedNode = head;
        head = head.next;

        if (head == null) {
            tail = null;
        }
        size--; 
        return removedNode.data; 
    }

   
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null; 
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void displayList(boolean withIndex) {
        Node<T> current = head;
        int index = 0;
        while (current != null) {
            if (withIndex) {
                System.out.println(index + ": " + current.data.toString());
            } else {
                System.out.println(current.data.toString());
            }
            current = current.next;
            index++;
        }
    }

    public GenericLinkedList<T> copyUntiltoCurrentIndex(int index) {
        GenericLinkedList<T> newList = new GenericLinkedList<>();
        if (index < 0 || index > size) {
            return newList; 
        }

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
           
            newList.insertLast(current.data);
            current = current.next;
        }
        return newList;
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }
}
