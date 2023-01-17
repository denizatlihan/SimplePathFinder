package cankaya.ie552.denizatlihan.pso;

import java.util.ArrayList;
import java.util.List;

public class ParticleHistory {

    private List<Coordinate> history = new ArrayList<Coordinate>();

    public void push(Coordinate point) {

        history.add(point);
    }

    public Coordinate pop() {

        Coordinate last = last();

        if (last != null) {

            history.remove(last);
        }

        return last;
    }

    public Coordinate last() {

        if (history.size() == 0) {

            return null;
        }

        return history.get(history.size() - 1);
    }

    public int size() {

        return history.size();
    }

    public Coordinate get(int i) {

        return history.get(i);
    }

}
