package net.nimbus.structures;

import org.bukkit.scheduler.BukkitRunnable;

public abstract class Cicle implements CicleRun {
    int cicles;

    public void onFinish(){

    }
    public void runCicle(int cicles){
        this.cicles = cicles;
        Cicle c = this;
        new BukkitRunnable(){
            @Override
            public void run() {
                c.run();
                c.cicles--;
                if(c.cicles <= 0) {
                    cancel();
                    c.onFinish();
                }
            }
        }.runTaskTimer(NStructure.a, 1, 1);
    }
}
