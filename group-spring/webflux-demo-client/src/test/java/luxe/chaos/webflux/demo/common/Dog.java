package luxe.chaos.webflux.demo.common;

/**
 * <p>
 * <strong>
 * 用一句话描述功能
 * </strong><br /><br />
 * As the title says.
 * </p>
 *
 * @author Cheng, Chao - 2/5/2021 10:46 AM <br />
 * @see [相关类]
 * @since 1.0
 */
public class Dog {

    private String name;

    public Dog() {
        super();
    }

    public Dog(String name) {
        this.name = name;
    }

    public String shout() {
        return this.name + " shout Wow!";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
