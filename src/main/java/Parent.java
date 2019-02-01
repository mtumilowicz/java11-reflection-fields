/**
 * Created by mtumilowicz on 2019-02-01.
 */
class Parent implements ParentInterface {
    private int privateField;
    String packagePrivateField;
    protected Object protectedField;
    public int publicField;
}

interface ParentInterface {
    String FIELD = "";
}