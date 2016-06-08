import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Multi-dimensional search
 *
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 * @version 1.0
 * @since 2016-03-23
 */

public class LP4Driver {
    static long[] description;
    static final int DLENGTH = 100000;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in;
        if (args.length > 0)
            in = new Scanner(new File(args[0]));
        else
            in = new Scanner(System.in);
        String s;
        double rv = 0;
        description = new long[DLENGTH];

        MDS mds = new MDS();
    
        while (in.hasNext()) {
            s = in.next();
            if (s.charAt(0) == '#') {
                in.nextLine();
                continue;
            }
            switch (s) {
                case "Insert": {
                    long id = in.nextLong();
                    double price = in.nextDouble();
                    long des = in.nextLong();
                    int index = 0;
                    while (des != 0) {
                        description[index++] = des;
                        des = in.nextInt();
                    }
                    description[index] = 0;
                    double temp = mds.insert(id, price, description, index);
                    rv += temp;
                    break;
                }
                case "Find": {
                    long id = in.nextLong();
                    double temp = mds.find(id);
                    rv += temp;
                    break;
                }
                case "Delete": {
                    long id = in.nextLong();
                    double temp = mds.delete(id);
                    rv += temp;
                    break;
                }
                case "FindMinPrice": {
                    long des = in.nextLong();
                    double temp = mds.findMinPrice(des);
                    rv += temp;
                    break;
                }
                case "FindMaxPrice": {
                    long des = in.nextLong();
                    double temp = mds.findMaxPrice(des);
                    rv += temp;
                    break;
                }
                case "FindPriceRange": {
                    long des = in.nextLong();
                    double lowPrice = in.nextDouble();
                    double highPrice = in.nextDouble();
                    double temp = mds.findPriceRange(des, lowPrice, highPrice);
                    rv += temp;
                    break;
                }
                case "PriceHike": {
                    long minid = in.nextLong();
                    long maxid = in.nextLong();
                    double rate = in.nextDouble();
                    double temp = mds.priceHike(minid, maxid, rate);
                    rv += temp;
                    break;
                }
                case "Range": {
                    double lowPrice = in.nextDouble();
                    double highPrice = in.nextDouble();
                    double temp = mds.range(lowPrice, highPrice);
                    rv += temp;
                    break;
                }
                case "SameSame": {
                    double temp = mds.samesame();
                    rv += temp;
                    break;
                }
                case "End":
                    break;
                default:
                    System.out.println("Houston, we have a problem.\nUnexpected line in input: " + s);
                    System.exit(0);
            }
        }
    
        System.out.printf("%.2f\n", rv);
    }
}