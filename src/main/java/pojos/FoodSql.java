package pojos;

public class FoodSql {

    private int id;
    private String name;
    private String type;
    private int exotic;

    public FoodSql(int id,String foodName, String foodType, int foodExotic) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getExotic() {
        return exotic;
    }

    public void setExotic(int exotic) {
        this.exotic = exotic;
    }




}
