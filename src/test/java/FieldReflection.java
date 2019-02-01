import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by mtumilowicz on 2019-02-01.
 */
public class FieldReflection {
    
    @Test
    public void getFields_childClass() {
        var fields = Child.class.getFields();

        assertThat(fields.length, is(4));

        String fieldsAsString = Arrays.toString(fields);
        assertThat(fieldsAsString, containsString("public int Child.publicField"));
        assertThat(fieldsAsString, containsString("public int Parent.publicField"));
        assertThat(fieldsAsString, containsString("public static final java.lang.String ParentInterface.FIELD"));
        assertThat(fieldsAsString, containsString("public static final java.lang.String ChildInterface.FIELD"));
    }

    @Test
    public void getFields_parentClass() {
        var fields = Parent.class.getFields();

        assertThat(fields.length, is(2));

        String fieldsAsString = Arrays.toString(fields);
        assertThat(fieldsAsString, containsString("public int Parent.publicField"));
        assertThat(fieldsAsString, containsString("public static final java.lang.String ParentInterface.FIELD"));
    }
    
    @Test
    public void getDeclaredFields_childClass() {
        var fields = Child.class.getDeclaredFields();

        assertThat(fields.length, is(4));

        String fieldsAsString = Arrays.toString(fields);
        assertThat(fieldsAsString, containsString("private int Child.privateField"));
        assertThat(fieldsAsString, containsString("java.lang.String Child.packagePrivateField"));
        assertThat(fieldsAsString, containsString("protected java.lang.Object Child.protectedField"));
        assertThat(fieldsAsString, containsString("public int Child.publicField"));
    }

    @Test
    public void getDeclaredFields_parentClass() {
        var fields = Parent.class.getDeclaredFields();

        assertThat(fields.length, is(4));

        String fieldsAsString = Arrays.toString(fields);
        assertThat(fieldsAsString, containsString("private int Parent.privateField"));
        assertThat(fieldsAsString, containsString("java.lang.String Parent.packagePrivateField"));
        assertThat(fieldsAsString, containsString("protected java.lang.Object Parent.protectedField"));
        assertThat(fieldsAsString, containsString("public int Parent.publicField"));
    }
    
    @Test(expected = NoSuchFieldException.class)
    public void getField_notPublic() throws NoSuchFieldException {
        Child.class.getField("privateField");
    }

    @Test(expected = NoSuchFieldException.class)
    public void getField_notExists() throws NoSuchFieldException {
        Child.class.getField("not exists");
    }

    @Test(expected = NoSuchFieldException.class)
    public void getField_public() throws NoSuchFieldException {
        Child.class.getField("publicField");
    }

    @Test
    public void getDeclaredField_notPublic() throws NoSuchFieldException {
        assertThat(Child.class.getDeclaredField("privateField").toGenericString(), is("private int Child.privateField"));
    }

    @Test(expected = NoSuchFieldException.class)
    public void getDeclaredField_notExists() throws NoSuchFieldException {
        Child.class.getDeclaredField("not exists");
    }
}
