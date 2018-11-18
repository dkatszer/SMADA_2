package application;

import model.Matrix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ParallelMatrixMultiply {
    public Matrix multiply(ArrayList<Matrix> matrices) {
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        return commonPool.invoke(new MatrixMultiplyTask(matrices));
    }

    private static class MatrixMultiplyTask extends RecursiveTask<Matrix> {
        private static int THRESHOLD = 3;

        private ArrayList<Matrix> matrices; // Type of list is present intentionally for specifying list witch is good for performance results.

        public MatrixMultiplyTask(ArrayList<Matrix> matrices) {
            this.matrices = matrices;
        }

        @Override
        protected Matrix compute() {
            if (matrices.size() < THRESHOLD) {
                System.out.println(Thread.currentThread().getName() + "| Process !  matrices.size()=" + matrices.size());
                return process();
            } else {
                System.out.println(Thread.currentThread().getName() + "| Dividing task");
                Matrix result = ForkJoinTask.invokeAll(createSubtasks())
                        .stream()
                        .map(ForkJoinTask::join)
                        .reduce(Matrix::multiply)
                        .get();
                return result;
            }
        }

        private Collection<MatrixMultiplyTask> createSubtasks() {
            ArrayList<MatrixMultiplyTask> dividedTasks = new ArrayList<>();
            int indexInHalf = matrices.size() / 2;
            System.out.println(Thread.currentThread().getName() + "| First Matrixes from 0 to " + indexInHalf );
            System.out.println(Thread.currentThread().getName() + "| Second Matrixes from " + indexInHalf + " to " + matrices.size());
            ArrayList<Matrix> matricesForFirstTask = new ArrayList<>(this.matrices.subList(0, indexInHalf));
            ArrayList<Matrix> matricesForSecondTask = new ArrayList<>(this.matrices.subList(indexInHalf, matrices.size()));
            dividedTasks.add(new MatrixMultiplyTask(matricesForFirstTask));
            dividedTasks.add(new MatrixMultiplyTask(matricesForSecondTask));
            return dividedTasks;
        }

        private Matrix process() {
            Matrix result = matrices.get(0);
            for (int i = 1; i < matrices.size(); i++) {
                System.out.println(Thread.currentThread().getName() + "| Result = "  + System.lineSeparator() + result);
                System.out.println(Thread.currentThread().getName() + "| Matrix = " + System.lineSeparator() + matrices.get(i));
                result = result.multiply(matrices.get(i));
            }
            return result;
        }
    }

}
