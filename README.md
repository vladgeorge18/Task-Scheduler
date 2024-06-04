# Task Scheduling in a Datacenter
Overview
This project involves implementing a task scheduling system within a datacenter using Java Threads. The main objective is to simulate a datacenter environment and manage task allocation based on predefined scheduling policies.

# Task Scheduling System
The system architecture consists of two primary components:

Dispatcher (Load Balancer): Responsible for receiving incoming tasks and distributing them to the datacenter nodes based on specific scheduling policies.
Nodes: Execute the received tasks based on priority and can preempt running tasks for more important ones. Each node maintains a queue for tasks waiting to be executed.
Scheduling Policies
The dispatcher can operate under one of four scheduling policies:

# Round Robin (RR):

Tasks are allocated to nodes in a circular fashion.
The next task is assigned to the node with ID (i + 1) % n, where i is the ID of the last node that received a task, and n is the total number of nodes.
Starts allocation with node ID 0.


# Shortest Queue (SQ):

Tasks are assigned to the node with the shortest queue (including tasks in execution and waiting).
If multiple nodes have the same queue length, the task is assigned to the node with the smallest ID.


# Size Interval Task Assignment (SITA):

Fixed number of three nodes, each dedicated to a specific task size (short, medium, long).
Tasks are categorized by size and assigned to the corresponding node.
For example, a short task is assigned to node 0.

# Least Work Left (LWL):

Tasks are assigned to the node with the least amount of remaining work.
This includes both the tasks currently being executed and those in the queue.

# Goals
Implement the logic for the dispatcher and the computation nodes.
Simulate task scheduling within a datacenter environment.
Ensure efficient task management and execution based on the selected scheduling policy.
