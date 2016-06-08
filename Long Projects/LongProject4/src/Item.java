/**
 * Class to represent the item
 */
public class Item{
    long id;
    long[] description;
    double price;
    boolean sorted;

    /**
     * Constructor for the class
     *
     * @param id Id of the item
     * @param price Price of the item
     * @param description Description of the item
     */
    Item(long id,double price,long[] description){
        this.id = id;
        this.description = description;
        this.price = price;
        sorted = false;
    }

    /**
     * Function to find the hash code of object
     *
     * @return Hash code of the object
     */
    @Override
    public int hashCode() {
        return (int)this.id;
    }

    /**
     * Function to compare two objects
     *
     * @param obj Object to be compared with
     *
     * @return True : If both the objects are equal
     *         False: If the objects are not equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        // Comparing the class of both the objects
        if (getClass() != obj.getClass())
            return false;
        final Item other = (Item) obj;
        return this.id == other.id;
    }


}