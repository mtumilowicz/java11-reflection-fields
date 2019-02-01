/**
 * Created by mtumilowicz on 2019-02-01.
 */
class Child extends Parent implements ChildInterface {
    
    private int privateChildField;
    String packagePrivateChildField;
    protected Object protectedChildField;
    public int publicChildField;
    
}

interface ChildInterface {
    String CHILD_FIELD = "";
}
