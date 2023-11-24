package DataStructure.StackAndQueue.Circular_Queue;

public class CircularQueue {
    int size;
    int rear;
    int front;
    int[] data;

    public CircularQueue(int size) {
        front = rear = -1;
        this.size = size;
        data = new int[size];
    }

    public boolean enQueue(int value) {
        if (isFull()) {
            return false;
        } else {
            if (front == -1)
                front = 0;
            rear = (rear + 1) % size;
            data[rear] = value;
            return true;
        }
    }

    public boolean deQueue() {
        if(isEmpty()){
            return false;
        }else {
            if (front == rear) {
                front = -1;
                rear = -1;
            }
            front = (front + 1) % size;
            ++front;
            return true;
        }
    }

    public int Front() {
        return isEmpty() ? -1 : data[front];
    }

    public int Rear() {
        return isEmpty() ? -1 : data[rear];
    }

    public boolean isEmpty() {
        return front == -1;
    }

    public boolean isFull() {
        if (front == 0 && rear == size - 1) {
            return true;
        }
        if (front == rear + 1) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");

        if (front >= 0 && rear >= 0) {
            if(front < rear) {
                for (int i = front; i <= rear; i++) {
                    if (i == rear) {
                        result.append(data[i]);
                        break;
                    }
                    result.append(data[i]).append(", ");
                }
            }else{
                for (int i = front; i >= rear; i--) {
                    if (i == rear) {
                        result.append(data[i]);
                        break;
                    }
                    result.append(data[i]).append(", ");
                }
            }
        }
        result.append("]");
        return result.toString();
    }
}
