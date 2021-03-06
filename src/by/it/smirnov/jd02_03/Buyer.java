package by.it.smirnov.jd02_03;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Класс покупателя
 */
public class Buyer extends Thread implements IBuyer, IUseBacket {
    private static Semaphore semaphore = new Semaphore(10);
    private int num; // номер покупателя
    private Set<Good> listGoods; // список покупок
    private boolean pensioneer;  // признак пенсионера
    private boolean isWait;

    public Buyer(int num, boolean pensioneer) {
        super("Покупатель №" + num);
        this.num = num;
        this.pensioneer = pensioneer;
        this.listGoods=new HashSet<Good>();
        this.isWait = false;
    }

    public void setWait(boolean wait) {
        isWait = wait;
    }

    /**
     * Встал в очередь
     */
    @Override
    public void gotoQueue(){
        System.out.println(this+" встал в очередь");
        QueueBuyers.add(this);
        synchronized (this) {
            isWait = true;
            while (isWait) try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(this+" обслужился в кассе");
    }

    @Override
    public String toString() {
        String info = (pensioneer) ? " (пенсионер)" : "";
        return getName()+ info;
    }

    @Override
    public void run() {
        enterToMarket();
        takeBacket();
        chooseGood();
        gotoQueue();
        backBacket();
        goToOut();
    }

    /**
     * Вошел в магазин
     */
    @Override
    public void enterToMarket() {
        System.out.println(this + " зашел в магазин");

    }
    /**
     * Взял корзинку
     */
    @Override
    public void takeBacket() {
        Helper.sleep(Helper.getRandom((int)(100*timeKoef()), (int)(200*timeKoef())));
        System.out.println(String.format("%s взял корзинку", this));
    }

    /**
     * Делает покупки
     */
    @Override
    public void chooseGood() {
        try {
            semaphore.acquire();
            System.out.println(this + " начал выбор товаров");
            int count = Helper.getRandom(1,4);
            for (int i = 1; i <= count; i++) {
                Helper.sleep(Helper.getRandom((int)(100*timeKoef()), (int)(200*timeKoef())));
                Good good = Goods.getRandom();
                System.out.println(String.format("%s выбрал товар \"%s\" цена: %s р.", this, good.getName(), good.getPrice()));
                putGoodsToBacket(good);
            }
            Helper.sleep(Helper.getRandom((int)(500*timeKoef()), (int)(2000*timeKoef())));
            System.out.println(this + " выбрал товары");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }

    /**
     * Положил товар в корзинку
     * @param good товар
     */
    @Override
    public void putGoodsToBacket(Good good) {
        Helper.sleep(Helper.getRandom((int)(100*timeKoef()), (int)(200*timeKoef())));
        listGoods.add(good);
        System.out.println(String.format("%s положил товар \"%s\" в корзинку", this, good.getName()));
    }

    /**
     * Вышел из магазина
     */
    @Override
    public void goToOut() {
        System.out.println(this + " вышел из магазина");
        Dispatcher.countComplete.getAndAdd(1);
        Dispatcher.countBuyers.getAndAdd(-1);
    }

    /**
     * Вернул корзинку
     */
    @Override
    public void backBacket() {
        Helper.sleep(Helper.getRandom((int)(100*timeKoef()), (int)(200*timeKoef())));
        System.out.println(String.format("%s вернул корзинку", this));
    }

    private double timeKoef(){
        return (pensioneer)? 1.5 : 1;
    }

    public Set<Good> getListGoods() {
        return new HashSet<Good>(listGoods);
    }

    public boolean isPensioneer() {
        return pensioneer;
    }

}
