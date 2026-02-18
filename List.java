/** A linked list of character data objects. */
public class List {

    // Points to the first node in this list
    private Node first;
    private int size;
    
    /** Constructs an empty list. */
    public List() {
        first = null;
        size = 0;
    }
    
    /** Returns the number of elements in this list. */
    public int getSize() {
          return size;
    }

    /** Returns the CharData of the first element in this list. */
    public CharData getFirst() {
        if (first == null) return null;
        return first.cp;
    }   

    /** GIVE Adds a CharData object with the given character to the beginning of this list. */
    public void addFirst(char chr) {
        CharData newch = new CharData(chr);
        Node newNode = new Node(newch);
        newNode.next = first;
        first = newNode;
        size++;
    }
    
    /** GIVE Textual representation of this list. */
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

    /** Returns the index of the first CharData object in this list. */
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

    /** * Refactored update Method:
     * 1. Updates count if char exists (and returns immediately).
     * 2. Adds new char to the END of the list (Fixes probability order).
     */

public void update(char chr) {

    int index = indexOf(chr);
    
    if (index == -1) {
        addFirst(chr);
    } else {
        CharData data = get(index);
        data.count++;
    }
}

    /** GIVE If the given character exists... removes it. */
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

    /** Returns the CharData object at the specified index. */
    public CharData get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        Node curr = first;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr.cp;
    }

    /** Returns an array of CharData objects. */
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

    /** Returns an iterator over the elements in this list. */
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