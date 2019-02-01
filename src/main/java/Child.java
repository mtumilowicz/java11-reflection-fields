/**
 * Created by mtumilowicz on 2019-02-01.
 */
class Child extends Parent implements ChildInterface {
    
    private int privateField;
    String packagePrivateField;
    protected Object protectedField;
    public int publicField;
    
}

interface ChildInterface {
    String FIELD = "";
}
