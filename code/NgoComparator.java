import java.util.Comparator;

public class NgoComparator implements Comparator<NGO> {
    public int compare(NGO n1, NGO n2) {
        System.out.println(n1.getManpower() +" "+ n2.getManpower());
        if (n1.getManpower() < n2.getManpower())
            return 1;
        
        else if (n1.getManpower() > n2.getManpower())
            return -1;

        else
            return 0;
    }
    
}
