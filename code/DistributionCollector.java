import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * This class represents the collection simulation for all reserved aids in the distribution system.
 */
public class DistributionCollector {
    private File distributedFile;
    private File ngoFile;

    /**
     * No-arg constructor
     */
    public DistributionCollector(){}

    /**
     * Constructs a DistributionCollector object that contains the distribution file as its data field, so that it can be used to simulate the collection sequence
     * 
     * @param distributedFile the file object of the file that contains all the distributed aids
     * @param ngoFile the file object of the file that contains all the incomplete requests
     */
    public DistributionCollector(File distributedFile, File ngoFile){
        this.distributedFile = distributedFile;
        this.ngoFile = ngoFile;
    }

    /**
     * Enqueues the NGO into the FIFO queue
     * 
     * @param queue the destination FIFO queue
     * @param name the name of the NGO
     */
    public void EnqueueFIFOQueue(Queue<String> queue, String name){
        if ((!(isNGOInQueue(queue, name))) && isNGOInSystem(name))
            queue.offer(name);
    }

    /**
     * Enqueues the NGO into the priority queue
     * 
     * @param queue the destinatioon priority queue
     * @param name the name of the NGO
     */
    public void EnqueuePriorityQueue(PriorityQueue<NGO> queue, String name){
        ArrayList<NGO> ngos = DataManagement.readAllData(ngoFile,NGO.class);
        if ((!(isNGOInQueue(queue, name))) && isNGOInSystem(name)){
            NGO ngo = null;
            for (int i = 0; i < ngos.size(); i++){
                NGO currentNgo = ngos.get(i);
                if (name.equals(currentNgo.getName())){
                    ngo = currentNgo; 
                }
            }
            queue.offer(ngo);
        }
    }

    /**
     * Dequeues the NGO from the queue
     * 
     * @param queue the origin queue
     */
    public <E> void DequeueCollectorQueue(Queue<E> queue){
        ArrayList<Aid> distributedAids = DataManagement.readAllData(distributedFile,Aid.class);
        E ngo = queue.remove();
        for (int i = 0; i < distributedAids.size(); i++){
            if (distributedAids.get(i).getNgo().equals(ngo.toString()))
                distributedAids.get(i).setStatus("Collected");
        }
        DataManagement.writeAllData(distributedAids, distributedFile);
    }

    /**
     * Checks whether the NGO is already in the queue
     * 
     * @param queue the FIFO queue to be checked through
     * @param ngoName the name of the NGO
     * @return the boolean value of whether the NGO is already in the queue
     */
    public boolean isNGOInQueue(Queue<String> queue, String ngoName){
        for (int i = 0; i < queue.size(); i++){
            if (queue.contains(ngoName)){
                throw new InputMismatchException("The NGO is already in the queue. Please try again.");
            }
        }
        return false;
    }

    /**
     * Works like {@link isNGOInQueue(Queue<String>, String)}, but {@code queue} is a PriorityQueue<NGO> 
     * 
     * @see DistributionCollector#isNGOInQueue(Queue<String>, String)
     */
    public boolean isNGOInQueue(PriorityQueue<NGO> queue, String ngoName){
        Queue<String> stringQueue = toStringQueue(queue);

        for (int i = 0; i < stringQueue.size(); i++){
            if (stringQueue.contains(ngoName)){
                throw new InputMismatchException("The NGO is already in the queue. Please try again.");
            }
        }
        return false;
    }

    /**
     * Checks whether the NGO has any aids that has been reserved to them
     * 
     * @param NgoName the name of the queue
     * @return the boolean value of whether the NGO has any aids that has been reserved to them
     */
    public boolean isNGOInSystem(String NgoName){
        ArrayList<Aid> distributedAids = DataManagement.readAllData(distributedFile, Aid.class);

        for (int i = 0; i < distributedAids.size(); i++){
            if (distributedAids.get(i).getNgo().equals(NgoName))
                return true;
        }
        throw new InputMismatchException("The NGO does not exist in our system / There are no aids reserved for the NGO. Please try again.");
    }

    /**
     * Gets a queue that contains all the elements in a priority queue in the sorted order
     * 
     * @param queue the priority queue to be sorted
     * @return a queue that contains all the elements in a priority queue in the sorted order
     */
    public Queue<NGO> getSortedPriorityQueue(PriorityQueue<NGO> queue){
        PriorityQueue<NGO> queueCopy = new PriorityQueue<NGO>(queue);
        Queue<NGO> sortedQueue = new LinkedList<NGO>();

        while(!(queueCopy.isEmpty())){
            NGO cut = queueCopy.remove();
            sortedQueue.add(cut);
        }
        return sortedQueue;
    }

    /**
     * Converts a queue of NGOs to a queue of Strings that contains all the NGO's name
     * 
     * @param queue the origin queue
     * @return a queue of Strings that contains all the names of the NGO in the queue
     */
    public Queue<String> toStringQueue(Queue<NGO> queue){
        Queue<NGO> queueCopy = new LinkedList<NGO>(queue);
        Queue<String> stringQueue = new LinkedList<String>();

        while(!(queueCopy.isEmpty())){
            NGO cut = queueCopy.remove();
            stringQueue.add(cut.toString());
        }

        return stringQueue;
    }
}
