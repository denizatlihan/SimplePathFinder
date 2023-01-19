package cankaya.ie552.denizatlihan.pso;

import java.util.ArrayList;
import java.util.List;

public class ParticleHistory<T> {

    private List<T> history = new ArrayList<T>();

    public void push(T point) {

        history.add(point);
    }

    public T pop() {

        T last = last();

        if (last != null) {

            history.remove(last);
        }

        return last;
    }

    public T last() {

        if (history.size() == 0) {

            return null;
        }

        return history.get(history.size() - 1);
    }

    public int size() {

        return history.size();
    }

    public T get(int i) {

        return history.get(i);
    }

}
