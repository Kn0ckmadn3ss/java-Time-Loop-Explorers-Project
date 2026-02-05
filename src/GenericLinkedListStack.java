package thetimeloopexplorers1;


public class GenericLinkedListStack <T> {
    
    private GenericLinkedList<T> list;

    public GenericLinkedListStack() {
        this.list = new GenericLinkedList<>();
    }

    
    public void push(T data) {
        list.insertFirst(data);
    }

    public T pop() {
        return list.removeFirst();
    }
    
    public T top() {
        return list.get(0);
    }
    
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    public int size() {
        return list.size();
    }
}