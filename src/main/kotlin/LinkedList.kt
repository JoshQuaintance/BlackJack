import java.lang.IndexOutOfBoundsException

class LinkedList (vararg values: Any) {

    var head: Node? = null
    var tail: Node? = null

    // Node Class
    data class Node (
        var value: Any,
        var next: Node? = null
    ) {

        // Makes sure it returns the value instead of the object
        override fun toString(): String {
            return "${this.value::class.simpleName}( $value )"
        }

    }

    init {
        if (values.isNotEmpty())
            for ((idx, value) in values.withIndex()) {
                val curr = Node(value)

                if (idx == 0) this.head = curr
                else this.tail?.next = curr
                this.tail = curr

            }
    }

    fun prepend(vararg values: Any) {
        // Create a new Linked list and using it
        LinkedList(*values).let {
            // Make the tail of it into the current head
            it.tail?.next = this.head

            // Then its tail into the current tail
            it.tail = this.tail

            // Exchange list (Make current list into it)
            this.head = it.head
            this.tail = it.tail
        }
    }

    fun append(vararg values: Any) {
        LinkedList(*values).let {
            this.tail?.next = it.head
            this.tail = it.tail
        }
    }


    class LinkedListIterator(private val list: LinkedList) : Iterator<Any> {
        private var curr = list.head;

        override fun next(): Any {
            val ret = curr;

            if (curr != null)
                curr = curr!!.next;
            else
                throw IndexOutOfBoundsException()

            return ret!!.value;

        }

        override fun hasNext(): Boolean {
            return curr != null
        }
    }

    operator fun iterator() : Iterator<Any> {
        return LinkedListIterator(this)
    }

    override fun toString(): String {
        var curr = this.head
        val outValues: MutableCollection<String> = mutableListOf()

        do {
            outValues.add(curr.toString())
            if (curr != null)
                curr = curr.next
        } while (curr != null)

        outValues.add("None")

        return outValues.joinToString(separator = " -> ")
    }
}