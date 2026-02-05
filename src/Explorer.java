package thetimeloopexplorers1;


public class Explorer {

    private String name;
    private int hp;
    private int treasure;

    private GenericLinkedList<String> inventory;

    public Explorer(String name) {
        this.name = name;
        this.hp = 3;
        this.treasure = 0;
        this.inventory = new GenericLinkedList<>();
    }

    public void takeDamage(int amount) {
        this.hp -= amount;
        if (this.hp < 0) {
            this.hp = 0;
        }
        if (!isAlive()) {
            System.out.println(name + " died!");
        }
    }

    public void heal(int amount) {
        this.hp += amount;
        if (this.hp > 3) {
            this.hp = 3;
        }
    }

    public void addTreasure(int amount) {
        this.treasure += amount;
    }

    public void addStone(String stoneName) {
        inventory.insertLast(stoneName);
        System.out.println(name + "," + " found " + stoneName);
    }

    public String useStone(String stoneName) {
        Node<String> current = inventory.head;
        Node<String> prev = null;

        while (current != null) {
            if (current.data.equals(stoneName)) {
                if (prev == null) {
                    inventory.removeFirst();
                } else {
                    prev.next = current.next;
                    if (current.next == null) {
                        inventory.tail = prev;
                    }
                    inventory.size--;
                }
                return stoneName;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    public boolean hasStone(String stoneName) {
        Node<String> current = inventory.head;
        while (current != null) {
            if (current.data.equals(stoneName)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    @Override
    public String toString() {
        String status = name + " (HP: " + hp + "/3, Treasure: " + treasure + ")";
        if (!isAlive()) {
            status = name + " DEAD ";
        }
        return status;
    }

    public void printInventory() {
        System.out.print("  Inventory: ");
        if (inventory.isEmpty()) {
            System.out.println("Empty!!!");
            return;
        }
        Node<String> current = inventory.head;
        while (current != null) {
            System.out.print("[" + current.data + "] ");
            current = current.next;
        }
        System.out.println();
    }

    public Explorer copy() {
        Explorer newExplorer = new Explorer(this.name);
        newExplorer.hp = this.hp;
        newExplorer.treasure = this.treasure;
        Node<String> current = this.inventory.head;
        while (current != null) {
            newExplorer.addStone(current.data);
            current = current.next;
        }
        return newExplorer;
    }

    public String getName() {
        return name;
    }
}
