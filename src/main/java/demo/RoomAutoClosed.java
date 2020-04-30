package demo;

import sun.misc.Cleaner;

/**
 * @author zhouruigang
 * 2019/8/13 14:09
 */
public class RoomAutoClosed implements AutoCloseable {

//    private static final Cleaner cleaner = Cleaner.create();

    /**
     * 内部静态
     */
    private static class State implements Runnable{
        int numJunkPiles;

        State(int numJunkPiles){
            this.numJunkPiles=numJunkPiles;
        }

        @Override
        public void run() {
            System.out.println("cleaning room....");
            numJunkPiles = 0 ;
        }
    }

  //  private final State state;
//
//    private final Cleaner.Cleanable cleanable;
//
//
//    public  RoomAutoClosed(int numJunkPiles){
//        state =new State(numJunkPiles);
//        cleanable = cleaner.register(this,state);
//    }

    @Override
    public void close() throws Exception {
//        cleanable.clean();
    }
}
