package ForkJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class BasicsOfForkJoin {

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        SimpleRecursiveAction simpleRecursiveAction = new SimpleRecursiveAction(120);
        pool.invoke(simpleRecursiveAction);
    }
}

class SimpleRecursiveAction extends RecursiveAction {

    private int task;

    public SimpleRecursiveAction(int task) {
        this.task = task;
    }

    @Override
    protected void compute() {

        if (task > 100) {
            SimpleRecursiveAction simpleRecursiveAction1 = new SimpleRecursiveAction(task / 2);
            SimpleRecursiveAction simpleRecursiveAction2 = new SimpleRecursiveAction(task / 2);

            System.out.println("Parallel Execution of task " + task);
            simpleRecursiveAction1.fork();
            simpleRecursiveAction2.fork();
        } else {
            System.out.println("not required");
        }

    }
}
