package ru.spbau.anastasia.race;


import java.util.ArrayList;
import java.util.Iterator;

public class mLayer {

    public static final int FREQUENCY_OF_ADDING = 3;
    private int lastAdding;

    ArrayList<mBasic> data = new ArrayList<>();
    int level;

    public  mLayer(int lev) {
        level = lev;
        lastAdding = FREQUENCY_OF_ADDING - 1;
    }

    public boolean tryToAdd(){
        lastAdding = (lastAdding + 1) % FREQUENCY_OF_ADDING ;
        return lastAdding == 0;
    }

    public synchronized void add(mBasic item) {
        data.add(item);
    }

    public int getSize() {
        return data.size();
    }

    public mBasic get(int i) {
        return data.get(i);
    }

    public synchronized void updateExist(){
        Iterator<mBasic> iter = data.iterator();
        while (iter.hasNext()) {
            mBasic s = iter.next();
            if (!s.exist) {
                iter.remove();
            }
        }
    }

    public synchronized void delete(mBasic barrier){
        if (barrier != null)
        {
            data.remove(barrier);
        }
    }

    public void restart(){
        data.clear();
    }

    public synchronized void clear() {
        data.clear();
    }

    public synchronized void update() {
        for (mBasic a : data) {
            if (a != null)
                a.update();
            }
    }


    public synchronized mBasic select(float f, float g) {
        mBasic tmp = null;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) != null && data.get(i).isSelected(f, g)) {
                tmp = data.get(i);
                break;
            }
        }
        return tmp;
    }
}