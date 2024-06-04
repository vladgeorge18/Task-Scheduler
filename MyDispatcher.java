/* Implement this class. */


import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MyDispatcher extends Dispatcher {

    final AtomicInteger rr_id = new AtomicInteger(0);
    final AtomicLong min_size_que = new AtomicLong(Long.MAX_VALUE);
    final AtomicLong min_size_work = new AtomicLong(Long.MAX_VALUE);
    public MyDispatcher(SchedulingAlgorithm algorithm, List<Host> hosts) {
        super(algorithm, hosts);
    }

    @Override
    public void addTask(Task task) {

        // Implementare RR
        if (algorithm.equals(SchedulingAlgorithm.ROUND_ROBIN)){
            synchronized (rr_id)
            {
                int n = hosts.size();
                hosts.get(rr_id.get() % n).addTask(task);
                rr_id.set(rr_id.get() + 1);
            }
        }
        // Implementare SQ
        else if (algorithm.equals(SchedulingAlgorithm.SHORTEST_QUEUE))
        {
            synchronized (min_size_que){
                MyHost chosen_host = (MyHost) hosts.get(0);
                min_size_que.set(hosts.get(0).getQueueSize());
                for (Host host : hosts) {

                    if (min_size_que.get() > host.getQueueSize()) {
                        min_size_que.set(host.getQueueSize());
                        chosen_host = (MyHost) host;
                    } else if (min_size_que.get() == 0 && ((MyHost) host).getCurrentTask() == null &&  chosen_host.getCurrentTask() != null) {
                        chosen_host = (MyHost) host;
                    }
                }
                chosen_host.addTask(task);
            }
        }
        // Implementare SITA
        else if (algorithm.equals(SchedulingAlgorithm.SIZE_INTERVAL_TASK_ASSIGNMENT))
        {
            if (task.getType() == TaskType.SHORT){
                hosts.get(0).addTask(task);
            } else if (task.getType() == TaskType.MEDIUM){
                hosts.get(1).addTask(task);
            } else{
                hosts.get(2).addTask(task);
            }
        }
        // Implementare LWL
        else if (algorithm.equals(SchedulingAlgorithm.LEAST_WORK_LEFT)) {
            synchronized (min_size_work) {
                MyHost chosen_host2 = (MyHost)hosts.get(0);
                min_size_work.set( hosts.get(0).getWorkLeft());
                for (Host host : hosts) {
                    if (min_size_work.get() - host.getWorkLeft() > 0) {
                        chosen_host2 = (MyHost)host;
                        min_size_work.set(host.getWorkLeft());
                    }
                }

                chosen_host2.addTask(task);

            }
        }
    }
}

