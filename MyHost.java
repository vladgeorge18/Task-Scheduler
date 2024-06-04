/* Implement this class. */

import java.util.Comparator;
import java.util.Queue;
import java.util.PriorityQueue;

public class MyHost extends Host {
    boolean keep_running = true;
    Task current_task = null;

    //Implementare comparator pentru coada de prioritati
    static class TaskComparator implements Comparator<Task> {
        public int compare(Task t1, Task t2) {
            if (t1.getPriority() < t2.getPriority())
                return 1;
            else if (t1.getPriority() > t2.getPriority())
                return -1;
            else {
                if (t1.getId() > t2.getId())
                    return 1;
                else
                    return -1;
            }
        }
    }
        
    Queue<Task> task_que = new PriorityQueue<>(new TaskComparator());

    @Override

    public void run() {
        //ma asigur daca task-ul intra in coada nodului inainte de incepera executiei
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            System.out.println("Exceptie");
        }
        while (keep_running){

            // opresc executia
            if (this.isInterrupted())
                break;

            // daca niciun task nu ruleaza pe acest nod
            if (current_task == null)
            {

                // verific coada
                if(!task_que.isEmpty()){
                    current_task = task_que.poll();
                }
                // daca si coada e goala reiau procesul
                else{
                    continue;
                }
            }

            // daca am un task care ruleaza
            else{
                // verific daca este preemtibil si aplic modificarile necesare
                if(current_task.isPreemptible() && !task_que.isEmpty()){
                    if(task_que.peek().getPriority() > current_task.getPriority()){
                        task_que.add(current_task);
                        current_task = task_que.poll();

                    }
                }
                // daca task-ul mai are de rulat
                if(current_task.getLeft()>0){
                    try {
                        current_task.setLeft(current_task.getLeft()-1000);

                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // Handle interrupted exception if needed
                        System.out.println("Exceptie");
                    }

                // daca task-ul nu mai are de rulat il marchez ca terminat
                }
                if(current_task.getLeft()<=0){
                    current_task.finish();
                    current_task = null;
                }
            }

            // opresc executia 
            if (this.isInterrupted())
                break;
        }

    }

    @Override
    public void addTask(Task task) {
        task_que.add(task);
    }

    @Override
    public int getQueueSize() {
        return task_que.size();
    }

    @Override
    public long getWorkLeft() {
        long workLeft = 0;
        Queue<Task> copyQueue = new PriorityQueue<>(task_que);
        for (Task task : copyQueue) {
            workLeft = workLeft + task.getLeft();
        }
        if (current_task!=null)
            workLeft= workLeft+current_task.getLeft();
        return workLeft;
    }

    @Override
    public void shutdown() {
        keep_running = false;
        this.interrupt();
    }
    public Task getCurrentTask() {
        return current_task;
    }
}

