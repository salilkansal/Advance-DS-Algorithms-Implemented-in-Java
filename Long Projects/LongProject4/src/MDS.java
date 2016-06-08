
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

/**
 * Long Project 4
 * Multi Dimensional Search
 * @author Salil Kansal
 * @author Twinkle Sharma
 * @author Sujit Sajja
 */

public class MDS {

    TreeMap<Long, Item> idMap = new TreeMap<>();
    TreeMap<Double, HashSet<Item>> priceMap = new TreeMap<>();
    TreeMap<Long, HashSet<Item>> descriptionMap = new TreeMap<>();

    /**
     * Function to insert a new item or update an existing item
     *
     * @param id Id of the item
     * @param price Price of the item
     * @param description Description of the item
     * @param size Size of the description
     *
     * @return 1: Inserting a new item
     *         0: Updating an existing item
     */
    int insert(long id, double price, long[] description, int size) {
        short newItem = 1;
        long[] desc = Arrays.copyOf(description, size);
        Item item = idMap.get(id);
        // If the item already exists update the price and description
        if(item!=null){
            newItem = 0;
            removePrice(item);
            if(size>1)
                updateDescription(item, desc);
        }
        // If the item does not exist create a new item
        else{
            item = new Item(id,price,desc);
            idMap.put(id, item);
            insertDescription(item,desc);
        }
        insertPrice(item, price);
        return newItem;
    }

    /**
     * Function to find the price of an item
     *
     * @param id Id of the item
     *
     * @return Price of the item
     */
    double find(long id) {
        Item item = idMap.get(id);
        return item==null?0:item.price;
    }

    /**
     * Function to delete the item
     *
     * @param id Id of the item
     *
     * @return Sum of the description
     */
    long delete(long id) {
        Item temp = idMap.get(id);
        if(temp == null)
            return 0;
        idMap.remove(id);
        removePrice(temp);
        return removeDescription(temp);
    }

    /**
     * Function to find the minimum price item
     * which has the given description
     *
     * @param des Description
     *
     * @return Price of the item
     */
    double findMinPrice(long des) {
        HashSet<Item> items = descriptionMap.get(des);
        double minPrice = 0;
        if(items!=null){
            minPrice = Double.MAX_VALUE;
            for(Item item:items){
                if(item.price<minPrice)
                    minPrice = item.price;
            }
        }
        return minPrice;
    }

    /**
     * Function to find the maximum price item
     * which has the given description
     *
     * @param des Description
     *
     * @return Price of the item
     */
    double findMaxPrice(long des) {
        HashSet<Item> items = descriptionMap.get(des);
        double maxPrice = 0;
        if(items!=null){
            for(Item item:items){
                if(item.price>maxPrice)
                    maxPrice = item.price;
            }
        }
        return maxPrice;
    }

    /**
     * Function to find the number of items with the
     * given description and in given range of price
     *
     * @param des Description
     * @param lowPrice Minimum Price
     * @param highPrice Maximum Price
     *
     * @return Number of items which satisfy the given condition
     */
    int findPriceRange(long des, double lowPrice, double highPrice) {
        HashSet<Item> items = descriptionMap.get(des);
        int count = 0;
        if(items!=null){
            for(Item item:items){
                if(item.price>=lowPrice && item.price<=highPrice)
                    count++;
            }
        }
        return count;
    }

    /**
     * Function to increase the price of items
     * by given percent with id's in the given range
     *
     * @param minid Minimum ID
     * @param maxid Maximum ID
     * @param rate rate by which price is supposed to be increased
     *
     * @return Net increase in the prices of the items
     */
    double priceHike(long minid, long maxid, double rate) {
        double netIncrease = 0;
        for(Map.Entry<Long,Item> entry : idMap.tailMap(minid).entrySet()) {
            if (entry.getKey() > maxid)
                break;
            Item temp = entry.getValue();
            double previousPrice = temp.price;
            double newPrice = previousPrice *(100+rate)/100;
            newPrice = truncateDouble(newPrice);
            netIncrease += (newPrice - previousPrice);
            netIncrease = truncateDouble(netIncrease);
            removePrice(temp);
            insertPrice(temp, newPrice);
        }
        return netIncrease;
    }
    private double truncateDouble(double value) {
        double epsilon = 0.000001;
        value = Math.floor((value+epsilon) * 100) / 100;
        return value;
    }

    /**
     * Function to find the number of items in the given price range
     *
     * @param lowPrice Minimum price
     * @param highPrice Maximum price
     *
     * @return Number of items that are in given price range
     */
    int range(double lowPrice, double highPrice) {
        int count = 0;
        for(Map.Entry<Double,HashSet<Item>> entry : priceMap.tailMap(lowPrice).entrySet()) {
            Double price = entry.getKey();
            if (price > highPrice)
                break;
            count += entry.getValue().size();
        }
        return count;
    }

    /**
     * Function to find the number of items that have
     * same description with minimum length of 8
     *
     * @return Number of items that satisfy the given condition
     */
    int samesame() {
        int count = 0;
        // Maintaining hashmap with item as the key and the list
        // of items which are similar to that item as the value
        HashMap<Item,ArrayList<Item>> sameSame = new HashMap<>();
        for(Map.Entry<Long, Item> entry : idMap.entrySet()) {
            Item item = entry.getValue();
            // If the description of item is less than 8
            // then we do not consider it
            if(item.description.length<8)
                continue;
            // Sorting the description array so that
            // we can use Arrays.equals() while comparing
            if(!item.sorted) {
                Arrays.sort(item.description);
                item.sorted = true;
            }
            boolean placed = false;
            // Compare the current item with the previously added items into list
            for(Map.Entry<Item,ArrayList<Item>> same : sameSame.entrySet()) {
                Item sameKey = same.getKey();
                if(Arrays.equals(sameKey.description, item.description)){
                    placed = true;
                    ArrayList<Item> sameList = same.getValue();
                    if(sameList.size()==1)
                        count+=2;
                    else
                        count++;
                    sameList.add(item);
                    break;
                }
            }
            // If none of the previous items match with this one
            // then add this item as a new entry
            if(!placed){
                ArrayList<Item> sameList = new ArrayList<>();
                sameList.add(item);
                sameSame.put(item, sameList);
            }
        }
        return count;
    }

    /**
     * Function to remove the mapping of item with its price
     *
     * @param item Item whose mapping needs to be removed
     */
    private void removePrice(Item item){
        double price = item.price;
        HashSet<Item> temp = priceMap.get(price);
        if(temp.size()==1)
            priceMap.remove(price);
        else
            temp.remove(item);
    }

    /**
     * Function to map the item with its price
     *
     * @param item Item to be mapped
     * @param price Price to be mapped with
     */
    private void insertPrice(Item item,double price){
        HashSet<Item> tempPrice = priceMap.get(price);
        item.price = price;
        if(tempPrice == null){
            tempPrice = new HashSet<>();
            priceMap.put(price, tempPrice);
        }
        tempPrice.add(item);
    }

    /**
     * Function to update the description of the given item
     *
     * @param item Item whose description needs to be updated
     * @param description New Description
     */
    private void updateDescription(Item item, long[] description){
        // First we remove the mappings of the item with previous description
        removeDescription(item);
        // Then we map the item with its new description
        item.sorted = false;
        insertDescription(item,description);
    }

    /**
     * Function to remove the description of the given item
     *
     * @param item Item whose description mappings needs to be removed
     *
     * @return Sum of descriptions whose mapping with the item are removed
     */
    private long removeDescription(Item item){
        long sum = 0;
        for(long des:item.description){
            HashSet<Item> tempDescription = descriptionMap.get(des);
            if(tempDescription.size()==1)
                descriptionMap.remove(des);
            else
                tempDescription.remove(item);
            sum += des;
        }
        return sum;
    }

    /**
     * Function to map the description to the given item
     *
     * @param item Item to be mapped
     * @param description Description to which item is supposed to be mapped
     */
    private void insertDescription(Item item, long[] description){
        item.description = description;
        for(long des:description){
            HashSet<Item> tempDescription = descriptionMap.get(des);
            if(tempDescription == null){
                tempDescription = new HashSet<>();
                descriptionMap.put(des, tempDescription);
            }
            tempDescription.add(item);
        }
    }

}