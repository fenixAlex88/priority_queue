package ru.clevertec.utils.queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CustomPriorityQueue Tests")
public class CustomPriorityQueueTest {
    CustomPriorityQueue<Object> queue;

    @BeforeEach
    void init() {
        queue = new CustomPriorityQueue<>();
    }

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {
        @Test
        @DisplayName("Test constructor with capacity")
        void testConstructorWithCapacity() {
            CustomPriorityQueue<Integer> queue = new CustomPriorityQueue<>(10);
            assertEquals(0, queue.size(), "Queue should be empty initially");
        }

        @Test
        @DisplayName("Test constructor with negative capacity")
        void testConstructorWithNegativeCapacity() {
            assertThrows(
                    IllegalArgumentException.class, () -> new CustomPriorityQueue<>(-1),
                    "Negative capacity should throw IllegalArgumentException");
        }

        @Test
        @DisplayName("Test default constructor")
        void testDefaultConstructor() {
            CustomPriorityQueue<Integer> queue = new CustomPriorityQueue<>();
            assertEquals(0, queue.size(), "Queue should be empty initially");
        }

        @Test
        @DisplayName("Test constructor with comparator")
        void testConstructorWithComparator() {
            CustomPriorityQueue<Integer> queue = new CustomPriorityQueue<>((o1, o2) -> o2 - o1);
            queue.add(7);
            queue.add(8);
            queue.add(7);
            queue.add(8);
            queue.add(11);
            queue.add(12);
            assertEquals(6, queue.size(), "Queue should be 6 elements");
            assertEquals(queue.peek(), 12, "The element at the top should be 12");
        }

        @Test
        @DisplayName("Test constructor with array")
        void testConstructorWithArray() {
            Integer[] array = {3, 1, 4, 1, 5};
            CustomPriorityQueue<Integer> queue = new CustomPriorityQueue<>(array);
            assertEquals(array.length, queue.size(), "Queue size should match array length");
            assertEquals(queue.peek(), 1, "The element at the top should be 1");
        }

        @Test
        @DisplayName("Test constructor with array and comparator")
        void testConstructorWithArrayAndComparator() {
            Integer[] array = {3, 1, 4, 1, 5};
            CustomPriorityQueue<Integer> queue = new CustomPriorityQueue<>(array, Comparator.reverseOrder());
            assertEquals(array.length, queue.size(), "Queue size should match array length");
            assertEquals(queue.peek(), 5, "The element at the top should be 5");
        }

        @Test
        @DisplayName("Test constructor with collection")
        void testConstructorWithCollection() {
            List<Integer> list = List.of(3, 1, 4, 1, 5);
            CustomPriorityQueue<Integer> queue = new CustomPriorityQueue<>(list);
            assertEquals(list.size(), queue.size(), "Queue size should match collection size");
            assertEquals(queue.peek(), 1, "The element at the top should be 1");
        }

        @Test
        @DisplayName("Test constructor with collection and comparator")
        void testConstructorWithCollectionAndComparator() {
            List<Integer> list = List.of(3, 1, 4, 1, 5);
            CustomPriorityQueue<Integer> queue = new CustomPriorityQueue<>(list, Comparator.reverseOrder());
            assertEquals(list.size(), queue.size(), "Queue size should match collection size");
            assertEquals(queue.peek(), 5, "The element at the top should be 5");
        }
    }

    @Nested
    @DisplayName("Add Tests")
    class AddTests {
        @Test
        @DisplayName("Test add single element")
        void testAddElement() {
            assertTrue(queue.add(10), "Element should be added successfully");
            assertEquals(1, queue.size(), "Queue size should be 1 after adding an element");
            assertEquals(10, queue.peek(), "The element at the top should be 10");
        }

        @Test
        @DisplayName("Test add multiple elements")
        void testAddMultipleElements() {
            assertTrue(queue.add(7), "Element should be added successfully");
            assertTrue(queue.add(12), "Element should be added successfully");
            assertTrue(queue.add(5), "Element should be added successfully");
            assertTrue(queue.add(9), "Element should be added successfully");

            assertEquals(4, queue.size(), "Queue size should be 4 after adding four elements");
            assertEquals(5, queue.peek(), "The element at the top should be 5 (assuming min-heap)");
        }

        @Test
        @DisplayName("Test add NULL element")
        void testAddNullElement() {
            assertThrows(IllegalArgumentException.class, () -> queue.add(null), "Adding null should throw IllegalArgumentException");
        }
    }

    @Nested
    @DisplayName("Poll Tests")
    class PollTests {
        @Test
        @DisplayName("Test poll from empty queue")
        void testPollFromEmptyQueue() {
            assertNull(queue.poll(), "Polling from an empty queue should return null");
        }

        @Test
        @DisplayName("Test poll single element")
        void testPollSingleElement() {
            queue.add(10);
            assertEquals(10, queue.poll(), "Polling should return the single element");
            assertEquals(0, queue.size(), "Queue size should be 0 after polling the single element");
        }

        @Test
        @DisplayName("Test poll multiple elements")
        void testPollMultipleElements() {
            queue.add(7);
            queue.add(5);
            queue.add(9);

            assertEquals(5, queue.poll(), "Polling should return the smallest element (assuming min-heap)");
            assertEquals(2, queue.size(), "Queue size should be 2 after polling one element");

            assertEquals(7, queue.poll(), "Polling should return the next smallest element");
            assertEquals(1, queue.size(), "Queue size should be 1 after polling another element");

            assertEquals(9, queue.poll(), "Polling should return the last element");
            assertEquals(0, queue.size(), "Queue size should be 0 after polling all elements");
        }

        @Test
        @DisplayName("Test poll with resize")
        void testPollWithResize() {
            for (int i = 0; i < 100; i++) {
                queue.add(i);
            }
            for (int i = 0; i < 100; i++) {
                assertEquals(i, queue.poll(), "Polling should return elements in ascending order");
            }
            assertEquals(0, queue.size(), "Queue size should be 0 after polling all elements");
        }
    }

    @Nested
    @DisplayName("Peek Tests")
    class PeekTests {
        @Test
        @DisplayName("Test peek from empty queue")
        void testPeekFromEmptyQueue() {
            assertNull(queue.peek(), "Peeking from an empty queue should return null");
        }

        @Test
        @DisplayName("Test peek single element")
        void testPeekSingleElement() {
            queue.add(10);
            assertEquals(10, queue.peek(), "Peeking should return the single element");
            assertEquals(1, queue.size(), "Queue size should remain 1 after peeking");
        }

        @Test
        @DisplayName("Test peek multiple elements")
        void testPeekMultipleElements() {
            queue.add(7);
            queue.add(5);
            queue.add(9);

            assertEquals(5, queue.peek(), "Peeking should return the smallest element (assuming min-heap)");
            assertEquals(3, queue.size(), "Queue size should remain 3 after peeking");
        }

        @Test
        @DisplayName("Test peek after poll")
        void testPeekAfterPoll() {
            queue.add(7);
            queue.add(5);
            queue.add(9);

            queue.poll();
            assertEquals(7, queue.peek(), "Peeking should return the next smallest element after polling");
            assertEquals(2, queue.size(), "Queue size should remain 2 after peeking");
        }

        @Test
        @DisplayName("Test poll after peek")
        void testPollAfterPeek() {
            queue.add(7);
            queue.add(5);
            queue.add(9);

            assertEquals(5, queue.peek(), "Peeking should return the smallest element");
            assertEquals(5, queue.poll(), "Polling should return the same smallest element");
            assertEquals(2, queue.size(), "Queue size should remain 2 after peeking and polling");
        }
    }

    @Nested
    @DisplayName("Size Tests")
    class SizeTests {
        @Test
        @DisplayName("Test size of empty queue")
        void testSizeEmptyQueue() {
            assertEquals(0, queue.size(), "Size of an empty queue should be 0");
        }

        @Test
        @DisplayName("Test size after adding elements")
        void testSizeAfterAddingElements() {
            queue.add(7);
            queue.add(5);
            queue.add(9);
            assertEquals(3, queue.size(), "Size should be 3 after adding three elements");
        }

        @Test
        @DisplayName("Test size after polling elements")
        void testSizeAfterPollingElements() {
            queue.add(7);
            queue.add(5);
            queue.add(9);
            queue.poll();
            assertEquals(2, queue.size(), "Size should be 2 after polling one element");
        }

        @Test
        @DisplayName("Test size after peeking elements")
        void testSizeAfterPeekingElements() {
            queue.add(7);
            queue.add(5);
            queue.add(9);
            queue.peek();
            assertEquals(3, queue.size(), "Size should remain 3 after peeking");
        }

        @Test
        @DisplayName("Test size after clearing queue")
        void testSizeAfterClearingQueue() {
            queue.add(7);
            queue.add(5);
            queue.add(9);
            queue.poll();
            queue.poll();
            queue.poll();
            assertEquals(0, queue.size(), "Size should be 0 after clearing the queue");
        }
    }

    @Nested
    @DisplayName("IsEmpty Tests")
    class IsEmptyTests {
        @Test
        @DisplayName("Test isEmpty on new queue")
        void testIsEmptyOnNewQueue() {
            assertTrue(queue.isEmpty(), "Newly created queue should be empty");
        }

        @Test
        @DisplayName("Test isEmpty after adding element")
        void testIsEmptyAfterAddingElement() {
            queue.add(10);
            assertFalse(queue.isEmpty(), "Queue should not be empty after adding an element");
        }

        @Test
        @DisplayName("Test isEmpty after polling all elements")
        void testIsEmptyAfterPollingAllElements() {
            queue.add(10);
            queue.add(20);
            queue.poll();
            queue.poll();
            assertTrue(queue.isEmpty(), "Queue should be empty after polling all elements");
        }

        @Test
        @DisplayName("Test isEmpty after peeking")
        void testIsEmptyAfterPeeking() {
            queue.add(10);
            queue.peek();
            assertFalse(queue.isEmpty(), "Queue should not be empty after peeking");
        }
    }

    @Nested
    @DisplayName("Resize Tests")
    class ResizeTests {
        @Test
        @DisplayName("Test resize when adding elements")
        void testResizeWhenAddingElements() {
            CustomPriorityQueue<Integer> queue = new CustomPriorityQueue<>(0);
            assertEquals(0, queue.size(), "Initial size should be 0");
            queue.add(10);
            queue.add(20);
            assertEquals(2, queue.size(), "Size should be 2 after adding two elements");
            queue.add(30);
            assertEquals(3, queue.size(), "Size should be 3 after adding three elements");
        }
    }

    @Nested
    @DisplayName("Compare Tests")
    class CompareTests {
        @Test
        @DisplayName("Test compare with non-comparable elements")
        void testCompareWithNonComparableElements() {
            CustomPriorityQueue<Object> objectQueue = new CustomPriorityQueue<>();
            Object obj1 = new Object();
            Object obj2 = new Object();
            objectQueue.add(obj1);

            assertThrows(IllegalArgumentException.class, () -> objectQueue.add(obj2), "Adding non-comparable elements should throw IllegalArgumentException");
        }

        @Test
        @DisplayName("Test compare with one comparable element")
        void testCompareWithOneComparableElement() {
            CustomPriorityQueue<Object> objectQueue = new CustomPriorityQueue<>();
            Object obj1 = new Object();
            Integer obj2 = 7;
            objectQueue.add(obj1);

            assertThrows(IllegalArgumentException.class, () -> objectQueue.add(obj2),
                    "Elements must be comparable or a comparator must be provided");
        }

        @Test
        @DisplayName("Test compare with other comparable element")
        void testCompareWithOtherComparableElement() {
            CustomPriorityQueue<Object> objectQueue = new CustomPriorityQueue<>();
            Integer obj1 = 7;
            Object obj2 = new Object();
            objectQueue.add(obj1);

            assertThrows(IllegalArgumentException.class, () -> objectQueue.add(obj2),
                    "Elements must be comparable or a comparator must be provided");
        }
    }

    @Nested
    @DisplayName("ToString Tests")
    class ToStringTests {
        @Test
        @DisplayName("Test toString with empty heap")
        public void testToStringEmptyHeap() {
            assertEquals("[]", queue.toString());
        }

        @Test
        @DisplayName("Test toString with single element")
        public void testToStringSingleElement() {
            queue.add(10);
            assertEquals("10 \n", queue.toString());
        }

        @Test
        @DisplayName("Test toString with multiple elements")
        public void testToStringMultipleElements() {
            queue.add(10);
            queue.add(20);
            queue.add(30);
            queue.add(40);
            queue.add(50);
            queue.add(60);
            queue.add(70);
            queue.add(80);
            queue.add(90);
            String expected = "10 \n20 30 \n40 50 60 70 \n80 90 \n";
            assertEquals(expected, queue.toString());
        }
    }
}
