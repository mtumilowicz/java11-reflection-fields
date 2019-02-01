# java11-reflection-fields
Fields descriptors obtained via reflection.

# preface
https://github.com/mtumilowicz/java-reflection

* `java.lang.reflect.Field` is used to represent field
* `Class` provides us with four methods to gather
fields information:
    * all fields:
        * `Field[] getFields()` - returns all public fields 
            of the class or interface (including public fields
            from superclasses and superinterfaces)
        * `Field[] getDeclaredFields()` - returns 
            all fields declared by the class or interface (
            excluding inherited fields)
    * if we know field's name:
        * `Field getField(String name)` - you can think
            of this methods as finding field in `getFields()`
            by name or throwing `NoSuchFieldException` (if field
            cannot be found)
        * `Field getDeclaredField(String name)` - you can think
            of this methods as finding field in 
            `getDeclaredFields()` by name or throwing 
            `NoSuchFieldException` (if field cannot be found)
# project description
We will show how to obtain info about declared fields.

Class structure is as simple as it can be:
* parent
    ```
    class Parent implements ParentInterface {
        private int privateField;
        String packagePrivateField;
        protected Object protectedField;
        public int publicField;
    }
    
    interface ParentInterface {
        String FIELD = "";
    }
    ```
* child
    ```
    class Child extends Parent implements ChildInterface {
        
        private int privateField;
        String packagePrivateField;
        protected Object protectedField;
        public int publicField;
        
    }
    
    interface ChildInterface {
        String FIELD = "";
    }
    ```

All tests are in `FieldReflection` class
* `getFields`
    * child
        ```
        var fields = Child.class.getFields();
        
        assertThat(fields.length, is(4));
        
        String fieldsAsString = Arrays.toString(fields);
        assertThat(fieldsAsString, containsString("public int Child.publicField"));
        assertThat(fieldsAsString, containsString("public int Parent.publicField"));
        assertThat(fieldsAsString, containsString("public static final java.lang.String ParentInterface.FIELD"));
        assertThat(fieldsAsString, containsString("public static final java.lang.String ChildInterface.FIELD"));
        ```
    * parent
        ```
        var fields = Parent.class.getFields();
        
        assertThat(fields.length, is(2));
        
        String fieldsAsString = Arrays.toString(fields);
        assertThat(fieldsAsString, containsString("public int Parent.publicField"));
        assertThat(fieldsAsString, containsString("public static final java.lang.String ParentInterface.FIELD"));
        ```
* `getDeclaredFields`
    * child
        ```
        var fields = Child.class.getDeclaredFields();
        
        assertThat(fields.length, is(4));
        
        String fieldsAsString = Arrays.toString(fields);
        assertThat(fieldsAsString, containsString("private int Child.privateField"));
        assertThat(fieldsAsString, containsString("java.lang.String Child.packagePrivateField"));
        assertThat(fieldsAsString, containsString("protected java.lang.Object Child.protectedField"));
        assertThat(fieldsAsString, containsString("public int Child.publicField"));
        ```
    * parent
        ```
        var fields = Parent.class.getDeclaredFields();
        
        assertThat(fields.length, is(4));
        
        String fieldsAsString = Arrays.toString(fields);
        assertThat(fieldsAsString, containsString("private int Parent.privateField"));
        assertThat(fieldsAsString, containsString("java.lang.String Parent.packagePrivateField"));
        assertThat(fieldsAsString, containsString("protected java.lang.Object Parent.protectedField"));
        assertThat(fieldsAsString, containsString("public int Parent.publicField"));
        ```