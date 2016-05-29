package be.sanderdecleer.dndapp.model;

/**
 * Created by SD on 29/05/2016.
 */
public class ExpendableModel {

    public String title;
    public int expendables_max;
    public int expendables_current;

    public ExpendableModel(String title, int maxExpendables, int currentExpendables){
        this.title = title;
        this.expendables_max = maxExpendables;
        this.expendables_current = currentExpendables;
    }

}
