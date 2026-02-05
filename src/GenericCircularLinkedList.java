package thetimeloopexplorers1;


public class GenericCircularLinkedList<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public GenericCircularLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = newNode;
            tail = newNode;
            newNode.next = head;
        } else {
            tail.next = newNode;
            tail = newNode;
            tail.next = head;
        }
        size++;
    }

    public T getNext() {
        if (head == null) {
            return null;
        }
        T currentData = head.data;
        head = head.next;
        tail = findTail();

        return currentData;
    }

    private Node<T> findTail() {
        if (head == null) {
            return null;
        }
        Node<T> current = head;
        while (current.next != head) {
            current = current.next;
        }
        return current;
    }

    public void reverse() {
        if (size <= 1) {
            return;
        }

        GenericLinkedListStack<T> stack = new GenericLinkedListStack<>();

        Node<T> current = head;
        do {
            stack.push(current.data);
            current = current.next;
        } while (current != head);

        this.head = null;
        this.tail = null;
        this.size = 0;

        while (!stack.isEmpty()) {
            this.add(stack.pop());
        }

        System.out.println("!!! Turn to reverse. !!!");
    }

    public GenericCircularLinkedList<T> copy() {
        GenericCircularLinkedList<T> newList = new GenericCircularLinkedList<>();
        if (head == null) {
            return newList;
        }
        Node<T> current = head;
        do {
            newList.add(current.data);
            current = current.next;
        } while (current != head);

        return newList;
    }

    public T top() {
        return (head != null) ? head.data : null;
    }

    Node<T> getHeadNode() {
        return head;
    }
}
