package thetimeloopexplorers1;


public class GenericQueue<T> {

    private GenericLinkedList<T> list;

    public GenericQueue() {
        this.list = new GenericLinkedList<>();
    }

    public void enqueue(T data) {
        list.insertLast(data);
    }

    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        return list.removeFirst();
    }

    public T top() {
        if (isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }

    public GenericQueue<T> copy() {
        GenericQueue<T> newQueue = new GenericQueue<>();
        Node<T> current = this.list.head;
        while (current != null) {
            newQueue.enqueue(current.data);
            current = current.next;
        }
        return newQueue;
    }
}
