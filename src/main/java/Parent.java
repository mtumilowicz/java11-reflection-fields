/**
 * Created by mtumilowicz on 2019-02-01.
 */
class Parent implements ParentInterface {
    private int privateParentField;
    String packagePrivateParentField;
    protected Object protectedParentField;
    public int publicParentField;
}

interface ParentInterface {
    String PARENT_FIELD = "";
}