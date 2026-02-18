/** A linked list of character data objects. */
public class List {

    private Node first;
    private int size;
    
    public List() {
        first = null;
        size = 0;
    }
    
    public int getSize() { return size; }

    public CharData getFirst() {
        if (first == null) return null;
        return first.cp;
    }   

    public void addFirst(char chr) {
        CharData newch = new CharData(chr);
        Node newNode = new Node(newch);
        newNode.next = first;
        first = newNode;
        size++;
    }
    
    public String toString() {
        if (size == 0) return "()";
        StringBuilder str = new StringBuilder("(");
        Node current = first;
        while (current != null) {
            str.append(current.cp.toString());
            if (current.next != null) {
                str.append(" "); 
            }
            current = current.next;
        }
        str.append(")");
        return str.toString();
    }

    public int indexOf(char chr) {
        Node current = first; 
        int index = 0;
        while (current != null) {
            if (current.cp.chr == chr) { 
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    public void update(char chr) {
        Node current = first;
        while (current != null) {
            if (current.cp.chr == chr) {
                current.cp.count++;
                return; 
            }
            current = current.next;
        }

        CharData newData = new CharData(chr);
        Node newNode = new Node(newData);

        if (first == null) {
            first = newNode;
        } else {
            Node last = first;
            while (last.next != null) {
                last = last.next;
            }
            last.next = newNode;
        }
        size++;
    }

    public boolean remove(char chr) {
        Node prev = null;
        Node curr = first;
        while (curr != null) {
            if (curr.cp.chr == chr) { 
                if (prev == null) {
                    first = first.next;
                } else {
                    prev.next = curr.next;
                }
                size--;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }
        return false;
    }

    public CharData get(int index) {
        if (index >= size || index < 0) throw new IndexOutOfBoundsException();
        Node curr = first;
        for (int i = 0; i < index; i++) curr = curr.next;
        return curr.cp;
    }

    public CharData[] toArray() {
        CharData[] arr = new CharData[size];
        Node current = first;
        int i = 0;
        while (current != null) {
            arr[i++]  = current.cp;
            current = current.next;
        }
        return arr;
    }

    public ListIterator listIterator(int index) {
        if (size == 0) return null;
        Node current = first;
        int i = 0;
        while (i < index) {
            current = current.next;
            i++;
        }
        return new ListIterator(current);
    }
}